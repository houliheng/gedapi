package com.gq.ged.order.dao.model;

import java.util.Date;

public class OrderVerify {
    private Long id;

    private Long orderId;

    private String companyInfo;

    private String loanPurpose;

    private String companyProductInfo;

    private String borrowerSanction;

    private String borrowerLitigation;

    private String borrowerActInfo;

    private String borrowerDebtInfo;

    private String bankLoanInfo;

    private Integer overdueNumber;

    private String assetsInfo;

    private String repayChanel;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(String companyInfo) {
        this.companyInfo = companyInfo == null ? null : companyInfo.trim();
    }

    public String getLoanPurpose() {
        return loanPurpose;
    }

    public void setLoanPurpose(String loanPurpose) {
        this.loanPurpose = loanPurpose == null ? null : loanPurpose.trim();
    }

    public String getCompanyProductInfo() {
        return companyProductInfo;
    }

    public void setCompanyProductInfo(String companyProductInfo) {
        this.companyProductInfo = companyProductInfo == null ? null : companyProductInfo.trim();
    }

    public String getBorrowerSanction() {
        return borrowerSanction;
    }

    public void setBorrowerSanction(String borrowerSanction) {
        this.borrowerSanction = borrowerSanction == null ? null : borrowerSanction.trim();
    }

    public String getBorrowerLitigation() {
        return borrowerLitigation;
    }

    public void setBorrowerLitigation(String borrowerLitigation) {
        this.borrowerLitigation = borrowerLitigation == null ? null : borrowerLitigation.trim();
    }

    public String getBorrowerActInfo() {
        return borrowerActInfo;
    }

    public void setBorrowerActInfo(String borrowerActInfo) {
        this.borrowerActInfo = borrowerActInfo == null ? null : borrowerActInfo.trim();
    }

    public String getBorrowerDebtInfo() {
        return borrowerDebtInfo;
    }

    public void setBorrowerDebtInfo(String borrowerDebtInfo) {
        this.borrowerDebtInfo = borrowerDebtInfo == null ? null : borrowerDebtInfo.trim();
    }

    public String getBankLoanInfo() {
        return bankLoanInfo;
    }

    public void setBankLoanInfo(String bankLoanInfo) {
        this.bankLoanInfo = bankLoanInfo == null ? null : bankLoanInfo.trim();
    }

    public Integer getOverdueNumber() {
        return overdueNumber;
    }

    public void setOverdueNumber(Integer overdueNumber) {
        this.overdueNumber = overdueNumber;
    }

    public String getAssetsInfo() {
        return assetsInfo;
    }

    public void setAssetsInfo(String assetsInfo) {
        this.assetsInfo = assetsInfo == null ? null : assetsInfo.trim();
    }

    public String getRepayChanel() {
        return repayChanel;
    }

    public void setRepayChanel(String repayChanel) {
        this.repayChanel = repayChanel == null ? null : repayChanel.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}