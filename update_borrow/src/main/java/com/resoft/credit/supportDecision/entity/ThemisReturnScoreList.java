package com.resoft.credit.supportDecision.entity;

import java.util.List;

import com.resoft.credit.financialStateImport.entity.ThemisReturnScore;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 封装Themis返回的一列分数Entity
 * 
 * @author wuxi01
 * @version 2016-03-25
 */
public class ThemisReturnScoreList extends DataEntity<ThemisReturnScoreList> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String companycode; // 企业编号
	private String reportYearMonth; // 财报年月
	private List<ThemisReturnScore> themisReturnScoreList;// 财报年月相同、企业编号相同的一组分数

	public ThemisReturnScoreList() {
		super();
	}

	public String getCompanycode() {
		return companycode;
	}

	public void setCompanycode(String companycode) {
		this.companycode = companycode;
	}

	public String getReportYearMonth() {
		return reportYearMonth;
	}

	public void setReportYearMonth(String reportYearMonth) {
		this.reportYearMonth = reportYearMonth;
	}

	public List<ThemisReturnScore> getThemisReturnScoreList() {
		return themisReturnScoreList;
	}

	public void setThemisReturnScoreList(List<ThemisReturnScore> themisReturnScoreList) {
		this.themisReturnScoreList = themisReturnScoreList;
	}

}