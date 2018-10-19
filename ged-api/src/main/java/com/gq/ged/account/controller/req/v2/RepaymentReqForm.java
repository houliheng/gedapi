package com.gq.ged.account.controller.req.v2;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;

public class RepaymentReqForm implements Serializable {

  @ApiModelProperty("充值金额")
  private BigDecimal amount;

  @ApiModelProperty("订单编号")
  private String orderNo;

  @ApiModelProperty("还款类型")
  private Integer type;

  @ApiModelProperty("还款期数")
  private Integer period;

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public String getOrderNo() {
    return orderNo;
  }

  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public Integer getPeriod() {
    return period;
  }

  public void setPeriod(Integer period) {
    this.period = period;
  }
}
