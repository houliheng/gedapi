/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.resoft.credit.loanApply.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 联系人信息表Entity
 * @author lirongchao
 * @version 2015-10-22
 */
public class CreCustContact extends DataEntity<CreCustContact> {
	
	/**
	 * @reqno: H1510080107
	 * @date-designer:20151022-lirongchao
	 * @date-author:20151022-lirongchao: 需求-联系人列表数据项：复选择框、姓名、人员类型、移动电话、是否知晓本次贷款、住宅电话、单位名称
											列表排序：创建时间降序
											表头按钮：新增、修改、删除、详情
										当前环节-创建联系人信息表的Entity层
	 */
	private static final long serialVersionUID = 1L;
	private String applyId;		// 申请ID
	private String contactName;		// 联系人姓名
	private String contactRelations;		// 联系人类型（字典类型：CONTACT_RELATIONS）
	private String conMobil;		// 移动电话
	private String isKnow;		// 是否知晓本次贷款（字典类型：yes_no）
	private String phone;		// 住宅电话
	private String homeDetails;		// 住宅地址
	private String deptName;		// 单位名称
	private String deptAddress;		// 单位地址
	private String department;		// 部门名称
	private String postName;		// 职位名称
	private Date createTime;		// 创建时间
	
	//扩展字段
	//联系人类型Label
	/**
	 * @reqno:H1511100082
	 * @date-designer:2015年11月13日-songmin
	 * @date-author:2015年11月13日-songmin:字典类型字段不和字典类型Label共用属性
	 */
	private String contactRelationsLabel;
	private String isKnowLabel;
	/**
	 * @return the isKnowLabel
	 */
	public String getIsKnowLabel() {
		return isKnowLabel;
	}

	/**
	 * @param isKnowLabel the isKnowLabel to set
	 */
	public void setIsKnowLabel(String isKnowLabel) {
		this.isKnowLabel = isKnowLabel;
	}

	/**
	 * @return the contactRelationsLabel
	 */
	public String getContactRelationsLabel() {
		return contactRelationsLabel;
	}

	/**
	 * @param contactRelationsLabel the contactRelationsLabel to set
	 */
	public void setContactRelationsLabel(String contactRelationsLabel) {
		this.contactRelationsLabel = contactRelationsLabel;
	}

	public CreCustContact() {
		super();
	}

	public CreCustContact(String id){
		super(id);
	}

	@Length(min=0, max=32, message="申请ID长度必须介于 0 和 32 之间")
	public String getApplyNo() {
		return applyId;
	}

	public void setApplyNo(String applyId) {
		this.applyId = applyId;
	}
	
	@Length(min=0, max=400, message="联系人姓名长度必须介于 0 和 400 之间")
	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	
	@Length(min=0, max=400, message="联系人类型（字典类型：CONTACT_RELATIONS）长度必须介于 0 和 400 之间")
	public String getContactRelations() {
		return contactRelations;
	}

	public void setContactRelations(String contactRelations) {
		this.contactRelations = contactRelations;
	}
	
	@Length(min=0, max=200, message="移动电话长度必须介于 0 和 200 之间")
	public String getConMobil() {
		return conMobil;
	}

	public void setConMobil(String conMobil) {
		this.conMobil = conMobil;
	}
	
	@Length(min=0, max=160, message="是否知晓本次贷款（字典类型：yes_no）长度必须介于 0 和 160 之间")
	public String getIsKnow() {
		return isKnow;
	}

	public void setIsKnow(String isKnow) {
		this.isKnow = isKnow;
	}
	
	@Length(min=0, max=20, message="住宅电话长度必须介于 0 和 20 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=0, max=200, message="住宅地址长度必须介于 0 和 200 之间")
	public String getHomeDetails() {
		return homeDetails;
	}

	public void setHomeDetails(String homeDetails) {
		this.homeDetails = homeDetails;
	}
	
	@Length(min=0, max=400, message="单位名称长度必须介于 0 和 400 之间")
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	@Length(min=0, max=2000, message="单位地址长度必须介于 0 和 2000 之间")
	public String getDeptAddress() {
		return deptAddress;
	}

	public void setDeptAddress(String deptAddress) {
		this.deptAddress = deptAddress;
	}
	
	@Length(min=0, max=100, message="部门名称长度必须介于 0 和 100 之间")
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	@Length(min=0, max=100, message="职位名称长度必须介于 0 和 100 之间")
	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}