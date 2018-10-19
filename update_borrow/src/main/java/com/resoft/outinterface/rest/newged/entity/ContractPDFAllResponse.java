package com.resoft.outinterface.rest.newged.entity;

import java.util.List;

public class ContractPDFAllResponse {
	private List<ContractPDFInstanceResponse> data;
	private String code;
	private String msg;
	
	
	
	public ContractPDFAllResponse(List<ContractPDFInstanceResponse> data, String code) {
		super();
		this.data = data;
		this.code = code;
	}

	public ContractPDFAllResponse() {
		super();
	}

	public List<ContractPDFInstanceResponse> getData() {
		return data;
	}

	public void setData(List<ContractPDFInstanceResponse> data) {
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	
	
}
