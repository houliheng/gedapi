package com.gq.ged.order.pc.req;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Modifyed by zhaozb on 2018/7/4.
 */

@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true)
public class RpdateServiceFlagByOrderNoReqForm implements Serializable {
  private static final long serialVersionUID = 1L;
  @ApiModelProperty("订单编号")
  private String orderNo;
  @ApiModelProperty("实收担保费")
  private String factGuaranteeFee;
  @ApiModelProperty("实收保证金")
  private String factGuaranteeGold;
  @ApiModelProperty("实收服务费")
  private String factServiceFee;
  @ApiModelProperty("企业合作类型 0债股结合1非债股结合")
  private String type;

  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  public String getOrderNo() {
    return orderNo;
  }

  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  public String getFactGuaranteeFee() {
    return factGuaranteeFee;
  }

  public void setFactGuaranteeFee(String factGuaranteeFee) {
    this.factGuaranteeFee = factGuaranteeFee;
  }

  public String getFactGuaranteeGold() {
    return factGuaranteeGold;
  }

  public void setFactGuaranteeGold(String factGuaranteeGold) {
    this.factGuaranteeGold = factGuaranteeGold;
  }

  public String getFactServiceFee() {
    return factServiceFee;
  }

  public void setFactServiceFee(String factServiceFee) {
    this.factServiceFee = factServiceFee;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
