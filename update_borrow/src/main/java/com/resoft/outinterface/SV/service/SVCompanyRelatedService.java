package com.resoft.outinterface.SV.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.outinterface.SV.dao.SVCompanyRelatedDao;
import com.resoft.outinterface.SV.server.entry.request.SVCompanyRelated;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;

/**
 * SV回盘关联企业Service
 * 
 * @author admin
 * @version 2016-04-21
 */
@Service
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class SVCompanyRelatedService {

	@Autowired
	private SVCompanyRelatedDao svCompanyRelatedDao;

	public SVCompanyRelated get(String id) {
		return svCompanyRelatedDao.get(id);
	}

	public List<SVCompanyRelated> findList(SVCompanyRelated sVCompanyRelated) {
		return svCompanyRelatedDao.findList(sVCompanyRelated);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void save(Map<String, Object> baseInfo, List<SVCompanyRelated> companyRelatedList) {
		baseInfo.put("list", companyRelatedList);
		svCompanyRelatedDao.insert(baseInfo);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void delete(SVCompanyRelated sVCompanyRelated) {
		svCompanyRelatedDao.delete(sVCompanyRelated);
	}

}