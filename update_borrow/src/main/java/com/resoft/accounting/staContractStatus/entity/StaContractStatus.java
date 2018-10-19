package com.resoft.accounting.staContractStatus.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * 合同还款明细查询Entity
 * @author yanwanmei
 * @version 2016-03-03
 */
public class StaContractStatus extends DataEntity<StaContractStatus> {
	
	private static final long serialVersionUID = 1L;
	private String dataDt;		// 数据日期
	private Office orgLevel2;		// 登记门店
	private Office orgLevel3;		// 区域
	private Office orgLevel4;		// 大区
	private String contractNo;		// 合同编号
	private String custName;		// 客户名称
	private Date deductDate;		// 还款日期（计划扣款日）
	private Date loanDate;		// 放款日期
	private String repayContractStatus;		// 还款状态
	private String periodValue;		// 合同期数
	private String contractAmount;		// 合同金额
	private String taTimes;		// 累计逾期期数
	private String currOverdueAmount;		// 当前逾期金额
	private String payTimes;		// 已还清的期数（保留）
	private String currOverdueCapitalAmount;		// 其中逾期本金（保留）
	private String currOverdueInterestAmount;		// 其中逾期利息（保留）
	private String overdueFee;		// 罚息总额（保留）
	private String repayFineAmount;		// 已还罚息（保留）
	private String fineExemptAmountSum;		// 罚息豁免总额（保留）
	private Date startTime; //做查询用  起始时间
	private Date endTime;  //做查询用  结束时间
	private Office company;
	private String companyName;
	private String approveProductId;
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Office getCompany() {
		return company;
	}

	public void setCompany(Office company) {
		this.company = company;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public StaContractStatus() {
		super();
	}

	public StaContractStatus(String id){
		super(id);
	}

	@Length(min=0, max=10, message="数据日期长度必须介于 0 和 10 之间")
	public String getDataDt() {
		return dataDt;
	}

	public void setDataDt(String dataDt) {
		this.dataDt = dataDt;
	}
	
	@Length(min=0, max=32, message="登记门店长度必须介于 0 和 32 之间")
	public Office getOrgLevel2() {
		return orgLevel2;
	}

	public void setOrgLevel2(Office orgLevel2) {
		this.orgLevel2 = orgLevel2;
	}
	
	@Length(min=0, max=32, message="区域长度必须介于 0 和 32 之间")
	public Office getOrgLevel3() {
		return orgLevel3;
	}

	public void setOrgLevel3(Office orgLevel3) {
		this.orgLevel3 = orgLevel3;
	}
	
	@Length(min=0, max=32, message="大区长度必须介于 0 和 32 之间")
	public Office getOrgLevel4() {
		return orgLevel4;
	}

	public void setOrgLevel4(Office orgLevel4) {
		this.orgLevel4 = orgLevel4;
	}
	
	@Length(min=0, max=50, message="合同编号长度必须介于 0 和 50 之间")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	@Length(min=0, max=20, message="客户名称长度必须介于 0 和 20 之间")
	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDeductDate() {
		return deductDate;
	}

	public void setDeductDate(Date deductDate) {
		this.deductDate = deductDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
	}
	
	@Length(min=0, max=4, message="还款状态长度必须介于 0 和 4 之间")
	public String getRepayContractStatus() {
		return repayContractStatus;
	}

	public void setRepayContractStatus(String repayContractStatus) {
		this.repayContractStatus = repayContractStatus;
	}
	
	@Length(min=0, max=10, message="合同期数长度必须介于 0 和 10 之间")
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
	
	@Length(min=0, max=10, message="累计逾期期数长度必须介于 0 和 10 之间")
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
	
	@Length(min=0, max=10, message="已还清的期数（保留）长度必须介于 0 和 10 之间")
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

	public String getApproveProductId() {
		return approveProductId;
	}

	public void setApproveProductId(String approveProductId) {
		this.approveProductId = approveProductId;
	}
	
	
	
}