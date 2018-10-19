package com.resoft.credit.processSuggestionInfo.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.resoft.credit.processSuggestionInfo.entity.CreStaTaskResult;
import com.resoft.credit.processSuggestionInfo.entity.ProcessSuggestionInfo;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 流程意见DAO接口
 * @author wuxi01
 * @version 2016-03-02
 */
@MyBatisDao
public interface ProcessSuggestionInfoDao extends CrudDao<ProcessSuggestionInfo> {
	
	public ProcessSuggestionInfo findByApplyNo(Map<String, String> params);

	public void updateByApplyNo(ProcessSuggestionInfo processSuggestionInfo);
	/**
	 * 查询总公司批复意见
	 * @param params
	 * @return
	 */
	public List<String> findTopComApproveSugg(Map<String, String> params);
	
	/**
	 * 获取活动的流程执行信息
	 */
	public Map<String, Object> getDataInActRuExecution(String procInstId);
	
	/**
	 * 更改主流程信息
	 */
	public void updateDataInActRuExecution(Map<String, Object> map);
	
	
	/**
	 * 更新任务信息
	 */
	public void updateRuExecutionId(@Param("executionId") String executionId,@Param("procInstId") String procInstId);
	
	
	/**
	 * 删除并行数据
	 */
	public void deleteParallelData(String procInstId);
	
	/**
	 * 保存流程批复结果
	 * @param params
	 * @return 通过，打回，拒绝
	 */
	public void insertDate(CreStaTaskResult creStaTaskResult);
	/**
	 * 取得开始时间，结束时间
	 * @param params
	 * @return 
	 */
	public HashMap<String, Object> getStartTime(ProcessSuggestionInfo processSuggestionInfo);
	/**
	 * 取得进件机构
	 * @param params
	 * @return 
	 */
	public HashMap<Object, Object> getOrg(ProcessSuggestionInfo processSuggestionInfo);

	public HashMap<String, Object> getStartTimeToo(
			ProcessSuggestionInfo processSuggestionInfo);

	public Map<String, Object> getMyTimeCliam(
			ProcessSuggestionInfo processSuggestionInfo);
}