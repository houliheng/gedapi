package com.gq.ged.order.pc.res;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

public class RepaymentPlan {

  @ApiModelProperty("期数")
  private Integer orderTerm;// 期数
  @ApiModelProperty("应还总金额")
  private BigDecimal repaymentAmount;// 应还总金额
  @ApiModelProperty("实还总金额")
  private BigDecimal actualRepaymentAmount;// 实还总金额
  @ApiModelProperty("应还时间")
  private String repaymentTime;// 应还时间
  @ApiModelProperty("实还时间")
  private String actualRepaymentTime;// 实还时间
  @ApiModelProperty("还款状态")
  private String repaymentStatus;// 还款状态 (根据本期还款标志0未还1已还2逾期 转换)

  public Integer getOrderTerm() {
    return orderTerm;
  }

  public void setOrderTerm(Integer orderTerm) {
    this.orderTerm = orderTerm;
  }

  public BigDecimal getRepaymentAmount() {
    return repaymentAmount;
  }

  public void setRepaymentAmount(BigDecimal repaymentAmount) {
    this.repaymentAmount = repaymentAmount;
  }

  public BigDecimal getActualRepaymentAmount() {
    return actualRepaymentAmount;
  }

  public void setActualRepaymentAmount(BigDecimal actualRepaymentAmount) {
    this.actualRepaymentAmount = actualRepaymentAmount;
  }

  public String getRepaymentTime() {
    return repaymentTime;
  }

  public void setRepaymentTime(String repaymentTime) {
    this.repaymentTime = repaymentTime;
  }

  public String getActualRepaymentTime() {
    return actualRepaymentTime;
  }

  public void setActualRepaymentTime(String actualRepaymentTime) {
    this.actualRepaymentTime = actualRepaymentTime;
  }

  public String getRepaymentStatus() {
    return repaymentStatus;
  }

  public void setRepaymentStatus(String repaymentStatus) {
    this.repaymentStatus = repaymentStatus;
  }
}
