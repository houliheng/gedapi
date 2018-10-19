package com.resoft.Accoutinterface.gqget.server.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GqgetResponse implements Serializable {
	private String code;
	private String msg;
	public GqgetResponse( String code, String msg) {
		this.code = code;
		this.msg = msg;
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
