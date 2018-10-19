package com.resoft.outinterface.rest.ged.entity;

import java.io.Serializable;

public class GedApproveState implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	private String custName;
	private String applyId;
	private String approveState;
	private String changeDate;

	public GedApproveState(String custName,String applyId,String approveState,String changeDate){
		this.custName = custName;
		this.applyId = applyId;
		this.approveState = approveState;
		this.changeDate = changeDate;
	}

	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public String getApproveState() {
		return approveState;
	}
	public void setApproveState(String approveState) {
		this.approveState = approveState;
	}
	public String getChangeDate() {
		return changeDate;
	}
	public void setChangeDate(String changeDate) {
		this.changeDate = changeDate;
	}

}
