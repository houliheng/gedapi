package com.resoft.outinterface.SV.server.entry.request.url;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class SVMortApplicationInfoUrl {
	@XmlElement(required=true,name="CAR_PROPERTY")
	private List<SVRequestBaseData> carProperty;
	@XmlElement(required=true,name="HOUSE_PROPERTY")
	private List<SVRequestBaseData> houseProperty;
	@XmlElement(required=true,name="MORT_INTERVIEW")
	private List<SVRequestBaseData> mortInterview;
	@XmlElement(required=true,name="MORT_BELONGING")
	private List<SVRequestBaseData> mortBelonging;
	@XmlElement(required=true,name="OTHER_MORT")
	private List<SVRequestBaseData> mortOther;
	public List<SVRequestBaseData> getCarProperty() {
		return carProperty;
	}
	public void setCarProperty(List<SVRequestBaseData> carProperty) {
		this.carProperty = carProperty;
	}
	public List<SVRequestBaseData> getHouseProperty() {
		return houseProperty;
	}
	public void setHouseProperty(List<SVRequestBaseData> houseProperty) {
		this.houseProperty = houseProperty;
	}
	public List<SVRequestBaseData> getMortInterview() {
		return mortInterview;
	}
	public void setMortInterview(List<SVRequestBaseData> mortInterview) {
		this.mortInterview = mortInterview;
	}
	public List<SVRequestBaseData> getMortBelonging() {
		return mortBelonging;
	}
	public void setMortBelonging(List<SVRequestBaseData> mortBelonging) {
		this.mortBelonging = mortBelonging;
	}
	public List<SVRequestBaseData> getMortOther() {
		return mortOther;
	}
	public void setMortOther(List<SVRequestBaseData> mortOther) {
		this.mortOther = mortOther;
	}
}
