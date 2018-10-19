package com.resoft.outinterface.SV.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.outinterface.SV.dao.SVCustInfoDao;
import com.resoft.outinterface.SV.server.entry.request.SVCustInfo;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;

/**
 * SV回盘客户基本信息Service
 * 
 * @author admin
 * @version 2016-04-21
 */
@Service
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class SVCustInfoService {

	@Autowired
	private SVCustInfoDao svCustInfoDao;

	public SVCustInfo get(String id) {
		return svCustInfoDao.get(id);
	}

	public List<SVCustInfo> findList(SVCustInfo sVCustInfo) {
		return svCustInfoDao.findList(sVCustInfo);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void save(Map<String, Object> baseInfo, List<SVCustInfo> custInfo) {
		baseInfo.put("list", custInfo);
		svCustInfoDao.insert(baseInfo);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void delete(SVCustInfo sVCustInfo) {
		svCustInfoDao.delete(sVCustInfo);
	}

}