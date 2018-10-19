package com.resoft.outinterface.themis.entry.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class NewFinanceBS {
	@XmlElement(name="REPORT_YEAR")
	private String reportyear;
	@XmlElement(name="REPORT_MONTH")
	private String reportmonth;
	@XmlElement(name="REPORT_INFO")
	private ReportInfoBS reportinfo;
	public String getReportyear() {
		return reportyear;
	}

	public void setReportyear(String reportyear) {
		this.reportyear = reportyear;
	}

	public String getReportmonth() {
		return reportmonth;
	}

	public void setReportmonth(String reportmonth) {
		this.reportmonth = reportmonth;
	}

	public ReportInfoBS getReportinfo() {
		return reportinfo;
	}

	public void setReportinfo(ReportInfoBS reportinfo) {
		this.reportinfo = reportinfo;
	}

	@Override
	public String toString() {
		return "NewFinanceBS [reportyear=" + reportyear + ", reportmonth="
				+ reportmonth + ", reportinfo=" + reportinfo.toString() + "]";
	}
	
}
