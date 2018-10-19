package com.resoft.credit.supportDecision.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.financialStateImport.dao.ThemisReturnHeadDao;
import com.resoft.credit.financialStateImport.dao.ThemisReturnScoreDao;
import com.resoft.credit.financialStateImport.entity.ThemisReturnHead;
import com.resoft.credit.financialStateImport.entity.ThemisReturnReview;
import com.resoft.credit.financialStateImport.entity.ThemisReturnScore;
import com.resoft.credit.supportDecision.dao.SupportDecisionDao;
import com.resoft.credit.supportDecision.dao.ThemisReturnReviewDao;
import com.resoft.credit.supportDecision.entity.SupportDecision;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
/**
 * 支持决策Service
 * @author wuxi01
 * @version 2016-03-25
 */
@Service
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class SupportDecisionService extends CrudService<SupportDecisionDao, SupportDecision> {

	@Autowired
	private ThemisReturnHeadDao themisReturnHeadDao;

	@Autowired
	private ThemisReturnReviewDao themisReturnReviewDao;

	@Autowired
	private ThemisReturnScoreDao themisReturnScoreDao;

	/**
	 * 查询Themis返回头信息
	 * 
	 * @param ThemisReturnHead
	 * @return List<ThemisReturnHead>
	 */
	public List<ThemisReturnHead> findThemisReturnHeadList(ThemisReturnHead themisReturnHead) {
		return themisReturnHeadDao.findList(themisReturnHead);
	}

	/**
	 * 查询Themis返回分数列表
	 * 
	 * @param ThemisReturnScore
	 * @return List<ThemisReturnScore>
	 */
	public List<ThemisReturnScore> findThemisReturnScoreList(ThemisReturnScore themisReturnScore) {
		return themisReturnScoreDao.findList(themisReturnScore);
	}

	/**
	 * 查询Themis返回评价信息
	 * 
	 * @param ThemisReturnReview
	 * @return List<ThemisReturnReview>
	 */
	public List<ThemisReturnReview> findThemisReturnReviewList(ThemisReturnReview themisReturnReview) {
		return themisReturnReviewDao.findList(themisReturnReview);
	}
	/**
	 * 根据进件号查询财报年月
	 * @param params
	 * @return
	 */
	public List<String> findDistinctReportYearMonth(Map<String, String> params){
		return themisReturnScoreDao.findDistinctReportYearMonth(params);
	}

}
