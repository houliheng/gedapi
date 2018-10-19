package com.resoft.accounting.penaltyInterestRate.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.accounting.penaltyInterestRate.entity.PenaltyInterestRate;

/**
 * 罚息利率调整DAO接口
 * @author wangguodong
 * @version 2016-08-11
 */
@MyBatisDao
public interface PenaltyInterestRateDao extends CrudDao<PenaltyInterestRate> {
	
}