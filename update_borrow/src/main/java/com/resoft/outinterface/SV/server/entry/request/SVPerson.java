package com.resoft.outinterface.SV.server.entry.request;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class SVPerson {
	@XmlElement(required = false, name = "PERSON_ROLE")
	private String role;
	@XmlElement(required = false, name = "PERSON_IDNUM")
	private String idNum;
	@XmlElement(required = false, name = "CUST_INFO")
	private SVCustInfo custInfo;// 个人基本信息
	@XmlElement(required = false, name = "CUST_WORK_INFO")
	private SVCustWorkInfo custWorkInfo;// 个人工作信息
	@XmlElement(required = false, name = "APPLY_CONTACT")
	private List<SVApplyContact> applyContact;// 联系人信息
	@XmlElement(required = false, name = "LINE_BANK")
	private List<SVLineBank> lineBank;// 银行卡信息
	@XmlElement(required = false, name = "CREDIT_PERSON")
	private SVCreditCust personCredit;// 个人征信信息
	@XmlElement(required = false, name = "CREDIT_ASSETS")
	private List<SVCreditAssets> assets;// 个人资产信息
	@XmlElement(required = false, name = "OTHER_INFO")
	private String otherInfo;// 个人其他信息

	public String getOtherInfo() {
		return otherInfo;
	}

	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
	}

	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}

	public SVCreditCust getPersonCredit() {
		return personCredit;
	}

	public void setPersonCredit(SVCreditCust personCredit) {
		this.personCredit = personCredit;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

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

	public SVCustInfo getCustInfo() {
		return custInfo;
	}

	public void setCustInfo(SVCustInfo custInfo) {
		this.custInfo = custInfo;
	}

	public SVCustWorkInfo getCustWorkInfo() {
		return custWorkInfo;
	}

	public void setCustWorkInfo(SVCustWorkInfo custWorkInfo) {
		this.custWorkInfo = custWorkInfo;
	}

	public List<SVApplyContact> getApplyContact() {
		return applyContact;
	}

	public void setApplyContact(List<SVApplyContact> applyContact) {
		this.applyContact = applyContact;
	}
}
