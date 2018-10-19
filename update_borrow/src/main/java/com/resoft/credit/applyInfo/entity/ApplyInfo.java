package com.resoft.credit.applyInfo.entity;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import com.resoft.credit.applyRegister.entity.ApplyRegister;
import com.resoft.credit.applyRelation.entity.ApplyRelation;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 申请借款信息表Entity
 * 
 * @author chenshaojia
 * @version 2016-03-01
 */
public class ApplyInfo extends DataEntity<ApplyInfo> {

	private static final long serialVersionUID = 1L;
	private ApplyRegister applyRegister; // 客户登记对象
	private String applyNo;
	private BigDecimal applyAmount; // 申请金额
	private String applyProductTypeCode; // 申请产品类型编码
	private String applyProductTypeName; // 申请产品类型名称
	private String applyProductId; // 申请产品ID
	private String applyProductName; // 申请产品名称
	private String applyPeriodId; // 申请期限ID
	private String applyPeriodValue; // 申请期限值
	private BigDecimal applyServiceRate; // 申请服务费率
	private BigDecimal applyYearRate; // 年利率
	private String loanRepayType; // 还款方式（字典类型：LOAN_REPAY_TYPE）
	private BigDecimal monthRepayAmount; // 月还款金额
	private String applyCustType; // 客户类型（借款申请客户类型）（字典类型：APPLY_CUST_TYPE）
	private String acceptHighestAmount; // 可接受最高月还款额
	private String loanKind; // 借款类型（字典类型：LOAN_KIND）
	private String loanPurpost; // 借款用途（字典类型：LOAN_PURPOST）
	private String loanOtherPurposeDesc; // 其他用途说明
	private String description; // 补充说明
	private String isFraud; // 是否曾被欺诈认定
	private String isHaveComLoan; // 是否有共借人
	private String isHaveAssure; // 是否有担保人
	// private String isHaveCompany; // 是否有企业
	private String isUrgent; // 是否加急
	private String svPlatform; // SV委托平台
	private ApplyRelation applyRelation;
	private String operateOrgName;//非本表数据，经办机构名称
	private BigDecimal contractAmount;//合同金额

	public ApplyInfo() {
		super();
	}

	public ApplyInfo(String id) {
		super(id);
	}

	public ApplyRegister getApplyRegister() {
		return applyRegister;
	}

	public void setApplyRegister(ApplyRegister applyRegister) {
		this.applyRegister = applyRegister;
	}

	public BigDecimal getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(BigDecimal applyAmount) {
		this.applyAmount = applyAmount;
	}

	@Length(min = 0, max = 10, message = "申请产品类型编码长度必须介于 0 和 10 之间")
	public String getApplyProductTypeCode() {
		return applyProductTypeCode;
	}

	public void setApplyProductTypeCode(String applyProductTypeCode) {
		this.applyProductTypeCode = applyProductTypeCode;
	}

	@Length(min = 0, max = 100, message = "申请产品类型名称长度必须介于 0 和 100 之间")
	public String getApplyProductTypeName() {
		return applyProductTypeName;
	}

	public void setApplyProductTypeName(String applyProductTypeName) {
		this.applyProductTypeName = applyProductTypeName;
	}

	@Length(min = 0, max = 32, message = "申请产品ID长度必须介于 0 和 32 之间")
	public String getApplyProductId() {
		return applyProductId;
	}

	public void setApplyProductId(String applyProductId) {
		this.applyProductId = applyProductId;
	}

	@Length(min = 0, max = 100, message = "申请产品名称长度必须介于 0 和 100 之间")
	public String getApplyProductName() {
		return applyProductName;
	}

	public void setApplyProductName(String applyProductName) {
		this.applyProductName = applyProductName;
	}

	@Length(min = 0, max = 32, message = "申请期限ID长度必须介于 0 和 32 之间")
	public String getApplyPeriodId() {
		return applyPeriodId;
	}

	public void setApplyPeriodId(String applyPeriodId) {
		this.applyPeriodId = applyPeriodId;
	}

	@Length(min = 0, max = 10, message = "申请期限值长度必须介于 0 和 10 之间")
	public String getApplyPeriodValue() {
		return applyPeriodValue;
	}

	public void setApplyPeriodValue(String applyPeriodValue) {
		this.applyPeriodValue = applyPeriodValue;
	}

	public BigDecimal getApplyServiceRate() {
		return applyServiceRate;
	}

	public void setApplyServiceRate(BigDecimal applyServiceRate) {
		this.applyServiceRate = applyServiceRate;
	}

