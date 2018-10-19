package com.gq.ged.account.controller.req;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by wyq_tomorrow on 2018/1/19.
 */
@ApiModel
public class AccountExtListForm {
  @ApiModelProperty(value = "主键")
  private Long id;
  @ApiModelProperty(value = "身份证正面")
  private String idCardFaceUrl;
  @ApiModelProperty(value = "身份证反面")
  private String idCardBackUrl;
  @ApiModelProperty(value = "手持身份证")
  private String idCardHoldUrl;
  @ApiModelProperty(value = "营业执照")
  private String businessLicenceUrl;
  @ApiModelProperty(value = "开户许可证")
  private String accountsPermitsUrl;

  public String getAccountsPermitsUrl() {
    return accountsPermitsUrl;
  }

  public void setAccountsPermitsUrl(String accountsPermitsUrl) {
    this.accountsPermitsUrl = accountsPermitsUrl;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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
}
