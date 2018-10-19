package com.resoft.outinterface.rest.financialPlatform.entry;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility=Visibility.PUBLIC_ONLY)
public class RepaymentWithholdingResponse {
	private String mchn;
	private String seq_no;
	private String trade_type;
	private String signature;
	private List<RepaymentWithholdingList> repay_list;
	private String resp_code;
	private String resp_msg;

	private String transfer_resp_code;
	private String transfer_resp_msg;

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
	public List<RepaymentWithholdingList> getRepay_list() {
		return repay_list;
	}
	public void setRepay_list(List<RepaymentWithholdingList> repay_list) {
		this.repay_list = repay_list;
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
	public String getTransfer_resp_code() {
		return transfer_resp_code;
	}
	public void setTransfer_resp_code(String transfer_resp_code) {
		this.transfer_resp_code = transfer_resp_code;
	}
	public String getTransfer_resp_msg() {
		return transfer_resp_msg;
	}
	public void setTransfer_resp_msg(String transfer_resp_msg) {
		this.transfer_resp_msg = transfer_resp_msg;
	}

}
