package com.resoft.outinterface.rest.fh.server.entity.response;

import java.io.Serializable;

public class FhRiskControlServerResponse implements Serializable{

	
	
	
	private static final long serialVersionUID = 1L;
	
	private String applyNo;// 申请编号
	private String custId;// 客户Id
	private String code;// 报告文件名称
	private String msg;// 存放路径

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
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
