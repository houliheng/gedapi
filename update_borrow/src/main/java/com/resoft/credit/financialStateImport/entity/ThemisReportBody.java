package com.resoft.credit.financialStateImport.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * Themis报表体Entity
 * 
 * @author caoyinglong
 * @version 2016-03-14
 */
public class ThemisReportBody extends DataEntity<ThemisReportBody> {

	private static final long serialVersionUID = 1L;
	private String applyNo;// 申请编号
	private String companycode; // 企业编号
	private String reportYearMonth; // 财报年月
	private ThemisReportDic themisReportDic; //指标信息对象(序号+所属表确定对应指标)
	private String reportIndexValue; // 导入财务指标值

	public ThemisReportBody() {
		super();
	}

	public ThemisReportBody(String id) {
		super(id);
	}

	@Length(min = 0, max = 255, message = "企业编号长度必须介于 0 和 255 之间")
	public String getCompanycode() {
		return companycode;
	}

	public void setCompanycode(String companycode) {
		this.companycode = companycode;
	}

	@Length(min = 0, max = 255, message = "财报年月长度必须介于 0 和 255 之间")
	public String getReportYearMonth() {
		return reportYearMonth;
	}

	public void setReportYearMonth(String reportYearMonth) {
		this.reportYearMonth = reportYearMonth;
	}

	@Length(min = 0, max = 255, message = "导入财务指标值长度必须介于 0 和 255 之间")
	public String getReportIndexValue() {
		return reportIndexValue;
	}

	public void setReportIndexValue(String reportIndexValue) {
		this.reportIndexValue = reportIndexValue;
	}

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public ThemisReportDic getThemisReportDic() {
		return themisReportDic;
	}

	public void setThemisReportDic(ThemisReportDic themisReportDic) {
		this.themisReportDic = themisReportDic;
	}

}