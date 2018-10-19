package com.resoft.postloan.overdueHandle.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 贷后逾期处理Entity
 * @author lixing
 * @version 2017-01-04
 */
public class OverdueHandle extends DataEntity<OverdueHandle> {
	
	private static final long serialVersionUID = 1L;
	private String custName;		// 客户名称
	private String contractNo;		// 合同编号
	private String periodNum;		// 期数
	private String handleStatus;		// 逾期处理状态（字典类型：）
	private String procInsId;		// 流程实例ID
	
	public OverdueHandle() {
		super();
	}

	public OverdueHandle(String id){
		super(id);
	}

	@Length(min=0, max=20, message="客户名称长度必须介于 0 和 20 之间")
	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}
	
	@Length(min=1, max=32, message="合同编号长度必须介于 1 和 32 之间")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	@Length(min=0, max=10, message="期数长度必须介于 0 和 10 之间")
	public String getPeriodNum() {
		return periodNum;
	}

	public void setPeriodNum(String periodNum) {
		this.periodNum = periodNum;
	}
	
	@Length(min=0, max=4, message="逾期处理状态（字典类型：）长度必须介于 0 和 4 之间")
	public String getHandleStatus() {
		return handleStatus;
	}

	public void setHandleStatus(String handleStatus) {
		this.handleStatus = handleStatus;
	}
	
	@Length(min=0, max=32, message="流程实例ID长度必须介于 0 和 32 之间")
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
}