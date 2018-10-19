package com.resoft.Accoutinterface.rest.financialPlatform.entry;

import java.io.Serializable;
import java.util.HashMap;

public class AccAccountDCRequest implements Serializable{
	private static final long serialVersionUID = 1L;
	private String  mchn;
	private String  seq_no;
	private String  trade_type;//见数据字典    交易类型    1
	private String data;
	//private String  from_cust_no;//出账客户编号
	//private String  from_cust_type;//出账账户类型   1
	//private String  to_cust_no;//入账客户编号
	//private String  to_cust_type;//入账用户类型
	//private String  amt;//转账金额
	//private String  funds_type;//funds_type交易子类型
	//private String  busi_type;//业务类型
	//private String  busi_id;//业务id
	//private String  transfer_flag;//transfer_flag转账标识
	private String  signature;//签名
	private String  back_url;
	private String  page_url;
	public AccAccountDCRequest() {
		super();
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

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getBack_url() {
		return back_url;
	}

	public void setBack_url(String back_url) {
		this.back_url = back_url;
	}

	public String getPage_url() {
		return page_url;
	}

	public void setPage_url(String page_url) {
		this.page_url = page_url;
	}
}
