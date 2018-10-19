package com.resoft.credit.checkApproveUnion.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.resoft.common.utils.Constants;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 批复信息授信Entity
 * @author wangguodong
 * @version 2016-12-14
 */
public class CheckApproveUnion extends DataEntity<CheckApproveUnion> {
	
	private static final long serialVersionUID = 1L;
	private String applyNo;		// 申请编号
	private String roleType;   //角色类型
	private String custId;		// 客户编号
	private String relatedId;		// 关联信息编号
	private String custName;		// 客户名称
	private String relatedName;		// 关联信息名称
	private String pricedRisk;		// 风险定价费率
	private String approProductTypeCode;		// 批贷产品类型
	private String approProductTypeName;		// 批贷产品类型名称
	private String approProductId;		// 批贷产品ID
	private String approProductName;		// 批贷产品名称
	private BigDecimal contractAmount;		// 合同金额
	private BigDecimal approAmount;		// 批贷金额
	private BigDecimal loanAmount;		// 放款金额
	private BigDecimal approYearRate;		// 利率
	private BigDecimal guanetongRate;		// 冠E通利率
	private BigDecimal serviceFeeRate;		// 服务费率
	private BigDecimal specialServiceFeeRate;		// 特殊服务费率
	private String serviceFeeType;		// 服务费收取方式
	private BigDecimal serviceFee;		// 服务费
	private BigDecimal specialServiceFee;		// 特殊服务费
	private BigDecimal allServiceFee;		// 服务费总计
	private String approPeriodId;		// 批贷期限ID
	private String approPeriodValue;		// 批贷期限值
	private String approLoanRepayType;		// 还款方式（字典类型：LOAN_REPAY_TYPE）
	private BigDecimal marginRate;		// 保证金率
	private BigDecimal marginAmount;		// 保证金
	private BigDecimal checkFee;		// 外访费
	private String loanModel;		// 借款模式
	private String isUrgent;		// 是否加急
	private String contractType;		// 建议签订合同类型
	private Date approDate;		// 批复日期
	private String taskDefKey;		// 流程ID
	private String processSequence;		// 批复信息流程顺序
	private String remark;		// 备注
	private String loanStatus;		// 放款状态 非表字段
	private String loanId;		// 放款状态 非表字段
	private String contractNo;	//合同号 非表字段
	private BigDecimal qualityServiceMarginAmount;//质量服务保证金 QUALITY_SERVICE_MARGIN_AMOUNT
	private BigDecimal qualityServiceMarginRate;//质量服务保证金率 QUALITY_SERVICE_MARGIN_RATE
	private String productCategory;//产品分类
	
	//采购贷新增字段
	private String topShopName;//上游供应商名称
	private String downShopName;//下游供应商名称
	private BigDecimal	topShopBackRate;//上游供应商返利 
	private BigDecimal	topShopBackMoney;//上游返利总额
	private BigDecimal	topShopMonthRate;//实际月利率
	private BigDecimal	mediacyServiceFee;//居间服务费
	private BigDecimal	interestRateDiff;//月息差
	private BigDecimal realityServiceFee;// 实际服务费
	private Integer addFundPeriod;// 增资期限
	private String confirmFeeFlag;

	private BigDecimal discountInterestRate;//让利后月利率
	private BigDecimal interestMonthlySpread;//利息月利率

	public String getRelatedId() {
		return relatedId;
	}

	public void setRelatedId(String relatedId) {
		this.relatedId = relatedId;
	}

	public String getRelatedName() {
		return relatedName;
	}

	public void setRelatedName(String relatedName) {
		this.relatedName = relatedName;
	}

	public BigDecimal getQualityServiceMarginAmount() {
		return qualityServiceMarginAmount;
	}

	public void setQualityServiceMarginAmount(BigDecimal qualityServiceMarginAmount) {
		this.qualityServiceMarginAmount = qualityServiceMarginAmount;
	}

	public BigDecimal getQualityServiceMarginRate() {
		return qualityServiceMarginRate;
	}

