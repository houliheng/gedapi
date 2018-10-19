package com.gq.ged.user.service;

import com.gq.ged.account.controller.req.CorporationReqForm;
import com.gq.ged.account.controller.res.v2.ActivateUser;
import com.gq.ged.account.controller.res.v2.EnterpriseAccount;
import com.gq.ged.account.controller.res.v2.PersonalAccount;
import com.gq.ged.user.api.req.PwdReqForm;
import com.gq.ged.user.api.req.UserQueryReqForm;
import com.gq.ged.user.api.req.UserRegisterReqForm;
import com.gq.ged.user.api.res.*;
import com.gq.ged.user.dao.model.User;
import com.gq.ged.user.dao.model.UserLogin;
import com.gq.ged.user.pc.req.UserBindForm;
import com.gq.ged.user.pc.req.UserPassForm;
import com.gq.ged.user.pc.req.UserRetrieveForm;
import com.gq.ged.user.tmodel.UserRegisterThriftForm;
import com.gq.ged.user.tmodel.UserRetrieveThriftForm;

import java.util.Date;
import java.util.List;

/**
 * Created by wyq_tomorrow on 2017/12/1.
 */
public interface UserService {
  /**
   * 用户注册
   *
   * @param registerForm
   */
  UserResForm registerUser(UserRegisterThriftForm registerForm);

  /**
   * 查询用户是否注册
   * 
   * @param reqForm
   */
  UserQueryResForm queryUserByLoanSys(UserQueryReqForm reqForm);

  /**
   * 通过借款系统注册用户
   * 
   * @param reqForm
   */
  void registerUserByLoanSys(UserRegisterReqForm reqForm) throws Exception;

  /**
   * 用户登录
   *
   * @param mobile
   * @param pwd
   * @return
   */
  UserResForm loginUser(String mobile, String pwd, String chanelStyle);

  /**
   * 根据手机号查询验证码
   *
   * @param mobile
   * @return
   */
  List<UserLogin> getUserByMobile(String mobile);

  /**
   * 根据手机号查询用户
   *
   * @param mobile
   * @return
   */
 User userByMobile(String mobile);

  /**
   * 忘记密码
   * 
   * @param reqForm
   */
  UserResForm retrievePasswd(UserRetrieveThriftForm reqForm);

  /**
   * pc端找回密码
   * 
   * @param reqForm
   * @return
   */
  Integer retrievePasswdPC(UserRetrieveForm reqForm);

  /**
   * 修改密码
   * 
   * @param reqForm
   */
  void updatePassword(UserPassForm reqForm, User user);

  /**
   * 绑定用户手机号
   * 
   * @param reqForm
   * @param user
   */
  void bindMobile(UserBindForm reqForm, User user);

  /**
   * 完善用户基本信息
   * 
   * @param user
   */
  void perfactUserBaseInfo(User user);

  /**
   * 根据手机号查询用户
   * 
   * @param mobile
   */
  void queryUserByMobile(String mobile);

  /**
   * 根据信用代码查询用户
   *
   * @param socialCreditCode
   */
  void SocialCreditCodeByUser(String socialCreditCode);

  /**
   * 检查验证码是否正确
   * 
   * @param mobile
   * @param code
   */
  void checkValidCode(String mobile, String code);

  /**
   * 查询用户信息
   * 
   * @return
   */
  UserCardResForm queryUserInfoById(Long userId);

  /**
   * 获得用户信息
   * 
   * @param userId
   * @return
   */
  User getUserById(Long userId);

  /**
   * 更新用户密码
   * 
   * @param pwdReqForm
   * @param user
   */
  UserResForm updateUserPwd(PwdReqForm pwdReqForm, User user, String chanelStyle);

  /**
   * 更新用户客户id
   * 
   * @param custId
   * @param userId
   */
  void updateUserCustId(String custId, Long userId);

  /**
   * 查询用户title
   *
   * @param userId
   * @return
   */
  UserTitleResForm queryUserTitle(Long userId);

  /**
   * 更新用户状态标志
   *
   * @param accountFlag
   */
  void updateCheckAccountFlag(Long userId, CorporationReqForm reqForm, Integer accountFlag);

  /**
   * 更新企业账户状态
   * 
   * @param userId
   * @param status
   */
  void updateCompanyAccountStatus(Long userId, String companyName, Integer status);

  /**
   * 根据企业证件类型 查询用户
   * 
   * @param companyTypeCard
   * @return
   */
  User selectUserBytype(String companyTypeCard, String companyCardCode);

  /**
   * 更新个人开户用户信息
   * 
   * @param userId
   * @param account
   * @param accountFlag
   * @param date
   */
  public void updatePersonalAccountFlag(Long userId, PersonalAccount account, Integer accountFlag,Date date);

  /**
   * 更新企业开户用户信息
   * 
   * @param userId
   * @param account
   * @param accountFlag
   * @param date
   */
  public void updateEnterpriseAccountFlag(Long userId, EnterpriseAccount account,
      Integer accountFlag,Date date);

  /**
   * 更新用户激活标识
   * 
   * @param userId
   * @param activateUser
   * @param activeFlag
   */
  public void updateActiveUserFlag(Long userId, ActivateUser activateUser, Integer activeFlag);

  /**
   * 根据手机号查询用户信息
   * @param mobile
   * @return
   */
  User queryByMobile(String mobile);

  /**
   * 更新用户表信息
   * @param user
   */
  public void updateUser(User user);

  public List<User> getBySocialCreditCode(String code);

  public int insertLoginUser(UserLogin userLogin);
  public int inserUser(User user);


    /**
     * 根据客户id查询用户信息
     * @param custId
     * @return
     */
  public User getUserByCustId(String custId);

}
