package com.resoft.postloan.overdueHandle.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.resoft.postloan.taskCenter.entity.ActTaskParam;
import com.resoft.multds.accounting.PLContract.service.PLContractService;
import com.resoft.postloan.common.utils.AjaxView;
import com.resoft.postloan.common.utils.Constants;
import com.resoft.postloan.overdueHandle.entity.OverdueHandle;
import com.resoft.postloan.overdueHandle.entity.OverdueHandleConclusion;
import com.resoft.postloan.overdueHandle.service.OverdueHandleConclusionService;
import com.resoft.postloan.overdueHandle.service.OverdueHandleService;
import com.resoft.multds.accounting.PLContract.entity.PLContract;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.act.entity.MyMap;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 贷后逾期处理Controller
 * 
 * @author lixing
 * @version 2017-01-04
 */
@Controller
@RequestMapping(value = "${adminPath}/postloan/overdueHandle")
public class OverdueHandleController extends BaseController {

	@Autowired
	private OverdueHandleService overdueHandleService;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private PLContractService pLContractService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private OverdueHandleConclusionService overdueHandleConclusionService;

	@ModelAttribute
	public OverdueHandle get(@RequestParam(required = false) String id) {
		OverdueHandle entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = overdueHandleService.get(id);
		}
		if (entity == null) {
			entity = new OverdueHandle();
		}
		return entity;
	}

	/*
	 * @RequiresPermissions("postloan:overdueHandle:view")
	 * 
	 * @RequestMapping(value = {"list", ""}) public String list(OverdueHandle
	 * overdueHandle, HttpServletRequest request, HttpServletResponse response,
	 * Model model) { Page<OverdueHandle> page =
	 * overdueHandleService.findCustomPage(new Page<OverdueHandle>(request,
	 * response), overdueHandle); model.addAttribute("page", page); return
	 * "app/postloan/overdueHandle/overdueHandleList"; }
	 */

	@RequiresPermissions("postloan:overdueHandle:view")
	@RequestMapping(value = "form")
	public String form(OverdueHandle overdueHandle, Model model) {
		model.addAttribute("overdueHandle", overdueHandle);
		return "app/postloan/overdueHandle/overdueHandleForm";
	}

	@RequiresPermissions("postloan:overdueHandle:edit")
	@RequestMapping(value = "save")
	public String save(OverdueHandle overdueHandle, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, overdueHandle)) {
			return form(overdueHandle, model);
		}
		overdueHandleService.save(overdueHandle);
		addMessage(redirectAttributes, "保存贷后逾期处理成功");
		return "redirect:" + Global.getAdminPath() + "/overdueHandle/overdueHandle/?repage";
	}

	@RequiresPermissions("postloan:overdueHandle:edit")
	@RequestMapping(value = "delete")
	public String delete(OverdueHandle overdueHandle, RedirectAttributes redirectAttributes) {
		overdueHandleService.delete(overdueHandle);
		addMessage(redirectAttributes, "删除贷后逾期处理成功");
		return "redirect:" + Global.getAdminPath() + "/overdueHandle/overdueHandle/?repage";
	}

	// 总部下发待办列表
	@RequiresPermissions("postloan:overdueHandle:view")
	@RequestMapping(value = "zbxfList")
	public String zbxfList(OverdueHandle overdueHandle, HttpServletRequest request, HttpServletResponse response, Model model) {
		MyMap paramMap = new MyMap();
		paramMap.put("custName", overdueHandle.getCustName());
		paramMap.put("contractNo", overdueHandle.getContractNo());
		Page<MyMap> page = overdueHandleService.findZBXFList(new Page<MyMap>(request, response), paramMap);
		model.addAttribute("page", page);
		return "app/postloan/overdueHandle/overdueHandleZBXFList";
	}

	// 总部下发已办列表
	@RequiresPermissions("postloan:overdueHandle:view")
	@RequestMapping(value = "zbxfDoneList")
	public String zbxfDoneList(OverdueHandle overdueHandle, HttpServletRequest request, HttpServletResponse response, Model model) {
		MyMap paramMap = new MyMap();
		paramMap.put("createBy", UserUtils.getUser().getId());
		paramMap.put("custName", overdueHandle.getCustName());
		paramMap.put("contractNo", overdueHandle.getContractNo());
		Page<MyMap> page = overdueHandleService.findZBXFDoneList(new Page<MyMap>(request, response), paramMap);
		model.addAttribute("page", page);
		return "app/postloan/overdueHandle/overdueHandleZBXFDoneList";
	}

	@ResponseBody
	@RequiresPermissions("postloan:overdueHandle:view")
	@RequestMapping(value = "zbxf")
	public AjaxView zbxf(OverdueHandle overdueHandle, Model model) {
		try {
			String contractNo = URLDecoder.decode(overdueHandle.getContractNo(), "UTF-8");
			overdueHandle.setContractNo(contractNo);
		} catch (UnsupportedEncodingException e) {
			logger.error("汉字解码失败", e);
		}
		AjaxView ajaxView = new AjaxView();
		try {
			overdueHandle.setHandleStatus("1");
			overdueHandleService.save(overdueHandle);
			String procInstId = actTaskService.startProcess("gqcc_overdue_handle", "PL_OVERDUE_HANDLE", overdueHandle.getId());
			overdueHandle.setProcInsId(procInstId);
			overdueHandleService.save(overdueHandle);
			ajaxView.setSuccess().setMessage("任务下发成功！");
		} catch (Exception e) {
			logger.error("逾期处理总部任务下发失败！", e);
			ajaxView.setFailed().setMessage("任务下发失败！");
		}
		return ajaxView;
	}

	// 待办任务
	@RequestMapping(value = "/list/{taskDefKey}")
	public String todoList(HttpServletRequest request, HttpServletResponse response, Model model, OverdueHandle overdueHandle, @PathVariable(value = "taskDefKey") String taskDefKey) {
		MyMap paramMap = new MyMap();
		paramMap.put("taskDefKey", taskDefKey);
		paramMap.put("custName", overdueHandle.getCustName());
		paramMap.put("contractNo", overdueHandle.getContractNo());
		paramMap.put("userLoginName", UserUtils.getUser().getLoginName());
		Page<MyMap> page = null;
		if (Constants.ADMIN_USER_ID.equalsIgnoreCase(UserUtils.getUser().getId())) {
			page = overdueHandleService.todoListAdmin(new Page<MyMap>(request, response), paramMap);
		} else {
			page = overdueHandleService.todoList(new Page<MyMap>(request, response), paramMap);
		}
		model.addAttribute("page", page);
		model.addAttribute("taskDefKey", taskDefKey);
		return "app/postloan/overdueHandle/overdueHandleList";
	}

	// 总部下发选择人员列表页面
	@RequiresPermissions("postloan:overdueHandle:edit")
	@RequestMapping(value = "zbxfAssignList")
	public String zbxfAssignList(OverdueHandle overdueHandle, ActTaskParam actTaskParam, HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			String contractNo = URLDecoder.decode(overdueHandle.getContractNo(), "UTF-8");
			overdueHandle.setContractNo(contractNo);
		} catch (UnsupportedEncodingException e) {
			logger.error("汉字解码失败", e);
		}
		MyMap paramMap = new MyMap();
		String userName = request.getParameter("userName");
		if (userName != null && !userName.isEmpty()) {
			paramMap.put("userName", userName);
		}
		String loginName = request.getParameter("loginName");
		if (loginName != null && !loginName.isEmpty()) {
			paramMap.put("loginName", loginName);
		}
		List<PLContract> contractList = pLContractService.findListByContractNo(overdueHandle.getContractNo());
		if (contractList.size() == 1) {
			if (actTaskParam.getTaskId() != null && !actTaskParam.getTaskId().isEmpty()) {
				paramMap.put("companyId", contractList.get(0).getOrgLevel3().getId());// 区域id
			} else {
				paramMap.put("companyId", contractList.get(0).getOrgLevel2().getId());// 大区id
			}
			paramMap.put("groupId", DictUtils.getDictValue("overdue_handle_group", "overdue_handle_group", ""));
			Page<MyMap> page = overdueHandleService.findZBXFAssignList(new Page<MyMap>(request, response), paramMap);
			model.addAttribute("page", page);
		} else {
			model.addAttribute("message", "查询任务下发人员列表发生异常！");
		}
		model.addAttribute("contractNo", overdueHandle.getContractNo());
		model.addAttribute("periodNum", overdueHandle.getPeriodNum());
		model.addAttribute("actTaskParam", actTaskParam);
		return "app/postloan/overdueHandle/zbxfAssignList";
	}

	// 总部下发指派人，启动流程
	@ResponseBody
	@RequiresPermissions("postloan:overdueHandle:edit")
	@RequestMapping(value = "zbxfAssign")
	public AjaxView zbxfAssign(OverdueHandle overdueHandle, HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxView ajaxView = new AjaxView();
		try {
			String loginName = request.getParameter("loginName");
			overdueHandle.setHandleStatus("1");
			overdueHandleService.save(overdueHandle);
			String procInstId = actTaskService.startProcess("gqcc_overdue_handle", "PL_OVERDUE_HANDLE", overdueHandle.getId());
			overdueHandle.setProcInsId(procInstId);
			overdueHandleService.save(overdueHandle);
			List<HistoricTaskInstance> hisTasks = historyService.createHistoricTaskInstanceQuery().processInstanceId(procInstId).unfinished().orderByHistoricTaskInstanceStartTime().asc().list();
			String taskId = hisTasks.get(0).getId();
			taskService.claim(taskId, loginName);
			ajaxView.setSuccess().setMessage("任务下发成功！");
		} catch (Exception e) {
			logger.error("逾期处理总部任务下发失败！", e);
			ajaxView.setFailed().setMessage("任务下发失败！");
		}
		return ajaxView;
	}

	// 大区下发指派人，提交流程
	@ResponseBody
	@RequiresPermissions("postloan:overdueHandle:edit")
	@RequestMapping(value = "dqxfAssign")
	public AjaxView dqxfAssign(OverdueHandle overdueHandle, ActTaskParam actTaskParam, HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxView ajaxView = new AjaxView();
		try {
			MyMap paramMap = new MyMap();
			paramMap.put("contractNo", overdueHandle.getContractNo());
			paramMap.put("periodNum", overdueHandle.getPeriodNum());
			paramMap.put("taskDefKey", "utask_dqxf");
			OverdueHandleConclusion overdueHandleConclusion = overdueHandleConclusionService.findConclusionByParams(paramMap);
			if (overdueHandleConclusion == null) {
				overdueHandleConclusion = new OverdueHandleConclusion();
				overdueHandleConclusion.setTaskDefKey("utask_dqxf");
				overdueHandleConclusion.setContractNo(overdueHandle.getContractNo());
				overdueHandleConclusion.setPeriodNum(overdueHandle.getPeriodNum());
				overdueHandleConclusionService.save(overdueHandleConclusion);
			}
			actTaskService.complete(actTaskParam.getTaskId(), actTaskParam.getProcInstId(), "【提交】" + "大区下发", "提交", null);
			String loginName = request.getParameter("loginName");
			List<HistoricTaskInstance> hisTasks = historyService.createHistoricTaskInstanceQuery().processInstanceId(actTaskParam.getProcInstId()).unfinished().orderByHistoricTaskInstanceStartTime().asc().list();
			String taskId = hisTasks.get(0).getId();
			taskService.claim(taskId, loginName);
			ajaxView.setSuccess().setMessage("任务下发成功！");
		} catch (Exception e) {
			logger.error("逾期处理大区任务下发失败！", e);
			ajaxView.setFailed().setMessage("任务下发失败！");
		}
		return ajaxView;
	}

	// 大区下发不指派人提交流程
	@ResponseBody
	@RequiresPermissions("postloan:overdueHandle:edit")
	@RequestMapping(value = "dqxf")
	public AjaxView dqxf(OverdueHandle overdueHandle, ActTaskParam actTaskParam, HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			String contractNo = URLDecoder.decode(overdueHandle.getContractNo(), "UTF-8");
			overdueHandle.setContractNo(contractNo);
		} catch (UnsupportedEncodingException e) {
			logger.error("汉字解码失败", e);
		}
		AjaxView ajaxView = new AjaxView();
		try {
			actTaskService.complete(actTaskParam.getTaskId(), actTaskParam.getProcInstId(), "【提交】", "提交", null);
			ajaxView.setSuccess().setMessage("任务下发成功！");
		} catch (Exception e) {
			logger.error("逾期处理大区任务下发失败！", e);
			ajaxView.setFailed().setMessage("任务下发失败！");
		}
		return ajaxView;
	}

	// 结论
	@RequiresPermissions("postloan:overdueHandle:edit")
	@RequestMapping(value = "doBusiness")
	public String doBusiness(OverdueHandle overdueHandle, ActTaskParam actTaskParam, HttpServletRequest request, HttpServletResponse response, Model model) {
		Task task = taskService.createTaskQuery().taskId(actTaskParam.getTaskId()).singleResult();
		if (task.getAssignee() == null) {
			actTaskService.claim(task.getId(), UserUtils.getUser().getLoginName());
		}
		try {
			String contractNo = URLDecoder.decode(overdueHandle.getContractNo(), "UTF-8");
			overdueHandle.setContractNo(contractNo);
		} catch (UnsupportedEncodingException e) {
			logger.error("汉字解码失败", e);
		}
		MyMap paramMap = new MyMap();
		paramMap.put("contractNo", overdueHandle.getContractNo());
		paramMap.put("periodNum", overdueHandle.getPeriodNum());
		paramMap.put("taskDefKey", actTaskParam.getTaskDefKey());
		OverdueHandleConclusion overdueHandleConclusion = overdueHandleConclusionService.findConclusionByParams(paramMap);
		if (overdueHandleConclusion == null) {
			overdueHandleConclusion = new OverdueHandleConclusion();
			overdueHandleConclusion.setContractNo(overdueHandle.getContractNo());
			overdueHandleConclusion.setPeriodNum(overdueHandle.getPeriodNum());
		}
		// 如果是大区处理环节或者总部处理环节，需要显示区域处理环节的意见
		if (actTaskParam.getTaskDefKey().equalsIgnoreCase("utask_dqcl") || actTaskParam.getTaskDefKey().equalsIgnoreCase("utask_zbcl")) {
			MyMap paramMap2 = new MyMap();
			paramMap2.put("contractNo", overdueHandle.getContractNo());
			paramMap2.put("periodNum", overdueHandle.getPeriodNum());
			paramMap2.put("taskDefKey", "utask_qycl");
			OverdueHandleConclusion overdueHandleConclusionQY = overdueHandleConclusionService.findConclusionByParams(paramMap2);
			if (null != overdueHandleConclusionQY) {
				model.addAttribute("conclusionDescQY", overdueHandleConclusionQY.getConclusionDesc());
			}
		}
		// 如果是总部处理环节或者总部处理环节，还需要显示大区处理环节的意见
		if (actTaskParam.getTaskDefKey().equalsIgnoreCase("utask_zbcl")) {
			MyMap paramMap3 = new MyMap();
			paramMap3.put("contractNo", overdueHandle.getContractNo());
			paramMap3.put("periodNum", overdueHandle.getPeriodNum());
			paramMap3.put("taskDefKey", "utask_dqcl");
			OverdueHandleConclusion overdueHandleConclusionDQ = overdueHandleConclusionService.findConclusionByParams(paramMap3);
			if (null != overdueHandleConclusionDQ) {
				model.addAttribute("conclusionDescDQ", overdueHandleConclusionDQ.getConclusionDesc());
			}
		}
		model.addAttribute("overdueHandleConclusion", overdueHandleConclusion);
		model.addAttribute("actTaskParam", actTaskParam);
		return "app/postloan/overdueHandle/overdueHandleForm";
	}

	// 协议模板下载
	@RequiresPermissions("postloan:overdueHandle:edit")
	@RequestMapping(value = "getTemplate")
	public String getTemplate(HttpServletRequest request, HttpServletResponse response, Model model) {
		response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
		InputStream ips = null;
		try {
			File file = new File(request.getSession().getServletContext().getRealPath("/") + "WEB-INF/classes/协议模板.docx");
			ips = new FileInputStream(file);
			OutputStream ops = response.getOutputStream();
			int data = -1;
			while ((data = ips.read()) != -1) {
				ops.write(data);
			}
			ops.flush();
		} catch (IOException e) {
			logger.error("下载失败", e);
			model.addAttribute("downloadMassage", "下载失败请重试!");
			return "app/postloan/overdueHandle/overdueHandleForm";
		} finally {
			try {
				ips.close();
			} catch (IOException e) {
				logger.error("关闭资源错误", e);
			}
		}
		return null;
	}

	// 灵活已办任务
	@RequiresPermissions("postloan:overdueHandle:view")
	@RequestMapping(value = "/doneList/{taskDefKey}")
	public String doneList(OverdueHandle overdueHandle, HttpServletRequest request, HttpServletResponse response, String cwfkFlag, Model model, @PathVariable(value = "taskDefKey") String taskDefKey) {
		MyMap paramMap = new MyMap();
		paramMap.put("taskDefKey", taskDefKey);
		paramMap.put("custName", overdueHandle.getCustName());
		paramMap.put("contractNo", overdueHandle.getContractNo());
		paramMap.put("userLoginName", UserUtils.getUser().getLoginName());
		Page<MyMap> page = null;
		page = overdueHandleService.doneList(new Page<MyMap>(request, response), paramMap);
		model.addAttribute("page", page);
		model.addAttribute("taskDefKey", taskDefKey);
		model.addAttribute("headUrl", "/credit/taskCenter/toDoneList/" + taskDefKey);
		model.addAttribute("loginOfficeId", UserUtils.getUser().getCompany().getId());
		return "app/postloan/overdueHandle/overdueHandleDoneList";
	}

	// 影像上传
	@RequiresPermissions("postloan:overdueHandle:edit")
	@RequestMapping(value = "/videoUpload")
	public String videoUpload(OverdueHandleConclusion overdueHandleConclusion, ActTaskParam actTaskParam, HttpServletRequest request, HttpServletResponse response) {
		List<PLContract> contractList = pLContractService.findListByContractNo(overdueHandleConclusion.getContractNo());
		String productType = contractList.get(0).getApproProductTypeId();
		String url = Global.getConfig("CRE_VISIT_URL");
		return "redirect:" + url + "/f/credit/PLworkflow/videoUpload?applyNo=" + actTaskParam.getApplyNo() + "&taskDefKey=" + actTaskParam.getTaskDefKey() + "&type=" + productType;
	}

	// 影像查阅
	@RequiresPermissions("postloan:overdueHandle:edit")
	@RequestMapping(value = "/videoView")
	public String videoView(OverdueHandleConclusion overdueHandleConclusion, ActTaskParam actTaskParam, HttpServletRequest request, HttpServletResponse response) {
		List<PLContract> contractList = pLContractService.findListByContractNo(overdueHandleConclusion.getContractNo());
		String productType = contractList.get(0).getApproProductTypeId();
		String url = Global.getConfig("CRE_VISIT_URL");
		return "redirect:" + url + "/f/credit/PLworkflow/videoView?status=" + actTaskParam.getStatus() + "&applyNo=" + actTaskParam.getApplyNo() + "&taskDefKey=" + actTaskParam.getTaskDefKey() + "&productTypeId=" + productType;
	}
}