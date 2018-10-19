package com.resoft.postloan.debtColletion.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 客户详情Controller
 * 
 * @author wangguodong
 * @version 2016-06-02
 */
@Controller
@RequestMapping(value = "${adminPath}/postloan/debtCollectionDetailsPage")
public class PLCustmorDetailToCreAndAccController extends BaseController {

	// acc还款详情页面
	@RequestMapping(value = "toAccDetailsPage")
	public String toAccDetailsPage(String contractNo) {
		String url = Global.getConfig("ACC_VISIT_URL");
		return "redirect:" + url + "/f/plAccounting/plDebtCollectionDetail/plDebtCollectionDetails?contractNo=" + contractNo;
	}

	// cre借款申请页面
	@RequestMapping(value = "toCreLoanApplyPage")
	public String toCreLoanApplyPage(String applyNo, boolean readOnly) {
		String url = Global.getConfig("CRE_VISIT_URL");
		return "redirect:" + url + "/f/credit/PLworkflow/toCreLoanApplyPage?applyNo=" + applyNo + "&readOnly=" + readOnly;
	}

	// cre客户信息页面
	@RequestMapping(value = "toCreCustInfoPage")
	public String toCreCustInfoPage(String applyNo, boolean readOnly) {
		String url = Global.getConfig("CRE_VISIT_URL");
		return "redirect:" + url + "/f/credit/PLworkflow/toCreCustInfoPage?applyNo=" + applyNo + "&readOnly=" + readOnly;
	}

	// cre担保信息页面
	@RequestMapping(value = "toCreGuarantorInfoPage")
	public String toCreGuarantorInfoPage(String applyNo, boolean readOnly) {
		String url = Global.getConfig("CRE_VISIT_URL");
		return "redirect:" + url + "/f/credit/PLworkflow/toCreGuarantorInfoPage?applyNo=" + applyNo + "&readOnly=" + readOnly;
	}

	// cre抵质押物信息页面
	@RequestMapping(value = "toCreMortgageCarInfoPage")
	public String toCreMortgageCarInfoPage(String applyNo, boolean readOnly) {
		String url = Global.getConfig("CRE_VISIT_URL");
		return "redirect:" + url + "/f/credit/PLworkflow/toCreMortgageCarInfoPage?applyNo=" + applyNo + "&readOnly=" + readOnly;
	}

}