package com.resoft.postloan.extendRepayPlan.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.resoft.postloan.common.utils.AjaxView;
import com.resoft.postloan.extendRepayPlan.entity.ExtendRepayPlan;
import com.resoft.postloan.extendRepayPlan.service.ExtendRepayPlanService;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 合同展期还款计划Controller
 * 
 * @author wangguodong
 * @version 2016-05-23
 */
@Controller
@RequestMapping(value = "${adminPath}/postloan/extendRepayPlan")
public class ExtendRepayPlanController extends BaseController {

	@Autowired
	private ExtendRepayPlanService extendRepayPlanService;

	@ModelAttribute
	public ExtendRepayPlan get(@RequestParam(required = false) String id) {
		ExtendRepayPlan entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = extendRepayPlanService.get(id);
		}
		if (entity == null) {
			entity = new ExtendRepayPlan();
		}
		return entity;
	}

	@RequiresPermissions("postloan:extendRepayPlan:view")
	@RequestMapping(value = { "list", "" })
	public String list(ExtendRepayPlan extendRepayPlan, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ExtendRepayPlan> page = extendRepayPlanService.findPage(new Page<ExtendRepayPlan>(request, response), extendRepayPlan);
		model.addAttribute("contractNo", extendRepayPlan.getContractNo());
		model.addAttribute("page", page);
		return "app/postloan/extendRepayPlan/extendRepayPlanList";
	}

	@RequiresPermissions("postloan:extendRepayPlan:view")
	@RequestMapping(value = "form")
	public String form(ExtendRepayPlan extendRepayPlan, String readonly, Model model) {
		if ("readonly".equals(readonly)) {
			model.addAttribute("readonly", true);
		}
		model.addAttribute("extendRepayPlan", extendRepayPlan);
		return "app/postloan/extendRepayPlan/extendRepayPlanForm";
	}

	@ResponseBody
	@RequiresPermissions("postloan:extendRepayPlan:edit")
	@RequestMapping(value = "save")
	public AjaxView save(ExtendRepayPlan extendRepayPlan, Model model, RedirectAttributes redirectAttributes) {
		AjaxView ajaxView = new AjaxView();
		try {
			int oldPeriodNum = extendRepayPlanService.findMaxExtendPeriodNum(extendRepayPlan.getContractNo());
			if (StringUtils.isBlank(extendRepayPlan.getId())) {
				if ((oldPeriodNum + 1) == Integer.parseInt(extendRepayPlan.getExtendPeriodNum())) {
					extendRepayPlanService.save(extendRepayPlan);
					ajaxView.put("contractNo", extendRepayPlan.getContractNo());
					ajaxView.put("periodNum", oldPeriodNum + 1);
					ajaxView.setSuccess().setMessage("保存合同展期还款计划成功");
				} else {
					ajaxView.setFailed().setMessage("请确认期数是否正确");
				}
			} else {
				extendRepayPlanService.save(extendRepayPlan);
				ajaxView.put("contractNo", extendRepayPlan.getContractNo());
				ajaxView.put("periodNum", oldPeriodNum);
				ajaxView.setSuccess().setMessage("更新合同展期还款计划成功");
			}
		} catch (Exception e) {
			logger.error("保存合同展期还款计划失败", e);
			ajaxView.setFailed().setMessage("保存合同展期还款计划失败");
		}
		return ajaxView;
	}

	@ResponseBody
	@RequiresPermissions("postloan:extendRepayPlan:edit")
	@RequestMapping(value = "delete")
	public AjaxView delete(ExtendRepayPlan extendRepayPlan) {
		AjaxView ajaxView = new AjaxView();
		int oldPeriodNum = extendRepayPlanService.findMaxExtendPeriodNum(extendRepayPlan.getContractNo());
		try {
			if (oldPeriodNum != Integer.parseInt(extendRepayPlan.getExtendPeriodNum())) {
				ajaxView.setFailed().setMessage("此期非最后一期，不能直接删除");
			} else {
				extendRepayPlanService.delete(extendRepayPlan);
				ajaxView.put("periodNum", oldPeriodNum - 1);
				ajaxView.setSuccess().setMessage("删除合同展期还款计划成功");
			}
		} catch (Exception e) {
			ajaxView.setFailed().setMessage("删除合同展期还款计划失败");
			logger.error("删除合同展期还款计划失败", e);
		}
		return ajaxView;
	}

}