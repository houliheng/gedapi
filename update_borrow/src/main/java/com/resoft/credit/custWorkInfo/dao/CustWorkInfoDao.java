package com.resoft.credit.custWorkInfo.dao;

import java.util.Map;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.credit.custWorkInfo.entity.CustWorkInfo;

/**
 * 客户工作信息表DAO接口
 * @author gaofeng
 * @version 2016-02-25
 */
@MyBatisDao
public interface CustWorkInfoDao extends CrudDao<CustWorkInfo> {
	
	/**
	 * 根据客户ID查询客户工作信息
	 * @param map
	 * @return
	 */
	public CustWorkInfo findCustWorkInfoByCustId(Map<String, String> map);
}