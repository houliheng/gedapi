package com.resoft.postloan.collateralDisposalConclusion.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 抵押物处置流程意见Entity
 * @author wangguodong
 * @version 2017-01-09
 */
public class CollateralDisposalConclusion extends DataEntity<CollateralDisposalConclusion> {
	
	private static final long serialVersionUID = 1L;
	private String contractNo;		// 合同编号
	private String periodNum;		// 期数
	private String taskDefKey;		// 流程标识
	private String dealAllAmount;		// 处置总金额
	private String conclusionDesc;		// 审批意见
	
	public CollateralDisposalConclusion() {
		super();
	}

	public CollateralDisposalConclusion(String id){
		super(id);
	}

	@Length(min=0, max=32, message="合同编号长度必须介于 0 和 32 之间")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	@Length(min=0, max=4, message="期数长度必须介于 0 和 4 之间")
	public String getPeriodNum() {
		return periodNum;
	}

	public void setPeriodNum(String periodNum) {
		this.periodNum = periodNum;
	}
	
	@Length(min=0, max=50, message="流程标识长度必须介于 0 和 50 之间")
	public String getTaskDefKey() {
		return taskDefKey;
	}

	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}
	
	public String getDealAllAmount() {
		return dealAllAmount;
	}

	public void setDealAllAmount(String dealAllAmount) {
		this.dealAllAmount = dealAllAmount;
	}
	
	@Length(min=0, max=1000, message="审批意见长度必须介于 0 和 1000 之间")
	public String getConclusionDesc() {
		return conclusionDesc;
	}

	public void setConclusionDesc(String conclusionDesc) {
		this.conclusionDesc = conclusionDesc;
	}
	
}