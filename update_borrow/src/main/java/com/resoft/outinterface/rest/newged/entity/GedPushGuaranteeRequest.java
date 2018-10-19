package com.resoft.outinterface.rest.newged.entity;

public class GedPushGuaranteeRequest {
	private String orderCode;	//子订单编号
	private String masterOrderCode;	//订单编号
	private String guarantorType;	//担保类型 	1自有担保人2自有担保企业3合作企业
	private String guarantMobile;	//担保人手机号
	private String type;	//企业证件类型
	private String code;	//企业证件号码
	private String guaranteeAmount; //担保额度
	private String borrowGuarantorId; //担保ID
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getMasterOrderCode() {
		return masterOrderCode;
	}
	public void setMasterOrderCode(String masterOrderCode) {
		this.masterOrderCode = masterOrderCode;
	}
	public String getGuarantorType() {
		return guarantorType;
	}
	public void setGuarantorType(String guarantorType) {
		this.guarantorType = guarantorType;
	}
	public String getGuarantMobile() {
		return guarantMobile;
	}
	public void setGuarantMobile(String guarantMobile) {
		this.guarantMobile = guarantMobile;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getGuaranteeAmount() {
		return guaranteeAmount;
	}
	public void setGuaranteeAmount(String guaranteeAmount) {
		this.guaranteeAmount = guaranteeAmount;
	}
	public String getBorrowGuarantorId() {
		return borrowGuarantorId;
	}
	public void setBorrowGuarantorId(String borrowGuarantorId) {
		this.borrowGuarantorId = borrowGuarantorId;
	}
	
}
