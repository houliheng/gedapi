package com.resoft.credit.gqgetAssertHouse.web;

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

import com.resoft.common.utils.AjaxView;
import com.resoft.credit.gqgetAssertHouse.entity.GqgetAssetHouse;
import com.resoft.credit.gqgetAssertHouse.service.GqgetAssetHouseService;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 冠E通房屋资产信息表Controller
 * @author wanghong
 * @version 2016-10-13
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/gqgetAssetHouse")
public class GqgetAssetHouseController extends BaseController {

	@Autowired
	private GqgetAssetHouseService gqgetAssetHouseService;
	
	@ModelAttribute
	public GqgetAssetHouse get(@RequestParam(required=false) String id) {
		GqgetAssetHouse entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = gqgetAssetHouseService.get(id);
		}
		if (entity == null){
			entity = new GqgetAssetHouse();
		}
		return entity;
	}
	
	@RequiresPermissions("credit:gqgetComInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(GqgetAssetHouse gqgetAssetHouse, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GqgetAssetHouse> page = gqgetAssetHouseService.findCustomPage(new Page<GqgetAssetHouse>(request, response), gqgetAssetHouse); 
		model.addAttribute("page", page);
		model.addAttribute("applyNo", gqgetAssetHouse.getApplyNo());
		return "app/credit/gqgetAssertHouse/gqgetAssetHouseList";
	}

	@RequiresPermissions("credit:gqgetComInfo:view")
	@RequestMapping(value = "form")
	public String form(GqgetAssetHouse gqgetAssetHouse, Model model) {
		model.addAttribute("gqgetAssetHouse", gqgetAssetHouse);
		return "app/credit/gqgetAssertHouse/gqgetAssetHouseForm";
	}

	@RequiresPermissions("credit:gqgetComInfo:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public AjaxView save(GqgetAssetHouse gqgetAssetHouse, Model model, RedirectAttributes redirectAttributes) {
		AjaxView ajaxView = new AjaxView();
		try {
			gqgetAssetHouseService.save(gqgetAssetHouse);
			ajaxView.put("applyNo", gqgetAssetHouse.getApplyNo());
			ajaxView.setSuccess().setMessage("保存房屋资产信息成功");
		} catch (Exception e) {
			logger.error("保存房屋资产信息失败", e);
			ajaxView.setFailed().setMessage("保存房屋资产信息失败");
		}
		return ajaxView;
	}
	
	@RequiresPermissions("credit:gqgetComInfo:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public AjaxView delete(GqgetAssetHouse gqgetAssetHouse, RedirectAttributes redirectAttributes) {
		AjaxView ajaxView = new AjaxView();
		try {
			gqgetAssetHouseService.delete(gqgetAssetHouse);
			ajaxView.setSuccess().setMessage("删除房屋资产信息成功");
		} catch (Exception e) {
			logger.error("删除房屋资产信息失败", e);
			ajaxView.setFailed().setMessage("删除房屋资产信息失败");
		}
		
		return ajaxView;
	}

}