/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.resoft.credit.contractarchive.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * 合同归档信息表Entity
 * @author lirongchao
 * @version 2016-01-20
 */
public class ContractArchive extends DataEntity<ContractArchive> {
	private static final long serialVersionUID = 1L;
	private String contractNo;		// 合同编号
	private String custName;		// 客户名称
	private String isSender;		// 是否发出
	private String isSenderName;		// 是否发出Name
	private String senderName;		// 发件人
	private Date senderTime;		// 发出时间
	private String isRecipitent;		// 是否签收
	private String isRecipitentName;		// 是否签收Name
	private String recipientName;		// 签收人
	private Date recipientTime;		// 签收时间
	private Office orgNum;		// 所属机构
	private String contractLocation;		// 合同资料存放位置
	private String isBorrowing;		// 是否被借出
	private String borrowingName;		// 借阅人
	private String borrowingReason;		// 借阅原因
	private Date borrowingTime;		// 借阅时间
	private Office registerOrg; // 登记机构(=合同表经办机构)
	private String expressCompany; //快递公司
	private String expressNo;   //快递单号
	//查询条件
	private Date startSenderTime ; //发出时间范围
	private Date endSenderTime ; //发出时间范围
	private Date startRecipientTime ; //发出时间范围
	private Date endRecipientTime ; //发出时间范围
	public ContractArchive() {
		super();
	}

	public ContractArchive(String id){
		super(id);
	}

	public Date getStartSenderTime() {
		return startSenderTime;
	}

	public String getIsSenderName() {
		return isSenderName;
	}

	public void setIsSenderName(String isSenderName) {
		this.isSenderName = isSenderName;
	}

	public String getIsRecipitentName() {
		return isRecipitentName;
	}

	public void setIsRecipitentName(String isRecipitentName) {
		this.isRecipitentName = isRecipitentName;
	}

	public void setStartSenderTime(Date startSenderTime) {
		this.startSenderTime = startSenderTime;
	}

	public Date getEndSenderTime() {
		return endSenderTime;
	}

	public void setEndSenderTime(Date endSenderTime) {
		this.endSenderTime = endSenderTime;
	}

	public Date getStartRecipientTime() {
		return startRecipientTime;
	}

	public void setStartRecipientTime(Date startRecipientTime) {
		this.startRecipientTime = startRecipientTime;
	}

	public Date getEndRecipientTime() {
		return endRecipientTime;
	}

	public void setEndRecipientTime(Date endRecipientTime) {
		this.endRecipientTime = endRecipientTime;
	}


	@Length(min=0, max=32, message="合同编号长度必须介于 0 和 32 之间")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Length(min=0, max=50, message="客户名称长度必须介于 0 和 50 之间")
	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	@Length(min=0, max=1, message="是否发出长度必须介于 0 和 1 之间")
	public String getIsSender() {
		return isSender;
	}

	public void setIsSender(String isSender) {
		this.isSender = isSender;
	}

	@Length(min=0, max=50, message="发件人长度必须介于 0 和 50 之间")
	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSenderTime() {
		return senderTime;
	}

	public void setSenderTime(Date senderTime) {
		this.senderTime = senderTime;
	}

	@Length(min=0, max=1, message="是否签收长度必须介于 0 和 1 之间")
	public String getIsRecipitent() {
		return isRecipitent;
	}

	public void setIsRecipitent(String isRecipitent) {
		this.isRecipitent = isRecipitent;
	}

	@Length(min=0, max=50, message="签收人长度必须介于 0 和 50 之间")
	public String getRecipientName() {
		return recipientName;
	}

	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRecipientTime() {
		return recipientTime;
	}

	public void setRecipientTime(Date recipientTime) {
		this.recipientTime = recipientTime;
	}

	@Length(min=0, max=50, message="所属机构长度必须介于 0 和 50 之间")
	public Office getOrgNum() {
		return orgNum;
	}

	public void setOrgNum(Office orgNum) {
		this.orgNum = orgNum;
	}

	@Length(min=0, max=1000, message="合同资料存放位置长度必须介于 0 和 1000 之间")
	public String getContractLocation() {
		return contractLocation;
	}

	public void setContractLocation(String contractLocation) {
		this.contractLocation = contractLocation;
	}

	@Length(min=0, max=1, message="是否被借出长度必须介于 0 和 1 之间")
	public String getIsBorrowing() {
		return isBorrowing;
	}

	public void setIsBorrowing(String isBorrowing) {
		this.isBorrowing = isBorrowing;
	}

	@Length(min=0, max=50, message="借阅人长度必须介于 0 和 50 之间")
	public String getBorrowingName() {
		return borrowingName;
	}

	public void setBorrowingName(String borrowingName) {
		this.borrowingName = borrowingName;
	}

	@Length(min=0, max=200, message="借阅原因长度必须介于 0 和 200 之间")
	public String getBorrowingReason() {
		return borrowingReason;
	}

	public void setBorrowingReason(String borrowingReason) {
		this.borrowingReason = borrowingReason;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBorrowingTime() {
		return borrowingTime;
	}

	public void setBorrowingTime(Date borrowingTime) {
		this.borrowingTime = borrowingTime;
	}

	public Office getRegisterOrg() {
		return registerOrg;
	}

	public void setRegisterOrg(Office registerOrg) {
		this.registerOrg = registerOrg;
	}

	public String getExpressCompany() {
		return expressCompany;
	}

	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}

	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}




}