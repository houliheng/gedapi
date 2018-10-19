package com.resoft.credit.relatedCompanyInfo.entity;

import org.hibernate.validator.constraints.Length;

import com.resoft.credit.companyInfo.entity.CompanyInfo;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 企业关联企业信息Entity
 * @author caoyinglong
 * @version 2016-02-29
 */
public class CompanyRelated extends DataEntity<CompanyRelated> {
	
	private static final long serialVersionUID = 1L;
	private CompanyInfo companyInfo;		// 企业
	private String companyId;		// 企业id
	private String relatedCompanyName;	// 被关联企业名称
	private String relatedCompanyOrg;		// 被关联企业组织机构代码
	private String relatedCompanyType;		// 被关联企业与客户企业关系
	
	public CompanyRelated() {
		super();
	}

	public CompanyRelated(String id){
		super(id);
	}

	public CompanyInfo getCompanyInfo() {
		return companyInfo;
	}

	public void setCompanyInfo(CompanyInfo companyInfo) {
		this.companyInfo = companyInfo;
	}
	
	@Length(min=0, max=300, message="被关联企业名称长度必须介于 0 和 300 之间")
	public String getRelatedCompanyName() {
		return relatedCompanyName;
	}

	public void setRelatedCompanyName(String relatedCompanyName) {
		this.relatedCompanyName = relatedCompanyName;
	}
	
	@Length(min=0, max=50, message="被关联企业组织机构代码长度必须介于 0 和 50 之间")
	public String getRelatedCompanyOrg() {
		return relatedCompanyOrg;
	}

	public void setRelatedCompanyOrg(String relatedCompanyOrg) {
		this.relatedCompanyOrg = relatedCompanyOrg;
	}
	
	@Length(min=0, max=50, message="被关联企业与客户企业关系长度必须介于 0 和 50 之间")
	public String getRelatedCompanyType() {
		return relatedCompanyType;
	}

	public void setRelatedCompanyType(String relatedCompanyType) {
		this.relatedCompanyType = relatedCompanyType;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
}