package com.gq.ged.account.controller.req.v2;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

public class RechargePayAPPReqForm implements Serializable {

  @ApiModelProperty("订单号")
  private String orderNo;
  @ApiModelProperty("手机号")
  private String mobile;

  public String getOrderNo() {
    return orderNo;
  }

  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }
}
