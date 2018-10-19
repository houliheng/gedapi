package com.resoft.credit.repayPlan.web;

import com.resoft.common.utils.AjaxView;
import com.resoft.common.utils.Constants;
import com.resoft.credit.checkApprove.entity.CheckApprove;
import com.resoft.credit.checkApprove.service.CheckApproveService;
import com.resoft.credit.contract.entity.RepayPlanData;
import com.resoft.credit.contract.service.ContractService;
import com.resoft.credit.repayPlan.utils.RepayPlanUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

import com.google.common.collect.Maps;
import com.resoft.credit.repayPlan.entity.RepayPlan;
import com.resoft.credit.repayPlan.service.RepayPlanService;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 还款计划Controller
 * @author wuxi01
 * @version 2016-03-01
 */
@Controller("creRepayPlanController")
@RequestMapping(value = "${adminPath}/credit/repayPlan")
public class RepayPlanController extends BaseController {

	@Autowired
	private RepayPlanService repayPlanService;

	@Autowired
	private CheckApproveService checkApproveService;

	@Autowired
	private ContractService contractService;
	
	@ModelAttribute
	public RepayPlan get(@RequestParam(required=false) String id) {
		RepayPlan entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = repayPlanService.get(id);
		}
		if (entity == null){
			entity = new RepayPlan();
		}
		return entity;
	}
	
	@RequiresPermissions("credit:checkApprove:repayPlan:view")
	@RequestMapping(value = {"list", ""})
	public String list(RepayPlan repayPlan, HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, String> param = Maps.newConcurrentMap();
		param.put("applyNo", request.getParameter("applyNo"));
		List<RepayPlan> repayPlanList = repayPlanService.getRepayPlanByApplyNo(param);
		model.addAttribute("repayPlanList", repayPlanList);
		return "app/credit/repayPlan/repayPlanList";
	}
	
	//去掉还款计划表的权限设置，因为批复信息和复议结论都会调用这个方法
	@RequestMapping(value = "toRepayPlan")
	public String toRepayPlan(RepayPlan repayPlan, HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, String> param = Maps.newConcurrentMap();
		param.put("applyNo", request.getParameter("applyNo"));
		param.put("taskDefKey", request.getParameter("taskDefKey"));
		List<RepayPlan> repayPlanList = repayPlanService.getRepayPlanByApplyNo(param);
		model.addAttribute("repayPlanList", repayPlanList);
		return "app/credit/repayPlan/checkApproRepayPlanList";
	}

	/*合同面签中的还款计划表*/
	@RequiresPermissions("credit:contract:view")
	@RequestMapping(value = "getRepayPlanByContractNo")
	public String getRepayPlanByContractNo(RepayPlan repayPlan, HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			Map<String, String> param = Maps.newConcurrentMap();
			param.put("applyNo", request.getParameter("applyNo"));
			param.put("taskDefKey", request.getParameter("taskDefKey"));
			List<RepayPlan> repayPlanList = repayPlanService.getRepayPlanByApplyNoAndTaskDefKey(param);
			model.addAttribute("repayPlanList", repayPlanList);
		} catch (Exception e) {
			logger.error("查询还款计划数据失败", e);
		}
		model.addAttribute("taskDefKey", request.getParameter("taskDefKey"));
		return "app/credit/repayPlan/repayPlanList";
	}
	
	@RequiresPermissions("credit:checkApprove:repayPlan:view")
	@RequestMapping(value = "form")
	public String form(RepayPlan repayPlan, Model model) {
		model.addAttribute("repayPlan", repayPlan);
		return "app/credit/repayPlan/repayPlanForm";
	}

	@RequiresPermissions("credit:checkApprove:repayPlan:edit")
	@RequestMapping(value = "save")
	public String save(RepayPlan repayPlan, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, repayPlan)){
			return form(repayPlan, model);
		}
		repayPlanService.save(repayPlan);
		addMessage(redirectAttributes, "保存还款计划成功");
		return "redirect:"+Global.getAdminPath()+"/repayPlan/repayPlan/?repage";
	}
	
	@RequiresPermissions("credit:checkApprove:repayPlan:edit")
	@RequestMapping(value = "delete")
	public String delete(RepayPlan repayPlan, RedirectAttributes redirectAttributes) {
		repayPlanService.delete(repayPlan);
		addMessage(redirectAttributes, "删除还款计划成功");
		return "redirect:"+Global.getAdminPath()+"/repayPlan/repayPlan/?repage";
	}

	@RequestMapping(value = "createUnderRepayPlan")
	@ResponseBody
	public AjaxView createUnderRepayPlan(String applyNo, String taskDefKey, Model model) {
		AjaxView result = new AjaxView();
		try {
			CheckApprove underCheckApprove = checkApproveService.getByApplyNo(applyNo, taskDefKey);
			if (underCheckApprove != null) {
				Map<String, String> params = Maps.newConcurrentMap();
				params.put("applyNo", applyNo);
				params.put("taskDefKey", taskDefKey);
				repayPlanService.deleteRepayPlanByApplyNo(params);
				//生成还款计划表
				RepayPlanData repayPlanData = RepayPlanUtils.packRepayPlan(underCheckApprove);
				repayPlanData.setDeductDate(underCheckApprove.getSignDate());
				List<RepayPlan> repayPlanListTmp = contractService.calculateRepayPlan(repayPlanData);
				// 判断 如果是一次性付清本息 则进行数据处理
				List<RepayPlan> repayPlanList = new ArrayList<>();
				if (Constants.LOAN_REPAY_TYPE_YCXHBFX.equals(underCheckApprove.getApproLoanRepayType())) {
					repayPlanList = contractService.recountData(repayPlanListTmp, underCheckApprove.getApproPeriodValue());
				} else {
					repayPlanList = repayPlanListTmp;
				}
				repayPlanService.insertRepayPlanList(repayPlanList);
				result.setSuccess().setMessage("生成还款计划成功!");
				addMessage(model, "生成还款计划成功!");
			} else {
				result.setFailed().setMessage("生成还款计划失败!找不到借款信息");
				addMessage(model, "生成还款计划失败!找不到借款信息");
			}
		} catch (Exception e) {
			result.setFailed().setMessage("生成还款计划失败!");
			addMessage(model, "生成还款计划失败!");
			logger.error("生成还款计划失败!", e);
		}
		return result;
	}

}