package com.resoft.credit.financialStateImport.entity;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * Themis报表体Entity
 * @author caoyinglong
 * @version 2016-03-14
 */
public class ThemisReportInfo extends DataEntity<ThemisReportInfo> {
	
	private static final long serialVersionUID = 1L;
	private String companycode;		// 企业编号
	private String reportYearMonth;		// 财报年月
	private List<ReportInfo4Excel> reportInfo;
	private String reportTable;		// 导入指标所属表
	
	public ThemisReportInfo() {
		super();
	}

	public ThemisReportInfo(String id){
		super(id);
	}

	@Length(min=0, max=255, message="企业编号长度必须介于 0 和 255 之间")
	public String getCompanycode() {
		return companycode;
	}

	public void setCompanycode(String companycode) {
		this.companycode = companycode;
	}
	
	@Length(min=0, max=255, message="财报年月长度必须介于 0 和 255 之间")
	public String getReportYearMonth() {
		return reportYearMonth;
	}

	public void setReportYearMonth(String reportYearMonth) {
		this.reportYearMonth = reportYearMonth;
	}
	
	public List<ReportInfo4Excel> getReportInfo() {
		return reportInfo;
	}

	public void setReportInfo(List<ReportInfo4Excel> reportInfo) {
		this.reportInfo = reportInfo;
	}

	@Length(min=0, max=255, message="导入指标所属表长度必须介于 0 和 255 之间")
	public String getReportTable() {
		return reportTable;
	}

	public void setReportTable(String reportTable) {
		this.reportTable = reportTable;
	}

	@Override
	public String toString() {
		return "ThemisReportInfo [companycode=" + companycode
				+ ", reportYearMonth=" + reportYearMonth + ", reportInfo="
				+ reportInfo.toString() + ", reportTable=" + reportTable + "]";
	}
	
}