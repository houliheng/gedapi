package com.resoft.outinterface.rest.ged.entity;

import java.io.Serializable;

public class GedDrawInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String orderNo;
	private String status;

	public GedDrawInfo() {}

	public GedDrawInfo(String orderNo,String status) {
		this.orderNo = orderNo;
		this.status = status;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
