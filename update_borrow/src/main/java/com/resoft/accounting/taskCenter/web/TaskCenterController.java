package com.resoft.accounting.taskCenter.web;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.resoft.accounting.common.utils.Constants;
import com.resoft.accounting.common.utils.DateUtils;
import com.resoft.accounting.taskCenter.entity.ActTaskParam;
import com.resoft.accounting.taskCenter.entity.TaskVO;
import com.resoft.accounting.taskCenter.service.TaskCenterService;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.act.entity.Act;
import com.thinkgem.jeesite.modules.act.entity.MyMap;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.sys.entity.CustomOffice;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 任务中心 Controller
 */
@Controller
@RequestMapping(value = "${adminPath}/accounting/taskCenter")
public class TaskCenterController extends BaseController {
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private TaskCenterService taskCenterService;
	@Autowired
	private TaskService taskService;

	private static StringBuffer marginRepaySqlBuffer = new StringBuffer().append(" SELECT ").append(" a.APPLY_NO AS APPLY_NO,").append(" a.CUST_NAME AS CUST_NAME,").append(" a.CONTRACT_NO AS CONTRACT_NO,").append(" o4.`name` AS REGIST_ORG_NAME,").append(" o3.`name` AS AREA_NAME, ").append(" o2.`name` AS LARGE_AREA_NAME,").append(" b.PROC_INS_ID AS PROC_INS_ID,").append(" '' AS PERIOD_NUM,").append(" b.CREATE_DATE AS CREATE_DATE").append(" FROM acc_contract a").append(" INNER JOIN acc_apply_margin_repay b ON a.CONTRACT_NO = b.CONTRACT_NO").append(" LEFT OUTER JOIN sys_office t3 ON t3.id = a.OPERATE_ORG_ID").append(" LEFT OUTER JOIN sys_office o4 ON o4.id = a.ORG_LEVEL4 AND o4.type = '1'").append(" LEFT OUTER JOIN sys_office o3 ON o3.id = a.ORG_LEVEL3 AND o3.type = '1'").append(" LEFT OUTER JOIN sys_office o2 ON o2.id = a.ORG_LEVEL2 AND o2.type = '1'").append(" WHERE 1=1 ");
	private static StringBuffer penaltyFineSqlBuffer = new StringBuffer().append(" SELECT ").append(" a.APPLY_NO AS APPLY_NO,").append(" a.CUST_NAME AS CUST_NAME,").append(" a.CONTRACT_NO AS CONTRACT_NO,").append(" o4.`name` AS REGIST_ORG_NAME,").append(" o3.`name` AS AREA_NAME,").append(" o2.`name` AS LARGE_AREA_NAME,").append(" b.PROC_INS_ID AS PROC_INS_ID,").append(" b.PERIOD_NUM AS PERIOD_NUM,").append(" b.CREATE_DATE AS CREATE_DATE").append(" FROM acc_contract a").append(" INNER JOIN acc_apply_penalty_fine_exempt b ON a.CONTRACT_NO = b.CONTRACT_NO").append(" LEFT OUTER JOIN sys_office t3 ON t3.id = a.OPERATE_ORG_ID").append(" LEFT OUTER JOIN sys_office o4 ON o4.id = a.ORG_LEVEL4 AND o4.type = '1'").append(" LEFT OUTER JOIN sys_office o3 ON o3.id = a.ORG_LEVEL3 AND o3.type = '1'").append(" LEFT OUTER JOIN sys_office o2 ON o2.id = a.ORG_LEVEL2 AND o2.type = '1'").append(" WHERE 1=1 ");

	private String generateQuery(TaskVO taskVO) {
		String marginRepaySql = marginRepaySqlBuffer.toString();
		marginRepaySql = appendConditions(taskVO, marginRepaySql);
		String penaltyFineSql = penaltyFineSqlBuffer.toString();
		penaltyFineSql = appendConditions(taskVO, penaltyFineSql);
		return marginRepaySql + "UNION ALL" + penaltyFineSql;
	}

