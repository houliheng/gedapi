package com.gq.ged.account.controller.res.v2;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RepaymentResForm implements Serializable {

  @ApiModelProperty("充值金额")
  private BigDecimal amount;
  @ApiModelProperty("订单号")
  private String orderNo;
  @ApiModelProperty("还款状态")
  private Integer status;// 1充值完成2还款完成3还款失败

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

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }
}
