package com.gq.ged.account.dao.model;

import java.io.Serializable;
import java.util.Date;

public class AccountVo  implements Serializable {
  private Long id;

  private String accountCode;

  private Long userId;

  private String corporationName;

  private String corporationPhone;

  private String corporationCardNum;

  private String companyName;

  private String socialCreditCode;

  private String companyBankOfDeposit;

  private String companyAccount;

  private String companyBankBranchName;

  private String provinceCode;

  private String cityCode;

  private String areaCode;

  private Integer status;

  private Long createId;

  private Date createTime;

  private Long modifyId;

  private Date modifyTime;

  private Integer accountFlag = 0;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getAccountCode() {
    return accountCode;
  }

  public void setAccountCode(String accountCode) {
    this.accountCode = accountCode;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getCorporationName() {
    return corporationName;
  }

  public void setCorporationName(String corporationName) {
    this.corporationName = corporationName;
  }

  public String getCorporationPhone() {
    return corporationPhone;
  }

  public void setCorporationPhone(String corporationPhone) {
    this.corporationPhone = corporationPhone;
  }

  public String getCorporationCardNum() {
    return corporationCardNum;
  }

  public void setCorporationCardNum(String corporationCardNum) {
    this.corporationCardNum = corporationCardNum;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public String getSocialCreditCode() {
    return socialCreditCode;
  }

  public void setSocialCreditCode(String socialCreditCode) {
    this.socialCreditCode = socialCreditCode;
  }

  public String getCompanyBankOfDeposit() {
    return companyBankOfDeposit;
  }

  public void setCompanyBankOfDeposit(String companyBankOfDeposit) {
    this.companyBankOfDeposit = companyBankOfDeposit;
  }

  public String getCompanyAccount() {
    return companyAccount;
  }

  public void setCompanyAccount(String companyAccount) {
    this.companyAccount = companyAccount;
  }

  public String getCompanyBankBranchName() {
    return companyBankBranchName;
  }

  public void setCompanyBankBranchName(String companyBankBranchName) {
    this.companyBankBranchName = companyBankBranchName;
  }

  public String getProvinceCode() {
    return provinceCode;
  }

  public void setProvinceCode(String provinceCode) {
    this.provinceCode = provinceCode;
  }

  public String getCityCode() {
    return cityCode;
  }

  public void setCityCode(String cityCode) {
    this.cityCode = cityCode;
  }

  public String getAreaCode() {
    return areaCode;
  }

  public void setAreaCode(String areaCode) {
    this.areaCode = areaCode;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Long getCreateId() {
    return createId;
  }

  public void setCreateId(Long createId) {
    this.createId = createId;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Long getModifyId() {
    return modifyId;
  }

  public void setModifyId(Long modifyId) {
    this.modifyId = modifyId;
  }

  public Date getModifyTime() {
    return modifyTime;
  }

  public void setModifyTime(Date modifyTime) {
    this.modifyTime = modifyTime;
  }

  public Integer getAccountFlag() {
    return accountFlag;
  }

  public void setAccountFlag(Integer accountFlag) {
    this.accountFlag = accountFlag;
  }
}
