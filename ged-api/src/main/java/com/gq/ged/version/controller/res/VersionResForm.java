package com.gq.ged.version.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by wrh on 2017/9/18.
 */
@ApiModel
public class VersionResForm implements Serializable{
  @ApiModelProperty(value = "版本号")
  private String versionCode;
  @ApiModelProperty(value = "渠道")
  private String channel;
  @ApiModelProperty(value = "更新url")
  private String updateUrl;
  @ApiModelProperty(value = "更新信息")
  private String updateMsg;
  @ApiModelProperty(value = "状态")
  private Integer status;
  @ApiModelProperty(value = "标题")
  private String title;

  public String getVersionCode() {
    return versionCode;
  }

  public void setVersionCode(String versionCode) {
    this.versionCode = versionCode;
  }

  public String getChannel() {
    return channel;
  }

  public void setChannel(String channel) {
    this.channel = channel;
  }

  public String getUpdateUrl() {
    return updateUrl;
  }

  public void setUpdateUrl(String updateUrl) {
    this.updateUrl = updateUrl;
  }

  public String getUpdateMsg() {
    return updateMsg;
  }

  public void setUpdateMsg(String updateMsg) {
    this.updateMsg = updateMsg;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
