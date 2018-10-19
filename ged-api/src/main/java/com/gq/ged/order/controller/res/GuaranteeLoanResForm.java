package com.gq.ged.order.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by wrh on 2018/4/17.
 */
@ApiModel
public class GuaranteeLoanResForm implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("借款金额")
    private BigDecimal loanAmount;
    @ApiModelProperty("借款期限")
    private String loanTerm;
    @ApiModelProperty("借款类型")
    private String loanType;
    @ApiModelProperty("日利率")
    private String rateDay;
    @ApiModelProperty("借款人")
    private String loanUser;
    @ApiModelProperty("是否开户成功 0否1是")
    private int whethorAccountFlag;
    @ApiModelProperty("判断是否有待担保记录 0没有1有")
    private int toWhethorGuarantee;
    @ApiModelProperty("订单编号")
    private String orderNo;


    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getLoanTerm() {
        return loanTerm;
    }

    public void setLoanTerm(String loanTerm) {
        this.loanTerm = loanTerm;
    }

    public String getRateDay() {
        return rateDay;
    }

    public void setRateDay(String rateDay) {
        this.rateDay = rateDay;
    }

    public String getLoanUser() {
        return loanUser;
    }

    public void setLoanUser(String loanUser) {
        this.loanUser = loanUser;
    }

    public int getWhethorAccountFlag() {
        return whethorAccountFlag;
    }

    public void setWhethorAccountFlag(int whethorAccountFlag) {
        this.whethorAccountFlag = whethorAccountFlag;
    }

    public int getToWhethorGuarantee() {
        return toWhethorGuarantee;
    }

    public void setToWhethorGuarantee(int toWhethorGuarantee) {
        this.toWhethorGuarantee = toWhethorGuarantee;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
