package com.resoft.postloan.debtColletion.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 合同待催收Entity
 * @author wangguodong
 * @version 2016-06-02
 */
public class DoneDebtCollection extends DataEntity<DoneDebtCollection> {
	
	private static final long serialVersionUID = 1L;
	private String contractNo;		// 合同编号
	private String dataDt;		// 催收日期
	private String custName;		// 客户名称
	private String riskLelve;		// 风险级别
	private String collectionTimes;		// 催收次数
	private String taOverdueTimes;		// 累计逾期期数
	private String contractAmount;		// 合同金额
	private String currOverdueAmount;		// 当前合同逾期金额
	private String operateOrgId;		// 登机门店Id
	private String operateOrgName;		// 登机门店
	private String currCollectionType;		// 截至目前进行的催收方式（1未执行过催收2电话3上门4外包5法务）
	private String newCollectionType;		// 截至目前进行的催收方式（1未执行过催收2电话3上门4外包5法务）
	private String currCollectionFrom;		// 截至目前的催收任务待分配来源（1逾期自动2电话转办3上门转办4外包转办5 null）
	private String currCollectionStatus;		// 截至目前的催收任务状态（1待分配2已分配待催收3转办待审核）
	private String currCollector;		// 截至目前的催收环节的催收人
	private String suggestion; //意见
	private String collectBy;//分配人
	
	public String getCollectBy() {
		return collectBy;
	}

	public void setCollectBy(String collectBy) {
		this.collectBy = collectBy;
	}

	public String getOperateOrgId() {
		return operateOrgId;
	}

	public void setOperateOrgId(String operateOrgId) {
		this.operateOrgId = operateOrgId;
	}

	public String getNewCollectionType() {
		return newCollectionType;
	}

	public void setNewCollectionType(String newCollectionType) {
		this.newCollectionType = newCollectionType;
	}

	public String getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	public DoneDebtCollection() {
		super();
	}

	public DoneDebtCollection(String id){
		super(id);
	}

	@Length(min=1, max=50, message="合同编号长度必须介于 1 和 50 之间")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	@Length(min=0, max=10, message="催收日期长度必须介于 0 和 10 之间")
	public String getDataDt() {
		return dataDt;
	}

	public void setDataDt(String dataDt) {
		this.dataDt = dataDt;
	}
	
	@Length(min=0, max=20, message="客户名称长度必须介于 0 和 20 之间")
	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}
	
	@Length(min=0, max=4, message="风险级别长度必须介于 0 和 4 之间")
	public String getRiskLelve() {
		return riskLelve;
	}

	public void setRiskLelve(String riskLelve) {
		this.riskLelve = riskLelve;
	}
	
	@Length(min=0, max=10, message="催收次数长度必须介于 0 和 10 之间")
	public String getCollectionTimes() {
		return collectionTimes;
	}

	public void setCollectionTimes(String collectionTimes) {
		this.collectionTimes = collectionTimes;
	}
	
	@Length(min=0, max=2, message="累计逾期期数长度必须介于 0 和 2 之间")
	public String getTaOverdueTimes() {
		return taOverdueTimes;
	}

	public void setTaOverdueTimes(String taOverdueTimes) {
		this.taOverdueTimes = taOverdueTimes;
	}
	
	public String getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(String contractAmount) {
		this.contractAmount = contractAmount;
	}
	
	public String getCurrOverdueAmount() {
		return currOverdueAmount;
	}

	public void setCurrOverdueAmount(String currOverdueAmount) {
		this.currOverdueAmount = currOverdueAmount;
	}
	
	@Length(min=0, max=300, message="登机门店长度必须介于 0 和 300 之间")
	public String getOperateOrgName() {
		return operateOrgName;
	}

	public void setOperateOrgName(String operateOrgName) {
		this.operateOrgName = operateOrgName;
	}
	
	@Length(min=0, max=4, message="截至目前进行的催收方式（1未执行过催收2电话3上门4外包5法务）长度必须介于 0 和 4 之间")
	public String getCurrCollectionType() {
		return currCollectionType;
	}

	public void setCurrCollectionType(String currCollectionType) {
		this.currCollectionType = currCollectionType;
	}
	
	@Length(min=0, max=4, message="截至目前的催收任务待分配来源（1逾期自动2电话转办3上门转办4外包转办5 null）长度必须介于 0 和 4 之间")
	public String getCurrCollectionFrom() {
		return currCollectionFrom;
	}

	public void setCurrCollectionFrom(String currCollectionFrom) {
		this.currCollectionFrom = currCollectionFrom;
	}
	
	@Length(min=0, max=4, message="截至目前的催收任务状态（1待分配2已分配待催收3转办待审核）长度必须介于 0 和 4 之间")
	public String getCurrCollectionStatus() {
		return currCollectionStatus;
	}

	public void setCurrCollectionStatus(String currCollectionStatus) {
		this.currCollectionStatus = currCollectionStatus;
	}
	
	@Length(min=0, max=30, message="截至目前的催收环节的催收人长度必须介于 0 和 30 之间")
	public String getCurrCollector() {
		return currCollector;
	}

	public void setCurrCollector(String currCollector) {
		this.currCollector = currCollector;
	}
	
}