package com.gq.ged.version.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by wrh on 2017/10/19.
 */
@ApiModel
public class CheckVersionReqForm implements Serializable {

  @ApiModelProperty(value = "版本号")
  private String app_version_header;

  public String getApp_version_header() {
    return app_version_header;
  }

  public void setApp_version_header(String app_version_header) {
    this.app_version_header = app_version_header;
  }
}
