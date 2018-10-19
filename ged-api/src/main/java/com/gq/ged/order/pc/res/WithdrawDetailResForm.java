package com.gq.ged.order.pc.res;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Levi on 2018/4/18.
 */
public class WithdrawDetailResForm extends BorrowOrderBaseInfo {

  @ApiModelProperty("已放款金额")
  private String loanAmount;
  @ApiModelProperty("账户余额")
  private String accountAmt;
  @ApiModelProperty("实际到账金额")
  private String actualAmt;
  @ApiModelProperty("提现到银行卡")
  private String bankInfo;
  @ApiModelProperty("订单号")
  private String orderNo;
  @ApiModelProperty("可提现金额")
  private String canWithdrawAmt;


  public String getCanWithdrawAmt() {
    return canWithdrawAmt;
  }

  public void setCanWithdrawAmt(String canWithdrawAmt) {
    this.canWithdrawAmt = canWithdrawAmt;
  }

  @Override
  public String getLoanAmount() {
    return loanAmount;
  }

  @Override
  public void setLoanAmount(String loanAmount) {
    this.loanAmount = loanAmount;
  }

  public String getAccountAmt() {
    return accountAmt;
  }

  public void setAccountAmt(String accountAmt) {
    this.accountAmt = accountAmt;
  }

  public String getActualAmt() {
    return actualAmt;
  }

  public void setActualAmt(String actualAmt) {
    this.actualAmt = actualAmt;
  }

  public String getBankInfo() {
    return bankInfo;
  }

  public void setBankInfo(String bankInfo) {
    this.bankInfo = bankInfo;
  }

  public String getOrderNo() {
    return orderNo;
  }

  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }
}
