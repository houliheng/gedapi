package com.resoft.outinterface.SV.server.entry.request;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.resoft.credit.companyInfo.entity.CompanyInfo;
import com.thinkgem.jeesite.common.utils.IdGen;

/**
 * SV接口企业征信列表Entity
 * 
 * @author wuxi01
 * @version 2016-04-21
 */
@XmlAccessorType(XmlAccessType.NONE)
public class SVCreditCompany implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String applyNo; // 申请编号
	private Date createDate; // 创建日期
	private Date updateDate; // 更新日期
	private String delFlag; // 删除标记（0：正常；1：删除；2：审核）
	private CompanyInfo companyInfo;// 企业基本信息
	// 生成xsd字段
	@XmlElement(required = false, name = "ROLE_TYPE")
	private String roleType; // 角色类型(主借人，共借人，担保人，配偶)
	@XmlElement(required = false, name = "BUSI_LIC_REG_NO")
	private String busiLicRegNo; // 营业执照注册号
	@XmlElement(required = false, name = "UN_SOC_CREDIT_NO")
	private String unSocCreditNo; // 统一社会信用代码
	@XmlElement(required = false, name = "ORGANIZATION_NO")
	private String organizationNo; // 组织机构代码
	@XmlElement(required = false, name = "REGISTER_CODE")
	private String registerCode; // 登记注册代码
	@XmlElement(required = false, name = "ID_NUM")
	private String idNum; // 法人证件号
	@XmlElement(required = false, name = "CREDIT_MONTHS")
	private String creditMonths; // 征信信用历史月
	@XmlElement(required = false, name = "GUARANTEE_OUT_AMOUNT")
	private String guaranteeOutAmount; // 对外担保总额
	@XmlElement(required = false, name = "COMPANY_PHONE")
	private String companyPhone; // 单位电话
	@XmlElement(required = false, name = "PRINTING_TIME")
	private String printingTime; // 打印时间
	@XmlElement(required = false, name = "IS_COMPANY_UP_DOWN")
	private String isCompanyUpDown; // 是否为大型企业的上下游
	@XmlElement(required = false, name = "SIX_MONTH_COMPANY_INCOME_AVG")
	private String sixMonthCompanyIncomeAvg; // 企业近六个月月均营业收入
	@XmlElement(required = false, name = "SUM_LOAN_AMOUNT")
	private String sumLoanAmount; // 贷款总额
	@XmlElement(required = false, name = "SUM_BALANCE_AMOUNT")
	private String sumBalanceAmount; // 贷款总余额
	@XmlElement(required = false, name = "SUM_OVERDUE_AMOUNT")
	private String sumOverdueAmount; // 贷款逾期总金额
	@XmlElement(required = false, name = "STATE_OF_TAXATION")
	private String stateOfTaxation; // 纳税情况（纳税逾期月数）
	@XmlElement(required = false, name = "TOTAL_PAYMENT_ONE_YEAR_AMOUNT")
	private String totalPaymentOneYearAmount;// 一年内需还贷款总额

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

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getUnSocCreditNo() {
		return unSocCreditNo;
	}

	public String getBusiLicRegNo() {
		return busiLicRegNo;
	}

	public void setBusiLicRegNo(String busiLicRegNo) {
		this.busiLicRegNo = busiLicRegNo;
	}

	public void setUnSocCreditNo(String unSocCreditNo) {
		this.unSocCreditNo = unSocCreditNo;
	}

	public String getOrganizationNo() {
		return organizationNo;
	}

	public void setOrganizationNo(String organizationNo) {
		this.organizationNo = organizationNo;
	}

	public String getRegisterCode() {
		return registerCode;
	}

	public void setRegisterCode(String registerCode) {
		this.registerCode = registerCode;
	}

	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}

	public String getCreditMonths() {
		return creditMonths;
	}

	public void setCreditMonths(String creditMonths) {
		this.creditMonths = creditMonths;
	}

	public String getGuaranteeOutAmount() {
		return guaranteeOutAmount;
	}

	public void setGuaranteeOutAmount(String guaranteeOutAmount) {
		this.guaranteeOutAmount = guaranteeOutAmount;
	}

	public String getCompanyPhone() {
		return companyPhone;
	}

	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}

	public String getPrintingTime() {
		return printingTime;
	}

	public void setPrintingTime(String printingTime) {
		this.printingTime = printingTime;
	}

	public String getIsCompanyUpDown() {
		return isCompanyUpDown;
	}

	public void setIsCompanyUpDown(String isCompanyUpDown) {
		this.isCompanyUpDown = isCompanyUpDown;
	}

	public String getSixMonthCompanyIncomeAvg() {
		return sixMonthCompanyIncomeAvg;
	}

	public void setSixMonthCompanyIncomeAvg(String sixMonthCompanyIncomeAvg) {
		this.sixMonthCompanyIncomeAvg = sixMonthCompanyIncomeAvg;
	}

	public String getSumLoanAmount() {
		return sumLoanAmount;
	}

	public void setSumLoanAmount(String sumLoanAmount) {
		this.sumLoanAmount = sumLoanAmount;
	}

	public String getSumBalanceAmount() {
		return sumBalanceAmount;
	}

	public void setSumBalanceAmount(String sumBalanceAmount) {
		this.sumBalanceAmount = sumBalanceAmount;
	}

	public String getSumOverdueAmount() {
		return sumOverdueAmount;
	}

	public void setSumOverdueAmount(String sumOverdueAmount) {
		this.sumOverdueAmount = sumOverdueAmount;
	}

	public String getStateOfTaxation() {
		return stateOfTaxation;
	}

	public void setStateOfTaxation(String stateOfTaxation) {
		this.stateOfTaxation = stateOfTaxation;
	}

	public String getTotalPaymentOneYearAmount() {
		return totalPaymentOneYearAmount;
	}

	public void setTotalPaymentOneYearAmount(String totalPaymentOneYearAmount) {
		this.totalPaymentOneYearAmount = totalPaymentOneYearAmount;
	}

	public void preInsert() {
		setId(IdGen.uuid());
		this.createDate = new Date();
	}

	public CompanyInfo getCompanyInfo() {
		return companyInfo;
	}

	public void setCompanyInfo(CompanyInfo companyInfo) {
		this.companyInfo = companyInfo;
	}
}