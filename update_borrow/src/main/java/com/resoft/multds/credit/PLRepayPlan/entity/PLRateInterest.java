package com.resoft.multds.credit.PLRepayPlan.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 申请借款信息表Entity
 * @author chenshaojia
 * @version 2016-03-01
 */
public class PLRateInterest extends DataEntity<PLRateInterest> {
	
	private static final long serialVersionUID = 1L;
	private String loanRepayType;
	private String loanRepayDesc;
	private String periodValue;
	private String rateInterest;
	public String getLoanRepayType() {
		return loanRepayType;
	}
	public void setLoanRepayType(String loanRepayType) {
		this.loanRepayType = loanRepayType;
	}
	public String getLoanRepayDesc() {
		return loanRepayDesc;
	}
	public void setLoanRepayDesc(String loanRepayDesc) {
		this.loanRepayDesc = loanRepayDesc;
	}
	public String getPeriodValue() {
		return periodValue;
	}
	public void setPeriodValue(String periodValue) {
		this.periodValue = periodValue;
	}
	public String getRateInterest() {
		return rateInterest;
	}
	public void setRateInterest(String rateInterest) {
		this.rateInterest = rateInterest;
	}

	
}