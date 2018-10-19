package com.gq.ged.order.pc.res;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Levi on 2018/4/18.
 */
public class LoanRecordResForm {

    @ApiModelProperty("借款金额")
    private String loanAmount;

    @ApiModelProperty("订单号")
    private String orderNo;

    @ApiModelProperty("期限")
    private String period;

    @ApiModelProperty("年化利率")
    private String rate;

    @ApiModelProperty("还款方式")
    private String repayWay;

    @ApiModelProperty("还款日")
    private String repayDate;

    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("是否需要缴费  1是   0 否")
    private String isCanServiceFee;

    @ApiModelProperty("是否能够提现  1是  0否")
    private String isCanWithdraw;

    @ApiModelProperty("是否需要还款   1是  0否")
    private String isCanRepay;

    @ApiModelProperty("合同签署状态   1是  0否")
    private String isSignContract;
    @ApiModelProperty("合同编号")
    private String contractNo;
    @ApiModelProperty("0增量 1存量")
    private String isStockFlag;
    @ApiModelProperty("状态标识代码")
    private String statusCode;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getIsStockFlag() {
        return isStockFlag;
    }

    public void setIsStockFlag(String isStockFlag) {
        this.isStockFlag = isStockFlag;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getIsSignContract() {
        return isSignContract;
    }

    public void setIsSignContract(String isSignContract) {
        this.isSignContract = isSignContract;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getRepayWay() {
        return repayWay;
    }

    public void setRepayWay(String repayWay) {
        this.repayWay = repayWay;
    }

    public String getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(String repayDate) {
        this.repayDate = repayDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsCanServiceFee() {
        return isCanServiceFee;
    }

    public void setIsCanServiceFee(String isCanServiceFee) {
        this.isCanServiceFee = isCanServiceFee;
    }

    public String getIsCanWithdraw() {
        return isCanWithdraw;
    }

    public void setIsCanWithdraw(String isCanWithdraw) {
        this.isCanWithdraw = isCanWithdraw;
    }

    public String getIsCanRepay() {
        return isCanRepay;
    }

    public void setIsCanRepay(String isCanRepay) {
        this.isCanRepay = isCanRepay;
    }
}
