package com.gq.ged.order.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.gq.ged.order.controller.req.*;
import com.gq.ged.order.controller.res.*;
import com.gq.ged.order.dao.model.GedOrder;
import com.gq.ged.order.dao.model.GedRepaymentRecord;
import com.gq.ged.order.pc.req.*;
import com.gq.ged.order.pc.res.*;
import com.gq.ged.user.dao.model.User;

/**
 * Created by wrh on 2018/1/18.
 */
public interface OrderNewService {

  /**
   * 新增订单
   *
   * @param reqForm
   * @return
   */
  GedOrderIdResForm createOrder(GedOrderReqForm reqForm, User userInfo) throws Exception;

  /**
   * 查询状态
   *
   * @param user
   * @return
   * @throws Exception
   */
  RepayPlanTotleResForm selectOrderStatus(User user) throws Exception;

  /**
   * 更新期限和金额
   *
   * @param reqForm
   * @return
   * @throws Exception
   */
  void updateLoanTeAmo(GedOrderUpdateReqForm reqForm) throws Exception;


  /**
   * 借款记录
   *
   * @param userInfo
   * @return
   * @throws Exception
   */
  List<GedLoanRecordResForm> selectLoanRecord(User userInfo) throws Exception;

  /**
   * 订单状态反馈接口
   *
   * @param reqForm
   */
  public void OrderStateFeedBack(GedOrderStatusReqForm reqForm) throws Exception;

  /**
   * 订单状态反馈审核未通过接口
   *
   * @param reqForm
   */
  public void OrderStateNotBack(GedOrderNotStatusReqForm reqForm) throws Exception;

  /**
   * 借款系统推送冠E贷新增
   *
   * @param reqForm
   */
  public void pushLoanCreateOrder(PushOrderListForm reqForm) throws Exception;


  /**
   * 还款计划查询
   *
   * @param userInfo
   */
  public RepayPlanTotleResForm selectRepayment(User userInfo, GedOrderNoReqForm reqForm)
      throws Exception;

  /**
   * 判断是否开户 返回信息
   *
   * @param userInfo
   * @return
   */
  public AccountWheResForm selectWheAccount(User userInfo);

  /**
   * 审核未通过 我知道接口
   *
   * @param userInfo
   * @param reqForm
   */
  public void auditFail(User userInfo, GedOrderNoReqForm reqForm);

  /**
   * 更新实名认证信息
   *
   * @param userInfo
   */
  public void updateCertification(User userInfo, CertificationReqForm reqForm);

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
   * 确认提现
   *
   * @param userInfo
   * @return
   */
  public GedServiceGuarFlagResForm confirWithDrawCash(User userInfo, String orderNo)
      throws Exception;

  /**
   * 根据用户id获取其所有订单
   * 
   * @param userId
   * @return
   */
  public List<GedOrder> getOrdersByUserId(Long userId);


  public List<GedOrder> selectOrderByMasterOrderNo(String masterOrderNo);

  /**
   * 根据用户id获取最新的订单
   * 
   * @param userId
   * @return
   */
  public GedOrder getNewOrderByUserId(Long userId);

  /**
   * 资金管理首页 1 转向费用支付页面 2 转向无此订单页面 3转向订单查看页面
   * 
   * @param userId
   * @return
   */
  public OrderFeesDetailReqForm orderManage(Long userId) throws Exception;

  /**
   * 获取费用支付页面信息
   * 
   * @param userId
   * @return
   */
  public OrderFeesDetailReqForm payFeeInfos(Long userId) throws Exception;

  /**
   * 获取订单查看页面详细信息
   * 
   * @param userId
   * @return
   */
  public OrderViewsReqForm orderView(Long userId);

  /**
   * 只用作调用资金端提现接口
   * 
   * @param userId
   * @param orderNo
   * @return
   * @throws Exception
   */
  public WithDrawCapitalResForm withDr(long userId, String orderNo) throws Exception;

  /**
   * get充值接口
   * 
   * @param paraForm
   * @return
   * @throws Exception
   */
  public JSONObject recharge(RechargeForm paraForm) throws Exception;

  /**
   * get查询充值是否成功接口
   * 
   * @param paraForm
   * @return
   * @throws Exception
   */
  public JSONObject rechargeCallBack(RechargeCallBackForm paraForm) throws Exception;

  /**
   * get费用接口
   * 
   * @param paraForm
   * @return
   * @throws Exception
   */
  public JSONObject compensation(ChargeAmountForm paraForm) throws Exception;

  /**
   * 用户充值接口
   * 
   * @param userId
   */
  public RechargeReForm userRecharge(Long userId, String bankId) throws Exception;

  public RechargeReForm userRechargeForRepayment(Long userId, String bankId, String amt,
      String periodNum) throws Exception;

  /***
   * 用户充值成功回调
   * 
   * @param userId
   */
  public void userRechargeCallBack(Long userId) throws Exception;

  /**
   * 还款充值成功后回调get入账
   * 
   * @param orderNo 第三方流水号
   * @throws Exception
   */
  public void userRechargeCallBackForRepayment(String orderNo, String amt, PayCallBackReqForm res)
      throws Exception;

  /**
   * 缴费
   * 
   * @param userId
   */
  public CompensationResForm userCompensation(Long userId) throws Exception;

  /**
   * 根据订单编号生成充值时第三方的请求参数
   * 
   * @param orderNo
   * @return
   */
  public GeneratePayParas generatePayParas(String seqNum, String loginName, String orderNo,
      Long userId, String orderPayType, String issinscd);

