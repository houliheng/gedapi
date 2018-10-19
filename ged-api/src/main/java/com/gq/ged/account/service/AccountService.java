package com.gq.ged.account.service;

import com.gq.ged.account.controller.req.*;
import com.gq.ged.account.controller.res.AccountInitResForm;
import com.gq.ged.account.dao.model.Account;
import com.gq.ged.account.dao.model.AccountCompany;
import com.gq.ged.user.dao.model.User;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * Created by wyq_tomorrow on 2018/1/19.
 */
public interface AccountService {
  /**
   * 完善账户基本信息
   */
  Long callAccountBaseInfo(AccountBaseReqForm reqForm, Long id)
      throws InvocationTargetException, IllegalAccessException;

  /**
   * 完善账户信息
   */
  Long callAccountInfo(AccountBankReqForm reqForm, Long id)
      throws InvocationTargetException, IllegalAccessException;

  /**
   * 补充账户扩展信息
   */
  void callAccountExtPicInfo(AccountExtListForm list, Long id) throws Exception;

  /**
   * 查询账户信息
   * 
   * @param id
   */
  AccountCompany queryCompanyAccountInfoById(Long id);

  /**
   * 查询开户状态
   * 
   * @param userId
   * @return
   */
  String selectAccStatus(long userId);

  /**
   * 调用借款系统账户信息
   * 
   * @param userId
   * @return
   */
  String callLoanSysAccountInfo(Long userId) throws Exception;

  /**
   * 根据用户id获取账户信息
   * 
   * @param userId
   * @return
   */
  List<Account> getAccountInfo(Long userId);

  /**
   * 根据身份证查询账户信息
   * 
   * @param idcardNo
   * @return
   */
  Account getAccountByIdCardNo(String idcardNo, Long userId);

  /**
   * 根据用户id获取账户
   * 
   * @param userId
   * @return
   */
  Account getAccountByUserId(Long userId);

  /**
   * 企业开户
   * 
   * @param reqForm
   */
  void createCorporationAccount(CorporationReqForm reqForm, User user) throws Exception;

  /**
   * copy个人信息
   * 
   * @param reqForm
   * @param user
   */
  void createAccountByCopyInfo(CorporationReqForm reqForm, User user) throws Exception;

  /**
   * 初始化账户信息
   * 
   * @param userId
   * @return
   */
  AccountInitResForm initAccountInfo(Long userId);

  /**
   * 获得用户开户标识
   */
  AccountFlagResForm queryUserAccountFlag(Long userId);

  /**
   * 更新账户状态
   *
   * @param account
   */
  Integer updateAccountStatus(Account account);

  /**
   * 根据手机号查询个人账户信息
   * 
   * @param mobile
   * @return
   */
  Account selectAccountByMobile(String mobile);

  /**
   * 根据银行卡号查询账户信息
   * 
   * @param accountCardNo
   * @return
   */
  Account selectAccountByCode(String accountCardNo);

  /**
   * 根据社会统一代码更新
   * 
   * @param socialCreditCode
   * @return
   */
  Account selectAccountBySocialCreditCode(String socialCreditCode);

  /**
   * 更新个人账户信息
   * 
   * @param account
   * @return
   */
  Integer updateAccount(Account account);

  /**
   * 插入账户信息
   * 
   * @param account
   */
  int insertAccount(Account account);
}
