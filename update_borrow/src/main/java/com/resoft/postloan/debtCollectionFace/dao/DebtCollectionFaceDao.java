package com.resoft.postloan.debtCollectionFace.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.postloan.debtCollectionFace.entity.DebtCollectionFace;

/**
 * 上门催收DAO接口
 * 
 * @author wangguodong
 * @version 2016-06-02
 */
@MyBatisDao
public interface DebtCollectionFaceDao extends CrudDao<DebtCollectionFace> {
	/**
	 * 指定合约编号进行查询数据条数（催收次数用）
	 */
	public int getDataCountByContractNo(String contractNo);
}