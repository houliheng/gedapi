package com.resoft.accounting.loanDetail.web;

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
import com.resoft.accounting.common.utils.Constants;
import com.resoft.accounting.loanDetail.entity.LoanDetail;
import com.resoft.accounting.loanDetail.service.LoanDetailService;
import com.resoft.accounting.staContractStatus.service.StaContractStatusService;

/**
 * 放款明细Controller
 * 
 * @author wangguodong
 * @version 2016-08-15
 */
@Controller
@RequestMapping(value = "${adminPath}/accounting/loanDetail")
public class LoanDetailController extends BaseController {

	@Autowired
	private LoanDetailService loanDetailService;

	@Autowired
	private StaContractStatusService staContractStatusService;

	@ModelAttribute
	public LoanDetail get(@RequestParam(required = false) String id) {
		LoanDetail entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = loanDetailService.get(id);
		}
		if (entity == null) {
			entity = new LoanDetail();
		}
		return entity;
	}

	@RequiresPermissions("accounting:loanDetail:view")
	@RequestMapping(value = { "list", "" })
	public String list(LoanDetail loanDetail, HttpServletRequest request, HttpServletResponse response, Model model, String queryFlag) {
		String level = null;
		if (loanDetail != null && loanDetail.getCompany() != null && loanDetail.getCompany().getId() != null) {
			Office office = new Office();
			office.setId(loanDetail.getCompany().getId());
			level = staContractStatusService.getOfficeLevel(loanDetail.getCompany().getId());
			if (Constants.OFFICE_LEVEL_DQ.equals(level)) {
				loanDetail.setOrgLevel2(office);
			} else if (Constants.OFFICE_LEVEL_QY.equals(level)) {
				loanDetail.setOrgLevel3(office);
			} else if (Constants.OFFICE_LEVEL_MD.equals(level)) {
				loanDetail.setOrgLevel4(office);
			}
		}
		Page<LoanDetail> page = null;
		if ("button".equals(queryFlag)) {
			page = loanDetailService.findCustomPage(new Page<LoanDetail>(request, response), loanDetail);
		}
		model.addAttribute("page", page);
		model.addAttribute("loanDetail", loanDetail);
		return "app/accounting/loanDetail/loanDetailList";
	}

	@RequiresPermissions("accounting:loanDetail:view")
	@RequestMapping(value = "form")
	public String form(LoanDetail loanDetail, Model model) {
		model.addAttribute("loanDetail", loanDetail);
		return "app/accounting/loanDetail/loanDetailForm";
	}

	@RequiresPermissions("accounting:loanDetail:edit")
	@RequestMapping(value = "save")
	public String save(LoanDetail loanDetail, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, loanDetail)) {
			return form(loanDetail, model);
		}
		// loanDetailService.save(loanDetail);
		addMessage(redirectAttributes, "保存放款明细成功");
		return "redirect:" + Global.getAdminPath() + "/loanDetail/loanDetail/?repage";
	}

	@RequiresPermissions("accounting:loanDetail:edit")
	@RequestMapping(value = "delete")
	public String delete(LoanDetail loanDetail, RedirectAttributes redirectAttributes) {
		// loanDetailService.delete(loanDetail);
		addMessage(redirectAttributes, "删除放款明细成功");
		return "redirect:" + Global.getAdminPath() + "/loanDetail/loanDetail/?repage";
	}

}