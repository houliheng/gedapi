//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.03.08 at 06:41:27 PM CST 
//


package com.resoft.outinterface.cn.com.experian.entry.request.body;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RequestInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Loan" type="{}LoanInfo"/>
 *         &lt;element name="CoApp" type="{}CoAppInfo" maxOccurs="10" minOccurs="0"/>
 *         &lt;element name="App" type="{}AppInfo"/>
 *         &lt;element name="GuApp" type="{}GuAppInfo" maxOccurs="10" minOccurs="0"/>
 *         &lt;element name="Firm" type="{}FirmInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="PBOC_CoApp" type="{}PBOC_CoAppInfo" maxOccurs="10" minOccurs="0"/>
 *         &lt;element name="PBOC_App" type="{}PBOC_AppInfo"/>
 *         &lt;element name="PBOC_Firm" type="{}PBOC_FirmInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="PBOC_GuApp" type="{}PBOC_GuAppInfo" maxOccurs="10" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
//@XmlRootElement(name="Reqinfo")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType( propOrder = {
    "loan",
    "housesInfo",
    "vehiclesInfo",
    "coApps",
    "app",
    "guApps",
    "firms",
    "pbocCoApps",
    "pbocApp",
    "pbocFirms",
    "pbocGuApps"
})
public class RequestInfo {

    @XmlElement(name = "Loan", required = true)
    protected LoanInfo loan;
    @XmlElement(name = "Houses", required = true)
    protected HousesInfo	housesInfo;
    @XmlElement(name = "Vehicles", required = true)
    protected VehiclesInfo	vehiclesInfo;
    @XmlElement(name = "CoApps")
    protected CoAppsInfo coApps;
    @XmlElement(name = "App", required = true)
    protected AppInfo app;
    @XmlElement(name = "GuApps")
    protected GuAppsInfo guApps;
    @XmlElement(name = "Firms")
    protected FirmsInfo firms;
    @XmlElement(name = "PBOC_CoApps")
    protected PBOCCoAppsInfo pbocCoApps;
    @XmlElement(name = "PBOC_App", required = true)
    protected PBOCAppInfo pbocApp;
    @XmlElement(name = "PBOC_Firms")
    protected PBOCFirms pbocFirms;
    @XmlElement(name = "PBOC_GuApps")
    protected PBOCGuApps pbocGuApps;
	public LoanInfo getLoan() {
		return loan;
	}
	public void setLoan(LoanInfo loan) {
		this.loan = loan;
	}
	public HousesInfo getHousesInfo() {
		return housesInfo;
	}
	public void setHousesInfo(HousesInfo housesInfo) {
		this.housesInfo = housesInfo;
	}
	public VehiclesInfo getVehiclesInfo() {
		return vehiclesInfo;
	}
	public void setVehiclesInfo(VehiclesInfo vehiclesInfo) {
		this.vehiclesInfo = vehiclesInfo;
	}
	public CoAppsInfo getCoApps() {
		return coApps;
	}
	public void setCoApps(CoAppsInfo coApps) {
		this.coApps = coApps;
	}
	public AppInfo getApp() {
		return app;
	}
	public void setApp(AppInfo app) {
		this.app = app;
	}
	public GuAppsInfo getGuApps() {
		return guApps;
	}
	public void setGuApps(GuAppsInfo guApps) {
		this.guApps = guApps;
	}
	public FirmsInfo getFirms() {
		return firms;
	}
	public void setFirms(FirmsInfo firms) {
		this.firms = firms;
	}
	public PBOCCoAppsInfo getPbocCoApps() {
		return pbocCoApps;
	}
	public void setPbocCoApps(PBOCCoAppsInfo pbocCoApps) {
		this.pbocCoApps = pbocCoApps;
	}
	public PBOCAppInfo getPbocApp() {
		return pbocApp;
	}
	public void setPbocApp(PBOCAppInfo pbocApp) {
		this.pbocApp = pbocApp;
	}
	public PBOCFirms getPbocFirms() {
		return pbocFirms;
	}
	public void setPbocFirms(PBOCFirms pbocFirms) {
		this.pbocFirms = pbocFirms;
	}
	public PBOCGuApps getPbocGuApps() {
		return pbocGuApps;
	}
	public void setPbocGuApps(PBOCGuApps pbocGuApps) {
		this.pbocGuApps = pbocGuApps;
	}
}
