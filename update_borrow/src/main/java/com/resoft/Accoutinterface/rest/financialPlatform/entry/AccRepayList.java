package com.resoft.Accoutinterface.rest.financialPlatform.entry;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
@JsonAutoDetect(fieldVisibility=Visibility.PUBLIC_ONLY)
public class AccRepayList implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	
	private String serial_number;
	private String contract_id;
	private String contract_no;
	private String acc_no;
	private BigDecimal amt;
	private String remark;

	private String mid_cust_id; //中间人客户id
	private String withHold_type;//代扣类型字段

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
	public String getMid_cust_id() {
		return mid_cust_id;
	}
	public void setMid_cust_id(String mid_cust_id) {
		this.mid_cust_id = mid_cust_id;
	}
	public String getWithHold_type() {
		return withHold_type;
	}
	public void setWithHold_type(String withHold_type) {
		this.withHold_type = withHold_type;
	}

	protected String getMidCustId() {
		return mid_cust_id;
	}
	protected void setMidCustId(String mid_cust_id) {
		this.mid_cust_id = mid_cust_id;
	}
	protected String getWithHoldType() {
		return withHold_type;
	}
	protected void setWithHoldType(String withHold_type) {
		this.withHold_type = withHold_type;
	}
	@Override
	public String toString() {
		return "AccRepayList [serial_number=" + serial_number + ", contract_id=" + contract_id + ", contract_no="
				+ contract_no + ", acc_no=" + acc_no + ", amt=" + amt + ", remark=" + remark + ", mid_cust_id="
				+ mid_cust_id + ", withHold_type=" + withHold_type + "]";
	}

}
