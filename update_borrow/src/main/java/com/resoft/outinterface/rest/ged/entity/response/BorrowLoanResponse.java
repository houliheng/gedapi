package com.resoft.outinterface.rest.ged.entity.response;

import java.io.Serializable;
import java.util.List;

/**
* @author guoshaohua
* @version 2018年4月19日 下午4:49:14
* 
*/
public class BorrowLoanResponse implements Serializable{
	private String code;
	private String exception;
	private String message;
	private String count;
	private String loanTotalMoney;
	private String contractStayMoney;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getLoanTotalMoney() {
		return loanTotalMoney;
	}
	public void setLoanTotalMoney(String loanTotalMoney) {
		this.loanTotalMoney = loanTotalMoney;
	}
	public String getContractStayMoney() {
		return contractStayMoney;
	}
	public void setContractStayMoney(String contractStayMoney) {
		this.contractStayMoney = contractStayMoney;
	}
	
	
	
}