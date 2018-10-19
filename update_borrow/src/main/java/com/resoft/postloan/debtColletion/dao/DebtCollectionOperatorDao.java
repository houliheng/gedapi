package com.resoft.postloan.debtColletion.dao;

import com.resoft.postloan.debtColletion.entity.DebtCollectionOperator;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 合同带催收统计DAO接口
 * @author wangguodong
 * @version 2016-06-02
 */
@MyBatisDao
public interface DebtCollectionOperatorDao extends CrudDao<DebtCollectionOperator> {
	
}