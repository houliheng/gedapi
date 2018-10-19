package com.resoft.credit.gedApiUser.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 冠E贷账号Entity
 * @author lb
 * @version 2018-05-23
 */
public class CreGedapiUser extends DataEntity<CreGedapiUser> {
	
	private static final long serialVersionUID = 1L;
	private String custId;		// 客户，企业主键
	private String roleType;		// 1.个人，2企业
	private String custName;		// 名字:客户或企业
	private String phone;		// 手机号:客户或企业
	private String idNum;		// 企业:社会统一信息代码   个人:身份证号码
	private String gedNumber;		// 冠易贷账号
	
	public CreGedapiUser() {
		super();
	}

	public CreGedapiUser(String id){
		super(id);
	}

	@Length(min=0, max=32, message="客户，企业主键长度必须介于 0 和 32 之间")
	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}
	
	@Length(min=0, max=1, message="个人，2企业长度必须介于 0 和 1 之间")
	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	
	@Length(min=1, max=32, message="名字:客户或企业长度必须介于 1 和 32 之间")
	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}
	
	@Length(min=1, max=32, message="手机号:客户或企业长度必须介于 1 和 32 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=0, max=50, message="企业:社会统一信息代码   个人:身份证号码长度必须介于 0 和 50 之间")
	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	
	@Length(min=0, max=50, message="冠易贷账号长度必须介于 0 和 50 之间")
	public String getGedNumber() {
		return gedNumber;
	}

	public void setGedNumber(String gedNumber) {
		this.gedNumber = gedNumber;
	}
	
}