package com.gq.ged.order.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by wrh on 2018/4/19.
 */
@ApiModel
public class GuarantConfirmReqForm implements Serializable {
  private static final long serialVersionUID = 1L;
  @ApiModelProperty(value = "订单编号")
  private String  orderNo;
  @ApiModelProperty(value = "确认标识0否1是")
  private int confirmFlag;
  @ApiModelProperty(value = "拒绝原因")
  private String msg;

  public String getOrderNo() {
    return orderNo;
  }

  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  public int getConfirmFlag() {
    return confirmFlag;
  }

  public void setConfirmFlag(int confirmFlag) {
    this.confirmFlag = confirmFlag;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }
}
