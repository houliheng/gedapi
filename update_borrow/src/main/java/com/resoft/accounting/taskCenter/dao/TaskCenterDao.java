package com.resoft.accounting.taskCenter.dao;

import java.util.List;
import java.util.Map;

import com.resoft.accounting.taskCenter.entity.ActHiTaskInst;
import com.resoft.accounting.taskCenter.entity.ActRuTask;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.act.entity.MyMap;

@MyBatisDao
public interface TaskCenterDao{
	/**
	 * @reqno:H1511100047
	 * @date-designer:20151111-huangxuecheng
	 * @date-author:20151111-huangxuecheng:CRE_信贷审批_进件管理_个人客户登记列表_流程图、流程轨迹.描述：根据传入的procInstId查询ActRuTask实体类，封装了参数execId和proDefId
	 * 									         提供流程图、流程轨迹使用
	 */
	//在act_ru_task中根据proInsId 查询出execId和proDefId
	public ActRuTask selectSingleRunTask(String procInstId);
	/**
	 * @reqno:H1511100047
	 * @date-designer:20151111-huangxuecheng
	 * @date-author:20151111-huangxuecheng:CRE_信贷审批_进件管理_个人客户登记列表_流程图、流程轨迹.描述：根据传入的procInstId查询ActHiTaskInst实体类，封装了参数execId和proDefId
	 * 									         提供流程图、流程轨迹使用
	 */

	// 根据 节点名称 还有流程id 查询运行时任务的信息
	public Integer selectActRuTaskByParamMap(Map<String,String> paramMap);

	//在act_hi_taskinst中根据proInsId 查询出execId和proDefId
	public List<ActHiTaskInst> selectHisTask(String procInstId);
	/**
	 * @reqno:H1510280020
	 * @date-designer:20151117-huangxuecheng
	 * @date-author:20151117-huangxuecheng:CRE_信贷审批_任务中心_在办流程监控.开发说明：实现在办流程监控的查询列表以及分页功能，查询出count在service中封装在page中								        
	 */
	public int selectToDoProcessCount(Map<String, Object> paramMap);
	/**
	 * @reqno:H1510280020
	 * @date-designer:20151117-huangxuecheng
	 * @date-author:20151117-huangxuecheng:CRE_信贷审批_任务中心_在办流程监控.开发说明：实现在办流程监控的查询列表以及分页功能，查询出list在service中封装在page中								        
	 */
	public List<MyMap> selectToDoProcess(Map<String, Object> paramMap);
	/**
	 * @reqno:H1510280021
	 * @date-designer:20151117-huangxuecheng
	 * @date-author:20151117-huangxuecheng:CRE_信贷审批_任务中心_办结流程监控.开发说明：实现办结流程监控的查询列表以及分页功能，查询出count在service中封装在page中									        
	 */
	public int selectDoneProcessCount(Map<String, Object> paramMap);
	/**
	 * @reqno:H1510280021
	 * @date-designer:20151117-huangxuecheng
	 * @date-author:20151117-huangxuecheng:CRE_信贷审批_任务中心_办结流程监控.开发说明：实现办结流程监控的查询列表以及分页功能，查询出list在service中封装在page中							        
	 */
	public List<MyMap> selectDoneProcess(Map<String, Object> paramMap);
	
	/**
	 * 保证金退还待办任务查询
	 * */
	public List<MyMap> findMarginRepayToDoList(Map<String, Object> paramMap);
	
	/**
	 * 保证金退还待办任务查询
	 * */
	public List<MyMap> findMarginRepayDoneList(Map<String, Object> paramMap);
	
	/**
	 * 违约金罚息减免待办任务查询
	 * */
	public List<MyMap> findPenaltyFineExemptToDoList(Map<String, Object> paramMap);
	
	/**
	 * 违约金罚息减免已办任务查询
	 * */
	public List<MyMap> findPenaltyFineExemptDoneList(Map<String, Object> paramMap);
	/**
	 * 提前还款待办
	 */
	public List<MyMap> findAdvanceRepayToDoList(Map<String, Object> paramMap);
	/**
	 * 提前还款已办
	 */
	public List<MyMap> findAdvanceRepayDoneList(Map<String, Object> paramMap);
}
