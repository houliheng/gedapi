package com.resoft.credit.financialStateImport.entity;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;


/**
 * Themis报表体Entity
 * @author caoyinglong
 * @version 2016-03-14
 */
public class ReportInfo4Excel  implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	
	
	private String reportOrderCol;		// 指标存储序号
	private String reportIndexCode;		// 导入财务指标编号
	private String reportIndexName;		// 导入财务指标描述
	private String reportIndexValue;		// 导入财务指标值
	
	public ReportInfo4Excel() {
		super();
	}

	@Length(min=0, max=255, message="指标存储序号长度必须介于 0 和 255 之间")
	public String getReportOrderCol() {
		return reportOrderCol;
	}

	public void setReportOrderCol(String reportOrderCol) {
		this.reportOrderCol = reportOrderCol;
	}
	
	@Length(min=0, max=255, message="导入财务指标编号长度必须介于 0 和 255 之间")
	public String getReportIndexCode() {
		return reportIndexCode;
	}

	public void setReportIndexCode(String reportIndexCode) {
		this.reportIndexCode = reportIndexCode;
	}
	
	@Length(min=0, max=255, message="导入财务指标描述长度必须介于 0 和 255 之间")
	public String getReportIndexName() {
		return reportIndexName;
	}

	public void setReportIndexName(String reportIndexName) {
		this.reportIndexName = reportIndexName;
	}
	
	@Length(min=0, max=255, message="导入财务指标值长度必须介于 0 和 255 之间")
	public String getReportIndexValue() {
		return reportIndexValue;
	}

	public void setReportIndexValue(String reportIndexValue) {
		this.reportIndexValue = reportIndexValue;
	}

	@Override
	public String toString() {
		return "ReportInfo4Excel [reportOrderCol=" + reportOrderCol
				+ ", reportIndexCode=" + reportIndexCode + ", reportIndexName="
				+ reportIndexName + ", reportIndexValue=" + reportIndexValue
				+ "]";
	}
}