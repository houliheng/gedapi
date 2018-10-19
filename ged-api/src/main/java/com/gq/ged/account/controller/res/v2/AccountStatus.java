package com.gq.ged.account.controller.res.v2;

/**
 * @Auther: zhaozb
 * @Date: 2018/6/26 17:16
 * @Description:
 */
public class AccountStatus {
  private String custId;// 客户ID
  private String custName;// 客户姓名
  private String mobile;// 客户手机号
  private String idCardNo;// 证件号码
  private String bankName;// 银行名称
  private String bankCode;// 银行编码
  private String bankNo;// 银行卡号
  private String userRole;// 用户角色 '1：投资人 2：借款人 ',
  private String accType;// 账户类型 1企业账户，2个人账户'
  private String status;// 账户状态 0 没有创建账户 1 已创建账户未设置交易密码 2开户成功
  private String remark;// 备注

  public String getCustId() {
    return custId;
  }

  public void setCustId(String custId) {
    this.custId = custId;
  }

  public String getCustName() {
    return custName;
  }

  public void setCustName(String custName) {
    this.custName = custName;
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

  public String getBankNo() {
    return bankNo;
  }

  public void setBankNo(String bankNo) {
    this.bankNo = bankNo;
  }

  public String getUserRole() {
    return userRole;
  }

  public void setUserRole(String userRole) {
    this.userRole = userRole;
  }

  public String getAccType() {
    return accType;
  }

  public void setAccType(String accType) {
    this.accType = accType;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }
}
