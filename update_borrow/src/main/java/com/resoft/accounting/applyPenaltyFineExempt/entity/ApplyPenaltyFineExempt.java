package com.resoft.accounting.applyPenaltyFineExempt.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 违约金罚息减免Entity
 * @author yanwanmei
 * @version 2016-03-03
 */
public class ApplyPenaltyFineExempt extends DataEntity<ApplyPenaltyFineExempt> {
	
	private static final long serialVersionUID = 1L;
	private String contractNo;		// 合同编号
	private String periodNum;		// 期数
	private String penaltyExemptAmount;		// 申请违约金减免  PENALTY_EXEMPT_AMOUNT
	private String fineExemptAmount;		// 申请罚息减免
	private Date throughTime;		// 通过时间
	private Date throughTime2;     //通过时间2
	private String description;		// 申请意见
	private String dataDt; //数据时间  汇总表用
	private String applyStatus; //审核状态  APPLY_STATUS
	private String doneOrDoFlag; //已办代办标记
	private String procInsId;   // 流程id PROC_INS_ID
	
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}

	public String getDoneOrDoFlag() {
		return doneOrDoFlag;
	}

	public void setDoneOrDoFlag(String doneOrDoFlag) {
		this.doneOrDoFlag = doneOrDoFlag;
	}

	public String getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}

	public Date getThroughTime2() {
		return throughTime2;
	}

	public void setThroughTime2(Date throughTime2) {
		this.throughTime2 = throughTime2;
	}

	public String getDataDt() {
		return dataDt;
	}

	public void setDataDt(String dataDt) {
		this.dataDt = dataDt;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ApplyPenaltyFineExempt() {
		super();
	}

	public ApplyPenaltyFineExempt(String id){
		super(id);
	}

	@Length(min=0, max=50, message="合同编号长度必须介于 0 和 50 之间")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	@Length(min=0, max=10, message="期数长度必须介于 0 和 10 之间")
	public String getPeriodNum() {
		return periodNum;
	}

	public void setPeriodNum(String periodNum) {
		this.periodNum = periodNum;
	}
	
	public String getpenaltyExemptAmount() {
		return penaltyExemptAmount;
	}

	public void setpenaltyExemptAmount(String penaltyExemptAmount) {
		this.penaltyExemptAmount = penaltyExemptAmount;
	}
	
	public String getFineExemptAmount() {
		return fineExemptAmount;
	}

	public void setFineExemptAmount(String fineExemptAmount) {
		this.fineExemptAmount = fineExemptAmount;
	}
	
	
	public Date getThroughTime() {
		return throughTime;
	}

	public void setThroughTime(Date throughTime) {
		this.throughTime = throughTime;
	}
	
	@Length(min=0, max=1000, message="申请意见长度必须介于 0 和 1000 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}