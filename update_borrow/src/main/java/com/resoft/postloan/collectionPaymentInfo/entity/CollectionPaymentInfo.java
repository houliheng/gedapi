package com.resoft.postloan.collectionPaymentInfo.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 客户催收回款详情Entity
 * @author yanwanmei
 * @version 2016-06-14
 */
public class CollectionPaymentInfo extends DataEntity<CollectionPaymentInfo> {
	
	private static final long serialVersionUID = 1L;
	private String contractNo;		// 合同编号
	private String collectionType;		// 催收环节（方式1电话2上门3外包4法务）
	private String paymentDate;		// 回款日期
	private String paymentAmount;		// 回款金额
	private String paymentType;		// 回款方式（1代扣2现金）
	private String collector;		// 催收人
	
	public CollectionPaymentInfo() {
		super();
	}

	public CollectionPaymentInfo(String id){
		super(id);
	}

	@Length(min=1, max=50, message="合同编号长度必须介于 1 和 50 之间")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	@Length(min=0, max=4, message="催收环节（方式1电话2上门3外包4法务）长度必须介于 0 和 4 之间")
	public String getCollectionType() {
		return collectionType;
	}

	public void setCollectionType(String collectionType) {
		this.collectionType = collectionType;
	}
	
	@Length(min=0, max=10, message="回款日期长度必须介于 0 和 10 之间")
	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	
	public String getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	
	@Length(min=0, max=4, message="回款方式（1代扣2现金）长度必须介于 0 和 4 之间")
	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	
	@Length(min=0, max=32, message="催收人长度必须介于 0 和 32 之间")
	public String getCollector() {
		return collector;
	}

	public void setCollector(String collector) {
		this.collector = collector;
	}
	
}