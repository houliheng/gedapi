package com.resoft.outinterface.rest.ged.entity;

import java.io.Serializable;

public class GedCustInfo implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	
	private String custName; //客户名称
	private String applyId;//申请ID
	private String phoneNo; //电话
	private String applyAmount; //申请金额
	private String idNum; //证件号码
	private String applyDate; //申请日期
	private String contCity; //所在城市
	/**
	 * 新增字段
	 */
	private String productType;//产品类型
	private String productName;//产品名称
	private String companyName;//企业名称
	private String companyCardType;//企业证件类型
	private String companyCardNum;//企业证件号码
	private String applyPeriod ; // 申请期限
	private String custType ; // 客户类型
	private String province ; // 客户类型
	private String city ; // 客户类型
	private String district ; // 客户类型
	
	private String businessLicence ; // 营业执照
	private String organizationCode ; // 组织机构代码
	private String taxCode ; // 税务号
	private String loanPurpost ; // 借款用途
	

	public GedCustInfo() {}

	public GedCustInfo(String custName,String applyId,String phoneNo,String applyAmount,String idNum,String applyDate,String contCity) {
		this.custName = custName;
		this.applyId = applyId;
		this.phoneNo = phoneNo;
		this.applyAmount = applyAmount;
		this.idNum = idNum;
		this.applyDate = applyDate;
		this.contCity = contCity;
	}

	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getApplyAmount() {
		return applyAmount;
	}
	public void setApplyAmount(String applyAmount) {
		this.applyAmount = applyAmount;
	}
	public String getIdNum() {
		return idNum;
	}
	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	public String getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}
	public String getContCity() {
		return contCity;
	}
	public void setContCity(String contCity) {
		this.contCity = contCity;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyCardType() {
		return companyCardType;
	}

	public void setCompanyCardType(String companyCardType) {
		this.companyCardType = companyCardType;
	}

	public String getCompanyCardNum() {
		return companyCardNum;
	}

	public void setCompanyCardNum(String companyCardNum) {
		this.companyCardNum = companyCardNum;
	}

	public String getApplyPeriod() {
		return applyPeriod;
	}

	public void setApplyPeriod(String applyPeriod) {
		this.applyPeriod = applyPeriod;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getBusinessLicence() {
		return businessLicence;
	}

	public void setBusinessLicence(String businessLicence) {
		this.businessLicence = businessLicence;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	public String getLoanPurpost() {
		return loanPurpost;
	}

	public void setLoanPurpost(String loanPurpost) {
		this.loanPurpost = loanPurpost;
	}


	
}
