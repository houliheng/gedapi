package com.gq.ged.account.controller.res.v2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Auther: zhaozb
 * @Date: 2018/7/4 21:15
 * @Description:
 */
@JsonIgnoreProperties
public class TradeList {
  private String tradeNo;// 交易编号 30 M 交易编号
  private Integer tradeType;// 交易类型 8 M 交易类型
  private String tradeTypeName;// 交易名称
  private String custId;// 客户编号
  private Integer accountId;// 账户id
  private BigDecimal income;// 收入
  private BigDecimal spending;// 支出
  private BigDecimal useableSum;// 可用余额
  private String remarks;// 交易备注
  private Date tardeTime;// 交易时间
  private Integer bidId;// 标的编号
  private Integer repaymentId;// 回款编号
  private BigDecimal bonusAmount;// 红包金额
  private String remark;// 备用字段

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public Integer getTradeType() {
        return tradeType;
    }

    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }

    public String getTradeTypeName() {
        return tradeTypeName;
    }

    public void setTradeTypeName(String tradeTypeName) {
        this.tradeTypeName = tradeTypeName;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public BigDecimal getSpending() {
        return spending;
    }

    public void setSpending(BigDecimal spending) {
        this.spending = spending;
    }

    public BigDecimal getUseableSum() {
        return useableSum;
    }

    public void setUseableSum(BigDecimal useableSum) {
        this.useableSum = useableSum;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getTardeTime() {
        return tardeTime;
    }

    public void setTardeTime(Date tardeTime) {
        this.tardeTime = tardeTime;
    }

    public Integer getBidId() {
        return bidId;
    }

    public void setBidId(Integer bidId) {
        this.bidId = bidId;
    }

    public Integer getRepaymentId() {
        return repaymentId;
    }

    public void setRepaymentId(Integer repaymentId) {
        this.repaymentId = repaymentId;
    }

    public BigDecimal getBonusAmount() {
        return bonusAmount;
    }

    public void setBonusAmount(BigDecimal bonusAmount) {
        this.bonusAmount = bonusAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
