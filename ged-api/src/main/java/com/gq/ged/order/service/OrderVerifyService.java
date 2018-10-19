package com.gq.ged.order.service;

import com.gq.ged.order.controller.req.CreditInfoToRequest;
import com.gq.ged.order.controller.res.OrderVerifyResForm;

/**
 * Created by wrh on 2018/4/25.
 */
public interface OrderVerifyService {
  /**
   * 新增信审信息
   *
   * @param reqForm
   */
  void insertOrderVerify(CreditInfoToRequest reqForm);

  /**
   * 信审信息
   * @param applyNo
   * @param orderSubNo
   * @return
   * @throws Exception
   */
  public OrderVerifyResForm selectOrderVerify(String applyNo, String orderSubNo) throws Exception;

}