	// 待办任务
	@RequestMapping(value = "todoList")
	public String todoList(HttpServletRequest request, HttpServletResponse response, Model model, TaskVO taskVO) {
		MyMap paramMap = new MyMap();
		paramMap.put("taskVO", taskVO);
		String querySql = generateQuery(taskVO);
		Page<MyMap> page = actTaskService.todoListNewByPage(new Page<MyMap>(request, response), generateParamMap(paramMap, querySql));
		model.addAttribute("page", page);
		model.addAttribute("taskVO", taskVO);
		model.addAttribute("submitPath", "todoList");
		// 流程下拉框
		List<ProcessDefinition> processList = taskCenterService.loadActiveProcessList();
		model.addAttribute("processList", processList);
		return "app/accounting/taskCenter/todo_business_list";
	}

	private MyMap setParamMap(TaskVO taskVO) {
		MyMap paramMap = new MyMap();
		paramMap.put("userId", UserUtils.getUser().getLoginName());
		Office company = UserUtils.getUser().getCompany();
		paramMap.put("currentOrg", company.getId());
		paramMap.put("currentOrgParentIds", company.getParentIds() + company.getId());
		if (taskVO != null) {
			if (StringUtils.isNotBlank(taskVO.getCustName())) {
				paramMap.put("custName", taskVO.getCustName());
			}
			if (StringUtils.isNotBlank(taskVO.getProDefKey())) {
				paramMap.put("proDefKey", taskVO.getProDefKey());
			}
			if (StringUtils.isNotBlank(taskVO.getTaskDefKey())) {
				paramMap.put("taskDefKey", taskVO.getTaskDefKey());
			}
			if(taskVO.getOffice()!=null){
				if ("4".equals(taskVO.getOffice().getLevelnumString())) {//登记门店
					paramMap.put("orgLevel", "4");
					paramMap.put("dataScope", taskVO.getOffice().getId());
				} else if ("3".equals(taskVO.getOffice().getLevelnumString())) {//区域
					paramMap.put("orgLevel", "3");
					paramMap.put("dataScope", taskVO.getOffice().getId());
				} else if ("2".equals(taskVO.getOffice().getLevelnumString())) {//大区
					paramMap.put("orgLevel", "2");
					paramMap.put("dataScope", taskVO.getOffice().getId());
				}
			}
			if (taskVO.getApplyStartTime() != null) {
				paramMap.put("applyStartTime", taskVO.getApplyStartTime());
			}
			if (taskVO.getApplyEndTime() != null) {
				paramMap.put("applyEndTime", taskVO.getApplyEndTime());
			}
			if (taskVO.getCreateStartTime() != null) {
				paramMap.put("cdateStart", taskVO.getCreateStartTime());
			}
			if (taskVO.getCreateEndTime() != null) {
				paramMap.put("cdateEnd", taskVO.getCreateEndTime());
			}
		}
		return paramMap;
	}

	// 违约金罚息减免待办任务
	@RequestMapping(value = { "todoList/penaltyFineExempt" })
	public String todoPenaltyFineExempt(HttpServletRequest request, HttpServletResponse response, Model model, TaskVO taskVO) {
		MyMap paramMap = setParamMap(taskVO);
		paramMap.put("procDefIdPre", Constants.WORKFLOW_APPLY_MARGIN_REPAY);
		Page<MyMap> page = taskCenterService.findPenaltyFineExemptToDoList(new Page<MyMap>(request, response), paramMap);
		model.addAttribute("page", page);
		if(taskVO.getOffice()!=null){//LevelnumString为空时，回显错误
			if (!"4".equals(taskVO.getOffice().getLevelnumString()) && !"3".equals(taskVO.getOffice().getLevelnumString()) && !"2".equals(taskVO.getOffice().getLevelnumString())) {//登记门店
				taskVO.setOffice(new CustomOffice());
			}
		}
		model.addAttribute("taskVO", taskVO);
		model.addAttribute("noProcessName", true);
		model.addAttribute("submitPath", "todoList/penaltyFineExempt");
		return "app/accounting/taskCenter/todo_business_list";
	}