  /**
   * 生称还款的支付参数
   * 
   * @param seqNum
   * @param loginName
   * @param orderNo
   * @param userId
   * @param orderPayType
   * @param issinscd
   * @return
   */
  public GeneratePayParas generatePayParasForRepayment(String seqNum, String loginName,
      String orderNo, Long userId, String orderPayType, String issinscd, String amt);

  /**
   * 第三方支付成功后保 回调的接口
   * 
   * @return
   */
  public Integer saveResParasAfterPay(PayCallBackReqForm res) throws Exception;

  /**
   * 保存指定订单费用支付时选择的bankid
   * 
   * @param orderNo
   * @param bankId
   * @return
   */
  public Integer saveBankId(String orderNo, String bankId, Long userId);


  /**
   * 提现回调接口
   * 
   * @param reqForm
   */
  public Map<String, Object> callBackwithDraw(CallBackWithDrawReqForm reqForm) throws Exception;

  /**
   * 根据用户ID查询订单信息
   * 
   * @param userId
   * @return
   */
  public List<GedOrder> selectGedOrder(long userId);

  /**
   * 还款计划
   * 
   * @return
   * @throws Exception
   */
  RepayPlanResForm repaymentPlan(Long userId) throws Exception;

  /**
   *
   * @param orderNo
   */
  public WithDrawViewResForm beforeWithDr(String orderNo, Long userId) throws Exception;

  /**
   * 第三方支付（还款）成功后回调的接口
   *
   * @param payReqForm
   * @return
   */
  public Integer saveRepaymentAfterPay(ParasAfterPayReqForm payReqForm) throws Exception;

  /**
   * 资产端收取担保费保证金后的回调接口 ---根据订单编号修改serviceFlag为2
   * 
   * @param req
   * @return
   */
  public Integer updateServiceFlagByOrderNo(RpdateServiceFlagByOrderNoReqForm req);

  /**
   * 将收完服务费的订单推送到资产端
   * 
   * @param orderNo
   * @param sysFlag
   * @return
   */
  public Integer pushToBrrow(String orderNo, Integer sysFlag, String contactNo) throws Exception;

  /**
   * 查询用户当前订单的缴费状态
   * 
   * @param userId
   * @return
   */
  public CheckServiceFeeResForm CheckServiceFeeStatus(Long userId);

  /**
   * 充值中操作
   * 
   * @param userId
   */
  public void paying(Long userId, String orderNo);

  /**
   * 根据订单
   * 
   * @param orderNo
   * @return
   */
  public PushToBorrowRepaymentResForm pushToBorrowRepayment(String orderNo) throws Exception;

  /**
   * 调用借款端执行还款
   * 
   * @param paraForm
   * @return
   * @throws Exception
   */
  public JSONObject repay(PushToBorrowRepaymentReqForm paraForm) throws Exception;


  /**
   * 根据userid获取还款状态
   * 
   * @param userId
   * @return
   */
  public Integer getRepayStatus(Long userId);

  /**
   * 根据订单号修改还款成功状态为0(还款进行中)
   * @param gedOrder
   */
  public void updateRepayStatusAfterRecharge(GedOrder gedOrder);



  /**
   * 提现金额
   * 
   * @param orderNo
   * @return
   */
  public BigDecimal withDrawAmount(String orderNo);

  /**
   * 根据订单号获取合同地址
   * 
   * @param ordrNo
   * @return
   */
  public String getContractUrlByOrderNo(String ordrNo) throws Exception;

  /**
   * 根据订单号获取合同地址
   * 
   * @param contractNo
   * @return
   */
  public String getContractUrlByContractNo(String contractNo) throws Exception;

  /**
   * 分页条件查询订单
   * 
   * @param gedOrder
   * @return
   */
  public PageInfo<GedOrder> getOrdersPageByCondition(GedOrder gedOrder, Integer pageNum,
      Integer pageSize);

  /**
   * 条件查询订单 不分页
   * 
   * @param gedOrder
   * @return
   */
  public List<GedOrder> getOrdersByCondition(GedOrder gedOrder);

  /**
   * 获取在途中的订单
   * 
   * @param gedOrder
   * @return
   */
  public List<GedOrder> getOrderingByCondition(GedOrder gedOrder, Integer type);

  /**
   * 获取还款计划
   * 
   * @param param
   * @return
   * @throws Exception
   */
  public Map LoanSystem(Map<String, String> param) throws Exception;

  /**
   * 根据订单编号获取订单
   * 
   * @param orderNo
   * @return
   */
  public GedOrder selectOrderByOrderNo(String orderNo);

  /**
   * 根据orderid查询订单信息
   * 
   * @param id
   * @return
   */
  public GedOrder selectByPrimy(Long id);

  /**
   * 获取在途中的订单
   * 
   * @param gedOrder
   * @param type
   * @return
   */
  public GedOrder getOneOrderingByCondition(GedOrder gedOrder, Integer type);

  public GedRepaymentRecord getGedRepaymentRecordByOrderNo(String orderNo);

  /**
   * 新增订单
   * 
   * @param gedOrder
   */
  public void pushCreateOrder(GedOrder gedOrder);


  /**
   * 根据订单号查询订单
   * @param orderNo
   * @return
   */
  public GedOrder getGedOrderByOrderNo(String orderNo);


}
