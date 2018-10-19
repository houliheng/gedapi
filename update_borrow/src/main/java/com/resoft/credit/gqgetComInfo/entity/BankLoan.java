package com.resoft.credit.gqgetComInfo.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
/**
 * 冠E通Entity 银行贷款信息
 * @author wanghong
 * @version 2016-09-19
 */
public class BankLoan extends DataEntity<BankLoan> {
	
	private static final long serialVersionUID = 1L;
	private String applyNo;		// 申请编号
	private String isLoan;  //是否贷款
	private String loanRecode; //共几笔贷款记录
	private String isOverdue; //是否有逾期
	private String sourceOfCreRegist; //征信记录等级
	
	public String getApplyNo() {
		return applyNo;
	}
	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
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
