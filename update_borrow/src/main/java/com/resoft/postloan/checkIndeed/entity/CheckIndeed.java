package com.resoft.postloan.checkIndeed.entity;

import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 借后实地外访Entity
 * @author wangguodong
 * @version 2016-08-31
 */
public class CheckIndeed extends DataEntity<CheckIndeed> {
	
	private static final long serialVersionUID = 1L;
	private String contractNo;		// 申请编号
	private String allocateId;// 分配id ALLOCATE_ID
	private Date checkDate;		// 外访日期
	private String checkUserId;		// 检查人ID
	private String checkUserName;		// 外访人
	private String riskPoint;		// 异常风险点
	private String checkAddress;		// 外访地点
	private String description;		// 外访详情
	
	public String getAllocateId() {
		return allocateId;
	}

	public void setAllocateId(String allocateId) {
		this.allocateId = allocateId;
	}

	public CheckIndeed() {
		super();
	}

	public CheckIndeed(String id){
		super(id);
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	
	public String getCheckUserId() {
		return checkUserId;
	}

	public void setCheckUserId(String checkUserId) {
		this.checkUserId = checkUserId;
	}
	
	public String getCheckUserName() {
		return checkUserName;
	}

	public void setCheckUserName(String checkUserName) {
		this.checkUserName = checkUserName;
	}
	
	public String getRiskPoint() {
		return riskPoint;
	}

	public void setRiskPoint(String riskPoint) {
		this.riskPoint = riskPoint;
	}
	
	public String getCheckAddress() {
		return checkAddress;
	}

	public void setCheckAddress(String checkAddress) {
		this.checkAddress = checkAddress;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}