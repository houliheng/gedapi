package com.resoft.Accoutinterface.gqget.server.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

@SuppressWarnings("serial")
public class BankInfo extends DataEntity<BankInfo>{
	private String bankId;
	private String gqgetCustId;
	private String bankcardNo;
	private String bankName;
	private String bankOffice;
	private String bankArea;
	private String bankDetails;

	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public String getGqgetCustId() {
		return gqgetCustId;
	}
	public void setGqgetCustId(String gqgetCustId) {
		this.gqgetCustId = gqgetCustId;
	}
	public String getBankcardNo() {
		return bankcardNo;
	}
	public void setBankcardNo(String bankcardNo) {
		this.bankcardNo = bankcardNo;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankOffice() {
		return bankOffice;
	}
	public void setBankOffice(String bankOffice) {
		this.bankOffice = bankOffice;
	}
	public String getBankArea() {
		return bankArea;
	}
	public void setBankArea(String bankArea) {
		this.bankArea = bankArea;
	}
	public String getBankDetails() {
		return bankDetails;
	}
	public void setBankDetails(String bankDetails) {
		this.bankDetails = bankDetails;
	}


}
