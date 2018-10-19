package com.gq.ged.order.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by wrh on 2018/3/19.
 */
@ApiModel
public class DeductResult {
    @ApiModelProperty(value = "期数")
    private String periodNum; // 期数
    private String repayPeriodStatus;// 状态
    private String deductDate; // 还款日期
    private String repayAmount; // 应还金额
    private String serviceFee; // 服务费
    private String managementFee; // 账户管理费
    private String interestAmount; // 利息
    private String capitalAmount; // 本金
    private String penaltyAmount; // 违约金
    private String factServiceFee;// 实还服务费
    private String factManagementFee;// 实还账户管理费
    private String factInterestAmount; // 实还利息
    private String factOverdueCapialAmount;// 实还本金
    private String factPenaltyAmount;// 实还违约金
    private String factFineAmount;// 实还罚息
    private String capitalInterestRepayDate;// 当期本息结清日期
    private String allRepayDate;// 当期总结清日期
    private String fineAmount;// 罚息
    private String oweCapitalAmount;// 逾期本金
    private String oweInterestAmount;// 逾期利息
    private String overdueDays;// 逾期天数
    private String penaltyExemptAmount;// 违约金减免
    private String fineExemptAmount;// 罚息减免
    private String factBreakAmount;// 提前还款费用
    private String factAddAmount;// 营业外收入金额
    private String backAmount;//退回金额
    private String compensatoryAmount;//代偿
    private String custId;//客户id

    private String id;

    private String isNewRecord;

    private String remarks;

    private String createDate;

    private String updateDate;

    private String stayMoney;//待还金额

    public String getStayMoney() {
        return stayMoney;
    }

    public void setStayMoney(String stayMoney) {
        this.stayMoney = stayMoney;
    }

    public String getPeriodNum() {
        return periodNum;
    }

    public void setPeriodNum(String periodNum) {
        this.periodNum = periodNum;
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

    public String getRepayAmount() {
        return repayAmount;
    }

    public void setRepayAmount(String repayAmount) {
        this.repayAmount = repayAmount;
    }

    public String getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(String serviceFee) {
        this.serviceFee = serviceFee;
    }

    public String getManagementFee() {
        return managementFee;
    }

    public void setManagementFee(String managementFee) {
        this.managementFee = managementFee;
    }

    public String getInterestAmount() {
        return interestAmount;
    }

    public void setInterestAmount(String interestAmount) {
        this.interestAmount = interestAmount;
    }

    public String getCapitalAmount() {
        return capitalAmount;
    }

    public void setCapitalAmount(String capitalAmount) {
        this.capitalAmount = capitalAmount;
    }

    public String getPenaltyAmount() {
        return penaltyAmount;
    }

    public void setPenaltyAmount(String penaltyAmount) {
        this.penaltyAmount = penaltyAmount;
    }

    public String getFactServiceFee() {
        return factServiceFee;
    }

    public void setFactServiceFee(String factServiceFee) {
        this.factServiceFee = factServiceFee;
    }

    public String getFactManagementFee() {
        return factManagementFee;
    }

    public void setFactManagementFee(String factManagementFee) {
        this.factManagementFee = factManagementFee;
    }

    public String getFactInterestAmount() {
        return factInterestAmount;
    }

    public void setFactInterestAmount(String factInterestAmount) {
        this.factInterestAmount = factInterestAmount;
    }

    public String getFactOverdueCapialAmount() {
        return factOverdueCapialAmount;
    }

    public void setFactOverdueCapialAmount(String factOverdueCapialAmount) {
        this.factOverdueCapialAmount = factOverdueCapialAmount;
    }

    public String getFactPenaltyAmount() {
        return factPenaltyAmount;
    }

    public void setFactPenaltyAmount(String factPenaltyAmount) {
        this.factPenaltyAmount = factPenaltyAmount;
    }

    public String getFactFineAmount() {
        return factFineAmount;
    }

    public void setFactFineAmount(String factFineAmount) {
        this.factFineAmount = factFineAmount;
    }

    public String getCapitalInterestRepayDate() {
        return capitalInterestRepayDate;
    }

    public void setCapitalInterestRepayDate(String capitalInterestRepayDate) {
        this.capitalInterestRepayDate = capitalInterestRepayDate;
    }

    public String getAllRepayDate() {
        return allRepayDate;
    }

    public void setAllRepayDate(String allRepayDate) {
        this.allRepayDate = allRepayDate;
    }

    public String getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(String fineAmount) {
        this.fineAmount = fineAmount;
    }

    public String getOweCapitalAmount() {
        return oweCapitalAmount;
    }

    public void setOweCapitalAmount(String oweCapitalAmount) {
        this.oweCapitalAmount = oweCapitalAmount;
    }

    public String getOweInterestAmount() {
        return oweInterestAmount;
    }

    public void setOweInterestAmount(String oweInterestAmount) {
        this.oweInterestAmount = oweInterestAmount;
    }

    public String getOverdueDays() {
        return overdueDays;
    }

    public void setOverdueDays(String overdueDays) {
        this.overdueDays = overdueDays;
    }

    public String getPenaltyExemptAmount() {
        return penaltyExemptAmount;
    }

    public void setPenaltyExemptAmount(String penaltyExemptAmount) {
        this.penaltyExemptAmount = penaltyExemptAmount;
    }

    public String getFineExemptAmount() {
        return fineExemptAmount;
    }

    public void setFineExemptAmount(String fineExemptAmount) {
        this.fineExemptAmount = fineExemptAmount;
    }

    public String getFactBreakAmount() {
        return factBreakAmount;
    }

    public void setFactBreakAmount(String factBreakAmount) {
        this.factBreakAmount = factBreakAmount;
    }

    public String getFactAddAmount() {
        return factAddAmount;
    }

    public void setFactAddAmount(String factAddAmount) {
        this.factAddAmount = factAddAmount;
    }

    public String getBackAmount() {
        return backAmount;
    }

    public void setBackAmount(String backAmount) {
        this.backAmount = backAmount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsNewRecord() {
        return isNewRecord;
    }

    public void setIsNewRecord(String isNewRecord) {
        this.isNewRecord = isNewRecord;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getCompensatoryAmount() {
        return compensatoryAmount;
    }

    public void setCompensatoryAmount(String compensatoryAmount) {
        this.compensatoryAmount = compensatoryAmount;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }
}
