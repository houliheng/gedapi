package com.gq.ged.order.pc.req;

import java.io.Serializable;
import java.util.List;

import com.gq.ged.order.controller.req.ContractForm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by wyq_tomorrow on 2018/3/7.
 */
@ApiModel
public class RePaymentPlanReqForm implements Serializable {
  private static final long serialVersionUID = 1L;
  @ApiModelProperty(value = "订单编号")
  private String orderCode;

  public String getOrderCode() {
    return orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }


}
