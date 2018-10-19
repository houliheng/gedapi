package com.resoft.credit.contract.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 合同模板管理entity
 * 
 */
public class ContractTemplate extends DataEntity<ContractTemplate> {
	
	private static final long serialVersionUID = 1L;
	private String  productType;//产品类型
	private String  templateType;//合同模板类型
	private String  templateName;//合同模板名称
	private String 	isuploadfile;//是否已上传模板
	private String 	orgplatform;//归属地
	private String 	orgPlatform;//归属地
	
	
	
	private String  templatemap; //打印模板时将cre系统的参数传递给通过此字段传递给cont系统
	
	
	public String getTemplatemap() {
		return templatemap;
	}
	public void setTemplatemap(String templatemap) {
		this.templatemap = templatemap;
	}
	public String getIsuploadfile() {
		return isuploadfile;
	}
	public void setIsuploadfile(String isuploadfile) {
		this.isuploadfile = isuploadfile;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getTemplateType() {
		return templateType;
	}
	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getOrgplatform() {
		return orgplatform;
	}
	public void setOrgplatform(String orgplatform) {
		this.orgplatform = orgplatform;
	}
	public String getOrgPlatform() {
		return orgPlatform;
	}
	public void setOrgPlatform(String orgPlatform) {
		this.orgPlatform = orgPlatform;
	}
	

	
}