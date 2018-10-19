package com.resoft.credit.taskCenter.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.resoft.credit.taskCenter.entity.ActHiTaskInst;
import com.resoft.credit.taskCenter.entity.ActRuTask;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.act.entity.MyMap;

@MyBatisDao("creTaskCenterDao")
public interface TaskCenterDao {
	// 在act_ru_task中根据proInsId 查询出execId和proDefId
	public ActRuTask selectSingleRunTask(String procInstId);

	// 在act_hi_taskinst中根据proInsId 查询出execId和proDefId
	public List<ActHiTaskInst> selectHisTask(String procInstId);

	public int selectToDoProcessCount(Map<String, Object> paramMap);

	public List<MyMap> selectToDoProcess(Map<String, Object> paramMap);

	public int selectDoneProcessCount(Map<String, Object> paramMap);

	public List<MyMap> selectDoneProcess(Map<String, Object> paramMap);

	public void insertOfficeApplyRelation(@Param("applyNo") String applyNo, @Param("officeId") String officeId);
	
	public void updateOfficeApplyRelationByApplyNo(@Param("applyNo") String applyNo, @Param("officeId") String officeId);

	/**
	 * 待办任务查询
	 * */
	public List<MyMap> findToDoListByPage(Map<String, Object> paramMap);

	/**
	 * 已办任务查询
	 * */
	public List<MyMap> findDoneTaskList(Map<String, Object> paramMap);
	

	/**
	 * 已办总任务查询
	 * */
	public List<MyMap> findAllDoneTaskList(Map<String, Object> paramMap);

	/**
	 * 已分配查询
	 */
	public List<MyMap> findDoneClaimlist(Map<String, Object> paramMap);

	/**
	 * 保存已分配信息
	 */
	public void saveTaskAllocate(Map<String, Object> paramMap);

	/**
	 * 待办任务查询
	 * */
	public List<MyMap> findAllToDoListByPage(Map<String, Object> paramMap);
	
	/**
	 * 已办监控查询
	 * */
	public List<MyMap> allFindDoneOrFinishList(Map<String, Object> paramMap);
	/**
	 * 拒绝签收
	 */
	public void refuseToSignTask(String taskId);
	
	/**
	 * 管理员合同审核、财务放款待办任务查询
	 * */
	public List<MyMap> findAllToDoListByPageTwo(Map<String, Object> paramMap);
	
	/**
	 * 非管理员合同审核、财务放款待办任务查询
	 * */
	public List<MyMap> findToDoListByPageTwo(Map<String, Object> paramMap);
	/**
	 * 合同审核已办任务查询
	 * */
	public List<MyMap> findDoneTaskListHtsh(Map<String, Object> paramMap);

	/**
	 * 得到当前任务人的id
	 * */
	public String getUserByProcess(@Param("procInstId") String procInstId);

	public List<MyMap> findUnderAllToDoListByPage(Map<String, Object> paramMap);

    HashMap<String,String> findUnderLoanFirstDate(@Param("procInstId") String procInstId);

    List<MyMap> findUnderAllDoneListByPage(MyMap paramMap);

	List<MyMap> findUnderEndProcess(MyMap paramMap);

}
