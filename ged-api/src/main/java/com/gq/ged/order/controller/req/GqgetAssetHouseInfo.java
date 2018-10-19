package com.gq.ged.order.controller.req;

import java.io.Serializable;

/**
 * 冠E通房屋资产信息表
 * Created by wrh on 2018/4/26.
 */
public class GqgetAssetHouseInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String contDetails;		// 地址
	private String buildingArea;		// 面积
	private String evaluatePrice;		// 估值
	private String marketPrice;		// 市值

	public String getContDetails() {
		return contDetails;
	}

	public void setContDetails(String contDetails) {
		this.contDetails = contDetails;
	}

	public String getBuildingArea() {
		return buildingArea;
	}

	public void setBuildingArea(String buildingArea) {
		this.buildingArea = buildingArea;
	}

	public String getEvaluatePrice() {
		return evaluatePrice;
	}

	public void setEvaluatePrice(String evaluatePrice) {
		this.evaluatePrice = evaluatePrice;
	}

	public String getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}
}