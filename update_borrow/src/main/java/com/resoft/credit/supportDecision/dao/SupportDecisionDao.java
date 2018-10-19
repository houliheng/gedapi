package com.resoft.credit.supportDecision.dao;

import com.resoft.credit.supportDecision.entity.SupportDecision;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 支持决策DAO接口
 * @author wuxi01
 * @version 2016-03-25
 */
@MyBatisDao
public interface SupportDecisionDao extends CrudDao<SupportDecision> {
	
}