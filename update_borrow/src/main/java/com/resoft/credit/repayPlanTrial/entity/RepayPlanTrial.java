package com.resoft.credit.repayPlanTrial.entity;

public class RepayPlanTrial {
	
	private String amountValue; 	// 标的金额
	private String productTypeCode; 	//借款类型
	private String periodValue; 		//期数
	private String loanRepayType; 		//还款类型
	private String contractRate; 	//合同月利率
	
	
	public String getAmountValue() {
		return amountValue;
	}
	public void setAmountValue(String amountValue) {
		this.amountValue = amountValue;
	}
	public String getProductTypeCode() {
		return productTypeCode;
	}
	public void setProductTypeCode(String productTypeCode) {
		this.productTypeCode = productTypeCode;
	}
	public String getPeriodValue() {
		return periodValue;
	}
	public void setPeriodValue(String periodValue) {
		this.periodValue = periodValue;
	}
	public String getLoanRepayType() {
		return loanRepayType;
	}
	public void setLoanRepayType(String loanRepayType) {
		this.loanRepayType = loanRepayType;
	}
	public String getContractRate() {
		return contractRate;
	}
	public void setContractRate(String contractRate) {
		this.contractRate = contractRate;
	}

}