	public void setQualityServiceMarginRate(BigDecimal qualityServiceMarginRate) {
		this.qualityServiceMarginRate = qualityServiceMarginRate;
	}
	
	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public CheckApproveUnion() {
		super();
	}

	public CheckApproveUnion(String id){
		super(id);
	}

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getPricedRisk() {
		return pricedRisk;
	}

	public void setPricedRisk(String pricedRisk) {
		this.pricedRisk = pricedRisk;
	}

	public String getApproProductTypeCode() {
		return approProductTypeCode;
	}

	public void setApproProductTypeCode(String approProductTypeCode) {
		this.approProductTypeCode = approProductTypeCode;
	}

	public String getApproProductTypeName() {
		return approProductTypeName;
	}

	public void setApproProductTypeName(String approProductTypeName) {
		this.approProductTypeName = approProductTypeName;
	}

	public String getApproProductId() {
		return approProductId;
	}

	public void setApproProductId(String approProductId) {
		this.approProductId = approProductId;
	}

	public String getApproProductName() {
		return approProductName;
	}

	public void setApproProductName(String approProductName) {
		this.approProductName = approProductName;
	}

	public BigDecimal getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(BigDecimal contractAmount) {
		this.contractAmount = contractAmount;
	}

	public BigDecimal getApproAmount() {
		return approAmount;
	}

	public void setApproAmount(BigDecimal approAmount) {
		this.approAmount = approAmount;
	}

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public BigDecimal getApproYearRate() {
		return approYearRate;
	}

	public void setApproYearRate(BigDecimal approYearRate) {
		this.approYearRate = approYearRate;
	}

	public BigDecimal getGuanetongRate() {
		return guanetongRate;
	}

	public void setGuanetongRate(BigDecimal guanetongRate) {
		this.guanetongRate = guanetongRate;
	}

	public BigDecimal getServiceFeeRate() {
		return serviceFeeRate;
	}

	public void setServiceFeeRate(BigDecimal serviceFeeRate) {
		this.serviceFeeRate = serviceFeeRate;
	}

	public BigDecimal getSpecialServiceFeeRate() {
		return specialServiceFeeRate;
	}

	public void setSpecialServiceFeeRate(BigDecimal specialServiceFeeRate) {
		this.specialServiceFeeRate = specialServiceFeeRate;
	}

	public String getServiceFeeType() {
		return serviceFeeType;
	}

	public void setServiceFeeType(String serviceFeeType) {
		this.serviceFeeType = serviceFeeType;
	}

	public BigDecimal getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(BigDecimal serviceFee) {
		this.serviceFee = serviceFee;
	}

	public BigDecimal getSpecialServiceFee() {
		return specialServiceFee;
	}

	public void setSpecialServiceFee(BigDecimal specialServiceFee) {
		this.specialServiceFee = specialServiceFee;
	}

	public BigDecimal getAllServiceFee() {
		return allServiceFee;
	}

	public void setAllServiceFee(BigDecimal allServiceFee) {
		this.allServiceFee = allServiceFee;
	}

	public String getApproPeriodId() {
		return approPeriodId;
	}

	public void setApproPeriodId(String approPeriodId) {
		this.approPeriodId = approPeriodId;
	}

	public String getApproPeriodValue() {
		return approPeriodValue;
	}

	public void setApproPeriodValue(String approPeriodValue) {
		this.approPeriodValue = approPeriodValue;
	}

	public String getApproLoanRepayType() {
		return approLoanRepayType;
	}

	public void setApproLoanRepayType(String approLoanRepayType) {
		this.approLoanRepayType = approLoanRepayType;
	}

	public BigDecimal getMarginRate() {
		return marginRate;
	}

	public void setMarginRate(BigDecimal marginRate) {
		this.marginRate = marginRate;
	}

	public BigDecimal getMarginAmount() {
		return marginAmount;
	}

	public void setMarginAmount(BigDecimal marginAmount) {
		this.marginAmount = marginAmount;
	}

