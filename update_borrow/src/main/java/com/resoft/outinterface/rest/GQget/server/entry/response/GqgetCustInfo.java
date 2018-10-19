package com.resoft.outinterface.rest.GQget.server.entry.response;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class GqgetCustInfo extends DataEntity<GqgetCustInfo> {

	private static final long serialVersionUID = 1L;
	private String cust_id;
	private String cust_type;
	private String phone_no;
	private String bankcard_no;
	private String bank_no;
	private String bank_office;
	private String cont_distinct;
	private String cont_details;

	public String getCust_id() {
		return cust_id;
	}
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}
	public String getCust_type() {
		return cust_type;
	}
	public void setCust_type(String cust_type) {
		this.cust_type = cust_type;
	}
	public String getPhone_no() {
		return phone_no;
	}
	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}
	public String getBankcard_no() {
		return bankcard_no;
	}
	public void setBankcard_no(String bankcard_no) {
		this.bankcard_no = bankcard_no;
	}
	public String getBank_no() {
		return bank_no;
	}
	public void setBank_no(String bank_no) {
		this.bank_no = bank_no;
	}
	public String getBank_office() {
		return bank_office;
	}
	public void setBank_office(String bank_office) {
		this.bank_office = bank_office;
	}
	public String getCont_distinct() {
		return cont_distinct;
	}
	public void setCont_distinct(String cont_distinct) {
		this.cont_distinct = cont_distinct;
	}
	public String getCont_details() {
		return cont_details;
	}
	public void setCont_details(String cont_details) {
		this.cont_details = cont_details;
	}

}
