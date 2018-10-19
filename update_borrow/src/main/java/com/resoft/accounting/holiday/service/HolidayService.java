package com.resoft.accounting.holiday.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.accounting.holiday.dao.HolidayDao;
import com.resoft.accounting.holiday.entity.Holiday;
import com.thinkgem.jeesite.common.persistence.Page;import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;

@Service@DbType("acc.dbType")
@Transactional(value="ACC",readOnly = true)
public class HolidayService extends CrudService<HolidayDao, Holiday> {

	public Page<Holiday> findPage(Page<Holiday> page, Holiday holiday) {
		return super.findPage(page, holiday);
	}

	// 查询所有节假日日期
	public int findDateCount(Holiday holiday) {
		return dao.findDateCount(holiday);
	}

}