	public BigDecimal getCheckFee() {
		return checkFee;
	}

	public void setCheckFee(BigDecimal checkFee) {
		this.checkFee = checkFee;
	}

	public String getLoanModel() {
		return loanModel;
	}

	public void setLoanModel(String loanModel) {
		this.loanModel = loanModel;
	}

	public String getIsUrgent() {
		return isUrgent;
	}

	public void setIsUrgent(String isUrgent) {
		this.isUrgent = isUrgent;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public Date getApproDate() {
		return approDate;
	}

	public void setApproDate(Date approDate) {
		this.approDate = approDate;
	}

	public String getTaskDefKey() {
		return taskDefKey;
	}

	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}

	public String getProcessSequence() {
		return processSequence;
	}

	public void setProcessSequence(String processSequence) {
		this.processSequence = processSequence;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	//服务费收取方式
	public String getServiceFeeTypeLabel() {
		return DictUtils.getDictLabel(serviceFeeType+"", Constants.SERVICE_FEE_TYPE, "");
	}
	//还款方式
	public String getApproLoanRepayTypeLabel() {
		return DictUtils.getDictLabel(approLoanRepayType, Constants.LOAN_REPAY_TYPE, "");
	}
	//贷款模式
	public String getLoanModelLabel() {
		return DictUtils.getDictLabel(loanModel, Constants.LOAN_MODEL, "");
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getLoanStatus() {
		return loanStatus;
	}

	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getTopShopName() {
		return topShopName;
	}

	public void setTopShopName(String topShopName) {
		this.topShopName = topShopName;
	}

	public BigDecimal getTopShopBackRate() {
		return topShopBackRate;
	}

	public void setTopShopBackRate(BigDecimal topShopBackRate) {
		this.topShopBackRate = topShopBackRate;
	}

	public BigDecimal getTopShopBackMoney() {
		return topShopBackMoney;
	}

	public void setTopShopBackMoney(BigDecimal topShopBackMoney) {
		this.topShopBackMoney = topShopBackMoney;
	}

	public BigDecimal getTopShopMonthRate() {
		return topShopMonthRate;
	}

	public void setTopShopMonthRate(BigDecimal topShopMonthRate) {
		this.topShopMonthRate = topShopMonthRate;
	}

	

	public String getDownShopName() {
		return downShopName;
	}

	public void setDownShopName(String downShopName) {
		this.downShopName = downShopName;
	}

	public BigDecimal getMediacyServiceFee() {
		return mediacyServiceFee;
	}

	public void setMediacyServiceFee(BigDecimal mediacyServiceFee) {
		this.mediacyServiceFee = mediacyServiceFee;
	}

	public BigDecimal getInterestRateDiff() {
		return interestRateDiff;
	}

	public void setInterestRateDiff(BigDecimal interestRateDiff) {
		this.interestRateDiff = interestRateDiff;
	}
	
	
	
	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public BigDecimal getRealityServiceFee() {
		return realityServiceFee;
	}

	public void setRealityServiceFee(BigDecimal realityServiceFee) {
		this.realityServiceFee = realityServiceFee;
	}

	public Integer getAddFundPeriod() {
		return addFundPeriod;
	}

	public void setAddFundPeriod(Integer addFundPeriod) {
		this.addFundPeriod = addFundPeriod;
	}

	public String getConfirmFeeFlag() {
		return confirmFeeFlag;
	}

	public void setConfirmFeeFlag(String confirmFeeFlag) {
		this.confirmFeeFlag = confirmFeeFlag;
	}

	public BigDecimal getDiscountInterestRate() {
		return discountInterestRate;
	}

	public void setDiscountInterestRate(BigDecimal discountInterestRate) {
		this.discountInterestRate = discountInterestRate;
	}

	public BigDecimal getInterestMonthlySpread() {
		return interestMonthlySpread;
	}

	public void setInterestMonthlySpread(BigDecimal interestMonthlySpread) {
		this.interestMonthlySpread = interestMonthlySpread;
	}
}