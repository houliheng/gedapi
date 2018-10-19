package com.resoft.outinterface.cn.com.experian.entry.request.body;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class HousesInfo {
	 @XmlElement(name = "House", required = true)
	    protected List<HouseInfo> houseInfo;

	public List<HouseInfo> getHouseInfo() {
		return houseInfo;
	}

	public void setHouseInfo(List<HouseInfo> houseInfo) {
		this.houseInfo = houseInfo;
	}
	 
	 
	 
}
