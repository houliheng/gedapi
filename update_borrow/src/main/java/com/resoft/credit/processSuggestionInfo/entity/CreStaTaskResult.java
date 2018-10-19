package com.resoft.credit.processSuggestionInfo.entity;


import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 流程节点记录表Entity
 * @author zhaohuakui
 * @version 2016-11-21
 */
public class CreStaTaskResult extends DataEntity<CreStaTaskResult> {
	
	private static final long serialVersionUID = 1L;
	private String taskDefKey;		// 流程ID
	private String applyNo;		// 申请编号
	private String orgId;		// 机构编号
	private Date  itemCreateDate;	// 进件时间,
	private Date  startTime;	// 进件开始时间,
	private Date  endTime;	// 进件结束时间,
	private String passFlag;//用于标识是否通过的状态
	private String officeId;		// 归属机构

	//非表字段，用于标识是保存还是提交
	private String sta;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public CreStaTaskResult() {
		super();
	}

	public CreStaTaskResult(String id){
		super(id);
	}

	
	public String getTaskDefKey() {
		return taskDefKey;
	}

	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}

	@Length(min=0, max=32, message="申请编号长度必须介于 0 和 32 之间")
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String object) {
		this.orgId = object;
	}

	public String getPassFlag() {
		return passFlag;
	}

	public void setPassFlag(String passFlag) {
		this.passFlag = passFlag;
	}

	public String getSta() {
		return sta;
	}

	public void setSta(String sta) {
		this.sta = sta;
	}

	public Date getItemCreateDate() {
		return itemCreateDate;
	}

	public void setItemCreateDate(Date itemCreateDate) {
		this.itemCreateDate = itemCreateDate;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}
	
}