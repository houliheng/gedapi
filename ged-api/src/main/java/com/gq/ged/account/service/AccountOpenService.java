package com.gq.ged.account.service;


import com.alibaba.fastjson.JSONObject;
import com.gq.ged.account.controller.req.v2.*;
import com.gq.ged.account.controller.res.v2.*;
import com.gq.ged.account.dao.model.Account;
import com.gq.ged.account.dao.model.AccountCompany;
import com.gq.ged.order.controller.res.OrderDetailResForm;
import com.gq.ged.order.controller.res.WithDrawResForm;
import com.gq.ged.order.dao.model.GedRepaymentRecord;
import com.gq.ged.order.pc.res.PushToBorrowRepaymentResForm;
import com.gq.ged.user.dao.model.User;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaozb on 2018/3/20.
 */
public interface AccountOpenService {

  /**
   * 根据统一社会信用代码查询企业开户信息
   * @param socialCreditCode
   * @return
   */
  public AccountCompany queryCompanyAccountBySocialCreditCode(String socialCreditCode);

  /**
   * 根据用户ID查询企业开户信息
   *
   * @param id
   */
  AccountCompany queryCompanyAccountByUserId(Long id);

  /**
   * 根据custId查询企业开户信息
   * @param custId
   * @return
   */
  AccountCompany queryCompanyAccountByCustId(String custId);

  /**
   * 根据用户id获取账户信息
   *
   * @param userId
   * @return
   */
  List<Account> getAccountInfo(Long userId);

  /**
   * 查询账户余额
   * @param userId
   * @return
   * @throws Exception
   */
  public BigDecimal queryAccountBalance(Long userId) throws Exception;


  /**
   * 个人开户-v2.0(APP端)
   * @param user
   * @throws Exception
   */
  public PageResForm toPersonalPageAPP(User user) throws Exception;

  /**
   * 个人开户-v2.0(PC端)
   * @param user
   * @throws Exception
   */
  public PageResForm toPersonalPagePC(User user) throws Exception;


  /**
   * 企业开户-v2.0(APP端)
   * @param user
   * @throws Exception
   */
  public PageResForm toEnterprisePageAPP(User user) throws Exception;

  /**
   * 企业开户-v2.0(PC端)
   * @param user
   * @throws Exception
   */
  public PageResForm toEnterprisePagePC(User user) throws Exception;

  /**
   * 企业修改上传资料（页面）-v2.0(PC端)
   * @param user
   * @throws Exception
   */
  public PageResForm toModifyEnterprisePC(User user) throws Exception;

  /**
   * 企业修改上传资料（页面）-v2.0(APP端)
   * @param user
   * @return
   * @throws Exception
   */
  public PageResForm toModifyEnterpriseAPP(User user) throws Exception;


  /**
   * 个人开户回调
   * @param resForm
   * @return
   * @throws Exception
   */
  public  Map<String,Object> personAccountCallBack(CapitalResForm resForm) throws Exception;



  /**
   * 企业开户回调
   * @param reqForm
   * @throws Exception
   */
  public Map<String,Object>  enterpriseAccountCallBack(CapitalResForm reqForm) throws Exception;

  /**
   * 推送企业开户信息到借款系统
    * @param accountCompany
   * @return
   * @throws Exception
   */
  public JSONObject pushEnterpriseAccounToLoan(AccountCompany accountCompany);


  /**
   * 推送企业开户信息到冠e通
   * @param resForm
   * @return
   * @throws Exception
   */
  public String pushEnterpriseAccountToGqget(CapitalResForm resForm) throws Exception;

  /**
   * 网银充值
   * @param reqForm
   * @param user
   */
  public RepayPageResForm repaymentRecharge(RepaymentReqForm reqForm, User user) throws Exception;


    /**
   * 提现
   *
   * @param userInfo
   * @param orderNo
   * @return
   * @throws Exception
   */
  public WithDrawResForm withDraw(User userInfo, String orderNo) throws Exception;

  /**
   * 网页提现
   * @param orderNo
   * @param user
   */
  public WithDrawPageResForm toWithdrawPage(String orderNo, User user) throws Exception;


  /**
   * 提现-APP
   * @param orderNo
   * @param user
   * @return
   * @throws Exception
   */
  WithDrawPageResForm toWithdrawPageAPP(String orderNo, User user) throws Exception;

    /**
   * 网页提现回调
   * @param reqForm
   */
  Map<String, Object> withdrawPageCallBack(CapitalResForm reqForm) throws Exception;

  /**
   * 网银充值回调
   * @param reqForm
   */
  public  Map<String,Object>  repaymentRechargeCallBack(CapitalResForm reqForm) throws Exception;

  /**
   * 把还款充值信息推送给借款系统
   * @param record 充值记录
   * @return
   * @throws Exception
   */
  //PushToBorrowRepaymentResForm pushToBorrowRepayment(GedRepaymentRecord record) throws Exception;

