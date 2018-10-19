package com.resoft.outinterface.SV.server.entry.request.infomation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class MortgageCarEvaluateInfoServerRequest {
	@XmlElement(name = "workId")
	private String workId; // 子工单编号
	@XmlElement(name = "vehicleNo")
	private String vehicleNo; // 车牌号码
	@XmlElement(name = "engineNum")
	private String engineNum; // 发动机号
	@XmlElement(name = "vehicleShelfNo")
	private String vehicleShelfNo; // 车架号
	@XmlElement(name = "netPurchasePrice")
	private String netPurchasePrice; // 净车购买价格（元）
	@XmlElement(name = "travelKms")
	private String travelKms; // 行驶公里数
	@XmlElement(name = "carStatus")
	private String carStatus; // 车辆状态
	

	public String getWorkId() {
		return workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getEngineNum() {
		return engineNum;
	}

	public void setEngineNum(String engineNum) {
		this.engineNum = engineNum;
	}

	public String getVehicleShelfNo() {
		return vehicleShelfNo;
	}

	public void setVehicleShelfNo(String vehicleShelfNo) {
		this.vehicleShelfNo = vehicleShelfNo;
	}

	public String getNetPurchasePrice() {
		return netPurchasePrice;
	}

	public void setNetPurchasePrice(String netPurchasePrice) {
		this.netPurchasePrice = netPurchasePrice;
	}

	public String getTravelKms() {
		return travelKms;
	}

	public void setTravelKms(String travelKms) {
		this.travelKms = travelKms;
	}

	public String getCarStatus() {
		return carStatus;
	}

	public void setCarStatus(String carStatus) {
		this.carStatus = carStatus;
	}

}
