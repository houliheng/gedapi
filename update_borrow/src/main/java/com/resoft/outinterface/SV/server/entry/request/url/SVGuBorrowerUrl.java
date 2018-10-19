package com.resoft.outinterface.SV.server.entry.request.url;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
@XmlAccessorType(XmlAccessType.FIELD)
public class SVGuBorrowerUrl {
	@XmlElement(required=true,name="IDENTIFY")
	private List<SVRequestBaseData> identify;
	@XmlElement(required=true,name="CREDIT_LINE")
	private List<SVRequestBaseData> creditLine;
	@XmlElement(required=true,name="HOUSE_INFO")
	private List<SVRequestBaseData> houseInfo;
	@XmlElement(required=true,name="CAR_INFO")
	private List<SVRequestBaseData> carInfo;
	@XmlElement(required=true,name="OTHER_ASSETS")
	private List<SVRequestBaseData> otherAssets;
	@XmlElement(required=true,name="LIVING_HOUSE_INFO")
	private List<SVRequestBaseData> livingHouseInfo;
	@XmlElement(required=true,name="PIC_NET_CHECK")
	private List<SVRequestBaseData> netCheck;
	@XmlElement(required=true,name="WORK_INFO")
	private List<SVRequestBaseData> workInfo;
	@XmlElement(required=true,name="HOME_INTERVIEW")
	private List<SVRequestBaseData> homeInterview;
	@XmlElement(required=true,name="OTHER_INFO")
	private List<SVRequestBaseData> otherInfo;
	public List<SVRequestBaseData> getIdentify() {
		return identify;
	}
	public void setIdentify(List<SVRequestBaseData> identify) {
		this.identify = identify;
	}
	public List<SVRequestBaseData> getCreditLine() {
		return creditLine;
	}
	public void setCreditLine(List<SVRequestBaseData> creditLine) {
		this.creditLine = creditLine;
	}
	public List<SVRequestBaseData> getHouseInfo() {
		return houseInfo;
	}
	public void setHouseInfo(List<SVRequestBaseData> houseInfo) {
		this.houseInfo = houseInfo;
	}
	public List<SVRequestBaseData> getCarInfo() {
		return carInfo;
	}
	public void setCarInfo(List<SVRequestBaseData> carInfo) {
		this.carInfo = carInfo;
	}
	public List<SVRequestBaseData> getOtherAssets() {
		return otherAssets;
	}
	public void setOtherAssets(List<SVRequestBaseData> otherAssets) {
		this.otherAssets = otherAssets;
	}
	public List<SVRequestBaseData> getLivingHouseInfo() {
		return livingHouseInfo;
	}
	public void setLivingHouseInfo(List<SVRequestBaseData> livingHouseInfo) {
		this.livingHouseInfo = livingHouseInfo;
	}
	public List<SVRequestBaseData> getNetCheck() {
		return netCheck;
	}
	public void setNetCheck(List<SVRequestBaseData> netCheck) {
		this.netCheck = netCheck;
	}
	public List<SVRequestBaseData> getWorkInfo() {
		return workInfo;
	}
	public void setWorkInfo(List<SVRequestBaseData> workInfo) {
		this.workInfo = workInfo;
	}
	public List<SVRequestBaseData> getHomeInterview() {
		return homeInterview;
	}
	public void setHomeInterview(List<SVRequestBaseData> homeInterview) {
		this.homeInterview = homeInterview;
	}
	public List<SVRequestBaseData> getOtherInfo() {
		return otherInfo;
	}
	public void setOtherInfo(List<SVRequestBaseData> otherInfo) {
		this.otherInfo = otherInfo;
	}
}
