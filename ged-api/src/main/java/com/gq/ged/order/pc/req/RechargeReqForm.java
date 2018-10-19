package com.gq.ged.order.pc.req;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 立即还款 Created by Zhaozb on 2018/3/14.
 */
public class RechargeReqForm implements Serializable {

  // 充值金额
  @ApiModelProperty(value = "充值金额")
  private BigDecimal amount;

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }
}
