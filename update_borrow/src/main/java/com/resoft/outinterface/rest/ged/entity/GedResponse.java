package com.resoft.outinterface.rest.ged.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class GedResponse  implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	private String applyId;
	private Map<String,Object> mapValue = new HashMap<String,Object>();
	private String code;
	private String msg;

	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
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
	public Map<String, Object> getMapValue() {
		return mapValue;
	}
	public void setMapValue(Map<String, Object> mapValue) {
		this.mapValue = mapValue;
	}

}
