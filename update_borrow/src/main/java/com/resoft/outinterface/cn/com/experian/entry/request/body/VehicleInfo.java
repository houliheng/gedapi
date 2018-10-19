package com.resoft.outinterface.cn.com.experian.entry.request.body;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VehicleInfo", propOrder = {
	    "mortgageType",
	    "isCarKeepPaper",
	    "carValue",
	    "transferNum",
	    "carType",
	    "asFraud",
	    "carUsedYear",
	    "isProcedureCom",
	    "isCarstatusNormal",
	    "carQuality",
	    "dueDispos",
	    "pledge"
	})
public class VehicleInfo {
  @XmlElement(name = "Mortgage_Type")
  protected BigDecimal mortgageType;
  @XmlElement(name = "IsCarKeepPaper")
  protected BigDecimal isCarKeepPaper;
  @XmlElement(name = "CarValue")
  protected BigDecimal carValue;
  @XmlElement(name = "TransferNum")
  protected BigDecimal transferNum;
  @XmlElement(name = "CarType")
  protected BigDecimal carType;
  @XmlElement(name = "AS_Fraud")
  protected BigDecimal asFraud;
  @XmlElement(name = "CarUsedYear")
  protected BigDecimal carUsedYear;
  @XmlElement(name = "IsProcedureCom")
  protected BigDecimal isProcedureCom;
  @XmlElement(name = "IsCarstatusNormal")
  protected BigDecimal isCarstatusNormal;
  @XmlElement(name = "CarQuality")
  protected BigDecimal carQuality;
  @XmlElement(name = "DueDispos")
  protected BigDecimal dueDispos;
  @XmlElement(name = "Vehicle_Pledge")
  protected BigDecimal pledge;
public BigDecimal getMortgageType() {
	return mortgageType;
}
public void setMortgageType(BigDecimal mortgageType) {
	this.mortgageType = mortgageType;
}
public BigDecimal getIsCarKeepPaper() {
	return isCarKeepPaper;
}
public void setIsCarKeepPaper(BigDecimal isCarKeepPaper) {
	this.isCarKeepPaper = isCarKeepPaper;
}
public BigDecimal getCarValue() {
	return carValue;
}
public void setCarValue(BigDecimal carValue) {
	this.carValue = carValue;
}
public BigDecimal getTransferNum() {
	return transferNum;
}
public void setTransferNum(BigDecimal transferNum) {
	this.transferNum = transferNum;
}
public BigDecimal getCarType() {
	return carType;
}
public void setCarType(BigDecimal carType) {
	this.carType = carType;
}
public BigDecimal getAsFraud() {
	return asFraud;
}
public void setAsFraud(BigDecimal asFraud) {
	this.asFraud = asFraud;
}
public BigDecimal getCarUsedYear() {
	return carUsedYear;
}
public void setCarUsedYear(BigDecimal carUsedYear) {
	this.carUsedYear = carUsedYear;
}
public BigDecimal getIsProcedureCom() {
	return isProcedureCom;
}
public void setIsProcedureCom(BigDecimal isProcedureCom) {
	this.isProcedureCom = isProcedureCom;
}
public BigDecimal getIsCarstatusNormal() {
	return isCarstatusNormal;
}
public void setIsCarstatusNormal(BigDecimal isCarstatusNormal) {
	this.isCarstatusNormal = isCarstatusNormal;
}
public BigDecimal getCarQuality() {
	return carQuality;
}
public void setCarQuality(BigDecimal carQuality) {
	this.carQuality = carQuality;
}
public BigDecimal getDueDispos() {
	return dueDispos;
}
public void setDueDispos(BigDecimal dueDispos) {
	this.dueDispos = dueDispos;
}
public BigDecimal getPledge() {
	return pledge;
}
public void setPledge(BigDecimal pledge) {
	this.pledge = pledge;
}

}
