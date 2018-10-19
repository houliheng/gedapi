package com.resoft.credit.taskCenter.web;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.resoft.outinterface.rest.newged.entity.FindIsRegisterRequest;
import com.resoft.outinterface.rest.newged.entity.FindIsRegisterResponse;
import com.resoft.outinterface.utils.Facade;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.resoft.common.utils.AjaxView;
import com.resoft.common.utils.Constants;
import com.resoft.common.utils.DataScopeFitter;
import com.resoft.common.utils.DateUtils;
import com.resoft.credit.applyLoanStatus.service.ApplyLoanStatusService;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.applyRegister.entity.Customer;
import com.resoft.credit.checkApproveUnion.entity.CheckApproveUnion;
import com.resoft.credit.processSuggestionInfo.entity.ProcessSuggestionInfo;
import com.resoft.credit.processSuggestionInfo.service.ProcessSuggestionInfoService;
import com.resoft.credit.taskCenter.entity.ActHiTaskInst;
import com.resoft.credit.taskCenter.entity.ActRuTask;
import com.resoft.credit.taskCenter.service.TaskCenterService;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.act.entity.Act;
import com.thinkgem.jeesite.modules.act.entity.MyMap;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.CustomUserService;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 任务中心 Controller
 * 
 * @author xielifeng
 * @version 2014-09-28
 */
@Controller("creTaskCenterController")
@RequestMapping(value = "${adminPath}/credit/taskCenter")
public class TaskCenterController extends BaseController {
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private TaskCenterService taskCenterService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private CustomUserService customUserService;
	@Autowired
	OfficeService officeService;
	@Autowired
	private ProcessSuggestionInfoService processSuggestionInfoService;
	@Autowired
	private ApplyLoanStatusService applyLoanStatusService;

	private static StringBuffer querySqlBuffer = new StringBuffer().append(" SELECT ").append(" t1.ID as APPLY_ID, ")
			.append(" t1.CUST_NAME as CUST_NAME, ").append(" t2.APPLY_AMOUNT as APPLY_AMOUNT, ")
			.append(" t1.ID_TYPE as ID_TYPE, ").append(" t1.CUST_TYPE as CUST_TYPE, ").append(" t1.ID_NUM as ID_NUM, ")
			.append(" t1.APPLY_NO as APPLY_NO,").append(" t3.NAME as ORG_ID, ")
			.append(" t1.PROC_INS_ID as PROC_INS_ID,").append(" t1.REGISTER_DATE as REGISTER_DATE ").append(" FROM ")
			.append(" CRE_APPLY_REGISTER t1 left join CRE_APPLY_INFO t2 ON t1.APPLY_NO=t2.APPLY_NO ")
			.append(" left join sys_office t3 on t1.ORG_ID = t3.id and t3.type = 1").append(" where 1=1 ");

	private MyMap setParamMap(Customer customer) {
		MyMap paramMap = new MyMap();
		DataScopeFitter.companyDataScopeFilter(customer);
		paramMap.put("sqlMap", customer.getSqlMap());
		paramMap.put("userId", UserUtils.getUser().getLoginName());
		paramMap.put("userID", UserUtils.getUser().getId());
		paramMap.put("custName", customer.getCusName());
		paramMap.put("applyNo", customer.getApplyNo());
		paramMap.put("idNum", customer.getIdNum());
		paramMap.put("cdateStart", customer.getDoneStartTime());
		paramMap.put("loanModel", customer.getLoanModel());
		if (StringUtils.isNotEmpty(customer.getContractNo())) {
			paramMap.put("contractNo", customer.getContractNo());
		}

		if (customer.getDoneEndTime() != null) {
			String cdateEnd = DateUtils.formatDate(customer.getDoneEndTime()).toString() + " 23:59:59";
			paramMap.put("cdateEnd", cdateEnd);
		}
		if (customer != null && StringUtils.isNotBlank(customer.getApplyProductTypeCode())) {
			paramMap.put("applyProductTypeCode", customer.getApplyProductTypeCode());
		}

		Office company = customer.getCompany();
		if (company != null && StringUtils.isNotBlank(company.getId())) {// 所选机构及以下机构
			paramMap.put("currentOrg", company.getId());
			try {
				company = officeService.get(company);
				paramMap.put("currentOrgParentIds", company.getParentIds() + company.getId());
			} catch (Exception e) {
				logger.error("根据Id获取office对象失败，可能为数据错误", e);
			}
		} else {// 当前机构及以下机构
			company = UserUtils.getUser().getCompany();
			paramMap.put("currentOrg", company.getId());
			paramMap.put("currentOrgParentIds", company.getParentIds() + company.getId());
		}
		return paramMap;
	}