	// 违约金罚息减免已办任务
	@RequestMapping(value = { "toDoneList/penaltyFineExempt" })
	public String donePenaltyFineExempt(HttpServletRequest request, HttpServletResponse response, Model model, TaskVO taskVO) {
		MyMap paramMap = setParamMap(taskVO);
		paramMap.put("procDefIdPre", Constants.WORKFLOW_APPLY_MARGIN_REPAY);
		Page<MyMap> page = taskCenterService.findPenaltyFineExemptDoneList(new Page<MyMap>(request, response), paramMap);
		model.addAttribute("page", page);
		if(taskVO.getOffice()!=null){//LevelnumString为空时，回显错误
			if (!"4".equals(taskVO.getOffice().getLevelnumString()) && !"3".equals(taskVO.getOffice().getLevelnumString()) && !"2".equals(taskVO.getOffice().getLevelnumString())) {//登记门店
				taskVO.setOffice(new CustomOffice());
			}
		}
		model.addAttribute("taskVO", taskVO);
		model.addAttribute("noProcessName", true);
		model.addAttribute("submitPath", "toDoneList/penaltyFineExempt");
		return "app/accounting/taskCenter/todone_business_list";
	}

	// 保证金退还待办任务
	@RequestMapping(value = { "todoList/marginRepay" })
	public String todoMarginRepay(HttpServletRequest request, HttpServletResponse response, Model model, TaskVO taskVO) {
		MyMap paramMap = setParamMap(taskVO);
		paramMap.put("procDefIdPre", Constants.WORKFLOW_MARGIN_REPAY);
		Page<MyMap> page = taskCenterService.findMarginRepayToDoList(new Page<MyMap>(request, response), paramMap);
		model.addAttribute("page", page);
		if(taskVO.getOffice()!=null){//LevelnumString为空时，回显错误
			if (!"4".equals(taskVO.getOffice().getLevelnumString()) && !"3".equals(taskVO.getOffice().getLevelnumString()) && !"2".equals(taskVO.getOffice().getLevelnumString())) {//登记门店
				taskVO.setOffice(new CustomOffice());
			}
		}
		model.addAttribute("taskVO", taskVO);
		model.addAttribute("noProcessName", true);
		model.addAttribute("submitPath", "todoList/marginRepay");
		return "app/accounting/taskCenter/todo_business_list";
	}

	// 保证金退还已办任务
	@RequestMapping(value = { "toDoneList/marginRepay" })
	public String doneMarginRepay(HttpServletRequest request, HttpServletResponse response, Model model, TaskVO taskVO) {
		MyMap paramMap = setParamMap(taskVO);
		paramMap.put("procDefIdPre", Constants.WORKFLOW_MARGIN_REPAY);
		Page<MyMap> page = taskCenterService.findMarginRepayDoneList(new Page<MyMap>(request, response), paramMap);
		model.addAttribute("page", page);
		if(taskVO.getOffice()!=null){//LevelnumString为空时，回显错误
			if (!"4".equals(taskVO.getOffice().getLevelnumString()) && !"3".equals(taskVO.getOffice().getLevelnumString()) && !"2".equals(taskVO.getOffice().getLevelnumString())) {//登记门店
				taskVO.setOffice(new CustomOffice());
			}
		}
		model.addAttribute("taskVO", taskVO);
		model.addAttribute("noProcessName", true);
		model.addAttribute("submitPath", "toDoneList/marginRepay");
		return "app/accounting/taskCenter/todone_business_list";
	}
	
	// 提前还款待办任务
	@RequestMapping(value = { "todoList/advanceRepay" })
	public String todoAdvanceRepay(HttpServletRequest request, HttpServletResponse response, Model model, TaskVO taskVO) {
		MyMap paramMap = setParamMap(taskVO);
		paramMap.put("procDefIdPre", Constants.WORKFLOW_ADVANCE_REPAY);
		Page<MyMap> page = taskCenterService.findAdvanceRepayToDoList(new Page<MyMap>(request, response), paramMap);
		model.addAttribute("page", page);
		if(taskVO.getOffice()!=null){//LevelnumString为空时，回显错误
			if (!"4".equals(taskVO.getOffice().getLevelnumString()) && !"3".equals(taskVO.getOffice().getLevelnumString()) && !"2".equals(taskVO.getOffice().getLevelnumString())) {//登记门店
				taskVO.setOffice(new CustomOffice());
			}
		}
		model.addAttribute("taskVO", taskVO);
		model.addAttribute("noProcessName", true);
		model.addAttribute("submitPath", "todoList/advanceRepay");
		return "app/accounting/taskCenter/todo_business_list";
	}
	
