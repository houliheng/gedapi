package com.resoft.credit.userGroup.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.credit.userGroup.entity.UserGroup;

/**
 * 群组管理DAO接口
 * @author 
 * @version 2016-03-01
 */
@MyBatisDao("creUserGroupDao")
public interface UserGroupDao extends CrudDao<UserGroup> {

	List<UserGroup> findUserGroupById(@Param("userId")String userId);

	List<UserGroup> findUserGroup();

	void deleteUserGroup(String userId);

	void insertUserGroup(@Param("allyIdList")List<String> allyIdList,@Param("userId") String userId);
}