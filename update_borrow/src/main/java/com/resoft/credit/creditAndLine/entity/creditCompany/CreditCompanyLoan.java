package com.resoft.credit.creditAndLine.entity.creditCompany;

import org.hibernate.validator.constraints.Length;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 征信企业贷款明细Entity
 * @author wuxi01
 * @version 2016-03-18
 */
public class CreditCompanyLoan extends DataEntity<CreditCompanyLoan> {
	
	private static final long serialVersionUID = 1L;
	private CreditCompany creditCompany;		// 企业征信ID
	private String bankName;		// 发贷行
	private String loanKind;		// 贷款类型
	private String loanStatus;		// 贷款状态
	private String loanDate;		// 放款日期
	private String expireDate;		// 到期日期
	private String currCd;		// 币种
	private String loanAmount;		// 放款金额
	private String balanceAmount;		// 余额
	private String overdueAmount;		// 逾期金额
	private String guaranteeOutStat;		// 对外担保状态
	private String monthExpireAmount;		// 月内贷款到期
	private String creditCompanyId;
	
	public CreditCompanyLoan() {
		super();
	}

	public CreditCompanyLoan(String id){
		super(id);
	}

	@Length(min=0, max=20, message="发贷行长度必须介于 0 和 20 之间")
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	@Length(min=0, max=4, message="贷款类型长度必须介于 0 和 4 之间")
	public String getLoanKind() {
		return loanKind;
	}

	public void setLoanKind(String loanKind) {
		this.loanKind = loanKind;
	}
	
	@Length(min=0, max=4, message="贷款状态长度必须介于 0 和 4 之间")
	public String getLoanStatus() {
		return loanStatus;
	}

	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}
	
	@Length(min=0, max=4, message="币种长度必须介于 0 和 4 之间")
	public String getCurrCd() {
		return currCd;
	}

	public void setCurrCd(String currCd) {
		this.currCd = currCd;
	}
	
	public String getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}
	
	public String getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(String balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	
	public String getOverdueAmount() {
		return overdueAmount;
	}

	public void setOverdueAmount(String overdueAmount) {
		this.overdueAmount = overdueAmount;
	}
	
	@Length(min=0, max=4, message="对外担保状态长度必须介于 0 和 4 之间")
	public String getGuaranteeOutStat() {
		return guaranteeOutStat;
	}

	public void setGuaranteeOutStat(String guaranteeOutStat) {
		this.guaranteeOutStat = guaranteeOutStat;
	}
	
	public String getMonthExpireAmount() {
		return monthExpireAmount;
	}

	public void setMonthExpireAmount(String monthExpireAmount) {
		this.monthExpireAmount = monthExpireAmount;
	}

	public CreditCompany getCreditCompany() {
		return creditCompany;
	}

	public void setCreditCompany(CreditCompany creditCompany) {
		this.creditCompany = creditCompany;
	}

	public String getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(String loanDate) {
		this.loanDate = loanDate;
	}

	public String getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	public String getCreditCompanyId() {
		return creditCompanyId;
	}

	public void setCreditCompanyId(String creditCompanyId) {
		this.creditCompanyId = creditCompanyId;
	}
	
}