package com.gq.ged.user.api.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by wyq_tomorrow on 2018/1/23.
 */
@ApiModel
public class UserQueryReqForm implements Serializable {
  private static final long serialVersionUID = 1L;
  @ApiModelProperty(value = "手机号")
  private String mobile;
  @ApiModelProperty(value = "0借款人1自有担保人2自有担保企业3合作企业")
  private Integer userRole;
  @ApiModelProperty(value = "类型")
  private String type;
  @ApiModelProperty(value = "code编码")
  private String code;

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
