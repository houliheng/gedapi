package com.gq.ged.user.api.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by wyq_tomorrow on 2018/1/28.
 */
@ApiModel
public class UserCardResForm implements Serializable {
  private static final long serialVersionUID = 1L;
  @ApiModelProperty(value = "用户id")
  private Long userId;
  @ApiModelProperty(value = "社会统一代码")
  private String socialCreditCode;
  @ApiModelProperty(value = "营业执照编号")
  private String businessLicence;
  @ApiModelProperty(value = "组织机构代码")
  private String organizationCode;
  @ApiModelProperty(value = "税务号")
  private String taxCode;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
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
}
