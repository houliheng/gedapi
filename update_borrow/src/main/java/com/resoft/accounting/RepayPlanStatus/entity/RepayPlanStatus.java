package com.resoft.accounting.RepayPlanStatus.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class RepayPlanStatus extends DataEntity<RepayPlanStatus>{
	private static final long serialVersionUID = 1L;
	private String createTime;
	private String contractNo;
	private Integer periodNum;
	private String repayPeriodStatus;
	
	
	
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public Integer getPeriodNum() {
		return periodNum;
	}
	public void setPeriodNum(Integer periodNum) {
		this.periodNum = periodNum;
	}
	public String getRepayPeriodStatus() {
		return repayPeriodStatus;
	}
	public void setRepayPeriodStatus(String repayPeriodStatus) {
		this.repayPeriodStatus = repayPeriodStatus;
	}
	
	
	
}
