package com.gq.ged.account.controller.req.v2;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

public class OnlineBankingRechargeReqForm implements Serializable{
  @ApiModelProperty("法人姓名")
  private String name;
  @ApiModelProperty("法人身份证号")
  private String cretNO;
  @ApiModelProperty("手机号")
  private String mobile;
  @ApiModelProperty("银行卡号")
  private String bankNo;




  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCretNO() {
    return cretNO;
  }

  public void setCretNO(String cretNO) {
    this.cretNO = cretNO;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getBankNo() {
    return bankNo;
  }

  public void setBankNo(String bankNo) {
    this.bankNo = bankNo;
  }
}