package com.resoft.outinterface.rest.GQget.client.entry.request.issuingTender;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
@JsonAutoDetect(fieldVisibility=Visibility.PUBLIC_ONLY)
public class BidRepayment {
	private int period;
	private BigDecimal bid_pre_principal;
	private BigDecimal bid_pre_interest;
	private BigDecimal bid_pre_amount;
	private BigDecimal contract_pre_principal;
	private BigDecimal contract_pre_interest;
	private BigDecimal contract_pre_amount;
	private BigDecimal pre_diff_principal;
	private BigDecimal pre_diff_interest;
	private BigDecimal pre_diff_amount;
	private BigDecimal pre_consulting_fee;
	
	public BigDecimal getPre_consulting_fee() {
		return pre_consulting_fee;
	}
	public void setPre_consulting_fee(BigDecimal pre_consulting_fee) {
		this.pre_consulting_fee = pre_consulting_fee;
	}
	protected BigDecimal getPreConsultingFee() {
		return pre_consulting_fee;
	}
	protected void setPreConsultingFee(BigDecimal pre_consulting_fee) {
		this.pre_consulting_fee = pre_consulting_fee;
	}
	public BigDecimal getPre_diff_principal() {
		return pre_diff_principal;
	}
	public void setPre_diff_principal(BigDecimal pre_diff_principal) {
		this.pre_diff_principal = pre_diff_principal;
	}
	public BigDecimal getPre_diff_interest() {
		return pre_diff_interest;
	}
	public void setPre_diff_interest(BigDecimal pre_diff_interest) {
		this.pre_diff_interest = pre_diff_interest;
	}
	public BigDecimal getPre_diff_amount() {
		return pre_diff_amount;
	}
	public void setPre_diff_amount(BigDecimal pre_diff_amount) {
		this.pre_diff_amount = pre_diff_amount;
	}
	public BigDecimal getContract_pre_principal() {
		return contract_pre_principal;
	}
	public void setContract_pre_principal(BigDecimal contract_pre_principal) {
		this.contract_pre_principal = contract_pre_principal;
	}
	public BigDecimal getContract_pre_interest() {
		return contract_pre_interest;
	}
	public void setContract_pre_interest(BigDecimal contract_pre_interest) {
		this.contract_pre_interest = contract_pre_interest;
	}
	public BigDecimal getContract_pre_amount() {
		return contract_pre_amount;
	}
	public void setContract_pre_amount(BigDecimal contract_pre_amount) {
		this.contract_pre_amount = contract_pre_amount;
	}
	protected BigDecimal getContractPrePrincipal() {
		return contract_pre_principal;
	}
	protected void setContractPrePrincipal(BigDecimal contract_pre_principal) {
		this.contract_pre_principal = contract_pre_principal;
	}
	protected BigDecimal getContractPreInterest() {
		return contract_pre_interest;
	}
	protected void setContractPreInterest(BigDecimal contract_pre_interest) {
		this.contract_pre_interest = contract_pre_interest;
	}
	protected BigDecimal getContractPreAmount() {
		return contract_pre_amount;
	}
	protected void setContractPreAmount(BigDecimal contract_pre_amount) {
		this.contract_pre_amount = contract_pre_amount;
	}
	protected BigDecimal getPreDiffPrincipal() {
		return pre_diff_principal;
	}
	protected void setPreDiffPrincipal(BigDecimal pre_diff_principal) {
		this.pre_diff_principal = pre_diff_principal;
	}
	protected BigDecimal getPreDiffInterest() {
		return pre_diff_interest;
	}
	protected void setPreDiffInterest(BigDecimal pre_diff_interest) {
		this.pre_diff_interest = pre_diff_interest;
	}
	protected BigDecimal getPreDiffAmount() {
		return pre_diff_amount;
	}
	protected void setPreDiffAmount(BigDecimal pre_diff_amount) {
		this.pre_diff_amount = pre_diff_amount;
	}
	public  int getPeriod() {
		return period;
	}
	public  void setPeriod(int period) {
		this.period = period;
	}
	protected BigDecimal getBidPrePrincipal() {
		return bid_pre_principal;
	}
	protected void setBidPrePrincipal(BigDecimal bid_pre_principal) {
		this.bid_pre_principal = bid_pre_principal;
	}
	protected BigDecimal getBidPreInterest() {
		return bid_pre_interest;
	}
	protected void setBidPreInterest(BigDecimal bid_pre_interest) {
		this.bid_pre_interest = bid_pre_interest;
	}
	protected BigDecimal getBidPreAmount() {
		return bid_pre_amount;
	}
	protected void setBidPreAmount(BigDecimal bid_pre_amount) {
		this.bid_pre_amount = bid_pre_amount;
	}
	public BigDecimal getBid_pre_principal() {
		return bid_pre_principal;
	}
	public void setBid_pre_principal(BigDecimal bid_pre_principal) {
		this.bid_pre_principal = bid_pre_principal;
	}
	public BigDecimal getBid_pre_interest() {
		return bid_pre_interest;
	}
	public void setBid_pre_interest(BigDecimal bid_pre_interest) {
		this.bid_pre_interest = bid_pre_interest;
	}
	public BigDecimal getBid_pre_amount() {
		return bid_pre_amount;
	}
	public void setBid_pre_amount(BigDecimal bid_pre_amount) {
		this.bid_pre_amount = bid_pre_amount;
	}
}
