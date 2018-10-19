package com.resoft.outinterface.SV.server.entry.request;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.thinkgem.jeesite.common.utils.IdGen;

/**
 * SV回盘关联企业Entity
 * 
 * @author admin
 * @version 2016-04-21
 */
@XmlAccessorType(XmlAccessType.NONE)
public class SVCompanyRelated implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String applyNo; // 申请编号
	private Date createDate; // 创建日期
	private Date updateDate; // 更新日期
	private String delFlag; // 删除标记（0：正常；1：删除；2：审核）

	@XmlElement(required = false, name = "ORGANIZATION_NO")
	private String organizationNo; // 主企业组织机构代码
	@XmlElement(required = false, name = "RELATED_COMPANY_NAME")
	private String relatedCompanyName; // 被关联企业名称
	@XmlElement(required = false, name = "UN_SOC_CREDIT_NO")
	private String unSocCreditNo; // 统一社会信用代码
	@XmlElement(required = false, name = "RELATED_COMPANY_ORG")
	private String relatedCompanyOrg; // 被关联企业组织机构代码
	@XmlElement(required = false, name = "BUSI_LIC_REG_NO")
	private String busiLicRegNo; // 营业执照注册号
	@XmlElement(required = false, name = "RELATED_COMPANY_TYPE")
	private String relatedCompanyType; // 被关联企业与客户企业关系
	@XmlElement(required = false, name = "REGISTER_CODE")
	private String registerCode; // 登记注册代码

	public SVCompanyRelated() {
		super();
	}

	public String getOrganizationNo() {
		return organizationNo;
	}

	public void setOrganizationNo(String organizationNo) {
		this.organizationNo = organizationNo;
	}

	public String getRelatedCompanyName() {
		return relatedCompanyName;
	}

	public void setRelatedCompanyName(String relatedCompanyName) {
		this.relatedCompanyName = relatedCompanyName;
	}

	public String getUnSocCreditNo() {
		return unSocCreditNo;
	}

	public void setUnSocCreditNo(String unSocCreditNo) {
		this.unSocCreditNo = unSocCreditNo;
	}

	public String getRelatedCompanyOrg() {
		return relatedCompanyOrg;
	}

	public void setRelatedCompanyOrg(String relatedCompanyOrg) {
		this.relatedCompanyOrg = relatedCompanyOrg;
	}

	public String getBusiLicRegNo() {
		return busiLicRegNo;
	}

	public void setBusiLicRegNo(String busiLicRegNo) {
		this.busiLicRegNo = busiLicRegNo;
	}

	public String getRelatedCompanyType() {
		return relatedCompanyType;
	}

	public void setRelatedCompanyType(String relatedCompanyType) {
		this.relatedCompanyType = relatedCompanyType;
	}

	public String getRegisterCode() {
		return registerCode;
	}

	public void setRegisterCode(String registerCode) {
		this.registerCode = registerCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public void preInsert() {
		setId(IdGen.uuid());
		this.createDate = new Date();
	}

}