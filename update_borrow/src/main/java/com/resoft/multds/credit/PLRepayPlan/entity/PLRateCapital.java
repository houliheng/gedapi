package com.resoft.multds.credit.PLRepayPlan.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 申请借款信息表Entity
 * 
 * @author chenshaojia
 * @version 2016-03-01
 */
public class PLRateCapital extends DataEntity<PLRateCapital> {

	private static final long serialVersionUID = 1L;
	private String loanRepayType;
	private String loanRepayDesc;
	private String periodValue;
	private String periodNum;
	private String rateCapitalCurr;
	private String rateCapitalRemain;
	private PLRateInterest plRateInterest;

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

	public String getPeriodNum() {
		return periodNum;
	}

	public void setPeriodNum(String periodNum) {
		this.periodNum = periodNum;
	}

	public String getRateCapitalCurr() {
		return rateCapitalCurr;
	}

	public void setRateCapitalCurr(String rateCapitalCurr) {
		this.rateCapitalCurr = rateCapitalCurr;
	}

	public String getRateCapitalRemain() {
		return rateCapitalRemain;
	}

	public void setRateCapitalRemain(String rateCapitalRemain) {
		this.rateCapitalRemain = rateCapitalRemain;
	}

	public PLRateInterest getPlRateInterest() {
		return plRateInterest;
	}

	public void setPlRateInterest(PLRateInterest plRateInterest) {
		this.plRateInterest = plRateInterest;
	}

    
}