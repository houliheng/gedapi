package com.resoft.outinterface.SV.server.entry.request;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.thinkgem.jeesite.common.utils.IdGen;

/**
 * sv回盘联系人Entity
 * 
 * @author admin
 * @version 2016-04-21
 */
@XmlAccessorType(XmlAccessType.NONE)
public class SVApplyContact implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String applyNo; // 申请编号
	private Date createDate; // 创建日期
	private Date updateDate; // 更新日期
	private String delFlag; // 删除标记（0：正常；1：删除；2：审核）
	@XmlElement(required = true, name = "CONTACT_NAME")
	private String contactName; // 联系人名称
	@XmlElement(required = true, name = "CONTACT_MOBILE")
	private String contactMobile; // 联系人手机号
	@XmlElement(required = true, name = "CONTACT_RELATIONS")
	private String contactRelations; // 联系人类型（字典类型：CONTACT_RELATIONS）

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getContactRelations() {
		return contactRelations;
	}

	public void setContactRelations(String contactRelations) {
		this.contactRelations = contactRelations;
	}

	public void preInsert() {
		setId(IdGen.uuid());
		this.createDate = new Date();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}