	// 提前还款已办任务
	@RequestMapping(value = { "toDoneList/advanceRepay" })
	public String doneAdvanceRepay(HttpServletRequest request, HttpServletResponse response, Model model, TaskVO taskVO) {
		MyMap paramMap = setParamMap(taskVO);
		paramMap.put("procDefIdPre", Constants.WORKFLOW_ADVANCE_REPAY);
		Page<MyMap> page = taskCenterService.findAdvanceRepayDoneList(new Page<MyMap>(request, response), paramMap);
		model.addAttribute("page", page);
		if(taskVO.getOffice()!=null){//LevelnumString为空时，回显错误
			if (!"4".equals(taskVO.getOffice().getLevelnumString()) && !"3".equals(taskVO.getOffice().getLevelnumString()) && !"2".equals(taskVO.getOffice().getLevelnumString())) {//登记门店
				taskVO.setOffice(new CustomOffice());
			}
		}
		model.addAttribute("taskVO", taskVO);
		model.addAttribute("noProcessName", true);
		model.addAttribute("submitPath", "toDoneList/advanceRepay");
		return "app/accounting/taskCenter/todone_business_list";
	}

	// 已办任务
	@RequestMapping(value = { "/toDoneList" })
	public String toDoneList(HttpServletRequest request, HttpServletResponse response, Model model, TaskVO taskVO) {
		String querySql = generateQuery(taskVO);
		MyMap paramMap = new MyMap();
		paramMap.put("taskVO", taskVO);
		Page<MyMap> page = actTaskService.toHistoricListAll(new Page<MyMap>(request, response), generateParamMap(paramMap, querySql));
		model.addAttribute("page", page);
		// 流程下拉框
		List<ProcessDefinition> processList = taskCenterService.loadActiveProcessList();
		model.addAttribute("processList", processList);
		model.addAttribute("taskVO", taskVO);
		model.addAttribute("submitPath", "toDoneList");
		return "app/accounting/taskCenter/todone_business_list";
	}

	/**
	 * 查询当前用户的待分配（状态：待签收）任务列表数据
	 */
	@RequestMapping("/claimlist")
	public String toClaimList(TaskVO taskVO, Model model, HttpServletRequest request, HttpServletResponse response) {
		String querySql = generateQuery(taskVO);
		MyMap paramMap = new MyMap();
		paramMap.put("taskVO", taskVO);
		paramMap.put("taskState", Act.STATE_TASK_IS_TOCLAIM);// 静态变量STATE_TASK_IS_TODO（待办）、STATE_TASK_IS_TOCLAIM（待签收）或为空（全部）
		Page<MyMap> page = actTaskService.todoListNewByPage(new Page<MyMap>(request, response), generateParamMap(paramMap, querySql));
		model.addAttribute("page", page);
		return "app/accounting/taskCenter/toclaim_business_list";
	}

	// 灵活待办任务
	@RequestMapping(value = "/list/{taskDefKey}")
	public String todoListDynamic(HttpServletRequest request, HttpServletResponse response, Model model, TaskVO taskVO, @PathVariable(value = "taskDefKey") String taskDefKey, String returnVal) {
		String querySql = generateQuery(taskVO);
		MyMap paramMap = new MyMap();
		paramMap.put("taskVO", taskVO);
		paramMap.put("taskDefKey", taskDefKey);
		Page<MyMap> page = actTaskService.todoListNewByPage(new Page<MyMap>(request, response), generateParamMap(paramMap, querySql));
		model.addAttribute("page", page);
		model.addAttribute("taskDefKey", taskDefKey);
		if (returnVal != null && !returnVal.equals("") && !returnVal.equals("undefined")) {
			if (returnVal.equals("1")) {
				addMessage(model, "批量提交成功");
			}
		}
		return "app/accounting/taskCenter/todo_business_list";
	}

