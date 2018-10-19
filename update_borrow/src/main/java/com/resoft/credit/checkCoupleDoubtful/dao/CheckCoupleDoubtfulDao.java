package com.resoft.credit.checkCoupleDoubtful.dao;

import java.util.List;
import java.util.Map;

import com.resoft.credit.checkCoupleDoubtful.entity.CheckCoupleDoubtful;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 两人外访DAO接口
 * @author yanwanmei
 * @version 2016-02-27
 */
@MyBatisDao
public interface CheckCoupleDoubtfulDao extends CrudDao<CheckCoupleDoubtful> {
	
	public String getCheckCoupleDoubtfulCount(Map<String,String> param);
	public String getCheckCoupleDoubtfulCountForNewJob(Map<String,String> param);
	public List<CheckCoupleDoubtful> getCheckCoupleDoubtfulByApplyNo(Map<String,String> params);
	public List<CheckCoupleDoubtful> getCheckCoupleDoubtfulByApplyNoForNewJob(Map<String,String> params);
}