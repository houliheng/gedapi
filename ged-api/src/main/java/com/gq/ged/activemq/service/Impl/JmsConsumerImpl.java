package com.gq.ged.activemq.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gq.ged.account.controller.req.v2.RechargeReqForm;
import com.gq.ged.account.controller.res.v2.CapitalResForm;
import com.gq.ged.account.dao.model.AccountCompany;
import com.gq.ged.account.service.AccountOpenService;
import com.gq.ged.common.Constant;
import com.gq.ged.order.service.OrderService;
import com.gq.ged.order.service.impl.OrderServiceImpl;
import com.gq.ged.underImport.service.GedImportGetOrderService;
import com.gq.ged.user.dao.mapper.UserMapper;
import com.gq.ged.user.dao.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by gickeuy on 2018/1/9.
 */
@Service
public class JmsConsumerImpl {
  Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
  @Resource
  UserMapper userMapper;

  @Resource
  OrderService orderService;

  @Resource
  GedImportGetOrderService gedImportGetOrderService;

  @Resource
  AccountOpenService accountOpenService;

  @JmsListener(destination = Constant.MQ_GED_USER_UPDATE_CUSTID,
      containerFactory = "jmsQueueListenerContainerFactory")
  public void receive(String msg) {
    logger.info("调用开户回调接口=======");
    JSONObject jsonObject = JSONObject.parseObject(msg);
    String userId = jsonObject.getString("userId");
    String custId = jsonObject.getString("custId");
    String username = jsonObject.getString("username");
    logger.info("userId:"+userId+"===="+"custId:"+custId+"==username:"+username);
    User user = new User();
    user.setId(Long.parseLong(userId));
    user.setGetCustId(custId);
    user.setUsername(username);
    userMapper.updateByPrimaryKeySelective(user);

  }

  /**
   * lwiei  充值成功后进行缴费操作
   * @param msg
   * @throws Exception
   */
  @JmsListener(destination = Constant.MQ_GED_COMPENSATION,
          containerFactory = "jmsQueueListenerContainerFactory")
  public void receivePayCallBack(String msg) throws Exception {
    logger.info("调用缴费接口...");
    JSONObject jsonObject = JSONObject.parseObject(msg);
    String userId = jsonObject.getString("userId");
    //缴费
    orderService.userCompensation(  Long.parseLong(userId));
  }

  /**
   * 李伟  缴费成功后进行推送借款端操作
   * @param msg
   * @throws Exception
   */
  @JmsListener(destination = Constant.MQ_GED_PUSH_TO_BORROW,
          containerFactory = "jmsQueueListenerContainerFactory")
  public void receivePushToBorrow(String msg) throws Exception {
    JSONObject jsonObject = JSONObject.parseObject(msg);
    String orderCode = jsonObject.getString("orderCode");
    String sysFlag = jsonObject.getString("sysFlag");
    String contractCode = jsonObject.getString("contractCode");
    //推送借款端
    orderService.pushToBrrow( orderCode, Integer.parseInt(sysFlag) ,contractCode);
    System.out.println("====================推送借款端信息beigen=====================");
    System.out.println("orderCode:"+orderCode);
    System.out.println("sysFlag:"+sysFlag);
    System.out.println("contractCode:"+sysFlag);
    System.out.println("====================推送借款端信息end=====================");
  }

  /**
    充值成功后推送get
   * @param msg
   * @throws Exception
   */
  @JmsListener(destination = Constant.MQ_GED_CALL_BACK_GET,
          containerFactory = "jmsQueueListenerContainerFactory")
  public void receiveCallBackGetAfterPay(String msg) throws Exception {
    JSONObject jsonObject = JSONObject.parseObject(msg);
    //推送get
    String userId = jsonObject.getString("userId");
    orderService.userRechargeCallBack(Long.parseLong(userId));
  }

  /**
   * 还款支付后推送冠e通
   * @param msg
   * @throws Exception
   */
//  @JmsListener(destination = Constant.MQ_GED_REPAYMENT_CALL_BACK_GET,
//          containerFactory = "jmsQueueListenerContainerFactory")
//  public void receiveCallBackGetAfterRepaymentPay(String msg) throws Exception {
//    JSONObject jsonObject = JSONObject.parseObject(msg);
//    //还款支付后推送冠e通
//    String userId = jsonObject.getString("userId");
//    orderService.userRechargeCallBack(Long.parseLong(userId));
//
//  }

  /**
   * 还款充值成功后推送借款系统
   * @param record
   * @throws Exception
   */
 /* @JmsListener(destination = Constant.MQ_GED_REPAYMENT_RECHARGE_BORROW,
          containerFactory = "jmsQueueListenerContainerFactory")
  public void receiveAfterRepaymentPayBorrow(GedRepaymentRecord record) throws Exception {
    //还款充值成功后推送借款系统
    accountOpenService.pushToBorrowRepayment(record);
  }*/


  /**
   * zhaozb  企业开户成功后推送到借款系统
   * @param accountCompany
   * @throws Exception
   */
  @JmsListener(destination = Constant.MQ_GED_ENTERPRISE_ACCOUNT_TO_LOAN,
          containerFactory = "jmsQueueListenerContainerFactory")
  public void receiveEnterpriseAccountCallBack(AccountCompany accountCompany) throws Exception {
    //企业开户成功后推送到借款系统
    accountOpenService.pushEnterpriseAccounToLoan(accountCompany);
  }

  /**
   * zhaozb 企业开户成功后推送到冠e通
   * @param resForm
   * @throws Exception
   */
  @JmsListener(destination = Constant.MQ_GED_ENTERPRISE_ACCOUNT_TO_GET,
          containerFactory = "jmsQueueListenerContainerFactory")
  public void receiveEnterpriseAccountCallBackToGET(CapitalResForm resForm) throws Exception {
    // 把企业开户信息推送到冠e通
    accountOpenService.pushEnterpriseAccountToGqget(resForm);
  }

  /**
   * zhaozb 还款充值后，调资金直连第三方缴费
   * @param rechargeReqForm
   * @throws Exception
   */
  @JmsListener(destination = Constant.MQ_GED_REPAYMENT_DEDUCT,
          containerFactory = "jmsQueueListenerContainerFactory")
  public void receiveCharge(RechargeReqForm rechargeReqForm) throws Exception {
    //调资金直连第三方缴费
    Map<String,Object> result = accountOpenService.charge(rechargeReqForm);
    logger.info("====================还款充值后，调资金直连第三方缴费   返回结果： ====================="+ JSON.toJSONString(result));
  }


  /**
   * 保存借款系统传过来的线下订单信息
   * @param msg
   * @throws Exception
   */
  @JmsListener(destination = Constant.MQ_GED_UNDER_INFOS,
          containerFactory = "jmsQueueListenerContainerFactory")
  public void savUnderInfos(String msg) throws Exception {
    gedImportGetOrderService.saveList(msg );

  }

}
