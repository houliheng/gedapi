package com.resoft.Accoutinterface.rest.financialPlatform.entry;

import java.io.Serializable;

public class AccAccountSearchResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	private String mchn;
	private String seq_no;
	private String trade_type;
	private String signature;
	private String resp_code;
	private String resp_msg;
	private String amount;
	public AccAccountSearchResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AccAccountSearchResponse(String mchn, String seq_no, String trade_type, String signature, String resp_code,
			String resp_msg) {
		super();
		this.mchn = mchn;
		this.seq_no = seq_no;
		this.trade_type = trade_type;
		this.signature = signature;
		this.resp_code = resp_code;
		this.resp_msg = resp_msg;
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
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	

}
