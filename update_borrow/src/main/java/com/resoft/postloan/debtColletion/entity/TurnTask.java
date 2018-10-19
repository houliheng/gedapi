package com.resoft.postloan.debtColletion.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 合同转办统计Entity
 * 
 * @author wangguodong
 * @version 2016-06-02
 */
public class TurnTask extends DataEntity<TurnTask> {

	private static final long serialVersionUID = 1L;
	private String debtId;
	private String contractNo;
	private String turnDate;
	private String turnPerson;
	private String currCollectionFrom;// 转办前来源
	private String turnBefore;
	private String turnAfter;
	private String turnStatus;
	private String dataDt; // 催收日期
	private String custName; // 客户名称
	private String riskLelve; // 风险级别
	private String collectionTimes; // 催收次数
	private String taOverdueTimes; // 累计逾期期数
	private String contractAmount; // 合同金额
	private String currOverdueAmount; // 当前合同逾期金额
	private String operateOrgId; // 登机门店Id
	private String operateOrgName; // 登机门店
	private String currCollectionType; // 截至目前进行的催收方式（1未执行过催收2电话3上门4外包5法务）
	private String newCollectionType; // 截至目前进行的催收方式（1未执行过催收2电话3上门4外包5法务）
	private String debtCurrCollectionFrom; // 截至目前的催收任务待分配来源（1逾期自动2电话转办3上门转办4外包转办5
											// null）
	private String currCollectionStatus; // 截至目前的催收任务状态（1待分配2已分配待催收3转办待审核）
	private String currCollector; // 截至目前的催收环节的催收人
	private String suggestion; // 意见

	public String getDebtId() {
		return debtId;
	}

	public void setDebtId(String debtId) {
		this.debtId = debtId;
	}

	public String getOperateOrgId() {
		return operateOrgId;
	}

	public void setOperateOrgId(String operateOrgId) {
		this.operateOrgId = operateOrgId;
	}

	public String getDataDt() {
		return dataDt;
	}

	public void setDataDt(String dataDt) {
		this.dataDt = dataDt;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getRiskLelve() {
		return riskLelve;
	}

	public void setRiskLelve(String riskLelve) {
		this.riskLelve = riskLelve;
	}

	public String getCollectionTimes() {
		return collectionTimes;
	}

	public void setCollectionTimes(String collectionTimes) {
		this.collectionTimes = collectionTimes;
	}

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

	public String getOperateOrgName() {
		return operateOrgName;
	}

	public void setOperateOrgName(String operateOrgName) {
		this.operateOrgName = operateOrgName;
	}

	public String getCurrCollectionType() {
		return currCollectionType;
	}

	public void setCurrCollectionType(String currCollectionType) {
		this.currCollectionType = currCollectionType;
	}

	public String getNewCollectionType() {
		return newCollectionType;
	}

	public void setNewCollectionType(String newCollectionType) {
		this.newCollectionType = newCollectionType;
	}

	public String getDebtCurrCollectionFrom() {
		return debtCurrCollectionFrom;
	}

	public void setDebtCurrCollectionFrom(String debtCurrCollectionFrom) {
		this.debtCurrCollectionFrom = debtCurrCollectionFrom;
	}

	public String getCurrCollectionStatus() {
		return currCollectionStatus;
	}

	public void setCurrCollectionStatus(String currCollectionStatus) {
		this.currCollectionStatus = currCollectionStatus;
	}

	public String getCurrCollector() {
		return currCollector;
	}

	public void setCurrCollector(String currCollector) {
		this.currCollector = currCollector;
	}

	public String getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	public String getTurnStatus() {
		return turnStatus;
	}

	public void setTurnStatus(String turnStatus) {
		this.turnStatus = turnStatus;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getTurnDate() {
		return turnDate;
	}

	public void setTurnDate(String turnDate) {
		this.turnDate = turnDate;
	}

	public String getTurnPerson() {
		return turnPerson;
	}

	public void setTurnPerson(String turnPerson) {
		this.turnPerson = turnPerson;
	}

	public String getCurrCollectionFrom() {
		return currCollectionFrom;
	}

	public void setCurrCollectionFrom(String currCollectionFrom) {
		this.currCollectionFrom = currCollectionFrom;
	}

	public String getTurnBefore() {
		return turnBefore;
	}

	public void setTurnBefore(String turnBefore) {
		this.turnBefore = turnBefore;
	}

	public String getTurnAfter() {
		return turnAfter;
	}

	public void setTurnAfter(String turnAfter) {
		this.turnAfter = turnAfter;
	}

}