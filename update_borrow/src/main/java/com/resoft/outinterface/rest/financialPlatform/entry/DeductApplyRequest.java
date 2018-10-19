package com.resoft.outinterface.rest.financialPlatform.entry;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class DeductApplyRequest extends DataEntity<DeductApplyRequest>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String deductTime;
	private String deductCustId;
	private RepayList repayList;
	public String getDeductTime() {
		return deductTime;
	}
	public void setDeductTime(String deductTime) {
		this.deductTime = deductTime;
	}
	public String getDeductCustId() {
		return deductCustId;
	}
	public void setDeductCustId(String deductCustId) {
		this.deductCustId = deductCustId;
	}
	public RepayList getRepayList() {
		return repayList;
	}
	public void setRepayList(RepayList repayList) {
		this.repayList = repayList;
	}
}
