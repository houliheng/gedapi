package com.resoft.credit.gqgetAssertHouse.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 冠E通房屋资产信息表Entity
 * @author wanghong
 * @version 2016-10-13
 */
public class GqgetAssetHouse extends DataEntity<GqgetAssetHouse> {
	
	private static final long serialVersionUID = 1L;
	private String applyNo;		// apply_no
	private String contDetails;		// cont_details
	private String buildingArea;		// building_area
	private String evaluatePrice;		// evaluate_price
	private String marketPrice;		// market_price
	
	public GqgetAssetHouse() {
		super();
	}

	public GqgetAssetHouse(String id){
		super(id);
	}

	@Length(min=0, max=32, message="apply_no长度必须介于 0 和 32 之间")
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
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