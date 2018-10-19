package com.resoft.outinterface.rest.sendGET.entry;

import java.io.Serializable;

public class SendGETResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	private String result;
	private String msg;
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	
}
