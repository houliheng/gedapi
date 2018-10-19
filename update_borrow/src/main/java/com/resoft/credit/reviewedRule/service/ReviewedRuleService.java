package com.resoft.credit.reviewedRule.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.common.utils.Constants;
import com.resoft.credit.reviewedRule.dao.ReviewedRuleDao;
import com.resoft.credit.reviewedRule.entity.ReviewedRule;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;

/**
 * 借款审核表Service
 * @author zhaohuakui
 * @version 2016-12-08
 */
@Service
@Transactional(readOnly = false)
public class ReviewedRuleService extends CrudService<ReviewedRuleDao, ReviewedRule> {
	//查询列表
	public List<ReviewedRule> findReviewedRule(ReviewedRule reviewedRule) {
	//第一次填  ProcessSequence为空
		if (Constants.UTASK_FGSFKSH.equals(reviewedRule.getTaskDefKey())||Constants.UTASK_FGSJLSH.equals(reviewedRule.getTaskDefKey())){
			reviewedRule.setProcessSequence("1");
		}
		if (Constants.UTASK_QYFKSH.equals(reviewedRule.getTaskDefKey())||Constants.UTASK_QYJLSH.equals(reviewedRule.getTaskDefKey())){
			reviewedRule.setProcessSequence("2");
		}
		if (Constants.UTASK_DQFKSH.equals(reviewedRule.getTaskDefKey())||Constants.UTASK_DQFKJLSH.equals(reviewedRule.getTaskDefKey())){
			reviewedRule.setProcessSequence("3");
		}
		if (Constants.UTASK_ZGSFKSH.equals(reviewedRule.getTaskDefKey())||Constants.UTASK_ZGSJLSH.equals(reviewedRule.getTaskDefKey())||Constants.UTASK_ZGSZJLSH.equals(reviewedRule.getTaskDefKey())){
			reviewedRule.setProcessSequence("3");
		}
		return super.dao.findReviewedRule(reviewedRule);
	}

	public List<ReviewedRule> findRule(ReviewedRule reviewedRule) {
		//第一次填  ProcessSequence为空
			if (Constants.UTASK_FGSFKSH.equals(reviewedRule.getTaskDefKey())||Constants.UTASK_FGSJLSH.equals(reviewedRule.getTaskDefKey())){
				reviewedRule.setProcessSequence("1");
			}
			if (Constants.UTASK_QYFKSH.equals(reviewedRule.getTaskDefKey())||Constants.UTASK_QYJLSH.equals(reviewedRule.getTaskDefKey())){
				reviewedRule.setProcessSequence("2");
			}
			if (Constants.UTASK_DQFKSH.equals(reviewedRule.getTaskDefKey())||Constants.UTASK_DQFKJLSH.equals(reviewedRule.getTaskDefKey())){
				reviewedRule.setProcessSequence("3");
			}
			if (Constants.UTASK_ZGSFKSH.equals(reviewedRule.getTaskDefKey())||Constants.UTASK_ZGSJLSH.equals(reviewedRule.getTaskDefKey())||Constants.UTASK_ZGSZJLSH.equals(reviewedRule.getTaskDefKey())){
				reviewedRule.setProcessSequence("3");
			}
			return super.dao.findRule(reviewedRule);
		}

	public List<ReviewedRule> findRuleS(ReviewedRule reviewedRule) {
			String range = super.dao.findApplyAmont(reviewedRule);
			if (Constants.UTASK_FGSFKSH.equals(reviewedRule.getTaskDefKey())||Constants.UTASK_FGSJLSH.equals(reviewedRule.getTaskDefKey())){
				reviewedRule.setProcessSequence("1");
			}
			if (Constants.UTASK_QYFKSH.equals(reviewedRule.getTaskDefKey())||Constants.UTASK_QYJLSH.equals(reviewedRule.getTaskDefKey())){
				reviewedRule.setProcessSequence("2");
			}
			if (Constants.UTASK_DQFKSH.equals(reviewedRule.getTaskDefKey())||Constants.UTASK_DQFKJLSH.equals(reviewedRule.getTaskDefKey())){
				reviewedRule.setProcessSequence("3");
			}
			if (Constants.UTASK_ZGSFKSH.equals(reviewedRule.getTaskDefKey())||Constants.UTASK_ZGSJLSH.equals(reviewedRule.getTaskDefKey())||Constants.UTASK_ZGSZJLSH.equals(reviewedRule.getTaskDefKey())){
				reviewedRule.setProcessSequence("3");
			}
			reviewedRule.setRange(range);
			return super.dao.findRule(reviewedRule);
		}

	// 插入数据
	public void saveRule(List<ReviewedRule> reviewedRulelst, Map<String, String> maps) {
		Iterator<ReviewedRule> it=reviewedRulelst.iterator();

		if (Constants.UTASK_FGSFKSH.equals(maps.get("taskDefKey"))){
			maps.put("processSequence","1");
		}
		else if (Constants.UTASK_QYFKSH.equals(maps.get("taskDefKey"))){
			maps.put("processSequence","2");
		}
		else {
			maps.put("processSequence","3");
		}
		maps.put("bookId", reviewedRulelst.get(0).getReviewedBook());
		String idd = this.dao.checkReviewedNull(maps);
		if (!StringUtils.isNull(idd)){
			super.dao.deleteInfo(maps);
		}
			while(it.hasNext()){
				ReviewedRule reviewedRule = new ReviewedRule();
				reviewedRule = it.next();
				reviewedRule.preInsert();
			}
			super.dao.saveRule(reviewedRulelst,maps);

	}
	// 必填校验
	public String checkReviewed(Map<String, String> maps) {
		return this.dao.checkReviewed(maps);
	}

	public String getProductId(ReviewedRule reviewedRule){
		return this.dao.getProductId(reviewedRule);
	}
	

	public String getProductIdByRecent(ReviewedRule reviewedRule){
		return this.dao.getProductIdByRecent(reviewedRule);
	}
}