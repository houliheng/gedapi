package com.resoft.credit.processSuggestionInfo.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.resoft.common.utils.Constants;
import com.resoft.credit.processSuggestionInfo.dao.ProcessSuggestionInfoDao;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;

@Service
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class RuTaskService {

	@Autowired
	private ProcessSuggestionInfoDao processSuggestionInfoDao;

	@Transactional(value = "CRE", readOnly = false)
	public void updateActTask(String procInstId) {
		processSuggestionInfoDao.updateRuExecutionId(procInstId, procInstId);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void dealData(String procInstId) {
		Map<String, Object> execution = processSuggestionInfoDao.getDataInActRuExecution(procInstId);
		Map<String, Object> map = Maps.newHashMap();
		map.put("rev", ((Integer) execution.get("REV_") + 1));
		map.put("actId", Constants.UTASK_SQLR);
		map.put("isActive", 1);
		map.put("procInstId", procInstId);
		processSuggestionInfoDao.updateDataInActRuExecution(map);
		processSuggestionInfoDao.deleteParallelData(procInstId);
	}

}
