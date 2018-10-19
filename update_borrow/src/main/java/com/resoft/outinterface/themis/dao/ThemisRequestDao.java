package com.resoft.outinterface.themis.dao;

import java.util.List;

import com.resoft.outinterface.themis.entry.request.ThemisRequestHead;
import com.resoft.outinterface.themis.entry.response.ThemisResponseHead;
import com.resoft.outinterface.themis.entry.response.ThemisReviewInsertBacthObject;
import com.resoft.outinterface.themis.entry.response.ThemisScoreInsertBacthObject;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * Themis报表体DAO接口
 * @author caoyinglong
 * @version 2016-03-14
 */
@MyBatisDao
public interface ThemisRequestDao {
	public List<String> findYearMonthByApply(String reportTable,String applyNo);
	public List<String> findFinancialReport(String reportTable,String yearMonth,String applyNo);
	public ThemisRequestHead findThemisReportHead(String applyNo);
	public void insertIntoThemisReturnHead(ThemisResponseHead trh,String applyNo);
	public void deleteThemisReturnHead(String applyNo);
	public void insertIntoThemisReview(List<ThemisReviewInsertBacthObject> valueString);
	public void deleteThemisReview(String applyNo);
	public void insertIntoThemisScore(List<ThemisScoreInsertBacthObject> valueString);
	public void deleteThemisScore(String applyNo);
}
