package com.resoft.postloan.accountClean.web;

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

import com.resoft.postloan.accountClean.entity.AccountClean;
import com.resoft.postloan.accountClean.service.AccountCleanService;
import com.resoft.postloan.common.utils.AjaxView;
import com.resoft.postloan.common.utils.Constants;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.act.entity.MyMap;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 账务清收Controller
 * @author yanwanmei
 * @version 2016-06-02
 */
@Controller
@RequestMapping(value = "${adminPath}/postloan/accountClean")
public class AccountCleanController extends BaseController {

	@Autowired
	private AccountCleanService accountCleanService;
	
	@ModelAttribute
	public AccountClean get(@RequestParam(required=false) String id) {
		AccountClean entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = accountCleanService.get(id);
		}
		if (entity == null){
			entity = new AccountClean();
		}
		return entity;
	}
	
	@RequiresPermissions("postloan:accountClean:view")
	@RequestMapping(value = {"list", ""})
	public String list(AccountClean accountClean, HttpServletRequest request, HttpServletResponse response, Model model,String readOnly,String flag) {
		Page<AccountClean> page = accountCleanService.findPage(new Page<AccountClean>(request, response), accountClean); 
		model.addAttribute("page", page);
		model.addAttribute("contractNo", accountClean.getContractNo());
		if(Constants.ACCOUNT_CLEAN_OVER.equalsIgnoreCase(request.getParameter("checkedType"))){
			readOnly = "readOnly";
		}
		model.addAttribute("readOnly", readOnly);
		model.addAttribute("flag", flag);
		return "app/postloan/accountClean/accountCleanList";
	}

	@RequiresPermissions("postloan:accountClean:view")
	@RequestMapping(value = "form")
	public String form(AccountClean accountClean, Model model,String flag) {
		model.addAttribute("accountClean", accountClean);
		model.addAttribute("flag", flag);
		return "app/postloan/accountClean/accountCleanForm";
	}

	@ResponseBody
	@RequiresPermissions("postloan:accountClean:edit")
	@RequestMapping(value = "save")
	public AjaxView save(AccountClean accountClean, Model model, RedirectAttributes redirectAttributes,String flag) {
		AjaxView ajaxView = new AjaxView();
		try {
			accountClean.setCleanById(UserUtils.getUser().getId());
			accountClean.setCleanBy(UserUtils.getUser().getName());
			String flags = accountCleanService.saveAccountClean(accountClean);
			if("success".equalsIgnoreCase(flags)){
				ajaxView.setSuccess().setMessage("保存账务清收成功");
			}else{
				ajaxView.setFailed().setMessage("保存账务清收失败");
			}
			ajaxView.setData(flag);
		} catch (Exception e) {
			logger.error("保存账务清收失败", e);
			ajaxView.setFailed().setMessage("保存账务清收失败");
		}
		return ajaxView;
	}
	
	@RequiresPermissions("postloan:accountClean:edit")
	@RequestMapping(value = "delete")
	public String delete(AccountClean accountClean, RedirectAttributes redirectAttributes) {
		accountCleanService.delete(accountClean);
		addMessage(redirectAttributes, "删除账务清收成功");
		return "redirect:"+Global.getAdminPath()+"/AccountClean/accountClean/?repage";
	}
	
	@RequiresPermissions("postloan:accountClean:view")
	@RequestMapping(value = "accountClean")
	public String accountClean(Model model,HttpServletResponse response,HttpServletRequest request,String contractNo,AccountClean accountClean) {
		Page<MyMap> page = null;
		try {
			MyMap paramMap = new MyMap();
			paramMap.put("liquidateName", accountClean.getCleanBy());
			paramMap.put("officeId", UserUtils.getUser().getOffice().getId());
			page = accountCleanService.getAccountCleanInfo(new Page<MyMap>(request, response), paramMap);
		} catch (Exception e) {
			logger.error("查询清收人员错误",e);
		}
		model.addAttribute("page", page);
		model.addAttribute("contractNo", contractNo);
		return "app/postloan/accountClean/liquidate";
	}

}