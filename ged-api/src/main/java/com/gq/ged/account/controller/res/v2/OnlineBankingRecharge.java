package com.gq.ged.account.controller.res.v2;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 网银充值回调数据
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OnlineBankingRecharge implements Serializable {
  private String custId;// 客户ID
  private String mobile;// 客户手机号
  private BigDecimal amount;// 充值金额
  private String custType;// 账户类型
  private String remark;// 备用字段
  private String tradeDate;//交易时间

  public String getCustId() {
    return custId;
  }

  public void setCustId(String custId) {
    this.custId = custId;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public String getCustType() {
    return custType;
  }

  public void setCustType(String custType) {
    this.custType = custType;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getTradeDate() {
    return tradeDate;
  }

  public void setTradeDate(String tradeDate) {
    this.tradeDate = tradeDate;
  }
}
