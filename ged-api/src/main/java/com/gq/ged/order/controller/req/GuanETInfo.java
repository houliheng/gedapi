package com.gq.ged.order.controller.req;

import java.io.Serializable;

/**
 * Created by wrh on 2018/4/26.
 */
public class GuanETInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String orderNo;		// 申请编号
	private String intrOfCompany;		// 企业信息
	private String mixLoanUsage;		// 借款用途
	private String sourceOfDepayment1;	// 还款来源1
	private String sourceOfDepayment2;	// 还款来源2
	private String sourceOfDepayment3;	// 还款来源3
	private String sourceOfDepayment4;	// 还款来源4
	private String intrOfComProduction;		// 企业产品介绍
	private String borrowAndMatePunish;//借款人及配偶受行政处罚情况
	private String borrowInvolveInfo;//借款人涉诉情况
	private String borrowCrimaAdminPunish;//借款人受刑事、行政处罚情况
	private String borrowNewLoanBlance;//借款人在其他网贷平台借款余额
	private String platformOverdueTime;//平台逾期次数
	private String platformOverdueMoney;//平台逾期金额
	private String orderSubNo;//子订单编号
	private String other;               // 其他
	private String isLoan;  //是否贷款
	private String loanRecode; //共几笔贷款记录
	private String isOverdue; //是否有逾期
	private String sourceOfCreRegist; //征信记录等级

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getIntrOfCompany() {
		return intrOfCompany;
	}

	public void setIntrOfCompany(String intrOfCompany) {
		this.intrOfCompany = intrOfCompany;
	}

	public String getMixLoanUsage() {
		return mixLoanUsage;
	}

	public void setMixLoanUsage(String mixLoanUsage) {
		this.mixLoanUsage = mixLoanUsage;
	}

	public String getSourceOfDepayment1() {
		return sourceOfDepayment1;
	}

	public void setSourceOfDepayment1(String sourceOfDepayment1) {
		this.sourceOfDepayment1 = sourceOfDepayment1;
	}

	public String getSourceOfDepayment2() {
		return sourceOfDepayment2;
	}

	public void setSourceOfDepayment2(String sourceOfDepayment2) {
		this.sourceOfDepayment2 = sourceOfDepayment2;
	}

	public String getSourceOfDepayment3() {
		return sourceOfDepayment3;
	}

	public void setSourceOfDepayment3(String sourceOfDepayment3) {
		this.sourceOfDepayment3 = sourceOfDepayment3;
	}

	public String getSourceOfDepayment4() {
		return sourceOfDepayment4;
	}

	public void setSourceOfDepayment4(String sourceOfDepayment4) {
		this.sourceOfDepayment4 = sourceOfDepayment4;
	}

	public String getIntrOfComProduction() {
		return intrOfComProduction;
	}

	public void setIntrOfComProduction(String intrOfComProduction) {
		this.intrOfComProduction = intrOfComProduction;
	}

	public String getBorrowAndMatePunish() {
		return borrowAndMatePunish;
	}

	public void setBorrowAndMatePunish(String borrowAndMatePunish) {
		this.borrowAndMatePunish = borrowAndMatePunish;
	}

	public String getBorrowInvolveInfo() {
		return borrowInvolveInfo;
	}

	public void setBorrowInvolveInfo(String borrowInvolveInfo) {
		this.borrowInvolveInfo = borrowInvolveInfo;
	}

	public String getBorrowCrimaAdminPunish() {
		return borrowCrimaAdminPunish;
	}

	public void setBorrowCrimaAdminPunish(String borrowCrimaAdminPunish) {
		this.borrowCrimaAdminPunish = borrowCrimaAdminPunish;
	}

	public String getBorrowNewLoanBlance() {
		return borrowNewLoanBlance;
	}

	public void setBorrowNewLoanBlance(String borrowNewLoanBlance) {
		this.borrowNewLoanBlance = borrowNewLoanBlance;
	}

	public String getPlatformOverdueTime() {
		return platformOverdueTime;
	}

	public void setPlatformOverdueTime(String platformOverdueTime) {
		this.platformOverdueTime = platformOverdueTime;
	}

	public String getPlatformOverdueMoney() {
		return platformOverdueMoney;
	}

	public void setPlatformOverdueMoney(String platformOverdueMoney) {
		this.platformOverdueMoney = platformOverdueMoney;
	}

	public String getOrderSubNo() {
		return orderSubNo;
	}

	public void setOrderSubNo(String orderSubNo) {
		this.orderSubNo = orderSubNo;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getIsLoan() {
		return isLoan;
	}

	public void setIsLoan(String isLoan) {
		this.isLoan = isLoan;
	}

	public String getLoanRecode() {
		return loanRecode;
	}

	public void setLoanRecode(String loanRecode) {
		this.loanRecode = loanRecode;
	}

	public String getIsOverdue() {
		return isOverdue;
	}

	public void setIsOverdue(String isOverdue) {
		this.isOverdue = isOverdue;
	}

	public String getSourceOfCreRegist() {
		return sourceOfCreRegist;
	}

	public void setSourceOfCreRegist(String sourceOfCreRegist) {
		this.sourceOfCreRegist = sourceOfCreRegist;
	}
}
