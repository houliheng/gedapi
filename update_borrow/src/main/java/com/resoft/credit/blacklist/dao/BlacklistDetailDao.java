/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.resoft.credit.blacklist.dao;

import com.resoft.credit.blacklist.entity.BlacklistDetail;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 黑名单详情表DAO接口
 * @author lirongchao
 * @version 2016-01-07
 */
@MyBatisDao
public interface BlacklistDetailDao extends CrudDao<BlacklistDetail> {
	/**
	 * @reqno: H1512210033
	 * @date-designer:2015年12月24日-lirongchao
	 * @date-author:2015年12月24日-lirongchao:1.查询条件-【客户名称（模糊查询）、状态】、【证件类型、证件号码（模糊查询）】、【创建日期（开始结束时间）】；
											查询表单按钮-查询、重置；
											2.列表数据项-单选框按钮、客户名称、状态（黑名单、白名单）、证件类型、证件号、创建人、创建日期、操作（详情）
											3.列表排序-创建日期降序
											4.表头按钮-加黑、刷白
											当前环节-黑名单详情dao
	 */	
}