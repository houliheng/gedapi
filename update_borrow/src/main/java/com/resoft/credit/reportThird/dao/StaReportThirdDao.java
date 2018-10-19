package com.resoft.credit.reportThird.dao;

import java.util.List;
import java.util.Map;

import com.resoft.credit.reportThird.entity.StaReportThird;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface StaReportThirdDao extends CrudDao<StaReportThird> {
	List<StaReportThird> findListThird(Map<String, Object> params);
}
