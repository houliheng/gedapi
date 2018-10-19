package com.resoft.credit.gqgetGuarantorCompany.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 冠e通担保企业Entity
 * @author wangguodong
 * @version 2016-09-29
 */
public class GqgetGuarantorCompany extends DataEntity<GqgetGuarantorCompany> {
	
	private static final long serialVersionUID = 1L;
	private String applyNo;		// apply_no
	private String relaton;		// 与借款公司关系
	private String companyDesc;		// 企业介绍
	private String incomeState;		// 经营状况
	private String operationState;		// 收入状况
	
	public GqgetGuarantorCompany() {
		super();
	}

	public GqgetGuarantorCompany(String id){
		super(id);
	}

	@Length(min=0, max=32, message="apply_no长度必须介于 0 和 32 之间")
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Length(min=0, max=500, message="relaton长度必须介于 0 和 500 之间")
	public String getRelaton() {
		return relaton;
	}

	public void setRelaton(String relaton) {
		this.relaton = relaton;
	}
	
	@Length(min=0, max=2000, message="company_desc长度必须介于 0 和 2000 之间")
	public String getCompanyDesc() {
		return companyDesc;
	}

	public void setCompanyDesc(String companyDesc) {
		this.companyDesc = companyDesc;
	}
	
	@Length(min=0, max=2000, message="income_state长度必须介于 0 和 2000 之间")
	public String getIncomeState() {
		return incomeState;
	}

	public void setIncomeState(String incomeState) {
		this.incomeState = incomeState;
	}
	
	@Length(min=0, max=2000, message="operation_state长度必须介于 0 和 2000 之间")
	public String getOperationState() {
		return operationState;
	}

	public void setOperationState(String operationState) {
		this.operationState = operationState;
	}
	
}