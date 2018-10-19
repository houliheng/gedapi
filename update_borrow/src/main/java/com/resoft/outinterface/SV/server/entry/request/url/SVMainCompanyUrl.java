package com.resoft.outinterface.SV.server.entry.request.url;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class SVMainCompanyUrl {
	@XmlElement(required=true,name="COMPANY_BASE_INFO")
	private List<SVRequestBaseData> compayBaseInfo;
	@XmlElement(required=true,name="HIS_INFO_STOCK_STRUC")
	private List<SVRequestBaseData> hisInfoStockStruc;
	@XmlElement(required=true,name="CREDIT_LINE")
	private List<SVRequestBaseData> creditLine;
	@XmlElement(required=true,name="CO_COMPANY_INFO")
	private List<SVRequestBaseData> coCompanyInfo;
	@XmlElement(required=true,name="BUS_CON_INFO")
	private List<SVRequestBaseData> busConInfo;
	@XmlElement(required=true,name="FEE_LIST")
	private List<SVRequestBaseData> feeList;
	@XmlElement(required=true,name="COMPANY_ASSETS")
	private List<SVRequestBaseData> companyAssets;
	@XmlElement(required=true,name="COMPANY_REPORT")
	private List<SVRequestBaseData> companyReport;
	@XmlElement(required=true,name="INTERVIEW_STATUS")
	private List<SVRequestBaseData> interviewStatus;
	@XmlElement(required=true,name="PIC_NET_CHECK")
	private List<SVRequestBaseData> netCheck;
	@XmlElement(required=true,name="OTHER_INFO")
	private List<SVRequestBaseData> otherInfo;
	public List<SVRequestBaseData> getCompayBaseInfo() {
		return compayBaseInfo;
	}
	public void setCompayBaseInfo(List<SVRequestBaseData> compayBaseInfo) {
		this.compayBaseInfo = compayBaseInfo;
	}
	public List<SVRequestBaseData> getHisInfoStockStruc() {
		return hisInfoStockStruc;
	}
	public void setHisInfoStockStruc(List<SVRequestBaseData> hisInfoStockStruc) {
		this.hisInfoStockStruc = hisInfoStockStruc;
	}
	public List<SVRequestBaseData> getCreditLine() {
		return creditLine;
	}
	public void setCreditLine(List<SVRequestBaseData> creditLine) {
		this.creditLine = creditLine;
	}
	public List<SVRequestBaseData> getCoCompanyInfo() {
		return coCompanyInfo;
	}
	public void setCoCompanyInfo(List<SVRequestBaseData> coCompanyInfo) {
		this.coCompanyInfo = coCompanyInfo;
	}
	public List<SVRequestBaseData> getBusConInfo() {
		return busConInfo;
	}
	public void setBusConInfo(List<SVRequestBaseData> busConInfo) {
		this.busConInfo = busConInfo;
	}
	public List<SVRequestBaseData> getFeeList() {
		return feeList;
	}
	public void setFeeList(List<SVRequestBaseData> feeList) {
		this.feeList = feeList;
	}
	public List<SVRequestBaseData> getCompanyAssets() {
		return companyAssets;
	}
	public void setCompanyAssets(List<SVRequestBaseData> companyAssets) {
		this.companyAssets = companyAssets;
	}
	public List<SVRequestBaseData> getCompanyReport() {
		return companyReport;
	}
	public void setCompanyReport(List<SVRequestBaseData> companyReport) {
		this.companyReport = companyReport;
	}
	public List<SVRequestBaseData> getInterviewStatus() {
		return interviewStatus;
	}
	public void setInterviewStatus(List<SVRequestBaseData> interviewStatus) {
		this.interviewStatus = interviewStatus;
	}
	public List<SVRequestBaseData> getNetCheck() {
		return netCheck;
	}
	public void setNetCheck(List<SVRequestBaseData> netCheck) {
		this.netCheck = netCheck;
	}
	public List<SVRequestBaseData> getOtherInfo() {
		return otherInfo;
	}
	public void setOtherInfo(List<SVRequestBaseData> otherInfo) {
		this.otherInfo = otherInfo;
	}
}
