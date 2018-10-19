package com.resoft.multds.credit.GuarantyContract.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 合同模板管理entity
 * 
 */
public class GuarantyContract extends DataEntity<GuarantyContract> {
	
	private static final long serialVersionUID = 1L;
	private String  productType;//产品类型
	private String  templateType;//合同模板类型
	private String  templateName;//合同模板名称
	private String 	isuploadfile;//是否已上传模板
	private String 	contractOrgplatform;//归属地
	private String contractNo;
	
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
	public String getContractOrgplatform() {
		return contractOrgplatform;
	}
	public void setContractOrgplatform(String contractOrgplatform) {
		this.contractOrgplatform = contractOrgplatform;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
}