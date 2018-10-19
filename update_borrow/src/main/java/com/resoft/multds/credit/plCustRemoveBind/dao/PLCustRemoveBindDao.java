package com.resoft.multds.credit.plCustRemoveBind.dao;

import com.resoft.multds.credit.plCustRemoveBind.entity.PLCustRemoveBind;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface PLCustRemoveBindDao extends CrudDao<PLCustRemoveBind> {

	// 绑定操作
	public void bind(PLCustRemoveBind plCustRemoveBind);

	/**
	 * 根据证件类型+证件号查询客户绑定信息
	 * 
	 * @param custRemoveBind
	 * @return
	 */
	public PLCustRemoveBind getByIdCard(String idNum);
}