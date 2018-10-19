package com.gq.ged.user.api.res;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by wyq_tomorrow on 2017/8/21.
 */
@ApiModel
public class UserResForm implements Serializable {
  /**
   * @Fields serialVersionUID : TODO
   */
  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "token")
  private String token;

  @ApiModelProperty(value = "手机号")
  private String mobile;

  @ApiModelProperty(value = "用户名称")
  private String userName;

  @ApiModelProperty(value = "社会统一代码")
  private String socialCreditCode;

  @ApiModelProperty(value = "公司名称")
  private String companyName;

  @ApiModelProperty(value = "激活标志")
  private Integer activeFlag;

  @ApiModelProperty(value = "openid")
  private String openid;

  @ApiModelProperty(value = "userRole")
  private Integer userRole;

  @ApiModelProperty(value = "userType")
  private Integer userType;

  @ApiModelProperty(value = "checkAccountFlag")
  private Integer checkAccountFlag;

  @ApiModelProperty(value = "companyAccountFlag")
  private Integer companyAccountFlag;

  @ApiModelProperty(value = "sysFlag")
  private Integer sysFlag;

  @ApiModelProperty(value = "status") // 1老用户
  private Integer status;

  private int code;// 返回码

  private String msg;// 返回描述

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
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

  public Integer getActiveFlag() {
    return activeFlag;
  }

  public void setActiveFlag(Integer activeFlag) {
    this.activeFlag = activeFlag;
  }

  public String getOpenid() {
    return openid;
  }

  public void setOpenid(String openid) {
    this.openid = openid;
  }

  public Integer getUserRole() {
    return userRole;
  }

  public void setUserRole(Integer userRole) {
    this.userRole = userRole;
  }

  public Integer getUserType() {
    return userType;
  }

  public void setUserType(Integer userType) {
    this.userType = userType;
  }

  public Integer getCheckAccountFlag() {
    return checkAccountFlag;
  }

  public void setCheckAccountFlag(Integer checkAccountFlag) {
    this.checkAccountFlag = checkAccountFlag;
  }

  public Integer getCompanyAccountFlag() {
    return companyAccountFlag;
  }

  public void setCompanyAccountFlag(Integer companyAccountFlag) {
    this.companyAccountFlag = companyAccountFlag;
  }

  public Integer getSysFlag() {
    return sysFlag;
  }

  public void setSysFlag(Integer sysFlag) {
    this.sysFlag = sysFlag;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }
}
