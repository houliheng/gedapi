package com.resoft.postloan.debtCollectionLegal.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.postloan.debtCollectionLegal.entity.DebtCollectionLegal;

/**
 * 法务催收DAO接口
 * 
 * @author wangguodong
 * @version 2016-06-02
 */
@MyBatisDao
public interface DebtCollectionLegalDao extends CrudDao<DebtCollectionLegal> {
	/**
	 * 指定合约编号进行查询数据条数（催收次数用）
	 */
	public int getDataCountByContractNo(String contractNo);
}