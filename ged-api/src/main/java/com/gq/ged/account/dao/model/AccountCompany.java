package com.gq.ged.account.dao.model;

import java.io.Serializable;
import java.util.Date;

public class AccountCompany implements Serializable{
    private Long id;

    private Long userId;

    private String custId;

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

    private String address;

    private String idCardFaceUrl;

    private String idCardBackUrl;

    private String idCardHoldUrl;

    private String businessLicenceUrl;

    private String accountsPermitsUrl;

    private Integer status;

    private Long createId;

    private Date createTime;

    private String accountVerifyInfo;

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

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId == null ? null : custId.trim();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getCompanyCardType() {
        return companyCardType;
    }

    public void setCompanyCardType(String companyCardType) {
        this.companyCardType = companyCardType == null ? null : companyCardType.trim();
    }

    public String getSocialCreditCode() {
        return socialCreditCode;
    }

    public void setSocialCreditCode(String socialCreditCode) {
        this.socialCreditCode = socialCreditCode == null ? null : socialCreditCode.trim();
    }

    public String getBusinessLicence() {
        return businessLicence;
    }

    public void setBusinessLicence(String businessLicence) {
        this.businessLicence = businessLicence == null ? null : businessLicence.trim();
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode == null ? null : organizationCode.trim();
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode == null ? null : taxCode.trim();
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName == null ? null : legalName.trim();
    }

    public String getLegalMobile() {
        return legalMobile;
    }

    public void setLegalMobile(String legalMobile) {
        this.legalMobile = legalMobile == null ? null : legalMobile.trim();
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
        this.legalCardNumber = legalCardNumber == null ? null : legalCardNumber.trim();
    }

    public String getCompanyContact() {
        return companyContact;
    }

    public void setCompanyContact(String companyContact) {
        this.companyContact = companyContact == null ? null : companyContact.trim();
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile == null ? null : contactMobile.trim();
    }

    public String getCompanyBankOfDeposit() {
        return companyBankOfDeposit;
    }

    public void setCompanyBankOfDeposit(String companyBankOfDeposit) {
        this.companyBankOfDeposit = companyBankOfDeposit == null ? null : companyBankOfDeposit.trim();
    }

    public String getCompanyBankOfDepositValue() {
        return companyBankOfDepositValue;
    }

    public void setCompanyBankOfDepositValue(String companyBankOfDepositValue) {
        this.companyBankOfDepositValue = companyBankOfDepositValue == null ? null : companyBankOfDepositValue.trim();
    }

    public String getCompanyAccount() {
        return companyAccount;
    }

    public void setCompanyAccount(String companyAccount) {
        this.companyAccount = companyAccount == null ? null : companyAccount.trim();
    }

    public String getCompanyBankBranchName() {
        return companyBankBranchName;
    }

    public void setCompanyBankBranchName(String companyBankBranchName) {
        this.companyBankBranchName = companyBankBranchName == null ? null : companyBankBranchName.trim();
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode == null ? null : provinceCode.trim();
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode == null ? null : areaCode.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getIdCardFaceUrl() {
        return idCardFaceUrl;
    }

    public void setIdCardFaceUrl(String idCardFaceUrl) {
        this.idCardFaceUrl = idCardFaceUrl == null ? null : idCardFaceUrl.trim();
    }

    public String getIdCardBackUrl() {
        return idCardBackUrl;
    }

    public void setIdCardBackUrl(String idCardBackUrl) {
        this.idCardBackUrl = idCardBackUrl == null ? null : idCardBackUrl.trim();
    }

    public String getIdCardHoldUrl() {
        return idCardHoldUrl;
    }

    public void setIdCardHoldUrl(String idCardHoldUrl) {
        this.idCardHoldUrl = idCardHoldUrl == null ? null : idCardHoldUrl.trim();
    }

    public String getBusinessLicenceUrl() {
        return businessLicenceUrl;
    }

    public void setBusinessLicenceUrl(String businessLicenceUrl) {
        this.businessLicenceUrl = businessLicenceUrl == null ? null : businessLicenceUrl.trim();
    }

    public String getAccountsPermitsUrl() {
        return accountsPermitsUrl;
    }

    public void setAccountsPermitsUrl(String accountsPermitsUrl) {
        this.accountsPermitsUrl = accountsPermitsUrl == null ? null : accountsPermitsUrl.trim();
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

    public String getAccountVerifyInfo() {
        return accountVerifyInfo;
    }

    public void setAccountVerifyInfo(String accountVerifyInfo) {
        this.accountVerifyInfo = accountVerifyInfo == null ? null : accountVerifyInfo.trim();
    }
}