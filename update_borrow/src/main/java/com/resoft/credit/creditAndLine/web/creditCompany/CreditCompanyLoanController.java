package com.resoft.credit.creditAndLine.web.creditCompany;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.resoft.common.utils.AjaxView;
import com.resoft.credit.creditAndLine.entity.creditCompany.CreditCompany;
import com.resoft.credit.creditAndLine.entity.creditCompany.CreditCompanyLoan;
import com.resoft.credit.creditAndLine.service.creditCompany.CreditCompanyLoanService;
import com.resoft.credit.creditAndLine.service.creditCompany.CreditCompanyService;

/**
 * 征信企业贷款明细Controller
 * 
 * @author wuxi01
 * @version 2016-02-27
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/creditAndLine/creditCompany/creditCompanyLoan")
public class CreditCompanyLoanController extends BaseController {

	@Autowired
	private CreditCompanyLoanService creditCompanyLoanService;

	@Autowired
	private CreditCompanyService creditCompanyService;

	@ModelAttribute
	public CreditCompanyLoan get(@RequestParam(required = false) String id) {
		CreditCompanyLoan entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = creditCompanyLoanService.get(id);
		}
		if (entity == null) {
			entity = new CreditCompanyLoan();
		}
		return entity;
	}
	
	@RequiresPermissions("credit:creditAndLine:creditCompany:creditCompanyLoan:view")
	@RequestMapping(value = { "load", "" })
	public String load(String creditCompanyId, String readOnly, Model model,String applyNo) {
		model.addAttribute("creditCompanyId", creditCompanyId);
		model.addAttribute("readOnly", readOnly);
		model.addAttribute("applyNo", applyNo);
		return "app/credit/creditAndLine/creditCompany/creditCompanyLoanIndex";
	}

	@RequiresPermissions("credit:creditAndLine:creditCompany:creditCompanyLoan:view")
	@RequestMapping(value = { "list", "" })
	public String list(String creditCompanyId, String readOnly, Model model,String applyNo) {
		CreditCompanyLoan creditCompanyLoan = new CreditCompanyLoan();
		CreditCompany creditCompany = new CreditCompany();
		creditCompany.setId(creditCompanyId);
		creditCompanyLoan.setCreditCompany(creditCompany);
		List<CreditCompanyLoan> creditCompanyLoanList = creditCompanyLoanService.findList(creditCompanyLoan);
		model.addAttribute("creditCompanyLoanList", creditCompanyLoanList);
		model.addAttribute("creditCompanyId", creditCompanyId);
		model.addAttribute("readOnly", readOnly);
		model.addAttribute("applyNo", applyNo);
		return "app/credit/creditAndLine/creditCompany/creditCompanyLoanList";
	}
	
	@RequiresPermissions("credit:creditAndLine:creditCompany:creditCompanyLoan:view")
	@RequestMapping(value = "form")
	public String form(String creditCompanyLoanId, String creditCompanyId, String readOnly, Model model,String applyNo) {
		CreditCompanyLoan creditCompanyLoan = new CreditCompanyLoan();
		// 1.新增
		if (StringUtils.isNotBlank(creditCompanyId)) {
			readOnly = "1";
			CreditCompany creditCompany = creditCompanyService.get(creditCompanyId);
			creditCompanyLoan.setCreditCompany(creditCompany);
		}
		// 2.修改
		if (StringUtils.isNotBlank(creditCompanyLoanId)) {
			try {
				creditCompanyLoan = creditCompanyLoanService.get(creditCompanyLoanId);
				creditCompanyId = creditCompanyLoan.getCreditCompany().getId();
				CreditCompany creditCompany = creditCompanyService.get(creditCompanyId);
				creditCompanyLoan.setCreditCompany(creditCompany);
			} catch (Exception e) {
				logger.error("系统数据发生错误，请联系管理员！", e);
				creditCompanyLoan = new CreditCompanyLoan();
				model.addAttribute("message", "系统数据发生错误，请联系管理员！");
			}
		}
		model.addAttribute("creditCompanyLoan", creditCompanyLoan);
		model.addAttribute("applyNo", applyNo);
		return "app/credit/creditAndLine/creditCompany/creditCompanyLoanForm";
	}

	@ResponseBody
	@RequiresPermissions("credit:creditAndLine:creditCompany:creditCompanyLoan:edit")
	@RequestMapping(value = "save")
	public AjaxView save(@RequestBody CreditCompanyLoan creditCompanyLoan) {
		AjaxView ajaxView = new AjaxView();
		try {
			creditCompanyLoan.setCreditCompanyId(creditCompanyLoan.getCreditCompany().getId());
			creditCompanyLoanService.save(creditCompanyLoan);
			try {
				creditCompanyService.updateSumElements(creditCompanyLoan.getCreditCompany().getId());
				ajaxView.setSuccess().setMessage("保存企业贷款信息成功！");
			} catch (Exception e) {
				logger.error("保存企业贷款明细成功，但更新企业贷款信息失败，请联系管理员！", e);
				ajaxView.setFailed().setMessage("保存企业贷款明细成功，但更新企业贷款信息失败，请联系管理员！");
			}
		} catch (Exception e) {
			logger.error("保存企业贷款信息失败！", e);
			ajaxView.setSuccess().setMessage("保存企业贷款信息成功！");
		}
		return ajaxView;
	}

	@ResponseBody
	@RequiresPermissions("credit:creditAndLine:creditCompany:creditCompanyLoan:edit")
	@RequestMapping(value = "delete")
	public AjaxView delete(String ids) {
		AjaxView ajaxView = new AjaxView();
		try {
			creditCompanyLoanService.banchDelete(ids);
			ajaxView.setSuccess().setMessage("删除企业贷款信息成功！");
			String[] idArrays =  ids.split(",");
			try {
				CreditCompanyLoan creditCompanyLoan = new CreditCompanyLoan();
				//如果被删除的数据是多条，也只查出一条数据，获取这条数据的creditCompanyId。
				creditCompanyLoan = creditCompanyLoanService.get(idArrays[0]);
				creditCompanyService.updateSumElements(creditCompanyLoan.getCreditCompany().getId());
				ajaxView.setSuccess().setMessage("删除企业贷款明细成功");
			} catch (Exception e) {
				logger.error("删除企业贷款明细成功,更新企业贷款信息失败，请联系管理员！", e);
				ajaxView.setFailed().setMessage("删除企业贷款明细成功,更新企业贷款信息失败，请联系管理员！");
			}
		} catch (Exception e) {
			logger.error("删除企业贷款信息失败！", e);
			ajaxView.setFailed().setMessage("删除企业贷款信息失败！");
		}
		return ajaxView;
	}

}