package com.resoft.outinterface.rest.financialPlatform.entry;

public class RepaymentWithholdingIn {
	private RepaymentWithholdingList rwl;
	private String mchn;
	private String seq_no;
	private String trade_type;
	private String signature;
	private String resp_code;
	private String resp_msg;
	private String transfer_resp_code;
	private String transfer_resp_msg;
	public RepaymentWithholdingList getRwl() {
		return rwl;
	}
	public void setRwl(RepaymentWithholdingList rwl) {
		this.rwl = rwl;
	}
	public String getMchn() {
		return mchn;
	}
	public void setMchn(String mchn) {
		this.mchn = mchn;
	}
	public String getSeqNo() {
		return seq_no;
	}
	public void setSeqNo(String seq_no) {
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
	public String getRespCode() {
		return resp_code;
	}
	public void setRespCode(String resp_code) {
		this.resp_code = resp_code;
	}
	public String getRespMsg() {
		return resp_msg;
	}
	public void setRespMsg(String resp_msg) {
		this.resp_msg = resp_msg;
	}
	public String getTransferRespCode() {
		return transfer_resp_code;
	}
	public void setTransferRespCode(String transfer_resp_code) {
		this.transfer_resp_code = transfer_resp_code;
	}
	public String getTransferRespMsg() {
		return transfer_resp_msg;
	}
	public void setTransferRespMsg(String transfer_resp_msg) {
		this.transfer_resp_msg = transfer_resp_msg;
	}

}
