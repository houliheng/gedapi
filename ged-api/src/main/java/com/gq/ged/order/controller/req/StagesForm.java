package com.gq.ged.order.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by wyq_tomorrow on 2018/3/7.
 */
@ApiModel
public class StagesForm implements Serializable {
  private static final long serialVersionUID = 1L;
  @ApiModelProperty(value = "期数")
  private Integer term;
  @ApiModelProperty(value = "还款金额")
  private BigDecimal repaymentAmount;
  @ApiModelProperty(value = "还款时间")
  private Date repaymentTime;
  @ApiModelProperty(value = "还款标志")
  private Integer repaymentFlag;
  @ApiModelProperty(value = "逾期金额")
  private BigDecimal overdueAmount;
  @ApiModelProperty(value = "逾期罚息")
  private BigDecimal overdueInterest;
  @ApiModelProperty(value = "逾期违约金")
  private BigDecimal overduePenalty;

  public Integer getTerm() {
    return term;
  }

  public void setTerm(Integer term) {
    this.term = term;
  }

  public BigDecimal getRepaymentAmount() {
    return repaymentAmount;
  }

  public void setRepaymentAmount(BigDecimal repaymentAmount) {
    this.repaymentAmount = repaymentAmount;
  }

  public Date getRepaymentTime() {
    return repaymentTime;
  }

  public void setRepaymentTime(Date repaymentTime) {
    this.repaymentTime = repaymentTime;
  }

  public Integer getRepaymentFlag() {
    return repaymentFlag;
  }

  public void setRepaymentFlag(Integer repaymentFlag) {
    this.repaymentFlag = repaymentFlag;
  }

  public BigDecimal getOverdueAmount() {
    return overdueAmount;
  }

  public void setOverdueAmount(BigDecimal overdueAmount) {
    this.overdueAmount = overdueAmount;
  }

  public BigDecimal getOverdueInterest() {
    return overdueInterest;
  }

  public void setOverdueInterest(BigDecimal overdueInterest) {
    this.overdueInterest = overdueInterest;
  }

  public BigDecimal getOverduePenalty() {
    return overduePenalty;
  }

  public void setOverduePenalty(BigDecimal overduePenalty) {
    this.overduePenalty = overduePenalty;
  }
}
