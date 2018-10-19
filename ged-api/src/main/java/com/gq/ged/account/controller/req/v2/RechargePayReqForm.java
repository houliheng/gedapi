package com.gq.ged.account.controller.req.v2;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;

public class RechargePayReqForm implements Serializable {

  @ApiModelProperty("订单编号")
  private String orderNo;
 /* @ApiModelProperty("充值金额")
  private BigDecimal amount;*/

  public String getOrderNo() {
    return orderNo;
  }

  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

}
