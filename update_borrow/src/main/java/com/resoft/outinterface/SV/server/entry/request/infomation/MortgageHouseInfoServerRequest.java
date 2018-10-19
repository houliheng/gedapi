package com.resoft.outinterface.SV.server.entry.request.infomation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class MortgageHouseInfoServerRequest {
	@XmlElement(name = "workId")
	private String workId; // 子工单编号
	@XmlElement(name = "houseYears")
	private String houseYears; // 房龄
	@XmlElement(name = "serviceLife")
	private String serviceLife;// 使用年限
	@XmlElement(name = "housePurchasePrice")
	private String housePurchasePrice;// 房产取得价格（元）
	@XmlElement(name = "isKeepPapers")
	private String isKeepPapers; // 是否留存相关证件
	

	public String getWorkId() {
		return workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}

	public String getHouseYears() {
		return houseYears;
	}

	public void setHouseYears(String houseYears) {
		this.houseYears = houseYears;
	}

	public String getServiceLife() {
		return serviceLife;
	}

	public void setServiceLife(String serviceLife) {
		this.serviceLife = serviceLife;
	}

	public String getHousePurchasePrice() {
		return housePurchasePrice;
	}

	public void setHousePurchasePrice(String housePurchasePrice) {
		this.housePurchasePrice = housePurchasePrice;
	}

	public String getIsKeepPapers() {
		return isKeepPapers;
	}

	public void setIsKeepPapers(String isKeepPapers) {
		this.isKeepPapers = isKeepPapers;
	}

}
