package com.resoft.credit.underCompanyInfo.entity;

import com.resoft.credit.gqgetComInfo.entity.BankLoan;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 线下借款-企业信息Entity
 * @author jml
 * @version 2018-06-26
 */
public class UnderCompanyInfo extends DataEntity<UnderCompanyInfo> {
	
	private static final long serialVersionUID = 1L;
	private String applyNo;		// 申请编号
	private String procInstId;		// 流程实例ID
	private String loanStatus;		// 借款状态
	private String busiRegName;		// 企业工商登记名称
	private String unSocCreditNo;		// 统一社会信用代码
	private String busiLicRegNo;		// 营业执照注册号
	private String paidInCapital;		// 实缴资本(单位：元)
	private String registerCapital;		// 注册资本
	private Date companyStartDate;		// 成立时间
	private String corporationName;		// 法人名称
	private String corporationCardIdNo;		// 法人证件号
	private String regDistinct;		// 经营区域
	private String categoryMain;		// 法定代表人行业-门类(行业表-levle1)
	private String categoryLarge;		// 法定代表人行业-大类(行业表-levle2)
	private String categoryMedium;		// 法定代表人行业-中类(行业表-levle3)
	private String categorySmall;		// 法定代表人行业-小类(行业表-levle4)
	private String operateProvince;		// 法定代表人地址：省
	private String operateCity;		// 法定代表人地址：市
	private String operateDistinct;		// 法定代表人地址：区
	private String operateDetails;		// 法定代表人地址详细
	private String registerProvince;		// 注册地址：省
	private String registerCity;		// 注册地址：市
	private String registerDistinct;		// 注册地址：区
	private String registerDetails;		// 注册地址详细
	private String officeProvince;		// 办公地址：省
	private String officeCity;		// 办公地址：市
	private String officeDistinct;		// 办公地址：区
	private String officeDetails;		// 办公地址详细
	private String companyInfo;		// 企业信息
	private String companyProductInfo;		// 企业产品介绍
	private String stockInfo;		// 股东信息
	private String creditCorporation;		// 企业法人征信
	private String otherLoanplatInfo;		// 其它平台借款情况
	private String isLoan;		// 是否有贷款记录
	private String loanRecode;		// 共几笔贷款记录
	private String isOverDue;		// 当前是否有逾期
	private String sourceOfCreregist;		// 企业征信记录等级
	private String gqCompanyName;		// 分公司名称
	private String gqAreaName;		// 所属大区
	private String loanBeCompany;		// 债权所属公司

	private String gedNumber;

	public UnderCompanyInfo() {
		super();
	}

	public UnderCompanyInfo(String id){
		super(id);
	}

