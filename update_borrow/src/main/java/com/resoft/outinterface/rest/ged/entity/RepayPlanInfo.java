package com.resoft.outinterface.rest.ged.entity;

import java.io.Serializable;
import java.util.List;

/**
* @author guoshaohua
* @version 2018年4月21日 下午2:47:00
* 
*/
public class RepayPlanInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private List<ContractRepayPlanDetail> data;
	private String stayTotalMoney;
	private String stayOverdueMoney;
	private String stayFineAmount;
	
	public String getStayTotalMoney() {
		return stayTotalMoney;
	}
	public void setStayTotalMoney(String stayTotalMoney) {
		this.stayTotalMoney = stayTotalMoney;
	}
	public String getStayOverdueMoney() {
		return stayOverdueMoney;
	}
	public void setStayOverdueMoney(String stayOverdueMoney) {
		this.stayOverdueMoney = stayOverdueMoney;
	}
	public String getStayFineAmount() {
		return stayFineAmount;
	}
	public void setStayFineAmount(String stayFineAmount) {
		this.stayFineAmount = stayFineAmount;
	}
	public List<ContractRepayPlanDetail> getData() {
		return data;
	}
	public void setData(List<ContractRepayPlanDetail> data) {
		this.data = data;
	}
	
	
}
