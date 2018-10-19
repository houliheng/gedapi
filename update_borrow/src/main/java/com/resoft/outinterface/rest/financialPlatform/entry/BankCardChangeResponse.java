package com.resoft.outinterface.rest.financialPlatform.entry;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility=Visibility.PUBLIC_ONLY)
public class BankCardChangeResponse {
	private String mchn;
	private String seq_no;
	private String trade_type;
	private String signature;
	private String bank_id;
	private String city_id;
	private String bank_card;
	private String acc_no;
	private String resp_code;
	private String resp_msg;
	public String getMchn() {
		return mchn;
	}
	public void setMchn(String mchn) {
		this.mchn = mchn;
	}
	protected String getSeqNo() {
		return seq_no;
	}
	protected void setSeqNo(String seq_no) {
		this.seq_no = seq_no;
	}
	protected String getTradeType() {
		return trade_type;
	}
	protected void setTradeType(String trade_type) {
		this.trade_type = trade_type;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	protected String getBankId() {
		return bank_id;
	}
	protected void setBankId(String bank_id) {
		this.bank_id = bank_id;
	}
	protected String getCityId() {
		return city_id;
	}
	protected void setCityId(String city_id) {
		this.city_id = city_id;
	}
	protected String getBankCard() {
		return bank_card;
	}
	protected void setBankCard(String bank_card) {
		this.bank_card = bank_card;
	}
	protected String getAccNo() {
		return acc_no;
	}
	protected void setAccNo(String acc_no) {
		this.acc_no = acc_no;
	}
	protected String getRespCode() {
		return resp_code;
	}
	protected void setRespCode(String resp_code) {
		this.resp_code = resp_code;
	}
	protected String getRespMsg() {
		return resp_msg;
	}
	protected void setRespMsg(String resp_msg) {
		this.resp_msg = resp_msg;
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
	public String getBank_id() {
		return bank_id;
	}
	public void setBank_id(String bank_id) {
		this.bank_id = bank_id;
	}
	public String getCity_id() {
		return city_id;
	}
	public void setCity_id(String city_id) {
		this.city_id = city_id;
	}
	public String getBank_card() {
		return bank_card;
	}
	public void setBank_card(String bank_card) {
		this.bank_card = bank_card;
	}
	public String getAcc_no() {
		return acc_no;
	}
	public void setAcc_no(String acc_no) {
		this.acc_no = acc_no;
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
