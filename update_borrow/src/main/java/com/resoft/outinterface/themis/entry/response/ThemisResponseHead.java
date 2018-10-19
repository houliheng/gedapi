package com.resoft.outinterface.themis.entry.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
@XmlAccessorType(XmlAccessType.FIELD)
public class ThemisResponseHead {
	@XmlElement(name="COMPANYCODE")
	private String companycode;
	@XmlElement(name="RETURNCODE")
	private String returncode;
	@XmlElement(name="RETURNMESSAGE")
	private String returnmessage;
	
	@Override
	public String toString() {
		return "Head [companycode=" + companycode + ", returncode="
				+ returncode + ", returnmessage=" + returnmessage + "]";
	}
	public String getCompanycode() {
		return companycode;
	}
	public void setCompanycode(String companycode) {
		this.companycode = companycode;
	}
	public String getReturncode() {
		return returncode;
	}
	public void setReturncode(String returncode) {
		this.returncode = returncode;
	}
	public String getReturnmessage() {
		return returnmessage;
	}
	public void setReturnmessage(String returnmessage) {
		this.returnmessage = returnmessage;
	}
	public ThemisResponseHead() {
		super();
	}
	
}
