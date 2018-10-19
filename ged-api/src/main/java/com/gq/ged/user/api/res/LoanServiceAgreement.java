package com.gq.ged.user.api.res;

import java.util.List;

/**
 * Created by yxh on 2018/5/28.
 */
public class LoanServiceAgreement {
  // 借款服务协议变量

  // 合同编号 contractNo
  private String contractNo;
  // 合同签订地 省province 市city 县county
  // private String ;
  // 借款人 jkCustName
  private String jkCustName;
  // 冠易贷注册ID gedCustName
  private String gedCustName;
  // 法定代表人 jkCustNameDb
  private String jkCustNameDb;
  // 身份证号 certNo
  private String certNo;
  // 联系地址 custAddress
  private String custAddress;
  // 手机号码 custPhone
  private String custPhone;
  // 电子邮箱 custEmail
  private String custEmail;
  // 借款金额 loanAmt
  private String loanAmt;
  // 借款用途 loanReason
  private String loanReason;
  // 平台服务费 小写serviceFee 大写serviceFeeUp
  private String serviceFee;
  private String serviceFeeUpper;//平台服务费大写金额
  //咨询服务费
  private String consultServiceFee;
  private String consultServiceFeeUpper;//咨询服务费大写
  // 账户管理费 小写manageFee 大写managerFeeUp
  private String manageFee;
  private String accountManageFeeUpper;//账户管理费大写金额
  // 支付账户管理费方式 payType
  private String payType;
  // 支付管理费期数 periodCount
  private String periodCount;

  // 还款计划
  private List<RepaymentInfo> list;

  // 账户 account
  private String account;
  // 开户银行 bankName
  private String bankName;
  // 账号 bankNo
  private String bankNo;
  // 授权代表人 representName
  private String representName;
  // 签订日期 signDate
  private String signDate;
  private String province;//省
  private String city;//市
  private String area;//区
  // jkfwContractType 201
  private String jkfwContractType = "201";
  // jkfwVersion v1.0
  private String jkfwVersion = "v1.2";

  public String getConsultServiceFee() {
    return consultServiceFee;
  }

  public void setConsultServiceFee(String consultServiceFee) {
    this.consultServiceFee = consultServiceFee;
  }

  public String getConsultServiceFeeUpper() {
    return consultServiceFeeUpper;
  }

  public void setConsultServiceFeeUpper(String consultServiceFeeUpper) {
    this.consultServiceFeeUpper = consultServiceFeeUpper;
  }

  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getArea() {
    return area;
  }

  public void setArea(String area) {
    this.area = area;
  }

  public String getServiceFeeUpper() {
    return serviceFeeUpper;
  }

  public void setServiceFeeUpper(String serviceFeeUpper) {
    this.serviceFeeUpper = serviceFeeUpper;
  }

  public String getAccountManageFeeUpper() {
    return accountManageFeeUpper;
  }

  public void setAccountManageFeeUpper(String accountManageFeeUpper) {
    this.accountManageFeeUpper = accountManageFeeUpper;
  }

  public String getJkfwContractType() {
    return jkfwContractType;
  }

  public void setJkfwContractType(String jkfwContractType) {
    this.jkfwContractType = jkfwContractType;
  }

  public String getJkfwVersion() {
    return jkfwVersion;
  }

  public void setJkfwVersion(String jkfwVersion) {
    this.jkfwVersion = jkfwVersion;
  }

  public String getContractNo() {
    return contractNo;
  }

  public void setContractNo(String contractNo) {
    this.contractNo = contractNo;
  }

  public String getJkCustName() {
    return jkCustName;
  }

  public void setJkCustName(String jkCustName) {
    this.jkCustName = jkCustName;
  }

  public String getGedCustName() {
    return gedCustName;
  }

  public void setGedCustName(String gedCustName) {
    this.gedCustName = gedCustName;
  }

  public String getJkCustNameDb() {
    return jkCustNameDb;
  }

  public void setJkCustNameDb(String jkCustNameDb) {
    this.jkCustNameDb = jkCustNameDb;
  }

  public String getCertNo() {
    return certNo;
  }

  public void setCertNo(String certNo) {
    this.certNo = certNo;
  }

  public String getCustAddress() {
    return custAddress;
  }

  public void setCustAddress(String custAddress) {
    this.custAddress = custAddress;
  }

  public String getCustPhone() {
    return custPhone;
  }

  public void setCustPhone(String custPhone) {
    this.custPhone = custPhone;
  }

  public String getCustEmail() {
    return custEmail;
  }

  public void setCustEmail(String custEmail) {
    this.custEmail = custEmail;
  }

  public String getLoanAmt() {
    return loanAmt;
  }

  public void setLoanAmt(String loanAmt) {
    this.loanAmt = loanAmt;
  }

  public String getLoanReason() {
    return loanReason;
  }

  public void setLoanReason(String loanReason) {
    this.loanReason = loanReason;
  }

  public String getServiceFee() {
    return serviceFee;
  }

  public void setServiceFee(String serviceFee) {
    this.serviceFee = serviceFee;
  }

  public String getManageFee() {
    return manageFee;
  }

  public void setManageFee(String manageFee) {
    this.manageFee = manageFee;
  }

  public String getPayType() {
    return payType;
  }

  public void setPayType(String payType) {
    this.payType = payType;
  }

  public String getPeriodCount() {
    return periodCount;
  }

  public void setPeriodCount(String periodCount) {
    this.periodCount = periodCount;
  }

  public List<RepaymentInfo> getList() {
    return list;
  }

  public void setList(List<RepaymentInfo> list) {
    this.list = list;
  }

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public String getBankName() {
    return bankName;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  public String getBankNo() {
    return bankNo;
  }

  public void setBankNo(String bankNo) {
    this.bankNo = bankNo;
  }

  public String getRepresentName() {
    return representName;
  }

  public void setRepresentName(String representName) {
    this.representName = representName;
  }

  public String getSignDate() {
    return signDate;
  }

  public void setSignDate(String signDate) {
    this.signDate = signDate;
  }
}
