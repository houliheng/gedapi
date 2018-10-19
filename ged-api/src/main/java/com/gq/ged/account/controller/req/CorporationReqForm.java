package com.gq.ged.account.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * Created by wyq_tomorrow on 2018/1/31.
 */
@ApiModel
public class CorporationReqForm implements Serializable {
  private static final long serialVersionUID = 1L;
  @ApiModelProperty("企业名称")
  private String companyName;
  @ApiModelProperty("法人姓名")
  private String corporationName;
  @ApiModelProperty("法人身份证")
  private String corporationCardNum;
  @ApiModelProperty("手机号")
  private String mobile;
  @ApiModelProperty("开户行")
  private String bankOfDeposit;
  @ApiModelProperty("银行卡号")
  private String bankNumber;
  @ApiModelProperty("社会统一代码")
  private String socialCreditCode;
  @ApiModelProperty("交易类型")
  private String tradeType;
  @ApiModelProperty("开户支行名称")
  private String companyBankBranchName;
  @ApiModelProperty("客户ID")
  private String custId;
  /*
   * @ApiModelProperty("11020024") private String tradeType;
   * 
   * @ApiModelProperty("04543666UDHF") private String mchn;
   */
  @ApiModelProperty("省")
  private String provinceId;
  @ApiModelProperty("市")
  private String cityId;
  @ApiModelProperty("区")
  private String areaId;

  public String getCustId() {
    return custId;
  }

  public void setCustId(String custId) {
    this.custId = custId;
  }

  public String getCompanyBankBranchName() {
    return companyBankBranchName;
  }

  public void setCompanyBankBranchName(String companyBankBranchName) {
    this.companyBankBranchName = companyBankBranchName;
  }

  public String getTradeType() {
    return tradeType;
  }

  public void setTradeType(String tradeType) {
    this.tradeType = tradeType;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public String getCorporationName() {
    return corporationName;
  }

  public void setCorporationName(String corporationName) {
    this.corporationName = corporationName;
  }

  public String getCorporationCardNum() {
    return corporationCardNum;
  }

  public void setCorporationCardNum(String corporationCardNum) {
    this.corporationCardNum = corporationCardNum;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getBankOfDeposit() {
    return bankOfDeposit;
  }

  public void setBankOfDeposit(String bankOfDeposit) {
    this.bankOfDeposit = bankOfDeposit;
  }

  public String getBankNumber() {
    return bankNumber;
  }

  public void setBankNumber(String bankNumber) {
    this.bankNumber = bankNumber;
  }

  public String getProvinceId() {
    return provinceId;
  }

  public void setProvinceId(String provinceId) {
    this.provinceId = provinceId;
  }

  public String getCityId() {
    return cityId;
  }

  public void setCityId(String cityId) {
    this.cityId = cityId;
  }

  public String getAreaId() {
    return areaId;
  }

  public void setAreaId(String areaId) {
    this.areaId = areaId;
  }

  public String getSocialCreditCode() {
    return socialCreditCode;
  }

  public void setSocialCreditCode(String socialCreditCode) {
    this.socialCreditCode = socialCreditCode;
  }
}
