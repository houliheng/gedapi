package com.gq.ged.account.controller.res.v2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Auther: zhaozb
 * @Date: 2018/5/30 15:19
 * @Description:
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountBalanceResForm implements Serializable {
  private String custId;// 客户ID
  private BigDecimal amount;// 账户余额
  private BigDecimal freezeAmt;// 冻结金额
  private Integer custType;// 账户类型 0主账户 1借款客户

  public String getCustId() {
    return custId;
  }

  public void setCustId(String custId) {
    this.custId = custId;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public BigDecimal getFreezeAmt() {
    return freezeAmt;
  }

  public void setFreezeAmt(BigDecimal freezeAmt) {
    this.freezeAmt = freezeAmt;
  }

  public Integer getCustType() {
    return custType;
  }

  public void setCustType(Integer custType) {
    this.custType = custType;
  }
}
