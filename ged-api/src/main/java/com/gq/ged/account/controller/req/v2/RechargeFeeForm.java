package com.gq.ged.account.controller.req.v2;

import java.math.BigDecimal;

/**
 * @Auther: zhaozb
 * @Date: 2018/6/30 15:04
 * @Description:
 */
public class RechargeFeeForm {
  private String orderNo;
  private BigDecimal amount;
  private String custId;

  public String getOrderNo() {
    return orderNo;
  }

  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public String getCustId() {
    return custId;
  }

  public void setCustId(String custId) {
    this.custId = custId;
  }
}
