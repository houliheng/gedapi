package com.gq.ged.account.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by wyq_tomorrow on 2018/1/19.
 */
@ApiModel
public class AccountBankReqForm implements Serializable {
  @ApiModelProperty(value = "主键")
  private Long id;
  @ApiModelProperty(value = "企业开户行")
  private String companyBankOfDeposit;
  @ApiModelProperty(value = "企业开户行中文名")
  private String companyBankOfDepositValue;
  @ApiModelProperty(value = "企业对公账户")
  private String companyAccount;
  @ApiModelProperty(value = "开户行支行名称")
  private String companyBankBranchName;
  @ApiModelProperty(value = "省代码")
  private String provinceCode;
  @ApiModelProperty(value = "市代码")
  private String cityCode;
  @ApiModelProperty(value = "区代码")
  private String areaCode;

  public String getCompanyBankOfDepositValue() {
    return companyBankOfDepositValue;
  }

  public void setCompanyBankOfDepositValue(String companyBankOfDepositValue) {
    this.companyBankOfDepositValue = companyBankOfDepositValue;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCompanyBankOfDeposit() {
    return companyBankOfDeposit;
  }

  public void setCompanyBankOfDeposit(String companyBankOfDeposit) {
    this.companyBankOfDeposit = companyBankOfDeposit;
  }

  public String getAreaCode() {
    return areaCode;
  }

  public void setAreaCode(String areaCode) {
    this.areaCode = areaCode;
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
}
