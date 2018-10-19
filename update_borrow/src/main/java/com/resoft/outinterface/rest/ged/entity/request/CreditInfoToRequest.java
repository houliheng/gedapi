package com.resoft.outinterface.rest.ged.entity.request;

import com.resoft.outinterface.rest.ged.entity.GqgetAssetCarInfo;
import com.resoft.outinterface.rest.ged.entity.GqgetAssetHouseInfo;
import com.resoft.outinterface.rest.ged.entity.GuanETInfo;
import com.resoft.outinterface.rest.ged.entity.LoanBankInfo;
import java.io.Serializable;
import java.util.List;


/**
* @author guoshaohua
* @version 2018年4月26日 下午12:59:08
* 
*/
public class CreditInfoToRequest implements Serializable{
	private static final long serialVersionUID = 1L;
	private String orderNo;
	private String orderSubNo;
	private GuanETInfo guanETInfo;
	private List<GqgetAssetCarInfo> getAssetCardList;
	private List<GqgetAssetHouseInfo> gqgetHouseList;
	private LoanBankInfo LoanBankInfo;
	private String code;
	public GuanETInfo getGuanETInfo() {
		return guanETInfo;
	}
	public void setGuanETInfo(GuanETInfo guanETInfo) {
		this.guanETInfo = guanETInfo;
	}
	public List<GqgetAssetCarInfo> getGetAssetCardList() {
		return getAssetCardList;
	}
	public void setGetAssetCardList(List<GqgetAssetCarInfo> getAssetCardList) {
		this.getAssetCardList = getAssetCardList;
	}
	public List<GqgetAssetHouseInfo> getGqgetHouseList() {
		return gqgetHouseList;
	}
	public void setGqgetHouseList(List<GqgetAssetHouseInfo> gqgetHouseList) {
		this.gqgetHouseList = gqgetHouseList;
	}
	public LoanBankInfo getLoanBankInfo() {
		return LoanBankInfo;
	}
	public void setLoanBankInfo(LoanBankInfo loanBankInfo) {
		LoanBankInfo = loanBankInfo;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOrderSubNo() {
		return orderSubNo;
	}
	public void setOrderSubNo(String orderSubNo) {
		this.orderSubNo = orderSubNo;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
	
}
