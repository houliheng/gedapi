package com.resoft.postloan.debtCollectionOut.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.postloan.debtCollectionOut.entity.DebtCollectionOut;

/**
 * 外包催收DAO接口
 * 
 * @author wangguodong
 * @version 2016-06-02
 */
@MyBatisDao
public interface DebtCollectionOutDao extends CrudDao<DebtCollectionOut> {
	/**
	 * 指定合约编号进行查询数据条数（催收次数用）
	 */
	public int getDataCountByContractNo(String contractNo);
}