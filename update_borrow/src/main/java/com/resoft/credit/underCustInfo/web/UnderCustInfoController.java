package com.resoft.credit.underCustInfo.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.resoft.common.utils.AjaxView;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.underCompanyInfo.entity.UnderCompanyInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.resoft.credit.underCustInfo.entity.UnderCustInfo;
import com.resoft.credit.underCustInfo.service.UnderCustInfoService;

/**
 * 线下借款-借款人基本信息Controller
 * @author jml
 * @version 2018-06-26
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/underCustInfo")
public class UnderCustInfoController extends BaseController {

	@Autowired
	private UnderCustInfoService underCustInfoService;


	@ModelAttribute
	public UnderCustInfo get(@RequestParam(required=false) String id) {
		UnderCustInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = underCustInfoService.get(id);
		}
		if (entity == null){
			entity = new UnderCustInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("credit:underCustInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(UnderCustInfo underCustInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UnderCustInfo> page = underCustInfoService.findCustomPage(new Page<UnderCustInfo>(request, response), underCustInfo); 
		model.addAttribute("page", page);
		return "app/credit/underCustInfo/underCustInfoList";
	}

	@RequiresPermissions("credit:underCustInfo:view")
	@RequestMapping(value = "form")
	public String form(UnderCustInfo underCustInfo, Model model) {
		model.addAttribute("underCustInfo", underCustInfo);
		return "app/credit/underCustInfo/underCustInfoForm";
	}

	@RequiresPermissions("credit:underCustInfo:edit")
	@RequestMapping(value = "save")
	public String save(UnderCustInfo underCustInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, underCustInfo)){
			return form(underCustInfo, model);
		}
		underCustInfoService.save(underCustInfo);
		addMessage(redirectAttributes, "保存线下借款-借款人基本信息成功");
		return "redirect:"+Global.getAdminPath()+"/underCustInfo/underCustInfo/?repage";
	}
	
	@RequiresPermissions("credit:underCustInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(UnderCustInfo underCustInfo, RedirectAttributes redirectAttributes) {
		underCustInfoService.delete(underCustInfo);
		addMessage(redirectAttributes, "删除线下借款-借款人基本信息成功");
		return "redirect:"+Global.getAdminPath()+"/underCustInfo/underCustInfo/?repage";
	}


	/***
	 * lwiei  保存线下客户信息
	 * @param underCustInfo
	 * @return
	 */
	@RequiresPermissions("credit:custinfo:edit")
	@ResponseBody
	@RequestMapping(value = "saveInfo")
	public AjaxView save(UnderCustInfo underCustInfo) {
		AjaxView ajaxView = new AjaxView();
//		String currApplyNo = underCompanyInfo.getApplyNo();
		try {
			Integer res = underCustInfoService.insertOrUpdate(underCustInfo);
			ajaxView.setSuccess().setMessage("保存主借人信息成功！");
			ajaxView.put("custIdForCustWork", underCustInfo.getId());
			ajaxView.put("res", res);
			ajaxView.put("id", underCustInfo.getId());
		} catch (Exception e) {
			logger.error("保存主借人信息失败！", e);
			ajaxView.setFailed().setMessage("保存主借人信息失败！");
		}
		return ajaxView;
	}


}