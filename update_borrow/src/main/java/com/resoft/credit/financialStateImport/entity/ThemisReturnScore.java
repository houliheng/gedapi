package com.resoft.credit.financialStateImport.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * Themis返回分数Entity
 * 
 * @author caoyinglong
 * @version 2016-03-14
 */
public class ThemisReturnScore extends DataEntity<ThemisReturnScore> {

	private static final long serialVersionUID = 1L;
	private String applyNo;//进件号
	private String companycode; // 企业编号
	private String reportYearMonth; // 财报年月
	private String returnOrderCol; // 指标存储序号
	private String returnIndexCode; // 返回指标编号
	private String returnIndexName; // 返回指标备注
	private String fullMarks; // full_marks
	private String score; // 返回指标值
	private String warnning; // warnning
	private ThemisReportDic themisReportDic;// 指标字典
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public ThemisReturnScore() {
		super();
	}

	public ThemisReturnScore(String id) {
		super(id);
	}

	@Length(min = 1, max = 255, message = "企业编号长度必须介于 1 和 255 之间")
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

	@Length(min = 0, max = 255, message = "指标存储序号长度必须介于 0 和 255 之间")
	public String getReturnOrderCol() {
		return returnOrderCol;
	}

	public void setReturnOrderCol(String returnOrderCol) {
		this.returnOrderCol = returnOrderCol;
	}

	@Length(min = 0, max = 255, message = "返回指标编号长度必须介于 0 和 255 之间")
	public String getReturnIndexCode() {
		return returnIndexCode;
	}

	public void setReturnIndexCode(String returnIndexCode) {
		this.returnIndexCode = returnIndexCode;
	}

	@Length(min = 0, max = 255, message = "返回指标备注长度必须介于 0 和 255 之间")
	public String getReturnIndexName() {
		return returnIndexName;
	}

	public void setReturnIndexName(String returnIndexName) {
		this.returnIndexName = returnIndexName;
	}

	@Length(min = 0, max = 255, message = "full_marks长度必须介于 0 和 255 之间")
	public String getFullMarks() {
		return fullMarks;
	}

	public void setFullMarks(String fullMarks) {
		this.fullMarks = fullMarks;
	}

	@Length(min = 0, max = 255, message = "返回指标值长度必须介于 0 和 255 之间")
	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	@Length(min = 0, max = 255, message = "warnning长度必须介于 0 和 255 之间")
	public String getWarnning() {
		return warnning;
	}

	public void setWarnning(String warnning) {
		this.warnning = warnning;
	}

	public ThemisReportDic getThemisReportDic() {
		return themisReportDic;
	}

	public void setThemisReportDic(ThemisReportDic themisReportDic) {
		this.themisReportDic = themisReportDic;
	}

}