package com.resoft.credit.loanApply.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * @reqno:H1509130076
 * @date-designer:2015年10月12日-songmin
 * @date-author:2015年10月12日-songmin:贷款申请信息表实体类   Table：cre_apply_info
 */
public class CreApplyInfo extends DataEntity<CreApplyInfo>{
	private static final long serialVersionUID = 9144782008913427548L;
	
	private String id;			// varchar(32) default null comment 'id',
	private String applyId;		// varchar(32) default null comment '申请id',
	private BigDecimal  applyAmount;	// decimal(18,2) default null comment '申请金额',
	private String applyProductTypeCode;// varchar(10) default null comment '申请产品类型编码',
	private String applyProductTypeName;// varchar(100) default null comment '申请产品类型名称',
	private String applyProductId;		// varchar(32) default null comment '申请产品id',
	private String applyProductName;	// varchar(100) default null comment '申请产品名称',
	private String applyPeriodId;		// varchar(32) default null comment '申请期限id',
	private String applyPeriodValue;	// varchar(10) default null comment '申请期限值',
	private BigDecimal applyYearRate;	// decimal(18,2) default null comment '年利率',
	private String loanRepayType;		// varchar(10) default null comment '还款方式（字典类型：loan_repay_type）',
	private String loanPurpose;			// varchar(10) default null comment '借款用途（字典类型：loan_purpost）',
	private String otherLoanPurpose;	// varchar(200) default null comment '其他用途说明',
	private BigDecimal acceptHighestAmount;// decimal(18,2) default null comment '可接受最高月还款额',
	private String kindLoan;			// varchar(10) default null comment '贷款类型（字典类型：kind_loan）',
	private int fraud;					// tinyint(4) default null comment '是否曾被欺诈认定',
	private String description;			// varchar(1000) default null comment '补充说明',
	private Date updateTime;			// timestamp null default null comment '更新时间',
	private String updatorId;			// varchar(32) default null comment '更新人id',
	private String procInsId;			// varchar(64) default null
	private String monthRepayAmount;//月还款金额
	
