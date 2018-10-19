package com.resoft.postloan.applyExtend.entity;

import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * 合同展期申请Entity
 * 
 * @author wangguodong
 * @version 2016-05-23
 */
public class ApplyExtend extends DataEntity<ApplyExtend> {

	private static final long serialVersionUID = 1L;
	private String contractNo; // 合同编号
	private String procInsId; // 流程实例ID
	private BigDecimal extendAmount; // 申请展期金额
	private String extendFeeRate1; // 展期费率1
	private String extendFeeRate2; // 展期费率2
	private BigDecimal extendMargin; // 展期保证金
	private Date extendStartDate; // 展期开始日期
	private Date extendEndDate; // 展期结束日期
	private BigDecimal extendServiceFee; // 展期服务费
	private String extendPeriod; // 展期期限值
	private String extendInterest; // 展期利息
	private BigDecimal extendFeePercent; // 展期费比例
	private BigDecimal extendFee; // 展期费
	private String extendAllAmount; // 展期应缴总额
	private String extendRerson; // 展期理由及风控措施
	private String applySpeciall; // 特别申请事项
	private String extendApplyStatus; // 展期申请状态（1保存未申请2提交申请待审批）
	private String operateOrgName; // 所属分公司name(经办机构)
	private String operateOrgId; // 所属分公司id(经办机构id)
	private String custName;// 借款人姓名
	private String contractAmount;// 合同金额
	private Date conStartDate;// 合同起始时间
	private Date conEndDate;// 合同结束时间
	private String approPeriodValue;// 合同期限
	private String flag;
	private Office company;// 机构 树
	
	public Office getCompany() {
		return company;
	}

	public void setCompany(Office company) {
		this.company = company;
	}

	public String getOperateOrgId() {
		return operateOrgId;
	}

	public void setOperateOrgId(String operateOrgId) {
		this.operateOrgId = operateOrgId;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getApproPeriodValue() {
		return approPeriodValue;
	}

	public void setApproPeriodValue(String approPeriodValue) {
		this.approPeriodValue = approPeriodValue;
	}

	public String getOperateOrgName() {
		return operateOrgName;
	}

	public void setOperateOrgName(String operateOrgName) {
		this.operateOrgName = operateOrgName;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(String contractAmount) {
		this.contractAmount = contractAmount;
	}

	public Date getConStartDate() {
		return conStartDate;
	}

	public void setConStartDate(Date conStartDate) {
		this.conStartDate = conStartDate;
	}

	public Date getConEndDate() {
		return conEndDate;
	}

	public void setConEndDate(Date conEndDate) {
		this.conEndDate = conEndDate;
	}

	public ApplyExtend() {
		super();
	}

	public ApplyExtend(String id) {
		super(id);
	}

	@Length(min = 0, max = 50, message = "合同编号长度必须介于 0 和 50 之间")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public BigDecimal getExtendAmount() {
		return extendAmount;
	}

	public void setExtendAmount(BigDecimal extendAmount) {
		this.extendAmount = extendAmount;
	}

	public String getExtendFeeRate1() {
		return extendFeeRate1;
	}

	public void setExtendFeeRate1(String extendFeeRate1) {
		this.extendFeeRate1 = extendFeeRate1;
	}

	public String getExtendFeeRate2() {
		return extendFeeRate2;
	}

	public void setExtendFeeRate2(String extendFeeRate2) {
		this.extendFeeRate2 = extendFeeRate2;
	}

	public BigDecimal getExtendMargin() {
		return extendMargin;
	}

	public void setExtendMargin(BigDecimal extendMargin) {
		this.extendMargin = extendMargin;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getExtendStartDate() {
		return extendStartDate;
	}

	public void setExtendStartDate(Date extendStartDate) {
		this.extendStartDate = extendStartDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getExtendEndDate() {
		return extendEndDate;
	}

	public void setExtendEndDate(Date extendEndDate) {
		this.extendEndDate = extendEndDate;
	}

	public BigDecimal getExtendServiceFee() {
		return extendServiceFee;
	}

	public void setExtendServiceFee(BigDecimal extendServiceFee) {
		this.extendServiceFee = extendServiceFee;
	}

	@Length(min = 0, max = 5, message = "展期期限值长度必须介于 0 和 5 之间")
	public String getExtendPeriod() {
		return extendPeriod;
	}

	public void setExtendPeriod(String extendPeriod) {
		this.extendPeriod = extendPeriod;
	}

	public String getExtendInterest() {
		return extendInterest;
	}

	public void setExtendInterest(String extendInterest) {
		this.extendInterest = extendInterest;
	}

	@Length(min = 0, max = 2, message = "展期费比例长度必须介于 0 和 2 之间")
	public BigDecimal getExtendFeePercent() {
		return extendFeePercent;
	}

	public void setExtendFeePercent(BigDecimal extendFeePercent) {
		this.extendFeePercent = extendFeePercent;
	}

	public BigDecimal getExtendFee() {
		return extendFee;
	}

	public void setExtendFee(BigDecimal extendFee) {
		this.extendFee = extendFee;
	}

	public String getExtendAllAmount() {
		return extendAllAmount;
	}

	public void setExtendAllAmount(String extendAllAmount) {
		this.extendAllAmount = extendAllAmount;
	}

	@Length(min = 0, max = 1000, message = "展期理由及风控措施长度必须介于 0 和 1000 之间")
	public String getExtendRerson() {
		return extendRerson;
	}

	public void setExtendRerson(String extendRerson) {
		this.extendRerson = extendRerson;
	}

	@Length(min = 0, max = 1000, message = "特别申请事项长度必须介于 0 和 1000 之间")
	public String getApplySpeciall() {
		return applySpeciall;
	}

	public void setApplySpeciall(String applySpeciall) {
		this.applySpeciall = applySpeciall;
	}

	@Length(min = 0, max = 4, message = "展期申请状态（1保存未申请2提交申请待审批）长度必须介于 0 和 4 之间")
	public String getExtendApplyStatus() {
		return extendApplyStatus;
	}

	public void setExtendApplyStatus(String extendApplyStatus) {
		this.extendApplyStatus = extendApplyStatus;
	}

	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}

}