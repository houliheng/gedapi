package com.resoft.outinterface.rest.financialPlatform.entry;

import java.math.BigDecimal;

public class SettleList{
	private BigDecimal settle_amt;
	private String account_type;
	public BigDecimal getSettle_amt() {
		return settle_amt;
	}
	public void setSettle_amt(BigDecimal settle_amt) {
		this.settle_amt = settle_amt;
	}
	public String getAccount_type() {
		return account_type;
	}
	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}
	protected BigDecimal getSettleAmt() {
		return settle_amt;
	}
	protected void setSettleAmt(BigDecimal settle_amt) {
		this.settle_amt = settle_amt;
	}
	protected String getAccountType() {
		return account_type;
	}
	protected void setAccountType(String account_type) {
		this.account_type = account_type;
	}
}
