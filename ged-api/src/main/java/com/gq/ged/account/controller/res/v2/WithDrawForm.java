package com.gq.ged.account.controller.res.v2;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Auther: zhaozb
 * @Date: 2018/6/12 12:46
 * @Description:
 */
public class WithDrawForm implements Serializable {
  @ApiModelProperty("提现状态") // 0未提现 1提现成功 2提现失败
  private Integer status;
  @ApiModelProperty("提现金额")
  private BigDecimal amount;
  @ApiModelProperty("提现银行卡")
  private String bankCardNo;

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

  public String getBankCardNo() {
    return bankCardNo;
  }

  public void setBankCardNo(String bankCardNo) {
    this.bankCardNo = bankCardNo;
  }
}
