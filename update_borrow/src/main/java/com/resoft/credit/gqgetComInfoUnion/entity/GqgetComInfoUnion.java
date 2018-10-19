package com.resoft.credit.gqgetComInfoUnion.entity;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import com.resoft.credit.companyInfo.entity.CompanyInfo;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 联合授信冠e通信息Entity
 * @author lixing
 * @version 2016-12-26
 */
public class GqgetComInfoUnion extends DataEntity<GqgetComInfoUnion> {
	
	private static final long serialVersionUID = 1L;
	private String applyNo;		// 申请编号
	private String approveId;		// 批复ID
	private String overallRanking;		// 综合评级
	private String introductionOfMainborrower;		// 借款人介绍
	private String introductionOfCompany;		// 企业信息
	private String mixLoanUsage;		// 借款用途
	private String other;		// 其他
	private String sourceOfPepayment1;		// 还款来源1
	private String sourceOfPepayment2;		// 还款来源2
	private String sourceOfPepayment3;		// 还款来源4
	private String sourceOfPepayment4;		// 还款来源4
	private String auditOpintion;		// 审核意见
	private String introductionOfComProduction;		// 企业产品介绍
	private String operateActuality;		// 经营情况
	private String propertyHouse;		// 资产信息-有房
	private String propertyCar;		// 资产信息-有车
	private String propertyOther;		// 资产信息-其他
	private String productType;		// 产品类型
	private String isLoan;		// 是否贷款
	private String loanRecode;		// 共几笔贷款记录
	private String isOverdue;		// 是否有逾期
	private String sourceOfCreditRegistries;		// 征信记录等级
	private String borrowAndMatePunish;//借款人及配偶受行政处罚情况
	private String borrowInvolveInfo;//借款人涉诉情况
	private String borrowCrimaAdminPunish;//借款人受刑事、行政处罚情况
	private String borrowNewLoanBlance;//借款人在其他网贷平台借款余额
	private String platformOverdueTime;//平台逾期次数
	private String platformOverdueMoney;//平台逾期金额
	private CompanyInfo companyInfo;
	private String creditCompany;
	public GqgetComInfoUnion() {
		super();
	}

	public GqgetComInfoUnion(String id){
		super(id);
	}

	@Length(min=0, max=32, message="申请编号长度必须介于 0 和 32 之间")
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	
	@Length(min=1, max=32, message="批复ID长度必须介于 1 和 32 之间")
	public String getApproveId() {
		return approveId;
	}

	public void setApproveId(String approveId) {
		this.approveId = approveId;
	}
	
	@Length(min=0, max=2, message="综合评级长度必须介于 0 和 2 之间")
	public String getOverallRanking() {
		return overallRanking;
	}

	public void setOverallRanking(String overallRanking) {
		this.overallRanking = overallRanking;
	}
	
	@Length(min=0, max=1000, message="借款人介绍长度必须介于 0 和 1000 之间")
	public String getIntroductionOfMainborrower() {
		return introductionOfMainborrower;
	}

	public void setIntroductionOfMainborrower(String introductionOfMainborrower) {
		this.introductionOfMainborrower = introductionOfMainborrower;
	}
	
	@Length(min=0, max=1000, message="企业信息长度必须介于 0 和 1000 之间")
	public String getIntroductionOfCompany() {
		return introductionOfCompany;
	}

	public void setIntroductionOfCompany(String introductionOfCompany) {
		this.introductionOfCompany = introductionOfCompany;
	}
	
	@Length(min=0, max=1000, message="借款用途长度必须介于 0 和 1000 之间")
	public String getMixLoanUsage() {
		return mixLoanUsage;
	}

	public void setMixLoanUsage(String mixLoanUsage) {
		this.mixLoanUsage = mixLoanUsage;
	}
	
