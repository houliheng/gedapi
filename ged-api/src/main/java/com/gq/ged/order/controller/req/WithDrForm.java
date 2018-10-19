package com.gq.ged.order.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by wrh on 2018/3/12.
 */
@ApiModel
public class WithDrForm implements Serializable {
  private static final long serialVersionUID = 1L;
  @ApiModelProperty(value = "系统代码")
  private String mchn;
  @ApiModelProperty(value = "交易流水号")
  private String  seqNo;
  @ApiModelProperty(value = "交易类型")
  private String tradeType;
  @ApiModelProperty(value = "合同ID")
  private String busiNo;
  @ApiModelProperty(value = "客户类型")
  private String custType;
  @ApiModelProperty(value = "申请类型")
  private String applyType;
  @ApiModelProperty(value = "合同编号")
  private String contractNo;
  @ApiModelProperty(value = "提现金额")
  private String amt;
  @ApiModelProperty(value = "提现时效")
  private String settleType;
  @ApiModelProperty(value = "签名")
  private String signature;
  @ApiModelProperty(value = "客户编号")
  private String custNo;

  public String getMchn() {
    return mchn;
  }

  public void setMchn(String mchn) {
    this.mchn = mchn;
  }

  public String getSeqNo() {
    return seqNo;
  }

  public void setSeqNo(String seqNo) {
    this.seqNo = seqNo;
  }

  public String getTradeType() {
    return tradeType;
  }

  public void setTradeType(String tradeType) {
    this.tradeType = tradeType;
  }

  public String getBusiNo() {
    return busiNo;
  }

  public void setBusiNo(String busiNo) {
    this.busiNo = busiNo;
  }

  public String getCustType() {
    return custType;
  }

  public void setCustType(String custType) {
    this.custType = custType;
  }

  public String getApplyType() {
    return applyType;
  }

  public void setApplyType(String applyType) {
    this.applyType = applyType;
  }

  public String getContractNo() {
    return contractNo;
  }

  public void setContractNo(String contractNo) {
    this.contractNo = contractNo;
  }

  public String getAmt() {
    return amt;
  }

  public void setAmt(String amt) {
    this.amt = amt;
  }

  public String getSettleType() {
    return settleType;
  }

  public void setSettleType(String settleType) {
    this.settleType = settleType;
  }

  public String getSignature() {
    return signature;
  }

  public void setSignature(String signature) {
    this.signature = signature;
  }

  public String getCustNo() {
    return custNo;
  }

  public void setCustNo(String custNo) {
    this.custNo = custNo;
  }
}
