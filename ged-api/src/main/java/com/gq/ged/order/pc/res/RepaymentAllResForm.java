package com.gq.ged.order.pc.res;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Levi on 2018/6/19.
 */
public class RepaymentAllResForm {

    @ApiModelProperty("提前结清违约金")
    private String advanceOverduePenalty;
    @ApiModelProperty("待还金额")
    private String stayMoney;
//    @ApiModelProperty("总期数")
//    private String periodValue;
    @ApiModelProperty("本笔借款到期日")
    private String conEndDate;
    @ApiModelProperty("合同号")
    private String contractNo;
    @ApiModelProperty("待还总期数")
    private String periodValue;
    @ApiModelProperty("合同总期数")
    private String periodValueAll;
    @ApiModelProperty("合同金额")
    private String contractAmount;
    @ApiModelProperty("逾期总罚息")
    private String fineAmountAll;
    @ApiModelProperty("逾期总违约金")
    private String overduePenaltyAll;
    @ApiModelProperty("放款日期")
    private String loanDate;



    @ApiModelProperty("是否能够还款 1 是 0 否")
    private String isCanRepay;

    public String getIsCanRepay() {
        return isCanRepay;
    }

    public void setIsCanRepay(String isCanRepay) {
        this.isCanRepay = isCanRepay;
    }

    public String getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate;
    }

    public String getAdvanceOverduePenalty() {
        return advanceOverduePenalty;
    }

    public void setAdvanceOverduePenalty(String advanceOverduePenalty) {
        this.advanceOverduePenalty = advanceOverduePenalty;
    }

    public String getStayMoney() {
        return stayMoney;
    }

    public void setStayMoney(String stayMoney) {
        this.stayMoney = stayMoney;
    }

    public String getConEndDate() {
        return conEndDate;
    }

    public void setConEndDate(String conEndDate) {
        this.conEndDate = conEndDate;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getPeriodValue() {
        return periodValue;
    }

    public void setPeriodValue(String periodValue) {
        this.periodValue = periodValue;
    }

    public String getPeriodValueAll() {
        return periodValueAll;
    }

    public void setPeriodValueAll(String periodValueAll) {
        this.periodValueAll = periodValueAll;
    }

    public String getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(String contractAmount) {
        this.contractAmount = contractAmount;
    }

    public String getFineAmountAll() {
        return fineAmountAll;
    }

    public void setFineAmountAll(String fineAmountAll) {
        this.fineAmountAll = fineAmountAll;
    }

    public String getOverduePenaltyAll() {
        return overduePenaltyAll;
    }

    public void setOverduePenaltyAll(String overduePenaltyAll) {
        this.overduePenaltyAll = overduePenaltyAll;
    }
}
