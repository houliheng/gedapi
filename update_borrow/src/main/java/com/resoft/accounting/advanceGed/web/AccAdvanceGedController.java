package com.resoft.accounting.advanceGed.web;

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

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.resoft.accounting.advanceGed.entity.AccAdvanceGed;
import com.resoft.accounting.advanceGed.service.AccAdvanceGedService;

/**
 * 冠E贷提前还款Controller
 * @author gsh
 * @version 2018-06-24
 */
@Controller
@RequestMapping(value = "${adminPath}/accounting/accAdvanceGed")
public class AccAdvanceGedController extends BaseController {

	@Autowired
	private AccAdvanceGedService accAdvanceGedService;
	
	@ModelAttribute
	public AccAdvanceGed get(@RequestParam(required=false) String id) {
		AccAdvanceGed entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = accAdvanceGedService.get(id);
		}
		if (entity == null){
			entity = new AccAdvanceGed();
		}
		return entity;
	}
	
	@RequiresPermissions("accounting:accAdvanceGed:view")
	@RequestMapping(value = {"list", ""})
	public String list(AccAdvanceGed accAdvanceGed, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AccAdvanceGed> page = accAdvanceGedService.findCustomPage(new Page<AccAdvanceGed>(request, response), accAdvanceGed); 
		model.addAttribute("page", page);
		return "app/accounting/advanceGed/accAdvanceGedList";
	}

	@RequiresPermissions("accounting:accAdvanceGed:view")
	@RequestMapping(value = "form")
	public String form(AccAdvanceGed accAdvanceGed, Model model) {
		model.addAttribute("accAdvanceGed", accAdvanceGed);
		return "app/accounting/advanceGed/accAdvanceGedForm";
	}

	@RequiresPermissions("accounting:accAdvanceGed:edit")
	@RequestMapping(value = "save")
	public String save(AccAdvanceGed accAdvanceGed, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, accAdvanceGed)){
			return form(accAdvanceGed, model);
		}
		accAdvanceGedService.save(accAdvanceGed);
		addMessage(redirectAttributes, "保存冠E贷提前还款成功");
		return "redirect:"+Global.getAdminPath()+"/advanceGed/accAdvanceGed/?repage";
	}
	
	@RequiresPermissions("accounting:accAdvanceGed:edit")
	@RequestMapping(value = "delete")
	public String delete(AccAdvanceGed accAdvanceGed, RedirectAttributes redirectAttributes) {
		accAdvanceGedService.delete(accAdvanceGed);
		addMessage(redirectAttributes, "删除冠E贷提前还款成功");
		return "redirect:"+Global.getAdminPath()+"/advanceGed/accAdvanceGed/?repage";
	}

}