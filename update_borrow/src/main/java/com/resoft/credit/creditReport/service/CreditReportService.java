package com.resoft.credit.creditReport.service;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.modules.act.entity.MyMap;
import com.resoft.credit.creditReport.dao.CreditReportDao;

/**
 * 征信意见书其他信息Service
 * @author wuxi01
 * @version 2016-02-29
 */
@Service @DbType("cre.dbType")
@Transactional(value="CRE",readOnly = true)
public class CreditReportService  {
@Autowired
private CreditReportDao creditReportDao;
	public String findStoragePathByApplyNo(String applyNo){
		return creditReportDao.findStoragePathByApplyNo(applyNo);
	}
	public String findFileNameByApplyNo(String applyNo){
		return creditReportDao.findFileNameByApplyNo(applyNo);
	}
	
	/**
	 * 查询进件下信审报告
	 * @param params
	 * @return
	 */
	public List<MyMap> findPdfList(Map<String, Object> params){
		return creditReportDao.findPdfList(params);
	}
}