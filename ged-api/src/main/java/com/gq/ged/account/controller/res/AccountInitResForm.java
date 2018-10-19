package com.gq.ged.account.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by wyq_tomorrow on 2018/2/7.
 */
@ApiModel
public class AccountInitResForm implements Serializable {
  private static final long serialVersionUID = 1L;
  @ApiModelProperty("企业名称")
  private String companyName;
  @ApiModelProperty("法人姓名")
  private String corporationName;
  @ApiModelProperty("法人身份证")
  private String corporationCardNum;
  @ApiModelProperty("社会统一代码")
  private String socialCreditCode;


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
}
