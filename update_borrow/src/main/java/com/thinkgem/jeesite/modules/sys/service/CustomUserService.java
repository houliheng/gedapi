package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.act.entity.MyMap;
import com.thinkgem.jeesite.modules.sys.dao.CustomUserDao;
import com.thinkgem.jeesite.modules.sys.entity.User;

@Service("customUserService")@DbType("sys.dbType")
@Transactional(value="RTSSYS",readOnly = true)
public class CustomUserService extends CrudService<CustomUserDao, User> {

	public List<User> findUserList(Map<String, Object> params){
		return super.dao.findUserList(params);
	}
	
	public List<User> findUserLists(User user){
		return this.dao.findUserLists(user);
	}
	
	public Page<MyMap> getUserList(Page<MyMap> page, MyMap paramMap){
		paramMap.setPage(page);
		page.setList(this.dao.findUserList(paramMap));
		return page;
	}
	
	public Page<MyMap> findAllUserList(Page<MyMap> page, MyMap paramMap){
		paramMap.setPage(page);
		page.setList(this.dao.findAllUserList(paramMap));
		return page;
	}
}
