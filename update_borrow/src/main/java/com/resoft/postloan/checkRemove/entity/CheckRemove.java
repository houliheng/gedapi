package com.resoft.postloan.checkRemove.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 核销实体类 VO Entity
 * 
 * @author zhaohuakui
 * @version 2016-05-23
 */
public class CheckRemove extends DataEntity<CheckRemove> {

	private static final long serialVersionUID = 1L;
	private String contractNo; // 合同编号
	private String custId; // 客户号
	private String custName; // 客户名称
	private BigDecimal contractAmount; // 合同金额
	private Date conStartDate; // 合同起始日期
	private Date conEndDate; // 合同结束日期
	private BigDecimal currOverdueAmount; // 当前逾期金额
	private String applySuggestion;// 核销原因
	private String approveSuggestion;// 审核意见 非表中字段
	private String approveSuggestionDQ;// 区域审核意见
	private String approveSuggestionZB;// 总部审核意见
	private String checkRemoveStatus;// 核销状态
	private String ZBorDQ;// 区分总部和大区
	private String approverDQSH;// 区域审核人
	private String approverZBSH;// 总部审核人

	public String getApproveSuggestion() {
		return approveSuggestion;
	}

	public void setApproveSuggestion(String approveSuggestion) {
		this.approveSuggestion = approveSuggestion;
	}

	public String getApproveSuggestionDQ() {
		return approveSuggestionDQ;
	}

	public void setApproveSuggestionDQ(String approveSuggestionDQ) {
		this.approveSuggestionDQ = approveSuggestionDQ;
	}

	public String getApproveSuggestionZB() {
		return approveSuggestionZB;
	}

	public void setApproveSuggestionZB(String approveSuggestionZB) {
		this.approveSuggestionZB = approveSuggestionZB;
	}

	public String getApproverDQSH() {
		return approverDQSH;
	}

	public void setApproverDQSH(String approverDQSH) {
		this.approverDQSH = approverDQSH;
	}

	public String getApproverZBSH() {
		return approverZBSH;
	}

	public void setApproverZBSH(String approverZBSH) {
		this.approverZBSH = approverZBSH;
	}

	public String getZBorDQ() {
		return ZBorDQ;
	}

	public void setZBorDQ(String zBorDQ) {
		ZBorDQ = zBorDQ;
	}

	public String getApplySuggestion() {
		return applySuggestion;
	}

	public void setApplySuggestion(String applySuggestion) {
		this.applySuggestion = applySuggestion;
	}

	public String getCheckRemoveStatus() {
		return checkRemoveStatus;
	}

	public void setCheckRemoveStatus(String checkRemoveStatus) {
		this.checkRemoveStatus = checkRemoveStatus;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public BigDecimal getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(BigDecimal contractAmount) {
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

	public BigDecimal getCurrOverdueAmount() {
		return currOverdueAmount;
	}

	public void setCurrOverdueAmount(BigDecimal currOverdueAmount) {
		this.currOverdueAmount = currOverdueAmount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}