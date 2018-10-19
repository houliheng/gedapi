package com.resoft.outinterface.rest.SVFinancialToThemis.entity;

import java.io.Serializable;
/*description:财报导入返还给sv接口的实体类
 * */
public class SVFinancialToThemisResponse implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private boolean is_success;
	private String resq_code;
	private String resq_msg;
	
	public SVFinancialToThemisResponse(boolean is_success, String resq_code, String resq_msg) {
		super();
		this.is_success = is_success;
		this.resq_code = resq_code;
		this.resq_msg = resq_msg;
	}
	public SVFinancialToThemisResponse() {
		super();
	}
	public boolean isIs_success() {
		return is_success;
	}
	public void setIs_success(boolean is_success) {
		this.is_success = is_success;
	}
	public String getResq_code() {
		return resq_code;
	}
	public void setResq_code(String resq_code) {
		this.resq_code = resq_code;
	}
	public String getResq_msg() {
		return resq_msg;
	}
	public void setResq_msg(String resq_msg) {
		this.resq_msg = resq_msg;
	}
	
}
