package com.resoft.outinterface.themis.entry.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
@XmlAccessorType(XmlAccessType.FIELD)
public class Review {
	@XmlElement(name="REPORT_YEAR")
	private String reportyear;
	@XmlElement(name="REPORT_MONTH")
	private String reportmonth;
	@XmlElement(name="REVIEW_INFO")
	private ReviewInfo reviewinfo;
	
	@Override
	public String toString() {
		return "Review [reportyear=" + reportyear + ", reportmonth="
				+ reportmonth + ", reviewinfo=" + reviewinfo.toString() + "]";
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
	public ReviewInfo getReviewinfo() {
		return reviewinfo;
	}
	public void setReviewinfo(ReviewInfo reviewinfo) {
		this.reviewinfo = reviewinfo;
	}
	public Review() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
