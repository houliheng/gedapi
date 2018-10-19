package com.gq.ged.order.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wyq_tomorrow on 2018/3/7.
 */
@ApiModel
public class PaymentPlanReqForm implements Serializable {
  private static final long serialVersionUID = 1L;
  @ApiModelProperty(value = "订单编号")
  private String orderCode;
  @ApiModelProperty(value = "授信类型")
  private String creditType;
  @ApiModelProperty(value = "操作标识")
  private String operatorFlag;
  @ApiModelProperty(value = "合同List")
  private List<ContractForm> list;

  public String getOrderCode() {
    return orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  public String getCreditType() {
    return creditType;
  }

  public void setCreditType(String creditType) {
    this.creditType = creditType;
  }

  public String getOperatorFlag() {
    return operatorFlag;
  }

  public void setOperatorFlag(String operatorFlag) {
    this.operatorFlag = operatorFlag;
  }

  public List<ContractForm> getList() {
    return list;
  }

  public void setList(List<ContractForm> list) {
    this.list = list;
  }
}