	// 待办任务
	@RequestMapping(value = { "list", "" })
	public String todoList(HttpServletRequest request, HttpServletResponse response, Model model, Customer customer) {
		MyMap paramMap = setParamMap(customer);
		Page<MyMap> page = null;
		if (Constants.ADMIN_USER_ID.equalsIgnoreCase(UserUtils.getUser().getId())) {
			page = taskCenterService.allTodoListNewByPage(new Page<MyMap>(request, response), paramMap);
		} else {
			page = taskCenterService.todoListNewByPage(new Page<MyMap>(request, response), paramMap);
		}
		model.addAttribute("page", page);
		model.addAttribute("headUrl", "/credit/taskCenter/list");
		model.addAttribute("loginOfficeId", UserUtils.getUser().getCompany().getId());
		// 根据不同的url跳转到不同的页面
		return "app/credit/taskCenter/todo_business_list";
	}

	/**
	 * 查询当前用户的待分配（状态：待签收）任务列表数据
	 */
	@RequestMapping("/claimlist")
	public String toClaimList(Customer customer, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		MyMap paramMap = setParamMap(customer);
		// paramMap.put("taskState", Act.STATE_TASK_IS_TOCLAIM);//
		// 静态变量STATE_TASK_IS_TODO（待办）、STATE_TASK_IS_TOCLAIM（待签收）或为空（全部）
		Page<MyMap> page = taskCenterService.todoListNewByPage(new Page<MyMap>(request, response), paramMap);
		model.addAttribute("page", page);
		model.addAttribute("headUrl", "/credit/taskCenter/claimlist");
		return "app/credit/taskCenter/toclaim_business_list";
	}

	/**
	 * 查询当前用户的已分配任务列表数据
	 */
	@RequestMapping("/doneClaimlist")
	public String doneClaimlist(Customer customer, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		MyMap paramMap = setParamMap(customer);
		Page<MyMap> page = taskCenterService.findDoneClaimlist(new Page<MyMap>(request, response), paramMap);
		model.addAttribute("page", page);
		model.addAttribute("headUrl", "/credit/taskCenter/doneClaimlist");
		return "app/credit/taskCenter/doneclaim_business_list";
	}

	// 按机构过滤待分配任务
	@RequestMapping(value = "/claimlist/{officeId}")
	public String toClaimListOfiice(HttpServletRequest request, HttpServletResponse response, Model model,
			Customer customer, @PathVariable(value = "officeId") String officeId) {
		MyMap paramMap = setParamMap(customer);
		Office office = officeService.get(officeId);
		paramMap.put("officeId", officeId);
		paramMap.put("parentAndSelf", office.getParentIds() + officeId);
		Page<MyMap> page = taskCenterService.todoListNewByPage(new Page<MyMap>(request, response), paramMap);
		model.addAttribute("page", page);
		model.addAttribute("ZBFlag", office.getlevelnum());
		model.addAttribute("officeId", officeId);
		model.addAttribute("headUrl", "/credit/taskCenter/claimlist/" + officeId);
		return "app/credit/taskCenter/toclaim_business_list";
	}

	// 按机构代码过滤待办任务
	@RequestMapping(value = "/list/office/{officeCode}")
	public String todoListOfiice(HttpServletRequest request, HttpServletResponse response, Model model,
			Customer customer, @PathVariable(value = "officeCode") String officeCode) {
		MyMap paramMap = new MyMap();
		String querySql = querySqlBuffer.toString();
		querySql = this.addCurrIdAndChildId(querySql);
		paramMap.put("officeCode", officeCode);
		querySql = querySql
				+ "AND EXISTS (SELECT 1 FROM cre_office_apply_relation oa inner join sys_office o ON o.id = oa.office_id WHERE oa.apply_no = t1.apply_no and o.code = #{officeCode})";
		paramMap.put("customer", customer);
		Page<MyMap> page = actTaskService.todoListNewByPage(new Page<MyMap>(request, response),
				this.sortTodoMap(paramMap, querySql));
		model.addAttribute("page", page);
		model.addAttribute("officeCode", officeCode);
		return "app/credit/taskCenter/todo_business_list";
	}

