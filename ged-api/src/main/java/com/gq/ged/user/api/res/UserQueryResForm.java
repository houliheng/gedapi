package com.gq.ged.user.api.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by wyq_tomorrow on 2018/1/29.
 */
@ApiModel
public class UserQueryResForm implements Serializable {
  @ApiModelProperty(value = "类型")
  private String type;
  @ApiModelProperty(value = "code编码")
  private String code;
  @ApiModelProperty(value = "手机号")
  private String mobile;
  @ApiModelProperty(value = "返回结果")
  private Integer resultCode;
  @ApiModelProperty(value = "消息")
  private String message;

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

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public Integer getResultCode() {
    return resultCode;
  }

  public void setResultCode(Integer resultCode) {
    this.resultCode = resultCode;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
