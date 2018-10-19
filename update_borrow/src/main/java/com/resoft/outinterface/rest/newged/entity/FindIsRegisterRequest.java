package com.resoft.outinterface.rest.newged.entity;
/**
 * @description:
 * @Author:jiangmenglun
 * @E-mail:
 * @version:2018年1月25日 下午3:45:38
 */
public class FindIsRegisterRequest {
	private String type;//企业证件类型
	private String code;//企业证件编码
	private String mobile;// 手机号
	private String userRole;//0借款人(需要社会统一代码)1自有担保人2自有担保企业3合作企业     
	
	public FindIsRegisterRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public FindIsRegisterRequest(String type, String code) {
		super();
		this.type = type;
		this.code = code;
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
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	
}
