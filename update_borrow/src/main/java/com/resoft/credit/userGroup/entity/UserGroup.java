package com.resoft.credit.userGroup.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 群组管理Entity
 * @author 
 * @version 2016-03-01
 */
public class UserGroup extends DataEntity<UserGroup> {
	private static final long serialVersionUID = 1L;
	private String  id; //id
	private String  userId; //用户id
	private String  allyId; //群组id
	private String  code;
	private String  allyName; //群组名称
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAllyId() {
		return allyId;
	}
	public void setAllyId(String allyId) {
		this.allyId = allyId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getAllyName() {
		return allyName;
	}
	public void setAllyName(String allyName) {
		this.allyName = allyName;
	}
	public UserGroup() {
		super();
	}
	
	
	
}