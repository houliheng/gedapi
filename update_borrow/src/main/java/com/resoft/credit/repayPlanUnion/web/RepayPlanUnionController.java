package com.resoft.credit.repayPlanUnion.web;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.google.common.collect.Maps;
import com.resoft.credit.repayPlanUnion.entity.RepayPlanUnion;
import com.resoft.credit.repayPlanUnion.service.RepayPlanUnionService;

/**
 * 还款计划授信Controller
 * @author wangguodong
 * @version 2016-12-14
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/repayPlanUnion")
public class RepayPlanUnionController extends BaseController {

	@Autowired
	private RepayPlanUnionService repayPlanUnionService;
	
	@ModelAttribute
	public RepayPlanUnion get(@RequestParam(required=false) String id) {
		RepayPlanUnion entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = repayPlanUnionService.get(id);
		}
		if (entity == null){
			entity = new RepayPlanUnion();
		}
		return entity;
	}
	
	@RequiresPermissions("credit:repayPlanUnion:view")
	@RequestMapping(value = {"list", ""})
	public String list(RepayPlanUnion repayPlanUnion, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<RepayPlanUnion> page = repayPlanUnionService.findCustomPage(new Page<RepayPlanUnion>(request, response), repayPlanUnion); 
		model.addAttribute("page", page);
		return "app/credit/repayPlanUnion/repayPlanUnionList";
	}

	@RequiresPermissions("credit:repayPlanUnion:view")
	@RequestMapping(value = "form")
	public String form(RepayPlanUnion repayPlanUnion, Model model) {
		model.addAttribute("repayPlanUnion", repayPlanUnion);
		return "app/credit/repayPlanUnion/repayPlanUnionForm";
	}

	@RequiresPermissions("credit:repayPlanUnion:edit")
	@RequestMapping(value = "save")
	public String save(RepayPlanUnion repayPlanUnion, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, repayPlanUnion)){
			return form(repayPlanUnion, model);
		}
		repayPlanUnionService.save(repayPlanUnion);
		addMessage(redirectAttributes, "保存还款计划授信成功");
		return "redirect:"+Global.getAdminPath()+"/repayPlanUnion/repayPlanUnion/?repage";
	}
	
	@RequiresPermissions("credit:repayPlanUnion:edit")
	@RequestMapping(value = "delete")
	public String delete(RepayPlanUnion repayPlanUnion, RedirectAttributes redirectAttributes) {
		repayPlanUnionService.delete(repayPlanUnion);
		addMessage(redirectAttributes, "删除还款计划授信成功");
		return "redirect:"+Global.getAdminPath()+"/repayPlanUnion/repayPlanUnion/?repage";
	}
	
	@RequiresPermissions("credit:repayPlanUnion:view")
	@RequestMapping(value = {"getRepayPlanByParam"})
	public String getRepayPlanByParam(RepayPlanUnion repayPlanUnion, HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			Map<String, String> param = Maps.newConcurrentMap();
			param.put("applyNo", request.getParameter("applyNo"));
			param.put("taskDefKey", request.getParameter("taskDefKey"));
			param.put("approId", request.getParameter("approId"));
			List<RepayPlanUnion> repayPlanUnionList =repayPlanUnionService.getRepayPlanByParam(param);
			model.addAttribute("repayPlanList", repayPlanUnionList);
		} catch (Exception e) {
			logger.error("查询还款计划数据失败", e);
		}
		return "app/credit/repayPlan/repayPlanList";
		//return "app/credit/repayPlanUnion/repayPlanUnionList";
	}
}