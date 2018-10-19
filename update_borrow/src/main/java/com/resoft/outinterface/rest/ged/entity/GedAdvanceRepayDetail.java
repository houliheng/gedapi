package com.resoft.outinterface.rest.ged.entity;

import java.math.BigDecimal;
import java.util.Date;

public class GedAdvanceRepayDetail {
    private BigDecimal repayAmount; //本  息 服务费  账户管理费
    private BigDecimal allRepayMoney; //本  息 服务费  账户管理费+违约金+罚息
    private BigDecimal overduePenalty; // 违约金
    private BigDecimal fineAmount; // 罚息
    private BigDecimal discountFee; // 贴息
    private BigDecimal penaltyExemptAmount; // 罚息减免
    private BigDecimal fineExemptAmount; // 违约金减免
    private BigDecimal factServiceFee; //实还6项
    private BigDecimal factManagementFee; //
    private BigDecimal factInterestFee; //
    private BigDecimal factCapitalAmount; //
    private BigDecimal facePenaltyAmount; //
    private BigDecimal factFineAmount; //
    private BigDecimal allFactMoney; //所有已经还了的金额=违约金罚息减免+实还本+实还息+实还服务+实还管理+实还违约+实还罚息
    private String contractNo; //合同号
    private String conEndDate; //合同结束日期
    private BigDecimal contractAmount;//合同表的合同金额
    private String repayPeriodStatus;//实还表的状态
    private String periodNum;
    private String deductDate;
    private String capitalAmount;
    private String periodValue;
    private Date loanDate;

    public BigDecimal getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(BigDecimal contractAmount) {
        this.contractAmount = contractAmount;
    }

    public BigDecimal getRepayAmount() {
        return repayAmount;
    }

    public void setRepayAmount(BigDecimal repayAmount) {
        this.repayAmount = repayAmount;
    }

    public BigDecimal getOverduePenalty() {
        return overduePenalty;
    }

    public void setOverduePenalty(BigDecimal overduePenalty) {
        this.overduePenalty = overduePenalty;
    }

    public BigDecimal getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(BigDecimal fineAmount) {
        this.fineAmount = fineAmount;
    }

    public BigDecimal getDiscountFee() {
        return discountFee;
    }

    public void setDiscountFee(BigDecimal discountFee) {
        this.discountFee = discountFee;
    }

    public BigDecimal getPenaltyExemptAmount() {
        return penaltyExemptAmount;
    }

    public void setPenaltyExemptAmount(BigDecimal penaltyExemptAmount) {
        this.penaltyExemptAmount = penaltyExemptAmount;
    }

    public BigDecimal getFineExemptAmount() {
        return fineExemptAmount;
    }

    public void setFineExemptAmount(BigDecimal fineExemptAmount) {
        this.fineExemptAmount = fineExemptAmount;
    }

    public BigDecimal getFactServiceFee() {
        return factServiceFee;
    }

    public void setFactServiceFee(BigDecimal factServiceFee) {
        this.factServiceFee = factServiceFee;
    }

    public BigDecimal getFactManagementFee() {
        return factManagementFee;
    }

    public void setFactManagementFee(BigDecimal factManagementFee) {
        this.factManagementFee = factManagementFee;
    }

    public BigDecimal getFactInterestFee() {
        return factInterestFee;
    }

    public void setFactInterestFee(BigDecimal factInterestFee) {
        this.factInterestFee = factInterestFee;
    }

    public BigDecimal getFactCapitalAmount() {
        return factCapitalAmount;
    }

    public void setFactCapitalAmount(BigDecimal factCapitalAmount) {
        this.factCapitalAmount = factCapitalAmount;
    }

    public BigDecimal getFacePenaltyAmount() {
        return facePenaltyAmount;
    }

    public void setFacePenaltyAmount(BigDecimal facePenaltyAmount) {
        this.facePenaltyAmount = facePenaltyAmount;
    }

    public BigDecimal getFactFineAmount() {
        return factFineAmount;
    }

    public void setFactFineAmount(BigDecimal factFineAmount) {
        this.factFineAmount = factFineAmount;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getConEndDate() {
        return conEndDate;
    }

    public void setConEndDate(String conEndDate) {
        this.conEndDate = conEndDate;
    }

    public BigDecimal getAllRepayMoney() {
        return allRepayMoney;
    }

    public void setAllRepayMoney(BigDecimal allRepayMoney) {
        this.allRepayMoney = allRepayMoney;
    }

    public BigDecimal getAllFactMoney() {
        return allFactMoney;
    }

    public void setAllFactMoney(BigDecimal allFactMoney) {
        this.allFactMoney = allFactMoney;
    }

    public String getRepayPeriodStatus() {
        return repayPeriodStatus;
    }

    public void setRepayPeriodStatus(String repayPeriodStatus) {
        this.repayPeriodStatus = repayPeriodStatus;
    }

    public String getDeductDate() {
        return deductDate;
    }

    public void setDeductDate(String deductDate) {
        this.deductDate = deductDate;
    }

    public String getPeriodNum() {
        return periodNum;
    }

    public void setPeriodNum(String periodNum) {
        this.periodNum = periodNum;
    }

    public String getCapitalAmount() {
        return capitalAmount;
    }

    public void setCapitalAmount(String capitalAmount) {
        this.capitalAmount = capitalAmount;
    }

    public String getPeriodValue() {
        return periodValue;
    }

    public void setPeriodValue(String periodValue) {
        this.periodValue = periodValue;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }
}
