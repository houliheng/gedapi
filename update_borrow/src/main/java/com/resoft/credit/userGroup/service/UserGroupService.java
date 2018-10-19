package com.resoft.credit.userGroup.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.resoft.credit.userGroup.dao.UserGroupDao;
import com.resoft.credit.userGroup.entity.UserGroup;

/**
 * 群组管理Service
 * @author 
 * @version 2016-07-15
 */
@Service @DbType("cre.dbType")
@Transactional(value="CRE",readOnly = false)
public class UserGroupService extends CrudService<UserGroupDao, UserGroup> {

	public  List<UserGroup> findUserGroupById(String userId) {
		return super.dao.findUserGroupById(userId);
	}

	public List<UserGroup> findUserGroup() {
		return super.dao.findUserGroup();
	}


	public void deleteUserGroup(String userId) {
		super.dao.deleteUserGroup(userId);
	}
	
	public void updateUserGroup(List<String> allyIdList, String userId) {
		super.dao.deleteUserGroup(userId);
		super.dao.insertUserGroup(allyIdList,userId);
	}

}