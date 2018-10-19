package com.resoft.credit.stockOpinion.web;

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
import com.resoft.credit.stockOpinion.entity.CreStockOpinion;
import com.resoft.credit.stockOpinion.service.CreStockOpinionService;

/**
 * 公司尽调审批意见Controller
 * @author jml
 * @version 2017-12-04
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/creStockOpinion")
public class CreStockOpinionController extends BaseController {

	@Autowired
	private CreStockOpinionService creStockOpinionService;
	
	@ModelAttribute
	public CreStockOpinion get(@RequestParam(required=false) String id) {
		CreStockOpinion entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = creStockOpinionService.get(id);
		}
		if (entity == null){
			entity = new CreStockOpinion();
		}
		return entity;
	}
	
	@RequiresPermissions("credit:creStockOpinion:view")
	@RequestMapping(value = {"list", ""})
	public String list(CreStockOpinion creStockOpinion, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CreStockOpinion> page = creStockOpinionService.findCustomPage(new Page<CreStockOpinion>(request, response), creStockOpinion); 
		model.addAttribute("page", page);
		return "app/credit/stockOpinion/creStockOpinionList";
	}

	@RequiresPermissions("credit:creStockOpinion:view")
	@RequestMapping(value = "form")
	public String form(CreStockOpinion creStockOpinion, Model model) {
		model.addAttribute("creStockOpinion", creStockOpinion);
		return "app/credit/stockOpinion/creStockOpinionForm";
	}

	@RequiresPermissions("credit:creStockOpinion:edit")
	@RequestMapping(value = "save")
	public String save(CreStockOpinion creStockOpinion, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, creStockOpinion)){
			return form(creStockOpinion, model);
		}
		creStockOpinionService.save(creStockOpinion);
		addMessage(redirectAttributes, "保存公司尽调审批意见成功");
		return "redirect:"+Global.getAdminPath()+"/stockOpinion/creStockOpinion/?repage";
	}
	
	@RequiresPermissions("credit:creStockOpinion:edit")
	@RequestMapping(value = "delete")
	public String delete(CreStockOpinion creStockOpinion, RedirectAttributes redirectAttributes) {
		creStockOpinionService.delete(creStockOpinion);
		addMessage(redirectAttributes, "删除公司尽调审批意见成功");
		return "redirect:"+Global.getAdminPath()+"/stockOpinion/creStockOpinion/?repage";
	}

}