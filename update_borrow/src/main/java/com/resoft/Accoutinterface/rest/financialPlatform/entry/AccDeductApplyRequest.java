package com.resoft.Accoutinterface.rest.financialPlatform.entry;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class AccDeductApplyRequest extends DataEntity<AccDeductApplyRequest>{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String deductTime;
	private String deductCustId;
	private String deductType;
	private AccRepayList repayList;
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
	public AccRepayList getRepayList() {
		return repayList;
	}
	public void setRepayList(AccRepayList repayList) {
		this.repayList = repayList;
	}
	public String getDeductType() {
		return deductType;
	}
	public void setDeductType(String deductType) {
		this.deductType = deductType;
	}

}
