package com.resoft.credit.gqgetAssetHouseUnion.web;

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

import com.resoft.common.utils.AjaxView;
import com.resoft.credit.gqgetAssetHouseUnion.entity.GqgetAssetHouseUnion;
import com.resoft.credit.gqgetAssetHouseUnion.service.GqgetAssetHouseUnionService;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 联合授信冠e通房产资产Controller
 * @author lixing
 * @version 2016-12-26
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/gqgetAssetHouseUnion")
public class GqgetAssetHouseUnionController extends BaseController {

	@Autowired
	private GqgetAssetHouseUnionService gqgetAssetHouseUnionService;
	
	@ModelAttribute
	public GqgetAssetHouseUnion get(@RequestParam(required=false) String id) {
		GqgetAssetHouseUnion entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = gqgetAssetHouseUnionService.get(id);
		}
		if (entity == null){
			entity = new GqgetAssetHouseUnion();
		}
		return entity;
	}
	
	@RequiresPermissions("credit:gqgetAssetHouseUnion:view")
	@RequestMapping(value = {"list", ""})
	public String list(GqgetAssetHouseUnion gqgetAssetHouseUnion, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GqgetAssetHouseUnion> page = gqgetAssetHouseUnionService.findPage(new Page<GqgetAssetHouseUnion>(request, response), gqgetAssetHouseUnion); 
		model.addAttribute("page", page);
		model.addAttribute("gqgetAssetHouseUnion", gqgetAssetHouseUnion);
		return "app/credit/gqgetAssetHouseUnion/gqgetAssetHouseUnionList";
	}

	@RequiresPermissions("credit:gqgetAssetHouseUnion:view")
	@RequestMapping(value = "form")
	public String form(GqgetAssetHouseUnion gqgetAssetHouseUnion, Model model) {
		model.addAttribute("gqgetAssetHouseUnion", gqgetAssetHouseUnion);
		return "app/credit/gqgetAssetHouseUnion/gqgetAssetHouseUnionForm";
	}

	@RequiresPermissions("credit:gqgetAssetHouseUnion:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public AjaxView save(GqgetAssetHouseUnion gqgetAssetHouseUnion) {
		AjaxView ajaxView = new AjaxView();
		try {
			gqgetAssetHouseUnionService.save(gqgetAssetHouseUnion);
			ajaxView.setSuccess().setMessage("保存信息成功");
			ajaxView.put("applyNo", gqgetAssetHouseUnion.getApplyNo());
			ajaxView.put("approveId", gqgetAssetHouseUnion.getApproveId());
		} catch (Exception e) {
			logger.error("保存信息失败",e);
			ajaxView.setFailed().setMessage("保存信息失败");
		}
		return ajaxView;
	}
	
	@RequiresPermissions("credit:gqgetAssetHouseUnion:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public AjaxView delete(GqgetAssetHouseUnion gqgetAssetHouseUnion) {
		AjaxView ajaxView = new AjaxView();
		try {
			gqgetAssetHouseUnionService.delete(gqgetAssetHouseUnion);
			ajaxView.setSuccess().setMessage("删除信息成功");
		} catch (Exception e) {
			logger.error("删除信息失败",e);
			ajaxView.setFailed().setMessage("删除信息失败");
		}
		return ajaxView;
	}

}