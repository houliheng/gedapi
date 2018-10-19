package com.resoft.postloan.debtCollectionPhone.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 电话催收Entity
 * 
 * @author wangguodong
 * @version 2016-06-02
 */
public class DebtCollectionPhone extends DataEntity<DebtCollectionPhone> {

	private static final long serialVersionUID = 1L;
	private String contractNo; // 合同编号
	private Date collectionDate; // 催收日期
	private String collector; // 催收人
	private String collectorName; // 催收人名称
	private String custType; // 客户 类型
	private String custName; // 客户名称
	private String riskLelve; // 风险级别
	private String callSource; // 电话来源
	private String callType; // 电话类型
	private String callNum; // 电话号码
	private String callTarget; // 拨打对象
	private String callResult; // 接听情况
	private String callReceiver; // 接听者身份
	private String riskAbnormal; // 异常风险点
	private String description; // 催收详情
	private String debtId;//催收汇总id
	
	public String getDebtId() {
		return debtId;
	}

	public void setDebtId(String debtId) {
		this.debtId = debtId;
	}

	public String getCollectorName() {
		return collectorName;
	}

	public void setCollectorName(String collectorName) {
		this.collectorName = collectorName;
	}

	public DebtCollectionPhone() {
		super();
	}

	public DebtCollectionPhone(String id) {
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

	@Length(min = 0, max = 32, message = "催收人长度必须介于 0 和 32 之间")
	public String getCollector() {
		return collector;
	}

	public void setCollector(String collector) {
		this.collector = collector;
	}

	@Length(min = 0, max = 4, message = "客户 类型长度必须介于 0 和 4 之间")
	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	@Length(min = 0, max = 20, message = "客户名称长度必须介于 0 和 20 之间")
	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	@Length(min = 0, max = 4, message = "风险级别长度必须介于 0 和 4 之间")
	public String getRiskLelve() {
		return riskLelve;
	}

	public void setRiskLelve(String riskLelve) {
		this.riskLelve = riskLelve;
	}

	@Length(min = 0, max = 4, message = "电话来源长度必须介于 0 和 4 之间")
	public String getCallSource() {
		return callSource;
	}

	public void setCallSource(String callSource) {
		this.callSource = callSource;
	}

	@Length(min = 0, max = 2, message = "电话类型长度必须介于 0 和 2 之间")
	public String getCallType() {
		return callType;
	}

	public void setCallType(String callType) {
		this.callType = callType;
	}

	@Length(min = 0, max = 15, message = "电话号码长度必须介于 0 和 15 之间")
	public String getCallNum() {
		return callNum;
	}

	public void setCallNum(String callNum) {
		this.callNum = callNum;
	}

	@Length(min = 0, max = 4, message = "拨打对象长度必须介于 0 和 4 之间")
	public String getCallTarget() {
		return callTarget;
	}

	public void setCallTarget(String callTarget) {
		this.callTarget = callTarget;
	}

	@Length(min = 0, max = 2, message = "接听情况长度必须介于 0 和 2 之间")
	public String getCallResult() {
		return callResult;
	}

	public void setCallResult(String callResult) {
		this.callResult = callResult;
	}

	@Length(min = 0, max = 2, message = "接听者身份长度必须介于 0 和 2 之间")
	public String getCallReceiver() {
		return callReceiver;
	}

	public void setCallReceiver(String callReceiver) {
		this.callReceiver = callReceiver;
	}

	@Length(min = 0, max = 300, message = "异常风险点长度必须介于 0 和 300 之间")
	public String getRiskAbnormal() {
		return riskAbnormal;
	}

	public void setRiskAbnormal(String riskAbnormal) {
		this.riskAbnormal = riskAbnormal;
	}

	@Length(min = 0, max = 1000, message = "催收详情长度必须介于 0 和 1000 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}