package com.resoft.postloan.borrowNew.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.postloan.borrowNew.entity.BorrowNew;

/**
 * 借新还旧信息DAO接口
 * @author wuxi01
 * @version 2016-06-17
 */
@MyBatisDao
public interface BorrowNewDao extends CrudDao<BorrowNew> {
	
}