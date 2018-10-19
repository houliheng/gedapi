package com.resoft.outinterface.rest.ged.entity.request;

import java.io.Serializable;

/**
* @author guoshaohua
* @version 2018年4月23日 下午3:49:08
* 
*/
public class ConfirmGuranteeRelationRequest implements Serializable{
	private static final Long SerialVersionUIDAdder = 1L;
	private String applyNo;
	private String flag;
	private String reason;
	private String socaliTotalNum;
	private String applySubNo;
	private String userRole;
	private String custId;
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getSocaliTotalNum() {
		return socaliTotalNum;
	}
	public void setSocaliTotalNum(String socaliTotalNum) {
		this.socaliTotalNum = socaliTotalNum;
	}
	
	
	public String getApplyNo() {
		return applyNo;
	}
	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	public String getApplySubNo() {
		return applySubNo;
	}
	public void setApplySubNo(String applySubNo) {
		this.applySubNo = applySubNo;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	
	
}
