package com.gq.ged.order.controller.req;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
/**
 * Created by wrh on 2018/4/25.
 */
public class PushOrderVerifyReqForm implements Serializable{
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "子订单编号")
    private String applyIdChild;
    @ApiModelProperty(value = "订单编号")
    private String orderNo;
    @ApiModelProperty(value = "企业信息")
    private String companyInfo;
    @ApiModelProperty(value = "借款用途")
    private String loanPurpose;
    @ApiModelProperty(value = "产品信息")
    private String companyProductInfo;
    @ApiModelProperty(value = "借款人行政处罚")
    private String borrowerSanction;
    @ApiModelProperty(value = "借款人涉诉情况")
    private String borrowerLitigation;
    @ApiModelProperty(value = "借款人行事处罚")
    private String borrowerActInfo;
    @ApiModelProperty(value = "借款人在其他平台共债情况")
    private String borrowerDebtInfo;
    @ApiModelProperty(value = "银行贷款情况")
    private String bankLoanInfo;
    @ApiModelProperty(value = "逾期次数")
    private Integer overdueNumber;
    @ApiModelProperty(value = "资产信息")
    private String assetsInfo;
    @ApiModelProperty(value = "还款来源")
    private String repayChanel;

    public String getApplyIdChild() {
        return applyIdChild;
    }

    public void setApplyIdChild(String applyIdChild) {
        this.applyIdChild = applyIdChild;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(String companyInfo) {
        this.companyInfo = companyInfo;
    }

    public String getLoanPurpose() {
        return loanPurpose;
    }

    public void setLoanPurpose(String loanPurpose) {
        this.loanPurpose = loanPurpose;
    }

    public String getCompanyProductInfo() {
        return companyProductInfo;
    }

    public void setCompanyProductInfo(String companyProductInfo) {
        this.companyProductInfo = companyProductInfo;
    }

    public String getBorrowerSanction() {
        return borrowerSanction;
    }

    public void setBorrowerSanction(String borrowerSanction) {
        this.borrowerSanction = borrowerSanction;
    }

    public String getBorrowerLitigation() {
        return borrowerLitigation;
    }

    public void setBorrowerLitigation(String borrowerLitigation) {
        this.borrowerLitigation = borrowerLitigation;
    }

    public String getBorrowerActInfo() {
        return borrowerActInfo;
    }

    public void setBorrowerActInfo(String borrowerActInfo) {
        this.borrowerActInfo = borrowerActInfo;
    }

    public String getBorrowerDebtInfo() {
        return borrowerDebtInfo;
    }

    public void setBorrowerDebtInfo(String borrowerDebtInfo) {
        this.borrowerDebtInfo = borrowerDebtInfo;
    }

    public String getBankLoanInfo() {
        return bankLoanInfo;
    }

    public void setBankLoanInfo(String bankLoanInfo) {
        this.bankLoanInfo = bankLoanInfo;
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
        this.assetsInfo = assetsInfo;
    }

    public String getRepayChanel() {
        return repayChanel;
    }

    public void setRepayChanel(String repayChanel) {
        this.repayChanel = repayChanel;
    }
}