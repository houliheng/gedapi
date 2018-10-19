package com.resoft.outinterface.rest.financialPlatform.entry;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
@JsonAutoDetect(fieldVisibility=Visibility.PUBLIC_ONLY)
public class MortgageeDepositResponse {
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
	private String resp_code;
	private String resp_msg;
	public String getContract_no() {
		return contract_no;
	}
	public void setContract_no(String contract_no) {
		this.contract_no = contract_no;
	}
	protected String getContractNo() {
		return contract_no;
	}
	protected void setContractNo(String contract_no) {
		this.contract_no = contract_no;
	}
	public String getSeq_no() {
		return seq_no;
	}
	public void setSeq_no(String seq_no) {
		this.seq_no = seq_no;
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
	public String getResp_code() {
		return resp_code;
	}
	public void setResp_code(String resp_code) {
		this.resp_code = resp_code;
	}
	public String getResp_msg() {
		return resp_msg;
	}
	public void setResp_msg(String resp_msg) {
		this.resp_msg = resp_msg;
	}
	protected String getRespCode() {
		return resp_code;
	}
	protected void setRespCode(String resp_code) {
		this.resp_code = resp_code;
	}
	protected String getRespMsg() {
		return resp_msg;
	}
	protected void setRespMsg(String resp_msg) {
		this.resp_msg = resp_msg;
	}
	public String getMchn() {
		return mchn;
	}
	public void setMchn(String mchn) {
		this.mchn = mchn;
	}
	protected String getSeqNo() {
		return seq_no;
	}
	protected void setSeqNo(String seq_no) {
		this.seq_no = seq_no;
	}
	protected String getTradeType() {
		return trade_type;
	}
	protected void setTradeType(String trade_type) {
		this.trade_type = trade_type;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	protected String getContractId() {
		return contract_id;
	}
	protected void setContractId(String contract_id) {
		this.contract_id = contract_id;
	}
	protected String getMortgageeAccNo() {
		return mortgagee_acc_no;
	}
	protected void setMortgageeAccNo(String mortgagee_acc_no) {
		this.mortgagee_acc_no = mortgagee_acc_no;
	}
	protected String getAccNo() {
		return acc_no;
	}
	protected void setAccNo(String acc_no) {
		this.acc_no = acc_no;
	}
	protected BigDecimal getContractAmt() {
		return contract_amt;
	}
	protected void setContractAmt(BigDecimal contract_amt) {
		this.contract_amt = contract_amt;
	}
	protected BigDecimal getPayAmt() {
		return pay_amt;
	}
	protected void setPayAmt(BigDecimal pay_amt) {
		this.pay_amt = pay_amt;
	}
}
