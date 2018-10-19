package com.gq.ged.account.controller.res.v2;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by zhaozb on 2018/3/20.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel
public class EnterpriseAccount implements Serializable {
  @ApiModelProperty("法人客户ID")
  private String custId;
  @ApiModelProperty("法人客户手机号")
  private String mobile;
  @ApiModelProperty("法人姓名")
  private String custName;
  @ApiModelProperty("法人证件号")
  private String idCardNo;
  @ApiModelProperty("开户行名称")
  private String bankName;
  @ApiModelProperty("开户行编码")
  private String bankCode;
  @ApiModelProperty("对公银行卡号")
  private String bankcardNo;
  @ApiModelProperty("统一社会信用代码")
  private String unifiedCode;
  @ApiModelProperty("企业名称")
  private String enterpriseName;
  @ApiModelProperty("开户行许可证号")
  private String bankLicense;
  @ApiModelProperty("组织机构代码")
  private String orgNo;
  @ApiModelProperty("营业执照编号")
  private String businessLicense;
  @ApiModelProperty("税务登记号")
  private String taxNo;
  @ApiModelProperty("机构信用代码")
  private String creditCode;
  @ApiModelProperty("企业联系人")
  private String contact;
  @ApiModelProperty("联系人手机号")
  private String contactPhone;
  @ApiModelProperty("审核状态")
  private Integer auditStatus;
  @ApiModelProperty("拍摄企业营业执照图片")
  private String licenseImg;
  @ApiModelProperty("法人身份证正面")
  private String cardFaceImg;
  @ApiModelProperty("法人身份证背面")
  private String cardBackImg;
  @ApiModelProperty("拍摄法人手持身份证")
  private String cardHandImg;
  @ApiModelProperty("开户许可证")
  private String openAccount;
  @ApiModelProperty("备用字段")
  private String remark;

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

  public String getCustName() {
    return custName;
  }

  public void setCustName(String custName) {
    this.custName = custName;
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

  public String getBankcardNo() {
    return bankcardNo;
  }

  public void setBankcardNo(String bankcardNo) {
    this.bankcardNo = bankcardNo;
  }

  public String getUnifiedCode() {
    return unifiedCode;
  }

  public void setUnifiedCode(String unifiedCode) {
    this.unifiedCode = unifiedCode;
  }

  public String getEnterpriseName() {
    return enterpriseName;
  }

  public void setEnterpriseName(String enterpriseName) {
    this.enterpriseName = enterpriseName;
  }

  public String getBankLicense() {
    return bankLicense;
  }

  public void setBankLicense(String bankLicense) {
    this.bankLicense = bankLicense;
  }

  public String getOrgNo() {
    return orgNo;
  }

  public void setOrgNo(String orgNo) {
    this.orgNo = orgNo;
  }

  public String getBusinessLicense() {
    return businessLicense;
  }

  public void setBusinessLicense(String businessLicense) {
    this.businessLicense = businessLicense;
  }

  public String getTaxNo() {
    return taxNo;
  }

  public void setTaxNo(String taxNo) {
    this.taxNo = taxNo;
  }

  public String getCreditCode() {
    return creditCode;
  }

  public void setCreditCode(String creditCode) {
    this.creditCode = creditCode;
  }

  public String getContact() {
    return contact;
  }

  public void setContact(String contact) {
    this.contact = contact;
  }

  public String getContactPhone() {
    return contactPhone;
  }

  public void setContactPhone(String contactPhone) {
    this.contactPhone = contactPhone;
  }

  public Integer getAuditStatus() {
    return auditStatus;
  }

  public void setAuditStatus(Integer auditStatus) {
    this.auditStatus = auditStatus;
  }

  public String getLicenseImg() {
    return licenseImg;
  }

  public void setLicenseImg(String licenseImg) {
    this.licenseImg = licenseImg;
  }

  public String getCardFaceImg() {
    return cardFaceImg;
  }

  public void setCardFaceImg(String cardFaceImg) {
    this.cardFaceImg = cardFaceImg;
  }

  public String getCardBackImg() {
    return cardBackImg;
  }

  public void setCardBackImg(String cardBackImg) {
    this.cardBackImg = cardBackImg;
  }

  public String getCardHandImg() {
    return cardHandImg;
  }

  public void setCardHandImg(String cardHandImg) {
    this.cardHandImg = cardHandImg;
  }

  public String getOpenAccount() {
    return openAccount;
  }

  public void setOpenAccount(String openAccount) {
    this.openAccount = openAccount;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }
}