	/**
	 * 如果url中是todoSupervisor时根据传入的customer实现待办流程监控
	 */
	// 待办流程监控
	@RequestMapping(value = { "/todoSupervisor" })
	public String todoSupervisor(HttpServletRequest request, HttpServletResponse response, Model model, TaskVO taskVO) {
		String querySql = generateQuery(taskVO);
		MyMap paramMap = new MyMap();
		paramMap.put("taskVO", taskVO);
		Page<MyMap> page = actTaskService.findDoneOrFinishList(new Page<MyMap>(request, response), generateParamMap(paramMap, querySql), true);
		model.addAttribute("page", page);
		// 流程下拉框
		List<ProcessDefinition> processList = taskCenterService.loadActiveProcessList();
		model.addAttribute("processList", processList);
		return "app/accounting/taskCenter/todo_business_supervisor";
	}

	// 已办流程监控
	@RequestMapping(value = { "/todoneSupervisor" })
	public String todoneSupervisor(HttpServletRequest request, HttpServletResponse response, Model model, TaskVO taskVO) {
		String querySql = generateQuery(taskVO);
		MyMap paramMap = new MyMap();
		paramMap.put("taskVO", taskVO);
		Page<MyMap> page = actTaskService.findDoneOrFinishList(new Page<MyMap>(request, response), generateParamMap(paramMap, querySql), false);
		model.addAttribute("page", page);
		return "app/accounting/taskCenter/todone_business_supervisor";
	}

	private MyMap generateParamMap(MyMap paramMap, String querySql) {
		if (paramMap.get("taskVO") != null) {
			if (paramMap.get("taskVO") instanceof TaskVO) {
				TaskVO taskVO = (TaskVO) paramMap.get("taskVO");
				if (StringUtils.isNotBlank(taskVO.getProDefKey())) {
					paramMap.put("proDefKey", taskVO.getProDefKey());
				}
				if (StringUtils.isNotBlank(taskVO.getCustName())) {
					paramMap.put("custName", taskVO.getCustName());
				}
				if (taskVO.getApplyStartTime() != null) {
					paramMap.put("applyStartTime", taskVO.getApplyStartTime());
				}
				if (taskVO.getApplyEndTime() != null) {
					paramMap.put("applyEndTime", taskVO.getApplyEndTime());
				}
				if (taskVO.getOffice() != null) {
					paramMap.put("dataScope", taskVO.getOffice().getId());
				}
				if (taskVO.getCreateStartTime() != null) {
					paramMap.put("cdateStart", taskVO.getCreateStartTime());
				}
				if (taskVO.getCreateEndTime() != null) {
					String dateLastHour = DateUtils.formatDate(taskVO.getCreateEndTime()).toString() + " 23:59:59";
					paramMap.put("cdateEnd", dateLastHour);
				}
			}
		}
		paramMap.put("tableSql", querySql);
		return paramMap;
	}

	public String appendConditions(TaskVO taskVO, String querySql) {
		if (taskVO != null) {
			if (StringUtils.isNotBlank(taskVO.getCustName())) {
				querySql = querySql + " AND a.CUST_NAME LIKE CONCAT('%',#{custName},'%')";
			}
			if (taskVO.getApplyStartTime() != null) {
				querySql = querySql + " AND b.create_date >= #{applyStartTime}";
			}
			if (taskVO.getApplyEndTime() != null) {
				querySql = querySql + " AND b.create_date <= #{applyEndTime}";
			}
			if (taskVO.getOffice() != null) {
				if ("3".equals(taskVO.getOffice().getLevelnumString())) {
					querySql = querySql + "AND o4.id = #{dataScope}";
				} else if ("2".equals(taskVO.getOffice().getLevelnumString())) {
					querySql = querySql + "AND o3.id = #{dataScope}";
				} else if ("1".equals(taskVO.getOffice().getLevelnumString())) {
					querySql = querySql + "AND o2.id = #{dataScope}";
				}
			}
		}
		Office company = UserUtils.getUser().getCompany();
		String id = company.getId();
		String parentAndSelf_ids = company.getParentIds() + company.getId() + ",";
		querySql = querySql + new StringBuffer().append(" and (t3.id='").append(id + "'").append("  or t3.parent_ids like '").append(parentAndSelf_ids + "%'").append(")").toString();
		return querySql;
	}

