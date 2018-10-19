package com.gq.ged.message.api.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by wyq_tomorrow on 2017/8/22.
 */
@ApiModel
public class MessageHttpReqForm implements Serializable {
  @ApiModelProperty(value = "手机号")
  private String phoneNo;
  @ApiModelProperty(value = "模板")
  private String tempCode;
  @ApiModelProperty(value = "发送类型")
  private String sendType;

  public String getPhoneNo() {
    return phoneNo;
  }

  public void setPhoneNo(String phoneNo) {
    this.phoneNo = phoneNo;
  }

  public String getTempCode() {
    return tempCode;
  }

  public void setTempCode(String tempCode) {
    this.tempCode = tempCode;
  }

  public String getSendType() {
    return sendType;
  }

  public void setSendType(String sendType) {
    this.sendType = sendType;
  }
}
