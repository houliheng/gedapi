package com.resoft.credit.guranteeProjectList.dao;

import com.resoft.credit.guranteeProjectList.entity.GuranteeProjectList;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
* @author guoshaohua
* @version 2018年4月19日 下午2:17:00
* 
*/
@MyBatisDao
public interface GuranteeProjectListDao extends CrudDao<GuranteeProjectList>{
	public GuranteeProjectList findGuranteeProjectListByContractNo(String contractNo);
}
