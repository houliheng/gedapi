package com.gq.ged.order.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by wrh on 2018/4/18.
 */
@ApiModel
public class GuaranteeConfirmResForm implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("借款金额")
    private BigDecimal loanAmount;
    @ApiModelProperty("借款期限")
    private String loanTerm;
    @ApiModelProperty("日利率")
    private String rateDay;
    @ApiModelProperty("借款人")
    private String loanUser;
    @ApiModelProperty("申请日期")
    private String applyDate;
    @ApiModelProperty("订单状态")
    private String status;
    @ApiModelProperty("订单编号")
    private String orderNo;

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

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
