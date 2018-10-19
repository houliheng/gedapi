package com.resoft.credit.applyRegister.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.applyRegister.dao.VideoParamDao;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;

@Service
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class VideoParamService {
	@Autowired
	private VideoParamDao videoParamDao;
	public String getAuthorityLevel(String taskDefKey)  {
		String authorityLevel= videoParamDao.getAuthorityLevel(taskDefKey);
		return authorityLevel;
	}
}
