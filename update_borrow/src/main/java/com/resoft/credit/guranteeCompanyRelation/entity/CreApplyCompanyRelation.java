package com.resoft.credit.guranteeCompanyRelation.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 批量借款企业关系表Entity
 * @author lb
 * @version 2018-04-25
 */
public class CreApplyCompanyRelation extends DataEntity<CreApplyCompanyRelation> {
	
	private static final long serialVersionUID = 1L;
	private String applyNo;		// 申请编号
	private String companyId;		// 主借款企业主键,批量借款企业主键
	private String custId;		// 担保公司，担保人，担保企业主键
	private String roleType;		// 1.担保人，2担保人企业，3担保公司
	private String notarizeFlag;		// 确认标记
	private String isConfirm;
	public CreApplyCompanyRelation() {
		super();
	}

	public CreApplyCompanyRelation(String id){
		super(id);
	}

	@Length(min=1, max=32, message="申请编号长度必须介于 1 和 32 之间")
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	
	@Length(min=1, max=32, message="主借款企业主键,批量借款企业主键长度必须介于 1 和 32 之间")
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	@Length(min=0, max=32, message="担保公司，担保人，担保企业主键长度必须介于 0 和 32 之间")
	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}
	@Length(min=0, max=1, message="担保人，2担保人企业，3担保公司长度必须介于 0 和 1 之间")
	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	
	@Length(min=0, max=1, message="确认标记长度必须介于 0 和 1 之间")
	public String getNotarizeFlag() {
		return notarizeFlag;
	}

	public void setNotarizeFlag(String notarizeFlag) {
		this.notarizeFlag = notarizeFlag;
	}

	public String getIsConfirm() {
		return isConfirm;
	}

	public void setIsConfirm(String isConfirm) {
		this.isConfirm = isConfirm;
	}
		
}