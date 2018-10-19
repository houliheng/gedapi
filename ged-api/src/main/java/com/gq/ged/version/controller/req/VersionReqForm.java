package com.gq.ged.version.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by wrh on 2017/9/18.
 */
@ApiModel
public class VersionReqForm implements Serializable {

  @ApiModelProperty(value = "版本号")
  private String version;
  @ApiModelProperty(value = "设备号")
  private String device;

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getDevice() {
    return device;
  }

  public void setDevice(String device) {
    this.device = device;
  }
}
