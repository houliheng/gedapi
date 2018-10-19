package com.gq.ged.account.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by wyq_tomorrow on 2018/1/29.
 */
@ApiModel
public class AccountResForm implements Serializable {
  private static final long serialVersionUID = 1L;
  @ApiModelProperty("开户行")
  private String bankOfDeposit;
  @ApiModelProperty("卡号")
  private String cardNum;
  /*
   * @ApiModelProperty("公司名称") private String companyName;
   */
  @ApiModelProperty("法人姓名")
  private String corporationName;

  private Integer status;
  @ApiModelProperty("企业户标识")
  private Integer companyStatus;

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getBankOfDeposit() {
    return bankOfDeposit;
  }

  public void setBankOfDeposit(String bankOfDeposit) {
    this.bankOfDeposit = bankOfDeposit;
  }

  public String getCardNum() {
    return cardNum;
  }

  public void setCardNum(String cardNum) {
    this.cardNum = cardNum;
  }

  public String getCorporationName() {
    return corporationName;
  }

  public void setCorporationName(String corporationName) {
    this.corporationName = corporationName;
  }

  public Integer getCompanyStatus() {
    return companyStatus;
  }

  public void setCompanyStatus(Integer companyStatus) {
    this.companyStatus = companyStatus;
  }
}