	/**
	 * @reqno:H1509130075
	 * @date-designer:2015年10月13日-songmin
	 * @date-author:2015年10月13日-songmin:CRE_信贷审批_贷款初审_结论信息_查询
	 * 				扩展字段：客户名称、证件类型、证件号、产品期限类型名称
	 */
	private String custName;//客户名称
	private String idType;//证件类型
	private String idTypeLabel;//证件类型
	private String idNum;//证件号
	private String applyPeriodType;//产品期限类型
	/**
	 * @reqno:H1510080091
	 * @date-designer:2015年10月15日-songmin
	 * @date-author:2015年10月15日-songmin:CRE_信贷审批_申请录入_贷款申请信息_贷款申请信息默认数据项展现
	 * 		新增实体类字段，这些字段以前是通过上面对应的字段共用字段节约下来的，但是在有些地方，2个值都是需要的，所以最终将2个值通过2个属性来接收
	 */
	private String applyPeriodTypeLabel;//产品期限类型名称
	private String loanRepayTypeLabel;//还款方式
	private String loanPurposeLabel;//借款用途
	
	
	/**
	 * @return the idTypeLabel
	 */
	public String getIdTypeLabel() {
		return idTypeLabel;
	}
	/**
	 * @param idTypeLabel the idTypeLabel to set
	 */
	public void setIdTypeLabel(String idTypeLabel) {
		this.idTypeLabel = idTypeLabel;
	}
	/**
	 * @return the applyPeriodTypeLabel
	 */
	public String getApplyPeriodTypeLabel() {
		return applyPeriodTypeLabel;
	}
	/**
	 * @param applyPeriodTypeLabel the applyPeriodTypeLabel to set
	 */
	public void setApplyPeriodTypeLabel(String applyPeriodTypeLabel) {
		this.applyPeriodTypeLabel = applyPeriodTypeLabel;
	}
	/**
	 * @return the loanRepayTypeLabel
	 */
	public String getLoanRepayTypeLabel() {
		return loanRepayTypeLabel;
	}
	/**
	 * @param loanRepayTypeLabel the loanRepayTypeLabel to set
	 */
	public void setLoanRepayTypeLabel(String loanRepayTypeLabel) {
		this.loanRepayTypeLabel = loanRepayTypeLabel;
	}
	/**
	 * @return the loanPurposeLabel
	 */
	public String getLoanPurposeLabel() {
		return loanPurposeLabel;
	}
	/**
	 * @param loanPurposeLabel the loanPurposeLabel to set
	 */
	public void setLoanPurposeLabel(String loanPurposeLabel) {
		this.loanPurposeLabel = loanPurposeLabel;
	}
	/**
	 * @return the monthRepayAmount
	 */
	public String getMonthRepayAmount() {
		return monthRepayAmount;
	}
	/**
	 * @param monthRepayAmount the monthRepayAmount to set
	 */
	public void setMonthRepayAmount(String monthRepayAmount) {
		this.monthRepayAmount = monthRepayAmount;
	}
	/**
	 * @return the custName
	 */
	public String getCustName() {
		return custName;
	}
	/**
	 * @param custName the custName to set
	 */
	public void setCustName(String custName) {
		this.custName = custName;
	}
	/**
	 * @return the idNum
	 */
	public String getIdNum() {
		return idNum;
	}
	/**
	 * @param idNum the idNum to set
	 */
	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	/**
	 * @return the idType
	 */
	public String getIdType() {
		return idType;
	}
	/**
	 * @param idType the idType to set
	 */
	public void setIdType(String idType) {
		this.idType = idType;
	}
	/**
	 * @return the applyPeriodType
	 */
	public String getApplyPeriodType() {
		return applyPeriodType;
	}
	/**
	 * @param applyPeriodType the applyPeriodType to set
	 */
	public void setApplyPeriodType(String applyPeriodType) {
		this.applyPeriodType = applyPeriodType;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the applyId
	 */
	public String getApplyNo() {
		return applyId;
	}
	/**
	 * @param applyId the applyId to set
	 */
	public void setApplyNo(String applyId) {
		this.applyId = applyId;
	}
	/**
	 * @return the applyAmount
	 */
	public BigDecimal getApplyAmount() {
		return applyAmount;
	}
	/**
	 * @param applyAmount the applyAmount to set
	 */
	public void setApplyAmount(BigDecimal applyAmount) {
		this.applyAmount = applyAmount;
	}
	/**
	 * @return the applyProductTypeCode
	 */
	public String getApplyProductTypeCode() {
		return applyProductTypeCode;
	}
	/**
	 * @param applyProductTypeCode the applyProductTypeCode to set
	 */
	public void setApplyProductTypeCode(String applyProductTypeCode) {
		this.applyProductTypeCode = applyProductTypeCode;
	}
	/**
	 * @return the applyProductTypeName
	 */
	public String getApplyProductTypeName() {
		return applyProductTypeName;
	}
	/**
	 * @param applyProductTypeName the applyProductTypeName to set
	 */
	public void setApplyProductTypeName(String applyProductTypeName) {
		this.applyProductTypeName = applyProductTypeName;
	}
	/**
	 * @return the applyProductId
	 */
	public String getApplyProductId() {
		return applyProductId;
	}
	/**
	 * @param applyProductId the applyProductId to set
	 */
	public void setApplyProductId(String applyProductId) {
		this.applyProductId = applyProductId;
	}
	/**
	 * @return the applyProductName
	 */
	public String getApplyProductName() {
		return applyProductName;
	}
	/**
	 * @param applyProductName the applyProductName to set
	 */
	public void setApplyProductName(String applyProductName) {
		this.applyProductName = applyProductName;
	}
	/**
	 * @return the applyPeriodId
	 */
	public String getApplyPeriodId() {
		return applyPeriodId;
	}
	/**
	 * @param applyPeriodId the applyPeriodId to set
	 */
	public void setApplyPeriodId(String applyPeriodId) {
		this.applyPeriodId = applyPeriodId;
	}
	/**
	 * @return the applyPeriodValue
	 */
	public String getApplyPeriodValue() {
		return applyPeriodValue;
	}
	/**
	 * @param applyPeriodValue the applyPeriodValue to set
	 */
	public void setApplyPeriodValue(String applyPeriodValue) {
		this.applyPeriodValue = applyPeriodValue;
	}
	/**
	 * @return the applyYearRate
	 */
	public BigDecimal getApplyYearRate() {
		return applyYearRate;
	}
	/**
	 * @param applyYearRate the applyYearRate to set
	 */
	public void setApplyYearRate(BigDecimal applyYearRate) {
		this.applyYearRate = applyYearRate;
	}
	/**
	 * @return the loanRepayType
	 */
	public String getLoanRepayType() {
		return loanRepayType;
	}
	/**
	 * @param loanRepayType the loanRepayType to set
	 */
	public void setLoanRepayType(String loanRepayType) {
		this.loanRepayType = loanRepayType;
	}
	/**
	 * @return the loanPurpose
	 */
	public String getLoanPurpose() {
		return loanPurpose;
	}
	/**
	 * @param loanPurpose the loanPurpose to set
	 */
	public void setLoanPurpose(String loanPurpose) {
		this.loanPurpose = loanPurpose;
	}
	/**
	 * @return the otherLoanPurpose
	 */
	public String getOtherLoanPurpose() {
		return otherLoanPurpose;
	}
	/**
	 * @param otherLoanPurpose the otherLoanPurpose to set
	 */
	public void setOtherLoanPurpose(String otherLoanPurpose) {
		this.otherLoanPurpose = otherLoanPurpose;
	}
	/**
	 * @return the acceptHighestAmount
	 */
	public BigDecimal getAcceptHighestAmount() {
		return acceptHighestAmount;
	}
	/**
	 * @param acceptHighestAmount the acceptHighestAmount to set
	 */
	public void setAcceptHighestAmount(BigDecimal acceptHighestAmount) {
		this.acceptHighestAmount = acceptHighestAmount;
	}
	/**
	 * @return the kindLoan
	 */
	public String getKindLoan() {
		return kindLoan;
	}
	/**
	 * @param kindLoan the kindLoan to set
	 */
	public void setKindLoan(String kindLoan) {
		this.kindLoan = kindLoan;
	}
	/**
	 * @return the fraud
	 */
	public int getFraud() {
		return fraud;
	}
	/**
	 * @param fraud the fraud to set
	 */
	public void setFraud(int fraud) {
		this.fraud = fraud;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * @return the updatorId
	 */
	public String getUpdatorId() {
		return updatorId;
	}
	/**
	 * @param updatorId the updatorId to set
	 */
	public void setUpdatorId(String updatorId) {
		this.updatorId = updatorId;
	}
	/**
	 * @return the procInsId
	 */
	public String getProcInsId() {
		return procInsId;
	}
	/**
	 * @param procInsId the procInsId to set
	 */
	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
}
