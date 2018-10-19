package com.resoft.credit.mortgagedCompanyUnion.web;

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
import com.resoft.credit.mortgagedCompanyUnion.entity.MortgagedCompanyUnion;
import com.resoft.credit.mortgagedCompanyUnion.service.MortgagedCompanyUnionService;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 联合授信冠e通担保企业Controller
 * @author wangguodong
 * @version 2016-12-26
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/mortgagedCompanyUnion")
public class MortgagedCompanyUnionController extends BaseController {

	@Autowired
	private MortgagedCompanyUnionService mortgagedCompanyUnionService;
	
	@ModelAttribute
	public MortgagedCompanyUnion get(@RequestParam(required=false) String id) {
		MortgagedCompanyUnion entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = mortgagedCompanyUnionService.get(id);
		}
		if (entity == null){
			entity = new MortgagedCompanyUnion();
		}
		return entity;
	}
	
	@RequiresPermissions("credit:mortgagedCompanyUnion:view")
	@RequestMapping(value = {"list", ""})
	public String list(MortgagedCompanyUnion mortgagedCompanyUnion, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MortgagedCompanyUnion> page = mortgagedCompanyUnionService.findPage(new Page<MortgagedCompanyUnion>(request, response), mortgagedCompanyUnion); 
		model.addAttribute("page", page);
		model.addAttribute("applyNo", mortgagedCompanyUnion.getApplyNo());
		model.addAttribute("approveId", mortgagedCompanyUnion.getApproveId());
		return "app/credit/mortgagedCompanyUnion/mortgagedCompanyUnionList";
	}

	@RequiresPermissions("credit:mortgagedCompanyUnion:view")
	@RequestMapping(value = "form")
	public String form(MortgagedCompanyUnion mortgagedCompanyUnion,String readOnly, Model model) {
		model.addAttribute("mortgagedCompanyUnion", mortgagedCompanyUnion);
		model.addAttribute("readOnly", readOnly);
		return "app/credit/mortgagedCompanyUnion/mortgagedCompanyUnionForm";
	}

	@ResponseBody
	@RequiresPermissions("credit:mortgagedCompanyUnion:edit")
	@RequestMapping(value = "save")
	public AjaxView save(MortgagedCompanyUnion mortgagedCompanyUnion, Model model, RedirectAttributes redirectAttributes) {
		AjaxView ajaxView = new AjaxView();
		try {
			mortgagedCompanyUnionService.save(mortgagedCompanyUnion);
			ajaxView.setSuccess().setMessage("保存担保企业成功");
			//ajaxView.put("applyNo", mortgagedCompanyUnion.getApplyNo());
		} catch (Exception e) {
			logger.error("保存担保企业失败", e);
			ajaxView.setFailed().setMessage("保存担保企业失败");
		}
		return ajaxView;
	}
	
	@ResponseBody
	@RequiresPermissions("credit:mortgagedCompanyUnion:edit")
	@RequestMapping(value = "delete")
	public AjaxView delete(MortgagedCompanyUnion mortgagedCompanyUnion) {
		AjaxView ajaxView = new AjaxView();
		try {
			mortgagedCompanyUnionService.delete(mortgagedCompanyUnion);
			ajaxView.setSuccess().setMessage("删除担保企业成功");
		} catch (Exception e) {
			logger.error("删除担保企业失败", e);
			ajaxView.setFailed().setMessage("删除担保企业失败");
		}
		return ajaxView;
	}

}