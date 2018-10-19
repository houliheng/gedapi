package com.resoft.outinterface.rest.GQget.client.entry.request.issuingTender;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

/**
 * 企业信息
 */
@JsonAutoDetect(fieldVisibility = Visibility.PUBLIC_ONLY)
public class LoanCompany {
	private String loan_company_info;// 企业全称或简称
	private String loan_license_number;// 营业执照号码
	private String corporation_name;// 法定代表人名称
	private String founding_date;// 成立时间
	private String registered_capital;// 注册资本
	private String paid_capital;//实缴资本
	private String registered_address;//注册地址
	private String corporation_cert_no;//法人身份证
	private String corporation_address;//法人地址
	
	//新增推送字段
	private String corporation_industry;//法人行业 国标代码
	private String operating_area;//经营区域
	private Integer busi_type;//主体性质：1-个人，2-企业
	private String administration_punish_info;//借款人受刑事、行政处罚情况
	private String other_plat_loan_desc;//借款人在其他网贷平台借款余额
	private String this_overdue_times;//本平台逾期次数
	private String this_overdue_money;//本平台逾期金额
	private String work_address;//办公地址
	private String shareholder_info;//股东信息
	private String corporation_credit_info;//企业法人征信信息
	protected String getCorporationCertNo() {
		return corporation_cert_no;
	}

	protected void setCorporationCertNo(String corporation_cert_no) {
		this.corporation_cert_no = corporation_cert_no;
	}

	protected String getCorporationAddress() {
		return corporation_address;
	}

	protected void setCorporationAddress(String corporation_address) {
		this.corporation_address = corporation_address;
	}

	protected String getCorporationIndustry() {
		return corporation_industry;
	}

	protected void setCorporationIndustry(String corporation_industry) {
		this.corporation_industry = corporation_industry;
	}
	
	public String getCorporation_cert_no() {
		return corporation_cert_no;
	}

	public void setCorporation_cert_no(String corporation_cert_no) {
		this.corporation_cert_no = corporation_cert_no;
	}

	public String getCorporation_address() {
		return corporation_address;
	}

	public void setCorporation_address(String corporation_address) {
		this.corporation_address = corporation_address;
	}

	public String getCorporation_industry() {
		return corporation_industry;
	}

	public void setCorporation_industry(String corporation_industry) {
		this.corporation_industry = corporation_industry;
	}

	public String getLoan_company_info() {
		return loan_company_info;
	}

	public void setLoan_company_info(String loan_company_info) {
		this.loan_company_info = loan_company_info;
	}

	public String getLoan_license_number() {
		return loan_license_number;
	}

	public void setLoan_license_number(String loan_license_number) {
		this.loan_license_number = loan_license_number;
	}

	public String getCorporation_name() {
		return corporation_name;
	}

	public void setCorporation_name(String corporation_name) {
		this.corporation_name = corporation_name;
	}

	public String getFounding_date() {
		return founding_date;
	}

	public void setFounding_date(String founding_date) {
		this.founding_date = founding_date;
	}

	public String getRegistered_capital() {
		return registered_capital;
	}

	public void setRegistered_capital(String registered_capital) {
		this.registered_capital = registered_capital;
	}

	public String getRegistered_address() {
		return registered_address;
	}

	public void setRegistered_address(String registered_address) {
		this.registered_address = registered_address;
	}

	protected String getLoanCompanyInfo() {
		return loan_company_info;
	}

	protected void setLoanCompanyInfo(String loanCompanyInfo) {
		this.loan_company_info = loanCompanyInfo;
	}

	protected String getLoanLicenseNumber() {
		return loan_license_number;
	}

	protected void setLoanLicenseNumber(String loanLicenseNumber) {
		this.loan_license_number = loanLicenseNumber;
	}

	protected String getCorporationName() {
		return corporation_name;
	}

	protected void setCorporationName(String corporationName) {
		this.corporation_name = corporationName;
	}

	protected String getFoundingDate() {
		return founding_date;
	}

	protected void setFoundingDate(String foundingDate) {
		this.founding_date = foundingDate;
	}

