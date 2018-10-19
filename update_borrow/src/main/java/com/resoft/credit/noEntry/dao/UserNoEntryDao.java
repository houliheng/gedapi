package com.resoft.credit.noEntry.dao;

import java.util.List;

import com.resoft.credit.noEntry.entity.UserNoEntry;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 人员禁件表DAO接口
 * @author lirongchao
 * @version 2016-01-08
 */
@MyBatisDao
public interface UserNoEntryDao extends CrudDao<UserNoEntry> {
	/**
	 * @reqno: H1512260024
	 * @date-designer:2016年01月13日-lirongchao
	 * @db-z : cre_user_no_entry : id
	 * @date-author:2016年01月13日-lirongchao:根据id批量删除人员禁件列表
	 */	
	public void batchDelete(List<String> idList);
	
	/**
	 * 根据用户ID查询客户禁件列表
	 * @param userId
	 * @return
	 */
	public List<UserNoEntry> findListByUserId(String userId);
}