package com.resoft.credit.rateInterest.dao;



import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.credit.rateInterest.entity.RateInterest;

/**
 * 还款计划DAO接口
 * @author wuxi01
 * @version 2016-03-01
 */
@MyBatisDao("creRateInterestDao")
public interface RateInterestDao extends CrudDao<RateInterest> {
	public List<RateInterest> getLoanRepayType(Map<String,String> param);
	
	public void saveAll(Map<String, Object> param);
	
	public void banchDelete(Map<String, Object> param);
	
	public List<RateInterest> checkRateInterestIsDouble(RateInterest rateInterestInsert);
	
}