/**
 * @reqno: H1512260023
 * @date-designer:2016年01月13日-lirongchao
 * @date-author:2016年01月13日-lirongchao:人员禁件表Entity
 */
package com.resoft.credit.noEntry.entity;

import com.thinkgem.jeesite.modules.sys.entity.User;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 人员禁件表Entity
 * @author lirongchao
 * @version 2016-01-08
 */
public class UserNoEntry extends DataEntity<UserNoEntry> {
	private static final long serialVersionUID = 1L;
	private User user;		// 用户
	private String userId;		// 用户id

	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public UserNoEntry() {
		super();
	}

	public UserNoEntry(String id){
		super(id);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}