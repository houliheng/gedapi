package com.resoft.Accoutinterface.rest.financialPlatform.entry;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
@JsonAutoDetect(fieldVisibility=Visibility.PUBLIC_ONLY)
public class AccRepaymentWithholdingList implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	
	private String serial_number;
	private String contract_id;
	private String contract_no;
	private String acc_no;
	private BigDecimal amt;
	private BigDecimal real_repay_amt;
	private String remark;
	private String complete_time;
	private String accounting_no;
	public String getContract_no() {
		return contract_no;
	}
	public void setContract_no(String contract_no) {
		this.contract_no = contract_no;
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
	protected String getContractNo() {
		return contract_no;
	}
	protected void setContractNo(String contract_no) {
		this.contract_no = contract_no;
	}
	public String getAcc_no() {
		return acc_no;
	}
	public void setAcc_no(String acc_no) {
		this.acc_no = acc_no;
	}
	public BigDecimal getReal_repay_amt() {
		return real_repay_amt;
	}
	public void setReal_repay_amt(BigDecimal real_repay_amt) {
		this.real_repay_amt = real_repay_amt;
	}
	public String getComplete_time() {
		return complete_time;
	}
	public void setComplete_time(String complete_time) {
		this.complete_time = complete_time;
	}
	public String getAccounting_no() {
		return accounting_no;
	}
	public void setAccounting_no(String accounting_no) {
		this.accounting_no = accounting_no;
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
	protected String getAccNo() {
		return acc_no;
	}
	protected void setAccNo(String acc_no) {
		this.acc_no = acc_no;
	}
	public BigDecimal getAmt() {
		return amt;
	}
	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}
	protected BigDecimal getRealRepayAmt() {
		return real_repay_amt;
	}
	protected void setRealRepayAmt(BigDecimal real_repay_amt) {
		this.real_repay_amt = real_repay_amt;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	protected String getCompleteTime() {
		return complete_time;
	}
	protected void setCompleteTime(String complete_time) {
		this.complete_time = complete_time;
	}
	protected String getAccountingNo() {
		return accounting_no;
	}
	protected void setAccountingNo(String accounting_no) {
		this.accounting_no = accounting_no;
	}
}
