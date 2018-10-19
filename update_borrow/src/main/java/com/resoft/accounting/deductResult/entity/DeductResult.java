package com.resoft.accounting.deductResult.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 补录流水Entity
 * 
 * @author wuxi01
 * @version 2016-03-04
 */
public class DeductResult extends DataEntity<DeductResult> {

	private static final long serialVersionUID = 1L;
	private String contractNo; // 合同编号
	private String streamNo; // 流水号
	private Date streamTime; // 流水时间
	private String deductAmount; // 扣款金额
	private String deductUserId; // 操作人员Id
	private String deductUserName; // 操作人员Name
	private String isLock; // 是否锁定（字典类型：yes_no）
	private Date entryTime; // 入账时间
	private String description; // 备注

	public DeductResult() {
		super();
	}

	public DeductResult(String id) {
		super(id);
	}

	public String getDeductUserName() {
		return deductUserName;
	}

	public void setDeductUserName(String deductUserName) {
		this.deductUserName = deductUserName;
	}

	@Length(min = 0, max = 32, message = "流水号长度必须介于 0 和 32 之间")
	public String getStreamNo() {
		return streamNo;
	}

	public void setStreamNo(String streamNo) {
		this.streamNo = streamNo;
	}

	@Length(min = 0, max = 50, message = "合同编号长度必须介于 0 和 50 之间")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getDeductAmount() {
		return deductAmount;
	}

	public void setDeductAmount(String deductAmount) {
		this.deductAmount = deductAmount;
	}

	@Length(min = 0, max = 10, message = "是否锁定（字典类型：yes_no）长度必须介于 0 和 10 之间")
	public String getIsLock() {
		return isLock;
	}

	public void setIsLock(String isLock) {
		this.isLock = isLock;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStreamTime() {
		return streamTime;
	}

	public void setStreamTime(Date streamTime) {
		this.streamTime = streamTime;
	}

	@Length(min = 0, max = 32, message = "操作人员代码长度必须介于 0 和 32 之间")
	public String getDeductUserId() {
		return deductUserId;
	}

	public void setDeductUserId(String deductUserId) {
		this.deductUserId = deductUserId;
	}

	@Length(min = 0, max = 1000, message = "备注长度必须介于 0 和 1000 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}