	/**
	 * CRE_信贷审批_任务中心_在办流程监控.开发说明：流程监控参数绑定，实现客户名和证件号的模糊查询，以及申请编号的查询，并实现机构及子机构查询功能
	 */
	// 流程监控map参数绑定
	public MyMap paramBinderMap(MyMap paramMap) {
		if (paramMap.get("taskVO") != null) {
			if (paramMap.get("taskVO") instanceof TaskVO) {
				TaskVO taskVO = (TaskVO) paramMap.get("taskVO");
				if (taskVO.getProcessName() != null && !taskVO.getProcessName().equals("")) {
					paramMap.put("processName", taskVO.getProcessName());
				}
			}
		}
		Office company = UserUtils.getUser().getCompany();
		String id = company.getId();
		String parentAndSelf_ids = company.getParentIds() + company.getId() + ",";
		paramMap.put("selfId", id);
		paramMap.put("parentIds", parentAndSelf_ids);
		return paramMap;
	}

	// 灵活已办任务
	@RequestMapping(value = "/toDoneList/{taskDefKey}")
	public String toDoneListDynamic(HttpServletRequest request, HttpServletResponse response, Model model, TaskVO taskVO, @PathVariable(value = "taskDefKey") String taskDefKey) {
		String querySql = generateQuery(taskVO);
		MyMap paramMap = new MyMap();
		paramMap.put("taskVO", taskVO);
		paramMap.put("taskDefKey", taskDefKey);
		Page<MyMap> page = actTaskService.toHistoricListAll(new Page<MyMap>(request, response), generateParamMap(paramMap, querySql));
		model.addAttribute("page", page);
		model.addAttribute("taskDefKey", taskDefKey);
		return "app/accounting/taskCenter/todone_business_list";
	}

	// 流程轨迹
	@RequestMapping(value = "/processTrack")
	public String processTrack(HttpServletRequest request, HttpServletResponse response, Model model, String procInstId) {
		List<MyMap> proList = actTaskService.findProcessTask(procInstId);
		proList = taskCenterService.sortList(proList);
		model.addAttribute("proList", proList);
		return "app/accounting/taskCenter/processTrack";
	}

