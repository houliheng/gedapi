package com.resoft.outinterface.themis.entry.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ThemisRequestHead {
	@XmlElement(name="USERID")
	private String userid;
	@XmlElement(name="PASSWORD")
	private String password;
	@XmlElement(name="DEPCODE")
	private String depcode;
	@XmlElement(name="COMPANYCODE")
	private String companycode;
	@XmlElement(name="COMPANY_CHN_NAME")
	private String companychnname;
	@XmlElement(name="COMPANY_ENG_NAME")
	private String companyengname;
	@XmlElement(name="CREATEDATE")
	private String createdate;
	@XmlElement(name="COMPANYNATURE")
	private String companynature;
	@XmlElement(name="SW_INDU_1")
	private String swindu1;
	@XmlElement(name="SW_INDU_2")
	private String swindu2;
	@XmlElement(name="MARKET_CLASS")
	private String marketclass;
	@XmlElement(name="POSTCODE")
	private String postcode;
	@XmlElement(name="IS_MERGER")
	private String ismerger;
	@XmlElement(name="REPORT_UNIT")
	private String reportunit;
	@XmlElement(name="REPORT_TYPE")
	private String reporttype;
	@XmlElement(name="TYPE")
	private String type;
	
	@Override
	public String toString() {
		return "Head [userid=" + userid + ", password=" + password
				+ ", depcode=" + depcode + ", companycode=" + companycode
				+ ", companychnname=" + companychnname + ", companyengname="
				+ companyengname + ", createdate=" + createdate
				+ ", companynature=" + companynature + ", swindu1=" + swindu1
				+ ", swindu2=" + swindu2 + ", marketclass=" + marketclass
				+ ", postcode=" + postcode + ", ismerger=" + ismerger
				+ ", reportunit=" + reportunit + ", reporttype=" + reporttype
				+ ", type=" + type + "]";
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDepcode() {
		return depcode;
	}
	public void setDepcode(String depcode) {
		this.depcode = depcode;
	}
	public String getCompanycode() {
		return companycode;
	}
	public void setCompanycode(String companycode) {
		this.companycode = companycode;
	}
	public String getCompanychnname() {
		return companychnname;
	}
	public void setCompanychnname(String companychnname) {
		this.companychnname = companychnname;
	}
	public String getCompanyengname() {
		return companyengname;
	}
	public void setCompanyengname(String companyengname) {
		this.companyengname = companyengname;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public String getCompanynature() {
		return companynature;
	}
	public void setCompanynature(String companynature) {
		this.companynature = companynature;
	}
	public String getSwindu1() {
		return swindu1;
	}
	public void setSwindu1(String swindu1) {
		this.swindu1 = swindu1;
	}
	public String getSwindu2() {
		return swindu2;
	}
	public void setSwindu2(String swindu2) {
		this.swindu2 = swindu2;
	}
	public String getMarketclass() {
		return marketclass;
	}
	public void setMarketclass(String marketclass) {
		this.marketclass = marketclass;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getIsmerger() {
		return ismerger;
	}
	public void setIsmerger(String ismerger) {
		this.ismerger = ismerger;
	}
	public String getReportunit() {
		return reportunit;
	}
	public void setReportunit(String reportunit) {
		this.reportunit = reportunit;
	}
	public String getReporttype() {
		return reporttype;
	}
	public void setReporttype(String reporttype) {
		this.reporttype = reporttype;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public ThemisRequestHead() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
