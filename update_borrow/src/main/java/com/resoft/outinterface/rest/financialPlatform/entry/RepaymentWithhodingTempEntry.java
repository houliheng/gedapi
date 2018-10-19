package com.resoft.outinterface.rest.financialPlatform.entry;

public class RepaymentWithhodingTempEntry {
	private String mchn;
	private String seqNo;
	private String tradeType;
	private String signature;
	private RepaymentWithholdingList rwl;
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
	public RepaymentWithholdingList getRwl() {
		return rwl;
	}
	public void setRwl(RepaymentWithholdingList rwl) {
		this.rwl = rwl;
	}
}
