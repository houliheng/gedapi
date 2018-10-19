package com.resoft.outinterface.rest.ged.entity.response;

import com.resoft.outinterface.rest.ged.entity.RepayPlanInfo;
import java.io.Serializable;
import java.util.List;

/**
* @author guoshaohua
* @version 2018年4月20日 上午11:29:05
* 
*/
public class RepayPlanStayDetailResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	private String code;
	private String message;
	private String exception;
	private RepayPlanInfo repayPlanInfo;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	public RepayPlanInfo getRepayPlanInfo() {
		return repayPlanInfo;
	}
	public void setRepayPlanInfo(RepayPlanInfo repayPlanInfo) {
		this.repayPlanInfo = repayPlanInfo;
	}	
}
