package com.resoft.credit.supportDecision.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Maps;
import com.resoft.common.utils.Constants;
import com.resoft.common.utils.JsonTransferUtils;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.financialStateImport.entity.ThemisReportDic;
import com.resoft.credit.financialStateImport.entity.ThemisReturnReview;
import com.resoft.credit.financialStateImport.entity.ThemisReturnScore;
import com.resoft.credit.financialStateImport.service.ThemisReportDicService;
import com.resoft.credit.supportDecision.entity.ThemisReturnScoreList;
import com.resoft.credit.supportDecision.service.SupportDecisionService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 支持决策Controller
 * 开发中
 * @author wuxi01
 * 
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/supportDecision")
public class SupportDecisionController extends BaseController {

	@Autowired
	private SupportDecisionService supportDecisionService;

	@Autowired
	private ThemisReportDicService themisReportDicService;

	@RequiresPermissions("credit:supportDecision:view")
	@RequestMapping(value = "index")
	public String index(ActTaskParam actTaskParam, Model model) {
		String applyNo = actTaskParam.getApplyNo();
		model.addAttribute("applyNo", applyNo);
		return "/app/credit/supportDecision/supportDecisionIndex";
	}

	@RequiresPermissions("credit:supportDecision:view")
	@RequestMapping(value = "themisRatingScore")
	public String themisRatingScore(String applyNo, Model model) {
		try {
			ThemisReturnScore themisReturnScore = new ThemisReturnScore();
			themisReturnScore.setApplyNo(applyNo);
			// 查询条件
			Map<String, String> params = Maps.newConcurrentMap();
			try {
				params.put("applyNo", applyNo);
			} catch (Exception e) {
				logger.error("系统参数出现异常，请联系管理员！");
				model.addAttribute("themisReturnScoreMessage", "系统参数出现异常，请联系管理员！");
				return "/app/credit/supportDecision/themisRatingScore";
			}
			// 所有年月
			List<String> reportYearMonthList = supportDecisionService.findDistinctReportYearMonth(params);
			// 列集合
			List<ThemisReturnScoreList> trsListForCol = new ArrayList<ThemisReturnScoreList>();
			for (int i = 0; i < reportYearMonthList.size(); i++) {
				// 3.根据财报年月、进件号查询一列
				ThemisReturnScoreList themisReturnScoreList = new ThemisReturnScoreList();
				themisReturnScoreList.setReportYearMonth(reportYearMonthList.get(i));
				themisReturnScore.setReportYearMonth(reportYearMonthList.get(i));
				themisReturnScoreList.setThemisReturnScoreList(supportDecisionService.findThemisReturnScoreList(themisReturnScore));
				trsListForCol.add(themisReturnScoreList);
			}
			// 列集合转化为Json字符串
			String trsListForColJson = JsonTransferUtils.bean2Json(trsListForCol);
			model.addAttribute("trsListForColJson", trsListForColJson);
		} catch (Exception e) {
			logger.error("流程发生异常，请联系管理员！", e);
			model.addAttribute("themisReturnScoreMessage", "流程发生异常，请联系管理员！");
			return "/app/credit/supportDecision/themisRatingScore";
		}
		// 指标字典集合
		try {
			List<ThemisReportDic> themisReportDicList = themisReportDicService.findList(new ThemisReportDic(Constants.THEMIS_RETURN_SCORE));
			String themisReportDicListJson = JsonTransferUtils.bean2Json(themisReportDicList);
			model.addAttribute("themisReportDicListJson", themisReportDicListJson);
			model.addAttribute("themisReportDicList", themisReportDicList);
		} catch (IOException e) {
			logger.error("Themis字典数据发生异常，请联系管理员！", e);
			model.addAttribute("themisReturnScoreMessage", "Themis字典数据发生异常，请联系管理员！");
			return "/app/credit/supportDecision/themisRatingScore";
		}
		return "/app/credit/supportDecision/themisRatingScore";
	}

	@RequiresPermissions("credit:supportDecision:view")
	@RequestMapping(value = "reviewIndex")
	public String reviewIndex(String applyNo, Model model) {
		try {
			// 查询条件
			Map<String, String> params = Maps.newConcurrentMap();
			try {
				params.put("applyNo", applyNo);
				model.addAttribute("applyNo", applyNo);
			} catch (Exception e) {
				logger.error("系统参数出现异常，请联系管理员！");
				model.addAttribute("themisReturnScoreMessage", "系统参数出现异常，请联系管理员！");
				return "/app/credit/supportDecision/themisRatingScore";
			}
			// 所有年月
			List<String> reportYearMonthList = supportDecisionService.findDistinctReportYearMonth(params);
			String reportYearMonthJson = JsonTransferUtils.bean2Json(reportYearMonthList);
			model.addAttribute("reportYearMonthJson", reportYearMonthJson);
			model.addAttribute("reportYearMonthList", reportYearMonthList);
		} catch (Exception e) {
			logger.error("流程发生异常，请联系管理员！", e);
			model.addAttribute("message", "流程发生异常，请联系管理员！");
			return "/app/credit/supportDecision/themisReturnReviewIndex";
		}
		return "/app/credit/supportDecision/themisReturnReviewIndex";
	}

	@RequiresPermissions("credit:supportDecision:view")
	@RequestMapping(value = "showSheet")
	public String showSheet(String applyNo, String reportYearMonth, Model model) {
		// 字典信息
		List<ThemisReportDic> themisReviewIndexList = themisReportDicService.findList(new ThemisReportDic(Constants.THEMIS_RETURN_REVIEW));
//		try {
//			String themisReviewIndexListJson = JsonTransferUtils.bean2Json(themisReviewIndexList);
//			model.addAttribute("themisReviewIndexListJson", themisReviewIndexListJson);
//			model.addAttribute("themisReviewIndexList", themisReviewIndexList);
//		} catch (IOException e) {
//			logger.error("Themis字典数据发生异常，请联系管理员！", e);
//			model.addAttribute("themisReturnReviewMessage", "Themis字典数据发生异常，请联系管理员！");
//			return "/app/credit/supportDecision/themisReturnReviewList";
//		}
		// 分析信息
		if (StringUtils.isNotBlank(applyNo) && StringUtils.isNotBlank(reportYearMonth)) {
			// 根据财报年月和进件号查询
			ThemisReturnReview themisReturnReview = new ThemisReturnReview();
			themisReturnReview.setApplyNo(applyNo);
			themisReturnReview.setReportYearMonth(reportYearMonth);
			List<ThemisReturnReview> themisReturnReviewList = supportDecisionService.findThemisReturnReviewList(themisReturnReview);
		
			if (themisReturnReviewList != null) {
				for(int i = 0 ;i<themisReturnReviewList.size();i++){
					themisReturnReviewList.get(i).setReturnIndexName (themisReviewIndexList.get(i).getReportIndexName());
				}
				model.addAttribute("themisReturnReviewList", themisReturnReviewList);
//				if (themisReturnReviewList.size() == themisReviewIndexList.size()) {
//					model.addAttribute("themisReturnReviewList", themisReturnReviewList);
//				} else {
//					model.addAttribute("themisReturnReviewMessage", "系统数据出现错误，请联系管理员！");
//				}
			} else {
				model.addAttribute("themisReturnReviewMessage", "暂无专家分析信息！");
			}
		} else {
			return "/app/credit/supportDecision/themisReturnReviewList";
		}
		return "/app/credit/supportDecision/themisReturnReviewList";
	}
	
}
