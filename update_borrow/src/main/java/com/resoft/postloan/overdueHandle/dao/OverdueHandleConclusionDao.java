package com.resoft.postloan.overdueHandle.dao;

import java.util.Map;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.postloan.overdueHandle.entity.OverdueHandleConclusion;

/**
 * 逾期处理流程意见DAO接口
 * @author lixing
 * @version 2017-01-06
 */
@MyBatisDao
public interface OverdueHandleConclusionDao extends CrudDao<OverdueHandleConclusion> {
	public OverdueHandleConclusion findConclusionByParams(Map<String, Object> params);
}