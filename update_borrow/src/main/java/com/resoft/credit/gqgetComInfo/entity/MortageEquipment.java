package com.resoft.credit.gqgetComInfo.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class MortageEquipment extends DataEntity<MortageEquipment>{

	private static final long serialVersionUID = 1L;
	private String applyNo;	// 申请编号
	private String model; //型号
	private String buyingPrice; //购买价格
	private String valueOfAssessment; //估值
	private String maketValue;  //市值
	
	public String getApplyNo() {
		return applyNo;
	}
	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getBuyingPrice() {
		return buyingPrice;
	}
	public void setBuyingPrice(String buyingPrice) {
		this.buyingPrice = buyingPrice;
	}
	public String getValueOfAssessment() {
		return valueOfAssessment;
	}
	public void setValueOfAssessment(String valueOfAssessment) {
		this.valueOfAssessment = valueOfAssessment;
	}
	public String getMaketValue() {
		return maketValue;
	}
	public void setMaketValue(String maketValue) {
		this.maketValue = maketValue;
	}
	
}
