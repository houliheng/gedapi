package com.resoft.postloan.checkIndeed.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.postloan.checkIndeed.entity.CheckIndeed;

/**
 * 借后实地外访DAO接口
 * @author wangguodong
 * @version 2016-08-31
 */
@MyBatisDao
public interface CheckIndeedDao extends CrudDao<CheckIndeed> {
	
	public List<CheckIndeed> getCheckIndeedByAllocateId(String allocateId);
	
}