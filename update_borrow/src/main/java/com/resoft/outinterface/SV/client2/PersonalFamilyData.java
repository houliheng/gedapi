package com.resoft.outinterface.SV.client2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * 家庭信息
 * 
 * @author wangguodong
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonalFamilyData {
	@XmlElement(name = "work_id")
	private String work_id; // 子工单编号
	@XmlElement(name = "p_numberChildrenMan")
	private String p_numberChildrenMan;// 10、子女数量(男)
	@XmlElement(name = "p_numberChildrenWoMan")
	private String p_numberChildrenWoMan;// 11、子女数量(女)
	@XmlElement(name = "p_monthlyHouseholdExpenses")
	private String p_monthlyHouseholdExpenses;// 12、每月家庭支出
	@XmlElement(name = "p_residentialTelephone")
	private String p_residentialTelephone;// 14、住宅电话
	@XmlElement(name = "p_currentResidenceTime")
	private String p_currentResidenceTime;// 15、现住宅地居住时间
	@XmlElement(name = "p_cityTime")
	private String p_cityTime; // 16、来本市时间
	@XmlElement(name = "p_registeredResidence")
	private String p_registeredResidence;// 户口所在地区
	@XmlElement(name = "p_registeredDetailResidence")
	private String p_registeredDetailResidence; // 20、户口所在详细地
	@XmlElement(name = "p_whetherholdRegistration")
	private String p_whetherholdRegistration;// 21、是否本地户籍
	@XmlElement(name = "p_whetherConsistent")
	private String p_whetherConsistent;// 28、现居地与户籍地址是否一致
	@XmlElement(name = "p_residentialStatus")
	private String p_residentialStatus; // 29、居住状况
	@XmlElement(name = "p_residentialStatusDescribe")
	private String p_residentialStatusDescribe;// 30、居住状况说明

	public String getWorkId() {
		return work_id;
	}

	public void setWorkId(String workId) {
		this.work_id = workId;
	}

	public String getNumberChildrenMan() {
		return p_numberChildrenMan;
	}

	public void setNumberChildrenMan(String numberChildrenMan) {
		this.p_numberChildrenMan = numberChildrenMan;
	}

	public String getNumberChildrenWoMan() {
		return p_numberChildrenWoMan;
	}

	public void setNumberChildrenWoMan(String numberChildrenWoMan) {
		this.p_numberChildrenWoMan = numberChildrenWoMan;
	}

	public String getMonthlyHouseholdExpenses() {
		return p_monthlyHouseholdExpenses;
	}

	public void setMonthlyHouseholdExpenses(String monthlyHouseholdExpenses) {
		this.p_monthlyHouseholdExpenses = monthlyHouseholdExpenses;
	}

	public String getResidentialTelephone() {
		return p_residentialTelephone;
	}

	public void setResidentialTelephone(String residentialTelephone) {
		this.p_residentialTelephone = residentialTelephone;
	}

	public String getCurrentResidenceTime() {
		return p_currentResidenceTime;
	}

	public void setCurrentResidenceTime(String currentResidenceTime) {
		this.p_currentResidenceTime = currentResidenceTime;
	}

	public String getCityTime() {
		return p_cityTime;
	}

	public void setCityTime(String cityTime) {
		this.p_cityTime = cityTime;
	}

	public String getRegisteredResidence() {
		return p_registeredResidence;
	}

	public void setRegisteredResidence(String registeredResidence) {
		this.p_registeredResidence = registeredResidence;
	}

	public String getRegisteredDetailResidence() {
		return p_registeredDetailResidence;
	}

	public void setRegisteredDetailResidence(String registeredDetailResidence) {
		this.p_registeredDetailResidence = registeredDetailResidence;
	}

	public String getWhetherholdRegistration() {
		return p_whetherholdRegistration;
	}

	public void setWhetherholdRegistration(String whetherholdRegistration) {
		this.p_whetherholdRegistration = whetherholdRegistration;
	}

	public String getWhetherConsistent() {
		return p_whetherConsistent;
	}

	public void setWhetherConsistent(String whetherConsistent) {
		this.p_whetherConsistent = whetherConsistent;
	}

	public String getResidentialStatus() {
		return p_residentialStatus;
	}

	public void setResidentialStatus(String residentialStatus) {
		this.p_residentialStatus = residentialStatus;
	}

	public String getResidentialStatusDescribe() {
		return p_residentialStatusDescribe;
	}

	public void setResidentialStatusDescribe(String residentialStatusDescribe) {
		this.p_residentialStatusDescribe = residentialStatusDescribe;
	}

}
