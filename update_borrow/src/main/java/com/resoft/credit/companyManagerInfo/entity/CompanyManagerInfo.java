package com.resoft.credit.companyManagerInfo.entity;

import org.hibernate.validator.constraints.Length;

import com.resoft.credit.companyInfo.entity.CompanyInfo;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 企业高管信息Entity
 * @author caoyinglong
 * @version 2016-02-27
 */
public class CompanyManagerInfo extends DataEntity<CompanyManagerInfo> {
	
	private static final long serialVersionUID = 1L;
	private CompanyInfo companyInfo;		// 企业对象
	private String companyId;
	private String managerName;		// 名称
	private String mobileNum;		// 手机号
	private String postName;		// 职位名称
	
	public CompanyManagerInfo() {
		super();
	}

	public CompanyManagerInfo(String id){
		super(id);
	}

	public CompanyInfo getCompanyInfo() {
		return companyInfo;
	}

	public void setCompanyInfo(CompanyInfo companyInfo) {
		this.companyInfo = companyInfo;
	}
	
	@Length(min=0, max=20, message="名称长度必须介于 0 和 20 之间")
	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	
	@Length(min=0, max=15, message="手机号长度必须介于 0 和 15 之间")
	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}
	
	@Length(min=0, max=30, message="职位名称长度必须介于 0 和 30 之间")
	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
}