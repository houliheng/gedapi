package com.resoft.credit.creditAndLine.web.creditCust;

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
import com.resoft.credit.creditAndLine.entity.creditCust.CreditCust;
import com.resoft.credit.creditAndLine.entity.creditCust.CreditCustLoan;
import com.resoft.credit.creditAndLine.service.creditCust.CreditCustLoanService;
import com.resoft.credit.creditAndLine.service.creditCust.CreditCustService;

/**
 * 征信个人贷款明细Controller
 * 
 * @author wuxi01
 * @version 2016-02-27
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/creditAndLine/creditCust/creditCustLoan")
public class CreditCustLoanController extends BaseController {

	@Autowired
	private CreditCustLoanService creditCustLoanService;

	@Autowired
	private CreditCustService creditCustService;

	@ModelAttribute
	public CreditCustLoan get(@RequestParam(required = false) String id) {
		CreditCustLoan entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = creditCustLoanService.get(id);
		}
		if (entity == null) {
			entity = new CreditCustLoan();
		}
		return entity;
	}

	@RequiresPermissions("credit:creditAndLine:creditCust:creditCustLoan:view")
	@RequestMapping(value = { "list", "" })
	public String list(String creditCustId, String readOnly, Model model,String applyNo) {
		CreditCustLoan creditCustLoan = new CreditCustLoan();
		CreditCust creditCust = creditCustService.get(creditCustId);
		creditCustLoan.setCreditCust(creditCust);
		List<CreditCustLoan> creditCustLoanList = creditCustLoanService.findList(creditCustLoan);
		model.addAttribute("creditCustId", creditCustId);
		model.addAttribute("readOnly", readOnly);
		model.addAttribute("applyNo", applyNo);
		model.addAttribute("creditCustLoanList", creditCustLoanList);
		return "app/credit/creditAndLine/creditCust/creditCustLoanList";
	}

	@RequiresPermissions("credit:creditAndLine:creditCust:creditCustLoan:view")
	@RequestMapping(value = "form")
	public String form(String creditCustId, String creditCustLoanId, String readOnly, Model model,String applyNo) {
		CreditCustLoan creditCustLoan = new CreditCustLoan();
		// 1.新增
		if (StringUtils.isNotBlank(creditCustId)) {
			CreditCust creditCust = creditCustService.get(creditCustId);
			creditCustLoan.setCreditCust(creditCust);
		}
		// 2.修改
		if (StringUtils.isNotBlank(creditCustLoanId)) {
			try {
				creditCustLoan = creditCustLoanService.get(creditCustLoanId);
				creditCustId = creditCustLoan.getCreditCust().getId();
				CreditCust creditCust = creditCustService.get(creditCustId);
				creditCustLoan.setCreditCust(creditCust);
			} catch (Exception e) {
				logger.error("系统数据出现错误，请联系管理员！", e);
			}
		}
		model.addAttribute("creditCustLoan", creditCustLoan);
		model.addAttribute("readOnly", readOnly);
		model.addAttribute("applyNo", applyNo);
		return "app/credit/creditAndLine/creditCust/creditCustLoanForm";
	}

	@ResponseBody
	@RequiresPermissions("credit:creditAndLine:creditCust:creditCustLoan:edit")
	@RequestMapping(value = "save")
	public AjaxView save(@RequestBody CreditCustLoan creditCustLoan) {
		AjaxView ajaxView = new AjaxView();
		try {
			creditCustLoan.setCreditCustId(creditCustLoan.getCreditCust().getId());
			creditCustLoanService.save(creditCustLoan);
			try {
				creditCustService.updateSumElements(creditCustLoan.getCreditCust().getId());
				ajaxView.setSuccess().setMessage("保存个人贷款信息成功！");
			} catch (Exception e) {
				logger.error("保存个人贷款信息成功，但更新个人征信信息失败，请联系管理员！", e);
				ajaxView.setFailed().setMessage("保存个人贷款信息成功，但更新个人征信信息失败，请联系管理员！");
			}
		} catch (Exception e) {
			logger.error("保存个人贷款信息失败！", e);
			ajaxView.setFailed().setMessage("保存个人贷款信息失败！");
		}
		return ajaxView;
	}

	@ResponseBody
	@RequiresPermissions("credit:creditAndLine:creditCust:creditCustLoan:edit")
	@RequestMapping(value = "delete")
	public AjaxView delete(String ids) {
		AjaxView ajaxView = new AjaxView();
		try {
			creditCustLoanService.banchDelete(ids);
			ajaxView.setSuccess().setMessage("删除个人贷款信息成功！");
			String[] idArrays = ids.split(",");
			try {
				CreditCustLoan creditCustLoan = new CreditCustLoan();
				//如果被删除的数据是多条，也只查出一条数据，获取这条数据的creditCustId。
				creditCustLoan = creditCustLoanService.get(idArrays[0]);
				creditCustService.updateSumElements(creditCustLoan.getCreditCust().getId());
				ajaxView.setSuccess();
			} catch (Exception e) {
				logger.error("删除个人贷款信息成功，但更新个人征信信息失败，请联系管理员！", e);
				ajaxView.setFailed().setMessage("删除个人贷款信息成功，但更新个人征信信息失败，请联系管理员！");
			}
			
		} catch (Exception e) {
			logger.error("删除个人贷款信息失败！", e);
			ajaxView.setFailed().setMessage("删除个人贷款信息失败！");
		}
		return ajaxView;
	}

}