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

import com.google.common.collect.Maps;
import com.resoft.common.utils.AjaxView;
import com.resoft.common.utils.Constants;
import com.resoft.credit.companyInfo.entity.CompanyInfo;
import com.resoft.credit.companyInfo.service.CompanyInfoService;
import com.resoft.credit.relatedCompanyInfo.entity.CompanyRelated;
import com.resoft.credit.relatedCompanyInfo.service.CompanyRelatedService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 担保企业关联企业信息Controller
 * @author caoyinglong
 * @version 2016-02-29
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/guarantorCompanyRelated")
public class GuarantorCompanyRelatedController extends BaseController {

	@Autowired
	private CompanyRelatedService companyRelatedService;
	@Autowired
	private CompanyInfoService companyInfoService;
	
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
	
	@RequiresPermissions("credit:guarantorInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(CompanyRelated companyRelated, HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, Object> params = Maps.newHashMap();
		params.put("applyNo", request.getParameter("applyNo"));
		params.put("roleType", Constants.ROLE_TYPE_DBQY);
		//根据申请编号查询担保企业关联企业
		List<CompanyRelated> companyRelatedList = companyRelatedService.findListByParams(params);
		model.addAttribute("applyNo", request.getParameter("applyNo"));
		model.addAttribute("companyRelatedList", companyRelatedList);
		return "app/credit/relatedCompanyInfo/guarantorCompanyRelatedList";
	}

	@RequiresPermissions("credit:guarantorInfo:view")
	@RequestMapping(value = "form")
	public String form(CompanyRelated companyRelated, Model model, String applyNo) {
		Map<String, Object> params = Maps.newHashMap();
		params.put("applyNo", applyNo);
		params.put("roleType", Constants.ROLE_TYPE_DBQY);
		//根据applyNo获取担保企业下拉列表
		List<CompanyInfo> companyInfoList = companyInfoService.findListByParams(params);
		model.addAttribute("companyInfoList", companyInfoList);
		model.addAttribute("companyRelated", companyRelated);
		model.addAttribute("applyNo", applyNo);
		return "app/credit/relatedCompanyInfo/guarantorCompanyRelatedForm";
	}

	@RequiresPermissions("credit:guarantorInfo:edit")
	@RequestMapping(value = "save")
	public String save(CompanyRelated companyRelated, Model model, String applyNo) {
		try {
			if (!beanValidator(model, companyRelated)){
				return form(companyRelated, model, applyNo);
			}
			companyRelatedService.save(companyRelated);
			addMessage(model, "保存关联企业成功");
			model.addAttribute("close", "close");
		} catch (Exception e) {
			logger.error("保存关联企业信息失败！", e);
			addMessage(model, "保存关联企业信息失败！");
		}
		return form(companyRelated,model, applyNo);
	}
	
	@ResponseBody
	@RequiresPermissions("credit:guarantorInfo:delete")
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