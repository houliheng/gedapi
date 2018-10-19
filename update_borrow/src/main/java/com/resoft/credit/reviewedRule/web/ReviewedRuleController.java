package com.resoft.credit.reviewedRule.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.resoft.common.utils.AjaxView;
import com.resoft.common.utils.Constants;
import com.resoft.credit.reviewedRule.entity.ReviewedRule;
import com.resoft.credit.reviewedRule.service.ReviewedRuleService;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

import resoft.crms.util.StringUtil;

/**
 * 借款审核表Controller
 *
 * @author zhaohuakui
 * @version 2016-12-08
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/reviewedRule")
public class ReviewedRuleController extends BaseController {

	@Autowired
	private ReviewedRuleService reviewedRuleService;

	@RequiresPermissions("credit:reviewedRule:view")
	@RequestMapping(value = { "list", "" })
	public String list(ReviewedRule reviewedRule, HttpServletRequest request, HttpServletResponse response, Model model,
			String readOnly) {

		String productId = reviewedRuleService.getProductId(reviewedRule);
//		if(StringUtil.isEmpty(productId)) productId = "1";
		//z解决分公司经理 区域经理在查看黑名单规则不一致的问题
		if(StringUtil.isEmpty(productId)){
			productId=reviewedRuleService.getProductIdByRecent(reviewedRule);
		}
		model.addAttribute("productId", productId);

		reviewedRule.setReviewedSheet("1");
		String taskDefKey = reviewedRule.getTaskDefKey();
		List<ReviewedRule> ruleLst = reviewedRuleService.findReviewedRule(reviewedRule);
		ReviewedRule ruleLst0 = ruleLst.get(0);
		ReviewedRule ruleLst1 = ruleLst.get(1);
		ReviewedRule ruleLst2 = ruleLst.get(2);
		model.addAttribute("ruleLst0", ruleLst0);
		model.addAttribute("ruleLst1", ruleLst1);
		model.addAttribute("ruleLst2", ruleLst2);
		model.addAttribute("ruleLst", ruleLst);
		model.addAttribute("taskDefKey", reviewedRule.getTaskDefKey());
		model.addAttribute("applyNo", reviewedRule.getApplyNo());
		model.addAttribute("readOnly", readOnly);

		{
			reviewedRule.setReviewedSheet("2");
			reviewedRule.setTaskDefKey("utask_fgsfksh");
			List<ReviewedRule> fg_tsbstkshList = reviewedRuleService.findRule(reviewedRule);
			model.addAttribute("fg_tsbstkshList", fg_tsbstkshList);

			reviewedRule.setReviewedSheet("2");
			reviewedRule.setTaskDefKey("utask_qyfksh");
			List<ReviewedRule> tsbstkshList = reviewedRuleService.findRule(reviewedRule);
			model.addAttribute("tsbstkshList", tsbstkshList);

			reviewedRule.setReviewedSheet("2");
			reviewedRule.setTaskDefKey("utask_dqfkzysh");
			List<ReviewedRule> dq_tsbstkshList = reviewedRuleService.findRule(reviewedRule);
			model.addAttribute("dq_tsbstkshList", dq_tsbstkshList);
		}

		{
			reviewedRule.setReviewedSheet("3");
			reviewedRule.setRange("");
			reviewedRule.setTaskDefKey("utask_fgsfksh");
			List<ReviewedRule> fg_xyjkshList = reviewedRuleService.findRule(reviewedRule);
			model.addAttribute("fg_xyjkshList", fg_xyjkshList);

			reviewedRule.setReviewedSheet("3");
			reviewedRule.setRange("");
			reviewedRule.setTaskDefKey("utask_qyfksh");
			List<ReviewedRule> qy_xyjkshList = reviewedRuleService.findRule(reviewedRule);
			model.addAttribute("qy_xyjkshList", qy_xyjkshList);

			reviewedRule.setReviewedSheet("3");
			reviewedRule.setRange("");
			reviewedRule.setTaskDefKey("utask_dqfkzysh");
			List<ReviewedRule> dq_xyjkshList = reviewedRuleService.findRule(reviewedRule);
			model.addAttribute("dq_xyjkshList", dq_xyjkshList);
		}

		{
			reviewedRule.setReviewedSheet("3");
			reviewedRule.setTaskDefKey("utask_fgsfksh");
			List<ReviewedRule> fgs_xyjkshList = reviewedRuleService.findRuleS(reviewedRule);
			model.addAttribute("fgs_xyjkshList", fgs_xyjkshList);

			reviewedRule.setReviewedSheet("3");
			reviewedRule.setTaskDefKey("utask_qyfksh");
			List<ReviewedRule> qys_xyjkshList = reviewedRuleService.findRuleS(reviewedRule);
			model.addAttribute("qys_xyjkshList", qys_xyjkshList);

			reviewedRule.setReviewedSheet("3");
			reviewedRule.setTaskDefKey("utask_dqfkzysh");
			List<ReviewedRule> dqs_xyjkshList = reviewedRuleService.findRuleS(reviewedRule);
			model.addAttribute("dqs_xyjkshList", dqs_xyjkshList);
		}

		{
			reviewedRule.setRange(null);
			reviewedRule.setReviewedSheet("4-1");
			reviewedRule.setTaskDefKey("utask_fgsfksh");
			List<ReviewedRule> fg_1hhjkshList = reviewedRuleService.findRule(reviewedRule);
			model.addAttribute("fg_1hhjkshList", fg_1hhjkshList);

			reviewedRule.setReviewedSheet("4-1");
			reviewedRule.setTaskDefKey("utask_qyfksh");
			List<ReviewedRule> qy_1hhjkshList = reviewedRuleService.findRule(reviewedRule);
			model.addAttribute("qy_1hhjkshList", qy_1hhjkshList);

			reviewedRule.setReviewedSheet("4-1");
			reviewedRule.setTaskDefKey("utask_dqfkzysh");
			List<ReviewedRule> dq_1hhjkshList = reviewedRuleService.findRule(reviewedRule);
			model.addAttribute("dq_1hhjkshList", dq_1hhjkshList);
		}

		{
			reviewedRule.setReviewedSheet("4-2");
			reviewedRule.setTaskDefKey("utask_fgsfksh");
			List<ReviewedRule> fg_2hhjkshList = reviewedRuleService.findRule(reviewedRule);
			model.addAttribute("fg_2hhjkshList", fg_2hhjkshList);

			reviewedRule.setReviewedSheet("4-2");
			reviewedRule.setTaskDefKey("utask_qyfksh");
			List<ReviewedRule> qy_2hhjkshList = reviewedRuleService.findRule(reviewedRule);
			model.addAttribute("qy_2hhjkshList", qy_2hhjkshList);

			reviewedRule.setReviewedSheet("4-2");
			reviewedRule.setTaskDefKey("utask_dqfkzysh");
			List<ReviewedRule> dq_2hhjkshList = reviewedRuleService.findRule(reviewedRule);
			model.addAttribute("dq_2hhjkshList", dq_2hhjkshList);
		}

		{
			reviewedRule.setReviewedSheet("5");
			reviewedRule.setTaskDefKey("utask_fgsfksh");
			List<ReviewedRule> fg_fcdyjkshList = reviewedRuleService.findRule(reviewedRule);
			model.addAttribute("fg_fcdyjkshList", fg_fcdyjkshList);

			reviewedRule.setReviewedSheet("5");
			reviewedRule.setTaskDefKey("utask_qyfksh");
			List<ReviewedRule> qy_fcdyjkshList = reviewedRuleService.findRule(reviewedRule);
			model.addAttribute("qy_fcdyjkshList", qy_fcdyjkshList);

			reviewedRule.setReviewedSheet("5");
			reviewedRule.setTaskDefKey("utask_dqfkzysh");
			List<ReviewedRule> dq_fcdyjkshList = reviewedRuleService.findRule(reviewedRule);
			model.addAttribute("dq_fcdyjkshList", dq_fcdyjkshList);
		}

		{
			reviewedRule.setReviewedSheet("6");
			reviewedRule.setTaskDefKey("utask_fgsfksh");
			List<ReviewedRule> fg_fcdzyjkshList = reviewedRuleService.findRule(reviewedRule);
			model.addAttribute("fg_fcdzyjkshList", fg_fcdzyjkshList);

			reviewedRule.setReviewedSheet("6");
			reviewedRule.setTaskDefKey("utask_qyfksh");
			List<ReviewedRule> qy_fcdzyjkshList = reviewedRuleService.findRule(reviewedRule);
			model.addAttribute("qy_fcdzyjkshList", qy_fcdzyjkshList);

			reviewedRule.setReviewedSheet("6");
			reviewedRule.setTaskDefKey("utask_dqfkzysh");
			List<ReviewedRule> dq_fcdzyjkshList = reviewedRuleService.findRule(reviewedRule);
			model.addAttribute("dq_fcdzyjkshList", dq_fcdzyjkshList);
		}

		if (Constants.UTASK_FGSFKSH.equals(taskDefKey) || Constants.UTASK_QYFKSH.equals(taskDefKey)
				|| Constants.UTASK_DQFKSH.equals(taskDefKey)) {
			model.addAttribute("checkReviewedId", "true");
		}
		if (Constants.UTASK_QYFKSH.equals(taskDefKey) || Constants.UTASK_QYJLSH.equals(taskDefKey)) {
			return "app/credit/reviewedRule/reviewedRuleBList";
		} else if (Constants.UTASK_FGSFKSH.equals(taskDefKey) || Constants.UTASK_FGSJLSH.equals(taskDefKey)) {
			return "app/credit/reviewedRule/reviewedRuleAList";
		} else {
			return "app/credit/reviewedRule/reviewedRuleCList";

		}
	}

	@RequestMapping(value = "saveRule", method = { RequestMethod.POST })
	@ResponseBody
	public AjaxView saveRule(@RequestBody List<ReviewedRule> reviewedRulelst, String taskDefKey, String applyNo, String sheetId) {
		AjaxView ajaxView = new AjaxView();
		try {
			String officeId = UserUtils.getUser().getOffice().getId();
			Map<String, String> maps = Maps.newHashMap();
			maps.put("officeId", officeId);
			maps.put("taskDefKey", taskDefKey);
			maps.put("applyNo", applyNo);
			maps.put("sheetId", sheetId);
			reviewedRuleService.saveRule(reviewedRulelst, maps);
			ajaxView.setSuccess().setMessage("保存黑名单规则成功");
		} catch (Exception e) {
			logger.error("保存黑名单规则失败！", e);
			ajaxView.setFailed().setMessage("保存黑名单规则失败！");
		}
		return ajaxView;
	}

}