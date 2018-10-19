package com.resoft.credit.reviewedRule.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.credit.reviewedRule.entity.ReviewedRule;

/**
 * 借款审核表DAO接口
 * @author zhaohuakui
 * @version 2016-12-08
 */
@MyBatisDao
public interface ReviewedRuleDao extends CrudDao<ReviewedRule> {

	List<ReviewedRule> findReviewedRule(ReviewedRule reviewedRule);

	List<ReviewedRule> findRule(ReviewedRule reviewedRule);

	public void saveRule(@Param("reviewedRulelst")List<ReviewedRule> reviewedRulelst,@Param("maps") Map<String, String> maps);

	public void deleteInfo(@Param("maps") Map<String, String> maps);

	String checkNull( Map<String, String> maps);

	void updataRule(@Param("reviewedRulelst") List<ReviewedRule> reviewedRulelst,@Param("maps") Map<String, String> maps,@Param("reviewedRule") ReviewedRule reviewedRule);

	String checkReviewedNull(@Param("maps") Map<String, String> maps);

	String checkReviewed(@Param("maps") Map<String, String> maps);

	public String findApplyAmont(ReviewedRule reviewedRule);

	public String getProductId(ReviewedRule reviewedRule);
	
	public String getProductIdByRecent(ReviewedRule reviewedRule);

}