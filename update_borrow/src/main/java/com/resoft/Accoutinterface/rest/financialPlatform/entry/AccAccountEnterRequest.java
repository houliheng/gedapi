package com.resoft.Accoutinterface.rest.financialPlatform.entry;

import java.io.Serializable;
import java.util.List;

public class AccAccountEnterRequest implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	
	private String mchn;
	private String seq_no;
	private String trade_type;
	private String signature;
	private List<AccEnterAccountList> enter_account;
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
	public List<AccEnterAccountList> getEnter_account() {
		return enter_account;
	}
	public void setEnter_account(List<AccEnterAccountList> enter_account) {
		this.enter_account = enter_account;
	}
	@Override
	public String toString() {
		return "AccountEnterRequest [mchn=" + mchn + ", seq_no=" + seq_no
				+ ", trade_type=" + trade_type + ", signature=" + signature
				+ ", enter_account=" + enter_account + "]";
	}
}
