package com.gq.ged.order.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by wrh on 2018/1/23.
 */

@ApiModel
public class RepayMentPlanResForm implements Serializable {
    @ApiModelProperty(value = "期限")
   private int orderTerm;
    @ApiModelProperty(value = "还款时间")
    private String repaymentTime;
    @ApiModelProperty(value = "还款金额")
    private BigDecimal repaymentAmount;
    @ApiModelProperty(value = "是否逾期状态")
    private int whethRepayFlag;
    @ApiModelProperty(value = "总共期限")
    private int  countTerm;
    @ApiModelProperty(value = "总共金额")
    private BigDecimal countRepaymengAmount;
    @ApiModelProperty(value = "代偿金额")
    private String compensatory;

    public int getOrderTerm() {
        return orderTerm;
    }

    public void setOrderTerm(int orderTerm) {
        this.orderTerm = orderTerm;
    }

    public String getRepaymentTime() {
        return repaymentTime;
    }

    public void setRepaymentTime(String repaymentTime) {
        this.repaymentTime = repaymentTime;
    }

    public BigDecimal getRepaymentAmount() {
        return repaymentAmount;
    }

    public void setRepaymentAmount(BigDecimal repaymentAmount) {
        this.repaymentAmount = repaymentAmount;
    }

    public int getWhethRepayFlag() {
        return whethRepayFlag;
    }

    public void setWhethRepayFlag(int whethRepayFlag) {
        this.whethRepayFlag = whethRepayFlag;
    }

    public int getCountTerm() {
        return countTerm;
    }

    public void setCountTerm(int countTerm) {
        this.countTerm = countTerm;
    }

    public BigDecimal getCountRepaymengAmount() {
        return countRepaymengAmount;
    }

    public void setCountRepaymengAmount(BigDecimal countRepaymengAmount) {
        this.countRepaymengAmount = countRepaymengAmount;
    }

    public String getCompensatory() {
        return compensatory;
    }

    public void setCompensatory(String compensatory) {
        this.compensatory = compensatory;
    }
}
