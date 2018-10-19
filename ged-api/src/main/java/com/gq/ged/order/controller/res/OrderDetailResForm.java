package com.gq.ged.order.controller.res;

import com.gq.ged.account.controller.res.AccountResForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by wyq_tomorrow on 2018/1/29.
 */
@ApiModel
public class OrderDetailResForm implements Serializable {
  private static final long serialVersionUID = 1L;
  @ApiModelProperty("借款金额")
  private BigDecimal loanAmount;
  @ApiModelProperty("借款期限")
  private String loanTerm;
  @ApiModelProperty("借款类型")
  private String loanType;
  @ApiModelProperty("日利率")
  private String rateDay;
  @ApiModelProperty("服务费")
  private String serviceFee;
  @ApiModelProperty("账户信息")
  private AccountResForm accountResForm;
  @ApiModelProperty("开始时间")
  private String startTime;
  @ApiModelProperty("结束时间")
  private String endTime;
  @ApiModelProperty("还款日期")
  private String paymentDay;
  @ApiModelProperty("借款用途")
  private String loanPurpose;
  @ApiModelProperty("状态")
  private String status;
  @ApiModelProperty("借款合同")
  private String contractUrl;
  @ApiModelProperty(value = "电子合同标识")
  private Integer signContractFlag;

  public String getLoanType() {
    return loanType;
  }

  public void setLoanType(String loanType) {
    this.loanType = loanType;
  }

  public BigDecimal getLoanAmount() {
    return loanAmount;
  }

  public void setLoanAmount(BigDecimal loanAmount) {
    this.loanAmount = loanAmount;
  }

  public String getLoanTerm() {
    return loanTerm;
  }

  public void setLoanTerm(String loanTerm) {
    this.loanTerm = loanTerm;
  }

  public String getRateDay() {
    return rateDay;
  }

  public void setRateDay(String rateDay) {
    this.rateDay = rateDay;
  }

  public String getServiceFee() {
    return serviceFee;
  }

  public void setServiceFee(String serviceFee) {
    this.serviceFee = serviceFee;
  }

  public AccountResForm getAccountResForm() {
    return accountResForm;
  }

  public void setAccountResForm(AccountResForm accountResForm) {
    this.accountResForm = accountResForm;
  }

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public String getPaymentDay() {
    return paymentDay;
  }

  public void setPaymentDay(String paymentDay) {
    this.paymentDay = paymentDay;
  }

  public String getLoanPurpose() {
    return loanPurpose;
  }

  public void setLoanPurpose(String loanPurpose) {
    this.loanPurpose = loanPurpose;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getContractUrl() {
    return contractUrl;
  }

  public void setContractUrl(String contractUrl) {
    this.contractUrl = contractUrl;
  }

  public Integer getSignContractFlag() {
    return signContractFlag;
  }

  public void setSignContractFlag(Integer signContractFlag) {
    this.signContractFlag = signContractFlag;
  }
}
