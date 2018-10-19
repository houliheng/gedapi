package com.resoft.outinterface.SV.server.entry.request;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.NONE)
public class SVCompany implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@XmlElement(required = false, name = "COMPANY_ROLE")
	private String role;

	@XmlElement(required = false, name = "COMPANY_INFO")
	private SVCompanyInfo svCompanyInfo;

	@XmlElement(required = false, name = "COMPANY_RELATED")
	private List<SVCompanyRelated> companyRelated;

	@XmlElement(required = false, name = "LINE_BANK")
	private List<SVLineBank> lineBank;

	@XmlElement(required = false, name = "CREDIT_COMPANY")
	private SVCreditCompany companyCredit;

	@XmlElement(required = false, name = "CREDIT_ASSETS")
	private List<SVCreditAssets> assets;

	public List<SVLineBank> getLineBank() {
		return lineBank;
	}

	public void setLineBank(List<SVLineBank> lineBank) {
		this.lineBank = lineBank;
	}

	public List<SVCreditAssets> getAssets() {
		return assets;
	}

	public void setAssets(List<SVCreditAssets> assets) {
		this.assets = assets;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public SVCreditCompany getCompanyCredit() {
		return companyCredit;
	}

	public void setCompanyCredit(SVCreditCompany companyCredit) {
		this.companyCredit = companyCredit;
	}

	public SVCompanyInfo getSvCompanyInfo() {
		return svCompanyInfo;
	}

	public void setSvCompanyInfo(SVCompanyInfo svCompanyInfo) {
		this.svCompanyInfo = svCompanyInfo;
	}

	public List<SVCompanyRelated> getCompanyRelated() {
		return companyRelated;
	}

	public void setCompanyRelated(List<SVCompanyRelated> companyRelated) {
		this.companyRelated = companyRelated;
	}
}
