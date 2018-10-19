package com.resoft.outinterface.rest.newged.entity;

import java.math.BigDecimal;

/**
 * @description:
 * @Author:jiangmenglun
 * @E-mail:
 * @version:2018年1月29日 下午5:16:40
 */
public class AddOrderRequest {
	private String custName;  //客户名称   
	private String idNum; //证件号码
	private String applyDate;//申请日期
	private Integer productType;//产品类型
	private String productName;//产品名称
	private Integer custType;//客户类型
	private String phoneNo;//移动电话
	private BigDecimal applyAmount;//申请金额
	private String applyPeriod;//申请期限
	private String companyName;//企业名称
	private String companyCardType;//企业证件类型
	private String companyCardNum;//企业证件号码
	private Integer custSource;//客户来源
	private String province;//省
	private String city;//市
	private String district;//县区
	private String contCity;//所在城市
	private String applyId;//申请ID
	private String businessLicence;//营业执照   no
	private String organizationCode;//组织机构代码  no
	private String taxCode;//税务号  no
	private String loanPurpost;//借款用途  
	private BigDecimal approveMonthRate;//月利率
	private String repaymentStyle;//还款方式
	private String applyIdChild;//子订单编号
	
	private String receivableCashDeposit;//应收保证金
	private String receivableGuaranteeFee;//应收担保费
	
	private String legalName;//法人名称
	private String legalMobile;//法人手机号
	private String legalCardNumber;//法人证件号码	


	
	public AddOrderRequest() {
		super();
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
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
	public Integer getProductType() {
		return productType;
	}
	public void setProductType(Integer productType) {
		this.productType = productType;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getCustType() {
		return custType;
	}
	public void setCustType(Integer custType) {
		this.custType = custType;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public BigDecimal getApplyAmount() {
		return applyAmount;
	}
	public void setApplyAmount(BigDecimal applyAmount) {
		this.applyAmount = applyAmount;
	}
	public String getApplyPeriod() {
		return applyPeriod;
	}
	public void setApplyPeriod(String applyPeriod) {
		this.applyPeriod = applyPeriod;
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
	public Integer getCustSource() {
		return custSource;
	}
	public void setCustSource(Integer custSource) {
		this.custSource = custSource;
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
	public String getContCity() {
		return contCity;
	}
	public void setContCity(String contCity) {
		this.contCity = contCity;
	}
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
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
	public BigDecimal getApproveMonthRate() {
		return approveMonthRate;
	}
	public void setApproveMonthRate(BigDecimal approveMonthRate) {
		this.approveMonthRate = approveMonthRate;
	}
	public String getRepaymentStyle() {
		return repaymentStyle;
	}
	public void setRepaymentStyle(String repaymentStyle) {
		this.repaymentStyle = repaymentStyle;
	}
	public String getApplyIdChild() {
		return applyIdChild;
	}
	public void setApplyIdChild(String applyIdChild) {
		this.applyIdChild = applyIdChild;
	}
	public String getReceivableCashDeposit() {
		return receivableCashDeposit;
	}
	public void setReceivableCashDeposit(String receivableCashDeposit) {
		this.receivableCashDeposit = receivableCashDeposit;
	}
	public String getReceivableGuaranteeFee() {
		return receivableGuaranteeFee;
	}
	public void setReceivableGuaranteeFee(String receivableGuaranteeFee) {
		this.receivableGuaranteeFee = receivableGuaranteeFee;
	}
	public String getLegalName() {
		return legalName;
	}
	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}
	public String getLegalMobile() {
		return legalMobile;
	}
	public void setLegalMobile(String legalMobile) {
		this.legalMobile = legalMobile;
	}
	public String getLegalCardNumber() {
		return legalCardNumber;
	}
	public void setLegalCardNumber(String legalCardNumber) {
		this.legalCardNumber = legalCardNumber;
	}
	
}
