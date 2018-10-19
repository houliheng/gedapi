package com.resoft.credit.generalAudit.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.resoft.credit.generalAudit.entity.GeneralManagerAudit;
import com.resoft.credit.generalAudit.service.GeneralManagerAuditService;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 总公司总经理审核条件配置Controller
 * @author chenshaojia
 * @version 2016-04-08
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/generalManagerAudit")
public class GeneralManagerAuditController extends BaseController {

	@Autowired
	private GeneralManagerAuditService generalManagerAuditService;
	
	@ModelAttribute
	public GeneralManagerAudit get(@RequestParam(required=false) String id) {
		GeneralManagerAudit entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = generalManagerAuditService.get(id);
		}
		if (entity == null){
			entity = new GeneralManagerAudit();
		}
		return entity;
	}
	
	@RequiresPermissions("credit:generalManagerAudit:view")
	@RequestMapping(value = {"list", ""})
	public String list(GeneralManagerAudit generalManagerAudit, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GeneralManagerAudit> page = generalManagerAuditService.findPage(new Page<GeneralManagerAudit>(request, response), generalManagerAudit); 
		model.addAttribute("page", page);
		return "app/credit/generalAudit/generalManagerAuditList";
	}

	@RequiresPermissions("credit:generalManagerAudit:view")
	@RequestMapping(value = "form")
	public String form(GeneralManagerAudit generalManagerAudit, Model model) {
		model.addAttribute("generalManagerAudit", generalManagerAudit);
		return "app/credit/generalAudit/generalManagerAuditForm";
	}

	@RequiresPermissions("credit:generalManagerAudit:edit")
	@RequestMapping(value = "save")
	public String save(GeneralManagerAudit generalManagerAudit, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, generalManagerAudit)){
			return form(generalManagerAudit, model);
		}
		User user = UserUtils.get(generalManagerAudit.getUser().getId());
		generalManagerAudit.setOffice(user.getOffice());
		generalManagerAuditService.save(generalManagerAudit);
		addMessage(redirectAttributes, "保存条件成功");
		return "redirect:"+Global.getAdminPath()+"/credit/generalManagerAudit/list?repage";
	}
	
	@RequiresPermissions("credit:generalManagerAudit:edit")
	@RequestMapping(value = "delete")
	public String delete(GeneralManagerAudit generalManagerAudit, RedirectAttributes redirectAttributes) {
		generalManagerAuditService.delete(generalManagerAudit);
		addMessage(redirectAttributes, "删除条件成功");
		return "redirect:"+Global.getAdminPath()+"/credit/generalManagerAudit/list?repage";
	}

}