package com.resoft.outinterface.SV.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.outinterface.SV.dao.SVCompanyInfoDao;
import com.resoft.outinterface.SV.server.entry.request.SVCompanyInfo;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;

/**
 * SV回盘企业Service
 * 
 * @author admin
 * @version 2016-04-21
 */
@Service
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class SVCompanyInfoService {

	@Autowired
	private SVCompanyInfoDao svCompanyInfoDao;

	public SVCompanyInfo get(String id) {
		return svCompanyInfoDao.get(id);
	}

	public List<SVCompanyInfo> findList(SVCompanyInfo sVCompanyInfo) {
		return svCompanyInfoDao.findList(sVCompanyInfo);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void save(Map<String, Object> baseInfo, List<SVCompanyInfo> allCompanyInfo) {
		baseInfo.put("list", allCompanyInfo);
		svCompanyInfoDao.insert(baseInfo);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void delete(SVCompanyInfo sVCompanyInfo) {
		svCompanyInfoDao.delete(sVCompanyInfo);
	}

}