package com.resoft.outinterface.SV.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.outinterface.SV.dao.SVCustWorkInfoDao;
import com.resoft.outinterface.SV.server.entry.request.SVCustWorkInfo;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;

/**
 * SV回盘客户工作信息Service
 * 
 * @author admin
 * @version 2016-04-21
 */
@Service
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class SVCustWorkInfoService {

	@Autowired
	private SVCustWorkInfoDao svCustWorkInfoDao;

	public SVCustWorkInfo get(String id) {
		return svCustWorkInfoDao.get(id);
	}

	public List<SVCustWorkInfo> findList(SVCustWorkInfo sVCustWorkInfo) {
		return svCustWorkInfoDao.findList(sVCustWorkInfo);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void save(Map<String, Object> baseInfo, List<SVCustWorkInfo> custWorkInfo) {
		baseInfo.put("list", custWorkInfo);
		svCustWorkInfoDao.insert(baseInfo);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void delete(SVCustWorkInfo sVCustWorkInfo) {
		svCustWorkInfoDao.delete(sVCustWorkInfo);
	}

}