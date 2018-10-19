package com.resoft.postloan.checkRemove.dao;

import com.resoft.postloan.checkRemove.entity.CheckRemove;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 核销DAO接口
 * 
 * @author zhaohuakui
 * @version 2016-05-23
 */
@MyBatisDao
public interface CheckRemoveDao extends CrudDao<CheckRemove> {
	/**
	 * 查询指定合同号的申请记录
	 */
	public CheckRemove findCheckRemoveByContractNo(CheckRemove checkRemove);
}