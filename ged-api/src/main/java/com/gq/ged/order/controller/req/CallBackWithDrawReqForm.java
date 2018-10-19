package com.gq.ged.order.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by wrh on 2018/3/12.
 */
@ApiModel
public class CallBackWithDrawReqForm implements Serializable {
  private static final long serialVersionUID = 1L;
  @ApiModelProperty(value = "系统代码")
  private String mchn;
  @ApiModelProperty(value = "交易流水号")
  private String  seq_no;
  @ApiModelProperty(value = "交易类型")
  private String trade_type;
  @ApiModelProperty(value = "合同ID")
  private String busi_no;
  @ApiModelProperty(value = "提现金额")
  private BigDecimal real_trade_amount;
  @ApiModelProperty(value = "返回码")
  private String resp_code;
  @ApiModelProperty(value = "返回消息内容")
  private String resp_msg;

  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  public String getMchn() {
    return mchn;
  }

  public void setMchn(String mchn) {
    this.mchn = mchn;
  }

  public String getSeq_no() {
    return seq_no;
  }

  public void setSeq_no(String seq_no) {
    this.seq_no = seq_no;
  }

  public String getTrade_type() {
    return trade_type;
  }

  public void setTrade_type(String trade_type) {
    this.trade_type = trade_type;
  }

  public String getBusi_no() {
    return busi_no;
  }

  public void setBusi_no(String busi_no) {
    this.busi_no = busi_no;
  }

  public BigDecimal getReal_trade_amount() {
    return real_trade_amount;
  }

  public void setReal_trade_amount(BigDecimal real_trade_amount) {
    this.real_trade_amount = real_trade_amount;
  }

  public String getResp_code() {
    return resp_code;
  }

  public void setResp_code(String resp_code) {
    this.resp_code = resp_code;
  }

  public String getResp_msg() {
    return resp_msg;
  }

  public void setResp_msg(String resp_msg) {
    this.resp_msg = resp_msg;
  }
}
