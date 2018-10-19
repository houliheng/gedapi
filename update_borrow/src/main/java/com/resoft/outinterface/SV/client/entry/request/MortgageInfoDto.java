package com.resoft.outinterface.SV.client.entry.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


@XmlAccessorType(XmlAccessType.FIELD)
public class MortgageInfoDto {
	@XmlElement(required=true,name="MORTGAGE_TYPE")
	private String mortgageType;
	@XmlElement(name="MORTGAGE_Count")
	private String mortgageCount;
	@XmlElement(required=true,name="MORTGAGE_Info")
	private String mortgageInfo;
	public String getMortgageType() {
		return mortgageType;
	}
	public void setMortgageType(String mortgageType) {
		this.mortgageType = mortgageType;
	}
	public String getMortgageCount() {
		return mortgageCount;
	}
	public void setMortgageCount(String mortgageCount) {
		this.mortgageCount = mortgageCount;
	}
	public String getMortgageInfo() {
		return mortgageInfo;
	}
	public void setMortgageInfo(String mortgageInfo) {
		this.mortgageInfo = mortgageInfo;
	}
	@Override
	public String toString() {
		return "MortgageInfoDto [mortgageType=" + mortgageType
				+ ", mortgageCount=" + mortgageCount + ", mortgageInfo="
				+ mortgageInfo + "]";
	}
	
}
