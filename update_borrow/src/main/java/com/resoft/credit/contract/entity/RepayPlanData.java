package com.resoft.credit.contract.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class RepayPlanData extends DataEntity<RepayPlanData> { 
	private String applyNo;
	private String contractNo;
	private String approLoanRepayType;
	private String approPeriodValue;
	private BigDecimal contractAmount;
	private String serviceFeeType;
	private BigDecimal serviceFeeRate;
	private Date deductDate;
	private String custName;//acc_repay_plan中所需字段
	private String deductDateStr;//acc_repay_plan中所需字段
	private String capitalTerraceNo;//acc_repay_plan中所需字段
	private String orgLevel2;//acc_repay_plan中所需字段
	private String orgLevel3;//acc_repay_plan中所需字段
	private String orgLevel4;//acc_repay_plan中所需字段
	private String isAcc;//用来标示是否计算acc中的还款计划
	private String approProductTypeCode;//产品类型
	private String taskDefKey;//流程ID
	private BigDecimal interest;//批复月利率
	private BigDecimal SpecialServiceFee;//分期的特殊服务费


	public BigDecimal getSpecialServiceFee() {
		return SpecialServiceFee;
	}

	public void setSpecialServiceFee(BigDecimal specialServiceFee) {
		SpecialServiceFee = specialServiceFee;
	}

	public String getApproLoanRepayType() {
		return approLoanRepayType;
	}
	public void setApproLoanRepayType(String approLoanRepayType) {
		this.approLoanRepayType = approLoanRepayType;
	}
	public String getApproPeriodValue() {
		return approPeriodValue;
	}
	public void setApproPeriodValue(String approPeriodValue) {
		this.approPeriodValue = approPeriodValue;
	}
	public BigDecimal getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(BigDecimal contractAmount) {
		this.contractAmount = contractAmount;
	}
	public String getApplyNo() {
		return applyNo;
	}
	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	public String getServiceFeeType() {
		return serviceFeeType;
	}
	public void setServiceFeeType(String serviceFeeType) {
		this.serviceFeeType = serviceFeeType;
	}
	public BigDecimal getServiceFeeRate() {
		return serviceFeeRate;
	}
	public void setServiceFeeRate(BigDecimal serviceFeeRate) {
		this.serviceFeeRate = serviceFeeRate;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public Date getDeductDate() {
		return deductDate;
	}
	public void setDeductDate(Date deductDate) {
		this.deductDate = deductDate;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getDeductDateStr() {
		return deductDateStr;
	}
	public void setDeductDateStr(String deductDateStr) {
		this.deductDateStr = deductDateStr;
	}
	public String getCapitalTerraceNo() {
		return capitalTerraceNo;
	}
	public void setCapitalTerraceNo(String capitalTerraceNo) {
		this.capitalTerraceNo = capitalTerraceNo;
	}
	public String getOrgLevel2() {
		return orgLevel2;
	}
	public void setOrgLevel2(String orgLevel2) {
		this.orgLevel2 = orgLevel2;
	}
	public String getOrgLevel3() {
		return orgLevel3;
	}
	public void setOrgLevel3(String orgLevel3) {
		this.orgLevel3 = orgLevel3;
	}
	public String getOrgLevel4() {
		return orgLevel4;
	}
	public void setOrgLevel4(String orgLevel4) {
		this.orgLevel4 = orgLevel4;
	}
	public String getIsAcc() {
		return isAcc;
	}
	public void setIsAcc(String isAcc) {
		this.isAcc = isAcc;
	}
	public String getApproProductTypeCode() {
		return approProductTypeCode;
	}
	public void setApproProductTypeCode(String approProductTypeCode) {
		this.approProductTypeCode = approProductTypeCode;
	}
	public String getTaskDefKey() {
		return taskDefKey;
	}
	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}
	public BigDecimal getInterest() {
		return interest;
	}
	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}
	
	
	
	
}