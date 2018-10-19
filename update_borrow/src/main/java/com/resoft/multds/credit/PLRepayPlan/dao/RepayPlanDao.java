package com.resoft.multds.credit.PLRepayPlan.dao;

import com.resoft.multds.credit.PLRepayPlan.entity.PLRateCapital;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface RepayPlanDao {
	public List<PLRateCapital> getRateCapitalCurr(Map<String, String> params);

	public String getInterestByApplyNo(String applyNo);

	public List<Map<String, String>> getOverduePenalty();
}
