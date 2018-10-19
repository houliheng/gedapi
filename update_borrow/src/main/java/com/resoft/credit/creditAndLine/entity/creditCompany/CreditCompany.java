package com.resoft.credit.creditAndLine.entity.creditCompany;

import org.hibernate.validator.constraints.Length;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 企业征信列表Entity
 * 
 * @author wuxi01
 * @version 2016-03-18
 */
public class CreditCompany extends DataEntity<CreditCompany> {

	private static final long serialVersionUID = 1L;
	private String applyNo; // 申请编号
	private String roleType; // 角色类型(主借人，共借人，担保人，配偶)
	private String companyId; // 企业ID
	private String companyName; // 企业名称
	private String creditMonths; // 征信信用历史月
	private String idNum; // 证件号
	private String guaranteeOutAmount; // 对外担保总额
	private String companyPhone; // 单位电话
	private String printingTime; // 打印时间
	private String isCompanyUpDown; // 是否为大型企业的上下游
	private String sixMonthCompanyIncomeAvg; // 企业近六个月月均营业收入
	private String sumLoanAmount; // 贷款总额
	private String sumBalanceAmount; // 贷款总余额
	private String sumOverdueAmount; // 贷款逾期总金额
	private String stateOfTaxation; // 纳税情况（纳税逾期月数）
    private String totalPaymentOneYearAmount;//一年内已还贷款总额
    private String firstRecordLoanYear;//首笔信贷交易记录年份
    private Integer operaterOriganNum;//办理过信贷业务的机构数量
    
	public CreditCompany() {
		super();
	}

	public CreditCompany(String id) {
		super(id);
	}

	@Length(min = 0, max = 32, message = "申请编号长度必须介于 0 和 32 之间")
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	@Length(min = 0, max = 4, message = "角色类型(主借人，共借人，担保人，配偶)长度必须介于 0 和 4 之间")
	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	@Length(min = 0, max = 32, message = "企业ID长度必须介于 0 和 32 之间")
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	@Length(min = 0, max = 300, message = "企业名称长度必须介于 0 和 300 之间")
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Length(min = 0, max = 10, message = "征信信用历史月长度必须介于 0 和 10 之间")
	public String getCreditMonths() {
		return creditMonths;
	}

	public void setCreditMonths(String creditMonths) {
		this.creditMonths = creditMonths;
	}

	@Length(min = 0, max = 18, message = "证件号长度必须介于 0 和 18 之间")
	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}

	public String getGuaranteeOutAmount() {
		return guaranteeOutAmount;
	}

	public void setGuaranteeOutAmount(String guaranteeOutAmount) {
		this.guaranteeOutAmount = guaranteeOutAmount;
	}

	@Length(min = 0, max = 15, message = "单位电话长度必须介于 0 和 15 之间")
	public String getCompanyPhone() {
		return companyPhone;
	}

	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}

	@Length(min = 0, max = 1, message = "是否为大型企业的上下游长度必须介于 0 和 1 之间")
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

	@Length(min = 0, max = 300, message = "纳税情况（纳税逾期月数）长度必须介于 0 和 300 之间")
	public String getStateOfTaxation() {
		return stateOfTaxation;
	}

	public void setStateOfTaxation(String stateOfTaxation) {
		this.stateOfTaxation = stateOfTaxation;
	}

	public String getPrintingTime() {
		return printingTime;
	}

	public void setPrintingTime(String printingTime) {
		this.printingTime = printingTime;
	}

	public String getTotalPaymentOneYearAmount() {
		return totalPaymentOneYearAmount;
	}

	public void setTotalPaymentOneYearAmount(String totalPaymentOneYearAmount) {
		this.totalPaymentOneYearAmount = totalPaymentOneYearAmount;
	}

	public String getFirstRecordLoanYear() {
		return firstRecordLoanYear;
	}

	public void setFirstRecordLoanYear(String firstRecordLoanYear) {
		this.firstRecordLoanYear = firstRecordLoanYear;
	}

	public Integer getOperaterOriganNum() {
		return operaterOriganNum;
	}

	public void setOperaterOriganNum(Integer operaterOriganNum) {
		this.operaterOriganNum = operaterOriganNum;
	}

	
}