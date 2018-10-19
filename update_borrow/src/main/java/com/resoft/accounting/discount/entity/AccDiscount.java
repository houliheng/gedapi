package com.resoft.accounting.discount.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 贴息表Entity
 * @author gsh
 * @version 2018-05-18
 */
public class AccDiscount extends DataEntity<AccDiscount> {
	
	private static final long serialVersionUID = 1L;
	private String contractNo;		// 合同编号
	private String periodNum;		// 期数
	private String discountFee;		// 贴息金额
	private String factDiscountFee;		// 实收贴息金额
	private Date discountDate;		// 贴息日期
	private String operateName;		// 操作人
	private String operateOrgName;		// 操作机构名称
	private String operateId;		// 操作人id
	private String operateOrgId;		// 操作机构id
	private String discountStatus;
	private String discountAccount;
	private String remarks;//备注
	private String discountPerson;//贴息人
	public AccDiscount() {
		super();
	}

	public AccDiscount(String id){
		super(id);
	}

	@Length(min=0, max=50, message="合同编号长度必须介于 0 和 50 之间")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	@Length(min=0, max=10, message="期数长度必须介于 0 和 10 之间")
	public String getPeriodNum() {
		return periodNum;
	}

	public void setPeriodNum(String periodNum) {
		this.periodNum = periodNum;
	}
	
	public String getDiscountFee() {
		return discountFee;
	}

	public void setDiscountFee(String discountFee) {
		this.discountFee = discountFee;
	}
	
	public String getFactDiscountFee() {
		return factDiscountFee;
	}

	public void setFactDiscountFee(String factDiscountFee) {
		this.factDiscountFee = factDiscountFee;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDiscountDate() {
		return discountDate;
	}

	public void setDiscountDate(Date discountDate) {
		this.discountDate = discountDate;
	}
	
	@Length(min=0, max=20, message="操作人长度必须介于 0 和 20 之间")
	public String getOperateName() {
		return operateName;
	}

	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}
	
	@Length(min=0, max=300, message="操作机构名称长度必须介于 0 和 300 之间")
	public String getOperateOrgName() {
		return operateOrgName;
	}

	public void setOperateOrgName(String operateOrgName) {
		this.operateOrgName = operateOrgName;
	}
	
	@Length(min=0, max=32, message="操作人id长度必须介于 0 和 32 之间")
	public String getOperateId() {
		return operateId;
	}

	public void setOperateId(String operateId) {
		this.operateId = operateId;
	}
	
	@Length(min=0, max=32, message="操作机构id长度必须介于 0 和 32 之间")
	public String getOperateOrgId() {
		return operateOrgId;
	}

	public void setOperateOrgId(String operateOrgId) {
		this.operateOrgId = operateOrgId;
	}

	public String getDiscountStatus() {
		return discountStatus;
	}

	public void setDiscountStatus(String discountStatus) {
		this.discountStatus = discountStatus;
	}

	public String getDiscountAccount() {
		return discountAccount;
	}

	public void setDiscountAccount(String discountAccount) {
		this.discountAccount = discountAccount;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDiscountPerson() {
		return discountPerson;
	}

	public void setDiscountPerson(String discountPerson) {
		this.discountPerson = discountPerson;
	}
	
	
	
}