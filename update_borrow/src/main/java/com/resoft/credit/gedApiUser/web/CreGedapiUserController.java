package com.resoft.credit.gedApiUser.web;

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
import com.resoft.credit.gedApiUser.entity.CreGedapiUser;
import com.resoft.credit.gedApiUser.service.CreGedapiUserService;

/**
 * 冠E贷账号Controller
 * @author lb
 * @version 2018-05-23
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/creGedapiUser")
public class CreGedapiUserController extends BaseController {

	@Autowired
	private CreGedapiUserService creGedapiUserService;
	
	@ModelAttribute
	public CreGedapiUser get(@RequestParam(required=false) String id) {
		CreGedapiUser entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = creGedapiUserService.get(id);
		}
		if (entity == null){
			entity = new CreGedapiUser();
		}
		return entity;
	}
	
	@RequiresPermissions("credit:creGedapiUser:view")
	@RequestMapping(value = {"list", ""})
	public String list(CreGedapiUser creGedapiUser, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CreGedapiUser> page = creGedapiUserService.findCustomPage(new Page<CreGedapiUser>(request, response), creGedapiUser); 
		model.addAttribute("page", page);
		return "app/credit/gedApiUser/creGedapiUserList";
	}

	@RequiresPermissions("credit:creGedapiUser:view")
	@RequestMapping(value = "form")
	public String form(CreGedapiUser creGedapiUser, Model model) {
		model.addAttribute("creGedapiUser", creGedapiUser);
		return "app/credit/gedApiUser/creGedapiUserForm";
	}

	@RequiresPermissions("credit:creGedapiUser:edit")
	@RequestMapping(value = "save")
	public String save(CreGedapiUser creGedapiUser, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, creGedapiUser)){
			return form(creGedapiUser, model);
		}
		creGedapiUserService.save(creGedapiUser);
		addMessage(redirectAttributes, "保存冠E贷账号成功");
		return "redirect:"+Global.getAdminPath()+"/gedApiUser/creGedapiUser/?repage";
	}
	
	@RequiresPermissions("credit:creGedapiUser:edit")
	@RequestMapping(value = "delete")
	public String delete(CreGedapiUser creGedapiUser, RedirectAttributes redirectAttributes) {
		creGedapiUserService.delete(creGedapiUser);
		addMessage(redirectAttributes, "删除冠E贷账号成功");
		return "redirect:"+Global.getAdminPath()+"/gedApiUser/creGedapiUser/?repage";
	}

}