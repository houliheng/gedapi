package com.resoft.Accoutinterface.rest.financialPlatform.entry;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility=Visibility.PUBLIC_ONLY)
public class AccEnterAccountList implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	
	private String serial_number;
	private String contract_id;
	private String contract_no;
	private String accounting_no;
	private String acc_no;
	private String mortgagee_acc_no;
	private String loan_platform;
	private String period;
	private List<AccSettleList> settle_list;
	
	public String getContract_no() {
		return contract_no;
	}
	public void setContract_no(String contract_no) {
		this.contract_no = contract_no;
	}
	protected String getContractNo() {
		return contract_no;
	}
	protected void setContractNo(String contract_no) {
		this.contract_no = contract_no;
	}
	public void setSettle_list(List<AccSettleList> settle_list) {
		this.settle_list = settle_list;
	}
	protected String getSerialNumber() {
		return serial_number;
	}
	protected void setSerialNumber(String serial_number) {
		this.serial_number = serial_number;
	}
	protected String getContractId() {
		return contract_id;
	}
	protected void setContractId(String contract_id) {
		this.contract_id = contract_id;
	}
	protected String getAccountingNo() {
		return accounting_no;
	}
	protected void setAccountingNo(String accounting_no) {
		this.accounting_no = accounting_no;
	}
	protected String getAccNo() {
		return acc_no;
	}
	protected void setAccNo(String acc_no) {
		this.acc_no = acc_no;
	}
	protected String getMortgageeAccNo() {
		return mortgagee_acc_no;
	}
	protected void setMortgageeAccNo(String mortgagee_acc_no) {
		this.mortgagee_acc_no = mortgagee_acc_no;
	}
	protected String getLoanPlatform() {
		return loan_platform;
	}
	protected void setLoanPlatform(String loan_platform) {
		this.loan_platform = loan_platform;
	}

	public String getLoan_platform() {
		return loan_platform;
	}
	public void setLoan_platform(String loan_platform) {
		this.loan_platform = loan_platform;
	}
	protected List<AccSettleList> getSettleList() {
		return settle_list;
	}
	protected void setSettleList(List<AccSettleList> settle_list) {
		this.settle_list = settle_list;
	}
	public String getSerial_number() {
		return serial_number;
	}
	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}
	public String getContract_id() {
		return contract_id;
	}
	public void setContract_id(String contract_id) {
		this.contract_id = contract_id;
	}
	public String getAccounting_no() {
		return accounting_no;
	}
	public void setAccounting_no(String accounting_no) {
		this.accounting_no = accounting_no;
	}
	public String getAcc_no() {
		return acc_no;
	}
	public void setAcc_no(String acc_no) {
		this.acc_no = acc_no;
	}
	public String getMortgagee_acc_no() {
		return mortgagee_acc_no;
	}
	public void setMortgagee_acc_no(String mortgagee_acc_no) {
		this.mortgagee_acc_no = mortgagee_acc_no;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public List<AccSettleList> getSettle_list() {
		return settle_list;
	}
}
