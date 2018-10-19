package com.gq.ged.order.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by wrh on 2018/4/21.
 */
@ApiModel
public class PushGuarantRecordReqForm implements Serializable {
  private static final long serialVersionUID = 1L;
  @ApiModelProperty(value = "子订单编号")
  private String orderCode;
  @ApiModelProperty(value = "订单编号")
  private String masterOrderCode;
  @ApiModelProperty(value = "担保类型(1自有担保人2自有担保企业3合作企业)")
  private Integer guarantorType;
  @ApiModelProperty(value = "担保人手机号")
  private String  guarantMobile;
  @ApiModelProperty(value = "企业证件类型")
  private String type;
  @ApiModelProperty(value = "企业证件编码")
  private String code;
  @ApiModelProperty(value = "担保额度")
  private BigDecimal guaranteeAmount;
  @ApiModelProperty(value = "担保id")
  private String borrowGuarantorId;

  public String getOrderCode() {
    return orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  public String getMasterOrderCode() {
    return masterOrderCode;
  }

  public void setMasterOrderCode(String masterOrderCode) {
    this.masterOrderCode = masterOrderCode;
  }

  public Integer getGuarantorType() {
    return guarantorType;
  }

  public void setGuarantorType(Integer guarantorType) {
    this.guarantorType = guarantorType;
  }

  public String getGuarantMobile() {
    return guarantMobile;
  }

  public void setGuarantMobile(String guarantMobile) {
    this.guarantMobile = guarantMobile;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public BigDecimal getGuaranteeAmount() {
    return guaranteeAmount;
  }

  public void setGuaranteeAmount(BigDecimal guaranteeAmount) {
    this.guaranteeAmount = guaranteeAmount;
  }

  public String getBorrowGuarantorId() {
    return borrowGuarantorId;
  }

  public void setBorrowGuarantorId(String borrowGuarantorId) {
    this.borrowGuarantorId = borrowGuarantorId;
  }
}
