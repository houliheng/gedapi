package com.resoft.multds.credit.evaluateAsset.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.multds.credit.evaluateAsset.dao.MortgageFindCustNameDao;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;

/**
 * 根绝角色类型查询人员姓名ListService
 * @author zhaohuakui
 * @version 2016-05-12
 */
@Service
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class MortgageFindCustNameService{

	@Autowired
	private MortgageFindCustNameDao mortgageFindCustNameDao;

	public List<Map<String, String>> findCustNameByRoleType(Map<String, String> params) {
		return mortgageFindCustNameDao.findCustNameByRoleType(params);
	}

	public List<Map<String, String>> getContactInfoByApplyNo(Map<String, String> params) {
		return mortgageFindCustNameDao.getContactInfoByApplyNo(params);
	}

	
}