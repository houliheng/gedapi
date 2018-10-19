package com.gq.ged.order.service;

import com.gq.ged.order.dao.model.OrderLog;

/**
 * Created by wyq_tomorrow on 2018/1/29.
 */
public interface OrderLogService {
  /**
   * 查询订单日志
   * 
   * @param orderId
   * @param targetStatus
   * @return
   */
  OrderLog getOrderLogByOrderId(Long orderId, Integer targetStatus);

  /**
   * 新增日志表
   * @param orderLog
   */
  int createOrderlog(OrderLog orderLog);
}
