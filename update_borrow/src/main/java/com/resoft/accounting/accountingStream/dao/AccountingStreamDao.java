package com.resoft.accounting.accountingStream.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.accounting.accountingStream.entity.AccountingStream;

/**
 * 账务流水DAO接口
 * @author wangguodong
 * @version 2016-08-12
 */
@MyBatisDao
public interface AccountingStreamDao extends CrudDao<AccountingStream> {
	
}