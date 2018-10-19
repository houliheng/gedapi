package com.resoft.credit.checkDoubtful.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.credit.checkDoubtful.entity.CheckDoubtful;

/**
 * 借前外访DAO接口
 * @author yanwanmei
 * @version 2016-03-01
 */
@MyBatisDao
public interface CheckDoubtfulDao extends CrudDao<CheckDoubtful> {
	
	public List<CheckDoubtful> getPageByApplyNo(String applyNo);

	public Object getFullMsg(String hitaskid);
	
}