package com.resoft.outinterface.SV.server.entry.request.infomation;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ROOT")
@XmlAccessorType(XmlAccessType.FIELD)
public class InformationMatchServerRequest {
	@XmlElement(name = "applyNo")
	private String applyNo;
	@XmlElement(name = "MortgageCarEvaluateInfo")
	private List<MortgageCarEvaluateInfoServerRequest> mortgageCarEvaluateInfoServerRequests;
	@XmlElement(name = "MortgageHouseInfo")
	private List<MortgageHouseInfoServerRequest> mortgageHouseInfoServerRequests;

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public List<MortgageCarEvaluateInfoServerRequest> getMortgageCarEvaluateInfoServerRequests() {
		return mortgageCarEvaluateInfoServerRequests;
	}

	public void setMortgageCarEvaluateInfoServerRequests(List<MortgageCarEvaluateInfoServerRequest> mortgageCarEvaluateInfoServerRequests) {
		this.mortgageCarEvaluateInfoServerRequests = mortgageCarEvaluateInfoServerRequests;
	}

	public List<MortgageHouseInfoServerRequest> getMortgageHouseInfoServerRequests() {
		return mortgageHouseInfoServerRequests;
	}

	public void setMortgageHouseInfoServerRequests(List<MortgageHouseInfoServerRequest> mortgageHouseInfoServerRequests) {
		this.mortgageHouseInfoServerRequests = mortgageHouseInfoServerRequests;
	}

}
