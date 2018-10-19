package com.gq.ged.user.api.req;

import com.gq.ged.common.enums.ChanelStyle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by wyq_tomorrow on 2018/1/30.
 */
@ApiModel
public class PwdReqForm implements Serializable {
  private static final long serialVersionUID = 1L;
  @ApiModelProperty("老密码")
  private String password;
  @ApiModelProperty("新密码")
  private String confirmPassword;
  @ApiModelProperty("手机号")
  private String mobile;
  @ApiModelProperty("类型")
  private ChanelStyle chanelStyle;

  public ChanelStyle getChanelStyle() {
    return chanelStyle;
  }

  public void setChanelStyle(ChanelStyle chanelStyle) {
    this.chanelStyle = chanelStyle;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }
}
