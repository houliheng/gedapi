package com.resoft.accounting.penaltyInterestRate.web;

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

import com.resoft.accounting.penaltyInterestRate.entity.PenaltyInterestRate;
import com.resoft.accounting.penaltyInterestRate.service.PenaltyInterestRateService;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 罚息利率调整Controller
 * 
 * @author wangguodong
 * @version 2016-08-11
 */
@Controller
@RequestMapping(value = "${adminPath}/accounting/penaltyInterestRate")
public class PenaltyInterestRateController extends BaseController {

	@Autowired
	private PenaltyInterestRateService penaltyInterestRateService;

	@ModelAttribute
	public PenaltyInterestRate get(@RequestParam(required = false) String id) {
		PenaltyInterestRate entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = penaltyInterestRateService.get(id);
		}
		if (entity == null) {
			entity = new PenaltyInterestRate();
		}
		return entity;
	}

	@RequiresPermissions("accounting:penaltyInterestRate:view")
	@RequestMapping(value = { "list", "" })
	public String list(PenaltyInterestRate penaltyInterestRate, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PenaltyInterestRate> page = penaltyInterestRateService.findCustomPage(new Page<PenaltyInterestRate>(request, response), penaltyInterestRate);
		model.addAttribute("page", page);
		return "app/accounting/penaltyInterestRate/penaltyInterestRateList";
	}

	@RequiresPermissions("accounting:penaltyInterestRate:view")
	@RequestMapping(value = "form")
	public String form(PenaltyInterestRate penaltyInterestRate, Model model, String readOnly) {
		model.addAttribute("penaltyInterestRate", penaltyInterestRate);
		model.addAttribute("readOnly", readOnly);
		return "app/accounting/penaltyInterestRate/penaltyInterestRateForm";
	}

	@RequiresPermissions("accounting:penaltyInterestRate:edit")
	@RequestMapping(value = "save")
	public String save(PenaltyInterestRate penaltyInterestRate, Model model, RedirectAttributes redirectAttributes) {
//		if (!beanValidator(model, penaltyInterestRate)) {
//			return form(penaltyInterestRate, model);
//		}
		penaltyInterestRateService.save(penaltyInterestRate);
		addMessage(redirectAttributes, "保存罚息利率成功");
		return "redirect:" + adminPath + "/accounting/penaltyInterestRate";
	}

	@RequiresPermissions("accounting:penaltyInterestRate:edit")
	@RequestMapping(value = "delete")
	public String delete(PenaltyInterestRate penaltyInterestRate, RedirectAttributes redirectAttributes) {
		penaltyInterestRateService.delete(penaltyInterestRate);
		addMessage(redirectAttributes, "删除罚息利率成功");
		return "redirect:" + adminPath + "/accounting/penaltyInterestRate";
	}

}