	@Length(min=0, max=1000, message="其他长度必须介于 0 和 1000 之间")
	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}
	
	@Length(min=0, max=1000, message="还款来源1长度必须介于 0 和 1000 之间")
	public String getSourceOfPepayment1() {
		return sourceOfPepayment1;
	}

	public void setSourceOfPepayment1(String sourceOfPepayment1) {
		this.sourceOfPepayment1 = sourceOfPepayment1;
	}
	
	@Length(min=0, max=1000, message="还款来源2长度必须介于 0 和 1000 之间")
	public String getSourceOfPepayment2() {
		return sourceOfPepayment2;
	}

	public void setSourceOfPepayment2(String sourceOfPepayment2) {
		this.sourceOfPepayment2 = sourceOfPepayment2;
	}
	
	@Length(min=0, max=1000, message="还款来源4长度必须介于 0 和 1000 之间")
	public String getSourceOfPepayment3() {
		return sourceOfPepayment3;
	}

	public void setSourceOfPepayment3(String sourceOfPepayment3) {
		this.sourceOfPepayment3 = sourceOfPepayment3;
	}
	
	@Length(min=0, max=1000, message="还款来源4长度必须介于 0 和 1000 之间")
	public String getSourceOfPepayment4() {
		return sourceOfPepayment4;
	}

	public void setSourceOfPepayment4(String sourceOfPepayment4) {
		this.sourceOfPepayment4 = sourceOfPepayment4;
	}
	
	@Length(min=0, max=1000, message="审核意见长度必须介于 0 和 1000 之间")
	public String getAuditOpintion() {
		return auditOpintion;
	}

	public void setAuditOpintion(String auditOpintion) {
		this.auditOpintion = auditOpintion;
	}
	
	@Length(min=0, max=1000, message="企业产品介绍长度必须介于 0 和 1000 之间")
	public String getIntroductionOfComProduction() {
		return introductionOfComProduction;
	}

	public void setIntroductionOfComProduction(String introductionOfComProduction) {
		this.introductionOfComProduction = introductionOfComProduction;
	}
	
	@Length(min=0, max=1000, message="经营情况长度必须介于 0 和 1000 之间")
	public String getOperateActuality() {
		return operateActuality;
	}

	public void setOperateActuality(String operateActuality) {
		this.operateActuality = operateActuality;
	}
	
	@Length(min=0, max=1, message="资产信息-有房长度必须介于 0 和 1 之间")
	public String getPropertyHouse() {
		return propertyHouse;
	}

	public void setPropertyHouse(String propertyHouse) {
		this.propertyHouse = propertyHouse;
	}
	
	@Length(min=0, max=1, message="资产信息-有车长度必须介于 0 和 1 之间")
	public String getPropertyCar() {
		return propertyCar;
	}

	public void setPropertyCar(String propertyCar) {
		this.propertyCar = propertyCar;
	}
	
	@Length(min=0, max=1, message="资产信息-其他长度必须介于 0 和 1 之间")
	public String getPropertyOther() {
		return propertyOther;
	}

	public void setPropertyOther(String propertyOther) {
		this.propertyOther = propertyOther;
	}
	
	@Length(min=0, max=3, message="产品类型长度必须介于 0 和 3 之间")
	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}
	
	@Length(min=0, max=2, message="是否贷款长度必须介于 0 和 2 之间")
	public String getIsLoan() {
		return isLoan;
	}

	public void setIsLoan(String isLoan) {
		this.isLoan = isLoan;
	}
	
	@Length(min=0, max=3, message="共几笔贷款记录长度必须介于 0 和 3 之间")
	public String getLoanRecode() {
		return loanRecode;
	}

	public void setLoanRecode(String loanRecode) {
		this.loanRecode = loanRecode;
	}
	
	@Length(min=0, max=2, message="是否有逾期长度必须介于 0 和 2 之间")
	public String getIsOverdue() {
		return isOverdue;
	}

	public void setIsOverdue(String isOverdue) {
		this.isOverdue = isOverdue;
	}
	
	@Length(min=0, max=2, message="征信记录等级长度必须介于 0 和 2 之间")
	public String getSourceOfCreditRegistries() {
		return sourceOfCreditRegistries;
	}

	public void setSourceOfCreditRegistries(String sourceOfCreditRegistries) {
		this.sourceOfCreditRegistries = sourceOfCreditRegistries;
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

	public String getCreditCompany() {
		return creditCompany;
	}

	public void setCreditCompany(String creditCompany) {
		this.creditCompany = creditCompany;
	}

	
	
	
	
}