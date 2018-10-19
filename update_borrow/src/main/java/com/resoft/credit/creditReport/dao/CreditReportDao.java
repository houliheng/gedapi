package com.resoft.credit.creditReport.dao;


import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.act.entity.MyMap;

/**
 * 征信意见书其他信息DAO接口
 * @author wuxi01
 * @version 2016-02-29
 */
@MyBatisDao
public interface CreditReportDao {
	
	public String findStoragePathByApplyNo(String applyNo);
	public String findFileNameByApplyNo(String applyNo);
	
	/**
	 * 查询进件下信审报告
	 * @param params
	 * @return
	 */
	public List<MyMap> findPdfList(Map<String, Object> params);
}