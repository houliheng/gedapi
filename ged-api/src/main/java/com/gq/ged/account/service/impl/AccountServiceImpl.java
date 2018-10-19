package com.gq.ged.account.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gq.ged.account.controller.req.*;
import com.gq.ged.account.controller.res.AccountInitResForm;
import com.gq.ged.account.dao.mapper.AccountCompanyMapper;
import com.gq.ged.account.dao.mapper.AccountMapper;
import com.gq.ged.account.dao.model.Account;
import com.gq.ged.account.dao.model.AccountCompany;
import com.gq.ged.account.dao.model.AccountCompanyExample;
import com.gq.ged.account.dao.model.AccountExample;
import com.gq.ged.account.service.AccountService;
import com.gq.ged.account.service.SignService;
import com.gq.ged.activemq.service.JmsProvider;
import com.gq.ged.common.Constant;
import com.gq.ged.common.Errors;
import com.gq.ged.common.exception.business.BusinessException;
import com.gq.ged.common.http.HttpUtils;
import com.gq.ged.common.utils.date.DateFormatUtils;
import com.gq.ged.dictionary.dao.model.FuiouArea;
import com.gq.ged.dictionary.dao.model.SystemDictionaryItem;
import com.gq.ged.dictionary.service.DictionaryService;
import com.gq.ged.dictionary.service.FuyouAreaService;
import com.gq.ged.file.service.SystemFileService;
import com.gq.ged.user.dao.mapper.UserMapper;
import com.gq.ged.user.dao.model.User;
import com.gq.ged.user.service.UserService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by wyq_tomorrow on 2018/1/19.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AccountServiceImpl implements AccountService {

  static Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

  @Resource
  AccountMapper accountMapper;

  @Resource
  AccountCompanyMapper companyMapper;

  @Resource
  UserService userService;

  @Resource
  SystemFileService systemFileService;

  @Resource
  FuyouAreaService fuyouAreaService;

  @Resource
  DictionaryService dictionaryService;

  @Resource
  SignService signService;

  @Value("${gq.borrow.url}")
  String borrowUrl;


  @Value("${gq.ged.call.get.createCust.url}")
  String gedCreateCustIdUrl;

  @Value("${gq.ged.call.capital.url}")
  String capitalUrl;

  @Value("${gq.ged.call.capital.account.tradetype.value}")
  String tradeType;

  @Value("${gq.ged.get.mchn}")
  String mchn;

  @Resource
  JmsProvider jmsProvider;

  @Resource
  UserMapper userMapper;

  @Override
  public Long callAccountBaseInfo(AccountBaseReqForm reqForm, Long id)
      throws InvocationTargetException, IllegalAccessException {
    // User user = new User();
    // BeanUtils.copyProperties(user, reqForm);
    // user.setId(id);
    // userService.perfactUserBaseInfo(user);
    AccountCompany company = getAccountCompayInfo(id);
    if (StringUtils.isNotBlank(reqForm.getId()) || company != null) {
      Long accountId = company.getId();
      BeanUtils.copyProperties(company, reqForm);
      company.setUserId(id);
      company.setId(accountId);
      companyMapper.updateByPrimaryKeySelective(company);
    } else {
      company = new AccountCompany();
      BeanUtils.copyProperties(company, reqForm);
      company.setUserId(id);
      companyMapper.insertSelective(company);
    }
    return company.getId();
  }

  @Override
  public Long callAccountInfo(AccountBankReqForm reqForm, Long id)
      throws InvocationTargetException, IllegalAccessException {
    AccountCompany company = new AccountCompany();
    BeanUtils.copyProperties(company, reqForm);
    company.setUserId(id);
    company.setCreateId(id);
    company.setCreateTime(new Date());
    companyMapper.updateByPrimaryKeySelective(company);
    return company.getId();
  }

  @Override
  public void callAccountExtPicInfo(AccountExtListForm form, Long id) throws Exception {
    AccountCompany company = new AccountCompany();
    company.setId(form.getId());
    company.setIdCardFaceUrl(form.getIdCardFaceUrl());
    company.setIdCardBackUrl(form.getIdCardBackUrl());
    company.setIdCardHoldUrl(form.getIdCardHoldUrl());
    company.setBusinessLicenceUrl(form.getBusinessLicenceUrl());
    company.setAccountsPermitsUrl(form.getAccountsPermitsUrl());
    company.setStatus(1);
    companyMapper.updateByPrimaryKeySelective(company);
    AccountCompany accountCompany = companyMapper.selectByPrimaryKey(form.getId());
    JSONObject jsonObject = HttpUtils.doPost(borrowUrl + "/api/companyAccount",
        JSONObject.toJSONString(accountCompany, SerializerFeature.WriteMapNullValue));
    userService.updateCompanyAccountStatus(id, accountCompany.getCompanyName(), 1);
    // JSONObject jsonObject = HttpUtils.doPost(loanCreateAccountUrl, JSONObject.toJSONString(map));
  }

  @Override
  public AccountCompany queryCompanyAccountInfoById(Long userId) {
    AccountCompanyExample example = new AccountCompanyExample();
    example.createCriteria().andUserIdEqualTo(userId);
    List<AccountCompany> list = companyMapper.selectByExample(example);
    if (list.size() > 0) {
      return list.get(0);
    } else {
      return null;
    }
  }

  @Override
  public String selectAccStatus(long userId) {
    String status = "";
    AccountExample example = new AccountExample();
    example.setOrderByClause("create_time desc");
    AccountExample.Criteria criteria = example.createCriteria();
    criteria.andUserIdEqualTo(userId);
    List<Account> list = accountMapper.selectByExample(example);
    if (list != null && list.size() != 0) {
      Account account = list.get(0);
      status = account.getStatus().toString();
    }
    return status;
  }

  @Override
  public String callLoanSysAccountInfo(Long userId) throws Exception {
    Account account = accountMapper.selectByPrimaryKey(userId);
    JSONObject object = HttpUtils.doPost(borrowUrl + "/api/gedAccount", JSON.toJSONString(account));
    logger.info("call result:" + JSON.toJSONString(object));
    return "";
  }

  public AccountCompany getAccountCompayInfo(Long userId) {
    AccountCompanyExample example = new AccountCompanyExample();
    example.createCriteria().andUserIdEqualTo(userId);
    List<AccountCompany> list = companyMapper.selectByExample(example);
    if (list.size() > 0) {
      return list.get(0);
    } else {
      return null;
    }
  }

  @Override
  public List<Account> getAccountInfo(Long userId) {
    AccountExample example = new AccountExample();
    example.createCriteria().andUserIdEqualTo(userId);
    List<Account> list = accountMapper.selectByExample(example);
    return list;
  }

  @Override
  public Account getAccountByIdCardNo(String idcardNo, Long userId) {
    AccountExample example = new AccountExample();
    example.createCriteria().andCorporationCardNumEqualTo(idcardNo);
    User user = userService.getUserById(userId);
    List<Account> list = accountMapper.selectByExample(example);
    if (list.size() > 0) {
      Account account = list.get(0);
      if (StringUtils.isBlank(account.getProvinceCode())) {
        if (StringUtils.isBlank(account.getCityCode())) {
          throw new BusinessException(Errors.SYSTEM_ERROR);
        }
        FuiouArea area1 = fuyouAreaService.selectFuiouAreaByCode(account.getCityCode());
        if (area1 != null) {
          account.setProvinceCode(area1.getParentId().toString());
        }

      }
      account.setSocialCreditCode(user.getSocialCreditCode());
      return account;
    }
    return null;
  }

  @Override
  public Account getAccountByUserId(Long userId) {
    List<Account> datas = this.getAccountInfo(userId);
    return datas.size() == 0 ? null : datas.get(0);
  }

  @Override
  public void createCorporationAccount(CorporationReqForm reqForm, User userRedis)
      throws Exception {
    User user = userService.getUserById(userRedis.getId());
    // 先判断账户表是否存在信息
    AccountExample example1 = new AccountExample();
    example1.createCriteria().andCompanyAccountEqualTo(reqForm.getBankNumber());
    List<Account> list1 = accountMapper.selectByExample(example1);
    if (list1.size() > 0) {
      Account account = list1.get(0);
      if (account.getUserId() != null) {
        throw new BusinessException(Errors.OPEN_ACCOUNT_ERROR);
      }
      account.setUserId(user.getId());
      account.setSocialCreditCode(user.getSocialCreditCode());
      account.setCompanyBankBranchName(reqForm.getCompanyBankBranchName());
      accountMapper.updateByPrimaryKey(account);
      reqForm.setCustId(account.getCustId());
      userService.updateCheckAccountFlag(user.getId(), reqForm, account.getStatus());
      if (account.getStatus() == 4) {
        signService.callLoanPlatformCreateAccount(account, user.getSocialCreditCode());
      }
      return;
    }
    AccountExample example = new AccountExample();
    example.createCriteria().andUserIdEqualTo(user.getId());
    List<Account> list = accountMapper.selectByExample(example);
    if (list.size() > 0) {
      throw new BusinessException(Errors.OPEN_ACCOUNT_ERROR);
    }
    String seqNo = DateFormatUtils.formatDate(new Date(), "yyyyMMddHHmmssSSS");
    Account account = new Account();
    account.setAccountCode(seqNo);
    account.setUserId(user.getId());
    account.setSocialCreditCode(user.getSocialCreditCode());
    account.setCompanyName(reqForm.getCompanyName());
    account.setCorporationName(reqForm.getCorporationName());
    account.setCorporationCardNum(reqForm.getCorporationCardNum());
    account.setCorporationPhone(user.getMobile());
    account.setCompanyBankBranchName(reqForm.getCompanyBankBranchName());
    account.setProvinceCode(reqForm.getProvinceId());
    account.setCityCode(reqForm.getCityId());
    account.setAreaCode(reqForm.getAreaId());
    account.setCompanyBankOfDeposit(reqForm.getBankOfDeposit());
    SystemDictionaryItem dictionaryItem =
        dictionaryService.getDictionaryByParam("BANK_TYPE", reqForm.getBankOfDeposit());
    account.setCompanyBankOfDepositValue(dictionaryItem.getValue());
    account.setCompanyAccount(reqForm.getBankNumber());
    account.setStatus(1);
    account.setCreateId(user.getId());
    account.setCreateTime(new Date());
    accountMapper.insertSelective(account);

    String custId = "";
    User users = userService.getUserById(user.getId());
    if (StringUtils.isBlank(users.getGetCustId())) {
      JSONObject getCustJson = callGetPlatformCreateCusId(reqForm, user.getMobile());
      if (!getCustJson.getBoolean("isSuccess")) {
        String msg = getCustJson.getString("msg");
        throw new BusinessException(Errors.USER_CREATE_ACCOUNT_ERROR, msg);
      }
      String gedJson = getCustJson.getString("data");
      logger.info("个人开户========" + gedJson);
      custId = JSONObject.parseObject(gedJson).getString("custId");
      logger.info("custId====" + custId);
      Map<String, Object> map = new HashedMap();
      map.put("userId", user.getId());
      map.put("custId", custId);
      map.put("username", reqForm.getCorporationName());
      reqForm.setCustId(custId);
      jmsProvider.sendMessage(Constant.MQ_GED_USER_UPDATE_CUSTID, JSONObject.toJSONString(map));
    } else {
      custId = users.getGetCustId();
    }
    // 更新用户信息
    // userService.updateUserCustId(custId, user.getId());
    JSONObject capitalAccountJson =
        callCapitalPlatformCreateAccount(reqForm, user.getMobile(), seqNo, custId);
    if (!Constant.RES_CODE_SUCCESS.equals(capitalAccountJson.getString("resp_code"))) {
      String msg = capitalAccountJson.getString("resp_msg");
      throw new BusinessException(Errors.USER_CREATE_ACCOUNT_ERROR, msg);
    }
    Account accountUpdate = new Account();
    accountUpdate.setId(account.getId());
    accountUpdate.setStatus(2);
    accountMapper.updateByPrimaryKeySelective(accountUpdate);

    userService.updateCheckAccountFlag(user.getId(), reqForm, 2);
    // callLoanPlatformCreateAccount(reqForm, user, custId, 2);
  }

  @Override
  public void createAccountByCopyInfo(CorporationReqForm reqForm, User user) throws Exception {
    Account account = getAccountByIdCardNo(reqForm.getCorporationCardNum(), user.getId());
    user = userService.getUserById(user.getId());
    if (account == null) {
      throw new BusinessException(Errors.ACCOUNT_NOT_EXISTS_ERROR);
    }
    Account copyAccount = new Account();
    if (account.getUserId() != null) {
      // 复制
      org.springframework.beans.BeanUtils.copyProperties(account, copyAccount);
      copyAccount.setId(null);
      copyAccount.setUserId(user.getId());
      copyAccount.setSocialCreditCode(user.getSocialCreditCode());
      accountMapper.insertSelective(copyAccount);
    } else {
      // 更新
      org.springframework.beans.BeanUtils.copyProperties(account, copyAccount);
      account.setUserId(user.getId());
      accountMapper.updateByPrimaryKey(account);
    }
    reqForm.setCustId(account.getCustId());
    userService.updateCheckAccountFlag(user.getId(), reqForm, 4);
    signService.callLoanPlatformCreateAccount(copyAccount, user.getSocialCreditCode());
  }

  /**
   * 调用冠E通获取客户ID
   *
   * @param reqForm
   * @return
   * @throws Exception
   */
  private JSONObject callGetPlatformCreateCusId(CorporationReqForm reqForm, String mobile)
      throws Exception {
    Map<String, String> map = new HashedMap();
    map.put("cretNO", reqForm.getCorporationCardNum());
    map.put("certType", "1");
    map.put("mobilePhone", mobile);
    map.put("name", reqForm.getCorporationName());
    map.put("bankNo", reqForm.getBankNumber());
    map.put("cityCode", reqForm.getCityId());// reqForm.getCityId()
    map.put("parentBankCode", reqForm.getProvinceId());// reqForm.getProvinceId()
    map.put("busiType", "1");
    String jsonObject = HttpUtils.doPostForm(gedCreateCustIdUrl, map);
    return JSONObject.parseObject(jsonObject);
  }

  private JSONObject callCapitalPlatformCreateAccount(CorporationReqForm reqForm, String mobile,
      String seqNo, String custId) throws Exception {
    // 开户调用
    Map<String, String> map = new HashedMap();
    /*
     * map.put("city_id", reqForm.getCityId()); map.put("seq_no", seqNo); map.put("name",
     * reqForm.getCorporationName()); map.put("cert_no", reqForm.getCorporationCardNum());
     * map.put("company_name", reqForm.getCompanyName()); map.put("mobile", reqForm.getMobile());
     * map.put("signature", ""); map.put("cust_no", custId); map.put("bank_id",
     * reqForm.getBankOfDeposit()); map.put("bank_card", reqForm.getBankNumber());
     * map.put("trade_type", tradeType); map.put("mchn", mchn);
     */

    map.put("city_id", reqForm.getCityId());
    map.put("seq_no", seqNo);
    map.put("name", reqForm.getCorporationName());
    map.put("cert_no", reqForm.getCorporationCardNum());
    map.put("mobile", mobile);
    map.put("signature", "");
    map.put("cust_no", custId);
    map.put("bank_id", reqForm.getBankOfDeposit());
    map.put("bank_card", reqForm.getBankNumber());
    map.put("trade_type", reqForm.getTradeType());
    map.put("mchn", mchn);
    // String jsonObject = HttpUtils.doPostForm(capitalAccountUrl, map);
    String jsonObject = HttpUtils.doPostForm(capitalUrl + "/api/createAccount", map);
    logger.info(jsonObject.toString());
    return JSONObject.parseObject(jsonObject);
  }

  /*
   * private JSONObject callLoanPlatformCreateAccount(CorporationReqForm reqForm, User user, String
   * custId, int status) throws Exception { user = userService.getUserById(user.getId());
   * Map<String, Object> map = new HashedMap(); SystemDictionaryItem dictionaryItem =
   * dictionaryService.getDictionaryByParam("BANK_TYPE", reqForm.getBankOfDeposit());
   * map.put("userId", user.getId()); map.put("custId", custId); map.put("companyBankOfDeposit",
   * dictionaryItem.getValue()); map.put("companyAccount", reqForm.getBankNumber());
   * map.put("companyBankBranchName", reqForm.getCompanyBankBranchName()); map.put("provinceName",
   * reqForm.getProvinceId()); map.put("cityName", reqForm.getCityId()); map.put("comIdNum",
   * reqForm.getSocialCreditCode()); map.put("legalPerNum", reqForm.getCorporationCardNum());
   * map.put("legalPerName", reqForm.getCorporationName()); map.put("companyName",
   * reqForm.getCompanyName()); map.put("legalPerPhone", user.getMobile()); map.put("comIdNum",
   * user.getSocialCreditCode()); map.put("companyName", ""); map.put("status", status); JSONObject
   * jsonObject = HttpUtils.doPost(loanCreateAccountUrl, JSONObject.toJSONString(map)); return
   * jsonObject; }
   */

  @Override
  public AccountInitResForm initAccountInfo(Long userId) {
    User user = userService.getUserById(userId);
    AccountInitResForm resForm = new AccountInitResForm();
    resForm.setCompanyName(user.getCompanyName());
    resForm.setCorporationCardNum(user.getLegalCardNumber());
    resForm.setCorporationName(user.getLegalName());
    resForm.setSocialCreditCode(user.getSocialCreditCode());
    return resForm;
  }

  @Override
  public AccountFlagResForm queryUserAccountFlag(Long userId) {
    AccountExample example1 = new AccountExample();
    example1.createCriteria().andUserIdEqualTo(userId);
    List<Account> list1 = accountMapper.selectByExample(example1);
    AccountCompanyExample example2 = new AccountCompanyExample();
    example2.createCriteria().andUserIdEqualTo(userId);
    List<AccountCompany> list2 = companyMapper.selectByExample(example2);
    AccountFlagResForm resForm = new AccountFlagResForm();
    User user = userService.getUserById(userId);
    if (user != null) {
      if (StringUtils.isBlank(user.getMobile())) {
        resForm.setMobileFlag(1);
      }
    }
    if (list1 != null && list1.size() > 0) {
      Account account = list1.get(0);
      if (account != null && account.getStatus() == 4) { // 已开户
        resForm.setPersonalFlag(1);
        resForm.setAccount(account);
        resForm.setStatus(account.getStatus());
      } else {
        resForm.setPersonalFlag(0);
        resForm.setAccount(account);
      }
    } else {
      Account account1 = new Account();
      account1.setStatus(0);
      resForm.setPersonalFlag(0);
      resForm.setAccount(account1);
    }
    if (list2 != null && list2.size() > 0) {
      AccountCompany accountCompany = list2.get(0);
      if (accountCompany != null && StringUtils.isNotBlank(accountCompany.getAccountsPermitsUrl())
          && StringUtils.isNotBlank(accountCompany.getBusinessLicenceUrl())
          && StringUtils.isNotBlank(accountCompany.getIdCardBackUrl())
          && StringUtils.isNotBlank(accountCompany.getIdCardFaceUrl())
          && StringUtils.isNotBlank(accountCompany.getIdCardHoldUrl())) {
        resForm.setBusinessFlag(1);// 已开户
        resForm.setAccountCompany(accountCompany);
      } else {
        resForm.setBusinessFlag(0);
        resForm.setAccountCompany(accountCompany);
      }

    }
    return resForm;
  }

  @Override
  public Integer updateAccountStatus(Account account) {
    return accountMapper.updateByPrimaryKey(account);
  }

  @Override
  public Account selectAccountByMobile(String mobile) {
    AccountExample example = new AccountExample();
    example.createCriteria().andCorporationPhoneEqualTo(mobile);
    List<Account> list = accountMapper.selectByExample(example);
    Account account = null;
    if (list != null && list.size() > 0) {
      account = list.get(0);
    }
    return account;
  }

  @Override
  public Account selectAccountByCode(String accountCardNo) {
    AccountExample example = new AccountExample();
    example.createCriteria().andCompanyAccountEqualTo(accountCardNo);
    List<Account> list = accountMapper.selectByExample(example);
    Account account = null;
    if (list != null && list.size() > 0) {
      account = list.get(0);
    }
    return account;
  }

  @Override
  public Account selectAccountBySocialCreditCode(String socialCreditCode) {
    AccountExample example = new AccountExample();
    example.createCriteria().andSocialCreditCodeEqualTo(socialCreditCode);
    List<Account> list = accountMapper.selectByExample(example);
    Account account = null;
    if (list != null && list.size() > 0) {
      account = list.get(0);
    }
    return account;
  }

  @Override
  public Integer updateAccount(Account account) {
    return accountMapper.updateByPrimaryKeySelective(account);
  }

  @Override
  public int insertAccount(Account account) {
    return accountMapper.insertSelective(account);
  }
}
