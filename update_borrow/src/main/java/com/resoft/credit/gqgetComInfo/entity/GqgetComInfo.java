package com.resoft.credit.gqgetComInfo.entity;


import com.resoft.credit.companyInfo.entity.CompanyInfo;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 冠E通Entity
 * @author wangong
 * @version 2016-09-19
 */
public class GqgetComInfo extends DataEntity<GqgetComInfo> {
	
	private static final long serialVersionUID = 1L;
	private String applyNo;		// 申请编号
	private String overallRanking;		// 综合评级
	private String intrOfMainborrower;  // 借款人介绍
	private String intrOfCompany;		// 企业介绍
	private String mixLoanUsage;		// 借款用途
	private String other;               // 其他
	private String sourceOfDepayment1;	// 还款来源1
	private String sourceOfDepayment2;	// 还款来源2
	private String sourceOfDepayment3;	// 还款来源3
	private String sourceOfDepayment4;	// 还款来源4
	private String auditOpintion;		// 审核意见
	private String intrOfComProduction;		// 企业产品介绍
	private String operateActuality;	//经营状况
	private String propertyHouse;		//资产信息-有房
	private String propertyCar;		//资产信息-有车
	private String propertyOther;		//资产信息-其他
	private BankLoan bankLoan;
	private MortageEquipment mortageEquipment;
	private String productType;		//产品类型
	private String borrowAndMatePunish;//借款人及配偶受行政处罚情况
	private String borrowInvolveInfo;//借款人涉诉情况
	private String borrowCrimaAdminPunish;//借款人受刑事、行政处罚情况
	private String borrowNewLoanBlance;//借款人在其他网贷平台借款余额
	private String platformOverdueTime;//平台逾期次数
	private String platformOverdueMoney;//平台逾期金额
	private CompanyInfo companyInfo;
	public String getApplyNo() {
		return applyNo;
	}
	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	public String getOverallRanking() {
		return overallRanking;
	}
	public void setOverallRanking(String overallRanking) {
		this.overallRanking = overallRanking;
	}
	public String getIntrOfMainborrower() {
		return intrOfMainborrower;
	}
	public void setIntrOfMainborrower(String intrOfMainborrower) {
		this.intrOfMainborrower = intrOfMainborrower;
	}
	public String getIntrOfCompany() {
		return intrOfCompany;
	}
	public void setIntrOfCompany(String intrOfCompany) {
		this.intrOfCompany = intrOfCompany;
	}
	public String getMixLoanUsage() {
		return mixLoanUsage;
	}
	public void setMixLoanUsage(String mixLoanUsage) {
		this.mixLoanUsage = mixLoanUsage;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	public String getSourceOfDepayment1() {
		return sourceOfDepayment1;
	}
	public void setSourceOfDepayment1(String sourceOfDepayment1) {
		this.sourceOfDepayment1 = sourceOfDepayment1;
	}
	public String getSourceOfDepayment2() {
		return sourceOfDepayment2;
	}
	public void setSourceOfDepayment2(String sourceOfDepayment2) {
		this.sourceOfDepayment2 = sourceOfDepayment2;
	}
	public String getSourceOfDepayment3() {
		return sourceOfDepayment3;
	}
	public void setSourceOfDepayment3(String sourceOfDepayment3) {
		this.sourceOfDepayment3 = sourceOfDepayment3;
	}
	public String getSourceOfDepayment4() {
		return sourceOfDepayment4;
	}
	public void setSourceOfDepayment4(String sourceOfDepayment4) {
		this.sourceOfDepayment4 = sourceOfDepayment4;
	}
	public String getAuditOpintion() {
		return auditOpintion;
	}
	public void setAuditOpintion(String auditOpintion) {
		this.auditOpintion = auditOpintion;
	}
	public String getIntrOfComProduction() {
		return intrOfComProduction;
	}
	public void setIntrOfComProduction(String intrOfComProduction) {
		this.intrOfComProduction = intrOfComProduction;
	}
	public String getOperateActuality() {
		return operateActuality;
	}
	public void setOperateActuality(String operateActuality) {
		this.operateActuality = operateActuality;
	}
	public BankLoan getBankLoan() {
		return bankLoan;
	}
	public void setBankLoan(BankLoan bankLoan) {
		this.bankLoan = bankLoan;
	}
	public MortageEquipment getMortageEquipment() {
		return mortageEquipment;
	}
	public void setMortageEquipment(MortageEquipment mortageEquipment) {
		this.mortageEquipment = mortageEquipment;
	}
	public String getPropertyHouse() {
		return propertyHouse;
	}
	public void setPropertyHouse(String propertyHouse) {
		this.propertyHouse = propertyHouse;
	}
	public String getPropertyCar() {
		return propertyCar;
	}
	public void setPropertyCar(String propertyCar) {
		this.propertyCar = propertyCar;
	}
	public String getPropertyOther() {
		return propertyOther;
	}
	public void setPropertyOther(String propertyOther) {
		this.propertyOther = propertyOther;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getBorrowAndMatePunish() {
		return borrowAndMatePunish;
	}
	public void setBorrowAndMatePunish(String borrowAndMatePunish) {
		this.borrowAndMatePunish = borrowAndMatePunish;
	}
	public String getBorrowInvolveInfo() {
		return borrowInvolveInfo;
	}
	public void setBorrowInvolveInfo(String borrowInvolveInfo) {
		this.borrowInvolveInfo = borrowInvolveInfo;
	}
	public String getBorrowCrimaAdminPunish() {
		return borrowCrimaAdminPunish;
	}
	public void setBorrowCrimaAdminPunish(String borrowCrimaAdminPunish) {
		this.borrowCrimaAdminPunish = borrowCrimaAdminPunish;
	}
	
	public String getBorrowNewLoanBlance() {
		return borrowNewLoanBlance;
	}
	public void setBorrowNewLoanBlance(String borrowNewLoanBlance) {
		this.borrowNewLoanBlance = borrowNewLoanBlance;
	}
	public CompanyInfo getCompanyInfo() {
		return companyInfo;
	}
	public void setCompanyInfo(CompanyInfo companyInfo) {
		this.companyInfo = companyInfo;
	}
	public String getPlatformOverdueTime() {
		return platformOverdueTime;
	}
	public void setPlatformOverdueTime(String platformOverdueTime) {
		this.platformOverdueTime = platformOverdueTime;
	}
	public String getPlatformOverdueMoney() {
		return platformOverdueMoney;
	}
	public void setPlatformOverdueMoney(String platformOverdueMoney) {
		this.platformOverdueMoney = platformOverdueMoney;
	}
	
	
	
}