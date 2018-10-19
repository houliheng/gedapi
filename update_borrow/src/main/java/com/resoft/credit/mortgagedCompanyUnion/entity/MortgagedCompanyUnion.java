package com.resoft.credit.mortgagedCompanyUnion.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 联合授信冠e通担保企业Entity
 * @author wangguodong
 * @version 2016-12-26
 */
public class MortgagedCompanyUnion extends DataEntity<MortgagedCompanyUnion> {
	
	private static final long serialVersionUID = 1L;
	private String applyNo;		// apply_no
	private String approveId;		// 批复ID
	private String relaton;		// 与借款公司关系
	private String companyDesc;		// 企业介绍
	private String incomeState;		// 经营状况
	private String operationState;		// 收入状况
	
	public MortgagedCompanyUnion() {
		super();
	}

	public MortgagedCompanyUnion(String id){
		super(id);
	}

	@Length(min=0, max=32, message="apply_no长度必须介于 0 和 32 之间")
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	
	@Length(min=1, max=32, message="批复ID长度必须介于 1 和 32 之间")
	public String getApproveId() {
		return approveId;
	}

	public void setApproveId(String approveId) {
		this.approveId = approveId;
	}
	
	@Length(min=0, max=500, message="与借款公司关系长度必须介于 0 和 500 之间")
	public String getRelaton() {
		return relaton;
	}

	public void setRelaton(String relaton) {
		this.relaton = relaton;
	}
	
	@Length(min=0, max=2000, message="企业介绍长度必须介于 0 和 2000 之间")
	public String getCompanyDesc() {
		return companyDesc;
	}

	public void setCompanyDesc(String companyDesc) {
		this.companyDesc = companyDesc;
	}
	
	@Length(min=0, max=2000, message="经营状况长度必须介于 0 和 2000 之间")
	public String getIncomeState() {
		return incomeState;
	}

	public void setIncomeState(String incomeState) {
		this.incomeState = incomeState;
	}
	
	@Length(min=0, max=2000, message="收入状况长度必须介于 0 和 2000 之间")
	public String getOperationState() {
		return operationState;
	}

	public void setOperationState(String operationState) {
		this.operationState = operationState;
	}
	
}