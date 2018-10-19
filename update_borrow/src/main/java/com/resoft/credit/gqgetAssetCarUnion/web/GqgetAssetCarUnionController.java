package com.resoft.credit.gqgetAssetCarUnion.web;

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
import com.resoft.credit.gqgetAssetCarUnion.entity.GqgetAssetCarUnion;
import com.resoft.credit.gqgetAssetCarUnion.service.GqgetAssetCarUnionService;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 联合授信冠e通车辆资产Controller
 * @author lixing
 * @version 2016-12-26
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/gqgetAssetCarUnion")
public class GqgetAssetCarUnionController extends BaseController {

	@Autowired
	private GqgetAssetCarUnionService gqgetAssetCarUnionService;
	
	@ModelAttribute
	public GqgetAssetCarUnion get(@RequestParam(required=false) String id) {
		GqgetAssetCarUnion entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = gqgetAssetCarUnionService.get(id);
		}
		if (entity == null){
			entity = new GqgetAssetCarUnion();
		}
		return entity;
	}
	
	@RequiresPermissions("credit:gqgetAssetCarUnion:view")
	@RequestMapping(value = {"list", ""})
	public String list(GqgetAssetCarUnion gqgetAssetCarUnion, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GqgetAssetCarUnion> page = gqgetAssetCarUnionService.findPage(new Page<GqgetAssetCarUnion>(request, response), gqgetAssetCarUnion); 
		model.addAttribute("page", page);
		model.addAttribute("gqgetAssetCarUnion", gqgetAssetCarUnion);
		return "app/credit/gqgetAssetCarUnion/gqgetAssetCarUnionList";
	}

	@RequiresPermissions("credit:gqgetAssetCarUnion:view")
	@RequestMapping(value = "form")
	public String form(GqgetAssetCarUnion gqgetAssetCarUnion, Model model) {
		model.addAttribute("gqgetAssetCarUnion", gqgetAssetCarUnion);
		return "app/credit/gqgetAssetCarUnion/gqgetAssetCarUnionForm";
	}

	@RequiresPermissions("credit:gqgetAssetCarUnion:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public AjaxView save(GqgetAssetCarUnion gqgetAssetCarUnion) {
		AjaxView ajaxView = new AjaxView();
		try {
			gqgetAssetCarUnionService.save(gqgetAssetCarUnion);
			ajaxView.setSuccess().setMessage("保存信息成功");
			ajaxView.put("applyNo", gqgetAssetCarUnion.getGqgetApplyNo());
			ajaxView.put("approveId", gqgetAssetCarUnion.getApproveId());
		} catch (Exception e) {
			logger.error("保存失败",e);
			ajaxView.setFailed().setMessage("保存信息失败");
		}
		return ajaxView;
	}
	
	@RequiresPermissions("credit:gqgetAssetCarUnion:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public AjaxView delete(GqgetAssetCarUnion gqgetAssetCarUnion) {
		AjaxView ajaxView = new AjaxView();
		try {
			gqgetAssetCarUnionService.delete(gqgetAssetCarUnion);
			ajaxView.setSuccess().setMessage("删除信息成功");
		} catch (Exception e) {
			logger.error("删除信息失败",e);
			ajaxView.setFailed().setMessage("删除信息失败");
		}
		return ajaxView;
	}

}