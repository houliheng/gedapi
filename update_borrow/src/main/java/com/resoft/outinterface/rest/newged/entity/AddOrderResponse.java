package com.resoft.outinterface.rest.newged.entity;
/**
 * @description:
 * @Author:jiangmenglun
 * @E-mail:
 * @version:2018年1月29日 下午5:27:13
 */
public class AddOrderResponse <T>{
	private String exception;
	private T data;
	private String code;
	
	private String httpStatus;
	private String path;
	private String timestamp;
	private String reason;
	public AddOrderResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AddOrderResponse(String exception, T data, String code, String httpStatus, String path, String timestamp,
			String reason) {
		super();
		this.exception = exception;
		this.data = data;
		this.code = code;
		this.httpStatus = httpStatus;
		this.path = path;
		this.timestamp = timestamp;
		this.reason = reason;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getHttpStatus() {
		return httpStatus;
	}
	public void setHttpStatus(String httpStatus) {
		this.httpStatus = httpStatus;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
}
