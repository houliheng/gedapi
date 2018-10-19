package com.resoft.outinterface.SV.client.entry.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "surveyInfoDto", propOrder = {
	"workId",
	"id",
    "address",
    "city",
    "district",
    "isMaster",
    "masterRelation",
    "phoneNo",
    "process",
    "provinces",
    "remark",
    "surveyName",
    "flag"
})
public class SurveyInfoDto {
	@XmlElement(required = true, name = "WORK_ID")
	private String workId;
	@XmlElement(required = true, name = "PROCESS")
	private String process;
	@XmlElement(required = true, name = "SURVEY_NAME")
	private String surveyName;
	@XmlElement(required = true, name = "PHONE_NO")
	private String phoneNo;
	@XmlElement (required = true, name="PROVINCES")
	private String provinces;
	@XmlElement (required = true, name="CITY")
	private String city;
	@XmlElement (required = true, name="DISTRICT")
	private String district;
	@XmlElement(required = true, name = "ADDRESS")
	private String address;
	@XmlElement(name = "MASTER_RELATION")
	private String masterRelation;
	@XmlElement(name = "REMARK")
	private String remark;
	@XmlElement(required = true, name = "IS_MASTER")
	private String isMaster;
	@XmlElement(required = true, name = "FLAG")
	private String flag;

	public String getWorkId() {
		return workId;
	}

	public void setWork_id(String workId) {
		this.workId = workId;
	}

	public String getProvinces() {
		return provinces;
	}

	public void setProvinces(String provinces) {
		this.provinces = provinces;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}


	public String getSurveyName() {
		return surveyName;
	}

	public void setSurveyName(String surveyName) {
		this.surveyName = surveyName;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMasterRelation() {
		return masterRelation;
	}

	public void setMasterRelation(String masterRelation) {
		this.masterRelation = masterRelation;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIsMaster() {
		return isMaster;
	}

	public void setIsMaster(String isMaster) {
		this.isMaster = isMaster;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

}
