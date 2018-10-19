package com.gq.ged.order.pc.res;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by Levi on 2018/3/21.
 */
public class RepaymentItem implements Serializable {
  private static final long serialVersionUID = 1L;

  @ApiModelProperty("期数")
  private String periodNum;
  @ApiModelProperty("应还日期")
  private String deductDate;
  @ApiModelProperty("应还款总金额")
  private String repayAmount;
  @ApiModelProperty("实际还款总金额")
  private String factAmount;
  @ApiModelProperty("应还罚息")
  private String fineAmount;
  @ApiModelProperty("应还违约金")
  private String penaltyAmount;
  @ApiModelProperty("还款状态")
  private String repayPeriodStatus;
  @ApiModelProperty("代偿")
  private String compensatoryAmount;
  @ApiModelProperty("是否出现还款按钮  1 出现  -1 不出现")
  private String isCanPay = "-1";
  @ApiModelProperty("待还金额")
  private String stayMoney;


  @ApiModelProperty("服务费")
  private String serviceFee;
  @ApiModelProperty("账户管理费")
  private String managementFee;

  @ApiModelProperty("利息")
  private String interestAmount;
  @ApiModelProperty("本金")
  private String capitalAmount;

  public String getPeriodNum() {
    return periodNum;
  }

  public void setPeriodNum(String periodNum) {
    this.periodNum = periodNum;
  }

  public String getDeductDate() {
    return deductDate;
  }

  public void setDeductDate(String deductDate) {
    this.deductDate = deductDate;
  }

  public String getRepayAmount() {
    return repayAmount;
  }

  public void setRepayAmount(String repayAmount) {
    this.repayAmount = repayAmount;
  }

  public String getFactAmount() {
    return factAmount;
  }

  public void setFactAmount(String factAmount) {
    this.factAmount = factAmount;
  }

  public String getFineAmount() {
    return fineAmount;
  }

  public void setFineAmount(String fineAmount) {
    this.fineAmount = fineAmount;
  }

  public String getPenaltyAmount() {
    return penaltyAmount;
  }

  public void setPenaltyAmount(String penaltyAmount) {
    this.penaltyAmount = penaltyAmount;
  }

  public String getRepayPeriodStatus() {
    return repayPeriodStatus;
  }

  public void setRepayPeriodStatus(String repayPeriodStatus) {
    this.repayPeriodStatus = repayPeriodStatus;
  }

  public String getCompensatoryAmount() {
    return compensatoryAmount;
  }

  public void setCompensatoryAmount(String compensatoryAmount) {
    this.compensatoryAmount = compensatoryAmount;
  }

  public String getIsCanPay() {
    return isCanPay;
  }

  public void setIsCanPay(String isCanPay) {
    this.isCanPay = isCanPay;
  }

  public String getStayMoney() {
    return stayMoney;
  }

  public void setStayMoney(String stayMoney) {
    this.stayMoney = stayMoney;
  }

  public String getServiceFee() {
    return serviceFee;
  }

  public void setServiceFee(String serviceFee) {
    this.serviceFee = serviceFee;
  }

  public String getManagementFee() {
    return managementFee;
  }

  public void setManagementFee(String managementFee) {
    this.managementFee = managementFee;
  }

  public String getInterestAmount() {
    return interestAmount;
  }

  public void setInterestAmount(String interestAmount) {
    this.interestAmount = interestAmount;
  }

  public String getCapitalAmount() {
    return capitalAmount;
  }

  public void setCapitalAmount(String capitalAmount) {
    this.capitalAmount = capitalAmount;
  }
}
