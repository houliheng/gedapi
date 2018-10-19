/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.resoft.credit.loanApply.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.credit.loanApply.entity.CreCustContact;

/**
 * 联系人信息表DAO接口
 * @author lirongchao
 * @version 2015-10-22
 */
@MyBatisDao
public interface CreCustContactDao extends CrudDao<CreCustContact> {
	/**
	 * @reqno: H1510080107
	 * @date-designer:20151022-lirongchao
	 * @date-author:20151022-lirongchao: 需求-联系人列表数据项：复选择框、姓名、人员类型、移动电话、是否知晓本次贷款、住宅电话、单位名称
											列表排序：创建时间降序
											表头按钮：新增、修改、删除、详情
										当前环节-创建联系人信息表的Dao层
	 */
}