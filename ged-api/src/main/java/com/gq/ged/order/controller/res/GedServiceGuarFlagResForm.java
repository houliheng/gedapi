package com.gq.ged.order.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by wrh on 2018/4/3.
 */
@ApiModel
public class GedServiceGuarFlagResForm {
  @ApiModelProperty(value = "是否交服务费(0否1是)")
  private Integer serviceFeeFlag;
  @ApiModelProperty(value = "是否交保证金(0否1是)")
  private  int guaranteeFeeFlag;
  @ApiModelProperty(value = "金额")
  private  String amt;
  @ApiModelProperty(value = "银行卡")
  private  String cardNum;

  public String getAmt() {
    return amt;
  }

  public void setAmt(String amt) {
    this.amt = amt;
  }

  public String getCardNum() {
    return cardNum;
  }

  public void setCardNum(String cardNum) {
    this.cardNum = cardNum;
  }

  public Integer getServiceFeeFlag() {
    return serviceFeeFlag;
  }

  public void setServiceFeeFlag(Integer serviceFeeFlag) {
    this.serviceFeeFlag = serviceFeeFlag;
  }

  public int getGuaranteeFeeFlag() {
    return guaranteeFeeFlag;
  }

  public void setGuaranteeFeeFlag(int guaranteeFeeFlag) {
    this.guaranteeFeeFlag = guaranteeFeeFlag;
  }
}
