package com.resoft.credit.applyRegister.dao;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface VideoParamDao{
	public String getAuthorityLevel(String taskDefKey); 
}