	// 流程图
	@RequestMapping(value = "trace/photo/{procDefId}/{execId}")
	public void tracePhoto(@PathVariable("procDefId") String procDefId, @PathVariable("execId") String execId, HttpServletResponse response) throws Exception {
		InputStream imageStream = actTaskService.tracePhoto(procDefId, execId);
		byte[] b = new byte[1024];
		int len;
		while ((len = imageStream.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
	}

	// 流程图(客户list入口)
	@RequestMapping(value = "trace/photo/taskVO")
	public void tracePhotoForCustomer(HttpServletResponse response, String procInstId) throws Exception {
		/*
		 * ActRuTask actRuTask =
		 * taskCenterService.findSingleRunTask(procInstId); InputStream
		 * imageStream = null; if (actRuTask == null) { List<ActHiTaskInst>
		 * actHiTaskInst = taskCenterService.findSingleHisTask(procInstId);
		 * imageStream =
		 * actTaskService.tracePhoto(actHiTaskInst.get(0).getProcDefId(),
		 * actHiTaskInst.get(0).getExecutionId()); } else { imageStream =
		 * actTaskService.tracePhoto(actRuTask.getProcDefId(),
		 * actRuTask.getExecutionId()); } byte[] b = new byte[1024]; int len;
		 * while ((len = imageStream.read(b, 0, 1024)) != -1) {
		 * response.getOutputStream().write(b, 0, len); }
		 */
	}

	/**
	 * 办理功能对应的controller方法，本方法传入任务定义ID和流程定义ID，查出对应的formKey,通过json返回对应的url，
	 * 在界面中进行获取并且使用
	 * 在办理的时候增加签收功能，逻辑：点击办理时，判定有无签收人，没有，可以进行办理，如果有签收人name，判断是否和当前用户的name一致
	 * ，如果一致可以办理，否则无法办理
	 */
	// 办理(增加签收功能)
	@RequestMapping(value = "doBusiness/{procDefId}/{execId}/{taskDefKey}")
	public @ResponseBody
	Map<String, String> doBusiness(@PathVariable("procDefId") String procDefId, @PathVariable("taskDefKey") String taskDefKey, HttpServletResponse response, String taskId) throws Exception {
		// 判定是否能够签收
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		Map<String, String> msgMap = new HashMap<String, String>();
		// 根据签收人名字判断
		if (task.getAssignee() == null) {
			actTaskService.claim(task.getId(), UserUtils.getUser().getLoginName());
		} else {
			if (!task.getAssignee().equals(UserUtils.getUser().getLoginName())) {
				msgMap.put("flag", "fail");
				return msgMap;
			}
		}
		String formKey = actTaskService.getFormKey(procDefId, taskDefKey);
		msgMap.put("location", formKey);
		msgMap.put("flag", "success");
		return msgMap;
	}

	/**
	 * 已办任务集成，获取任务地址信息
	 */
	@RequestMapping(value = "loadTaskAddr/{procDefId}/{execId}/{taskDefKey}")
	public @ResponseBody Map<String, String> getTaskAddr(@PathVariable("procDefId") String procDefId, 
		 @PathVariable("taskDefKey") String taskDefKey, 
		 HttpServletResponse response ,String taskId) throws Exception {
		
		Map<String, String> msgMap = new HashMap<String, String>();
		String formKey = actTaskService.getFormKey(procDefId, taskDefKey);
		msgMap.put("location", formKey);
		msgMap.put("flag", "success");
		String canReDoFlag = actTaskService.canReDo(taskId);
		if(Act.VERIFY_PASS.equals(canReDoFlag)){
			msgMap.put("canRedo", "1");
		}else{
			msgMap.put("canRedo", "0");
		}
		return msgMap;
	}

	// 点击批量办理
	@RequestMapping(value = "/list/toBatchSubmit")
	public String toBatchSubmit(String procInsIds, String taskIds, Model model, TaskVO taskVO) throws Exception {
		model.addAttribute("procInsIds", procInsIds);
		model.addAttribute("taskIds", taskIds);
		model.addAttribute("taskVO", taskVO);
		return "app/accounting/taskCenter/todo_batch_business_edit";
	}

	// 批量办理(增加签收)
	@RequestMapping(value = "/list/batchSubmit")
	public @ResponseBody
	Map<String, String> batchSubmit(String procInsIds, String taskIds, String textAreaComment, Model model, TaskVO taskVO) throws Exception {
		Map<String, String> msgMap = new HashMap<String, String>();
		try {
			String[] str = null;
			List<String> procInsIdsList = new LinkedList<String>();
			List<String> taskIdsList = new LinkedList<String>();
			if (procInsIds.contains(",")) {
				str = procInsIds.trim().split(",");
				for (String id : str) {
					procInsIdsList.add(id);
				}
			} else {
				procInsIdsList.add(procInsIds);
			}
			if (taskIds.contains(",")) {
				str = taskIds.trim().split(",");
				for (String id : str) {
					taskIdsList.add(id);
				}
			} else {
				taskIdsList.add(taskIds);
			}
			for (String i : taskIdsList) {
				Task task = taskService.createTaskQuery().taskId(i).singleResult();
				// 根据签收人名字判断
				if (task.getAssignee() == null) {
					actTaskService.claim(task.getId(), UserUtils.getUser().getLoginName());
				} else {
					if (!task.getAssignee().equals(UserUtils.getUser().getLoginName())) {
						msgMap.put("flag", "fail");
						return msgMap;
					}
				}
			}
			String comment = "[通过]" + textAreaComment;
			String title = "[通过]";
			Map<String, Object> vars = new HashMap<String, Object>();
			for (int i = 0; i < procInsIdsList.size(); i++) {
				actTaskService.complete(taskIdsList.get(i), procInsIdsList.get(i), comment, title, vars);
			}
			msgMap.put("result", "success");
			msgMap.put("reval", "1");
		} catch (Exception e) {
			msgMap.put("result", "发生" + e.getMessage() + "异常,无法批量办理");
		}
		return msgMap;
	}

	public List<Map<String, Object>> toSortList(List<MyMap> listTemp) {
		List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
		for (MyMap m : listTemp) {
			Iterator<Map.Entry<String, Object>> i = m.entrySet().iterator();
			Map<String, Object> map = new HashMap<String, Object>();
			;
			while (i.hasNext()) {
				Map.Entry<String, Object> entry = i.next();
				map.put(entry.getKey(), entry.getValue());
			}
			list.add(map);
		}
		return list;
	}

	// 增加子查询配置
	public String addCurrIdAndChildId(String querySql) {
		Office company = UserUtils.getUser().getCompany();
		String id = company.getId();
		String parentAndSelf_ids = company.getParentIds() + company.getId() + ",";
		querySql = querySql + new StringBuffer().append(" and (t3.id='").append(id + "'").append("  or t3.parent_ids like '").append(parentAndSelf_ids + "%'").append(")").toString();
		return querySql;
	}

	// 根据PRO_INS_ID查询数据
	public Map<String, Object> sortToExportMap(Map<String, Object> paramMap, String querySql, List<String> procInsIdsList) {
		if (procInsIdsList.size() > 0) {
			querySql = querySql + "AND t1.PROC_INS_ID in (";
			for (int i = 0; i < procInsIdsList.size(); i++) {
				if (i != procInsIdsList.size() - 1) {
					querySql = querySql + "'" + procInsIdsList.get(i) + "',";
				} else {
					querySql = querySql + "'" + procInsIdsList.get(i) + "'";
				}
			}
			querySql = querySql + ")";
		}
		paramMap.put("tableSql", querySql);
		return paramMap;
	}

	public MyMap sortToExportMyMap(MyMap paramMap, String querySql, List<String> procInsIdsList) {
		if (procInsIdsList.size() > 0) {
			querySql = querySql + "AND t1.PROC_INS_ID in (";
			for (int i = 0; i < procInsIdsList.size(); i++) {
				if (i != procInsIdsList.size() - 1) {
					querySql = querySql + "'" + procInsIdsList.get(i) + "',";
				} else {
					querySql = querySql + "'" + procInsIdsList.get(i) + "'";
				}
			}
			querySql = querySql + ")";
		}
		paramMap.put("tableSql", querySql);
		return paramMap;
	}

	// 根据url获取当前的requestMapping的请求标志位
	public String getUrlFlag(String url) {
		String[] args = url.split("/");
		return args[args.length - 1];
	}

	/**
	 * 代办、已办流程详情-跳转代办、已办流程详情页面，代办、已办区分标识：status
	 */
	@RequestMapping(value = "/infoView")
	public String toInfoView(ActTaskParam actTaskParam, Model model, String readOnly) {
		// String productType = ""; // 产品类型
		// CreApplyInfo creApplyInfo = new CreApplyInfo();
		// creApplyInfo.setApplyNo(actTaskParam.getApplyNo());
		// List<CreApplyInfo> onlyPructTypeApplyInfo =
		// creApplyInfoService.findList(creApplyInfo);
		// if (onlyPructTypeApplyInfo != null && onlyPructTypeApplyInfo.size() >
		// 0) {
		// productType =
		// onlyPructTypeApplyInfo.get(0).getApplyProductTypeCode();
		// }

		model.addAttribute("actTaskParam", actTaskParam);
		return "app/accounting/taskCenter/process_info_index";
	}

	/**
	 * 在各已办任务详情页面增加“撤回”按钮， 先判断当前流程是否能撤回，能撤回则在页面显示
	 * “撤回”按钮，否则页面无“撤回”按钮；点击“撤回”按钮，撤回流程，并返回页面提示。
	 */
	// 撤回操作
	@RequestMapping(value = "/reDo")
	public String reDo(Model model, HttpServletRequest request, HttpServletResponse response) {
		String taskId = request.getParameter("taskId");
		String comment = "【撤回】";
		if (actTaskService.exeReDo(taskId, comment)) {// 执行撤回
			return super.renderString(response, "success");
		} else {
			return super.renderString(response, "fail");
		}
	}

	/**
	 * 查询、展示当前用户待分配任务列表 任务指派：将隶属于当前用户且尚未签收的任务指派给特定人员进行任务处理,任务指派成功后返回“success”
	 */
	@RequestMapping("/doAsign")
	public String doAsign(ActTaskParam actTaskParam, String user, HttpServletResponse response) {
		try {
			actTaskService.doAssign(actTaskParam.getTaskId(), user);
		} catch (Exception e) {
			logger.error("任务指派错误：" + actTaskParam.getTaskId() + "," + user, e);
			return super.renderString(response, "error");
		}
		return super.renderString(response, "success");
	}
}
