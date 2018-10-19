package com.resoft.credit.creditAndLine.web.creditCompany;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.google.common.collect.Maps;
import com.resoft.common.utils.AjaxView;
import com.resoft.credit.applyRelation.service.ApplyRelationService;
import com.resoft.credit.creditAndLine.entity.creditCompany.CreditCompany;
import com.resoft.credit.creditAndLine.service.creditCompany.CreditCompanyService;

/**
 * 企业征信列表Controller
 * 
 * @author wuxi01
 * @version 2016-02-26
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/creditAndLine/creditCompany")
public class CreditCompanyController extends BaseController {

	@Autowired
	private CreditCompanyService creditCompanyService;
	
	@Autowired
	private ApplyRelationService applyRelationService;

	@ModelAttribute
	public CreditCompany get(@RequestParam(required = false) String id) {
		CreditCompany entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = creditCompanyService.get(id);
		}
		if (entity == null) {
			entity = new CreditCompany();
		}
		return entity;
	}

	@RequiresPermissions("credit:creditAndLine:creditCompany:view")
	@RequestMapping(value = { "list", "" })
	public String list(String applyNo, String readOnly, Model model) {
		CreditCompany creditCompany = new CreditCompany();
		creditCompany.setApplyNo(applyNo);
		List<CreditCompany> creditCompanyList = creditCompanyService.findList(creditCompany);
		model.addAttribute("creditCompanyList", creditCompanyList);
		model.addAttribute("applyNo", applyNo);
		model.addAttribute("readOnly", readOnly);
		return "app/credit/creditAndLine/creditCompany/creditCompanyList";
	}

	@RequiresPermissions("credit:creditAndLine:creditCompany:view")
	@RequestMapping(value = "form")
	public String form(String creditCompanyId, String applyNo, String readOnly, Model model) {
		CreditCompany creditCompany = new CreditCompany();
		// 1.新增
		if (StringUtils.isNotBlank(applyNo)) {
			readOnly = "1";
			creditCompany.setApplyNo(applyNo);
		}
		// 2.修改
		if (StringUtils.isNotBlank(creditCompanyId)) {
			creditCompany = creditCompanyService.get(creditCompanyId);
			try {
				Map<String, String> params = Maps.newConcurrentMap();
				params.put("roleType", creditCompany.getRoleType());
				params.put("applyNo", creditCompany.getApplyNo());
				List<CreditCompany> companyNameMap = applyRelationService.findCustNameByRoleTypeOnCreditCompany(params);
				model.addAttribute("companyNameMap", companyNameMap);
			} catch (Exception e) {
				logger.error("系统数据发生异常，请联系管理员！");
				model.addAttribute("message", "系统数据发生异常，请联系管理员！");
			}
			if (creditCompany == null) {
				
			}
		}
		model.addAttribute("creditCompany", creditCompany);
		return "app/credit/creditAndLine/creditCompany/creditCompanyForm";
	}

	@ResponseBody
	@RequiresPermissions("credit:creditAndLine:creditCompany:edit")
	@RequestMapping(value = "save")
	public AjaxView save(@RequestBody CreditCompany creditCompany) {
		AjaxView ajaxView = new AjaxView();
		try {
			creditCompanyService.save(creditCompany);
			ajaxView.setSuccess().setMessage("保存企业征信信息成功！");
		} catch (Exception e) {
			logger.error("");
			ajaxView.setFailed().setMessage("保存企业征信信息失败！");
		}
		return ajaxView;
	}

	@ResponseBody
	@RequiresPermissions("credit:creditAndLine:creditCompany:edit")
	@RequestMapping(value = "delete")
	public AjaxView delete(String ids) {
		AjaxView ajaxView = new AjaxView();
		try {
			creditCompanyService.banchDelete(ids);
			ajaxView.setSuccess().setMessage("删除企业征信信息成功！");
		} catch (Exception e) {
			logger.error("删除企业征信信息失败！", e);
			ajaxView.setFailed().setMessage("删除企业征信信息失败！");
		}
		return ajaxView;
	}

}