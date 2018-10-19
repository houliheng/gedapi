package com.resoft.accounting.applyChangeBankcard.web;

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

import com.resoft.accounting.applyChangeBankcard.entity.ApplyChangeBankcard;
import com.resoft.accounting.applyChangeBankcard.service.ApplyChangeBankcardService;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 银行卡变更申请Controller
 * 
 * @author wuxi01
 * @version 2016-03-03
 */
@Controller
@RequestMapping(value = "${adminPath}/accounting/applyChangeBankcard")
public class ApplyChangeBankcardController extends BaseController {

	@Autowired
	private ApplyChangeBankcardService applyChangeBankcardService;

	@ModelAttribute
	public ApplyChangeBankcard get(@RequestParam(required = false) String id) {
		ApplyChangeBankcard entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = applyChangeBankcardService.get(id);
		}
		if (entity == null) {
			entity = new ApplyChangeBankcard();
		}
		return entity;
	}

	@RequiresPermissions("accounting:applyChangeBankcard:view")
	@RequestMapping(value = { "list", "" })
	public String list(ApplyChangeBankcard applyChangeBankcard, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ApplyChangeBankcard> page = applyChangeBankcardService.findPage(new Page<ApplyChangeBankcard>(request, response), applyChangeBankcard);
		model.addAttribute("page", page);
		return "app/accounting/applyChangeBankcard/applyChangeBankcardList";
	}

	@RequiresPermissions("accounting:applyChangeBankcard:view")
	@RequestMapping(value = "form")
	public String form(ApplyChangeBankcard applyChangeBankcard, Model model) {
		model.addAttribute("applyChangeBankcard", applyChangeBankcard);
		return "app/accounting/applyChangeBankcard/applyChangeBankcardForm";
	}


	@RequiresPermissions("accounting:applyChangeBankcard:edit")
	@RequestMapping(value = "delete")
	public String delete(ApplyChangeBankcard applyChangeBankcard, RedirectAttributes redirectAttributes) {
		applyChangeBankcardService.delete(applyChangeBankcard);
		addMessage(redirectAttributes, "删除银行卡变更申请成功");
		return "redirect:" + Global.getAdminPath() + "/applyChangeBankcard/applyChangeBankcard/?repage";
	}

}