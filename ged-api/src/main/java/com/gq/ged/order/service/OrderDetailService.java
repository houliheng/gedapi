package com.gq.ged.order.service;

import com.gq.ged.order.controller.res.OrderDetailResForm;

/**
 * Created by wyq_tomorrow on 2018/1/29.
 */
public interface OrderDetailService {
  /**
   * 查询借款详情
   * 
   * @param orderCode
   * @return
   */
  OrderDetailResForm getOrderDetailInfo(String orderCode, Long userId)throws  Exception;
}
