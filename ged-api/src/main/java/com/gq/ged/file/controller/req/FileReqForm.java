package com.gq.ged.file.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by wyq_tomorrow on 2018/1/19.
 */
@ApiModel
public class FileReqForm implements Serializable {
  @ApiModelProperty(value = "文件类型1图片2文件")
  private String fileType;
  @ApiModelProperty(value = "业务类型(1用户2订单3)")
  private String busType;
  @ApiModelProperty(value = "业务子类型")
  private String busSubType;
  @ApiModelProperty(value = "关联id")
  private Long refId;
  @ApiModelProperty(value = "资源url")
  private String fileUrl;

  public String getFileType() {
    return fileType;
  }

  public void setFileType(String fileType) {
    this.fileType = fileType;
  }

  public String getBusType() {
    return busType;
  }

  public void setBusType(String busType) {
    this.busType = busType;
  }

  public String getBusSubType() {
    return busSubType;
  }

  public void setBusSubType(String busSubType) {
    this.busSubType = busSubType;
  }

  public Long getRefId() {
    return refId;
  }

  public void setRefId(Long refId) {
    this.refId = refId;
  }

  public String getFileUrl() {
    return fileUrl;
  }

  public void setFileUrl(String fileUrl) {
    this.fileUrl = fileUrl;
  }
}
