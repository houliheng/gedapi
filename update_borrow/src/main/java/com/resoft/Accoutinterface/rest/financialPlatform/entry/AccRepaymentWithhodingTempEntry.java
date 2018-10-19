package com.resoft.Accoutinterface.rest.financialPlatform.entry;

import java.io.Serializable;

public class AccRepaymentWithhodingTempEntry implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	
	private String mchn;
	private String seqNo;
	private String tradeType;
	private String signature;
	private AccRepaymentWithholdingList rwl;
	public String getMchn() {
		return mchn;
	}
	public void setMchn(String mchn) {
		this.mchn = mchn;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public AccRepaymentWithholdingList getRwl() {
		return rwl;
	}
	public void setRwl(AccRepaymentWithholdingList rwl) {
		this.rwl = rwl;
	}
}
