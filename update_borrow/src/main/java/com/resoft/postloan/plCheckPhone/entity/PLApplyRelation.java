package com.resoft.postloan.plCheckPhone.entity;

import org.hibernate.validator.constraints.Length;

import com.resoft.multds.credit.plCustinfo.entity.PLCustInfo;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 申请客户关联信息表Entity
 * 
 * @author caoyinglong
 * @version 2016-02-29
 */
public class PLApplyRelation extends DataEntity<PLApplyRelation> {

	private static final long serialVersionUID = 1L;
	private String applyNo; // 申请编号
	private String roleType; // 角色类型(主借人，共借人，担保人，配偶，主借企业，担保企业)
	private String relationForApply; // 与主借人关系(亲属，朋友，同事，其他)
	private String custId;
	private String custName;
	private PLCustInfo custInfo; // 客户ID(主借人，共借人，担保人，配偶，主借企业，担保企业)
	// private CompanyInfo companyInfo;//
	private String description; // 备注
	private String visitCount;// 访问次数
	// private ApplyLoanStatus applyLoanStatus;
	private String matchRoleType;// 匹配的角色类型
	private String matchCustName;// 匹配的客户名称
	private String matchApplyNo;// 匹配的申请编号

	public PLApplyRelation() {
		super();
	}

	public PLApplyRelation(String id) {
		super(id);
	}

	public PLCustInfo getCustInfo() {
		return custInfo;
	}

	public void setCustInfo(PLCustInfo custInfo) {
		this.custInfo = custInfo;
	}

	@Length(min = 1, max = 32, message = "申请编号长度必须介于 1 和 32 之间")
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	@Length(min = 1, max = 32, message = "申请编号长度必须介于 1 和 32 之间")
	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Length(min = 0, max = 2, message = "角色类型(主借人，共借人，担保人，配偶，主借企业，担保企业)长度必须介于 0 和 2 之间")
	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	@Length(min = 0, max = 4, message = "与主借人关系(亲属，朋友，同事，其他)长度必须介于 0 和 4 之间")
	public String getRelationForApply() {
		return relationForApply;
	}

	public void setRelationForApply(String relationForApply) {
		this.relationForApply = relationForApply;
	}

	@Length(min = 0, max = 1000, message = "备注长度必须介于 0 和 1000 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getVisitCount() {
		return visitCount;
	}

	public void setVisitCount(String visitCount) {
		this.visitCount = visitCount;
	}

	public String getMatchRoleType() {
		return matchRoleType;
	}

	public void setMatchRoleType(String matchRoleType) {
		this.matchRoleType = matchRoleType;
	}

	public String getMatchCustName() {
		return matchCustName;
	}

	public void setMatchCustName(String matchCustName) {
		this.matchCustName = matchCustName;
	}

	public String getMatchApplyNo() {
		return matchApplyNo;
	}

	public void setMatchApplyNo(String matchApplyNo) {
		this.matchApplyNo = matchApplyNo;
	}

}