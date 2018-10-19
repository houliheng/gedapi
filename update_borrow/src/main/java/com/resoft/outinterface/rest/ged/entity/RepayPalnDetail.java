package com.resoft.outinterface.rest.ged.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

/**
* @author guoshaohua
* @version 2018年4月20日 上午11:47:19
* 
*/
@JsonAutoDetect(fieldVisibility = Visibility.PUBLIC_ONLY)
public class RepayPalnDetail implements Serializable{
	private static final Long seriaVerserialVersionUIDsion = 1L;
	private String periodNum;//期数
	private String stayMoney;//待还金额
	private String deductDate;//还款日期
	private String repayStatus;//应还金额
	private String overdueMoney;//逾期违约金
	private String fineMoney;//罚息
	private String repayPrincipal;//应还本息
	private String manageFee;//管理费
	private String serviceFee;//服务费
	private String discountStayMoney;//减去贴息后的待还金额
	@JsonIgnore
	private String repayAmount;//应还金额
	@JsonIgnore
	private String factRepayAmount;//实还金额
	public String getPeriodNum() {
		return periodNum;
	}
	public void setPeriodNum(String periodNum) {
		this.periodNum = periodNum;
	}
	public String getStayMoney() {
		return stayMoney;
	}
	public void setStayMoney(String stayMoney) {
		this.stayMoney = stayMoney;
	}
	
	public String getDeductDate() {
		return deductDate;
	}
	public void setDeductDate(String deductDate) {
		this.deductDate = deductDate;
	}
	public String getRepayStatus() {
		return repayStatus;
	}
	public void setRepayStatus(String repayStatus) {
		this.repayStatus = repayStatus;
	}
	public String getOverdueMoney() {
		return overdueMoney;
	}
	public void setOverdueMoney(String overdueMoney) {
		this.overdueMoney = overdueMoney;
	}
	public String getFineMoney() {
		return fineMoney;
	}
	public void setFineMoney(String fineMoney) {
		this.fineMoney = fineMoney;
	}
	public String getRepayPrincipal() {
		return repayPrincipal;
	}
	public void setRepayPrincipal(String repayPrincipal) {
		this.repayPrincipal = repayPrincipal;
	}
	public String getManageFee() {
		return manageFee;
	}
	public void setManageFee(String manageFee) {
		this.manageFee = manageFee;
	}
	public String getServiceFee() {
		return serviceFee;
	}
	public void setServiceFee(String serviceFee) {
		this.serviceFee = serviceFee;
	}
	public String getDiscountStayMoney() {
		return discountStayMoney;
	}
	public void setDiscountStayMoney(String discountStayMoney) {
		this.discountStayMoney = discountStayMoney;
	}
	public String getRepayAmount() {
		return repayAmount;
	}
	public void setRepayAmount(String repayAmount) {
		this.repayAmount = repayAmount;
	}
	public String getFactRepayAmount() {
		return factRepayAmount;
	}
	public void setFactRepayAmount(String factRepayAmount) {
		this.factRepayAmount = factRepayAmount;
	}
	
	
	
	
}
