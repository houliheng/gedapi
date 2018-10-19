package com.resoft.credit.creditAndLine.web;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 征信及流水Controller
 * @author wuxi01
 * @version 2016-02-26
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/creditAndLine")
public class CreditAndLineController extends BaseController {
	
	@RequiresPermissions("credit:creditAndLine")
	@RequestMapping(value = {"load", ""})
	public String load(ActTaskParam actTaskParam, String readOnly, Model model) {
		model.addAttribute("actTaskParam", actTaskParam);
		model.addAttribute("readOnly", readOnly);
		return "app/credit/creditAndLine/creditAndLineIndex";
	}

}