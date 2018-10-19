package com.resoft.credit.guaranteeRelation.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 担保信息关联表Entity
 * @author jml
 * @version 2018-04-17
 */
public class GuaranteeRelation extends DataEntity<GuaranteeRelation> {
	
	private static final long serialVersionUID = 1L;
	private String applyNo;		// 申请编号
	private String roleType;		// 角色类型
	private String custId;		// 客户ID(主借人，共借人，担保人，主借企业，担保企业)
	private String custName;		// 客户名称
	private String companyId; //批量企业ID
	//非表字段
	private String idNum;//身份证号   	统一社会
	private String mobileNum;//手机号
	private String gedAccount;//冠易贷账号
	private String orderCode;
	private String isConfirm;
	public GuaranteeRelation() {
		super();
	}

	public GuaranteeRelation(String id){
		super(id);
	}

	@Length(min=1, max=32, message="申请编号长度必须介于 1 和 32 之间")
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	
	@Length(min=0, max=4, message="角色类型长度必须介于 0 和 4 之间")
	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	
	@Length(min=1, max=32, message="客户ID(主借人，共借人，担保人，主借企业，担保企业)长度必须介于 1 和 32 之间")
	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}
	
	@Length(min=0, max=30, message="客户名称长度必须介于 0 和 30 之间")
	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}

	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	public String getGedAccount() {
		return gedAccount;
	}

	public void setGedAccount(String gedAccount) {
		this.gedAccount = gedAccount;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getIsConfirm() {
		return isConfirm;
	}

	public void setIsConfirm(String isConfirm) {
		this.isConfirm = isConfirm;
	}

	
}