	// 灵活待办任务
	@RequestMapping(value = "/list/{taskDefKey}")
	public String todoListDynamic(HttpServletRequest request, HttpServletResponse response, Model model,
			Customer customer, @PathVariable(value = "taskDefKey") String taskDefKey, String returnVal) {
		MyMap paramMap = setParamMap(customer);
		if (Constants.UTASK_ZGSJLSH.equalsIgnoreCase(taskDefKey)
				|| Constants.UTASK_ZGSSXQR.equalsIgnoreCase(taskDefKey)) {
			User currentUser = UserUtils.getUser();
			if (currentUser.getOffice() != null) {
				paramMap.put("parentOfficeId", currentUser.getOffice().getId());
			}
		}
		if (Constants.UTASK_HTSH.equalsIgnoreCase(taskDefKey) || Constants.UTASK_CWFK.equalsIgnoreCase(taskDefKey)) { // 合同审核财务放款按机构查询
			if (null != customer.getCompany()) {
				Office company = officeService.get(customer.getCompany());
				if (null != company) {
					paramMap.put("selectOrgId", company.getId());
					paramMap.put("selectOrgParentIds", company.getParentIds() + company.getId() + ",");
				}
			}
		}
		paramMap.put("taskDefKey", taskDefKey);
		Page<MyMap> page = null;
		if (Constants.ADMIN_USER_ID.equalsIgnoreCase(UserUtils.getUser().getId())) {
			if (Constants.UTASK_HTSH.equalsIgnoreCase(taskDefKey)
					|| Constants.UTASK_CWFK.equalsIgnoreCase(taskDefKey)) {// 合同审核财务放款待办单独方法，不影响其他待办查询速度
				page = taskCenterService.allTodoListNewByPageTwo(new Page<MyMap>(request, response), paramMap);
			} else {
				page = taskCenterService.allTodoListNewByPage(new Page<MyMap>(request, response), paramMap);
			}
		} else {
			if (Constants.UTASK_HTSH.equalsIgnoreCase(taskDefKey)
					|| Constants.UTASK_CWFK.equalsIgnoreCase(taskDefKey)) {// 合同审核财务放款待办单独方法，不影响其他待办查询速度
				page = taskCenterService.todoListNewByPageTwo(new Page<MyMap>(request, response), paramMap);
			} else {
				page = taskCenterService.todoListNewByPage(new Page<MyMap>(request, response), paramMap);
			}
		}
		if (taskDefKey.equals(Constants.UTASK_CWFK)) {
			List<MyMap> lstLoan = page.getList();
			for (int i = 0; i < lstLoan.size(); i++) {
				try {
					Map<String, Object> maps = lstLoan.get(i);
					String applyNo = (String) maps.get("APPLY_NO");
					List<CheckApproveUnion> applyLoanStatusList = applyLoanStatusService.getLoanStatusNew(applyNo);
					if (applyLoanStatusList.size() == 1) {
						page.getList().get(i).put("LOAN_STATUS", applyLoanStatusList.get(0).getLoanStatus());
					} else if (applyLoanStatusList.size() >= 2) {
						page.getList().get(i).put("LOAN_STATUS", "25");
					} else {
						String loanStatus = applyLoanStatusService.getLoanStatusByApplyNo(applyNo);
						page.getList().get(i).put("LOAN_STATUS", loanStatus);
					}
				} catch (Exception e) {
					logger.error("查看放款状态出现错误！", e);
				}
			}
		}
		if("utask_fgsfksh".equals(taskDefKey)||"utask_fgsjlsh".equals(taskDefKey)||"utask_qyfksh".equals(taskDefKey)||"utask_qyjlsh".equals(taskDefKey)||"utask_dqfkzysh".equals(taskDefKey)||"utask_dqfkjlsh".equals(taskDefKey)||"utask_zgsfksh".equals(taskDefKey)||"utask_zgsjlsh".equals(taskDefKey)){
			model.addAttribute("isShowGZ", "1");
		}
		model.addAttribute("page", page);
		model.addAttribute("headUrl", "/credit/taskCenter/list/" + taskDefKey);
		model.addAttribute("taskDefKey", taskDefKey);
		model.addAttribute("loginOfficeId", UserUtils.getUser().getCompany().getId());

		return "app/credit/taskCenter/todo_business_list";
	}

	// 待办流程监控
	@RequestMapping(value = { "/todoSupervisor" })
	public String todoSupervisor(HttpServletRequest request, HttpServletResponse response, Model model,
			Customer customer) {
		MyMap paramMap = new MyMap();
		String querySql = querySqlBuffer.toString();
		querySql = querySql + " AND t1.APPLY_STATUS = '" + Constants.APPLY_STATUS_SUBMIT + "' ";
		querySql = this.addCurrIdAndChildId(querySql);
		paramMap.put("customer", customer);
		Page<MyMap> page = actTaskService.findDoneOrFinishList(new Page<MyMap>(request, response),
				sortTodoMap(paramMap, querySql), true);
		model.addAttribute("page", page);
		return "app/credit/taskCenter/todo_business_supervisor";
	}

	public MyMap sortTodoMap(MyMap paramMap, String querySql) {
		if (paramMap.get("customer") != null) {
			if (paramMap.get("customer") instanceof Customer) {
				Customer customer = (Customer) paramMap.get("customer");
				if (StringUtils.isNotBlank(customer.getCusName())) {
					paramMap.put("custName", customer.getCusName());
					querySql = querySql + "AND t1.CUST_NAME LIKE CONCAT('%',#{custName},'%')";
				}
				if (StringUtils.isNotBlank(customer.getApplyNo())) {
					paramMap.put("applyNo", customer.getApplyNo());
					querySql = querySql + " AND t1.APPLY_NO LIKE CONCAT(#{applyNo},'%') ";
				}
				if (StringUtils.isNotBlank(customer.getIdNum())) {
					paramMap.put("idNum", customer.getIdNum());
					querySql = querySql + "AND t1.ID_NUM LIKE CONCAT('%',#{idNum},'%')";
				}
				if (customer.getCreateStartTime() != null) {
					paramMap.put("cdateStart", customer.getCreateStartTime());
				}
				if (customer.getCreateEndTime() != null) {
					String dateLastHour = DateUtils.formatDate(customer.getCreateEndTime()).toString() + " 23:59:59";
					paramMap.put("cdateEnd", dateLastHour);
				}
			}
		}
		paramMap.put("tableSql", querySql);
		return paramMap;
	}

