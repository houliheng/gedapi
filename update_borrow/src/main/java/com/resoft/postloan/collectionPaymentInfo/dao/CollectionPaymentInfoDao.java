package com.resoft.postloan.collectionPaymentInfo.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.postloan.collectionPaymentInfo.entity.CollectionPaymentInfo;

/**
 * 客户催收回款详情DAO接口
 * @author yanwanmei
 * @version 2016-06-14
 */
@MyBatisDao
public interface CollectionPaymentInfoDao extends CrudDao<CollectionPaymentInfo> {
	
}