package com.resoft.outinterface.rest.newged.entity;
/**
 * @description:
 * @Author:jiangmenglun
 * @E-mail:
 * @version:2018年1月29日 下午3:58:50
 */
public class ReturnData {
	
	private String type;
	private String code;
	private String mobile;
	private String message;
	private String resultCode;
	private String guaranteeAmount ;
	private String userRole;
	private String registerType;
	

	public ReturnData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getGuaranteeAmount() {
		return guaranteeAmount;
	}
	public void setGuaranteeAmount(String guaranteeAmount) {
		this.guaranteeAmount = guaranteeAmount;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public String getRegisterType() {
		return registerType;
	}
	public void setRegisterType(String registerType) {
		this.registerType = registerType;
	}
	
}
