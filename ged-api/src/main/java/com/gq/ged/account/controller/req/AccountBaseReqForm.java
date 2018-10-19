package com.gq.ged.account.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by wyq_tomorrow on 2018/1/19.
 */
@ApiModel
public class AccountBaseReqForm implements Serializable {

  @ApiModelProperty(value = "主键")
  private String id;
  @ApiModelProperty(value = "企业名称")
  private String companyName;
  @ApiModelProperty(value = "企业证件类型")
  private String companyCardType;
  @ApiModelProperty(value = "社会统一代码")
  private String socialCreditCode;
  @ApiModelProperty(value = "营业执照编号")
  private String businessLicence;
  @ApiModelProperty(value = "组织机构代码")
  private String organizationCode;
  @ApiModelProperty(value = "税务登记号")
  private String taxCode;
  @ApiModelProperty(value = "法人名称")
  private String legalName;
  @ApiModelProperty(value = "法人手机号")
  private String legalMobile;
  @ApiModelProperty(value = "法人证件类型")
  private Integer legalCardType;
  @ApiModelProperty(value = "法人证件号码")
  private String legalCardNumber;
  @ApiModelProperty(value = "联系人姓名")
  private String companyContact;
  @ApiModelProperty(value = "联系人电话")
  private String contactMobile;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
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
}
