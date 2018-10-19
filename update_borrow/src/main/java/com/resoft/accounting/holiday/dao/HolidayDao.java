package com.resoft.accounting.holiday.dao;

import com.resoft.accounting.holiday.entity.Holiday;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface HolidayDao extends CrudDao<Holiday> {
	
	//查询所有节假日日期
	public int findDateCount(Holiday holiday);
	
}