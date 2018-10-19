package com.resoft.credit.stockTaskDistribute.dao;

import java.util.List;
import java.util.Map;

import com.resoft.credit.stockTaskDistribute.entity.StockTaskDistribute;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 股权估值服务列表DAO接口
 * @author jml
 * @version 2017-09-06
 */
@MyBatisDao
public interface StockTaskDistributeDao extends CrudDao<StockTaskDistribute> {
	
	public List<User> findUserAndOrleList(Map<String, Object> params);
}