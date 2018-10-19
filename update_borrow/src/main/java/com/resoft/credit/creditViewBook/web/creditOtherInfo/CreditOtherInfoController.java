package com.resoft.credit.creditViewBook.web.creditOtherInfo;

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
import com.resoft.credit.creditViewBook.entity.creditOtherInfo.CreditOtherInfo;
import com.resoft.credit.creditViewBook.service.creditOtherInfo.CreditOtherInfoService;

/**
 * 征信意见书其他信息Controller
 * @author wuxi01
 * @version 2016-02-29
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/creditViewBook/creditOtherInfo")
public class CreditOtherInfoController extends BaseController {

	@Autowired
	private CreditOtherInfoService creditOtherInfoService;
	
	@ModelAttribute
	public CreditOtherInfo get(@RequestParam(required=false) String id) {
		CreditOtherInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = creditOtherInfoService.get(id);
		}
		if (entity == null){
			entity = new CreditOtherInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("credit:creditViewBook:creditOtherInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(CreditOtherInfo creditOtherInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CreditOtherInfo> page = creditOtherInfoService.findPage(new Page<CreditOtherInfo>(request, response), creditOtherInfo); 
		model.addAttribute("page", page);
		return "app/credit/creditViewBook/creditOtherInfo/creditOtherInfoList";
	}

	@RequiresPermissions("credit:creditViewBook:creditOtherInfo:view")
	@RequestMapping(value = "form")
	public String form(CreditOtherInfo creditOtherInfo, Model model) {
		model.addAttribute("creditOtherInfo", creditOtherInfo);
		return "app/credit/creditViewBook/creditOtherInfo/creditOtherInfoForm";
	}

	@RequiresPermissions("credit:creditViewBook:creditOtherInfo:edit")
	@RequestMapping(value = "save")
	public String save(CreditOtherInfo creditOtherInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, creditOtherInfo)){
			return form(creditOtherInfo, model);
		}
		creditOtherInfoService.save(creditOtherInfo);
		addMessage(redirectAttributes, "保存征信意见书其他信息成功");
		return "redirect:"+Global.getAdminPath()+"/creditViewBook/creditOtherInfo/?repage";
	}
	
	@RequiresPermissions("credit:creditViewBook:creditOtherInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(CreditOtherInfo creditOtherInfo, RedirectAttributes redirectAttributes) {
		creditOtherInfoService.delete(creditOtherInfo);
		addMessage(redirectAttributes, "删除征信意见书其他信息成功");
		return "redirect:"+Global.getAdminPath()+"/creditViewBook/creditOtherInfo/?repage";
	}

}