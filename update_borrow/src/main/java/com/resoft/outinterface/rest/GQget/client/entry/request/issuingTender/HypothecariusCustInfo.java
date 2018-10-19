package com.resoft.outinterface.rest.GQget.client.entry.request.issuingTender;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

/**
 *担保客户信息
 */
@JsonAutoDetect(fieldVisibility=Visibility.PUBLIC_ONLY)
public class HypothecariusCustInfo {
	private String moblie_phone;
	private String cust_name;
	private String cert_no;
	private int sex;
	private String birth_date;
	private int marital_status;
	private String education;
	private String bank_no;
	private String bank_long_name;
	private String city_code;
	private String bank_code;
	private String industry;
	
	public String getBank_no() {
		return bank_no;
	}
	public void setBank_no(String bank_no) {
		this.bank_no = bank_no;
	}
	public String getBank_long_name() {
		return bank_long_name;
	}
	public void setBank_long_name(String bank_long_name) {
		this.bank_long_name = bank_long_name;
	}
	public String getCity_code() {
		return city_code;
	}
	public void setCity_code(String city_code) {
		this.city_code = city_code;
	}
	public String getBank_code() {
		return bank_code;
	}
	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}
	protected String getBankNo() {
		return bank_no;
	}
	protected void setBankNo(String bank_no) {
		this.bank_no = bank_no;
	}
	protected String getBankLongName() {
		return bank_long_name;
	}
	protected void setBankLongName(String bank_long_name) {
		this.bank_long_name = bank_long_name;
	}
	protected String getCityCode() {
		return city_code;
	}
	protected void setCityCode(String city_code) {
		this.city_code = city_code;
	}
	protected String getBankCode() {
		return bank_code;
	}
	protected void setBankCode(String bank_code) {
		this.bank_code = bank_code;
	}
	protected String getMobliePhone() {
		return moblie_phone;
	}
	protected void setMobliePhone(String moblie_phone) {
		this.moblie_phone = moblie_phone;
	}
	protected String getCustName() {
		return cust_name;
	}
	protected void setCustName(String cust_name) {
		this.cust_name = cust_name;
	}
	protected String getCertNo() {
		return cert_no;
	}
	protected void setCertNo(String cert_no) {
		this.cert_no = cert_no;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	protected String getBirthDate() {
		return birth_date;
	}
	protected void setBirthDate(String birth_date) {
		this.birth_date = birth_date;
	}
	protected int getMaritalStatus() {
		return marital_status;
	}
	protected void setMaritalStatus(int marital_status) {
		this.marital_status = marital_status;
	}
	protected String getEducation() {
		return education;
	}
	protected void setEducation(String education) {
		this.education = education;
	}
	protected String getIndustry() {
		return industry;
	}
	protected void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getMoblie_phone() {
		return moblie_phone;
	}
	public void setMoblie_phone(String moblie_phone) {
		this.moblie_phone = moblie_phone;
	}
	public String getCust_name() {
		return cust_name;
	}
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
	public String getCert_no() {
		return cert_no;
	}
	public void setCert_no(String cert_no) {
		this.cert_no = cert_no;
	}
	public String getBirth_date() {
		return birth_date;
	}
	public void setBirth_date(String birth_date) {
		this.birth_date = birth_date;
	}
	public int getMarital_status() {
		return marital_status;
	}
	public void setMarital_status(int marital_status) {
		this.marital_status = marital_status;
	}
}
