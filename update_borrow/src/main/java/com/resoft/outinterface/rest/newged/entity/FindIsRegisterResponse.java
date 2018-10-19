package com.resoft.outinterface.rest.newged.entity;


/**
 * @description:
 * @Author:jiangmenglun
 * @E-mail:
 * @version:2018年1月25日 下午3:52:51
 * @param <T>
 */
public class FindIsRegisterResponse<T> {
	private String exception;
	private ReturnData data;
	private String code;
	
	private String httpStatus;
	private String path;
	private String timestamp;
	private String reason;
	

	
	public FindIsRegisterResponse() {
		super();
	}
	
	public FindIsRegisterResponse(String exception, ReturnData data, String code, String httpStatus, String path,
			String timestamp, String reason) {
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

	public ReturnData getData() {
		return data;
	}

	public void setData(ReturnData data) {
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
