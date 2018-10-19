/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.resoft.credit.blacklist.dao;


import java.util.List;
import java.util.Map;

import com.resoft.credit.blacklist.entity.Blacklist;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 黑名单表DAO接口
 * @author lirongchao
 * @version 2015-12-22
 */
@MyBatisDao
public interface BlacklistDao extends CrudDao<Blacklist> {
	
	/**
	 * 根据证件类型、证件号获取黑名单记录信息
	 * CRE_信贷审批_进件管理_个人客户登记_功能重构
	 * 		参数类型修改为Map
	 */
	public List<Blacklist> findBlacklistByIdCard(Map<String,String> params);
}