package com.resoft.credit.companyManagerInfo.web;

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

import com.google.common.collect.Maps;
import com.resoft.common.utils.AjaxView;
import com.resoft.credit.companyManagerInfo.entity.CompanyManagerInfo;
import com.resoft.credit.companyManagerInfo.service.CompanyManagerInfoService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 企业高管信息Controller
 * @author caoyinglong
 * @version 2016-02-27
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/companyManagerInfo")
public class CompanyManagerInfoController extends BaseController {

	@Autowired
	private CompanyManagerInfoService companyManagerInfoService;
	
	@ModelAttribute
	public CompanyManagerInfo get(@RequestParam(required=false) String id) {
		CompanyManagerInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = companyManagerInfoService.get(id);
		}
		if (entity == null){
			entity = new CompanyManagerInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("credit:custinfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(String companyId, String applyNo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, Object> params = Maps.newHashMap();
		params.put("companyId", companyId);
		params.put("applyNo", applyNo);
		List<CompanyManagerInfo> companyManagerInfoList = companyManagerInfoService.findManagerList(params);
		model.addAttribute("companyManagerInfoList", companyManagerInfoList);
		model.addAttribute("applyNo", applyNo);
		return "app/credit/companyManagerInfo/companyManagerInfoList";
	}

	@RequiresPermissions("credit:custinfo:edit")
	@RequestMapping(value = "form")
	public String form(CompanyManagerInfo companyManagerInfo,String applyNo, Model model) {
		model.addAttribute("companyManagerInfo", companyManagerInfo);
		model.addAttribute("applyNo", applyNo);
		return "app/credit/companyManagerInfo/companyManagerInfoForm";
	}

	@RequiresPermissions("credit:custinfo:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public AjaxView save(CompanyManagerInfo companyManagerInfo, Model model) {
		AjaxView rtn = new AjaxView();
		try {
			companyManagerInfoService.save(companyManagerInfo);
			addMessage(model, "保存企业高管信息成功!");
			rtn.setSuccess().setMessage("保存企业高管信息成功!");
			model.addAttribute("close", "close");
		} catch (Exception e) {
			logger.error("保存企业高管信息失败！", e);
			addMessage(model, "保存企业高管信息失败！");
			rtn.setFailed().setMessage("保存企业高管信息失败！");
		}
		return rtn;
	}
	
	@ResponseBody
	@RequiresPermissions("credit:custinfo:edit")
	@RequestMapping(value = "delete")
	public AjaxView delete(@RequestParam("ids")List<String> idList) {
		AjaxView rtn = new AjaxView();
		try {
			companyManagerInfoService.batchDelete(idList);;
			rtn.setSuccess().setMessage("删除企业高管成功!");
		} catch (Exception e) {
			logger.error("删除企业高管失败", e);
			rtn.setFailed().setMessage("删除企业高管失败!");
		}
		return rtn;
	}

}