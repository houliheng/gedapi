package com.resoft.accounting.WithdrawStream.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.accounting.WithdrawStream.entity.CreWithdrawStream;

/**
 * 冠E贷充值流水记录DAO接口
 * @author gsh
 * @version 2018-04-02
 */
@MyBatisDao
public interface CreWithdrawStreamDao extends CrudDao<CreWithdrawStream> {
	
}