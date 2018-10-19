package com.resoft.credit.gqgetAssetHouseUnion.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 联合授信冠e通房产资产Entity
 * @author lixing
 * @version 2016-12-26
 */
public class GqgetAssetHouseUnion extends DataEntity<GqgetAssetHouseUnion> {
	
	private static final long serialVersionUID = 1L;
	private String applyNo;		// 申请编号
	private String approveId;		// 批复ID
	private String contDetails;		// 地址
	private String buildingArea;		// 面积
	private String evaluatePrice;		// 估值
	private String marketPrice;		// 市值
	
	public GqgetAssetHouseUnion() {
		super();
	}

	public GqgetAssetHouseUnion(String id){
		super(id);
	}

	@Length(min=0, max=32, message="申请编号长度必须介于 0 和 32 之间")
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	
	@Length(min=1, max=32, message="批复ID长度必须介于 1 和 32 之间")
	public String getApproveId() {
		return approveId;
	}

	public void setApproveId(String approveId) {
		this.approveId = approveId;
	}
	
	@Length(min=0, max=500, message="地址长度必须介于 0 和 500 之间")
	public String getContDetails() {
		return contDetails;
	}

	public void setContDetails(String contDetails) {
		this.contDetails = contDetails;
	}
	
	@Length(min=0, max=50, message="面积长度必须介于 0 和 50 之间")
	public String getBuildingArea() {
		return buildingArea;
	}

	public void setBuildingArea(String buildingArea) {
		this.buildingArea = buildingArea;
	}
	
	@Length(min=0, max=20, message="估值长度必须介于 0 和 20 之间")
	public String getEvaluatePrice() {
		return evaluatePrice;
	}

	public void setEvaluatePrice(String evaluatePrice) {
		this.evaluatePrice = evaluatePrice;
	}
	
	@Length(min=0, max=20, message="市值长度必须介于 0 和 20 之间")
	public String getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}
	
}