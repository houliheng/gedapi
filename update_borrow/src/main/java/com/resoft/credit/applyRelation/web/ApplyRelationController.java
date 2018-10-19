package com.resoft.credit.applyRelation.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.applyRelation.entity.ApplyRelation;
import com.resoft.credit.applyRelation.service.ApplyRelationService;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 申请客户关联信息表Controller
 * @author caoyinglong
 * @version 2016-02-29
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/applyRelation")
public class ApplyRelationController extends BaseController {

	@Autowired
	private ApplyRelationService applyRelationService;
	
	@ModelAttribute
	public ApplyRelation get(@RequestParam(required=false) String id) {
		ApplyRelation entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = applyRelationService.get(id);
		}
		if (entity == null){
			entity = new ApplyRelation();
		}
		return entity;
	}
	
	@RequiresPermissions("credit:applyRelation:view")
	@RequestMapping(value = {"list", ""})
	public String list(ApplyRelation applyRelation, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ApplyRelation> page = applyRelationService.findPage(new Page<ApplyRelation>(request, response), applyRelation); 
		model.addAttribute("page", page);
		return "app/credit/applyRelation/applyRelationList";
	}

	@RequiresPermissions("credit:applyRelation:view")
	@RequestMapping(value = "form")
	public String form(ApplyRelation applyRelation, Model model) {
		model.addAttribute("applyRelation", applyRelation);
		return "app/credit/applyRelation/applyRelationForm";
	}

	@RequiresPermissions("credit:applyRelation:edit")
	@RequestMapping(value = "save")
	public String save(ApplyRelation applyRelation, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, applyRelation)){
			return form(applyRelation, model);
		}
		applyRelationService.save(applyRelation);
		addMessage(redirectAttributes, "保存客户关联信息成功");
		return "redirect:"+Global.getAdminPath()+"/applyRelation/applyRelation/?repage";
	}
	
	@RequiresPermissions("credit:applyRelation:edit")
	@RequestMapping(value = "delete")
	public String delete(ApplyRelation applyRelation, RedirectAttributes redirectAttributes) {
		applyRelationService.delete(applyRelation);
		addMessage(redirectAttributes, "删除客户关联信息成功");
		return "redirect:"+Global.getAdminPath()+"/applyRelation/applyRelation/?repage";
	}
	
	@RequiresPermissions("credit:applyRelation:view")
	@RequestMapping(value = "relationalMatch")
	public String relationalMatch(ActTaskParam actTaskParam, String readOnly, ApplyRelation applyRelation, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<ApplyRelation> applyRelationList = applyRelationService.getRelationMatchByApplyNo(actTaskParam.getApplyNo());
		model.addAttribute("applyRelationList", applyRelationList);
		model.addAttribute("readOnly", readOnly);
		return "app/credit/relationMatch/relationMatchList";
	}
	
	@RequiresPermissions("credit:applyRelation:view")
	@RequestMapping(value = "relationalMatchInfo")
	public String relationalMatchInfo(ActTaskParam actTaskParam, HttpServletRequest request, HttpServletResponse response, Model model) {
		String contractNo = request.getParameter("contractNo");
		model.addAttribute("contractNo", contractNo);
		return "app/credit/relationMatch/relationMatchInfo";
	}




}