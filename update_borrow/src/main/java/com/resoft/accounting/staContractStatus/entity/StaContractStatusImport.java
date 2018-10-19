package com.resoft.accounting.staContractStatus.entity;


/**
 * 合同还款明细查询Entity
 * 
 * @author yanwanmei
 * @version 2016-03-03
 */
public class StaContractStatusImport  {

	
	
	
	private String dataDt; // 数据日期
	private String orgLevel2; // 登记门店
	private String orgLevel3; // 区域
	private String orgLevel4; // 大区
	private String contractNo; // 合同编号
	private String custName; // 客户名称
	private String deductDate; // 还款日期（计划扣款日）
	private String loanDate; // 放款日期
	private String repayContractStatus; // 还款状态
	private String periodValue; // 合同期数
	private String contractAmount; // 合同金额
	private String taTimes; // 累计逾期期数
	private String currOverdueAmount; // 当前逾期金额
	private String settleDate; // SETTLE_DATE结清日期
	private String payTimes; // 已还清的期数（保留）
	private String currOverdueCapitalAmount; // 其中逾期本金（保留）
	private String currOverdueInterestAmount; // 其中逾期利息（保留）
	private String overdueFee; // 罚息总额（保留）
	private String repayFineAmount; // 已还罚息（保留）
	private String fineExemptAmountSum; // 罚息豁免总额（保留）

	
	public String getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}

	public String getDataDt() {
		return dataDt;
	}

	public void setDataDt(String dataDt) {
		this.dataDt = dataDt;
	}

	public String getOrgLevel2() {
		return orgLevel2;
	}

	public void setOrgLevel2(String orgLevel2) {
		this.orgLevel2 = orgLevel2;
	}

	public String getOrgLevel3() {
		return orgLevel3;
	}

	public void setOrgLevel3(String orgLevel3) {
		this.orgLevel3 = orgLevel3;
	}

	public String getOrgLevel4() {
		return orgLevel4;
	}

	public void setOrgLevel4(String orgLevel4) {
		this.orgLevel4 = orgLevel4;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getDeductDate() {
		return deductDate;
	}

	public void setDeductDate(String deductDate) {
		this.deductDate = deductDate;
	}

	public String getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(String loanDate) {
		this.loanDate = loanDate;
	}

	public String getRepayContractStatus() {
		return repayContractStatus;
	}

	public void setRepayContractStatus(String repayContractStatus) {
		this.repayContractStatus = repayContractStatus;
	}

	public String getPeriodValue() {
		return periodValue;
	}

	public void setPeriodValue(String periodValue) {
		this.periodValue = periodValue;
	}

	public String getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(String contractAmount) {
		this.contractAmount = contractAmount;
	}

	public String getTaTimes() {
		return taTimes;
	}

	public void setTaTimes(String taTimes) {
		this.taTimes = taTimes;
	}

	public String getCurrOverdueAmount() {
		return currOverdueAmount;
	}

	public void setCurrOverdueAmount(String currOverdueAmount) {
		this.currOverdueAmount = currOverdueAmount;
	}

	public String getPayTimes() {
		return payTimes;
	}

	public void setPayTimes(String payTimes) {
		this.payTimes = payTimes;
	}

	public String getCurrOverdueCapitalAmount() {
		return currOverdueCapitalAmount;
	}

	public void setCurrOverdueCapitalAmount(String currOverdueCapitalAmount) {
		this.currOverdueCapitalAmount = currOverdueCapitalAmount;
	}

	public String getCurrOverdueInterestAmount() {
		return currOverdueInterestAmount;
	}

	public void setCurrOverdueInterestAmount(String currOverdueInterestAmount) {
		this.currOverdueInterestAmount = currOverdueInterestAmount;
	}

	public String getOverdueFee() {
		return overdueFee;
	}

	public void setOverdueFee(String overdueFee) {
		this.overdueFee = overdueFee;
	}

	public String getRepayFineAmount() {
		return repayFineAmount;
	}

	public void setRepayFineAmount(String repayFineAmount) {
		this.repayFineAmount = repayFineAmount;
	}

	public String getFineExemptAmountSum() {
		return fineExemptAmountSum;
	}

	public void setFineExemptAmountSum(String fineExemptAmountSum) {
		this.fineExemptAmountSum = fineExemptAmountSum;
	}

}