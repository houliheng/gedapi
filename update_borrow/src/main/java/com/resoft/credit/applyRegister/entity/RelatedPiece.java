package com.resoft.credit.applyRegister.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 关联进件entity
 * 
 * @version 2016-03-24
 */
public class RelatedPiece extends DataEntity<RelatedPiece> {

	private static final long serialVersionUID = 1L;
	private String roleType;// 关联关系
	private String custId;//客户编号
	private String custName; // 客户名称
	private String idType; // 证件类型
	private String idNum; // 证件号
	private String contractAmount;// 合同金额
	private String registerDate; // 登记日期
	private String orgId;// 登机门店
	private String applyStatus; // 客户申请状态（字典类型：APPLY_STATUS）

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public RelatedPiece() {
		super();
	}

	public RelatedPiece(String id) {
		super(id);
	}

	@Length(min = 0, max = 20, message = "客户名称长度必须介于 0 和 20 之间")
	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	@Length(min = 0, max = 10, message = "证件类型长度必须介于 0 和 10 之间")
	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	@Length(min = 0, max = 18, message = "证件号长度必须介于 0 和 18 之间")
	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}

	@Length(min = 0, max = 4, message = "客户申请状态（字典类型：APPLY_STATUS）长度必须介于 0 和 4 之间")
	public String getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}

	public String getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(String contractAmount) {
		this.contractAmount = contractAmount;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

}