package com.resoft.postloan.taskCenter.dao;

import java.util.List;
import java.util.Map;

import com.resoft.postloan.taskCenter.entity.ActHiTaskInst;
import com.resoft.postloan.taskCenter.entity.ActRuTask;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.act.entity.MyMap;

@MyBatisDao("plTaskCenterDao")
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
	 * 展期待办任务查询
	 * */
	public List<MyMap> findContractExtendToDoList(Map<String, Object> paramMap);
	
	/**
	 * 展期已办任务查询
	 * */
	public List<MyMap> findContractExtendDoneList(Map<String, Object> paramMap);
	
	/**
	 * 待办任务列表
	 */
	public List<MyMap> findTaskToDoList(Map<String, Object> paramMap);
	/**
	 * 管理员待办列表
	 */
	public List<MyMap> findTaskToDoListAdmin(Map<String, Object> paramMap);
	/**
	 * 已办列表
	 */
	public List<MyMap> findTaskToDoneList(Map<String, Object> paramMap);
}
