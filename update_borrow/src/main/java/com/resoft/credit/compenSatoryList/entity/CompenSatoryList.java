package com.resoft.credit.compenSatoryList.entity;

import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class CompenSatoryList  extends DataEntity<CompenSatoryList> {
	private static final long serialVersionUID = 1L;
	private String compenSatoryFlag;//代偿状态
	private String overdueStatus;//期供状态
	private String contractNo;//合同编号
	private String periodNum;//期数
	private String repayAmount;//应还金额
	private String capitalInterestAmount;//应还本息
	private String fineAmount;//应还罚息
	private String allAmount;//实还金额
	private String factInterestCapital;//实还本息
	private String factFineAmount;//实还罚息
	private String remainCompensatoryAmount;//代偿余额 
	private Date lastTime;//最新日期
	private String sumRemainAmount; //代偿余额总和
	private Date fineDate; //应还时间

	public String getSumRemainAmount() {
		return sumRemainAmount;
	}

	public void setSumRemainAmount(String sumRemainAmount) {
		this.sumRemainAmount = sumRemainAmount;
	}

	public Date getFineDate() {
		return fineDate;
	}

	public void setFineDate(Date fineDate) {
		this.fineDate = fineDate;
	}

	public CompenSatoryList() {
		super();
	}

	public CompenSatoryList(String id){
		super(id);
	}

	public String getCompenSatoryFlag() {
		return compenSatoryFlag;
	}

	public void setCompenSatoryFlag(String compenSatoryFlag) {
		this.compenSatoryFlag = compenSatoryFlag;
	}

	public String getOverdueStatus() {
		return overdueStatus;
	}

	public void setOverdueStatus(String overdueStatus) {
		this.overdueStatus = overdueStatus;
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

	public String getRepayAmount() {
		return repayAmount;
	}

	public void setRepayAmount(String repayAmount) {
		this.repayAmount = repayAmount;
	}

	public String getCapitalInterestAmount() {
		return capitalInterestAmount;
	}

	public void setCapitalInterestAmount(String capitalInterestAmount) {
		this.capitalInterestAmount = capitalInterestAmount;
	}

	public String getFineAmount() {
		return fineAmount;
	}

	public void setFineAmount(String fineAmount) {
		this.fineAmount = fineAmount;
	}

	public String getAllAmount() {
		return allAmount;
	}

	public void setAllAmount(String allAmount) {
		this.allAmount = allAmount;
	}

	public String getFactInterestCapital() {
		return factInterestCapital;
	}

	public void setFactInterestCapital(String factInterestCapital) {
		this.factInterestCapital = factInterestCapital;
	}

	public String getFactFineAmount() {
		return factFineAmount;
	}

	public void setFactFineAmount(String factFineAmount) {
		this.factFineAmount = factFineAmount;
	}

	public String getRemainCompensatoryAmount() {
		return remainCompensatoryAmount;
	}

	public void setRemainCompensatoryAmount(String remainCompensatoryAmount) {
		this.remainCompensatoryAmount = remainCompensatoryAmount;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	
}
