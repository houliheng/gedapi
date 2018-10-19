package com.resoft.outinterface.rest.ged.entity.response;

public class GedRepayPlanResponse {
	private String managementFee;		// 还款金额-账户管理费(差额还款金额-合计)
	private String periodNum;		// 期数
	private String deductDate;		// 计划扣款日期
	
	public GedRepayPlanResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GedRepayPlanResponse(String managementFee, String periodNum, String deductDate) {
		super();
		this.managementFee = managementFee;
		this.periodNum = periodNum;
		this.deductDate = deductDate;
	}
	public String getManagementFee() {
		return managementFee;
	}
	public void setManagementFee(String managementFee) {
		this.managementFee = managementFee;
	}
	public String getPeriodNum() {
		return periodNum;
	}
	public void setPeriodNum(String periodNum) {
		this.periodNum = periodNum;
	}
	public String getDeductDate() {
		return deductDate;
	}
	public void setDeductDate(String deductDate) {
		this.deductDate = deductDate;
	}
	
	

}
