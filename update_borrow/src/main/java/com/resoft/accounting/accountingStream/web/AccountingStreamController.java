package com.resoft.accounting.accountingStream.web;

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
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.resoft.accounting.accountingStream.entity.AccountingStream;
import com.resoft.accounting.accountingStream.service.AccountingStreamService;
import com.resoft.accounting.common.utils.Constants;
import com.resoft.accounting.staContractStatus.service.StaContractStatusService;

/** 
 * 账务流水Controller
 * 
 * @author wangguodong
 * @version 2016-08-12
 */
@Controller
@RequestMapping(value = "${adminPath}/accounting/accountingStream")
public class AccountingStreamController extends BaseController {

	@Autowired
	private AccountingStreamService accountingStreamService;

	@Autowired
	private StaContractStatusService staContractStatusService;

	@ModelAttribute
	public AccountingStream get(@RequestParam(required = false) String id) {
		AccountingStream entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = accountingStreamService.get(id);
		}
		if (entity == null) {
			entity = new AccountingStream();
		}
		return entity;
	}

	@RequiresPermissions("accounting:accountingStream:view")
	@RequestMapping(value = { "list", "" })
	public String list(AccountingStream accountingStream, HttpServletRequest request, HttpServletResponse response, Model model) {
		String level = null;
		if (accountingStream != null && accountingStream.getCompany() != null && accountingStream.getCompany().getId() != null) {
			Office office = new Office();
			office.setId(accountingStream.getCompany().getId());
			level = staContractStatusService.getOfficeLevel(accountingStream.getCompany().getId());
			if (Constants.OFFICE_LEVEL_DQ.equals(level)) {
				accountingStream.setOrgLevel2(office);
			} else if (Constants.OFFICE_LEVEL_QY.equals(level)) {
				accountingStream.setOrgLevel3(office);
			} else if (Constants.OFFICE_LEVEL_MD.equals(level)) {
				accountingStream.setOrgLevel4(office);
			}
		}
		Page<AccountingStream> page = accountingStreamService.findCustomPage(new Page<AccountingStream>(request, response), accountingStream);
		model.addAttribute("page", page);
		model.addAttribute("accountingStream", accountingStream);
		return "app/accounting/accountingStream/accountingStreamList";
	}

	@RequiresPermissions("accounting:accountingStream:view")
	@RequestMapping(value = "form")
	public String form(AccountingStream accountingStream, Model model) {
		model.addAttribute("accountingStream", accountingStream);
		return "app/accounting/accountingStream/accountingStreamForm";
	}

	@RequiresPermissions("accounting:accountingStream:edit")
	@RequestMapping(value = "save")
	public String save(AccountingStream accountingStream, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, accountingStream)) {
			return form(accountingStream, model);
		}
		accountingStreamService.save(accountingStream);
		addMessage(redirectAttributes, "保存账务流水成功");
		return "redirect:" + Global.getAdminPath() + "/accountingStream/accountingStream/?repage";
	}

	@RequiresPermissions("accounting:accountingStream:edit")
	@RequestMapping(value = "delete")
	public String delete(AccountingStream accountingStream, RedirectAttributes redirectAttributes) {
		accountingStreamService.delete(accountingStream);
		addMessage(redirectAttributes, "删除账务流水成功");
		return "redirect:" + Global.getAdminPath() + "/accountingStream/accountingStream/?repage";
	}

}