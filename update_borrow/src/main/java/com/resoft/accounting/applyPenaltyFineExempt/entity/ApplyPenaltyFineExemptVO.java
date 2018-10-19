package com.resoft.accounting.applyPenaltyFineExempt.entity;

import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * 违约金罚息减免VOEntity
 * @author yanwanmei
 * @version 2016-03-03
 */
public class ApplyPenaltyFineExemptVO extends DataEntity<ApplyPenaltyFineExemptVO> {
	
	private static final long serialVersionUID = 1L;
	private String periodNum; //期数
	private String custName; //客户名称 
	private String contractNo;// 合同编号
	private Office orgLevel2;// 登记大区
	private Office orgLevel3;// 区域
	private Office orgLevel4;// 门店
	private String approPeriodValue;// 合同期限
	private String repayPeriodStatus;//期状态
	private String deductDate; //应还款日期
	private String repayAmount; //应还金额
	private String overdueDays;//逾期天数
	private String penaltyAmount; //应还违约金 
	private String fineAmount;//应还罚息
	private String factPenaltyAmount;//实还违约金
	private String factFineAmount;//实还罚息
	private String penaltyExemptAmount;//违约金减免
	private String fineExemptAmount;//罚息减免
	private Date startTime;//开始时间
	private Date endTime;// 结束时间
	private Date penaltyStartTime;//减免开始时间
	private Date penaltyEndTime;// 减免结束时间
	private Office company;
	private String contractAmount;//合同金额
	
	public Date getPenaltyStartTime() {
		return penaltyStartTime;
	}
	public void setPenaltyStartTime(Date penaltyStartTime) {
		this.penaltyStartTime = penaltyStartTime;
	}
	public Date getPenaltyEndTime() {
		return penaltyEndTime;
	}
	public void setPenaltyEndTime(Date penaltyEndTime) {
		this.penaltyEndTime = penaltyEndTime;
	}
	public String getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(String contractAmount) {
		this.contractAmount = contractAmount;
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
	public String getPeriodNum() {
		return periodNum;
	}
	public void setPeriodNum(String periodNum) {
		this.periodNum = periodNum;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public Office getOrgLevel2() {
		return orgLevel2;
	}
	public void setOrgLevel2(Office orgLevel2) {
		this.orgLevel2 = orgLevel2;
	}
	public Office getOrgLevel3() {
		return orgLevel3;
	}
	public void setOrgLevel3(Office orgLevel3) {
		this.orgLevel3 = orgLevel3;
	}
	public Office getOrgLevel4() {
		return orgLevel4;
	}
	public void setOrgLevel4(Office orgLevel4) {
		this.orgLevel4 = orgLevel4;
	}
	public String getApproPeriodValue() {
		return approPeriodValue;
	}
	public void setApproPeriodValue(String approPeriodValue) {
		this.approPeriodValue = approPeriodValue;
	}
	public String getRepayPeriodStatus() {
		return repayPeriodStatus;
	}
	public void setRepayPeriodStatus(String repayPeriodStatus) {
		this.repayPeriodStatus = repayPeriodStatus;
	}
	public String getDeductDate() {
		return deductDate;
	}
	public void setDeductDate(String deductDate) {
		this.deductDate = deductDate;
	}
	public String getRepayAmount() {
		return repayAmount;
	}
	public void setRepayAmount(String repayAmount) {
		this.repayAmount = repayAmount;
	}
	public String getOverdueDays() {
		return overdueDays;
	}
	public void setOverdueDays(String overdueDays) {
		this.overdueDays = overdueDays;
	}
	public String getPenaltyAmount() {
		return penaltyAmount;
	}
	public void setPenaltyAmount(String penaltyAmount) {
		this.penaltyAmount = penaltyAmount;
	}
	public String getFineAmount() {
		return fineAmount;
	}
	public void setFineAmount(String fineAmount) {
		this.fineAmount = fineAmount;
	}
	public String getFactPenaltyAmount() {
		return factPenaltyAmount;
	}
	public void setFactPenaltyAmount(String factPenaltyAmount) {
		this.factPenaltyAmount = factPenaltyAmount;
	}
	public String getFactFineAmount() {
		return factFineAmount;
	}
	public void setFactFineAmount(String factFineAmount) {
		this.factFineAmount = factFineAmount;
	}
	public String getPenaltyExemptAmount() {
		return penaltyExemptAmount;
	}
	public void setPenaltyExemptAmount(String penaltyExemptAmount) {
		this.penaltyExemptAmount = penaltyExemptAmount;
	}
	public String getFineExemptAmount() {
		return fineExemptAmount;
	}
	public void setFineExemptAmount(String fineExemptAmount) {
		this.fineExemptAmount = fineExemptAmount;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}