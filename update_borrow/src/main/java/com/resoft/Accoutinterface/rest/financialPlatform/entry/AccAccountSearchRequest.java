package com.resoft.Accoutinterface.rest.financialPlatform.entry;

import java.io.Serializable;

public class AccAccountSearchRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	private String mchn;
	private String seq_no;
	private String cust_no;
	private String trade_type;
	private String user_no;
	private String busi_no;
	private String signature;
	private String busi_type;

	public AccAccountSearchRequest() {
		super();
		// TODO Auto-generated constructor stub
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
	public String getCust_no() {
		return cust_no;
	}
	public void setCust_no(String cust_no) {
		this.cust_no = cust_no;
	}
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	public String getUser_no() {
		return user_no;
	}
	public void setUser_no(String user_no) {
		this.user_no = user_no;
	}
	public String getBusi_no() {
		return busi_no;
	}
	public void setBusi_no(String busi_no) {
		this.busi_no = busi_no;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getBusi_type() {
		return busi_type;
	}
	public void setBusi_type(String busi_type) {
		this.busi_type = busi_type;
	}
	
}
