package com.gq.ged.account.controller.res.v2;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Auther: zhaozb
 * @Date: 2018/6/12 12:46
 * @Description:
 */
public class RechargeFeeResForm implements Serializable {
  @ApiModelProperty("充值缴费状态")//0未充值1充值成功2充值失败3缴费成功6缴费失败
  private Integer status;
  @ApiModelProperty("充值金额")
  private BigDecimal amount;

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }
}
