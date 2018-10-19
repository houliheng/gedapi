package com.resoft.credit.CreGedBorrowStatus.web;

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
import com.resoft.credit.CreGedBorrowStatus.entity.CreGedBorrowStatus;
import com.resoft.credit.CreGedBorrowStatus.service.CreGedBorrowStatusService;

/**
 * 冠E贷同步状态Controller
 * @author zcl
 * @version 2018-03-13
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/creGedBorrowStatus")
public class CreGedBorrowStatusController extends BaseController {

	@Autowired
	private CreGedBorrowStatusService creGedBorrowStatusService;
	
	@ModelAttribute
	public CreGedBorrowStatus get(@RequestParam(required=false) String id) {
		CreGedBorrowStatus entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = creGedBorrowStatusService.get(id);
		}
		if (entity == null){
			entity = new CreGedBorrowStatus();
		}
		return entity;
	}
	
	@RequiresPermissions("credit:creGedBorrowStatus:view")
	@RequestMapping(value = {"list", ""})
	public String list(CreGedBorrowStatus creGedBorrowStatus, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CreGedBorrowStatus> page = creGedBorrowStatusService.findCustomPage(new Page<CreGedBorrowStatus>(request, response), creGedBorrowStatus); 
		model.addAttribute("page", page);
		return "app/credit/CreGedBorrowStatus/creGedBorrowStatusList";
	}

	@RequiresPermissions("credit:creGedBorrowStatus:view")
	@RequestMapping(value = "form")
	public String form(CreGedBorrowStatus creGedBorrowStatus, Model model) {
		model.addAttribute("creGedBorrowStatus", creGedBorrowStatus);
		return "app/credit/CreGedBorrowStatus/creGedBorrowStatusForm";
	}

	@RequiresPermissions("credit:creGedBorrowStatus:edit")
	@RequestMapping(value = "save")
	public String save(CreGedBorrowStatus creGedBorrowStatus, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, creGedBorrowStatus)){
			return form(creGedBorrowStatus, model);
		}
		creGedBorrowStatusService.save(creGedBorrowStatus);
		addMessage(redirectAttributes, "保存冠E贷同步状态成功");
		return "redirect:"+Global.getAdminPath()+"/CreGedBorrowStatus/creGedBorrowStatus/?repage";
	}
	
	@RequiresPermissions("credit:creGedBorrowStatus:edit")
	@RequestMapping(value = "delete")
	public String delete(CreGedBorrowStatus creGedBorrowStatus, RedirectAttributes redirectAttributes) {
		creGedBorrowStatusService.delete(creGedBorrowStatus);
		addMessage(redirectAttributes, "删除冠E贷同步状态成功");
		return "redirect:"+Global.getAdminPath()+"/CreGedBorrowStatus/creGedBorrowStatus/?repage";
	}

}