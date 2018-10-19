package com.gq.ged.order.pc.res;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Levi on 2018/4/18.
 */
public class BorrowOrderBaseInfo {
    @ApiModelProperty("借款金额")
    private String loanAmount;

    @ApiModelProperty(" 期限")
    private String period;

    @ApiModelProperty("利率")
    private String rate;

    @ApiModelProperty("借款类型")
    private String loanType;

    @ApiModelProperty("起止时间")
    private String date;

    @ApiModelProperty("状   态")
    private String status;

    @ApiModelProperty("借款用途")
    private String loanPurpose;

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

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLoanPurpose() {
        return loanPurpose;
    }

    public void setLoanPurpose(String loanPurpose) {
        this.loanPurpose = loanPurpose;
    }
}
