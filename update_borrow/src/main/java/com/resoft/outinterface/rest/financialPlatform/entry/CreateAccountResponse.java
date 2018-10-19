package com.resoft.outinterface.rest.financialPlatform.entry;

public class CreateAccountResponse {
	private String mchn;
	private String seq_no;
	private String trade_type;
	private String signature;
	private String acc_no;
	private String resp_code;
	private String resp_msg;
	public String getAcc_no() {
		return acc_no;
	}
	public void setAcc_no(String acc_no) {
		this.acc_no = acc_no;
	}
	public String getAccNo() {
		return acc_no;
	}
	public void setAccNo(String acc_no) {
		this.acc_no = acc_no;
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
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
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
	@Override
	public String toString() {
		return "CreateAccountResponse [mchn=" + mchn + ", seq_no=" + seq_no
				+ ", trade_type=" + trade_type + ", signature=" + signature
				+ ", resp_code=" + resp_code + ", resp_msg=" + resp_msg + "]";
	}
}
