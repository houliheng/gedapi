package com.resoft.credit.relatedCompanyInfo.web;

import java.util.List;
import java.util.Map;

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

import com.google.common.collect.Maps;
import com.resoft.common.utils.AjaxView;
import com.resoft.credit.relatedCompanyInfo.entity.CompanyRelated;
import com.resoft.credit.relatedCompanyInfo.service.CompanyRelatedService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 企业关联企业信息Controller
 * @author caoyinglong
 * @version 2016-02-29
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/companyRelated")
public class CompanyRelatedController extends BaseController {

	@Autowired
	private CompanyRelatedService companyRelatedService;
	
	@ModelAttribute
	public CompanyRelated get(@RequestParam(required=false) String id) {
		CompanyRelated entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = companyRelatedService.get(id);
		}
		if (entity == null){
			entity = new CompanyRelated();
		}
		return entity;
	}
	
	@RequiresPermissions("credit:custinfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(String companyId, String applyNo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, Object> params = Maps.newHashMap();
		params.put("applyNo", applyNo);
		params.put("companyId", companyId);
		List<CompanyRelated> companyRelatedList = companyRelatedService.findRelatedList(params); 
		model.addAttribute("applyNo", applyNo);
		model.addAttribute("companyRelatedList", companyRelatedList);
		return "app/credit/relatedCompanyInfo/companyRelatedList";
	}

	@RequiresPermissions("credit:custinfo:edit")
	@RequestMapping(value = "form")
	public String form(CompanyRelated companyRelated, String applyNo, Model model) {
		model.addAttribute("companyRelated", companyRelated);
		model.addAttribute("applyNo", applyNo);
		return "app/credit/relatedCompanyInfo/companyRelatedForm";
	}

	@RequiresPermissions("credit:custinfo:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public AjaxView save(CompanyRelated companyRelated, Model model, RedirectAttributes redirectAttributes) {
		AjaxView rtn = new AjaxView();
		try {
			companyRelatedService.save(companyRelated);
			addMessage(model, "保存关联企业成功");
			model.addAttribute("close", "close");
			rtn.setSuccess().setMessage("保存关联企业成功!");
		} catch (Exception e) {
			logger.error("保存关联企业信息失败！", e);
			addMessage(model, "保存关联企业信息失败！");
			rtn.setFailed().setMessage("保存关联企业失败!");
		}
		return rtn;
	}
	
	@ResponseBody
	@RequiresPermissions("credit:custinfo:delete")
	@RequestMapping(value = "delete")
	public AjaxView delete(@RequestParam("ids")List<String> idList) {
		AjaxView rtn = new AjaxView();
		try {
			companyRelatedService.batchDelete(idList);;
			rtn.setSuccess().setMessage("删除关联企业成功!");
		} catch (Exception e) {
			logger.error("删除关联企业失败", e);
			rtn.setFailed().setMessage("删除关联企业失败!");
		}
		return rtn;
	}

}