package com.resoft.credit.contactInfo.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 联系人信息Entity
 * 
 * @author chenshaojia
 * @version 2016-03-11
 */
public class ContactInfo extends DataEntity<ContactInfo> {

	private static final long serialVersionUID = 1L;
	private String relationId; // 关系ID(主借人关系)
	private String sexNo; // 性别（字典类型：sex）
	private String contactName; // 联系人名称
	private String contactMobile; // 联系人手机号
	private String contactRelations; // 联系人类型（字典类型：CONTACT_RELATIONS）
	private String isKnow; // 是否知晓本次贷款
	private String housePhoneNo; // 住宅电话
	private String houseAddressDetails; // 住宅地址
	private String deptName; // 单位名称
	private String deptAddressDetails; // 单位地址
	private String department; // 部门名称
	private String postName; // 职位名称
	private String visitCount;//电核次数

	public ContactInfo() {
		super();
	}

	public ContactInfo(String id) {
		super(id);
	}

	@Length(min = 1, max = 32, message = "关系ID长度必须介于 1 和 32 之间")
	public String getRelationId() {
		return relationId;
	}

	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}

	@Length(min = 0, max = 1, message = "性别（字典类型：sex）长度必须介于 0 和 1 之间")
	public String getSexNo() {
		return sexNo;
	}

	public void setSexNo(String sexNo) {
		this.sexNo = sexNo;
	}

	@Length(min = 0, max = 20, message = "联系人名称长度必须介于 0 和 20 之间")
	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	@Length(min = 0, max = 15, message = "联系人手机号长度必须介于 0 和 15 之间")
	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	@Length(min = 0, max = 10, message = "联系人类型（字典类型：CONTACT_RELATIONS）长度必须介于 0 和 10 之间")
	public String getContactRelations() {
		return contactRelations;
	}

	public void setContactRelations(String contactRelations) {
		this.contactRelations = contactRelations;
	}

	@Length(min = 0, max = 1, message = "是否知晓本次贷款长度必须介于 0 和 1 之间")
	public String getIsKnow() {
		return isKnow;
	}

	public void setIsKnow(String isKnow) {
		this.isKnow = isKnow;
	}

	@Length(min = 0, max = 15, message = "住宅电话长度必须介于 0 和 15 之间")
	public String getHousePhoneNo() {
		return housePhoneNo;
	}

	public void setHousePhoneNo(String housePhoneNo) {
		this.housePhoneNo = housePhoneNo;
	}

	@Length(min = 0, max = 300, message = "住宅地址长度必须介于 0 和 300 之间")
	public String getHouseAddressDetails() {
		return houseAddressDetails;
	}

	public void setHouseAddressDetails(String houseAddressDetails) {
		this.houseAddressDetails = houseAddressDetails;
	}

	@Length(min = 0, max = 300, message = "单位名称长度必须介于 0 和 300 之间")
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Length(min = 0, max = 300, message = "单位地址长度必须介于 0 和 300 之间")
	public String getDeptAddressDetails() {
		return deptAddressDetails;
	}

	public void setDeptAddressDetails(String deptAddressDetails) {
		this.deptAddressDetails = deptAddressDetails;
	}

	@Length(min = 0, max = 30, message = "部门名称长度必须介于 0 和 30 之间")
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@Length(min = 0, max = 30, message = "职位名称长度必须介于 0 和 30 之间")
	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public String getVisitCount() {
		return visitCount;
	}

	public void setVisitCount(String visitCount) {
		this.visitCount = visitCount;
	}

}