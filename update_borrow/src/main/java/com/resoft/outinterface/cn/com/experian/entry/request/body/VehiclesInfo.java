package com.resoft.outinterface.cn.com.experian.entry.request.body;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class VehiclesInfo {
	@XmlElement(name = "Vehicle", required = true)
    protected List<VehicleInfo> vehicleInfo;

	public List<VehicleInfo> getVehicleInfo() {
		return vehicleInfo;
	}

	public void setVehicleInfo(List<VehicleInfo> vehicleInfo) {
		this.vehicleInfo = vehicleInfo;
	}
	
	
}