	protected String getRegisteredCapital() {
		return registered_capital;
	}

	protected void setRegisteredCapital(String registeredCapital) {
		this.registered_capital = registeredCapital;
	}

	protected String getRegisteredAddress() {
		return registered_address;
	}

	protected void setRegisteredAddress(String registeredAddress) {
		this.registered_address = registeredAddress;
	}

	protected String getPaidCapital() {
		return paid_capital;
	}

	protected void setPaidCapital(String paidCapital) {
		this.paid_capital = paidCapital;
	}

	protected String getOperatingArea() {
		return operating_area;
	}

	protected void setOperatingArea(String operatingArea) {
		this.operating_area = operatingArea;
	}

	protected Integer getBusiType() {
		return busi_type;
	}

	protected void setBusiType(Integer busiType) {
		this.busi_type = busiType;
	}

	protected String getAdministrationPunishInfo() {
		return administration_punish_info;
	}

	protected void setAdministrationPunishInfo(String administrationPunishInfo) {
		this.administration_punish_info = administrationPunishInfo;
	}

	protected String getOtherPlatLoanDesc() {
		return other_plat_loan_desc;
	}

	protected void setOtherPlatLoanDesc(String otherPlatLoanDesc) {
		this.other_plat_loan_desc = otherPlatLoanDesc;
	}

	protected String getThisOverdueTimes() {
		return this_overdue_times;
	}

	protected void setThisOverdueTimes(String thisOverdueTimes) {
		this.this_overdue_times = thisOverdueTimes;
	}

	protected String getThisOverdueMoney() {
		return this_overdue_money;
	}

	protected void setThisOverdueMoney(String thisOverdueMoney) {
		this.this_overdue_money = thisOverdueMoney;
	}

	protected String getWorkAddress() {
		return work_address;
	}

	protected void setWorkAddress(String workAddress) {
		this.work_address = workAddress;
	}

	protected String getShareholderInfo() {
		return shareholder_info;
	}

	protected void setShareholderInfo(String shareholderInfo) {
		this.shareholder_info = shareholderInfo;
	}

	protected String getCorporationCreditInfo() {
		return corporation_credit_info;
	}

	protected void setCorporationCreditInfo(String corporationCreditInfo) {
		this.corporation_credit_info = corporationCreditInfo;
	}

	public String getPaid_capital() {
		return paid_capital;
	}

	public void setPaid_capital(String paid_capital) {
		this.paid_capital = paid_capital;
	}

	public String getOperating_area() {
		return operating_area;
	}

	public void setOperating_area(String operating_area) {
		this.operating_area = operating_area;
	}

	public Integer getBusi_type() {
		return busi_type;
	}

	public void setBusi_type(Integer busi_type) {
		this.busi_type = busi_type;
	}

	public String getAdministration_punish_info() {
		return administration_punish_info;
	}

	public void setAdministration_punish_info(String administration_punish_info) {
		this.administration_punish_info = administration_punish_info;
	}

	public String getOther_plat_loan_desc() {
		return other_plat_loan_desc;
	}

	public void setOther_plat_loan_desc(String other_plat_loan_desc) {
		this.other_plat_loan_desc = other_plat_loan_desc;
	}

	public String getThis_overdue_times() {
		return this_overdue_times;
	}

	public void setThis_overdue_times(String this_overdue_times) {
		this.this_overdue_times = this_overdue_times;
	}

	public String getThis_overdue_money() {
		return this_overdue_money;
	}

	public void setThis_overdue_money(String this_overdue_money) {
		this.this_overdue_money = this_overdue_money;
	}

	public String getWork_address() {
		return work_address;
	}

	public void setWork_address(String work_address) {
		this.work_address = work_address;
	}

	public String getShareholder_info() {
		return shareholder_info;
	}

	public void setShareholder_info(String shareholder_info) {
		this.shareholder_info = shareholder_info;
	}

	public String getCorporation_credit_info() {
		return corporation_credit_info;
	}

	public void setCorporation_credit_info(String corporation_credit_info) {
		this.corporation_credit_info = corporation_credit_info;
	}

	
	
	
	

}
