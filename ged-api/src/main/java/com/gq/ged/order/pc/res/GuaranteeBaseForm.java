package com.gq.ged.order.pc.res;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * Created by Levi on 2018/4/23.
 */
public class GuaranteeBaseForm {
//    款人名称        借款金额（万元）      期限（月）    年化利率        还款方式       借款用途     产品类型
   //借款人名称      借款金额（万元）    期限（月）   年化利率           还款方式       保证金(万元)      担保服务费（元）      状态
    @ApiModelProperty("借款人名称")
    private String name;
    @ApiModelProperty("借款金额")
    private String loanAmount;
    @ApiModelProperty("借款期限")
    private String loanTerm;
    @ApiModelProperty("日利率")
    private String rateDay;
    @ApiModelProperty("还款方式")
    private String repayWay;

    @ApiModelProperty("订单状态")
    private String status;
    @ApiModelProperty("订单编号")
    private String orderNo;
    @ApiModelProperty("担保id")
    private String guaranteeId;

    public String getGuaranteeId() {
        return guaranteeId;
    }

    public void setGuaranteeId(String guaranteeId) {
        this.guaranteeId = guaranteeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
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

    public String getRepayWay() {
        return repayWay;
    }

    public void setRepayWay(String repayWay) {
        this.repayWay = repayWay;
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
