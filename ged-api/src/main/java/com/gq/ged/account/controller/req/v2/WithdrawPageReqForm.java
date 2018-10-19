package com.gq.ged.account.controller.req.v2;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

public class WithdrawPageReqForm implements Serializable {

  @ApiModelProperty("订单编号")
  private String orderNo;

  public String getOrderNo() {
    return orderNo;
  }

  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

}
