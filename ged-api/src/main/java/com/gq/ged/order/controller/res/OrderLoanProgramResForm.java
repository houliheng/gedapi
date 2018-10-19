package com.gq.ged.order.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by wrh on 2018/5/29.
 */
@ApiModel
public class OrderLoanProgramResForm implements Serializable {
    @ApiModelProperty(value = "订单编号")
    private String orderCode;;
    @ApiModelProperty(value = "借款金额")
    private  BigDecimal loanAmount;
    @ApiModelProperty(value = "借款期限")
    private String loanTerm;
    @ApiModelProperty(value = "订单状态")
    private String status;
    @ApiModelProperty(value = "借款人名称")
    private String companyName;
    @ApiModelProperty(value = "月利率")
    private String rateDay;
    @ApiModelProperty(value = "还款方式")
    private String repaymentStyle;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRateDay() {
        return rateDay;
    }

    public void setRateDay(String rateDay) {
        this.rateDay = rateDay;
    }

    public String getRepaymentStyle() {
        return repaymentStyle;
    }

    public void setRepaymentStyle(String repaymentStyle) {
        this.repaymentStyle = repaymentStyle;
    }
}
