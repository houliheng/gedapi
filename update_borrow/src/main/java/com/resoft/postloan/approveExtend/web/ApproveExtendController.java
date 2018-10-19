package com.resoft.postloan.approveExtend.web;

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
import com.resoft.postloan.applyExtend.entity.ApplyExtend;
import com.resoft.postloan.applyExtend.service.ApplyExtendService;
import com.resoft.postloan.approveExtend.entity.ApproveExtend;
import com.resoft.postloan.approveExtend.service.ApproveExtendService;
import com.resoft.postloan.common.utils.AjaxView;
import com.resoft.postloan.taskCenter.entity.ActTaskParam;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 合同展期审批Controller
 * 
 * @author wangguodong
 * @version 2016-05-23
 */
@Controller
@RequestMapping(value = "${adminPath}/postloan/approveExtend")
public class ApproveExtendController extends BaseController {

	@Autowired
	private ApproveExtendService approveExtendService;

	@Autowired
	private ApplyExtendService applyExtendService;

	@Autowired
	private PLContractService plContractService;

	@ModelAttribute
	public ApproveExtend get(@RequestParam(required = false) String id) {
		ApproveExtend entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = approveExtendService.get(id);
		}
		if (entity == null) {
			entity = new ApproveExtend();
		}
		return entity;
	}

	@RequiresPermissions("postloan:approveExtend:view")
	@RequestMapping(value = { "list", "" })
	public String list(ApproveExtend approveExtend, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ApproveExtend> page = approveExtendService.findPage(new Page<ApproveExtend>(request, response), approveExtend);
		model.addAttribute("page", page);
		return "app/postloan/approveExtend/approveExtendList";
	}

	@RequiresPermissions("postloan:approveExtend:edit")
	@RequestMapping(value = "index")
	public String index(ActTaskParam actTaskParam, Model model) {
		PLContract plContract = plContractService.get(actTaskParam.getApplyNo());
		ApplyExtend applyExtend = new ApplyExtend();
		applyExtend = applyExtendService.get(actTaskParam.getExtendId());
		model.addAttribute("applyExtend", applyExtend);
		model.addAttribute("extendId", actTaskParam.getExtendId());
		model.addAttribute("contractNo", applyExtend.getContractNo());
		model.addAttribute("plContract", plContract);
		model.addAttribute("actTaskParam", actTaskParam);
		model.addAttribute("readOnly", true);
		return "app/postloan/approveExtend/approveExtendIndex";
	}

	@RequiresPermissions("postloan:approveExtend:view")
	@RequestMapping(value = "form")
	public String form(ApproveExtend approveExtend, Model model) {
		model.addAttribute("approveExtend", approveExtend);
		return "app/postloan/approveExtend/approveExtendForm";
	}

	@ResponseBody
	@RequiresPermissions("postloan:approveExtend:edit")
	@RequestMapping(value = "save")
	public AjaxView save(ActTaskParam actTaskParam, ApproveExtend approveExtend, Model model, RedirectAttributes redirectAttributes) {
		AjaxView ajaxView = new AjaxView();
		try {
			ajaxView = approveExtendService.updateApplyExtendStatus(actTaskParam, approveExtend);
		} catch (Exception e) {
			logger.error("流程出现问题", e);
			ajaxView.setFailed().setMessage("系统出现问题，请联系管理员。");
		}
		return ajaxView;
	}

	@RequiresPermissions("postloan:approveExtend:edit")
	@RequestMapping(value = "delete")
	public String delete(ApproveExtend approveExtend, RedirectAttributes redirectAttributes) {
		approveExtendService.delete(approveExtend);
		addMessage(redirectAttributes, "删除合同展期审批成功");
		return "redirect:" + Global.getAdminPath() + "/approveExtend/approveExtend/?repage";
	}

}