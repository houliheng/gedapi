package com.resoft.credit.companyHistory.web;

import com.resoft.credit.companyHistory.entity.AccCompanyHistory;
import com.resoft.credit.companyHistory.service.AccCompanyHistoryService;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 企业开户历史表Controller
 * @author weiruihong
 * @version 2018-06-26
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/accCompanyHistory")
public class AccCompanyHistoryController extends BaseController {

	@Autowired
	private AccCompanyHistoryService accCompanyHistoryService;
	
	@ModelAttribute
	public AccCompanyHistory get(@RequestParam(required=false) String id) {
		AccCompanyHistory entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = accCompanyHistoryService.get(id);
		}
		if (entity == null){
			entity = new AccCompanyHistory();
		}
		return entity;
	}
	
	@RequiresPermissions("credit:accCompanyHistory:view")
	@RequestMapping(value = {"list", ""})
	public String list(AccCompanyHistory accCompanyHistory, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AccCompanyHistory> page = accCompanyHistoryService.findCustomPage(new Page<AccCompanyHistory>(request, response), accCompanyHistory); 
		model.addAttribute("page", page);
		return "app/credit/companyHistory/accCompanyHistoryList";
	}

	@RequiresPermissions("credit:accCompanyHistory:view")
	@RequestMapping(value = "form")
	public String form(AccCompanyHistory accCompanyHistory, Model model) {
		model.addAttribute("accCompanyHistory", accCompanyHistory);
		return "app/credit/companyHistory/accCompanyHistoryForm";
	}

	@RequiresPermissions("credit:accCompanyHistory:edit")
	@RequestMapping(value = "save")
	public String save(AccCompanyHistory accCompanyHistory, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, accCompanyHistory)){
			return form(accCompanyHistory, model);
		}
		accCompanyHistoryService.save(accCompanyHistory);
		addMessage(redirectAttributes, "保存企业开户历史表成功");
		return "redirect:"+Global.getAdminPath()+"/companyHistory/accCompanyHistory/?repage";
	}
	
	@RequiresPermissions("credit:accCompanyHistory:edit")
	@RequestMapping(value = "delete")
	public String delete(AccCompanyHistory accCompanyHistory, RedirectAttributes redirectAttributes) {
		accCompanyHistoryService.delete(accCompanyHistory);
		addMessage(redirectAttributes, "删除企业开户历史表成功");
		return "redirect:"+Global.getAdminPath()+"/companyHistory/accCompanyHistory/?repage";
	}


	@RequiresPermissions("credit:accCompanyHistory:view")
	@RequestMapping(value = "historyDetail")
	public String historyDetail(String id, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<AccCompanyHistory> accCompanyHistoList = accCompanyHistoryService.selectAccByaccountCompanyId(id);
		model.addAttribute("accCompanyHistoList",accCompanyHistoList);
		return "app/credit/creGedAccountCompany/companyHistory";
	}
}