package com.gq.ged.user.dao.model;

import java.math.BigDecimal;
import java.util.Date;

public class User {
    private Long id;

    private String username;

    private String getCustId;

    private String mobile;

    private String companyName;

    private String companyCardType;

    private String companyCardCode;

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

    private String idCardNum;

    private Integer idCardFlag;

    private Integer activeFlag;

    private Integer checkAccountFlag;

    private Integer companyAccountFlag;

    private String recommendCode;

    private Integer bindEmailFlag;

    private Integer userType;

    private Integer userRole;

    private BigDecimal guaranteeAmount;

    private Integer status;

    private Byte isEnabled;

    private Long createId;

    private Date createTime;

    private Long modifyId;

    private Date modifyTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getGetCustId() {
        return getCustId;
    }

    public void setGetCustId(String getCustId) {
        this.getCustId = getCustId == null ? null : getCustId.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
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

    public String getCompanyCardCode() {
        return companyCardCode;
    }

    public void setCompanyCardCode(String companyCardCode) {
        this.companyCardCode = companyCardCode == null ? null : companyCardCode.trim();
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

    public String getIdCardNum() {
        return idCardNum;
    }

    public void setIdCardNum(String idCardNum) {
        this.idCardNum = idCardNum == null ? null : idCardNum.trim();
    }

    public Integer getIdCardFlag() {
        return idCardFlag;
    }

    public void setIdCardFlag(Integer idCardFlag) {
        this.idCardFlag = idCardFlag;
    }

    public Integer getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Integer activeFlag) {
        this.activeFlag = activeFlag;
    }

    public Integer getCheckAccountFlag() {
        return checkAccountFlag;
    }

    public void setCheckAccountFlag(Integer checkAccountFlag) {
        this.checkAccountFlag = checkAccountFlag;
    }

    public Integer getCompanyAccountFlag() {
        return companyAccountFlag;
    }

    public void setCompanyAccountFlag(Integer companyAccountFlag) {
        this.companyAccountFlag = companyAccountFlag;
    }

    public String getRecommendCode() {
        return recommendCode;
    }

    public void setRecommendCode(String recommendCode) {
        this.recommendCode = recommendCode == null ? null : recommendCode.trim();
    }

    public Integer getBindEmailFlag() {
        return bindEmailFlag;
    }

    public void setBindEmailFlag(Integer bindEmailFlag) {
        this.bindEmailFlag = bindEmailFlag;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }

    public BigDecimal getGuaranteeAmount() {
        return guaranteeAmount;
    }

    public void setGuaranteeAmount(BigDecimal guaranteeAmount) {
        this.guaranteeAmount = guaranteeAmount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Byte getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Byte isEnabled) {
        this.isEnabled = isEnabled;
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
}