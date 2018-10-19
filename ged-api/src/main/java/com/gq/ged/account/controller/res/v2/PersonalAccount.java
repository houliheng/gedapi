package com.gq.ged.account.controller.res.v2;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by zhaozb on 2018/3/19.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonalAccount implements Serializable {
  @ApiModelProperty("客户银行预留手机号")
  private String mobile;
  @ApiModelProperty("身份证号")
  private String idCardNo;
  @ApiModelProperty("客户真实姓名")
  private String custName;
  @ApiModelProperty("开户行名称")
  private String bankName;
  @ApiModelProperty("银行编码")
  private String bankCode;
  @ApiModelProperty("银行卡号")
  private String bankcardNo;
  @ApiModelProperty("备用字段")
  private String remark;
  @ApiModelProperty("客户ID")
  private String custId;


  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getIdCardNo() {
    return idCardNo;
  }

  public void setIdCardNo(String idCardNo) {
    this.idCardNo = idCardNo;
  }

  public String getCustName() {
    return custName;
  }

  public void setCustName(String custName) {
    this.custName = custName;
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

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getCustId() {
    return custId;
  }

  public void setCustId(String custId) {
    this.custId = custId;
  }
}
