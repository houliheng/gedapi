package com.resoft.credit.gqgetGuarantorCompany.web;

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
import com.resoft.credit.gqgetGuarantorCompany.entity.GqgetGuarantorCompany;
import com.resoft.credit.gqgetGuarantorCompany.service.GqgetGuarantorCompanyService;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 冠e通担保企业Controller
 * 
 * @author wangguodong
 * @version 2016-09-29
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/gqgetGuarantorCompany")
public class GqgetGuarantorCompanyController extends BaseController {

	@Autowired
	private GqgetGuarantorCompanyService gqgetGuarantorCompanyService;

	@ModelAttribute
	public GqgetGuarantorCompany get(@RequestParam(required = false) String id) {
		GqgetGuarantorCompany entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = gqgetGuarantorCompanyService.get(id);
		}
		if (entity == null) {
			entity = new GqgetGuarantorCompany();
		}
		return entity;
	}

	@RequiresPermissions("credit:gqgetComInfo:view")
	@RequestMapping(value = { "list", "" })
	public String list(GqgetGuarantorCompany gqgetGuarantorCompany, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GqgetGuarantorCompany> page = gqgetGuarantorCompanyService.findCustomPage(new Page<GqgetGuarantorCompany>(request, response), gqgetGuarantorCompany);
		model.addAttribute("page", page);
		model.addAttribute("applyNo", gqgetGuarantorCompany.getApplyNo());
		return "app/credit/gqgetGuarantorCompany/gqgetGuarantorCompanyList";
	}

	@RequiresPermissions("credit:gqgetComInfo:view")
	@RequestMapping(value = "form")
	public String form(GqgetGuarantorCompany gqgetGuarantorCompany, String readOnly, Model model) {
		model.addAttribute("gqgetGuarantorCompany", gqgetGuarantorCompany);
		model.addAttribute("readOnly", readOnly);
		return "app/credit/gqgetGuarantorCompany/gqgetGuarantorCompanyForm";
	}

	@RequiresPermissions("credit:gqgetComInfo:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public AjaxView save(GqgetGuarantorCompany gqgetGuarantorCompany) {
		AjaxView ajaxView = new AjaxView();
		try {
			gqgetGuarantorCompanyService.save(gqgetGuarantorCompany);
			ajaxView.setSuccess().setMessage("保存担保企业成功");
			ajaxView.put("applyNo", gqgetGuarantorCompany.getApplyNo());
		} catch (Exception e) {
			logger.error("保存担保企业失败", e);
			ajaxView.setFailed().setMessage("保存担保企业失败");
		}
		return ajaxView;
	}

	@RequiresPermissions("credit:gqgetComInfo:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public AjaxView delete(GqgetGuarantorCompany gqgetGuarantorCompany) {
		AjaxView ajaxView = new AjaxView();
		try {
			gqgetGuarantorCompanyService.delete(gqgetGuarantorCompany);
			ajaxView.setSuccess().setMessage("删除担保企业成功");
		} catch (Exception e) {
			logger.error("删除担保企业失败", e);
			ajaxView.setFailed().setMessage("删除担保企业失败");
		}
		return ajaxView;
	}

}