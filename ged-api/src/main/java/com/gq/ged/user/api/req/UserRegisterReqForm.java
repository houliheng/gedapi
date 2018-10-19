package com.gq.ged.user.api.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by wyq_tomorrow on 2018/1/22.
 */
@ApiModel
public class UserRegisterReqForm implements Serializable {
  private static final long serialVersionUID = 1L;
  @ApiModelProperty(value = "注册类型0主借人1法人2社会统一代码")
  private String registerType;
  @ApiModelProperty(value = "手机号")
  private String mobile;
  @ApiModelProperty(value = "证件类型")
  private String type;
  @ApiModelProperty(value = "证件编码")
  private String code;
  @ApiModelProperty(value = "0个人1企业")
  private Integer userType;
  @ApiModelProperty(value = "0借款企业1自有担保人2自有担保企业3合作企业4借款人")
  private Integer userRole;
  @ApiModelProperty(value = "担保金额")
  private BigDecimal guaranteeAmount;
  @ApiModelProperty(value = "老用户")
  private Integer status;

  @ApiModelProperty(value = "银行卡号")
  private String bankCode;

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getBankCode() {
    return bankCode;
  }

  public void setBankCode(String bankCode) {
    this.bankCode = bankCode;
  }

  public Integer getUserType() {
    return userType;
  }

  public void setUserType(Integer userType) {
    this.userType = userType;
  }

  public String getRegisterType() {
    return registerType;
  }

  public void setRegisterType(String registerType) {
    this.registerType = registerType;
  }

  public BigDecimal getGuaranteeAmount() {
    return guaranteeAmount;
  }

  public void setGuaranteeAmount(BigDecimal guaranteeAmount) {
    this.guaranteeAmount = guaranteeAmount;
  }

  public Integer getUserRole() {
    return userRole;
  }

  public void setUserRole(Integer userRole) {
    this.userRole = userRole;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }
}
