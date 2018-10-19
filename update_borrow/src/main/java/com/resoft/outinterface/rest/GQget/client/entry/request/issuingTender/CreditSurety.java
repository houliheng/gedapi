package com.resoft.outinterface.rest.GQget.client.entry.request.issuingTender;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility=Visibility.PUBLIC_ONLY)
public class CreditSurety {
	private String firm_credit_name;//担保人姓名
	private String relation;
	private String job_content;
	private String firm_credit_address;
	private int gender;
	private String identity_num;
	private String marital_desc;
	private String house_predict_amount;
	private String house_desc;
	
	public String getFirm_credit_address() {
		return firm_credit_address;
	}
	public void setFirm_credit_address(String firm_credit_address) {
		this.firm_credit_address = firm_credit_address;
	}
	public String getFirm_credit_name() {
		return firm_credit_name;
	}
	public void setFirm_credit_name(String firm_credit_name) {
		this.firm_credit_name = firm_credit_name;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public String getJob_content() {
		return job_content;
	}
	public void setJob_content(String job_content) {
		this.job_content = job_content;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public String getIdentity_num() {
		return identity_num;
	}
	public void setIdentity_num(String identity_num) {
		this.identity_num = identity_num;
	}
	public String getMarital_desc() {
		return marital_desc;
	}
	public void setMarital_desc(String marital_desc) {
		this.marital_desc = marital_desc;
	}
	public String getHouse_predict_amount() {
		return house_predict_amount;
	}
	public void setHouse_predict_amount(String house_predict_amount) {
		this.house_predict_amount = house_predict_amount;
	}
	public String getHouse_desc() {
		return house_desc;
	}
	public void setHouse_desc(String house_desc) {
		this.house_desc = house_desc;
	}
	protected String getFirmCreditAddress() {
		return firm_credit_address;
	}
	protected void setFirmCreditAddress(String firm_credit_address) {
		this.firm_credit_address = firm_credit_address;
	}
	protected String getFirmCreditName() {
		return firm_credit_name;
	}
	protected void setFirmCreditName(String firm_credit_name) {
		this.firm_credit_name = firm_credit_name;
	}
	
	protected String getJobContent() {
		return job_content;
	}
	protected void setJobContent(String job_content) {
		this.job_content = job_content;
	}
	
	protected String getIdentityNum() {
		return identity_num;
	}
	protected void setIdentityNum(String identity_num) {
		this.identity_num = identity_num;
	}
	protected String getMaritalDesc() {
		return marital_desc;
	}
	protected void setMaritalDesc(String marital_desc) {
		this.marital_desc = marital_desc;
	}
	protected String getHousePredictAmount() {
		return house_predict_amount;
	}
	protected void setHousePredictAmount(String house_predict_amount) {
		this.house_predict_amount = house_predict_amount;
	}
	protected String getHouseDesc() {
		return house_desc;
	}
	protected void setHouseDesc(String house_desc) {
		this.house_desc = house_desc;
	}
}
