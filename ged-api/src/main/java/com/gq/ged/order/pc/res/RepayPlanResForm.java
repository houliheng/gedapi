package com.gq.ged.order.pc.res;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.gq.ged.order.controller.res.DeductResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Zhaozb on 2018/3/14.
 */

@ApiModel
public class RepayPlanResForm implements Serializable {
  private static final long serialVersionUID = 1L;

  @ApiModelProperty("isPay == 1 可以支付还款  ！=0  不可支付还款")
  private String isPay;

  @ApiModelProperty("订单号")
  private String orderNo;
  @ApiModelProperty("借款金额")
  private String loanAmount;
  @ApiModelProperty("借款期限")
  private String loanTerm;
  @ApiModelProperty("借款状态")
  private String status;
  @ApiModelProperty("是否能够还款计划  1 是  0 否  -1 处查询订单失败")
  private String isCanRepay = "1";


  @ApiModelProperty("企业开户行")
  private String companyBankOfDeposit;
  @ApiModelProperty("收款户名")
  private String corporationName;
  @ApiModelProperty("账号")
  private String CompanyAccount;

  @ApiModelProperty("已还期数")
  private String repaymentPeriod;
  @ApiModelProperty("应还日期")
  private String dueDate;
  @ApiModelProperty("应还金额")
  private String dueAmount;
  @ApiModelProperty("剩余应还金额")
  private String remainDueAmount;


  @ApiModelProperty("还款方式")
  private String repayWay;
  @ApiModelProperty("待还款总额")
  private String dueRepayTotal;
  @ApiModelProperty("实还总额")
  private String actualRepayTotal;
  @ApiModelProperty("放款日")
  private String loanDay;

  @ApiModelProperty("还款列表")
  List<RepaymentItem> repaymentPlanList;


  public String getIsCanRepay() {
    return isCanRepay;
  }

  public void setIsCanRepay(String isCanRepay) {
    this.isCanRepay = isCanRepay;
  }

  public String getIsPay() {
    return isPay;
  }

  public void setIsPay(String isPay) {
    this.isPay = isPay;
  }

  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  public String getOrderNo() {
    return orderNo;
  }

  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  public String getLoanAmount() {
    return loanAmount;
  }

  public void setLoanAmount(String loanAmount) {
    this.loanAmount = loanAmount;
  }

  public String getLoanTerm() {
    return loanTerm;
  }

  public void setLoanTerm(String loanTerm) {
    this.loanTerm = loanTerm;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getCompanyBankOfDeposit() {
    return companyBankOfDeposit;
  }

  public void setCompanyBankOfDeposit(String companyBankOfDeposit) {
    this.companyBankOfDeposit = companyBankOfDeposit;
  }

  public String getCorporationName() {
    return corporationName;
  }

  public void setCorporationName(String corporationName) {
    this.corporationName = corporationName;
  }

  public String getCompanyAccount() {
    return CompanyAccount;
  }

  public void setCompanyAccount(String companyAccount) {
    CompanyAccount = companyAccount;
  }

  public String getRepaymentPeriod() {
    return repaymentPeriod;
  }

  public void setRepaymentPeriod(String repaymentPeriod) {
    this.repaymentPeriod = repaymentPeriod;
  }

  public String getDueDate() {
    return dueDate;
  }

  public void setDueDate(String dueDate) {
    this.dueDate = dueDate;
  }

  public String getDueAmount() {
    return dueAmount;
  }

  public void setDueAmount(String dueAmount) {
    this.dueAmount = dueAmount;
  }

  public String getRemainDueAmount() {
    return remainDueAmount;
  }

  public void setRemainDueAmount(String remainDueAmount) {
    this.remainDueAmount = remainDueAmount;
  }

  public String getRepayWay() {
    return repayWay;
  }

  public void setRepayWay(String repayWay) {
    this.repayWay = repayWay;
  }

  public String getDueRepayTotal() {
    return dueRepayTotal;
  }

  public void setDueRepayTotal(String dueRepayTotal) {
    this.dueRepayTotal = dueRepayTotal;
  }

  public String getActualRepayTotal() {
    return actualRepayTotal;
  }

  public void setActualRepayTotal(String actualRepayTotal) {
    this.actualRepayTotal = actualRepayTotal;
  }

  public String getLoanDay() {
    return loanDay;
  }

  public void setLoanDay(String loanDay) {
    this.loanDay = loanDay;
  }

  public List<RepaymentItem> getRepaymentPlanList() {
    return repaymentPlanList;
  }

  public void setRepaymentPlanList(List<RepaymentItem> repaymentPlanList) {
    this.repaymentPlanList = repaymentPlanList;
  }
}
