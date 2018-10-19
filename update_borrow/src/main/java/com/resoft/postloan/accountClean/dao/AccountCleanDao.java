package com.resoft.postloan.accountClean.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.act.entity.MyMap;
import com.resoft.postloan.accountClean.entity.AccountClean;

/**
 * 账务清收DAO接口
 * @author yanwanmei
 * @version 2016-06-02
 */
@MyBatisDao
public interface AccountCleanDao extends CrudDao<AccountClean> {
	public List<MyMap> getAccountCleanInfo(MyMap paramMap);
}