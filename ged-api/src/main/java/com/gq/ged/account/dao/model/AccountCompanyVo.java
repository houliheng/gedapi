package com.gq.ged.account.dao.model;

import java.io.Serializable;
import java.util.Date;

public class AccountCompanyVo  implements Serializable {
  private Long id;

  private Long userId;

  private String companyName;

  private String companyCardType;

  private String socialCreditCode;

  private String businessLicence;

  private String organizationCode;

  private String taxCode;

  private String legalName;

  private String legalMobile;

  private Integer legalCardType;

  private String legalCardNumber;

  private String companyContact;

  private String contactMobile;

  private String companyBankOfDeposit;

  private String companyBankOfDepositValue;

  private String companyAccount;

  private String companyBankBranchName;

  private String provinceCode;

  private String cityCode;

  private String areaCode;

  private String idCardFaceUrl;

  private String idCardBackUrl;

  private String idCardHoldUrl;

  private String businessLicenceUrl;

  private String accountsPermitsUrl;

  private Long createId;

  private Date createTime;

  private Integer accountFlag = 0;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public String getCompanyCardType() {
    return companyCardType;
  }

  public void setCompanyCardType(String companyCardType) {
    this.companyCardType = companyCardType;
  }

  public String getSocialCreditCode() {
    return socialCreditCode;
  }

  public void setSocialCreditCode(String socialCreditCode) {
    this.socialCreditCode = socialCreditCode;
  }

  public String getBusinessLicence() {
    return businessLicence;
  }

  public void setBusinessLicence(String businessLicence) {
    this.businessLicence = businessLicence;
  }

  public String getOrganizationCode() {
    return organizationCode;
  }

  public void setOrganizationCode(String organizationCode) {
    this.organizationCode = organizationCode;
  }

  public String getTaxCode() {
    return taxCode;
  }

  public void setTaxCode(String taxCode) {
    this.taxCode = taxCode;
  }

  public String getLegalName() {
    return legalName;
  }

  public void setLegalName(String legalName) {
    this.legalName = legalName;
  }

  public String getLegalMobile() {
    return legalMobile;
  }

  public void setLegalMobile(String legalMobile) {
    this.legalMobile = legalMobile;
  }

  public Integer getLegalCardType() {
    return legalCardType;
  }

  public void setLegalCardType(Integer legalCardType) {
    this.legalCardType = legalCardType;
  }

  public String getLegalCardNumber() {
    return legalCardNumber;
  }

  public void setLegalCardNumber(String legalCardNumber) {
    this.legalCardNumber = legalCardNumber;
  }

  public String getCompanyContact() {
    return companyContact;
  }

  public void setCompanyContact(String companyContact) {
    this.companyContact = companyContact;
  }

  public String getContactMobile() {
    return contactMobile;
  }

  public void setContactMobile(String contactMobile) {
    this.contactMobile = contactMobile;
  }

  public String getCompanyBankOfDeposit() {
    return companyBankOfDeposit;
  }

  public void setCompanyBankOfDeposit(String companyBankOfDeposit) {
    this.companyBankOfDeposit = companyBankOfDeposit;
  }

  public String getCompanyBankOfDepositValue() {
    return companyBankOfDepositValue;
  }

  public void setCompanyBankOfDepositValue(String companyBankOfDepositValue) {
    this.companyBankOfDepositValue = companyBankOfDepositValue;
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

  public String getIdCardFaceUrl() {
    return idCardFaceUrl;
  }

  public void setIdCardFaceUrl(String idCardFaceUrl) {
    this.idCardFaceUrl = idCardFaceUrl;
  }

  public String getIdCardBackUrl() {
    return idCardBackUrl;
  }

  public void setIdCardBackUrl(String idCardBackUrl) {
    this.idCardBackUrl = idCardBackUrl;
  }

  public String getIdCardHoldUrl() {
    return idCardHoldUrl;
  }

  public void setIdCardHoldUrl(String idCardHoldUrl) {
    this.idCardHoldUrl = idCardHoldUrl;
  }

  public String getBusinessLicenceUrl() {
    return businessLicenceUrl;
  }

  public void setBusinessLicenceUrl(String businessLicenceUrl) {
    this.businessLicenceUrl = businessLicenceUrl;
  }

  public String getAccountsPermitsUrl() {
    return accountsPermitsUrl;
  }

  public void setAccountsPermitsUrl(String accountsPermitsUrl) {
    this.accountsPermitsUrl = accountsPermitsUrl;
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

  public Integer getAccountFlag() {
    return accountFlag;
  }

  public void setAccountFlag(Integer accountFlag) {
    this.accountFlag = accountFlag;
  }
}
