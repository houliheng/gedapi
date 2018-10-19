package com.resoft.credit.financialStateImport.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.credit.financialStateImport.entity.ReportInfo4Excel;
import com.resoft.credit.financialStateImport.entity.ThemisReportBody;
import com.resoft.credit.financialStateImport.entity.ThemisReportHead;

/**
 * Themis报表体DAO接口
 * @author caoyinglong
 * @version 2016-03-14
 */
@MyBatisDao
public interface ThemisReportBodyDao extends CrudDao<ThemisReportBody> {
	public List<String> findYearMonthByApply(String reportTable,String applyNo);
	public List<ReportInfo4Excel> findFinancialReport(String reportTable,String yearMonth,String applyNo);
	public ThemisReportHead findThemisReportHead(String applyNo);
	/**
	 * 批量插入
	 * @param themisReportBodyList
	 */
	public void banchInsert(List<ThemisReportBody> themisReportBodyList);
	public void batchInsert(String applyNo,String companyCode,String reportYearMonth,String reportTable,List<ReportInfo4Excel> reportInfo);
	public void deleteByApplyNo(String applyNo,String reportTable);
}