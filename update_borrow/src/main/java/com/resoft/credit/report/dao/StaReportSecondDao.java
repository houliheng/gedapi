package com.resoft.credit.report.dao;

import java.util.List;
import java.util.Map;

import com.resoft.credit.report.entity.StaReportFirst;
import com.resoft.credit.report.entity.StaReportSecond;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.act.entity.MyMap;

/**
 * 统计报表2DAO接口
 * @author lixing
 * @version 2016-12-09
 */
@MyBatisDao
public interface StaReportSecondDao extends CrudDao<StaReportSecond> {
	public List<Map<String, Object>> findListFirst(Map<String, Object> params);
	public List<Map<String, Object>> findListSecond(Map<String, Object> params);
	public List<Map<String, Object>> findListFourth(Map<String, Object> params);
	public List<StaReportFirst> findOfficeLevelTwoList();
	public List<MyMap> findPageSecond(MyMap myMap);
}