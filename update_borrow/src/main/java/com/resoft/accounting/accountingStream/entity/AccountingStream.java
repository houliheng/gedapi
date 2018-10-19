package com.resoft.accounting.accountingStream.entity;

import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * 账务流水Entity
 * 
 * @author wangguodong
 * @version 2016-08-12
 */
public class AccountingStream extends DataEntity<AccountingStream> {

	private static final long serialVersionUID = 1L;
	private String custName; // 客户名称
	private String contractNo; // 合同编号
	private String periodNum; // 期数
	private String streamNo; // 流水号
	private String applyDeductAmount; // 申请划扣金额
	private String deductAmountResult; // 成功划扣金额
	private Office orgLevel4; // 登记门店
	private String streamType;// 流水类型
	private String deductType;// 划扣类型
	private Office orgLevel3; // 区域
	private Office orgLevel2; // 大区
	private String deductResult; // 划扣状态
	private String decuctTime; // 划扣时间
	private String approPeriodValue; // 合同期数
	private String repayCapitalAmount; // 还款本金 REPAY_CAPITAL_AMOUNT
	private String repayInterestAmount; // 还款利息
	private String repayServiceFee;// 服务费
	private String repayManagementFee;// 账户管理费
	private String repayPenaltyAmount;// 违约金
	private String repayFineAmount;// 罚息
	private String deductCustId;// 划扣人Id
	private String deductCustName;// 划扣人Name
	private Date streamTime;// 流水时间
	private String repayPeriodStatus;// 期状态
	private Date startTime;// 开始时间
	private Date endTime;// 结束时间
	private Date streamStartTime;// 流水开始时间
	private Date streamEndTime;// 流水结束时间
	private Office company;
	private String repayBreakAmount; // REPAY_BREAK_AMOUNT 提前还款费用

	public String getDeductCustName() {
		return deductCustName;
	}

	public void setDeductCustName(String deductCustName) {
		this.deductCustName = deductCustName;
	}


	public String getStreamType() {
		return streamType;
	}

	public void setStreamType(String streamType) {
		this.streamType = streamType;
	}

	public String getDeductType() {
		return deductType;
	}

	public void setDeductType(String deductType) {
		this.deductType = deductType;
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

	public String getPeriodNum() {
		return periodNum;
	}

	public void setPeriodNum(String periodNum) {
		this.periodNum = periodNum;
	}

	public String getStreamNo() {
		return streamNo;
	}

	public void setStreamNo(String streamNo) {
		this.streamNo = streamNo;
	}

	public String getApplyDeductAmount() {
		return applyDeductAmount;
	}

	public void setApplyDeductAmount(String applyDeductAmount) {
		this.applyDeductAmount = applyDeductAmount;
	}

	public String getDeductAmountResult() {
		return deductAmountResult;
	}

	public void setDeductAmountResult(String deductAmountResult) {
		this.deductAmountResult = deductAmountResult;
	}

	public Office getOrgLevel4() {
		return orgLevel4;
	}

	public void setOrgLevel4(Office orgLevel4) {
		this.orgLevel4 = orgLevel4;
	}

	public Office getOrgLevel3() {
		return orgLevel3;
	}

	public void setOrgLevel3(Office orgLevel3) {
		this.orgLevel3 = orgLevel3;
	}

	public Office getOrgLevel2() {
		return orgLevel2;
	}

	public void setOrgLevel2(Office orgLevel2) {
		this.orgLevel2 = orgLevel2;
	}

	public String getDeductResult() {
		return deductResult;
	}

	public void setDeductResult(String deductResult) {
		this.deductResult = deductResult;
	}

	public String getDecuctTime() {
		return decuctTime;
	}

	public void setDecuctTime(String decuctTime) {
		this.decuctTime = decuctTime;
	}

	public String getApproPeriodValue() {
		return approPeriodValue;
	}

	public void setApproPeriodValue(String approPeriodValue) {
		this.approPeriodValue = approPeriodValue;
	}

	public String getRepayCapitalAmount() {
		return repayCapitalAmount;
	}

	public void setRepayCapitalAmount(String repayCapitalAmount) {
		this.repayCapitalAmount = repayCapitalAmount;
	}

	public String getRepayInterestAmount() {
		return repayInterestAmount;
	}

	public void setRepayInterestAmount(String repayInterestAmount) {
		this.repayInterestAmount = repayInterestAmount;
	}

	public String getRepayServiceFee() {
		return repayServiceFee;
	}

	public void setRepayServiceFee(String repayServiceFee) {
		this.repayServiceFee = repayServiceFee;
	}

	public String getRepayManagementFee() {
		return repayManagementFee;
	}

	public void setRepayManagementFee(String repayManagementFee) {
		this.repayManagementFee = repayManagementFee;
	}

	public String getRepayPenaltyAmount() {
		return repayPenaltyAmount;
	}

	public void setRepayPenaltyAmount(String repayPenaltyAmount) {
		this.repayPenaltyAmount = repayPenaltyAmount;
	}

	public String getRepayFineAmount() {
		return repayFineAmount;
	}

	public void setRepayFineAmount(String repayFineAmount) {
		this.repayFineAmount = repayFineAmount;
	}

	public String getDeductCustId() {
		return deductCustId;
	}

	public void setDeductCustId(String deductCustId) {
		this.deductCustId = deductCustId;
	}

	public Date getStreamTime() {
		return streamTime;
	}

	public void setStreamTime(Date streamTime) {
		this.streamTime = streamTime;
	}

	public String getRepayPeriodStatus() {
		return repayPeriodStatus;
	}

	public void setRepayPeriodStatus(String repayPeriodStatus) {
		this.repayPeriodStatus = repayPeriodStatus;
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

	public Date getStreamStartTime() {
		return streamStartTime;
	}

	public void setStreamStartTime(Date streamStartTime) {
		this.streamStartTime = streamStartTime;
	}

	public Date getStreamEndTime() {
		return streamEndTime;
	}

	public void setStreamEndTime(Date streamEndTime) {
		this.streamEndTime = streamEndTime;
	}

	public Office getCompany() {
		return company;
	}

	public void setCompany(Office company) {
		this.company = company;
	}

	public String getRepayBreakAmount() {
		return repayBreakAmount;
	}

	public void setRepayBreakAmount(String repayBreakAmount) {
		this.repayBreakAmount = repayBreakAmount;
	}

}