package com.resoft.postloan.checkDaily.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.postloan.checkDaily.entity.CheckDailyAllocate;

/**
 * 日常检查流程DAO接口
 * 
 * @author wuxi01
 * @version 2016-06-01
 */
@MyBatisDao
public interface CheckDailyAllocateDao extends CrudDao<CheckDailyAllocate> {

	/**
	 * 更新为检查状态（已检查/待检查）
	 * 
	 * @param checkDailyAllocate
	 */
	public void updateAllocateType(CheckDailyAllocate checkDailyAllocate);
}