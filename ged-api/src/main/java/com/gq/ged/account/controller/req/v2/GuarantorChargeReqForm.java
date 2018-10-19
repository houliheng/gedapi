package com.gq.ged.account.controller.req.v2;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;

public class GuarantorChargeReqForm implements Serializable {

  @ApiModelProperty("充值金额")
  private BigDecimal amount;

  /*
   * @ApiModelProperty("订单编号") private String orderNo;
   */

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }
}
