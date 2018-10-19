package com.resoft.credit.custRemoveBind.dao;

import java.util.List;

import com.resoft.credit.custRemoveBind.entity.CustRemoveBind;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.User;

  
@MyBatisDao
public interface CustRemoveBindDao extends CrudDao<CustRemoveBind> {
	//在绑定时查找所有的用户
	public List<User> findUserList(User user);
	//绑定操作
	public void bind(CustRemoveBind custRemoveBind);
	/**
	 * 根据证件类型+证件号查询客户绑定信息
	 * @param custRemoveBind
	 * @return
	 */
	public CustRemoveBind getByIdCard(CustRemoveBind custRemoveBind);
}