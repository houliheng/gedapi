package com.resoft.accounting.advanceGed.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 冠E贷提前还款Entity
 * @author gsh
 * @version 2018-06-24
 */
public class AccAdvanceGed extends DataEntity<AccAdvanceGed> {
	
	private static final long serialVersionUID = 1L;
	private String contractNo;		// contract_no
	private String periodNum;		// period_num
	private String advanceFlag;		// advance_flag
	private String dataDate;
	public AccAdvanceGed() {
		super();
	}

	public AccAdvanceGed(String id){
		super(id);
	}

	@Length(min=0, max=32, message="contract_no长度必须介于 0 和 32 之间")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	@Length(min=0, max=10, message="period_num长度必须介于 0 和 10 之间")
	public String getPeriodNum() {
		return periodNum;
	}

	public void setPeriodNum(String periodNum) {
		this.periodNum = periodNum;
	}
	
	@Length(min=0, max=1, message="advance_flag长度必须介于 0 和 1 之间")
	public String getAdvanceFlag() {
		return advanceFlag;
	}

	public void setAdvanceFlag(String advanceFlag) {
		this.advanceFlag = advanceFlag;
	}

	public String getDataDate() {
		return dataDate;
	}

	public void setDataDate(String dataDate) {
		this.dataDate = dataDate;
	}
	
	
}