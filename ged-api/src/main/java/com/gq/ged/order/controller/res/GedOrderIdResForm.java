package com.gq.ged.order.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by wrh on 2017/9/8.
 */
@ApiModel
public class GedOrderIdResForm {
  @ApiModelProperty(value = "订单编号")
  private String orderNo;
  @ApiModelProperty(value = "身份证实名标志(0否1是)")
  private  int idCardFlag;

  public String getOrderNo() {
    return orderNo;
  }

  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  public int getIdCardFlag() {
    return idCardFlag;
  }

  public void setIdCardFlag(int idCardFlag) {
    this.idCardFlag = idCardFlag;
  }
}
