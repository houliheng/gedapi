package com.resoft.accounting.applyAdvanceRepay.entity;

import java.math.BigDecimal;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 提前还款Entity
 * 
 * @author yanwanmei
 * @version 2016-03-03
 */
public class ApplyAdvanceRepay extends DataEntity<ApplyAdvanceRepay> {

	private static final long serialVersionUID = 1L;
	private String contractNo; // 合同编号
	private String periodNum; // 执行期数
	private String advanceDeductType; // 提前还款费用收取方式
	private String advanceDeductTypeName; // 提前还款费用收取方式名称
	private BigDecimal advanceDeductFee; // 提前还款费用
	private BigDecimal advanceDeductFeeExempt; // 申请提前还款费用
	private BigDecimal remainDeductAmount; // 剩余应还总额
	private BigDecimal remainDeductAmountExempt; // 申请剩余应还总额
	private BigDecimal allDeductAmount; // 全额应还总金额（包含提前还款费用）
	private BigDecimal allDeductAmountApply; // 申请全额应还总金额（包含提前还款费用）
	private String advanceDeductStatus; // 全额还款划扣状态
	private String orgId; // 创建人机构ID
	private String advanceDescription; // 申请说明
	private BigDecimal approveDeductFee; // 提前还款费用批复金额
	private BigDecimal approveRemainDeductAmount;// 剩余应还总额批复金额
	private BigDecimal approveAllDeductAmount;// 全额应还总金额（包含提前还款费用）批复金额
	private String approveDescription;// 批复原因说明
	private String procInsId;// 流程id

	public ApplyAdvanceRepay() {
		super();
	}

	public ApplyAdvanceRepay(String id) {
		super(id);
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getPeriodNum() {
		return periodNum;
	}

	public void setPeriodNum(String periodNum) {
		this.periodNum = periodNum;
	}

	public String getAdvanceDeductType() {
		return advanceDeductType;
	}

	public void setAdvanceDeductType(String advanceDeductType) {
		this.advanceDeductType = advanceDeductType;
	}

	public String getAdvanceDeductTypeName() {
		return advanceDeductTypeName;
	}

	public void setAdvanceDeductTypeName(String advanceDeductTypeName) {
		this.advanceDeductTypeName = advanceDeductTypeName;
	}

	public BigDecimal getAdvanceDeductFee() {
		return advanceDeductFee;
	}

	public void setAdvanceDeductFee(BigDecimal advanceDeductFee) {
		this.advanceDeductFee = advanceDeductFee;
	}

	public BigDecimal getAdvanceDeductFeeExempt() {
		return advanceDeductFeeExempt;
	}

	public void setAdvanceDeductFeeExempt(BigDecimal advanceDeductFeeExempt) {
		this.advanceDeductFeeExempt = advanceDeductFeeExempt;
	}

	public BigDecimal getRemainDeductAmount() {
		return remainDeductAmount;
	}

	public void setRemainDeductAmount(BigDecimal remainDeductAmount) {
		this.remainDeductAmount = remainDeductAmount;
	}

	public BigDecimal getRemainDeductAmountExempt() {
		return remainDeductAmountExempt;
	}

	public void setRemainDeductAmountExempt(BigDecimal remainDeductAmountExempt) {
		this.remainDeductAmountExempt = remainDeductAmountExempt;
	}

	public BigDecimal getAllDeductAmount() {
		return allDeductAmount;
	}

	public void setAllDeductAmount(BigDecimal allDeductAmount) {
		this.allDeductAmount = allDeductAmount;
	}

	public BigDecimal getAllDeductAmountApply() {
		return allDeductAmountApply;
	}

	public void setAllDeductAmountApply(BigDecimal allDeductAmountApply) {
		this.allDeductAmountApply = allDeductAmountApply;
	}

	public String getAdvanceDeductStatus() {
		return advanceDeductStatus;
	}

	public void setAdvanceDeductStatus(String advanceDeductStatus) {
		this.advanceDeductStatus = advanceDeductStatus;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getAdvanceDescription() {
		return advanceDescription;
	}

	public void setAdvanceDescription(String advanceDescription) {
		this.advanceDescription = advanceDescription;
	}

	public BigDecimal getApproveDeductFee() {
		return approveDeductFee;
	}

	public void setApproveDeductFee(BigDecimal approveDeductFee) {
		this.approveDeductFee = approveDeductFee;
	}

	public BigDecimal getApproveRemainDeductAmount() {
		return approveRemainDeductAmount;
	}

	public void setApproveRemainDeductAmount(BigDecimal approveRemainDeductAmount) {
		this.approveRemainDeductAmount = approveRemainDeductAmount;
	}

	public BigDecimal getApproveAllDeductAmount() {
		return approveAllDeductAmount;
	}

	public void setApproveAllDeductAmount(BigDecimal approveAllDeductAmount) {
		this.approveAllDeductAmount = approveAllDeductAmount;
	}

	public String getApproveDescription() {
		return approveDescription;
	}

	public void setApproveDescription(String approveDescription) {
		this.approveDescription = approveDescription;
	}

	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
}