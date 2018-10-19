package com.gq.ged.order.pc.res;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by Levi on 2018/4/23.
 */
public class GuaranteeRecordDetailResForm extends GuaranteeBaseForm {
    @ApiModelProperty("借款用途")
    private String loanType;
    @ApiModelProperty("产品类型loan_type")
    private String loanPurpose;
    @ApiModelProperty("保证金")
    private String cashDeposit;
    @ApiModelProperty("担保服务费")
    private String guaranteeFee;
    @ApiModelProperty("担保状态")
    private String guaranteeStatus;
    @ApiModelProperty("还款日")
    private String replayDate;

    @ApiModelProperty("还款计划")
    private List<RepaymentItem> repaymentPlans;

    public String getReplayDate() {
        return replayDate;
    }

    public void setReplayDate(String replayDate) {
        this.replayDate = replayDate;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public String getLoanPurpose() {
        return loanPurpose;
    }

    public void setLoanPurpose(String loanPurpose) {
        this.loanPurpose = loanPurpose;
    }

    public String getCashDeposit() {
        return cashDeposit;
    }

    public void setCashDeposit(String cashDeposit) {
        this.cashDeposit = cashDeposit;
    }

    public String getGuaranteeFee() {
        return guaranteeFee;
    }

    public void setGuaranteeFee(String guaranteeFee) {
        this.guaranteeFee = guaranteeFee;
    }

    public String getGuaranteeStatus() {
        return guaranteeStatus;
    }

    public void setGuaranteeStatus(String guaranteeStatus) {
        this.guaranteeStatus = guaranteeStatus;
    }

    public List<RepaymentItem> getRepaymentPlans() {
        return repaymentPlans;
    }

    public void setRepaymentPlans(List<RepaymentItem> repaymentPlans) {
        this.repaymentPlans = repaymentPlans;
    }
}
