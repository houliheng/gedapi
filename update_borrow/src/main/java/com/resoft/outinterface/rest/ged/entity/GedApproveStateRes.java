package com.resoft.outinterface.rest.ged.entity;

import java.io.Serializable;

public class GedApproveStateRes implements Serializable{

	
	private static final long serialVersionUID = 1L;
	

	private String code;
	private boolean success;
	private String msg;

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

}
