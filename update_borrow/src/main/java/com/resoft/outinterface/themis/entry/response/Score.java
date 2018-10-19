package com.resoft.outinterface.themis.entry.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
@XmlAccessorType(XmlAccessType.FIELD)
public class Score {
	@XmlElement(name="REPORT_YEAR")
	private String reportyear;
	@XmlElement(name="REPORT_MONTH")
	private String reportmonth;
	@XmlElement(name="SCORE_INFO")
	private ScoreInfo scoreinfo;
	
	
	@Override
	public String toString() {
		return "Score [reportyear=" + reportyear + ", reportmonth="
				+ reportmonth + ", scoreinfo=" + scoreinfo.toString() + "]";
	}
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
	public ScoreInfo getScoreinfo() {
		return scoreinfo;
	}
	public void setScoreinfo(ScoreInfo scoreinfo) {
		this.scoreinfo = scoreinfo;
	}
	public Score() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
