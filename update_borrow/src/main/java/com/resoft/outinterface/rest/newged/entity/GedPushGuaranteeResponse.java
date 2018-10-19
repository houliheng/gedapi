package com.resoft.outinterface.rest.newged.entity;

public class GedPushGuaranteeResponse <T>{
	private String exception;
	private T data;
	private int code;
	public GedPushGuaranteeResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GedPushGuaranteeResponse(String exception, T data, int code) {
		super();
		this.exception = exception;
		this.data = data;
		this.code = code;
	}
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
}
