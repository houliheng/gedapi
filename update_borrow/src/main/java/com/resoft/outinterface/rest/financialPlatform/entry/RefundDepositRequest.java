package com.resoft.outinterface.rest.financialPlatform.entry;

import java.math.BigDecimal;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class RefundDepositRequest extends DataEntity<RefundDepositRequest>{
	private static final long serialVersionUID = 1L;
	private String mchn;
	private String seq_no;
	private String trade_type;
	private String signature;
	private String contract_id;
	private String contract_no;
	private String acc_no;
	private BigDecimal refund_amt;
	
	public String getContract_no() {
		return contract_no;
	}
	public void setContract_no(String contract_no) {
		this.contract_no = contract_no;
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
	public String getContractNo() {
		return contract_no;
	}
	public void setContractNo(String contract_no) {
		this.contract_no = contract_no;
	}
	public String getAcc_no() {
		return acc_no;
	}
	public void setAcc_no(String acc_no) {
		this.acc_no = acc_no;
	}
	public BigDecimal getRefund_amt() {
		return refund_amt;
	}
	public void setRefund_amt(BigDecimal refund_amt) {
		this.refund_amt = refund_amt;
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
	public String getAccNo() {
		return acc_no;
	}
	public void setAccNo(String acc_no) {
		this.acc_no = acc_no;
	}
	public BigDecimal getRefundAmt() {
		return refund_amt;
	}
	public void setRefundAmt(BigDecimal refund_amt) {
		this.refund_amt = refund_amt;
	}
	@Override
	public String toString() {
		return "RefundDepositRequest [mchn=" + mchn + ", seq_no=" + seq_no
				+ ", trade_type=" + trade_type + ", signature=" + signature
				+ ", contract_id=" + contract_id + ", acc_no=" + acc_no
				+ ", refund_amt=" + refund_amt + "]";
	}
	
}
