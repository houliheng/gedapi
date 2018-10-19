package com.resoft.postloan.mortgage.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class Mortgage extends DataEntity<Mortgage> {
	private static final long serialVersionUID = 1L;
	private String dealApplyNo;//申请编号
	private String contractNo;//合同编号
	private String dealAmount;// 处置金额
	private String dealAllAmount;// 处置总金额
	private String dealDescription;// 意见
	private String mortgageId;//抵押物Id
	private String dealStatus;//处置状态
	
	
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getDealStatus() {
		return dealStatus;
	}

	public void setDealStatus(String dealStatus) {
		this.dealStatus = dealStatus;
	}

	public String getDealApplyNo() {
		return dealApplyNo;
	}

	public void setDealApplyNo(String dealApplyNo) {
		this.dealApplyNo = dealApplyNo;
	}

	public String getMortgageId() {
		return mortgageId;
	}

	public void setMortgageId(String mortgageId) {
		this.mortgageId = mortgageId;
	}

	public String getDealDescription() {
		return dealDescription;
	}

	public void setDealDescription(String dealDescription) {
		this.dealDescription = dealDescription;
	}

	public String getDealAmount() {
		return dealAmount;
	}

	public void setDealAmount(String dealAmount) {
		this.dealAmount = dealAmount;
	}

	public String getDealAllAmount() {
		return dealAllAmount;
	}

	public void setDealAllAmount(String dealAllAmount) {
		this.dealAllAmount = dealAllAmount;
	}

}
