package com.resoft.postloan.accountCleanAllocate.web;

import java.util.HashMap;
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

import com.resoft.postloan.accountCleanAllocate.entity.AccountCleanAllocate;
import com.resoft.postloan.accountCleanAllocate.service.AccountCleanAllocateService;
import com.resoft.postloan.common.utils.AjaxView;
import com.resoft.postloan.common.utils.Constants;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 借后清收任务分配表Controller
 * @author yanwanmei
 * @version 2016-06-02
 */
@Controller
@RequestMapping(value = "${adminPath}/postloan/accountCleanAllocate")
public class AccountCleanAllocateController extends BaseController {

	@Autowired
	private AccountCleanAllocateService accountCleanAllocateService;
	
	
	@ModelAttribute
	public AccountCleanAllocate get(@RequestParam(required=false) String id) {
		AccountCleanAllocate entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = accountCleanAllocateService.get(id);
		}
		if (entity == null){
			entity = new AccountCleanAllocate();
		}
		return entity;
	}
	
	@RequiresPermissions("postloan:accountCleanAllocate:view")
	@RequestMapping(value = {"list", ""})
	public String list(AccountCleanAllocate accountCleanAllocate, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AccountCleanAllocate> page = accountCleanAllocateService.findCustomPage(new Page<AccountCleanAllocate>(request, response), accountCleanAllocate); 
		model.addAttribute("page", page);
		return "app/postloan/accountCleanAllocate/accountCleanAllocateList";
	}

	@RequiresPermissions("postloan:accountCleanAllocate:view")
	@RequestMapping(value = "form")
	public String form(AccountCleanAllocate accountCleanAllocate, Model model) {
		model.addAttribute("accountCleanAllocate", accountCleanAllocate);
		return "app/postloan/accountCleanAllocate/accountCleanAllocateForm";
	}

	@ResponseBody
	@RequiresPermissions("postloan:checkTwentyFive:edit")
	@RequestMapping(value = "save")
	public AjaxView save(AccountCleanAllocate accountCleanAllocate, Model model, RedirectAttributes redirectAttributes) {
		AjaxView ajaxView = new AjaxView();
		//区域经理的登录id和name
		try {
			String allocateById = UserUtils.getUser().getId();
			String allocateBy = UserUtils.getUser().getName();
			accountCleanAllocate.setAllocateById(allocateById);
			accountCleanAllocate.setAllocateBy(allocateBy);
			accountCleanAllocateService.save(accountCleanAllocate);
			ajaxView.setSuccess().setMessage("任务指定成功");
		} catch (Exception e) {
			logger.error("任务指定失败", e);
			ajaxView.setFailed().setMessage("任务指定失败");
		}
		return ajaxView;
	}
	
	@RequiresPermissions("postloan:accountCleanAllocate:edit")
	@RequestMapping(value = "delete")
	public String delete(AccountCleanAllocate accountCleanAllocate, RedirectAttributes redirectAttributes) {
		accountCleanAllocateService.delete(accountCleanAllocate);
		addMessage(redirectAttributes, "删除借后清收任务分配表成功");
		return "redirect:"+Global.getAdminPath()+"/AccountCleanAllocate/accountCleanAllocate/?repage";
	}
	
	@ResponseBody
	@RequiresPermissions("postloan:accountCleanAllocate:view")
	@RequestMapping(value = "finishAccountClean")
	public AjaxView finishAccountClean(String contractNo,String flag) {
		AjaxView ajaxView = new AjaxView();
		try {
			String flags = accountCleanAllocateService.updateStatus(contractNo);
			if("true".equalsIgnoreCase(flags)){
				ajaxView.setSuccess().setMessage("清收结束");
			}else{
				ajaxView.setFailed().setMessage("清收结束失败");

			}
		} catch (Exception e) {
			ajaxView.setFailed().setMessage("清收结束失败");
			logger.error("清收结束失败", e);
		}
		ajaxView.setData(flag);
		return ajaxView;
	}
	
	@ResponseBody
	@RequiresPermissions("postloan:debtCollectionLegal:view")
	@RequestMapping(value = "toApprove")
	public AjaxView toApprove(String contractNo,AccountCleanAllocate accountCleanAllocate) {
		AjaxView ajaxView = new AjaxView();
		try {
			Map<String, String> param = new HashMap<String, String>();
			param.put("allocateType", Constants.TO_BE_CHECKED);
			param.put("contractNo", contractNo);
			accountCleanAllocateService.save(accountCleanAllocate);
			ajaxView.setSuccess().setMessage("清收任务提交成功");
		} catch (Exception e) {
			ajaxView.setFailed().setMessage("清收任务提交失败");
			logger.error("清收任务提交失败", e);
		}
		return ajaxView;
	}
	
	
}