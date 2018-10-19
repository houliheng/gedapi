package com.resoft.outinterface.rest.ged.entity;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

/**
 * 冠E通房屋资产信息表Entity
 * @author wanghong
 * @version 2016-10-13
 */
public class GqgetAssetHouseInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String contDetails;		// 地址
	private String buildingArea;		// 面积
	private String evaluatePrice;		// 估值
	private String marketPrice;		// 市值
	
	public GqgetAssetHouseInfo() {
		super();
	}

	@Length(min=0, max=500, message="cont_details长度必须介于 0 和 500 之间")
	public String getContDetails() {
		return contDetails;
	}

	public void setContDetails(String contDetails) {
		this.contDetails = contDetails;
	}
	
	@Length(min=0, max=50, message="building_area长度必须介于 0 和 50 之间")
	public String getBuildingArea() {
		return buildingArea;
	}

	public void setBuildingArea(String buildingArea) {
		this.buildingArea = buildingArea;
	}
	
	@Length(min=0, max=20, message="evaluate_price长度必须介于 0 和 20 之间")
	public String getEvaluatePrice() {
		return evaluatePrice;
	}

	public void setEvaluatePrice(String evaluatePrice) {
		this.evaluatePrice = evaluatePrice;
	}
	
	@Length(min=0, max=20, message="market_price长度必须介于 0 和 20 之间")
	public String getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}
	
}