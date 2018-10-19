package com.resoft.outinterface.rest.financialPlatform.entry;

import java.util.List;

public class RepaymentWithholdingRequest  {
	private String mchn;
	private String seq_no;
	private String trade_type;
	private String signature;
	private List<RepayList> repay_list;
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
	public List<RepayList> getRepay_list() {
		return repay_list;
	}
	public void setRepay_list(List<RepayList> repay_list) {
		this.repay_list = repay_list;
	}
	@Override
	public String toString() {
		return "RepaymentWithholdingRequest [mchn=" + mchn + ", seq_no="
				+ seq_no + ", trade_type=" + trade_type + ", signature="
				+ signature + ", repay_list=" + repay_list + "]";
	}
}
