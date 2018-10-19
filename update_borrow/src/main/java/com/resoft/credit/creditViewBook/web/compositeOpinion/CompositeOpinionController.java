package com.resoft.credit.creditViewBook.web.compositeOpinion;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.web.BaseController;
import com.resoft.credit.checkCoupleDoubtful.entity.CheckCoupleDoubtful;
import com.resoft.credit.checkCoupleDoubtful.service.CheckCoupleDoubtfulService;
import com.resoft.credit.checkWeb.entity.CheckWeb;
import com.resoft.credit.checkWeb.service.CheckWebService;

/**
 * 信审意见书-综合意见Controller
 * @author wuxi01
 * @version 2016-03-01
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/creditViewBook/compositeOpinion")
public class CompositeOpinionController extends BaseController {

	@Autowired
	private CheckCoupleDoubtfulService checkCoupleDoubtfulService;
	
	@Autowired
	private CheckWebService checkWebService;
	
	@RequiresPermissions("credit:creditViewBook:compositeOpinion:view")
	@RequestMapping(value = "form")
	public String form(CheckCoupleDoubtful checkCoupleDoubtful, CheckWeb checkWeb, Model model) {
		model.addAttribute("checkCoupleDoubtful", checkCoupleDoubtful);
		model.addAttribute("checkWeb", checkWeb);
		return "app/credit/creditViewBook/compositeOpinion/compositeOpinionForm";
	}

	@RequiresPermissions("credit:creditViewBook:compositeOpinion:edit")
	@RequestMapping(value = "save")
	public String save(CheckCoupleDoubtful checkCoupleDoubtful, CheckWeb checkWeb, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, checkCoupleDoubtful)){
			return form(checkCoupleDoubtful, checkWeb, model);
		}
		checkCoupleDoubtfulService.save(checkCoupleDoubtful);
		checkWebService.save(checkWeb);
		addMessage(redirectAttributes, "保存综合信息成功");
		return "redirect:"+Global.getAdminPath()+"/creditViewBook/compositeOpinion/?repage";
	}

}