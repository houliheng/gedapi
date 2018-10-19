package com.resoft.outinterface.cn.com.experian.entry.request.body;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HouseInfo", propOrder = {
	    "houseES",
	    "houseUsedYear",
	    "houseCity",
	    "houseCityLevel",
	    "houseUse",
	    "isDispute",
	    "isHouseKeepPaper",
	    "houseMortgageType",
	    "isQueryFiles",
	    /*"loanDueDate",*/
	    "isTransfRec",
	    "isHouseOnly",
	    "pledge"
	})
public class HouseInfo {
	@XmlElement(name = "House_ES")
    protected String houseES;
	@XmlElement(name = "HouseUsedYear")
	protected String houseUsedYear;
	@XmlElement(name = "House_City")
	protected String houseCity;
	@XmlElement(name = "House_CityLevel")
	protected String houseCityLevel;
	@XmlElement(name = "House_Use")
	protected String houseUse;
	@XmlElement(name = "IsDispute")
	protected String isDispute;
	@XmlElement(name = "IsHouseKeepPaper")
	protected String isHouseKeepPaper;
	@XmlElement(name = "HouseMortgage_Type")
	protected String houseMortgageType;
	@XmlElement(name = "IsQueryFiles")
	protected String isQueryFiles;
	/*@XmlElement(name = "LoanDueDate")
	@XmlSchemaType(name = "date")
	protected XMLGregorianCalendar loanDueDate;*/
	@XmlElement(name = "IsTransfRec")
	protected String isTransfRec;
	@XmlElement(name = "IsHouseOnly")
	protected String isHouseOnly;
	@XmlElement(name = "House_Pledge")
	protected String pledge;
	public String getHouseES() {
		return houseES;
	}
	public void setHouseES(String houseES) {
		this.houseES = houseES;
	}
	public String getHouseUsedYear() {
		return houseUsedYear;
	}
	public void setHouseUsedYear(String houseUsedYear) {
		this.houseUsedYear = houseUsedYear;
	}
	public String getHouseCityLevel() {
		return houseCityLevel;
	}
	public void setHouseCityLevel(String houseCityLevel) {
		this.houseCityLevel = houseCityLevel;
	}
	public String getHouseUse() {
		return houseUse;
	}
	public void setHouseUse(String houseUse) {
		this.houseUse = houseUse;
	}
	public String getIsDispute() {
		return isDispute;
	}
	public void setIsDispute(String isDispute) {
		this.isDispute = isDispute;
	}
	public String getIsHouseKeepPaper() {
		return isHouseKeepPaper;
	}
	public void setIsHouseKeepPaper(String isHouseKeepPaper) {
		this.isHouseKeepPaper = isHouseKeepPaper;
	}

	public String getHouseMortgageType() {
		return houseMortgageType;
	}
	public void setHouseMortgageType(String houseMortgageType) {
		this.houseMortgageType = houseMortgageType;
	}
	public String getIsQueryFiles() {
		return isQueryFiles;
	}
	public void setIsQueryFiles(String isQueryFiles) {
		this.isQueryFiles = isQueryFiles;
	}
	public String getIsTransfRec() {
		return isTransfRec;
	}
	public void setIsTransfRec(String isTransfRec) {
		this.isTransfRec = isTransfRec;
	}
	public String getIsHouseOnly() {
		return isHouseOnly;
	}
	public void setIsHouseOnly(String isHouseOnly) {
		this.isHouseOnly = isHouseOnly;
	}
	public String getHouseCity() {
		return houseCity;
	}
	public void setHouseCity(String houseCity) {
		this.houseCity = houseCity;
	}
	public String getPledge() {
		return pledge;
	}
	public void setPledge(String pledge) {
		this.pledge = pledge;
	}
	
}
