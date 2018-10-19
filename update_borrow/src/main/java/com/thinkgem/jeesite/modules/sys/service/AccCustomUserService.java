package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.dao.AccCustomUserDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
@Service("accCustomUserService")
@DbType("sys.dbType")
@Transactional(value="RTSSYS",readOnly = true)
public class AccCustomUserService extends CrudService<AccCustomUserDao, User> {

	public List<User> findUserList(Map<String, Object> params){
		return super.dao.findUserList(params);
	}
}
