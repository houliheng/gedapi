package com.gq.ged.account.controller.res.v2;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ActivateUser implements Serializable{
  private String custId;// 客户ID
  private String mobile;// 客户银行预留手机号
  private String idCardNo;// 身份证号
  private String custName;// 客户真实姓名
  private String bankName;// 开户行名称
  private String bankCode;// 银行编码
  private String bankcardNo;// 银行卡号
  private String remark;// 备用字段

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

  public String getIdCardNo() {
    return idCardNo;
  }

  public void setIdCardNo(String idCardNo) {
    this.idCardNo = idCardNo;
  }

  public String getCustName() {
    return custName;
  }

  public void setCustName(String custName) {
    this.custName = custName;
  }

  public String getBankName() {
    return bankName;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  public String getBankCode() {
    return bankCode;
  }

  public void setBankCode(String bankCode) {
    this.bankCode = bankCode;
  }

  public String getBankcardNo() {
    return bankcardNo;
  }

  public void setBankcardNo(String bankcardNo) {
    this.bankcardNo = bankcardNo;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }
}
