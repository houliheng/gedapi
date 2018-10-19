package com.gq.ged.order.pc.req;

import java.io.Serializable;
import java.math.BigDecimal;

public class RepaymentWithholding implements Serializable{
  private String serial_number; // 序列号 90002019

  private String contract_id; // 合同Id 90002016

  private String contract_no; // 合同编号 90002022

  private String acc_no; // 借款人资金平台账号 90002006

  private BigDecimal amt; // 还款金额 90002020

  private BigDecimal real_repay_amt; // 还款金额

  private String mid_cust_id; // 中间人客户ID

  private String withHold_type; // 代扣类型字段

  private String remark; // 还款备注

  private String complete_time; // 完成时间

  private String accounting_no; // 账务流水号


  public String getSerial_number() {
    return serial_number;
  }

  public void setSerial_number(String serial_number) {
    this.serial_number = serial_number;
  }

  public String getContract_id() {
    return contract_id;
  }

  public void setContract_id(String contract_id) {
    this.contract_id = contract_id;
  }

  public String getContract_no() {
    return contract_no;
  }

  public void setContract_no(String contract_no) {
    this.contract_no = contract_no;
  }

  public String getAcc_no() {
    return acc_no;
  }

  public void setAcc_no(String acc_no) {
    this.acc_no = acc_no;
  }

  public BigDecimal getAmt() {
    return amt;
  }

  public void setAmt(BigDecimal amt) {
    this.amt = amt;
  }

  public BigDecimal getReal_repay_amt() {
    return real_repay_amt;
  }

  public void setReal_repay_amt(BigDecimal real_repay_amt) {
    this.real_repay_amt = real_repay_amt;
  }

  public String getMid_cust_id() {
    return mid_cust_id;
  }

  public void setMid_cust_id(String mid_cust_id) {
    this.mid_cust_id = mid_cust_id;
  }

  public String getWithHold_type() {
    return withHold_type;
  }

  public void setWithHold_type(String withHold_type) {
    this.withHold_type = withHold_type;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getComplete_time() {
    return complete_time;
  }

  public void setComplete_time(String complete_time) {
    this.complete_time = complete_time;
  }

  public String getAccounting_no() {
    return accounting_no;
  }

  public void setAccounting_no(String accounting_no) {
    this.accounting_no = accounting_no;
  }
}
