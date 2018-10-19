package com.resoft.postloan.debtCollectionFace.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 上门催收Entity
 * 
 * @author wangguodong
 * @version 2016-06-02
 */
public class DebtCollectionFace extends DataEntity<DebtCollectionFace> {

	private static final long serialVersionUID = 1L;
	private String contractNo; // 合同编号
	private Date collectionDate; // 催收时间
	private String collector; // 催收人
	private String addressPro; // 上门催收地址省级代码
	private String addressCity; // 上门催收地址市级代码
	private String addressDistinct; // 上门催收地址区县级代码
	private String addressDetail; // 上门催收地址详细信息
	private String custType; // 催收对象类型
	private String custName; // 催收对象名字
	private String description; // 催收详情
	private String custResult; // 催收客户回款描述信息
	private String debtId;// 催收汇总id

	public String getDebtId() {
		return debtId;
	}

	public void setDebtId(String debtId) {
		this.debtId = debtId;
	}

	public DebtCollectionFace() {
		super();
	}

	public DebtCollectionFace(String id) {
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

	public String getCollector() {
		return collector;
	}

	public void setCollector(String collector) {
		this.collector = collector;
	}

	@Length(min = 0, max = 10, message = "上门催收地址省级代码长度必须介于 0 和 10 之间")
	public String getAddressPro() {
		return addressPro;
	}

	public void setAddressPro(String addressPro) {
		this.addressPro = addressPro;
	}

	@Length(min = 0, max = 10, message = "上门催收地址市级代码长度必须介于 0 和 10 之间")
	public String getAddressCity() {
		return addressCity;
	}

	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}

	@Length(min = 0, max = 10, message = "上门催收地址区县级代码长度必须介于 0 和 10 之间")
	public String getAddressDistinct() {
		return addressDistinct;
	}

	public void setAddressDistinct(String addressDistinct) {
		this.addressDistinct = addressDistinct;
	}

	@Length(min = 0, max = 300, message = "上门催收地址详细信息长度必须介于 0 和 300 之间")
	public String getAddressDetail() {
		return addressDetail;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}

	@Length(min = 0, max = 4, message = "催收对象类型长度必须介于 0 和 4 之间")
	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	@Length(min = 0, max = 30, message = "催收对象名字长度必须介于 0 和 30 之间")
	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	@Length(min = 0, max = 1000, message = "催收详情长度必须介于 0 和 1000 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Length(min = 0, max = 1000, message = "催收客户回款描述信息长度必须介于 0 和 1000 之间")
	public String getCustResult() {
		return custResult;
	}

	public void setCustResult(String custResult) {
		this.custResult = custResult;
	}

}