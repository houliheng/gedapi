package com.resoft.outinterface.themis.server;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.outinterface.rest.financialPlatform.FinancialPlatformClient;
import com.resoft.outinterface.themis.dao.ThemisRequestDao;
import com.resoft.outinterface.themis.entry.request.ThemisRequestHead;
import com.resoft.outinterface.themis.entry.response.Review;
import com.resoft.outinterface.themis.entry.response.Score;
import com.resoft.outinterface.themis.entry.response.ThemisResponse;
import com.resoft.outinterface.themis.entry.response.ThemisResponseBody;
import com.resoft.outinterface.themis.entry.response.ThemisResponseHead;
import com.resoft.outinterface.themis.entry.response.ThemisReviewInsertBacthObject;
import com.resoft.outinterface.themis.entry.response.ThemisScoreInsertBacthObject;
import com.resoft.outinterface.utils.OutInterfaceUtils;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
@Service
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class ThemisRequestServer {
	@Autowired
	private ThemisRequestDao themisRequestDao;
	/**
	 * 根据进件号查询导入库中的报表，转换成接口对象
	 * 
	 * @param applyNo
	 */
	private static final Logger logger = LoggerFactory.getLogger(FinancialPlatformClient.class);
	@Transactional(value = "CRE", readOnly = false)
	public List<String> findYearMonthByApply(String report,String applyNo) {
		List<String> bs = themisRequestDao.findYearMonthByApply(report, applyNo);
		return bs;
	}
	@Transactional(value = "CRE", readOnly = false)
	public List<String> findFinancialReport (String report,String str,String applyNo){
		return themisRequestDao.findFinancialReport(report,str,applyNo);
	}
	@Transactional(value = "CRE", readOnly = false)
	public ThemisRequestHead findThemisReportHead(String applyNo){
		 return themisRequestDao.findThemisReportHead(applyNo);
	}
	@Transactional(value = "CRE", readOnly = false)
	public void insertIntoThemisReturnOnHead(ThemisResponse themisResponse,String applyNo){
		this.insertIntoThemisReturnHead(themisResponse.getHead(),applyNo);
	}
	@Transactional(value = "CRE", readOnly = false)
	public void insertIntoThemisReturnOnBody(ThemisResponse themisResponse,String applyNo){
		this.insertIntoThemisReturnBody(themisResponse.getBody(),themisResponse.getHead().getCompanycode(),applyNo);
	}
	public void insertIntoThemisReturnHead(ThemisResponseHead trh,String applyNo){
		themisRequestDao.deleteThemisReturnHead(applyNo);
		themisRequestDao.insertIntoThemisReturnHead(trh,applyNo);
	}
	public void insertIntoThemisReturnBody(ThemisResponseBody trb,String companyCode,String applyNo){
		themisRequestDao.deleteThemisReview(applyNo);
		themisRequestDao.deleteThemisScore(applyNo);
		this.insertIntoThemisReview(trb.getReview(),companyCode,applyNo);
		this.insertIntoThemisScore(trb.getScore(),companyCode,applyNo);
	}
	public void insertIntoThemisReview(List<Review> review,String companyCode,String applyNo){
		Iterator<Review> it =  review.iterator();
		while(it.hasNext()){
			Review its =it.next();
			String yearMonth =its.getReportyear()+ its.getReportmonth();
			List<ThemisReviewInsertBacthObject> valueList = new ArrayList<ThemisReviewInsertBacthObject>();
			
			Field[] bean = its.getReviewinfo().getClass().getDeclaredFields();
			for( int z=0;z<bean.length;z++){
 				bean[z].setAccessible(true);
				String name=bean[z].getName();
				ThemisReviewInsertBacthObject tribo = new ThemisReviewInsertBacthObject();
				tribo.setCompanycode(companyCode);
				tribo.setOrderCol(z+1+"");
				tribo.setIndexCode("R"+String.format("%02d",z+1));
				tribo.setYearMonth(yearMonth);
				tribo.setApplyNo(applyNo);
				try {
					tribo.setReview((String)OutInterfaceUtils.getFieldValueByName(name,  its.getReviewinfo()));
				} catch (Exception e) {
					logger.error("ThemisReview返回值入库错误",e);
				}	
				valueList.add(tribo);
			}
			themisRequestDao.insertIntoThemisReview(valueList);
		}
	}
	public void insertIntoThemisScore(List<Score> score,String companyCode,String applyNo){
		Iterator<Score> it =  score.iterator();
		while(it.hasNext()){
			Score its =it.next();
			String yearMonth =its.getReportyear()+ its.getReportmonth();
			List<ThemisScoreInsertBacthObject> valueList = new ArrayList<ThemisScoreInsertBacthObject>();
			
			Field[] bean = its.getScoreinfo().getClass().getDeclaredFields();
			for( int z=0;z<bean.length;z++){
				bean[z].setAccessible(true);
				String name=bean[z].getName();
				ThemisScoreInsertBacthObject tribo = new ThemisScoreInsertBacthObject();
				tribo.setCompanycode(companyCode);
				tribo.setOrderCol(z+1+"");
				tribo.setIndexCode("S"+String.format("%03d",z+1));
				tribo.setYearMonth(yearMonth);
				tribo.setApplyNo(applyNo);
				try {
					tribo.setScore((String)OutInterfaceUtils.getFieldValueByName(name, its.getScoreinfo()));
				} catch (Exception e) {
					logger.error("ThemisScore返回值入库错误",e);
				}	
				valueList.add(tribo);
			}
			themisRequestDao.insertIntoThemisScore(valueList);
		}
	}
	
	@Transactional(value = "CRE", readOnly = false)
	public void insertIntoThemisReturn(ThemisResponse returnxml, String applyNo) {
		this.insertIntoThemisReturnOnHead(returnxml,applyNo);
		this.insertIntoThemisReturnOnBody(returnxml,applyNo);
	}

}