	public BigDecimal getApplyYearRate() {
		return applyYearRate;
	}

	public void setApplyYearRate(BigDecimal applyYearRate) {
		this.applyYearRate = applyYearRate;
	}

	@Length(min = 0, max = 10, message = "还款方式（字典类型：LOAN_REPAY_TYPE）长度必须介于 0 和 10 之间")
	public String getLoanRepayType() {
		return loanRepayType;
	}

	public void setLoanRepayType(String loanRepayType) {
		this.loanRepayType = loanRepayType;
	}

	public BigDecimal getMonthRepayAmount() {
		return monthRepayAmount;
	}

	public void setMonthRepayAmount(BigDecimal monthRepayAmount) {
		this.monthRepayAmount = monthRepayAmount;
	}

	@Length(min = 0, max = 4, message = "客户类型（借款申请客户类型）（字典类型：APPLY_CUST_TYPE）长度必须介于 0 和 4 之间")
	public String getApplyCustType() {
		return applyCustType;
	}

	public void setApplyCustType(String applyCustType) {
		this.applyCustType = applyCustType;
	}

	public String getAcceptHighestAmount() {
		return acceptHighestAmount;
	}

	public void setAcceptHighestAmount(String acceptHighestAmount) {
		this.acceptHighestAmount = acceptHighestAmount;
	}

	@Length(min = 0, max = 10, message = "借款类型（字典类型：LOAN_KIND）长度必须介于 0 和 10 之间")
	public String getLoanKind() {
		return loanKind;
	}

	public void setLoanKind(String loanKind) {
		this.loanKind = loanKind;
	}

	@Length(min = 0, max = 10, message = "借款用途（字典类型：LOAN_PURPOST）长度必须介于 0 和 10 之间")
	public String getLoanPurpost() {
		return loanPurpost;
	}

	public void setLoanPurpost(String loanPurpost) {
		this.loanPurpost = loanPurpost;
	}

	@Length(min = 0, max = 200, message = "其他用途说明长度必须介于 0 和 200 之间")
	public String getLoanOtherPurposeDesc() {
		return loanOtherPurposeDesc;
	}

	public void setLoanOtherPurposeDesc(String loanOtherPurposeDesc) {
		this.loanOtherPurposeDesc = loanOtherPurposeDesc;
	}

	@Length(min = 0, max = 1000, message = "补充说明长度必须介于 0 和 1000 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Length(min = 0, max = 1, message = "是否曾被欺诈认定长度必须介于 0 和 1 之间")
	public String getIsFraud() {
		return isFraud;
	}

	public void setIsFraud(String isFraud) {
		this.isFraud = isFraud;
	}

	@Length(min = 0, max = 1, message = "是否有共借人长度必须介于 0 和 1 之间")
	public String getIsHaveComLoan() {
		return isHaveComLoan;
	}

	public void setIsHaveComLoan(String isHaveComLoan) {
		this.isHaveComLoan = isHaveComLoan;
	}

	@Length(min = 0, max = 1, message = "是否有担保人长度必须介于 0 和 1 之间")
	public String getIsHaveAssure() {
		return isHaveAssure;
	}

	public void setIsHaveAssure(String isHaveAssure) {
		this.isHaveAssure = isHaveAssure;
	}

	/*
	 * @Length(min=0, max=1, message="是否有企业长度必须介于 0 和 1 之间") public String
	 * getIsHaveCompany() { return isHaveCompany; }
	 * 
	 * public void setIsHaveCompany(String isHaveCompany) { this.isHaveCompany =
	 * isHaveCompany; }
	 */

	@Length(min = 0, max = 1, message = "是否加急长度必须介于 0 和 1 之间")
	public String getIsUrgent() {
		return isUrgent;
	}

	public void setIsUrgent(String isUrgent) {
		this.isUrgent = isUrgent;
	}

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public String getSvPlatform() {
		return svPlatform;
	}

	public void setSvPlatform(String svPlatform) {
		this.svPlatform = svPlatform;
	}

	public ApplyRelation getApplyRelation() {
		return applyRelation;
	}

	public void setApplyRelation(ApplyRelation applyRelation) {
		this.applyRelation = applyRelation;
	}

	public String getOperateOrgName() {
		return operateOrgName;
	}

	public void setOperateOrgName(String operateOrgName) {
		this.operateOrgName = operateOrgName;
	}

	public BigDecimal getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(BigDecimal contractAmount) {
		this.contractAmount = contractAmount;
	}
 
    
}