package com.resoft.credit.creditAndLine.entity.creditCust;

import org.hibernate.validator.constraints.Length;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 征信个人贷款明细Entity
 * @author wuxi01
 * @version 2016-03-17
 */
public class CreditCustLoan extends DataEntity<CreditCustLoan> {
	
	private static final long serialVersionUID = 1L;
	private CreditCust creditCust;		// 个人征信ID
	private String bankName;		// 发贷行
	private String loanKind;		// 贷款类型
	private String loanStatus;		// 贷款状态
	private String loanDate;		// 放款日期
	private String expireDate;		// 到期日期
	private String guaranteeType;		// 担保方式
	private String loanAmount;		// 放款金额
	private String balanceAmount;		// 余额
	private String overdueAmount;		// 逾期金额
	private String monthExpireAmount;		// 月内贷款到期
	private String creditCustId;
	private String loanNature; //贷款性质 LOAN_NATURE
	
	public String getLoanNature() {
		return loanNature;
	}

	public void setLoanNature(String loanNature) {
		this.loanNature = loanNature;
	}

	public CreditCustLoan() {
		super();
	}

	public CreditCustLoan(String id){
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
	
	@Length(min=0, max=4, message="担保方式长度必须介于 0 和 4 之间")
	public String getGuaranteeType() {
		return guaranteeType;
	}

	public void setGuaranteeType(String guaranteeType) {
		this.guaranteeType = guaranteeType;
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
	
	public String getMonthExpireAmount() {
		return monthExpireAmount;
	}

	public void setMonthExpireAmount(String monthExpireAmount) {
		this.monthExpireAmount = monthExpireAmount;
	}

	public CreditCust getCreditCust() {
		return creditCust;
	}

	public void setCreditCust(CreditCust creditCust) {
		this.creditCust = creditCust;
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

	public String getCreditCustId() {
		return creditCustId;
	}

	public void setCreditCustId(String creditCustId) {
		this.creditCustId = creditCustId;
	}
	
	
	
}