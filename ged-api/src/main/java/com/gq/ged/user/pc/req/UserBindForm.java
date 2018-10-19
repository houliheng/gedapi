package com.gq.ged.user.pc.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by wyq_tomorrow on 2018/4/23.
 */
@ApiModel
public class UserBindForm implements Serializable {
  @ApiModelProperty("手机号")
  private String mobile;
  @ApiModelProperty("验证码")
  private String code;

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }
}
