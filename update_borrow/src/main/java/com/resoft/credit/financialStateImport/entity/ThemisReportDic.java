package com.resoft.credit.financialStateImport.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * Themis报表字典表Entity
 * @author wuxi01
 * @version 2016-03-23
 */
public class ThemisReportDic extends DataEntity<ThemisReportDic> {
	
	private static final long serialVersionUID = 1L;
	private String reportOrderCol;		// 指标序号
	private String reportIndexCode;		// 导入财务指标编号
	private String reportIndexName;		// 导入财务指标值
	private String reportTable;		// 导入指标所属表
	
	public ThemisReportDic() {
		super();
	}
	public ThemisReportDic(String reportTable) {
		this.reportTable = reportTable;
	}

	@Length(min=1, max=5, message="指标序号长度必须介于 1 和 5 之间")
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
	
	@Length(min=0, max=255, message="导入财务指标值长度必须介于 0 和 255 之间")
	public String getReportIndexName() {
		return reportIndexName;
	}

	public void setReportIndexName(String reportIndexName) {
		this.reportIndexName = reportIndexName;
	}
	
	@Length(min=0, max=255, message="导入指标所属表长度必须介于 0 和 255 之间")
	public String getReportTable() {
		return reportTable;
	}

	public void setReportTable(String reportTable) {
		this.reportTable = reportTable;
	}
	
}