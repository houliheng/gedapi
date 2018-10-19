package com.resoft.credit.gqgetAssetCar.web;

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
import com.resoft.credit.gqgetAssetCar.entity.GqgetAssetCar;
import com.resoft.credit.gqgetAssetCar.service.GqgetAssetCarService;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 冠e通资产车辆信息Controller
 * @author wangguodong
 * @version 2016-10-13
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/gqgetAssetCar")
public class GqgetAssetCarController extends BaseController {

	@Autowired
	private GqgetAssetCarService gqgetAssetCarService;
	
	@ModelAttribute
	public GqgetAssetCar get(@RequestParam(required=false) String id) {
		GqgetAssetCar entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = gqgetAssetCarService.get(id);
		}
		if (entity == null){
			entity = new GqgetAssetCar();
		}
		return entity;
	}
	
	@RequiresPermissions("credit:gqgetComInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(GqgetAssetCar gqgetAssetCar, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GqgetAssetCar> page = gqgetAssetCarService.findCustomPage(new Page<GqgetAssetCar>(request, response), gqgetAssetCar); 
		model.addAttribute("page", page);
		return "app/credit/gqgetAssetCar/gqgetAssetCarList";
	}

	@RequiresPermissions("credit:gqgetComInfo:view")
	@RequestMapping(value = "form")
	public String form(GqgetAssetCar gqgetAssetCar, Model model,String readOnly) {
		model.addAttribute("gqgetAssetCar", gqgetAssetCar);
		model.addAttribute("readOnly", readOnly);
		return "app/credit/gqgetAssetCar/gqgetAssetCarForm";
	}

	@RequiresPermissions("credit:gqgetComInfo:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public AjaxView save(GqgetAssetCar gqgetAssetCar) {
		AjaxView ajaxView = new AjaxView();
		try {
			gqgetAssetCarService.save(gqgetAssetCar);
			ajaxView.put("gqgetApplyNo", gqgetAssetCar.getGqgetApplyNo());
			ajaxView.setSuccess().setMessage("保存冠e通资产车辆信息成功");
		} catch (Exception e) {
			logger.error("保存冠e通资产车辆信息失败",e);
			ajaxView.setFailed().setMessage("保存冠e通资产车辆信息失败");
		}
		return ajaxView;
	}
	
	@RequiresPermissions("credit:gqgetComInfo:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public AjaxView delete(GqgetAssetCar gqgetAssetCar) {
		AjaxView ajaxView = new AjaxView();
		try {
			gqgetAssetCarService.delete(gqgetAssetCar);
			ajaxView.setSuccess().setMessage("删除冠e通资产车辆信息成功");
		} catch (Exception e) {
			logger.error("删除冠e通资产车辆信息失败",e);
			ajaxView.setFailed().setMessage("删除冠e通资产车辆信息失败");
		}
		return ajaxView;
	}

}