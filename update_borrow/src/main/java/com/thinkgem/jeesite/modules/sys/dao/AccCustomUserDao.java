package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.User;

@MyBatisDao("accCustomUserDao")
public interface AccCustomUserDao extends CrudDao<User> {

	public List<User> findUserList(Map<String, Object> params);
}
