package com.resoft.outinterface.rest.financialPlatform.entry;

import java.math.BigDecimal;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * @author caoyinglong
 *抵押权人提现
 */
public class MortgageeDepositRequest {
	private String mchn;
	private String seq_no;
	private String trade_type;
	private String signature;
	private String contract_id;
	private String contract_no;
	private String mortgagee_acc_no;
	private String acc_no;
	private BigDecimal contract_amt;
	private BigDecimal pay_amt;
	public String getContract_no() {
		return contract_no;
	}
	public void setContract_no(String contract_no) {
		this.contract_no = contract_no;
	}
	public String getContractNo() {
		return contract_no;
	}
	public void setContractNo(String contract_no) {
		this.contract_no = contract_no;
	}
	public String getMchn() {
		return mchn;
	}
	public void setMchn(String mchn) {
		this.mchn = mchn;
	}
	public String getSeq_no() {
		return seq_no;
	}
	public void setSeq_no(String seq_no) {
		this.seq_no = seq_no;
	}
	public String getTradeType() {
		return trade_type;
	}
	public void setTradeType(String trade_type) {
		this.trade_type = trade_type;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getContractId() {
		return contract_id;
	}
	public void setContractId(String contract_id) {
		this.contract_id = contract_id;
	}
	public String getMortgageeAccNo() {
		return mortgagee_acc_no;
	}
	public void setMortgageeAccNo(String mortgagee_acc_no) {
		this.mortgagee_acc_no = mortgagee_acc_no;
	}
	public String getAccNo() {
		return acc_no;
	}
	public void setAccNo(String acc_no) {
		this.acc_no = acc_no;
	}
	public BigDecimal getContractAmt() {
		return contract_amt;
	}
	public void setContractAmt(BigDecimal contract_amt) {
		this.contract_amt = contract_amt;
	}
	public BigDecimal getPayAmt() {
		return pay_amt;
	}
	public void setPayAmt(BigDecimal pay_amt) {
		this.pay_amt = pay_amt;
	}
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	public String getContract_id() {
		return contract_id;
	}
	public void setContract_id(String contract_id) {
		this.contract_id = contract_id;
	}
	public String getMortgagee_acc_no() {
		return mortgagee_acc_no;
	}
	public void setMortgagee_acc_no(String mortgagee_acc_no) {
		this.mortgagee_acc_no = mortgagee_acc_no;
	}
	public String getAcc_no() {
		return acc_no;
	}
	public void setAcc_no(String acc_no) {
		this.acc_no = acc_no;
	}
	public BigDecimal getContract_amt() {
		return contract_amt;
	}
	public void setContract_amt(BigDecimal contract_amt) {
		this.contract_amt = contract_amt;
	}
	public BigDecimal getPay_amt() {
		return pay_amt;
	}
	public void setPay_amt(BigDecimal pay_amt) {
		this.pay_amt = pay_amt;
	}
	@Override
	public String toString() {
		return "MortgageeDepositRequest [mchn=" + mchn + ", seq_no=" + seq_no
				+ ", trade_type=" + trade_type + ", signature=" + signature
				+ ", contract_id=" + contract_id + ", mortgagee_acc_no="
				+ mortgagee_acc_no + ", acc_no=" + acc_no + ", contract_amt="
				+ contract_amt + ", pay_amt=" + pay_amt + "]";
	}
	
}
