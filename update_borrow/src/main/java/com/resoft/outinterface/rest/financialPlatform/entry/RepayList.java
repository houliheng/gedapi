package com.resoft.outinterface.rest.financialPlatform.entry;

import java.math.BigDecimal;

public class RepayList{
	private String serial_number;
	private String contract_id;
	private String contract_no;
	private String acc_no;
	private BigDecimal amt;
	private String remark;
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
	public String getContract_no() {
		return contract_no;
	}
	public void setContract_no(String contract_no) {
		this.contract_no = contract_no;
	}
	public String getAcc_no() {
		return acc_no;
	}
	public void setAcc_no(String acc_no) {
		this.acc_no = acc_no;
	}
	public String getContractNo() {
		return contract_no;
	}
	public void setContractNo(String contract_no) {
		this.contract_no = contract_no;
	}
	public String getSerialNumber() {
		return serial_number;
	}
	public void setSerialNumber(String serial_number) {
		this.serial_number = serial_number;
	}
	public String getContractId() {
		return contract_id;
	}
	public void setContractId(String contract_id) {
		this.contract_id = contract_id;
	}
	public String getAccNo() {
		return acc_no;
	}
	public void setAccNo(String acc_no) {
		this.acc_no = acc_no;
	}
	public BigDecimal getAmt() {
		return amt;
	}
	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "RepayList [serial_number=" + serial_number
				+ ", contract_id=" + contract_id + ", acc_no=" + acc_no
				+ ", amt=" + amt + ", remark=" + remark + "]";
	} 
}