  /**
   * 用户激活
   * @param user
   * @throws Exception
   */
  public PageResForm activateUser(User user) throws Exception;

  /**
   * 用户激活回调
   * @param reqForm
   */
  public Map<String,Object> activateUserCallBack(CapitalResForm reqForm) throws Exception;

  /**
   * 跳到缴费充值页面
   * @param chargeReqForm
   * @return
   */
  public RechargeFeePageResForm toChargePage(ChargeReqForm chargeReqForm,User user) throws Exception;

  /**
   * 费用收取回调
   * @param reqForm
   * @throws Exception
   */
  public Map<String, Object> chargesCallBack(CapitalResForm reqForm) throws Exception;

  /**
   * 银行卡变更
   * @param user
   * @return
   * @throws Exception
   */
  PageResForm bankCardChange(  User user)throws Exception;

  /**
   * 银行卡变更回调接口
   * @param reqForm
   * @throws IOException
   */
  Map<String,Object> bankCardChangePageCallBack(CapitalResForm reqForm) throws IOException;

  /**
   * 根据用户id查询账户信息
   * @param userId
   * @return
   */
  public Map<String, Object> queryAccountInfo(Long userId) throws Exception;


  public BigDecimal withDrawAmount(String orderNo);

  /**
   * 查询借款详情
   *
   * @param orderCode
   * @return
   */
  OrderDetailResForm getOrderDetailInfo(String orderCode, Long userId);


  int personAccountSuccess(CapitalResForm reqForm);

  /**
   * 根据用户id查询个人开户信息
   * @param userId
   * @return
   */
  public Account getAccountByUserId(long userId);

  /**
   * 根据用户id查询企业开户信息
   * @param userId
   * @return
   */
  public AccountCompany getAccountCompanyByUserId(long userId);

  /**
   * 获得用户开户标识等开户信息
   * @param user
   * @return
   */
  AccountFlagNewResForm queryUserAccountFlag(User user);

  /**
   * 缴费充值
   * @param resForm
   * @throws Exception
   */
  public Map<String,Object> paychargeCallBack(CapitalResForm resForm) throws Exception;

  /**
   *
   * @param user
   * @return
   */
  public Integer selectAccStatus(User user);

  AccountStatusResForm queryAccountStatus(User user);

  /**
   * 担保人充值(只充值)
   * @param resForm
   * @return
   */
  public  Map<String,Object> rechargeGuarantorCallBack(CapitalResForm resForm) throws Exception;

  /**
   * 缴费接口（直连）
   * @param rechargeReqForm
   * @return
   */
  Map<String,Object> charge(RechargeReqForm rechargeReqForm) throws Exception;

  /**
   * 缴费接口（页面）
   * @param chargeReqForm
   * @param user
   * @return
   * @throws Exception
   */
  VerifyDeductPageResForm toVerifyDeductPage(ChargeReqForm chargeReqForm, User user) throws Exception;

  /**
   * 缴费接口（页面）回调接口
   * @param reqForm
   * @return
   */
  Map<String,Object>  verifyDeductCallBack(CapitalResForm reqForm) throws Exception;

  /**
   * 查询充值缴费结果
   * @param reqForm
   * @return
   */
  RechargeFeeResForm queryRechargeFeeStatus(RechargePayReqForm reqForm);

  /**
   * 查询提现结果
   * @param reqForm
   * @return
   */
  /*WithDrawForm queryWithDrawStatus(User user, RechargePayReqForm reqForm);*/

  /**
   * 查询用户激活状态
   * @param custId
   * @return
   */
  ActivateUserResForm queryActivateUserStatus(String custId);

  /**
   * 查询用户状态
   * @param id
   * @return
   */
  UserStatusResForm queryUserStatus(Long id);

  /**
   * 查询还款充值结果
   * @param user
   * @param reqForm
   * @return
   */
  RepaymentResForm queryRepaymentStatus(User user, RechargePayReqForm reqForm);


  /**
   * 担保人充值(缴费)
   * @param user
   * @return
   */
  public PageResForm toChargePageGuarantor(User user) throws Exception;

  /**
   * 查询交易流水
   * @param userId 用户id
   * @param tradeFilters 业务标识
   * @return
   * @throws Exception
   */
  public TradeFlowResForm queryTradeFlow(Long userId,String tradeFilters) throws Exception;

  /**
     * 根据custId查询账户信息
      * @param userId
     * @return
     * @throws Exception
     */
  public AccountStatus queryAccStatus(Long userId) throws Exception;

    /**
     * 企业开户借款系统退回回调接口
     * @param reqForm
     * @return
     */
  Map<String,Object> enterpriseAccountLoanCallBack(LoanEnterpriseReturnForm reqForm);

}
