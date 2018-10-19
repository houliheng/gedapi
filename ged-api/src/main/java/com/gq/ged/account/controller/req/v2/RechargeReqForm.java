package com.gq.ged.account.controller.req.v2;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;

public class RechargeReqForm implements Serializable {

  @ApiModelProperty("分期账户管理费")
  private BigDecimal serviceFee;

  @ApiModelProperty("分期服务费")
  private BigDecimal managementFee;

  @ApiModelProperty("订单编号")
  private String orderNo;

  @ApiModelProperty("用户ID")
  private String custId;

  public BigDecimal getServiceFee() {
    return serviceFee;
  }

  public void setServiceFee(BigDecimal serviceFee) {
    this.serviceFee = serviceFee;
  }

  public BigDecimal getManagementFee() {
    return managementFee;
  }

  public void setManagementFee(BigDecimal managementFee) {
    this.managementFee = managementFee;
  }

  public String getOrderNo() {
    return orderNo;
  }

  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  public String getCustId() {
    return custId;
  }

  public void setCustId(String custId) {
    this.custId = custId;
  }
}
