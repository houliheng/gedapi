package com.resoft.postloan.checkRemove.web;

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

import com.resoft.postloan.checkRemove.entity.CheckRemove;
import com.resoft.postloan.checkRemove.service.CheckRemoveService;
import com.resoft.postloan.common.utils.AjaxView;
import com.resoft.postloan.common.utils.Constants;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 核销Controller
 * 
 * @author zhaohuakui
 * @version 2016-05-23
 */
@Controller
@RequestMapping(value = "${adminPath}/postloan/checkRemove")
public class CheckRemoveController extends BaseController {

	@Autowired
	private CheckRemoveService checkRemoveService;

	@ModelAttribute
	public CheckRemove get(@RequestParam(required = false) String id) {
		CheckRemove entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = checkRemoveService.get(id);
		}
		if (entity == null) {
			entity = new CheckRemove();
		}
		return entity;
	}

	@RequiresPermissions("postloan:checkRemove:view")
	@RequestMapping(value = { "list", "" })
	public String list(CheckRemove checkRemove, HttpServletRequest request, HttpServletResponse response, Model model) {
		if (checkRemove.getCheckRemoveStatus() == null) {
			checkRemove.setCheckRemoveStatus(Constants.CHECK_REMOVE_STATUS_WSQ);
		}
		Page<CheckRemove> page = checkRemoveService.findPage(new Page<CheckRemove>(request, response), checkRemove);
		model.addAttribute("page", page);
		return "app/postloan/checkRemove/checkRemoveList";
	}

	@RequiresPermissions("postloan:checkRemove:view")
	@RequestMapping(value = "approveListDQ")
	public String DQApprove(CheckRemove checkRemove, HttpServletRequest request, HttpServletResponse response, Model model) {
		checkRemove.setCheckRemoveStatus(Constants.CHECK_REMOVE_STATUS_SQWSH);
		Page<CheckRemove> page = checkRemoveService.findPage(new Page<CheckRemove>(request, response), checkRemove);
		model.addAttribute("page", page);
		model.addAttribute("ZBorDQ", Constants.CHECK_REMOVE_DQSP);
		model.addAttribute("approveList", "approveListDQ");
		return "app/postloan/checkRemove/checkRemoveApproveList";
	}

	@RequiresPermissions("postloan:checkRemove:view")
	@RequestMapping(value = "approveListZB")
	public String ZBApprove(CheckRemove checkRemove, HttpServletRequest request, HttpServletResponse response, Model model) {
		checkRemove.setCheckRemoveStatus(Constants.CHECK_REMOVE_STATUS_DQSHTG);
		Page<CheckRemove> page = checkRemoveService.findPage(new Page<CheckRemove>(request, response), checkRemove);
		model.addAttribute("page", page);
		model.addAttribute("ZBorDQ", Constants.CHECK_REMOVE_ZBSP);
		model.addAttribute("approveList", "approveListZB");
		return "app/postloan/checkRemove/checkRemoveApproveList";
	}

	@RequiresPermissions("postloan:checkRemove:view")
	@RequestMapping(value = "form")
	public String form(CheckRemove checkRemove, Model model) {
		model.addAttribute("checkRemove", checkRemove);
		return "app/postloan/checkRemove/checkRemoveForm";
	}

	@RequiresPermissions("postloan:checkRemove:view")
	@RequestMapping(value = "approveForm")
	public String approveForm(CheckRemove checkRemove, Model model) {
		model.addAttribute("checkRemove", checkRemove);
		return "app/postloan/checkRemove/checkRemoveApproveForm";
	}

	@ResponseBody
	@RequiresPermissions("postloan:checkRemove:edit")
	@RequestMapping(value = "save")
	public AjaxView save(CheckRemove checkRemove) {
		AjaxView ajaxView = new AjaxView();
		CheckRemove remove = new CheckRemove();
		try {
			remove = checkRemoveService.findCheckRemoveByContractNo(checkRemove);
			if (remove != null) {
				checkRemove.setId(remove.getId());
			}
			checkRemove.setCheckRemoveStatus(Constants.CHECK_REMOVE_STATUS_SQWSH);
			checkRemoveService.save(checkRemove);
			ajaxView.setSuccess().setMessage("保存成功");
		} catch (Exception e) {
			logger.error("核销失败", e);
			ajaxView.setFailed().setMessage("保存失败");
		}
		return ajaxView;
	}

	@ResponseBody
	@RequiresPermissions("postloan:checkRemove:edit")
	@RequestMapping(value = "approveSave")
	public AjaxView approveSave(CheckRemove checkRemove, String flag) {
		AjaxView ajaxView = new AjaxView();
		try {
			checkRemoveService.approveCheckRemove(checkRemove, flag);
			ajaxView.setSuccess().setMessage("提交成功");
		} catch (Exception e) {
			logger.error("核销审批失败", e);
			ajaxView.setFailed().setMessage("提交失败");
		}
		return ajaxView;
	}

	@ResponseBody
	@RequiresPermissions("postloan:checkRemove:edit")
	@RequestMapping(value = "validate")
	public AjaxView validate(CheckRemove checkRemove) {
		CheckRemove remove = new CheckRemove();
		AjaxView ajaxView = new AjaxView();
		try {
			remove = checkRemoveService.findCheckRemoveByContractNo(checkRemove);
			if (remove != null) {
				if (Constants.CHECK_REMOVE_STATUS_SQWSH.equals(remove.getCheckRemoveStatus()) || Constants.CHECK_REMOVE_STATUS_DQSHTG.equals(remove.getCheckRemoveStatus())) {
					ajaxView.setFailed().setMessage("此合同正在进行审核，请稍后查看核销状态");
				} else if (Constants.CHECK_REMOVE_STATUS_ZBSHTG.equals(remove.getCheckRemoveStatus())) {
					ajaxView.setFailed().setMessage("此合同审核已通过，无需进行再次核销");
				} else {
					ajaxView.setSuccess();
				}
			} else {
				ajaxView.setSuccess();
			}

		} catch (Exception e) {
			logger.error("核销验证失败", e);
			ajaxView.setFailed().setMessage("操作失败，请联系管理员");
		}
		return ajaxView;
	}
}