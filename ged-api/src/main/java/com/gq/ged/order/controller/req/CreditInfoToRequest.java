package com.gq.ged.order.controller.req;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wrh on 2018/4/26.
 */
public class CreditInfoToRequest implements Serializable{
	private static final long serialVersionUID = 1L;
	private String orderNo;
	private String orderSubNo;
	private GuanETInfo guanETInfo;
	private List<GqgetAssetCarInfo> getAssetCardList;
	private List<GqgetAssetHouseInfo> gqgetHouseList;

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
}
