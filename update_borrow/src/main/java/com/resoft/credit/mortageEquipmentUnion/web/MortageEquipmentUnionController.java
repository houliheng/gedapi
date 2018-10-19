package com.resoft.credit.mortageEquipmentUnion.web;

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
import com.resoft.credit.mortageEquipmentUnion.entity.MortageEquipmentUnion;
import com.resoft.credit.mortageEquipmentUnion.service.MortageEquipmentUnionService;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 联合授信设备抵押Controller
 * @author wangguodong
 * @version 2016-12-26
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/mortageEquipmentUnion")
public class MortageEquipmentUnionController extends BaseController {

	@Autowired
	private MortageEquipmentUnionService mortageEquipmentUnionService;
	
	@ModelAttribute
	public MortageEquipmentUnion get(@RequestParam(required=false) String id) {
		MortageEquipmentUnion entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = mortageEquipmentUnionService.get(id);
		}
		if (entity == null){
			entity = new MortageEquipmentUnion();
		}
		return entity;
	}
	
	@RequiresPermissions("credit:mortageEquipmentUnion:view")
	@RequestMapping(value = {"list", ""})
	public String list(MortageEquipmentUnion mortageEquipmentUnion, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MortageEquipmentUnion> page = mortageEquipmentUnionService.findCustomPage(new Page<MortageEquipmentUnion>(request, response), mortageEquipmentUnion); 
		model.addAttribute("page", page);
		return "app/credit/mortageEquipmentUnion/mortageEquipmentUnionList";
	}

	@RequiresPermissions("credit:mortageEquipmentUnion:view")
	@RequestMapping(value = "form")
	public String form(MortageEquipmentUnion mortageEquipmentUnion, Model model, HttpServletRequest request) {
		model.addAttribute("mortageEquipmentUnion", mortageEquipmentUnion);
		if ("true".equals(request.getParameter("readOnly"))) {
			model.addAttribute("readOnly", true);
		}
		return "app/credit/mortageEquipmentUnion/mortageEquipmentUnionForm";
	}
	
	@ResponseBody
	@RequiresPermissions("credit:mortageEquipmentUnion:edit")
	@RequestMapping(value = "save")
	public AjaxView save(MortageEquipmentUnion mortageEquipmentUnion, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		AjaxView rtn = new AjaxView();
		try {
			mortageEquipmentUnionService.save(mortageEquipmentUnion);
			rtn.setSuccess().setMessage("保存设备抵质押物成功!");
		} catch (Exception e) {
			logger.error("保存设备抵质押物失败", e);
			rtn.setFailed().setMessage("保存设备抵质押物失败!");
		}
		return rtn;
	}
	
	@ResponseBody
	@RequiresPermissions("credit:mortageEquipmentUnion:edit")
	@RequestMapping(value = "delete")
	public AjaxView delete(MortageEquipmentUnion mortageEquipmentUnion, RedirectAttributes redirectAttributes) {
		AjaxView rtn = new AjaxView();
		try {
			mortageEquipmentUnionService.delete(mortageEquipmentUnion);
			rtn.setSuccess().setMessage("删除设备抵质押物成功!");
		} catch (Exception e) {
			logger.error("删除设备抵质押物失败", e);
			rtn.setFailed().setMessage("删除设备抵质押物失败!");
		}
		return rtn;
	}


}