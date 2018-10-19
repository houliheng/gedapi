package com.resoft.credit.companyManagerInfo.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.resoft.common.utils.Constants;
import com.resoft.credit.companyInfo.entity.CompanyInfo;
import com.resoft.credit.companyInfo.service.CompanyInfoService;
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
@RequestMapping(value = "${adminPath}/credit/guarantorCompanyManagerInfo")
public class GuarCompanyManagerInfoController extends BaseController {

	@Autowired
	private CompanyManagerInfoService companyManagerInfoService;
	@Autowired
	private CompanyInfoService companyInfoService;
	
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
	
	@RequiresPermissions("credit:guarantorInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(CompanyManagerInfo companyManagerInfo, HttpServletRequest request, Model model) {
		Map<String, Object> params = Maps.newHashMap();
		params.put("applyNo", request.getParameter("applyNo"));
		params.put("roleType", Constants.ROLE_TYPE_DBQY);
		List<CompanyManagerInfo> companyManagerInfoList = companyManagerInfoService.findListByParams(params);
		model.addAttribute("companyManagerInfoList", companyManagerInfoList);
		model.addAttribute("applyNo", request.getParameter("applyNo"));
		return "app/credit/guarantorInfo/guarantorCompanyManagerInfoList";
	}

	@RequiresPermissions("credit:guarantorInfo:view")
	@RequestMapping(value = "form")
	public String form(CompanyManagerInfo companyManagerInfo, Model model,String applyNo) {
		Map<String, Object> params = Maps.newHashMap();
		params.put("applyNo", applyNo);
		params.put("roleType", Constants.ROLE_TYPE_DBQY);
		//根据applyNo获取担保企业下拉列表
		List<CompanyInfo> companyInfoList = companyInfoService.findListByParams(params);
		model.addAttribute("companyInfoList", companyInfoList);
		model.addAttribute("companyManagerInfo", companyManagerInfo);
		model.addAttribute("applyNo", applyNo);
		return "app/credit/guarantorInfo/guarantorCompanyManagerInfoForm";
	}

	@RequiresPermissions("credit:guarantorInfo:edit")
	@RequestMapping(value = "save")
	public String save(CompanyManagerInfo companyManagerInfo, Model model, String applyNo) {
		try {
			if (!beanValidator(model, companyManagerInfo)){
				return form(companyManagerInfo, model,applyNo);
			}
			companyManagerInfoService.save(companyManagerInfo);
			addMessage(model, "保存企业高管成功成功!");
			model.addAttribute("close", "close");
			model.addAttribute("applyNo", applyNo);
		} catch (Exception e) {
			logger.error("保存企业高管信息失败！", e);
			addMessage(model, "保存企业高管信息失败！");
		}
		return form(companyManagerInfo,model,applyNo);
	}
	
	@ResponseBody
	@RequiresPermissions("credit:guarantorInfo:delete")
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