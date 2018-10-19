package com.resoft.outinterface.rest.ged.entity;

import java.io.Serializable;

/**
* @author guoshaohua
* @version 2018年4月26日 下午1:10:40
* 
*/
public class LoanBankInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String isLoan;  //是否贷款
	private String loanRecode; //共几笔贷款记录
	private String isOverdue; //是否有逾期
	private String sourceOfCreRegist; //征信记录等级
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
