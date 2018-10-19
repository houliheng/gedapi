package com.resoft.postloan.debtCollectionLegal.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 法务催收Entity
 * 
 * @author wangguodong
 * @version 2016-06-02
 */
public class DebtCollectionLegal extends DataEntity<DebtCollectionLegal> {

	private static final long serialVersionUID = 1L;
	private String id;
	private String contractNo; // 合同编号
	private Date collectionDate; // 催收时间（立案时间）
	private String collector; // 催收人（立案人）
	private String legalType; // 立案类型
	private String legalOrg; // 立案机构
	private String legalStatus; // 法务处理状态
	private String legalResult; // 催收结果
	private String register; // 登记人
	private Date registrationTime; // 登记时间
	private String description; // 法务催收处理详情
	private String collectorName; // 催收人姓名
	private String debtId;// 催收汇总id

	public String getDebtId() {
		return debtId;
	}

	public void setDebtId(String debtId) {
		this.debtId = debtId;
	}

	public DebtCollectionLegal() {
		super();
	}

	public DebtCollectionLegal(String id) {
		super(id);
	}

	@Length(min = 0, max = 50, message = "合同编号长度必须介于 0 和 50 之间")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Date getCollectionDate() {
		return collectionDate;
	}

	public void setCollectionDate(Date collectionDate) {
		this.collectionDate = collectionDate;
	}

	public String getCollector() {
		return collector;
	}

	public void setCollector(String collector) {
		this.collector = collector;
	}

	@Length(min = 0, max = 2, message = "立案类型长度必须介于 0 和 2 之间")
	public String getLegalType() {
		return legalType;
	}

	public void setLegalType(String legalType) {
		this.legalType = legalType;
	}

	@Length(min = 0, max = 4, message = "立案机构长度必须介于 0 和 4 之间")
	public String getLegalOrg() {
		return legalOrg;
	}

	public void setLegalOrg(String legalOrg) {
		this.legalOrg = legalOrg;
	}

	@Length(min = 0, max = 4, message = "法务处理状态长度必须介于 0 和 4 之间")
	public String getLegalStatus() {
		return legalStatus;
	}

	public void setLegalStatus(String legalStatus) {
		this.legalStatus = legalStatus;
	}

	@Length(min = 0, max = 4, message = "催收结果长度必须介于 0 和 4 之间")
	public String getLegalResult() {
		return legalResult;
	}

	public void setLegalResult(String legalResult) {
		this.legalResult = legalResult;
	}

	@Length(min = 0, max = 30, message = "登记人长度必须介于 0 和 30 之间")
	public String getRegister() {
		return register;
	}

	public void setRegister(String register) {
		this.register = register;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRegistrationTime() {
		return registrationTime;
	}

	public void setRegistrationTime(Date registrationTime) {
		this.registrationTime = registrationTime;
	}

	@Length(min = 0, max = 1000, message = "法务催收处理详情长度必须介于 0 和 1000 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCollectorName() {
		return collectorName;
	}

	public void setCollectorName(String collectorName) {
		this.collectorName = collectorName;
	}

}