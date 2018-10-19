package com.resoft.postloan.applyExtend.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

import com.resoft.multds.accounting.PLContract.entity.PLContract;
import com.resoft.multds.accounting.PLContract.service.PLContractService;
import com.resoft.multds.credit.PLRepayPlan.entity.PLRepayPlan;
import com.resoft.multds.credit.PLRepayPlan.entity.PLRepayPlanData;
import com.resoft.multds.credit.PLRepayPlan.service.RepayPlanService;
import com.resoft.postloan.applyExtend.entity.ApplyExtend;
import com.resoft.postloan.applyExtend.service.ApplyExtendService;
import com.resoft.postloan.common.utils.AjaxView;
import com.resoft.postloan.common.utils.DateUtils;
import com.resoft.postloan.extendRepayPlan.service.ExtendRepayPlanService;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 合同展期申请Controller
 * 
 * @author wangguodong
 * @version 2016-05-23
 */
@Controller
@RequestMapping(value = "${adminPath}/postloan/applyExtend")
public class ApplyExtendController extends BaseController {

	@Autowired
	private RepayPlanService repayPlanService;

	@Autowired
	private ApplyExtendService applyExtendService;

	@Autowired
	private PLContractService plContractService;

	@Autowired
	private ExtendRepayPlanService extendRepayPlanService;

	@ModelAttribute
	public ApplyExtend get(@RequestParam(required = false) String id) {
		ApplyExtend entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = applyExtendService.get(id);
		}
		if (entity == null) {
			entity = new ApplyExtend();
		}
		return entity;
	}

	@RequiresPermissions("postloan:applyExtend:view")
	@RequestMapping(value = "/list/{flag}")
	public String list(ApplyExtend applyExtend, HttpServletRequest request, HttpServletResponse response, Model model) {
		applyExtend.setOperateOrgId(UserUtils.getUser().getCompany().getId());
		applyExtend.setOperateOrgId(UserUtils.getUser().getCompany().getParentIds());
		Page<ApplyExtend> page = applyExtendService.findPage(new Page<ApplyExtend>(request, response), applyExtend);
		model.addAttribute("page", page);
		model.addAttribute("flag", applyExtend.getFlag());
		model.addAttribute("applyExtend", applyExtend);
		return "app/postloan/applyExtend/applyExtendList";
	}

	@RequiresPermissions("postloan:applyExtend:edit")
	@RequestMapping(value = "index")
	public String index(ApplyExtend applyExtend, Model model) {
		PLContract plContract = plContractService.get(applyExtend.getContractNo());
		if (StringUtils.isBlank(applyExtend.getId())) {
			BigDecimal outStandingAmount = plContractService.getOutStandingCapitalAmount(applyExtend.getContractNo());
			applyExtend.setExtendAmount(outStandingAmount);
		}
		model.addAttribute("applyExtend", applyExtend);
		model.addAttribute("contractNo", applyExtend.getContractNo());
		model.addAttribute("plContract", plContract);
		return "app/postloan/applyExtend/applyExtendIndex";
	}

	@RequiresPermissions("postloan:applyExtend:view")
	@RequestMapping(value = "form")
	public String form(ApplyExtend applyExtend, Model model) {
		model.addAttribute("applyExtend", applyExtend);
		return "app/postloan/applyExtend/applyExtendForm";
	}

	@ResponseBody
	@RequiresPermissions("postloan:applyExtend:edit")
	@RequestMapping(value = "save")
	public AjaxView save(ApplyExtend applyExtend, String saveFlag) {
		AjaxView ajaxView = new AjaxView();
		try {
			PLContract plContract = plContractService.get(applyExtend.getContractNo());
			PLRepayPlanData repayPlanData = new PLRepayPlanData();
			repayPlanData.setApproProductTypeCode(plContract.getApproProductTypeId());
			repayPlanData.setApplyNo(plContract.getApplyNo());
			repayPlanData.setContractNo(plContract.getContractNo());
			repayPlanData.setApproLoanRepayType(plContract.getApproLoanRepayType());
			repayPlanData.setApproPeriodValue(applyExtend.getExtendPeriod());
			repayPlanData.setContractAmount(applyExtend.getExtendAmount());
			repayPlanData.setServiceFeeType(plContract.getServiceFeeType());
			repayPlanData.setServiceFeeRate(applyExtend.getExtendFeePercent());
			repayPlanData.setDeductDate(DateUtils.getNextMonth(applyExtend.getExtendStartDate()));
			List<PLRepayPlan> repayPlanList = new ArrayList<PLRepayPlan>();
			repayPlanList = repayPlanService.calculateRepayPlan(repayPlanData);
			extendRepayPlanService.deleteExtendRepayPlan(applyExtend.getContractNo());
			extendRepayPlanService.saveExtendRepayPlan(repayPlanList);
			// 保存展期申请信息
			applyExtendService.saveApply(applyExtend, saveFlag, ajaxView);
		} catch (Exception e) {
			logger.error("保存合同展期申请失败", e);
			ajaxView.setFailed().setMessage("保存合同展期申请失败");
		}
		return ajaxView;
	}

	@RequiresPermissions("postloan:applyExtend:edit")
	@RequestMapping(value = "delete")
	public String delete(ApplyExtend applyExtend, RedirectAttributes redirectAttributes) {
		applyExtendService.delete(applyExtend);
		addMessage(redirectAttributes, "删除合同展期申请成功");
		return "redirect:" + Global.getAdminPath() + "/applyExtend/applyExtend/?repage";
	}

	@ResponseBody
	@RequiresPermissions("postloan:applyExtend:edit")
	@RequestMapping(value = "validateApplyExtendForm")
	public AjaxView validateApplyExtendForm(String contractNo) {
		AjaxView ajaxView = new AjaxView();
		try {
			ApplyExtend applyExtend = applyExtendService.validateApplyExtend(contractNo);
			if (applyExtend == null) {
				ajaxView.setSuccess();
			} else {
				ajaxView.setFailed().setMessage("该合同已经进入展期流程");
			}
		} catch (Exception e) {
			logger.error("校验展期申请失败", e);
			ajaxView.setFailed().setMessage("数据出现问题，请联系管理员");
		}
		return ajaxView;
	}
}