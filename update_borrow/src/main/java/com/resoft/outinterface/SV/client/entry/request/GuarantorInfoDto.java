package com.resoft.outinterface.SV.client.entry.request;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class GuarantorInfoDto {
	@XmlElement(required=true,name="SURVEY_TYPE")
	private String surveyType;
	@XmlElement(required=true,name="COMPANY_NAME")
	private String companyName;
	@XmlElement(required=true,name="LEGAL_NAME")
	private String legalName;
	@XmlElement(required=true,name="ADDRESS")
	private String address;
	@XmlElement(required=true,name="MASTER_RELATION")
	private String masterRelation;
	@XmlElement(required=true,name="PHONE_NO")
	private String phoneNo;
	public String getSurveyType() {
		return surveyType;
	}
	public void setSurveyType(String surveyType) {
		this.surveyType = surveyType;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getLegalName() {
		return legalName;
	}
	public void setLegalName(String legalName) {
		this.legalName = legalName;
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
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	@Override
	public String toString() {
		return "WorkOrderDto [surveyType=" + surveyType + ", companyName="
				+ companyName + ", legalName=" + legalName + ", address="
				+ address + ", masterRelation=" + masterRelation + ", phoneNo="
				+ phoneNo + "]";
	}

	
}
