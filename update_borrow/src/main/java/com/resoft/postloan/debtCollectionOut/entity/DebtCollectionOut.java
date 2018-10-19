package com.resoft.postloan.debtCollectionOut.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 外包催收Entity
 * 
 * @author wangguodong
 * @version 2016-06-02
 */
public class DebtCollectionOut extends DataEntity<DebtCollectionOut> {

	private static final long serialVersionUID = 1L;
	private String contractNo; // 合同编号
	private Date collectionDate; // 催收时间
	private String collector; // 催收人
	private String description; // 催收详情
	private String custResult; // 客户回款描述
	private String debtId;// 催收汇总id

	public String getDebtId() {
		return debtId;
	}

	public void setDebtId(String debtId) {
		this.debtId = debtId;
	}

	public DebtCollectionOut() {
		super();
	}

	public DebtCollectionOut(String id) {
		super(id);
	}

	@Length(min = 1, max = 50, message = "合同编号长度必须介于 1 和 50 之间")
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

	@Length(min = 0, max = 32, message = "催收人长度必须介于 0 和 32 之间")
	public String getCollector() {
		return collector;
	}

	public void setCollector(String collector) {
		this.collector = collector;
	}

	@Length(min = 0, max = 1000, message = "催收详情长度必须介于 0 和 1000 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Length(min = 0, max = 1000, message = "客户回款描述长度必须介于 0 和 1000 之间")
	public String getCustResult() {
		return custResult;
	}

	public void setCustResult(String custResult) {
		this.custResult = custResult;
	}

}