package com.resoft.outinterface.rest.financialPlatform.entry;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

/**Create Financial Platform Account Entry*/
@JsonAutoDetect(fieldVisibility=Visibility.PUBLIC_ONLY)
public class CreateAccountRequest {
	private String mchn;
	private String seq_no;
	private String trade_type;
	private String signature;
	private String contract_no;
	private String bank_card;
	private String name;
	private String mobile;
	private String cert_no;
	private String bank_id;
	private String city_id;
	
	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	public String getContract_no() {
		return contract_no;
	}

	public void setContract_no(String contract_no) {
		this.contract_no = contract_no;
	}

	public String getBank_card() {
		return bank_card;
	}

	public void setBank_card(String bank_card) {
		this.bank_card = bank_card;
	}

	public String getCert_no() {
		return cert_no;
	}

	public void setCert_no(String cert_no) {
		this.cert_no = cert_no;
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

	public CreateAccountRequest() {
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
	protected String getContractNo() {
		return contract_no;
	}
	protected void setContractNo(String contract_no) {
		this.contract_no = contract_no;
	}
	protected String getBankCard() {
		return bank_card;
	}
	protected void setBankCard(String bank_card) {
		this.bank_card = bank_card;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	protected String getCertNo() {
		return cert_no;
	}
	protected void setCertNo(String cert_no) {
		this.cert_no = cert_no;
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
	@Override
	public String toString() {
		return "CreateAccountRequest [mchn=" + mchn + ", seq_no=" + seq_no
				+ ", trade_type=" + trade_type + ", signature=" + signature
				+ ", contract_id=" + contract_no + ", bank_card=" + bank_card
				+ ", name=" + name + ", mobile=" + mobile + ", cert_no="
				+ cert_no + ", bank_id=" + bank_id + ", city_id=" + city_id
				+ "]";
	}
	
}
