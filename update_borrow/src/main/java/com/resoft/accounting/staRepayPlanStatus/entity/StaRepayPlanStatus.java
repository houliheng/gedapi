package com.resoft.accounting.staRepayPlanStatus.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * 账务调整Entity
 * 
 * @author yanwanmei
 * @version 2016-03-03
 */
public class StaRepayPlanStatus extends DataEntity<StaRepayPlanStatus> {

	private static final long serialVersionUID = 1L;
	private String dataDt; // 还款日期
	private Office orgLevel2; // 2级公司
	private Office orgLevel3; // 3级公司
	private Office orgLevel4; // 4级公司
	private String contractNo; // 合同编号
	private String periodNum; // 期数
	private String repayPeriodStatus; // 每期还款状态
	private String factServiceFee; // 服务费
	private String factMangementFee; // 账户管理费
	private String factCapitalAmount; // 本金
	private String factInterestAmount; // 利息
	private String factPenaltyAmount; // 违约金
	private String factFineAmount; // 罚息
	private Date capitalInterestRepayDate;// 当期本息结清日期
	private Date allRepayDate;// 当期总结清日期
	private String factBreakAmount;// 提前还款费用
	private String factAddAmount;// 营业外收入金额项
	private String penaltyExemptAmount; // 违约金减免 PENALTY_EXEMPT_AMOUNT
	private String fineEepmtAmount; // 罚息减免 FINE_EXEMPT_AMOUNT
	private String backAmount;//退回金额
	private String factRepayAmount;
	public String getBackAmount() {
		return backAmount;
	}

	public void setBackAmount(String backAmount) {
		this.backAmount = backAmount;
	}

	public String getPenaltyExemptAmount() {
		return penaltyExemptAmount;
	}

	public void setPenaltyExemptAmount(String penaltyExemptAmount) {
		this.penaltyExemptAmount = penaltyExemptAmount;
	}

	public String getFineEepmtAmount() {
		return fineEepmtAmount;
	}

	public void setFineEepmtAmount(String fineEepmtAmount) {
		this.fineEepmtAmount = fineEepmtAmount;
	}

	public String getFactAddAmount() {
		return factAddAmount;
	}

	public void setFactAddAmount(String factAddAmount) {
		this.factAddAmount = factAddAmount;
	}

	public String getFactBreakAmount() {
		return factBreakAmount;
	}

	public void setFactBreakAmount(String factBreakAmount) {
		this.factBreakAmount = factBreakAmount;
	}

	public Date getCapitalInterestRepayDate() {
		return capitalInterestRepayDate;
	}

	public void setCapitalInterestRepayDate(Date capitalInterestRepayDate) {
		this.capitalInterestRepayDate = capitalInterestRepayDate;
	}

	public Date getAllRepayDate() {
		return allRepayDate;
	}

	public void setAllRepayDate(Date allRepayDate) {
		this.allRepayDate = allRepayDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public StaRepayPlanStatus() {
		super();
	}

	public StaRepayPlanStatus(String id) {
		super(id);
	}

	@Length(min = 0, max = 10, message = "还款日期长度必须介于 0 和 10 之间")
	public String getDataDt() {
		return dataDt;
	}

	public void setDataDt(String dataDt) {
		this.dataDt = dataDt;
	}

	@Length(min = 0, max = 32, message = "2级公司长度必须介于 0 和 32 之间")
	public Office getOrgLevel2() {
		return orgLevel2;
	}

	public void setOrgLevel2(Office orgLevel2) {
		this.orgLevel2 = orgLevel2;
	}

	@Length(min = 0, max = 32, message = "3级公司长度必须介于 0 和 32 之间")
	public Office getOrgLevel3() {
		return orgLevel3;
	}

	public void setOrgLevel3(Office orgLevel3) {
		this.orgLevel3 = orgLevel3;
	}

	@Length(min = 0, max = 32, message = "4级公司长度必须介于 0 和 32 之间")
	public Office getOrgLevel4() {
		return orgLevel4;
	}

	public void setOrgLevel4(Office orgLevel4) {
		this.orgLevel4 = orgLevel4;
	}

	@Length(min = 0, max = 50, message = "合同编号长度必须介于 0 和 50 之间")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Length(min = 0, max = 10, message = "期数长度必须介于 0 和 10 之间")
	public String getPeriodNum() {
		return periodNum;
	}

	public void setPeriodNum(String periodNum) {
		this.periodNum = periodNum;
	}

	@Length(min = 0, max = 4, message = "每期还款状态长度必须介于 0 和 4 之间")
	public String getRepayPeriodStatus() {
		return repayPeriodStatus;
	}

	public void setRepayPeriodStatus(String repayPeriodStatus) {
		this.repayPeriodStatus = repayPeriodStatus;
	}

	public String getFactServiceFee() {
		return factServiceFee;
	}

	public void setFactServiceFee(String factServiceFee) {
		this.factServiceFee = factServiceFee;
	}

	public String getFactMangementFee() {
		return factMangementFee;
	}

	public void setFactMangementFee(String factMangementFee) {
		this.factMangementFee = factMangementFee;
	}

	public String getFactCapitalAmount() {
		return factCapitalAmount;
	}

	public void setFactCapitalAmount(String factCapitalAmount) {
		this.factCapitalAmount = factCapitalAmount;
	}

	public String getFactInterestAmount() {
		return factInterestAmount;
	}

	public void setFactInterestAmount(String factInterestAmount) {
		this.factInterestAmount = factInterestAmount;
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

	public String getFactRepayAmount() {
		return factRepayAmount;
	}

	public void setFactRepayAmount(String factRepayAmount) {
		this.factRepayAmount = factRepayAmount;
	}
	
	

}