package com.resoft.credit.checkApproveUnion.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 授信企业个人关系Entity
 * @author wangguodong
 * @version 2016-12-14
 */
public class CompanyCustInfoRelated extends DataEntity<CompanyCustInfoRelated> {
	
	private static final long serialVersionUID = 1L;
	private String applyNo;		// 申请编号
	private String approId;		// 申请编号
	private String custId;		// 客户编号
	private String companyId;		// 关联信息编号
	private String custName;		// 客户名称
	public String getApplyNo() {
		return applyNo;
	}
	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	public String getApproId() {
		return approId;
	}
	public void setApproId(String approId) {
		this.approId = approId;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	
	
	
}