package com.resoft.credit.borrowInfoDisclose.web;

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
import com.resoft.credit.borrowInfoDisclose.entity.CreBorrowInfoDisclose;
import com.resoft.credit.borrowInfoDisclose.service.CreBorrowInfoDiscloseService;

/**
 * 借后信息披露Controller
 * @author zcl
 * @version 2018-03-09
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/creBorrowInfoDisclose")
public class CreBorrowInfoDiscloseController extends BaseController {

	@Autowired
	private CreBorrowInfoDiscloseService creBorrowInfoDiscloseService;
	
	@ModelAttribute
	public CreBorrowInfoDisclose get(@RequestParam(required=false) String id) {
		CreBorrowInfoDisclose entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = creBorrowInfoDiscloseService.get(id);
		}
		if (entity == null){
			entity = new CreBorrowInfoDisclose();
		}
		return entity;
	}
	
	@RequiresPermissions("credit:creBorrowInfoDisclose:view")
	@RequestMapping(value = {"list", ""})
	public String list(CreBorrowInfoDisclose creBorrowInfoDisclose, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CreBorrowInfoDisclose> page = creBorrowInfoDiscloseService.findCustomPage(new Page<CreBorrowInfoDisclose>(request, response), creBorrowInfoDisclose); 
		model.addAttribute("page", page);
		return "app/credit/borrowInfoDisclose/creBorrowInfoDiscloseList";
	}

	@RequiresPermissions("credit:creBorrowInfoDisclose:view")
	@RequestMapping(value = "form")
	public String form(CreBorrowInfoDisclose creBorrowInfoDisclose, Model model) {
		model.addAttribute("creBorrowInfoDisclose", creBorrowInfoDisclose);
		return "app/credit/borrowInfoDisclose/creBorrowInfoDiscloseForm";
	}

	@RequiresPermissions("credit:creBorrowInfoDisclose:edit")
	@RequestMapping(value = "save")
	public String save(CreBorrowInfoDisclose creBorrowInfoDisclose, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, creBorrowInfoDisclose)){
			return form(creBorrowInfoDisclose, model);
		}
		creBorrowInfoDiscloseService.save(creBorrowInfoDisclose);
		addMessage(redirectAttributes, "保存借后信息披露成功");
		return "redirect:"+Global.getAdminPath()+"/borrowInfoDisclose/creBorrowInfoDisclose/?repage";
	}
	
	@RequiresPermissions("credit:creBorrowInfoDisclose:edit")
	@RequestMapping(value = "delete")
	public String delete(CreBorrowInfoDisclose creBorrowInfoDisclose, RedirectAttributes redirectAttributes) {
		creBorrowInfoDiscloseService.delete(creBorrowInfoDisclose);
		addMessage(redirectAttributes, "删除借后信息披露成功");
		return "redirect:"+Global.getAdminPath()+"/borrowInfoDisclose/creBorrowInfoDisclose/?repage";
	}

}