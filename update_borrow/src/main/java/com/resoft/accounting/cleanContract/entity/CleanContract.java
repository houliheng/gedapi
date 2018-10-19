package com.resoft.accounting.cleanContract.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 数据处理Entity
 * @author wangguodong
 * @version 2017-09-19
 */
public class CleanContract extends DataEntity<CleanContract> {
	
	private static final long serialVersionUID = 1L;
	private String dataDt;		// 数据时间
	private String orgLevel2;		// 2级公司
	private String orgLevel3;		// 3级公司
	private String orgLevel4;		// 4级公司
	private String contractNo;		// 合同编号
	private String periodNum;		// 期数
	private String repayPeriodStatus;		// 每期还款状态
	private Date capitalInterestRepayDate;		// 本息结清日期
	private Date allRepayDate;		// 总结清日期
	private String factRepayAmount;		// 还款金额-合计（服务费，账户管理费，本期本金，本期利息）
	private String factServiceFee;		// 实还-服务费
	private String factManagementFee;		// 实还-账户管理费
	private String factCapitalAmount;		// 实还-本期本金
	private String factInterestAmount;		// 实还-本期利息
	private String factPenaltyAmount;		// 实还-违约金
	private String factFineAmount;		// 实还-罚息
	private String factAddAmount;		// 实还-营业外收入
	private String factBreakAmount;		// 实还-提前还款费用
	private String backAmount;		// 退回金额
	
	public CleanContract() {
		super();
	}

	public CleanContract(String id){
		super(id);
	}

	@Length(min=0, max=10, message="数据时间长度必须介于 0 和 10 之间")
	public String getDataDt() {
		return dataDt;
	}

	public void setDataDt(String dataDt) {
		this.dataDt = dataDt;
	}
	
	@Length(min=0, max=32, message="2级公司长度必须介于 0 和 32 之间")
	public String getOrgLevel2() {
		return orgLevel2;
	}

	public void setOrgLevel2(String orgLevel2) {
		this.orgLevel2 = orgLevel2;
	}
	
	@Length(min=0, max=32, message="3级公司长度必须介于 0 和 32 之间")
	public String getOrgLevel3() {
		return orgLevel3;
	}

	public void setOrgLevel3(String orgLevel3) {
		this.orgLevel3 = orgLevel3;
	}
	
	@Length(min=0, max=32, message="4级公司长度必须介于 0 和 32 之间")
	public String getOrgLevel4() {
		return orgLevel4;
	}

	public void setOrgLevel4(String orgLevel4) {
		this.orgLevel4 = orgLevel4;
	}
	
	@Length(min=0, max=50, message="合同编号长度必须介于 0 和 50 之间")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	@Length(min=0, max=10, message="期数长度必须介于 0 和 10 之间")
	public String getPeriodNum() {
		return periodNum;
	}

	public void setPeriodNum(String periodNum) {
		this.periodNum = periodNum;
	}
	
	@Length(min=0, max=4, message="每期还款状态长度必须介于 0 和 4 之间")
	public String getRepayPeriodStatus() {
		return repayPeriodStatus;
	}

	public void setRepayPeriodStatus(String repayPeriodStatus) {
		this.repayPeriodStatus = repayPeriodStatus;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCapitalInterestRepayDate() {
		return capitalInterestRepayDate;
	}

	public void setCapitalInterestRepayDate(Date capitalInterestRepayDate) {
		this.capitalInterestRepayDate = capitalInterestRepayDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAllRepayDate() {
		return allRepayDate;
	}

	public void setAllRepayDate(Date allRepayDate) {
		this.allRepayDate = allRepayDate;
	}
	
	public String getFactRepayAmount() {
		return factRepayAmount;
	}

	public void setFactRepayAmount(String factRepayAmount) {
		this.factRepayAmount = factRepayAmount;
	}
	
	public String getFactServiceFee() {
		return factServiceFee;
	}

	public void setFactServiceFee(String factServiceFee) {
		this.factServiceFee = factServiceFee;
	}
	
	public String getFactManagementFee() {
		return factManagementFee;
	}

	public void setFactManagementFee(String factManagementFee) {
		this.factManagementFee = factManagementFee;
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
	
	public String getBackAmount() {
		return backAmount;
	}

	public void setBackAmount(String backAmount) {
		this.backAmount = backAmount;
	}
	
}