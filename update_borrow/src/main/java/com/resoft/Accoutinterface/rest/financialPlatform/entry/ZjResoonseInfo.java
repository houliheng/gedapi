package com.resoft.Accoutinterface.rest.financialPlatform.entry;

import java.io.Serializable;
import java.math.BigDecimal;

public class ZjResoonseInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String contractNo;
	private String from_cust_id;
	private Integer from_cust_type;
	private String to_cust_id;
	private Integer to_cust_type;
	private BigDecimal amount;
	private String remark;
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getFrom_cust_id() {
		return from_cust_id;
	}
	public void setFrom_cust_id(String from_cust_id) {
		this.from_cust_id = from_cust_id;
	}
	
	public String getTo_cust_id() {
		return to_cust_id;
	}
	public void setTo_cust_id(String to_cust_id) {
		this.to_cust_id = to_cust_id;
	}
	public Integer getTo_cust_type() {
		return to_cust_type;
	}
	public void setTo_cust_type(Integer to_cust_type) {
		this.to_cust_type = to_cust_type;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getFrom_cust_type() {
		return from_cust_type;
	}
	public void setFrom_cust_type(Integer from_cust_type) {
		this.from_cust_type = from_cust_type;
	}
	
	
	
}
