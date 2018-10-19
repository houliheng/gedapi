package com.resoft.Accoutinterface.rest.financialPlatform.entry;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility=Visibility.PUBLIC_ONLY)
public class AccRepaymentWithholdingResponse  implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	
	private String mchn;
	private String seq_no;
	private String trade_type;
	private String signature;
	private List<AccRepaymentWithholdingList> repay_list;
	private String resp_code;
	private String resp_msg;

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
	public List<AccRepaymentWithholdingList> getRepay_list() {
		return repay_list;
	}
	public void setRepay_list(List<AccRepaymentWithholdingList> repay_list) {
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
}
