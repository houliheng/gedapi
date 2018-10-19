package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.act.entity.MyMap;
import com.thinkgem.jeesite.modules.sys.entity.User;

@MyBatisDao("customUserDao")
public interface CustomUserDao extends CrudDao<User> {

	public List<User> findUserList(Map<String, Object> params);
	
	public List<User> findUserLists(User user);
	
	public List<MyMap> findUserList(MyMap paramMap);

	public List<MyMap> findAllUserList(MyMap paramMap);
}