	// 流程监控map参数绑定
	public MyMap paramBinderMap(MyMap paramMap) {
		if (paramMap.get("customer") != null) {
			if (paramMap.get("customer") instanceof Customer) {
				Customer customer = (Customer) paramMap.get("customer");
				if (customer.getCusName() != null && !customer.getCusName().equals("")) {
					paramMap.put("custName", customer.getCusName());
				}
				if (customer.getApplyNo() != null && !customer.getApplyNo().equals("")) {
					paramMap.put("applyNo", customer.getApplyNo());
				}
				if (customer.getIdNum() != null && !customer.getIdNum().equals("")) {
					paramMap.put("idNum", customer.getIdNum());
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

	// 已办任务
	@RequestMapping(value = { "/toDoneList" })
	public String toDoneList(HttpServletRequest request, HttpServletResponse response, Model model, Customer customer) {
		Page<MyMap> page = taskCenterService.findDoneTaskList(new Page<MyMap>(request, response),
				setParamMap(customer));
		model.addAttribute("page", page);
		model.addAttribute("headUrl", "/credit/taskCenter/toDoneList");
		model.addAttribute("loginOfficeId", UserUtils.getUser().getCompany().getId());
		String url = request.getServletPath();
		// 根据不同的url跳转到不同的页面
		String urlFlag = this.getUrlFlag(url);
		if (urlFlag.equals("todoneSupervisor")) {
			return "app/credit/taskCenter/todone_business_supervisor";
		} else {
			return "app/credit/taskCenter/todone_business_list";
		}
	}


	// 灵活已办任务
	@RequestMapping(value = "/toDoneList/{taskDefKey}")
	public String toDoneListDynamic(HttpServletRequest request, HttpServletResponse response, String cwfkFlag,
			Model model, Customer customer, @PathVariable(value = "taskDefKey") String taskDefKey) {
		MyMap paramMap = setParamMap(customer);
		paramMap.put("taskDefKey", taskDefKey);
		paramMap.put("cwfkFlag", cwfkFlag);
		Page<MyMap> page = null;
		if (Constants.UTASK_HTSH.equalsIgnoreCase(taskDefKey) || Constants.UTASK_CWFK.equalsIgnoreCase(taskDefKey)) { // 合同审核财务放款按机构查询
			if (null != customer.getCompany()) {
				Office company = officeService.get(customer.getCompany());
				if (null != company) {
					paramMap.put("selectOrgId", company.getId());
					paramMap.put("selectOrgParentIds", company.getParentIds() + company.getId() + ",");
				}
			}
		}
		if (Constants.UTASK_HTSH.equalsIgnoreCase(taskDefKey)) { // 合同审核已办单独方法，不影响其他待办查询速度
			page = taskCenterService.findDoneTaskListHtsh(new Page<MyMap>(request, response), paramMap);
		} else {
			if ("cwfk".equals(cwfkFlag)) {
				page = taskCenterService.findAllDoneTaskList(new Page<MyMap>(request, response), paramMap);
			} else {
				page = taskCenterService.findDoneTaskList(new Page<MyMap>(request, response), paramMap);
			}
		}
		String headUrl = null;
		headUrl = "/credit/taskCenter/toDoneList/" + taskDefKey;
		if (!(StringUtils.isNull(cwfkFlag))) {
			headUrl = "/credit/taskCenter/toDoneList/" + taskDefKey + "?cwfkFlag=" + cwfkFlag;
		}
		model.addAttribute("cwfkFlag", cwfkFlag);
		model.addAttribute("page", page);
		model.addAttribute("taskDefKey", taskDefKey);
		model.addAttribute("headUrl", headUrl);
		model.addAttribute("loginOfficeId", UserUtils.getUser().getCompany().getId());
		return "app/credit/taskCenter/todone_business_list";
	}

	@Autowired
	RuntimeService runtimeService;

	// 已办流程监控
	@RequestMapping(value = { "/todoneSupervisor" })
	public String todoneSupervisor(HttpServletRequest request, HttpServletResponse response, Model model,
			Customer customer) {
		MyMap paramMap = new MyMap();
		String querySql = querySqlBuffer.toString();
		querySql = this.addCurrIdAndChildId(querySql);
		paramMap.put("customer", customer);
		Page<MyMap> page = null;
		if (Constants.ADMIN_USER_ID.equalsIgnoreCase(UserUtils.getUser().getId())) {
			page = taskCenterService.allFindDoneOrFinishList(new Page<MyMap>(request, response),
					sortTodoMap(paramMap, querySql));
		} else {
			page = actTaskService.findDoneOrFinishList(new Page<MyMap>(request, response),
					sortTodoMap(paramMap, querySql), false);
		}
		model.addAttribute("headUrl", "/credit/taskCenter/todoneSupervisor");
		model.addAttribute("page", page);
		return "app/credit/taskCenter/todone_business_supervisor";
	}

	public MyMap sortToDoneMap(MyMap paramMap, String querySql) {
		if (paramMap.get("customer") != null) {
			if (paramMap.get("customer") instanceof Customer) {
				Customer customer = (Customer) paramMap.get("customer");
				if (StringUtils.isNotBlank(customer.getCusName())) {
					paramMap.put("custName", customer.getCusName());
					querySql = querySql + "AND t1.CUST_NAME LIKE CONCAT('%',#{custName},'%')";
				}
				if (StringUtils.isNotBlank(customer.getApplyNo())) {
					paramMap.put("applyNo", customer.getApplyNo());
					querySql = querySql + " AND t1.APPLY_NO = #{applyNo}";
				}
				if (StringUtils.isNotBlank(customer.getIdNum())) {
					paramMap.put("idNum", customer.getIdNum());
					querySql = querySql + "AND t1.ID_NUM LIKE CONCAT('%',#{idNum},'%')";
				}
				if (customer.getDoneStartTime() != null) {
					paramMap.put("cdateStart", customer.getDoneStartTime());
				}
				if (customer.getDoneEndTime() != null) {
					String dateLastHour = DateUtils.formatDate(customer.getDoneEndTime()).toString() + " 23:59:59";
					paramMap.put("cdateEnd", dateLastHour);
				}
			}
		}
		paramMap.put("tableSql", querySql);
		return paramMap;
	}

	// 流程轨迹
	@RequestMapping(value = "/processTrack")
	public String processTrack(HttpServletRequest request, HttpServletResponse response, Model model,
			String procInstId) {
		List<MyMap> proList = actTaskService.findProcessTask(procInstId);
		proList = taskCenterService.sortList(proList);
		model.addAttribute("proList", proList);
		return "app/credit/taskCenter/processTrack";
	}

	// 流程图
	@RequestMapping(value = "trace/photo/{procDefId}/{execId}")
	public void tracePhoto(@PathVariable("procDefId") String procDefId, @PathVariable("execId") String execId,
			HttpServletResponse response) throws Exception {
		InputStream imageStream = null;
		try {
			imageStream = actTaskService.tracePhoto(procDefId, execId);
			byte[] b = new byte[imageStream.available()];
			//byte[] b = new byte[imageStream.];
			int len;
			while ((len = imageStream.read(b, 0,imageStream.available())) != -1) {
				response.getOutputStream().write(b);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(imageStream!=null){
				imageStream.close();
			}
		}
	}

	// 流程图(客户list入口)
	@RequestMapping(value = "trace/photo/customer")
	public void tracePhotoForCustomer(HttpServletResponse response, String procInstId) throws Exception {
		ActRuTask actRuTask = taskCenterService.findSingleRunTask(procInstId);
		InputStream imageStream = null;
		if (actRuTask == null) {
			List<ActHiTaskInst> actHiTaskInst = taskCenterService.findSingleHisTask(procInstId);
			imageStream = actTaskService.tracePhoto(actHiTaskInst.get(0).getProcDefId(),
					actHiTaskInst.get(0).getExecutionId());
		} else {
			imageStream = actTaskService.tracePhoto(actRuTask.getProcDefId(), actRuTask.getExecutionId());
		}
		byte[] b = new byte[1024];
		int len;
		while ((len = imageStream.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
	}

	// 办理(增加签收功能)
	@RequestMapping(value = "doBusiness/{procDefId}/{execId}/{taskDefKey}")
	public @ResponseBody Map<String, String> doBusiness(@PathVariable("procDefId") String procDefId,
			@PathVariable("taskDefKey") String taskDefKey, HttpServletResponse response, String taskId)
			throws Exception {
		// 判定是否能够签收
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		Map<String, String> msgMap = new HashMap<String, String>();

		// 系统管理员的待办任务列表可以重新分配已签收的任务
		if (!Constants.ADMIN_USER_ID.equalsIgnoreCase(UserUtils.getUser().getId())) {
			// 根据签收人名字判断
			if (task.getAssignee() == null) {
				actTaskService.claim(task.getId(), UserUtils.getUser().getLoginName());
			} else {
				if (!task.getAssignee().equals(UserUtils.getUser().getLoginName())) {
					msgMap.put("flag", "fail");
					return msgMap;
				}
			}
		} else {
			if (task.getAssignee() == null) {
				actTaskService.claim(task.getId(), UserUtils.getUser().getLoginName());
			}
		}
		String formKey = actTaskService.getFormKey(procDefId, taskDefKey);
		msgMap.put("location", formKey);
		msgMap.put("flag", "success");
		return msgMap;
	}

	// 办结流程监控，详情页面
	@RequestMapping(value = "doDetail")
	public @ResponseBody Map<String, String> doDetail(String procDefId, String taskDefKey) throws Exception {
		Map<String, String> msgMap = new HashMap<String, String>();
		try {
			String formKey = actTaskService.getFormKey(procDefId, taskDefKey);
			msgMap.put("location", formKey);
			msgMap.put("flag", "success");
		} catch (Exception e) {
			msgMap.put("flag", "fail");
			logger.error("查看任务详情失败！", e);
		}
		return msgMap;
	}

	/**
	 * 已办任务集成，获取任务地址信息
	 */
	@RequestMapping(value = "loadTaskAddr/{procDefId}/{execId}/{taskDefKey}")
	public @ResponseBody Map<String, String> getTaskAddr(@PathVariable("procDefId") String procDefId,
			@PathVariable("taskDefKey") String taskDefKey, HttpServletResponse response, String taskId)
			throws Exception {

		Map<String, String> msgMap = new HashMap<String, String>();
		String formKey = actTaskService.getFormKey(procDefId, taskDefKey);
		msgMap.put("location", formKey);
		msgMap.put("flag", "success");
		String canReDoFlag = actTaskService.canReDo(taskId);
		if (Act.VERIFY_PASS.equals(canReDoFlag)) {
			msgMap.put("canRedo", Constants.CANREDO_Y);
		} else {
			msgMap.put("canRedo", Constants.CANREDO_N);
		}
		// 网查、两人外访、电话核查、外访费登记五个并发环节，不可撤回
		if (Constants.UTASK_ZGSZJLSH.equals(taskDefKey) || Constants.UTASK_ZGSJLSH.equals(taskDefKey)
				|| Constants.UTASK_LRWF1.equals(taskDefKey) || Constants.UTASK_LRWF2.equals(taskDefKey)
				|| Constants.UTASK_WC.equals(taskDefKey) || Constants.UTASK_DHHC.equals(taskDefKey)
				|| Constants.UTASK_WFFDJ.equals(taskDefKey)) {
			msgMap.put("canRedo", Constants.CANREDO_N);
		}
		return msgMap;
	}

	// 点击批量办理
	@RequestMapping(value = "/list/toBatchSubmit")
	public String toBatchSubmit(String procInsIds, String taskIds, Model model, Customer customer) throws Exception {
		model.addAttribute("procInsIds", procInsIds);
		model.addAttribute("taskIds", taskIds);
		model.addAttribute("customer", customer);
		return "app/credit/taskCenter/todo_batch_business_edit";
	}

	// 拒绝签收
	@ResponseBody
	@RequestMapping(value = "refuseToSignTask")
	public AjaxView refuseToSignTask(String taskId) {
		AjaxView ajaxView = new AjaxView();
		try {
			taskCenterService.refuseToSignTask(taskId);
			ajaxView.setSuccess().setMessage("拒签成功");
		} catch (Exception e) {
			logger.error("拒签失败", e);
			ajaxView.setFailed().setMessage("拒签失败，请查看后台信息");
		}
		return ajaxView;
	}

	// 拒绝签收
	@ResponseBody
	@RequestMapping(value = "taskBack")
	public AjaxView taskBack(ActTaskParam actTaskParam) {
		AjaxView ajaxView = new AjaxView();
		try {
			actTaskService.backOnANode(actTaskParam.getTaskId(), actTaskParam.getProcInstId(), "【打回】");
			ajaxView.setSuccess().setMessage("退回成功");
		} catch (Exception e) {
			logger.error("退回失败", e);
			ajaxView.setFailed().setMessage("退回失败，请查看后台信息");
		}
		return ajaxView;
	}

	// 批量办理(增加签收)
	@RequestMapping(value = "/list/batchSubmit")
	public @ResponseBody Map<String, String> batchSubmit(String procInsIds, String taskIds, String textAreaComment,
			Model model, Customer customer) throws Exception {
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
		if (!(Constants.ADMIN_USER_ID.equalsIgnoreCase(UserUtils.getUser().getId()))) {
			Office company = UserUtils.getUser().getCompany();
			String id = company.getId();
			String parentAndSelf_ids = company.getParentIds() + company.getId() + ",";
			querySql = querySql + new StringBuffer().append(" and (t3.id='").append(id + "'")
					.append("  or t3.parent_ids like '").append(parentAndSelf_ids + "%'").append(")").toString();
		}
		return querySql;
	}

	// 根据PRO_INS_ID查询数据
	public Map<String, Object> sortToExportMap(Map<String, Object> paramMap, String querySql,
			List<String> procInsIdsList) {
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
		model.addAttribute("actTaskParam", actTaskParam);
		model.addAttribute("readOnly", readOnly);
		return "app/credit/taskCenter/process_info_index";
	}

	/**
	 * 先判断当前流程是否能撤回，能撤回则在页面显示“撤回”按钮，否则页面无“撤回”按钮； 点击“撤回”按钮，撤回流程，并返回页面提示。
	 */
	// 撤回操作
	@RequestMapping(value = "/reDo")
	public String reDo(Model model, HttpServletRequest request, String utask, String applyNo,
			HttpServletResponse response) {
		String taskId = request.getParameter("taskId");
		String comment = "【撤回】";
		ProcessSuggestionInfo processSuggestionInfo = new ProcessSuggestionInfo();
		if (actTaskService.exeReDo(taskId, comment)) {// 执行撤回
			processSuggestionInfo.setUpdateBy(UserUtils.getUser().getUpdateBy());
			processSuggestionInfo.setApplyNo(applyNo);
			processSuggestionInfo.setUpdateDate(UserUtils.getUser().getUpdateDate());
			processSuggestionInfo.setPassFlag("reDo");
			processSuggestionInfoService.insertFlag(processSuggestionInfo, utask);
			return super.renderString(response, "success");
		} else {
			return super.renderString(response, "fail");
		}
	}

	/**
	 * 任务指派：将隶属于当前用户且尚未签收的任务指派给特定人员进行任务处理 任务指派成功后返回“success”
	 */
	@RequestMapping("/doAsign")
	public String doAsign(ActTaskParam actTaskParam, String user, String processName, String userId,
			HttpServletResponse response) {
		try {
			taskCenterService.saveTaskAllocateAndDoAsign(actTaskParam, user, processName, userId);
		} catch (Exception e) {
			logger.error("任务指派错误：" + actTaskParam.getTaskId() + "," + user, e);
			return super.renderString(response, "error");
		}
		return super.renderString(response, "success");
	}

	@RequestMapping("/loadOrgUser")
	public String loadCurrOrgUser(ActTaskParam actTaskParam, String processName, Model model, User user,
			HttpServletRequest request, HttpServletResponse response) {
		Page<MyMap> page = null;
		try {
			MyMap parmas = new MyMap();
			parmas.put("loginName", user.getLoginName());
			parmas.put("name", user.getName());
			user = UserUtils.getUser();
			parmas.put("companyId", user.getCompany().getId());
			parmas.put("id", user.getId());
			page = customUserService.getUserList(new Page<MyMap>(request, response), parmas);

			model.addAttribute("page", page);
			model.addAttribute("processName", processName);
			model.addAttribute("actTaskParam", actTaskParam);
		} catch (Exception e) {
			logger.error("查询分配人员列表失败", e);
		}
		return "/app/credit/customUser/orgUser";
	}

	@RequestMapping("/change")
	public String change(ActTaskParam actTaskParam, Model model) {
		User user = UserUtils.getUser();
		HashMap<String, Object> parmas = Maps.newHashMap();
		parmas.put("companyId", user.getCompany().getId());
		parmas.put("id", user.getId());
		List<User> list = customUserService.findUserList(parmas);
		model.addAttribute("list", list);
		model.addAttribute("actTaskParam", actTaskParam);
		return "/app/credit/customUser/selectTurnToDoUser";
	}

	@ResponseBody
	@RequestMapping("/changeup")
	public AjaxView changeup(ActTaskParam actTaskParam, Model model, HttpServletRequest request) {
		AjaxView ajaxView = new AjaxView();
		String sysuserid = request.getParameter("sysuserid");
		User user = UserUtils.get(sysuserid);
		try {
			if (!(StringUtils.isNull(user))) {
				taskCenterService.turnToDoTask(actTaskParam, user);
			} else {
				ajaxView.setFailed().setMessage("用户查询出错，请检查");
				return ajaxView;
			}
			ajaxView.setSuccess().setMessage("转办成功");
		} catch (Exception e) {
			logger.error("转办失败", e);
			ajaxView.setFailed().setMessage("转办失败");
		}
		return ajaxView;

	}

	@RequestMapping("/adminChange")
	public String adminChange(ActTaskParam actTaskParam, Model model, User user, HttpServletRequest request,
			HttpServletResponse response, String searchFlag) {
		MyMap parmaAdmin = new MyMap();
		User currentUser = UserUtils.getUser();
		if (user.getCompany() != null && !StringUtils.isEmpty(user.getCompany().getId())) {
			parmaAdmin.put("companyId", user.getCompany().getId());
		}
		if (!StringUtils.isEmpty(user.getLoginName())) {
			parmaAdmin.put("loginName", user.getLoginName());
		}
		if (!StringUtils.isEmpty(user.getName())) {
			parmaAdmin.put("name", user.getName());
		}
		parmaAdmin.put("id", currentUser.getId());
		Page<MyMap> page = null;
		if (!StringUtils.isEmpty(searchFlag)) {
			page = customUserService.findAllUserList(new Page<MyMap>(request, response), parmaAdmin);
		}
		model.addAttribute("page", page);
		model.addAttribute("actTaskParam", actTaskParam);
		model.addAttribute("user", user);
		return "/app/credit/customUser/adminSelectTurnToDoUser";
	}

	//线下借款待办列表
	/*@RequestMapping(value = { "underLoanList", "" })
	public String underLoanList(HttpServletRequest request, HttpServletResponse response, Model model, Customer customer) {
		MyMap paramMap = new MyMap();
		Page<MyMap> page = null;
		page = taskCenterService.findUnderAllToDoListByPage(new Page<MyMap>(request, response), paramMap);
		model.addAttribute("page", page);
		model.addAttribute("headUrl", "/credit/taskCenter/underLoanList");
		model.addAttribute("loginOfficeId", UserUtils.getUser().getCompany().getId());
		// 根据不同的url跳转到不同的页面
		return "app/credit/underLoanTask/todo_business_list";
	}*/


	// 灵活待办任务
	@RequestMapping(value = "/underLoanList/{taskDefKey}")
	public String underLoanList(HttpServletRequest request, HttpServletResponse response, Model model,
								  Customer customer, @PathVariable(value = "taskDefKey") String taskDefKey) {
		MyMap paramMap = new MyMap();
		paramMap.put("applyProductTypeCode", customer.getApplyProductTypeCode());
		paramMap.put("custName", customer.getCusName());
		paramMap.put("applyNo", customer.getApplyNo());
		paramMap.put("taskDefKey", taskDefKey);
		Page<MyMap> page = null;
		page = taskCenterService.findUnderAllToDoListByPage(new Page<MyMap>(request, response), paramMap);
		/*if(!("under_tb".equals(taskDefKey))){
			List<MyMap> list = page.getList();
			for (MyMap map: list) {
				FindIsRegisterRequest findIsRegister=new FindIsRegisterRequest("1",(String)map.get("unSocCreditNo"));
				findIsRegister.setMobile((String)map.get("corporationMobile"));
				findIsRegister.setUserRole("0");//借款人
				FindIsRegisterResponse findIsRegisterResponse = Facade.facade.findGedRegisterInterface(findIsRegister, (String)map.get("applyNo"));
				if (findIsRegisterResponse != null) {
					//String data =findIsRegisterResponse.getData().get//电话号码
					String data = null;
					if (findIsRegisterResponse.getData() != null) {
						data = findIsRegisterResponse.getData().getMobile();
					}
					map.put("gedNumber",data);
				}
			}
		}*/
		model.addAttribute("page", page);
		model.addAttribute("customer", customer);
		model.addAttribute("headUrl", "/credit/taskCenter/underLoanList/" + taskDefKey);
		model.addAttribute("taskDefKey", taskDefKey);
		model.addAttribute("loginOfficeId", UserUtils.getUser().getCompany().getId());

		return "app/credit/underLoanTask/todo_business_list";
	}

	@RequestMapping(value = "/underLoanDone/{taskDefKey}")
	public String underLoanDone(HttpServletRequest request, HttpServletResponse response, String cwfkFlag,
								Model model, Customer customer, @PathVariable(value = "taskDefKey") String taskDefKey) {
		MyMap paramMap = new MyMap();
		paramMap.put("applyProductTypeCode", customer.getApplyProductTypeCode());
		paramMap.put("custName", customer.getCusName());
		paramMap.put("applyNo", customer.getApplyNo());
		paramMap.put("taskDefKey", taskDefKey);
		Page<MyMap> page = null;

		page = taskCenterService.findUnderAllDoneListByPage(new Page<MyMap>(request, response), paramMap);
		model.addAttribute("page", page);
		model.addAttribute("customer", customer);
		model.addAttribute("headUrl", "/credit/taskCenter/underLoanDone/" + taskDefKey);
		model.addAttribute("taskDefKey", taskDefKey);
		model.addAttribute("loginOfficeId", UserUtils.getUser().getCompany().getId());
		return "app/credit/underLoanTask/todone_business_list";
	}

	@RequestMapping(value = "/underEndProcess/{taskDefKey}")
	public String underEndProcess(HttpServletRequest request, HttpServletResponse response, String cwfkFlag,
								Model model, Customer customer, @PathVariable(value = "taskDefKey") String taskDefKey) {
		MyMap paramMap = new MyMap();
		paramMap.put("applyProductTypeCode", customer.getApplyProductTypeCode());
		paramMap.put("custName", customer.getCusName());
		paramMap.put("applyNo", customer.getApplyNo());
		paramMap.put("taskDefKey", taskDefKey);
		Page<MyMap> page = null;

		page = taskCenterService.findUnderEndProcess(new Page<MyMap>(request, response), paramMap);
		model.addAttribute("page", page);
		model.addAttribute("customer", customer);
		model.addAttribute("flagLoan", "1");
		model.addAttribute("headUrl", "/credit/taskCenter/underEndProcess/" + taskDefKey);
		model.addAttribute("taskDefKey", taskDefKey);
		model.addAttribute("loginOfficeId", UserUtils.getUser().getCompany().getId());
		return "app/credit/underLoanTask/todone_business_list";
	}


}
