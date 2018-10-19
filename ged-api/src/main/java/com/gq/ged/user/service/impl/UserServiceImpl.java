package com.gq.ged.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gq.ged.account.controller.req.CorporationReqForm;
import com.gq.ged.account.controller.res.v2.ActivateUser;
import com.gq.ged.account.controller.res.v2.EnterpriseAccount;
import com.gq.ged.account.controller.res.v2.PersonalAccount;
import com.gq.ged.account.dao.model.Account;
import com.gq.ged.account.service.AccountService;
import com.gq.ged.account.service.SignService;
import com.gq.ged.common.Constant;
import com.gq.ged.common.Errors;
import com.gq.ged.common.enums.ChanelStyle;
import com.gq.ged.common.enums.MsgType;
import com.gq.ged.common.exception.business.BusinessException;
import com.gq.ged.common.utils.StringUtil;
import com.gq.ged.common.utils.TokenUtil;
import com.gq.ged.common.utils.date.DateFormatUtils;
import com.gq.ged.dictionary.service.FuyouAreaService;
import com.gq.ged.message.api.res.MsgRedisEntity;
import com.gq.ged.message.service.MsgService;
import com.gq.ged.message.tmodel.MsgTypeEnum;
import com.gq.ged.order.dao.model.GedOrder;
import com.gq.ged.order.service.OrderNewService;
import com.gq.ged.user.api.req.PwdReqForm;
import com.gq.ged.user.api.req.UserQueryReqForm;
import com.gq.ged.user.api.req.UserRegisterReqForm;
import com.gq.ged.user.api.res.*;
import com.gq.ged.user.dao.mapper.UserLoginMapper;
import com.gq.ged.user.dao.mapper.UserMapper;
import com.gq.ged.user.dao.model.User;
import com.gq.ged.user.dao.model.UserExample;
import com.gq.ged.user.dao.model.UserLogin;
import com.gq.ged.user.dao.model.UserLoginExample;
import com.gq.ged.user.pc.req.UserBindForm;
import com.gq.ged.user.pc.req.UserPassForm;
import com.gq.ged.user.pc.req.UserRetrieveForm;
import com.gq.ged.user.service.UserService;
import com.gq.ged.user.tmodel.UserRegisterThriftForm;
import com.gq.ged.user.tmodel.UserRetrieveThriftForm;
import jodd.util.BCrypt;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by wyq_tomorrow on 2017/12/1.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class UserServiceImpl implements UserService {
  static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  @Resource
  UserMapper userMapper;

  @Resource
  UserLoginMapper userLoginMapper;

  @Resource
  RedissonClient redissonClient;

  @Resource
  MsgService msgService;

  @Resource
  AccountService accountService;

  @Resource
  SignService signService;

  @Resource
  FuyouAreaService FuyouAreaService;

  @Resource
  OrderNewService orderNewService;

  @Value("${user.password.salt}")
  String salt;

  @Value("${gq.cash.msm.template.url}")
  String templateUrl;


  @Override
  public UserResForm registerUser(UserRegisterThriftForm registerForm) {
    // 1、查询该手机号是否注册
    List<UserLogin> list = getUserByMobile(registerForm.getMobile());
    if (list.size() > 0) {
      throw new BusinessException(Errors.USER_MOBILE_EXISTS_ERROR);
    }
    // 2、判断验证码是否过期
    RBucket rBucket = redissonClient
        .getBucket(Constant.VALID_MSG + ":MSG_TYPE_REGISTER" + ":" + registerForm.getMobile());
    if (!rBucket.isExists()) {
      throw new BusinessException(Errors.USER_VALIDE_CODE_NOT_EXISTS_ERROR);
    }
    // 3、判断验证码是否正确
    MsgRedisEntity mr = (MsgRedisEntity) rBucket.get();
    String rcode = mr.getParam()[0];
    if (!rcode.equals(registerForm.getCode())) {
      throw new BusinessException(Errors.USER_VALIDE_CODE_NOT_EXISTS_ERROR);
    }

    // 创建用户
    User user = createUser(registerForm);
    String mobile = registerForm.getMobile();
    String uid = String.format("%032d", Long.parseLong(mobile));
    // 来源默认系统注册
    String token = TokenUtil.encodeToken((byte) 1, uid, "ged");
    redissonClient.getKeys().deleteByPattern(
        Constant.CURR_USER_INFO_TOKEN + ":" + ChanelStyle.MOBILE.getValue() + ":" + uid + "*");
    redissonClient.getBucket(Constant.CURR_USER_INFO_TOKEN + ":" + ChanelStyle.MOBILE.getValue()
        + ":" + uid + ":" + token).set(user, 7, TimeUnit.DAYS);
    UserResForm userResForm = new UserResForm();
    // 替换掉特殊字符
    userResForm.setToken(token.replaceAll("&|=|\\?", "A"));
    userResForm.setMobile(mobile);
    userResForm.setUserName(user.getCompanyName());
    return userResForm;
  }

  @Override
  @Transactional
  public void registerUserByLoanSys(UserRegisterReqForm reqForm) throws Exception {
    logger.info("registerUserByLoanSys==" + JSONObject.toJSONString(reqForm));
    // 判断手机号是否存在
    if (StringUtils.isNotBlank(reqForm.getMobile())) {
      UserExample example1 = new UserExample();
      example1.createCriteria().andMobileEqualTo(reqForm.getMobile());
      List<User> list2 = userMapper.selectByExample(example1);
      if (list2.size() > 0) {
        throw new BusinessException(Errors.USER_COMPANY_EXISTS_ERROR);
      }
    }

    // 1、判断用户是否存在
    UserExample example = new UserExample();
    if (reqForm.getUserType() == null) {
      throw new BusinessException(Errors.USER_TYPE_NOT_EXISTS_ERROR);
    }
    if (reqForm.getUserRole() == 1) {
      logger.info("==========担保人=========");
      reqForm.setUserType(0);
    }
    logger.info("userType=" + reqForm.getUserType());
    if (reqForm.getUserType() == 0) {
      example.createCriteria().andMobileEqualTo(reqForm.getMobile());
    } else if (reqForm.getUserType() == 1) {
      if (StringUtils.isBlank(reqForm.getCode())) {
        throw new BusinessException(Errors.USER_SHTYDM_NOT_EXISTS_ERROR);
      }
      example.createCriteria().andCompanyCardTypeEqualTo(reqForm.getType())
          .andCompanyCardCodeEqualTo(reqForm.getCode());
    }

    /*
     * if (reqForm.getUserRole() == 0 || reqForm.getUserRole() == 2 || reqForm.getUserRole() == 3) {
     * example.createCriteria().andCompanyCardTypeEqualTo(reqForm.getType())
     * .andCompanyCardCodeEqualTo(reqForm.getCode()); } else if (reqForm.getUserRole() == 1 ||
     * reqForm.getUserRole() == 4) { example.createCriteria().andMobileEqualTo(reqForm.getMobile());
     * }
     */

    List<User> list = userMapper.selectByExample(example);
    if (list.size() > 0) {
      throw new BusinessException(Errors.USER_COMPANY_EXISTS_ERROR);
    }

    Date date = new Date();
    UserLogin userLogin = new UserLogin();
    String password = "";
    String mobile = reqForm.getMobile();
    String socialCreditCode = reqForm.getCode();
    userLogin.setMobile(mobile);
    if (reqForm.getRegisterType() != null && "2".equals(reqForm.getRegisterType())) {
      password =
          socialCreditCode.substring(socialCreditCode.length() - 6, socialCreditCode.length());
    } else {
      password = mobile.substring(mobile.length() - 6, mobile.length());
    }
    userLogin.setPassword(BCrypt.hashpw(password, salt));
    userLogin.setSocialCreditCode(reqForm.getCode());
    userLogin.setIsEnabled(Constant.COMMONE_ONE);
    userLogin.setCreateTime(date);
    userLogin.setCreateId(-1L);
    userLoginMapper.insertSelective(userLogin);
    User user = new User();
    user.setId(userLogin.getId());
    user.setStatus(reqForm.getStatus());
    user.setGuaranteeAmount(reqForm.getGuaranteeAmount());
    user.setMobile(mobile);

    user.setCompanyCardCode(reqForm.getCode());
    // 企业社会统一代码
    if (reqForm.getType() != null && "1".equals(reqForm.getType())) {
      user.setSocialCreditCode(reqForm.getCode());
    }

    user.setUserType(reqForm.getUserType());
    user.setCompanyCardType(reqForm.getType());
    user.setUserRole(reqForm.getUserRole());
    user.setIsEnabled(Constant.COMMONE_ONE);
    user.setCreateId(-1L);
    user.setCreateTime(date);
    userMapper.insertSelective(user);
    // 发送短信
    msgService.sendMessageByLoanSysRegister(mobile, mobile, password);
    // }

    logger.info("创建冠e贷账户接口请求参数：" + JSON.toJSONString(reqForm));
    // 关联账户
    if (StringUtils.isNotBlank(reqForm.getBankCode())) {
      relationAccountByBankCode(reqForm.getBankCode(), user, user.getSocialCreditCode());
    } else if (StringUtils.isNotBlank(user.getSocialCreditCode())) {
      relationAccountBySocialCreditCode(user.getId(), user.getSocialCreditCode());
    }

  }

  private void relationAccountByBankCode(String bankCode, User user, String socialCreditCode)
      throws Exception {
    logger.info("创建冠e贷账户>>>执行关联账户>>>");
    Account account = accountService.selectAccountByCode(bankCode);
    logger.info("创建冠e贷账户>>> account的值" + (account == null));
    Long userId = user.getId();
    if (account != null) {
      logger.info("创建冠e贷账户>>>查询到的个人户信息：>>>" + JSON.toJSONString(account));
      if (account.getUserId() == null) {
        String seqNo = DateFormatUtils.formatDate(new Date(), "yyyyMMddHHmmssSSS");
        account.setAccountCode(seqNo);
        account.setSocialCreditCode(socialCreditCode);
        account.setUserId(userId);
        account.setModifyId(userId);
        logger.info("创建冠e贷账户>>>更新的个人户信息：>>>" + JSON.toJSONString(account));
        accountService.updateAccount(account);
      } else {
        Account account1 = new Account();
        BeanUtils.copyProperties(account, account1);
        account1.setSocialCreditCode(socialCreditCode);
        account1.setUserId(userId);
        account1.setId(null);
        accountService.insertAccount(account1);
      }

      user.setGetCustId(account.getCustId());
      user.setCheckAccountFlag(account.getStatus());// 4签约成功
      user.setModifyId(userId);
      user.setModifyTime(new Date());
      logger.info("创建冠e贷账户>>>更新的用户信息：>>>" + JSON.toJSONString(user));
      updateUser(user);
      account.setUserId(userId);
      signService.callLoanPlatformCreateAccount(account, socialCreditCode);
    }
  }

  private void relationAccountBySocialCreditCode(Long userId, String socialCreditCode)
      throws Exception {
    Account account = accountService.selectAccountBySocialCreditCode(socialCreditCode);
    if (account != null) {
      String seqNo = DateFormatUtils.formatDate(new Date(), "yyyyMMddHHmmssSSS");
      account.setAccountCode(seqNo);
      account.setSocialCreditCode(socialCreditCode);
      account.setUserId(userId);
      account.setModifyId(userId);
      logger.info("创建冠e贷账户>>>更新的个人户信息：>>>" + JSON.toJSONString(account));
      accountService.updateAccount(account);
      signService.callLoanPlatformCreateAccount(account, socialCreditCode);
    }
  }

  @Override
  public UserQueryResForm queryUserByLoanSys(UserQueryReqForm reqForm) {
    UserExample example = new UserExample();
    UserExample.Criteria criteria = example.createCriteria();
    Errors errors;
    if (reqForm.getUserRole() == 0 || reqForm.getUserRole() == 2 || reqForm.getUserRole() == 3) {
      criteria.andCompanyCardTypeEqualTo(reqForm.getType())
          .andCompanyCardCodeEqualTo(reqForm.getCode());
      errors = Errors.USER_COMPANY_EXISTS_ERROR;
    } else {
      criteria.andMobileEqualTo(reqForm.getMobile());
      errors = Errors.USER_COMPANY_MOBILE_EXISTS_ERROR;
    }
    List<User> list = userMapper.selectByExample(example);
    UserQueryResForm resForm = new UserQueryResForm();
    if (list.size() > 0) {
      User user = list.get(0);
      // 更新用户信息
      user.setCompanyCardType(reqForm.getType());
      user.setCompanyCardCode(reqForm.getCode());
      user.setSocialCreditCode(reqForm.getCode());
      userMapper.updateByPrimaryKeySelective(user);
      if (StringUtils.isBlank(user.getMobile())) {
        resForm.setMobile(reqForm.getCode());
      } else {
        resForm.setMobile(user.getMobile());
      }
      resForm.setCode(reqForm.getCode());
      resForm.setType(reqForm.getType());
      resForm.setResultCode(errors.getCode());
      resForm.setMessage(errors.getLabel());
      return resForm;
    } else {
      resForm.setResultCode(Errors.SUCCESS.getCode());
    }

    /*
     * list = userMapper.selectByExample(example); if (list.size() > 0) { User user = list.get(0);
     * // 更新用户信息 user.setCompanyCardType(reqForm.getType());
     * user.setCompanyCardCode(reqForm.getCode()); user.setSocialCreditCode(reqForm.getCode());
     * userMapper.updateByPrimaryKeySelective(user);
     * 
     * resForm.setMobile(user.getMobile()); resForm.setCode(reqForm.getCode());
     * resForm.setType(reqForm.getType());
     * resForm.setResultCode(Errors.USER_COMPANY_EXISTS_ERROR.getCode());
     * resForm.setMessage(Errors.USER_COMPANY_EXISTS_ERROR.getLabel()); } else {
     * resForm.setResultCode(Errors.SUCCESS.getCode()); }
     */
    return resForm;
  }

  private User createUser(UserRegisterThriftForm reqForm) {
    UserLogin userLogin = new UserLogin();
    Date date = new Date();
    userLogin.setMobile(reqForm.getMobile());
    if (StringUtils.isNotBlank(reqForm.password)) {
      // userReqForm.getPasswd()
      userLogin.setPassword(BCrypt.hashpw(reqForm.password, salt));
    }
    userLogin.setIsEnabled(Constant.COMMONE_ONE);
    userLogin.setCreateId(-1L);
    userLogin.setCreateTime(date);
    userLogin.setModifyId(-1L);
    userLogin.setModifyTime(date);
    userLoginMapper.insertSelective(userLogin);

    // 插入用户
    User user = new User();
    user.setId(userLogin.getId());
    user.setMobile(userLogin.getMobile());
    user.setActiveFlag(1);
    user.setStatus(0);
    user.setRecommendCode(reqForm.getRecommendCode());
    user.setIsEnabled(Constant.COMMONE_ONE);
    user.setCreateId(userLogin.getId());
    user.setCreateTime(date);
    user.setModifyId(userLogin.getId());
    user.setModifyTime(date);
    userMapper.insertSelective(user);
    return user;
  }

  @Override
  public UserResForm loginUser(String mobile, String pwd, String chanelStyle) {
    // 1、判断传入的是手机号还是社会统一代码
    List<UserLogin> list = new ArrayList<>();
    Errors errors;
    if (StringUtil.isMoblie(mobile)) {
      list = getUserByMobile(mobile);
      errors = Errors.USER_MOBILE_NOT_EXISTS_ERROR;
    } else {
      list = getUserBySocialCreditCode(mobile);
      errors = Errors.USER_COMPANY_NOT_EXISTS_ERROR;
    }
    if (list.size() == 0) {
      throw new BusinessException(errors);
    }
    UserLogin userLogin = list.get(0);
    String inPasswd = BCrypt.hashpw(pwd, salt);
    RBucket rBucket = redissonClient.getBucket(Constant.LOGIN_ERROR + mobile);
    if (rBucket.isExists()) {
      UserLoginErrorForm errorForm = (UserLoginErrorForm) rBucket.get();
      if (errorForm.isTimeFlag()) {
        rBucket.set(errorForm, 10, TimeUnit.MINUTES);
        throw new BusinessException(Errors.BUSI_PASSWORD_WRONG_TOO_MANY_ERROR);
      }
    }
    if (!inPasswd.equals(userLogin.getPassword())) {
      logger.info("密码错误");
      setPassErrorForm(mobile, rBucket);
    }
    rBucket.delete();
    return afterLoginReturnToken(userLogin.getId(), mobile, chanelStyle);
  }

  private void setPassErrorForm(String mobile, RBucket rBucket) {
    if (rBucket.isExists()) {
      UserLoginErrorForm errorForm = (UserLoginErrorForm) rBucket.get();
      int count = errorForm.getCount();
      if (count >= 5) {
        errorForm.setCount(5);
        errorForm.setTimeFlag(true);
        rBucket.set(errorForm, 10, TimeUnit.MINUTES);
        throw new BusinessException(Errors.BUSI_PASSWORD_WRONG_TOO_MANY_ERROR);
      } else {
        String msg = Errors.BUSI_PASSWORD_WRONG_ERROR.getLabel();
        String tomsg = String.format(msg, String.valueOf(4 - count));
        errorForm.setCount(count + 1);
        if (4 - count == 0) {
          errorForm.setTimeFlag(true);
          rBucket.set(errorForm, 10, TimeUnit.MINUTES);
          throw new BusinessException(Errors.BUSI_PASSWORD_WRONG_TOO_MANY_ERROR);
        } else {
          rBucket.set(errorForm, 1, TimeUnit.DAYS);
          throw new BusinessException(Errors.BUSI_PASSWORD_WRONG_ERROR, tomsg);
        }

      }
    } else {
      UserLoginErrorForm errorForm = new UserLoginErrorForm();
      errorForm.setMobile(mobile);
      errorForm.setCount(1);
      rBucket.set(errorForm, 1, TimeUnit.DAYS);
      String msg = Errors.BUSI_PASSWORD_WRONG_ERROR.getLabel();
      String tomsg = String.format(msg, String.valueOf(4));
      throw new BusinessException(Errors.BUSI_PASSWORD_WRONG_ERROR, tomsg);
    }
  }

  public String addZeroForNum(String str, int strLength) {
    int strLen = str.length();
    if (strLen < strLength) {
      while (strLen < strLength) {
        StringBuffer sb = new StringBuffer();
        sb.append("0").append(str);// 左补0
        // sb.append(str).append("0");//右补0
        str = sb.toString();
        strLen = str.length();
      }
    }

    return str;
  }

  private UserResForm afterLoginReturnToken(Long id, String mobile, String chanelStyle) {
    User user = userMapper.selectByPrimaryKey(id);
    String uid = "";
    if (StringUtils.isBlank(mobile)) {
      uid = addZeroForNum(user.getSocialCreditCode(), 32);
    } else {
      if (StringUtil.isMoblie(mobile)) {
        uid = String.format("%032d", Long.parseLong(mobile));
      } else {
        uid = addZeroForNum(mobile, 32);// String.format("%032s", mobile);
      }
    }
    // 来源默认系统注册
    String token = TokenUtil.encodeToken((byte) 1, uid, "ged");
    redissonClient.getKeys()
        .deleteByPattern(Constant.CURR_USER_INFO_TOKEN + ":" + chanelStyle + ":" + uid + "*");
    redissonClient
        .getBucket(Constant.CURR_USER_INFO_TOKEN + ":" + chanelStyle + ":" + uid + ":" + token)
        .set(user, 7, TimeUnit.DAYS);
    UserResForm userResForm = new UserResForm();
    // 替换掉特殊字符
    userResForm.setToken(token.replaceAll("&|=|\\?", "A"));
    userResForm.setMobile(user.getMobile());
    userResForm.setUserName(user.getCompanyName());
    userResForm.setCompanyName(user.getCompanyName());
    userResForm.setUserRole(user.getUserRole());
    userResForm.setSocialCreditCode(user.getSocialCreditCode());
    userResForm.setUserType(user.getUserType());
    userResForm.setCheckAccountFlag(user.getCheckAccountFlag());
    userResForm.setCompanyAccountFlag(user.getCompanyAccountFlag());
    userResForm.setActiveFlag(user.getActiveFlag());

    GedOrder gedOrder = orderNewService.getNewOrderByUserId(user.getId());
    if(gedOrder == null)
      userResForm.setSysFlag(1);//自主进件
    else
      userResForm.setSysFlag(gedOrder.getSysFlag());
    userResForm.setStatus(user.getStatus());
    return userResForm;
  }

  @Override
  public List<UserLogin> getUserByMobile(String mobile) {
    UserLoginExample example = new UserLoginExample();
    example.createCriteria().andMobileEqualTo(mobile).andIsEnabledEqualTo(Constant.COMMONE_ONE);
    List<UserLogin> list = userLoginMapper.selectByExample(example);
    return list;
  }

  @Override
  public User userByMobile(String mobile) {
    UserExample example = new UserExample();
    example.createCriteria().andMobileEqualTo(mobile);
    List<User> list = userMapper.selectByExample(example);
    if (list != null && list.size() != 0) {
      return list.get(0);
    } else {
      return null;
    }
  }

  public List<UserLogin> getUserBySocialCreditCode(String socialCreditCode) {
    UserLoginExample example = new UserLoginExample();
    example.createCriteria().andSocialCreditCodeEqualTo(socialCreditCode)
        .andIsEnabledEqualTo(Constant.COMMONE_ONE);
    List<UserLogin> list = userLoginMapper.selectByExample(example);
    return list;
  }

  @Override
  public void SocialCreditCodeByUser(String socialCreditCode) {
    UserExample example = new UserExample();
    example.createCriteria().andSocialCreditCodeEqualTo(socialCreditCode);
    List<User> list = userMapper.selectByExample(example);
    if (list.size() == 0) {
      throw new BusinessException(Errors.USER_MOBILE_NOT_EXISTS_ERROR);
    }
  }

  @Override
  public UserResForm retrievePasswd(UserRetrieveThriftForm reqForm) {
    if (!reqForm.getNewPassword().equals(reqForm.getConfirmPassword())) {
      throw new BusinessException(Errors.SYSTEM_ERROR);
    }
    Boolean flag = msgService.checkCodeExists(reqForm.getMobile(), reqForm.getCode(),
        MsgTypeEnum.MSG_TYPE_FORGET.toString());
    UserLogin userLogin = new UserLogin();

    UserLoginExample example1 = new UserLoginExample();
    Errors errors;
    if (StringUtil.isMoblie(reqForm.getMobile())) {
      userLogin.setMobile(reqForm.getMobile());
      example1.createCriteria().andMobileEqualTo(reqForm.getMobile());
      errors = Errors.USER_MOBILE_NOT_EXISTS_ERROR;
    } else {
      userLogin.setSocialCreditCode(reqForm.getMobile());
      example1.createCriteria().andSocialCreditCodeEqualTo(reqForm.getMobile());
      errors = Errors.USER_COMPANY_NOT_EXISTS_ERROR;
    }
    List<UserLogin> lists = userLoginMapper.selectByExample(example1);
    if (lists.size() == 0) {
      throw new BusinessException(errors);
    }
    if (flag) {
      userLogin.setPassword(BCrypt.hashpw(reqForm.getNewPassword(), salt));
      UserLoginExample example = new UserLoginExample();
      example.createCriteria().andIsEnabledEqualTo(Constant.COMMONE_ONE)
          .andMobileEqualTo(reqForm.getMobile());
      userLoginMapper.updateByExampleSelective(userLogin, example);
      String mobile = reqForm.getMobile();
      UserExample exampleUser = new UserExample();
      exampleUser.createCriteria().andMobileEqualTo(mobile);
      List<User> list = userMapper.selectByExample(exampleUser);
      User user = list.get(0);
      String uid = String.format("%032d", Long.parseLong(mobile));
      // 来源默认系统注册
      String token = TokenUtil.encodeToken((byte) 1, uid, "ged");
      redissonClient.getKeys().deleteByPattern(Constant.CURR_USER_INFO_TOKEN + ":" + uid + "*");
      redissonClient.getBucket(Constant.CURR_USER_INFO_TOKEN + ":" + uid + ":" + token).set(user, 7,
          TimeUnit.DAYS);
      UserResForm userResForm = new UserResForm();
      // 替换掉特殊字符
      userResForm.setToken(token.replaceAll("&|=|\\?", "A"));
      userResForm.setMobile(mobile);
      userResForm.setUserRole(user.getUserRole());
      userResForm.setUserType(user.getUserType());
      userResForm.setCompanyName(user.getCompanyName());
      userResForm.setUserName(user.getCompanyName());
      return userResForm;
    } else {
      throw new BusinessException(Errors.USER_VALIDE_CODE_ERROR);
    }
  }

  @Override
  public Integer retrievePasswdPC(UserRetrieveForm reqForm) {

    // 1 校验密码与确认密码一致性
    if (!reqForm.getPwd().equals(reqForm.getPwdSure())) {
      throw new BusinessException(Errors.SYSTEM_ERROR);
    }

    List<UserLogin> users = this.getUserByMobile(reqForm.getMobile());
    if (users == null || users.size() == 0) {
      throw new BusinessException(Errors.USER_VALIDE_CODE_ERROR,
          "手机号:" + reqForm.getMobile() + "不是系统用户！");
    }

    // 校验短信验证码是否正确
    Boolean flag = msgService.checkCodeExists(reqForm.getMobile(), reqForm.getMobileCode(),
        MsgTypeEnum.MSG_TYPE_FORGET.toString());
    if (flag) {
      UserLogin userLogin = new UserLogin();
      userLogin.setMobile(reqForm.getMobile());
      userLogin.setPassword(BCrypt.hashpw(reqForm.getPwd(), salt));
      UserLoginExample example = new UserLoginExample();
      example.createCriteria().andIsEnabledEqualTo(Constant.COMMONE_ONE)
          .andMobileEqualTo(reqForm.getMobile());
      // 更新用户数据
      userLoginMapper.updateByExampleSelective(userLogin, example);
    } else {
      throw new BusinessException(Errors.USER_VALIDE_CODE_ERROR);
    }
    return null;
  }

  @Override
  public void updatePassword(UserPassForm reqForm, User user) {
    UserLogin userLogin = userLoginMapper.selectByPrimaryKey(user.getId());
    if (!BCrypt.hashpw(reqForm.getOldPassword(), salt).equals(userLogin.getPassword())) {
      throw new BusinessException(Errors.USER_OLD_PASSWORD_ERROR);
    }

    if (BCrypt.hashpw(reqForm.getNewPassword(), salt).equals(userLogin.getPassword())) {
      throw new BusinessException(Errors.USER_NEW_SAME_PASSWORD_ERROR);
    }
    userLogin.setPassword(BCrypt.hashpw(reqForm.getNewPassword(), salt));
    // 更新用户数据
    userLoginMapper.updateByPrimaryKeySelective(userLogin);
  }


  @Override
  public void bindMobile(UserBindForm reqForm, User user) {
    RBucket rBucket = redissonClient.getBucket(
        Constant.VALID_MSG + ":" + MsgType.BIND_MOBILE.toString() + ":" + reqForm.getMobile());
    if (!rBucket.isExists()) {
      throw new BusinessException(Errors.VALIDECODE_EXPIRE_ERROR);
    }
    MsgRedisEntity mr = (MsgRedisEntity) rBucket.get();
    String rcode = mr.getParam()[0];
    if (!rcode.equals(reqForm.getCode())) {
      throw new BusinessException(Errors.VALIDECODE_ERROR);
    }
    List<UserLogin> list = getUserByMobile(reqForm.getMobile());
    if (list.size() > 0) {
      throw new BusinessException(Errors.USER_COMPANY_MOBILE_EXISTS_ERROR);
    }
    User userQuery = userMapper.selectByPrimaryKey(user.getId());
    if (StringUtils.isNotBlank(userQuery.getMobile())) {
      throw new BusinessException(Errors.USER_BIND_MOBILE_ERROR);
    }
    user.setMobile(reqForm.getMobile());
    userMapper.updateByPrimaryKeySelective(user);
    UserLogin userLogin = new UserLogin();
    userLogin.setMobile(reqForm.getMobile());
    userLogin.setId(user.getId());
    userLoginMapper.updateByPrimaryKeySelective(userLogin);
  }

  @Override
  public void queryUserByMobile(String mobile) {
    UserExample example = new UserExample();
    example.createCriteria().andMobileEqualTo(mobile);
    List<User> list = userMapper.selectByExample(example);
    if (list.size() == 0) {
      throw new BusinessException(Errors.USER_MOBILE_NOT_EXISTS_ERROR);
    }

  }

  @Override
  public User queryByMobile(String mobile) {
    UserExample example = new UserExample();
    example.createCriteria().andMobileEqualTo(mobile);
    List<User> list = userMapper.selectByExample(example);
    if (list != null && list.size() != 0) {
      return list.get(0);
    }
    return null;
  }

  @Override
  public void updateUser(User user) {
    userMapper.updateByPrimaryKeySelective(user);
  }

  @Override
  public List<User> getBySocialCreditCode(String code) {
    UserExample example = new UserExample();
    example.createCriteria().andSocialCreditCodeEqualTo(code);
    List<User> list = userMapper.selectByExample(example);
    if (list.size() == 0) {
      return null;
    }
    return list;
  }

  @Override
  public int insertLoginUser(UserLogin userLogin) {
    return userLoginMapper.insertSelective(userLogin);
  }

  @Override
  public int inserUser(User user) {
    return userMapper.insertSelective(user);
  }

  @Override
  public void perfactUserBaseInfo(User user) {
    userMapper.updateByPrimaryKeySelective(user);
  }

  @Override
  public void checkValidCode(String mobile, String code) {
    List<UserLogin> list = getUserByMobile(mobile);
    if (list.size() == 0) {
      throw new BusinessException(Errors.USER_MOBILE_NOT_EXISTS_ERROR);
    }
    Boolean flag = msgService.checkCodeExists(mobile, code, MsgTypeEnum.MSG_TYPE_FORGET.toString());
    if (!flag) {
      throw new BusinessException(Errors.USER_VALIDE_CODE_ERROR);
    }
  }

  @Override
  public UserCardResForm queryUserInfoById(Long userId) {
    User user = userMapper.selectByPrimaryKey(userId);
    UserCardResForm userCardResForm = new UserCardResForm();
    userCardResForm.setUserId(user.getId());
    userCardResForm.setBusinessLicence(user.getBusinessLicence());
    userCardResForm.setOrganizationCode(user.getOrganizationCode());
    userCardResForm.setSocialCreditCode(user.getSocialCreditCode());
    userCardResForm.setTaxCode(user.getTaxCode());
    return userCardResForm;
  }

  @Override
  public User getUserById(Long userId) {
    return userMapper.selectByPrimaryKey(userId);
  }

  @Override
  public UserResForm updateUserPwd(PwdReqForm pwdReqForm, User userToken, String chanelStyle) {
    UserLogin userLogin = userLoginMapper.selectByPrimaryKey(userToken.getId());
    userLogin.setId(userToken.getId());
    if (BCrypt.hashpw(pwdReqForm.getConfirmPassword(), salt).equals(userLogin.getPassword())) {
      throw new BusinessException(Errors.USER_NEW_SAME_PASSWORD_ERROR);
    }
    String inPasswd = BCrypt.hashpw(pwdReqForm.getPassword(), salt);
    userLogin.setPassword(inPasswd);
    userLoginMapper.updateByPrimaryKeySelective(userLogin);
    User user = new User();
    user.setActiveFlag(1);
    user.setId(userToken.getId());
    userMapper.updateByPrimaryKeySelective(user);
    return afterLoginReturnToken(userToken.getId(), userToken.getMobile(), chanelStyle);
  }

  @Override
  public void updateUserCustId(String custId, Long userId) {
    User user = new User();
    user.setGetCustId(custId);
    user.setId(userId);
    userMapper.updateByPrimaryKeySelective(user);
  }

  @Override
  public void updateCheckAccountFlag(Long userId, CorporationReqForm reqForm, Integer accountFlag) {
    User user = new User();
    user.setId(userId);
    if (reqForm != null) {
      // user.setCompanyName(reqForm.getCompanyName());
      user.setUsername(reqForm.getCorporationName());
      user.setIdCardNum(reqForm.getCorporationCardNum());
      user.setLegalCardNumber(reqForm.getCorporationCardNum());
      user.setSocialCreditCode(reqForm.getSocialCreditCode());
      if (accountFlag == 4) {
        user.setGetCustId(reqForm.getCustId());
      }
    }
    user.setCheckAccountFlag(accountFlag);
    user.setLegalCardType(1);
    user.setIdCardFlag(1);
    userMapper.updateByPrimaryKeySelective(user);
  }

  @Override
  public void updateCompanyAccountStatus(Long userId, String companyName, Integer status) {
    User record = new User();
    record.setId(userId);
    record.setCompanyName(companyName);
    record.setCompanyAccountFlag(status);
    record.setIdCardFlag(1);
    userMapper.updateByPrimaryKeySelective(record);
  }

  @Override
  public void updatePersonalAccountFlag(Long userId, PersonalAccount account, Integer accountFlag,Date date) {
    User user = new User();
    user.setId(userId);
    user.setCheckAccountFlag(accountFlag);
    user.setGetCustId(account.getCustId());
    user.setUsername(account.getCustName());
    user.setLegalCardNumber(account.getIdCardNo());
    user.setIdCardNum(account.getIdCardNo());
    user.setIdCardFlag(1);
    user.setModifyId(userId);
    user.setModifyTime(date);
    userMapper.updateByPrimaryKeySelective(user);
  }

  @Override
  public void updateActiveUserFlag(Long userId, ActivateUser activateUser, Integer activeFlag) {
    User user = new User();
    user.setId(userId);
    user.setCheckAccountFlag(activeFlag);
    user.setGetCustId(activateUser.getCustId());
    user.setLegalName(activateUser.getCustName());
    user.setLegalCardNumber(activateUser.getIdCardNo());
    user.setIdCardNum(activateUser.getIdCardNo());
    user.setMobile(activateUser.getMobile());
    user.setIdCardFlag(1);
    user.setActiveFlag(activeFlag);// 已激活
    userMapper.updateByPrimaryKeySelective(user);
  }


  @Override
  public void updateEnterpriseAccountFlag(Long userId, EnterpriseAccount account,
      Integer accountFlag,Date date) {
    User user = new User();
    user.setId(userId);
    user.setCompanyAccountFlag(accountFlag);
    user.setCompanyName(account.getEnterpriseName());//公司名称
    user.setGetCustId(account.getCustId());
    user.setUsername(account.getCustName());
    user.setLegalCardNumber(account.getIdCardNo());
    user.setIdCardNum(account.getIdCardNo());
    user.setIdCardFlag(1);
    user.setModifyId(userId);
    user.setModifyTime(date);
    userMapper.updateByPrimaryKeySelective(user);
  }

  @Override
  public UserTitleResForm queryUserTitle(Long userId) {
    User user = userMapper.selectByPrimaryKey(userId);
    UserTitleResForm resForm = new UserTitleResForm();
    resForm.setUserName(user.getUsername());

    resForm.setIsCompanyAccount(user.getCompanyAccountFlag());
    resForm.setIsRealName(user.getIdCardFlag());

    if(user.getCompanyAccountFlag() != null && user.getCompanyAccountFlag() != 0){
      resForm.setIsCompanyAccount(user.getCompanyAccountFlag());
      resForm.setCompanyName(user.getCompanyName());
    }else if(user.getCheckAccountFlag() != null && user.getCheckAccountFlag() != 0){
      resForm.setIsCompanyAccount(user.getCheckAccountFlag());
    }else {
      resForm.setIsCompanyAccount(0);
    }

    //resForm.setIsAccount(null);//该字段在2.0弃用
    return resForm;
  }

  @Override
  public User selectUserBytype(String companyTypeCard, String companyCardCode) {
    UserExample example = new UserExample();
    UserExample.Criteria criteria = example.createCriteria();
    criteria.andCompanyCardTypeEqualTo(companyTypeCard);
    criteria.andCompanyCardCodeEqualTo(companyCardCode);
    List<User> list = userMapper.selectByExample(example);
    if (list != null && list.size() != 0) {
      return list.get(0);
    }
    return null;
  }

  @Override
  public User getUserByCustId(String custId){
    UserExample example = new UserExample();
    example.createCriteria().andGetCustIdEqualTo(custId);
    List<User> userList = userMapper.selectByExample(example);
    User user = null;
    if(userList != null && userList.size() > 0){
      user = userList.get(0);
    }
    return user;
  }

}
