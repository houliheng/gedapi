package com.resoft.credit.creditAndLine.web.creditAnalysis;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.resoft.common.utils.AjaxView;
import com.resoft.credit.creditAndLine.entity.creditAnalysis.CreditAnalysis;
import com.resoft.credit.creditAndLine.entity.creditAnalysisExtend.CreditAnalysisExtend;
import com.resoft.credit.creditAndLine.entity.creditAnalysisMostExtend.CreditAnalysisMostExtend;
import com.resoft.credit.creditAndLine.entity.creditAnalysisMostExtends.CreditAnalysisMostExtends;
import com.resoft.credit.creditAndLine.entity.creditCust.CreditCust;
import com.resoft.credit.creditAndLine.entity.creditLineBank.CreditLineBank;
import com.resoft.credit.creditAndLine.service.creditAnalysis.CreditAnalysisService;
import com.resoft.credit.creditAndLine.service.creditAnalysisExtend.CreditAnalysisExtendService;
import com.resoft.credit.creditAndLine.service.creditAnalysisMostExtend.CreditAnalysisMostExtendService;
import com.resoft.credit.creditAndLine.service.creditAnalysisMostExtends.CreditAnalysisMostExtendsService;
import com.resoft.credit.creditAndLine.service.creditCust.CreditCustService;
import com.resoft.credit.creditAndLine.service.creditLineBank.CreditLineBankService;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 征信分析信息详细Controller
 * 
 * @author wuxi01
 * @version 2016-02-26
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/creditAndLine/creditAnalysis")
public class CreditAnalysisController extends BaseController {

	@Autowired
	private CreditAnalysisService creditAnalysisService;
	
	@Autowired
	private CreditAnalysisExtendService creditAnalysisExtendService;
	
	@Autowired
	private CreditAnalysisMostExtendsService creditAnalysisMostExtendsService;
	
	@Autowired
	private CreditAnalysisMostExtendService creditAnalysisMostExtendService;
	
	@Autowired
	private CreditCustService creditCustService;
	
	@Autowired 
	private CreditLineBankService creditLineBankService;

	@ModelAttribute
	public CreditAnalysis get(@RequestParam(required = false) String id) {
		CreditAnalysis entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = creditAnalysisService.get(id);
		}
		if (entity == null) {
			entity = new CreditAnalysis();
		}
		return entity;
	}

	@RequiresPermissions("credit:creditAndLine:creditAnalysis:view")
	@RequestMapping(value = { "list", "" })
	public String list(CreditAnalysis creditAnalysis, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CreditAnalysis> page = creditAnalysisService.findPage(new Page<CreditAnalysis>(request, response), creditAnalysis);
		model.addAttribute("page", page);
		return "app/credit/creditAndLine/creditAnalysis/creditAnalysisList";
	}

	/**
	 * 征信及流水-分析信息
	 * 
	 * @param applyNo
	 * @param creditAnalysisId
	 * @param readOnly
	 * @param model
	 * @return
	 */
	@RequiresPermissions("credit:creditAndLine:creditAnalysis:view")
	@RequestMapping(value = "form")
	public String form(String applyNo, String readOnly, Model model) {
		CreditAnalysis creditAnalysis = creditAnalysisService.getCreditAnalysisByApplyNo(applyNo);
		if (creditAnalysis != null) {
			model.addAttribute("creditAnalysis", creditAnalysis);
		}
		model.addAttribute("applyNo", applyNo);
		model.addAttribute("readOnly", readOnly);
		return "app/credit/creditAndLine/creditAnalysis/creditAnalysisInfoForm";
	}

	/**
	 * 征信及流水-分析信息-保存
	 * 
	 * @param creditAnalysis
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("credit:creditAndLine:creditAnalysis:edit")
	@RequestMapping(value = "save")
	public AjaxView save(CreditAnalysis creditAnalysis) {
		AjaxView ajaxView = new AjaxView();
		try {
			creditAnalysisService.saveData(creditAnalysis);
			ajaxView.setSuccess().setMessage("保存分析信息成功！");
			ajaxView.put("id", creditAnalysis.getId());
		} catch (Exception e) {
			logger.error("保存分析信息失败！", e);
			ajaxView.setFailed().setMessage("保存分析信息失败！");
		}
		return ajaxView;
	}

	@RequiresPermissions("credit:creditAndLine:creditAnalysis:edit")
	@RequestMapping(value = "delete")
	public String delete(CreditAnalysis creditAnalysis, RedirectAttributes redirectAttributes) {
		creditAnalysisService.delete(creditAnalysis);
		
		CreditAnalysisExtend creditAnalysisExtend=new CreditAnalysisExtend();
		creditAnalysisExtend.setId(creditAnalysis.getId());
		creditAnalysisExtend.setApplyNo(creditAnalysis.getApplyNo());
		creditAnalysisExtendService.delete(creditAnalysisExtend);
		
		CreditAnalysisMostExtend creditAnalysisMostExtend = new CreditAnalysisMostExtend();
		creditAnalysisMostExtend.setId(creditAnalysis.getId());
		creditAnalysisMostExtend.setApplyNo(creditAnalysis.getApplyNo());
		creditAnalysisMostExtendService.delete(creditAnalysisMostExtend);
		
		CreditAnalysisMostExtends creditAnalysisMostExtends = new CreditAnalysisMostExtends();
		creditAnalysisMostExtends.setId(creditAnalysis.getId());
		creditAnalysisMostExtends.setApplyNo(creditAnalysis.getApplyNo());
		creditAnalysisMostExtendsService.delete(creditAnalysisMostExtends);
		
		addMessage(redirectAttributes, "删除征信分析信息详细成功");
		return "redirect:" + Global.getAdminPath() + "/creditAndLine/creditAnalysis/?repage";
	}

	@ResponseBody
	@RequiresPermissions("credit:creditAndLine:creditAnalysis:edit")
	@RequestMapping(value = "validateMandatoryMess")
	public AjaxView validateMandatoryMess(String applyNo){
		AjaxView ajaxView = new AjaxView();
		//1.银行卡
		CreditLineBank creditLineBank = new CreditLineBank();
		creditLineBank.setApplyNo(applyNo);
		List<CreditLineBank> creditLineBankList = creditLineBankService.findList(creditLineBank);
		if(creditLineBankList == null || creditLineBankList.size() == 0){
			ajaxView.setFailed().setMessage("请先录入银行卡流水信息！");
			return ajaxView;
		}
		//2.个人征信
		CreditCust creditCust = new CreditCust();
		creditCust.setApplyNo(applyNo);
		List<CreditCust> creditCustList = creditCustService.findList(creditCust);
		if(creditCustList == null || creditCustList.size() == 0){
			ajaxView.setFailed().setMessage("请先录入个人征信信息！");
			return ajaxView;
		}
		ajaxView.setSuccess();
		return ajaxView;
	}
}