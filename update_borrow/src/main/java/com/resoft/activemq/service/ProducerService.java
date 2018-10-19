package com.resoft.activemq.service;


/**
 * Created by wyq_tomorrow on 2017/9/4.
 */
public interface ProducerService {

  /**
   * 发送消息
   * 
   * @param reqForm
   */
  void sendMessage();

  /**
   * 还款队列
   */
  void sendMessageRepayment(String msg);
  
  /**
   *入账队列 
   */
  
  void pushMoney(String msg);
  /**
   * 贴息队列
   */
  void discountEnterAccount(String msg);

  /**
   * 提前结清
   * @param msg 信息
   */
  void advancePullRepayment(String msg);
}