	@Length(min=1, max=32, message="申请编号长度必须介于 1 和 32 之间")
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	
	@Length(min=1, max=32, message="流程实例ID长度必须介于 1 和 32 之间")
	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}
	
	@Length(min=0, max=10, message="借款状态长度必须介于 0 和 10 之间")
	public String getLoanStatus() {
		return loanStatus;
	}

	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}
	
	@Length(min=0, max=30, message="企业工商登记名称长度必须介于 0 和 30 之间")
	public String getBusiRegName() {
		return busiRegName;
	}

	public void setBusiRegName(String busiRegName) {
		this.busiRegName = busiRegName;
	}
	
	@Length(min=0, max=50, message="统一社会信用代码长度必须介于 0 和 50 之间")
	public String getUnSocCreditNo() {
		return unSocCreditNo;
	}

	public void setUnSocCreditNo(String unSocCreditNo) {
		this.unSocCreditNo = unSocCreditNo;
	}
	
	@Length(min=0, max=50, message="营业执照注册号长度必须介于 0 和 50 之间")
	public String getBusiLicRegNo() {
		return busiLicRegNo;
	}

	public void setBusiLicRegNo(String busiLicRegNo) {
		this.busiLicRegNo = busiLicRegNo;
	}
	
	public String getPaidInCapital() {
		return paidInCapital;
	}

	public void setPaidInCapital(String paidInCapital) {
		this.paidInCapital = paidInCapital;
	}
	
	public String getRegisterCapital() {
		return registerCapital;
	}

	public void setRegisterCapital(String registerCapital) {
		this.registerCapital = registerCapital;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCompanyStartDate() {
		return companyStartDate;
	}

	public void setCompanyStartDate(Date companyStartDate) {
		this.companyStartDate = companyStartDate;
	}
	
	@Length(min=0, max=300, message="法人名称长度必须介于 0 和 300 之间")
	public String getCorporationName() {
		return corporationName;
	}

	public void setCorporationName(String corporationName) {
		this.corporationName = corporationName;
	}
	
	@Length(min=0, max=18, message="法人证件号长度必须介于 0 和 18 之间")
	public String getCorporationCardIdNo() {
		return corporationCardIdNo;
	}

	public void setCorporationCardIdNo(String corporationCardIdNo) {
		this.corporationCardIdNo = corporationCardIdNo;
	}
	
	@Length(min=0, max=10, message="经营区域长度必须介于 0 和 10 之间")
	public String getRegDistinct() {
		return regDistinct;
	}

	public void setRegDistinct(String regDistinct) {
		this.regDistinct = regDistinct;
	}
	
	@Length(min=0, max=10, message="法定代表人行业-门类(行业表-levle1)长度必须介于 0 和 10 之间")
	public String getCategoryMain() {
		return categoryMain;
	}

	public void setCategoryMain(String categoryMain) {
		this.categoryMain = categoryMain;
	}
	
	@Length(min=0, max=10, message="法定代表人行业-大类(行业表-levle2)长度必须介于 0 和 10 之间")
	public String getCategoryLarge() {
		return categoryLarge;
	}

	public void setCategoryLarge(String categoryLarge) {
		this.categoryLarge = categoryLarge;
	}
	
	@Length(min=0, max=10, message="法定代表人行业-中类(行业表-levle3)长度必须介于 0 和 10 之间")
	public String getCategoryMedium() {
		return categoryMedium;
	}

	public void setCategoryMedium(String categoryMedium) {
		this.categoryMedium = categoryMedium;
	}
	
	@Length(min=0, max=10, message="法定代表人行业-小类(行业表-levle4)长度必须介于 0 和 10 之间")
	public String getCategorySmall() {
		return categorySmall;
	}

	public void setCategorySmall(String categorySmall) {
		this.categorySmall = categorySmall;
	}
	
	@Length(min=0, max=10, message="法定代表人地址：省长度必须介于 0 和 10 之间")
	public String getOperateProvince() {
		return operateProvince;
	}

	public void setOperateProvince(String operateProvince) {
		this.operateProvince = operateProvince;
	}
	
	@Length(min=0, max=10, message="法定代表人地址：市长度必须介于 0 和 10 之间")
	public String getOperateCity() {
		return operateCity;
	}

	public void setOperateCity(String operateCity) {
		this.operateCity = operateCity;
	}
	
	@Length(min=0, max=10, message="法定代表人地址：区长度必须介于 0 和 10 之间")
	public String getOperateDistinct() {
		return operateDistinct;
	}

	public void setOperateDistinct(String operateDistinct) {
		this.operateDistinct = operateDistinct;
	}
	
	@Length(min=0, max=300, message="法定代表人地址详细长度必须介于 0 和 300 之间")
	public String getOperateDetails() {
		return operateDetails;
	}

	public void setOperateDetails(String operateDetails) {
		this.operateDetails = operateDetails;
	}
	
	@Length(min=0, max=10, message="注册地址：省长度必须介于 0 和 10 之间")
	public String getRegisterProvince() {
		return registerProvince;
	}

	public void setRegisterProvince(String registerProvince) {
		this.registerProvince = registerProvince;
	}
	
	@Length(min=0, max=10, message="注册地址：市长度必须介于 0 和 10 之间")
	public String getRegisterCity() {
		return registerCity;
	}

	public void setRegisterCity(String registerCity) {
		this.registerCity = registerCity;
	}
	
	@Length(min=0, max=10, message="注册地址：区长度必须介于 0 和 10 之间")
	public String getRegisterDistinct() {
		return registerDistinct;
	}

	public void setRegisterDistinct(String registerDistinct) {
		this.registerDistinct = registerDistinct;
	}
	
	@Length(min=0, max=300, message="注册地址详细长度必须介于 0 和 300 之间")
	public String getRegisterDetails() {
		return registerDetails;
	}

	public void setRegisterDetails(String registerDetails) {
		this.registerDetails = registerDetails;
	}
	
	@Length(min=0, max=10, message="办公地址：省长度必须介于 0 和 10 之间")
	public String getOfficeProvince() {
		return officeProvince;
	}

	public void setOfficeProvince(String officeProvince) {
		this.officeProvince = officeProvince;
	}
	
	@Length(min=0, max=10, message="办公地址：市长度必须介于 0 和 10 之间")
	public String getOfficeCity() {
		return officeCity;
	}

	public void setOfficeCity(String officeCity) {
		this.officeCity = officeCity;
	}
	
	@Length(min=0, max=10, message="办公地址：区长度必须介于 0 和 10 之间")
	public String getOfficeDistinct() {
		return officeDistinct;
	}

	public void setOfficeDistinct(String officeDistinct) {
		this.officeDistinct = officeDistinct;
	}
	
	@Length(min=0, max=300, message="办公地址详细长度必须介于 0 和 300 之间")
	public String getOfficeDetails() {
		return officeDetails;
	}

	public void setOfficeDetails(String officeDetails) {
		this.officeDetails = officeDetails;
	}
	
	@Length(min=0, max=1000, message="企业信息长度必须介于 0 和 1000 之间")
	public String getCompanyInfo() {
		return companyInfo;
	}

	public void setCompanyInfo(String companyInfo) {
		this.companyInfo = companyInfo;
	}
	
	@Length(min=0, max=1000, message="企业产品介绍长度必须介于 0 和 1000 之间")
	public String getCompanyProductInfo() {
		return companyProductInfo;
	}

	public void setCompanyProductInfo(String companyProductInfo) {
		this.companyProductInfo = companyProductInfo;
	}
	
	@Length(min=0, max=1000, message="股东信息长度必须介于 0 和 1000 之间")
	public String getStockInfo() {
		return stockInfo;
	}

	public void setStockInfo(String stockInfo) {
		this.stockInfo = stockInfo;
	}
	
	@Length(min=0, max=1000, message="企业法人征信长度必须介于 0 和 1000 之间")
	public String getCreditCorporation() {
		return creditCorporation;
	}

	public void setCreditCorporation(String creditCorporation) {
		this.creditCorporation = creditCorporation;
	}
	
	@Length(min=0, max=1000, message="其它平台借款情况长度必须介于 0 和 1000 之间")
	public String getOtherLoanplatInfo() {
		return otherLoanplatInfo;
	}

	public void setOtherLoanplatInfo(String otherLoanplatInfo) {
		this.otherLoanplatInfo = otherLoanplatInfo;
	}
	
	@Length(min=0, max=1, message="是否有贷款记录长度必须介于 0 和 1 之间")
	public String getIsLoan() {
		return isLoan;
	}

	public void setIsLoan(String isLoan) {
		this.isLoan = isLoan;
	}
	
	@Length(min=0, max=10, message="共几笔贷款记录长度必须介于 0 和 10 之间")
	public String getLoanRecode() {
		return loanRecode;
	}

	public void setLoanRecode(String loanRecode) {
		this.loanRecode = loanRecode;
	}
	
	@Length(min=0, max=1, message="当前是否有逾期长度必须介于 0 和 1 之间")
	public String getIsOverDue() {
		return isOverDue;
	}

	public void setIsOverDue(String isOverDue) {
		this.isOverDue = isOverDue;
	}
	
	@Length(min=0, max=10, message="企业征信记录等级长度必须介于 0 和 10 之间")
	public String getSourceOfCreregist() {
		return sourceOfCreregist;
	}

	public void setSourceOfCreregist(String sourceOfCreregist) {
		this.sourceOfCreregist = sourceOfCreregist;
	}
	
	@Length(min=0, max=50, message="分公司名称长度必须介于 0 和 50 之间")
	public String getGqCompanyName() {
		return gqCompanyName;
	}

	public void setGqCompanyName(String gqCompanyName) {
		this.gqCompanyName = gqCompanyName;
	}
	
	@Length(min=0, max=50, message="所属大区长度必须介于 0 和 50 之间")
	public String getGqAreaName() {
		return gqAreaName;
	}

	public void setGqAreaName(String gqAreaName) {
		this.gqAreaName = gqAreaName;
	}
	
	@Length(min=0, max=50, message="债权所属公司长度必须介于 0 和 50 之间")
	public String getLoanBeCompany() {
		return loanBeCompany;
	}

	public void setLoanBeCompany(String loanBeCompany) {
		this.loanBeCompany = loanBeCompany;
	}

	public String getGedNumber() {
		return gedNumber;
	}

	public void setGedNumber(String gedNumber) {
		this.gedNumber = gedNumber;
	}
}