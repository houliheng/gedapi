package com.resoft.postloan.collateralDisposal.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.resoft.postloan.collateralDisposal.entity.CollateralDisposal;
import com.resoft.postloan.collateralDisposal.entity.TaskTempEntity;
import com.resoft.postloan.collateralDisposal.service.CollateralDisposalService;
import com.resoft.postloan.collateralDisposalConclusion.entity.CollateralDisposalConclusion;
import com.resoft.postloan.collateralDisposalConclusion.service.CollateralDisposalConclusionService;
import com.resoft.postloan.common.utils.AjaxView;
import com.resoft.postloan.taskCenter.entity.ActTaskParam;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 借后抵押物处置Controller
 * 
 * @author wangguodong
 * @version 2017-01-04
 */
@Controller
@RequestMapping(value = "${adminPath}/postloan/collateralDisposal")
public class CollateralDisposalController extends BaseController {

	@Autowired
	private CollateralDisposalService collateralDisposalService;

	@Autowired
	private CollateralDisposalConclusionService collateralDisposalConclusionService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private ActTaskService actTaskService;

	@ModelAttribute
	public CollateralDisposal get(@RequestParam(required = false) String id) {
		CollateralDisposal entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = collateralDisposalService.get(id);
		}
		if (entity == null) {
			entity = new CollateralDisposal();
		}
		return entity;
	}

	@RequiresPermissions("postloan:collateralDisposal:view")
	@RequestMapping(value = { "list", "" })
	public String list(CollateralDisposal collateralDisposal, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CollateralDisposal> page = collateralDisposalService.findPage(new Page<CollateralDisposal>(request, response), collateralDisposal);
		model.addAttribute("page", page);
		return "app/postloan/collateralDisposal/collateralDisposalList";
	}

	@RequiresPermissions("postloan:collateralDisposal:view")
	@RequestMapping(value = "toDoneList")
	public String toDoneList(CollateralDisposal collateralDisposal, HttpServletRequest request, HttpServletResponse response, Model model) {
		collateralDisposal.setCreateById(UserUtils.getUser().getId());
		Page<CollateralDisposal> page = collateralDisposalService.findtoDoneList(new Page<CollateralDisposal>(request, response), collateralDisposal);
		model.addAttribute("page", page);
		return "app/postloan/collateralDisposal/collateralDisposalToDoneList";
	}

	@RequiresPermissions("postloan:collateralDisposal:view")
	@RequestMapping(value = "toTaskDown")
	public String toTaskDown(ActTaskParam actTaskParam, CollateralDisposal collateralDisposal, Model model) {
		Map<String, Object> params = Maps.newHashMap();
		params.put("companyId", collateralDisposal.getCompanyId());
		if (StringUtils.isNull(actTaskParam.getApplyNo())) {
			params.put("allyId", DictUtils.getDictValue("大区任务下发群组", "COLLATERAL_DISPOSAL_ALLY", null));
		} else {
			params.put("allyId", DictUtils.getDictValue("区域任务处置群组", "COLLATERAL_DISPOSAL_ALLY", null));
		}
		List<Map<String, Object>> maps = collateralDisposalService.findOrgUserList(params);
		model.addAttribute("userList", maps);
		model.addAttribute("actTaskParam", actTaskParam);
		model.addAttribute("collateralDisposal", collateralDisposal);
		return "app/postloan/collateralDisposal/orgUser";
	}

	@ResponseBody
	@RequiresPermissions("postloan:collateralDisposal:view")
	@RequestMapping(value = "taskDown")
	public AjaxView taskDown(ActTaskParam actTaskParam, CollateralDisposal collateralDisposal, String loginName, Model model) {
		AjaxView ajaxView = new AjaxView();
		try {
			collateralDisposalService.taskDown(actTaskParam, collateralDisposal, loginName);
			ajaxView.setSuccess().setMessage("抵押物处置任务下发成功");
		} catch (Exception e) {
			logger.error("抵押物处置任务下发失败", e);
			ajaxView.setFailed().setMessage("抵押物处置任务下发失败");
		}
		return ajaxView;
	}

	// 详情页面
	@RequestMapping(value = "toDetailsPage")
	public String toDetailsPage(String applyNo) {
		String url = Global.getConfig("CRE_VISIT_URL");
		return "redirect:" + url + "/f/credit/PLworkflow/parentCompanyAudit?applyNo=" + applyNo;
	}

	// 处置
	@RequestMapping(value = "mortgage")
	public String mortgage(ActTaskParam actTaskParam, String readOnly, Model model) {
		
		Task task = taskService.createTaskQuery().taskId(actTaskParam.getTaskId()).singleResult();
		if (!(StringUtils.isNull(task)) && StringUtils.isNull(task.getAssignee())) {
			actTaskService.claim(task.getId(), UserUtils.getUser().getLoginName());
		}
		// 合同信息
		List<TaskTempEntity> taskTempEntities = collateralDisposalService.findContractNoAndOverdueDataByProcInsId(actTaskParam.getProcInstId());
		TaskTempEntity taskTempEntity = new TaskTempEntity();
		if (taskTempEntities != null && taskTempEntities.size() != 0) {
			taskTempEntity = taskTempEntities.get(0);
		}
		Map<String, Object> params = Maps.newHashMap();
		CollateralDisposalConclusion collateralDisposalConclusion = new CollateralDisposalConclusion();
		CollateralDisposalConclusion collateralDisposalConclusionQY = new CollateralDisposalConclusion();
		CollateralDisposalConclusion collateralDisposalConclusionDQ = new CollateralDisposalConclusion();
		params.put("contractNo", taskTempEntity.getContractNo());
		params.put("periodNum", taskTempEntity.getPeriodNum());
		List<CollateralDisposalConclusion> collateralDisposalConclusions = collateralDisposalConclusionService.findCollateralDisposalConclusionListByContractNoAndPeriodNum(params);
		for (int i = 0; i < collateralDisposalConclusions.size(); i++) {
			if ("utask_zbrwcz".equals(collateralDisposalConclusions.get(i).getTaskDefKey())) {
				collateralDisposalConclusion = collateralDisposalConclusions.get(i);
			} else if ("utask_dqrwcz".equals(collateralDisposalConclusions.get(i).getTaskDefKey())) {
				collateralDisposalConclusionDQ = collateralDisposalConclusions.get(i);
			} else {
				collateralDisposalConclusionQY = collateralDisposalConclusions.get(i);
			}
		}
		if (StringUtils.isNull(collateralDisposalConclusion.getId())) {
			if (!(StringUtils.isNull(collateralDisposalConclusionDQ))) {
				collateralDisposalConclusion = collateralDisposalConclusionDQ;
			} else {
				if (!(StringUtils.isNull(collateralDisposalConclusionQY.getId()))) {
					collateralDisposalConclusion = collateralDisposalConclusionQY;
				}
			}
		}
		model.addAttribute("collateralDisposalConclusionQY", collateralDisposalConclusionQY);
		model.addAttribute("collateralDisposalConclusionDQ", collateralDisposalConclusionDQ);
		model.addAttribute("collateralDisposalConclusion", collateralDisposalConclusion);
		model.addAttribute("taskTempEntity", taskTempEntity);
		model.addAttribute("actTaskParam", actTaskParam);
		model.addAttribute("readOnly", readOnly);
		return "app/postloan/mortgage/mortgageIndex";
	}
}