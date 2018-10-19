package com.resoft.postloan.extendRepayPlan.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 合同展期还款计划Entity
 * @author wangguodong
 * @version 2016-05-23
 */
public class ExtendRepayPlan extends DataEntity<ExtendRepayPlan> {
	
	private static final long serialVersionUID = 1L;
	private String contractNo;		// 原合同号
	private String extendPeriodNum;		// 展期期数值
	private Date extendRepayDate;		// 展期还款日期
	private Double extendServiceFee;		// 展期-服务费
	private Double extendManagementFee;		// 展期账户管理费
	private Double extendInterest;		// 展期利息
	private Double extendCapital;		// 展期本金
	private Double extendPenalty;		// 展期违约金
	private String extendFine;		// 展期罚息
	private Double extendRepayAll;		// 展期该期应还总额
	
	public ExtendRepayPlan() {
		super();
	}

	public ExtendRepayPlan(String id){
		super(id);
	}

	@Length(min=0, max=50, message="原合同号长度必须介于 0 和 50 之间")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	@Length(min=0, max=10, message="展期期数值长度必须介于 0 和 10 之间")
	public String getExtendPeriodNum() {
		return extendPeriodNum;
	}

	public void setExtendPeriodNum(String extendPeriodNum) {
		this.extendPeriodNum = extendPeriodNum;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getExtendRepayDate() {
		return extendRepayDate;
	}

	public void setExtendRepayDate(Date extendRepayDate) {
		this.extendRepayDate = extendRepayDate;
	}
	
	public Double getExtendServiceFee() {
		return extendServiceFee;
	}

	public void setExtendServiceFee(Double extendServiceFee) {
		this.extendServiceFee = extendServiceFee;
	}
	
	public Double getExtendManagementFee() {
		return extendManagementFee;
	}

	public void setExtendManagementFee(Double extendManagementFee) {
		this.extendManagementFee = extendManagementFee;
	}
	
	public Double getExtendInterest() {
		return extendInterest;
	}

	public void setExtendInterest(Double extendInterest) {
		this.extendInterest = extendInterest;
	}
	
	public Double getExtendCapital() {
		return extendCapital;
	}

	public void setExtendCapital(Double extendCapital) {
		this.extendCapital = extendCapital;
	}
	
	public Double getExtendPenalty() {
		return extendPenalty;
	}

	public void setExtendPenalty(Double extendPenalty) {
		this.extendPenalty = extendPenalty;
	}
	
	public String getExtendFine() {
		return extendFine;
	}

	public void setExtendFine(String extendFine) {
		this.extendFine = extendFine;
	}
	
	public Double getExtendRepayAll() {
		return extendRepayAll;
	}

	public void setExtendRepayAll(Double extendRepayAll) {
		this.extendRepayAll = extendRepayAll;
	}
	
}