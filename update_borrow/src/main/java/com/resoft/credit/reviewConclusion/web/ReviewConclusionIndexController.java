package com.resoft.credit.reviewConclusion.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Maps;
import com.resoft.common.utils.Constants;
import com.resoft.credit.applyInfo.entity.ApplyInfo;
import com.resoft.credit.applyInfo.service.ApplyInfoService;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.applyRegister.entity.ApplyRegister;
import com.resoft.credit.applyRegister.service.ApplyRegisterService;
import com.resoft.credit.checkApprove.entity.CheckApprove;
import com.resoft.credit.checkApprove.service.CheckApproveService;
import com.resoft.credit.processSuggestionInfo.entity.ProcessSuggestionInfo;
import com.resoft.credit.processSuggestionInfo.service.ProcessSuggestionInfoService;
import com.resoft.credit.rateInterest.entity.RateInterest;
import com.resoft.credit.rateInterest.service.RateInterestService;
import com.resoft.outinterface.utils.Facade;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 复议信息Controller
 * 
 * @author wuxi01
 * @version 2016-02-29
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/reviewConclusionIndex")
public class ReviewConclusionIndexController extends BaseController {

	@Autowired
	private ApplyInfoService applyInfoService;
	
	@Autowired
	private ApplyRegisterService applyRegisterService;
	
	@Autowired
	private CheckApproveService checkApproveService;
	
	@Autowired
	private ProcessSuggestionInfoService processSuggestionInfoService;
	
	@Autowired
	private RateInterestService rateInterestService;
	
	@RequiresPermissions("credit:reviewConclusionIndex")
	@RequestMapping(value = { "load", "" })
	public String load(ActTaskParam actTaskParam, String readOnly, Model model) {
		String applyNo = actTaskParam.getApplyNo();
		ApplyInfo applyInfo = applyInfoService.findApplyInfoByApplyNo(actTaskParam.getApplyNo());
		//从申请客户登记信息表表中查询客户的详细信息
		ApplyRegister applyRegister = applyRegisterService.getApplyRegisterByApplyNo(actTaskParam.getApplyNo());
		applyInfo.setApplyRegister(applyRegister);
		//根据申请编号查询批复信息,查询最后环节的批复信息
		Map<String, String> paramCheckApprove = Maps.newConcurrentMap();
		paramCheckApprove.put("applyNo", actTaskParam.getApplyNo());
		paramCheckApprove.put("taskDefKey", actTaskParam.getTaskDefKey());
		paramCheckApprove.put("processSequence", DictUtils.getDictValue(actTaskParam.getTaskDefKey(), "PROCESS_SEQUENCE", null));
		List<CheckApprove> checkApproveList = checkApproveService.getCheckApproveByApplyNo(paramCheckApprove);
		CheckApprove checkApprove = new CheckApprove();
		if(checkApproveList.size() > 0){
			checkApprove = checkApproveList.get(0);
		}
		//查询总公司批复意见
		Map<String, String> topComParam = Maps.newConcurrentMap();
		try {
			topComParam.put("applyNo", applyNo);
		} catch (Exception e) {
			logger.error("流程参数出现错误",e);
			model.addAttribute("message", "流程参数出现错误，请联系管理员！");
		}
		List<String> topComApproveSugg = processSuggestionInfoService.findTopComApproveSugg(topComParam);
		model.addAttribute("topComApproveSugg", topComApproveSugg);
		//预约意见
		Map<String, String> params = Maps.newConcurrentMap();
		params.put("applyNo", actTaskParam.getApplyNo());
		params.put("taskDefKey", actTaskParam.getTaskDefKey());
		ProcessSuggestionInfo processSuggestionInfo = processSuggestionInfoService.findByApplyNo(params);
		if (processSuggestionInfo == null) {
			processSuggestionInfo = new ProcessSuggestionInfo();
			processSuggestionInfo.setApplyNo(actTaskParam.getApplyNo());
			processSuggestionInfo.setTaskDefKey(actTaskParam.getTaskDefKey());
		}
		
		//根据申请的产品类型查询还款方式
		Map<String,String> param = new HashMap<String,String>();
		param.put("productType", checkApprove.getApproProductTypeCode());
		param.put("loanRepayType", checkApprove.getApproLoanRepayType());
		List<RateInterest> loanRepayTypeList = rateInterestService.getLoanRepayType(param);
		if(loanRepayTypeList.size() > 0){
			model.addAttribute("loanRepayTypeName", loanRepayTypeList.get(0).getLoanRepayDesc());
		}
				
		model.addAttribute("applyInfo", applyInfo);
		model.addAttribute("checkApprove", checkApprove);
		model.addAttribute("processSuggestionInfo", processSuggestionInfo);
		model.addAttribute("actTaskParam", actTaskParam);
		model.addAttribute("readOnly", readOnly);
		
		return "app/credit/reviewConclusion/reviewConclusionIndex";
	}
	
	/**
	 * 合同预约-预约信息-保存复议结论
	 * 
	 * @param processSuggestionInfo
	 * @param model
	 * @return
	 */
	@RequiresPermissions("credit:reviewConclusionIndex:edit")
	@RequestMapping(value = "saveReviewConclusion")
	public String saveReviewConclusion(ActTaskParam actTaskParam, ProcessSuggestionInfo processSuggestionInfo, HttpServletRequest request, Model model) {
		String readOnly = request.getParameter("readOnly");
		String flag = request.getParameter("sta");
		String passFlag = request.getParameter("passFlag");
		String blacklistRemarks = request.getParameter("blacklistRemarks");
		String suggestionDesc = request.getParameter("suggestionDesc");
		try {
			Map<String, String> processMap = Maps.newHashMap();
			processMap.put("suggestionDesc", suggestionDesc);
			processMap.put("passFlag", passFlag);
			processMap.put("flag", flag);
			processMap.put("blacklistRemarks", blacklistRemarks);
			processSuggestionInfoService.saveApproveSuggestion(actTaskParam, processMap,model);
			if("0".equals(flag) && "yes".equals(passFlag) && Constants.UTASK_FGSFY.equals(actTaskParam.getTaskDefKey())){
				Facade.facade.getExperianClient(actTaskParam.getApplyNo());
			}
			model.addAttribute("close", "close");
			model.addAttribute("flag", flag);
			model.addAttribute("passFlag", passFlag);
			model.addAttribute("reviewMessage", "意见保存成功！");
		} catch (Exception e) {
			logger.error("意见保存失败！", e);
			model.addAttribute("reviewMessage", "意见保存失败！");
		}
		return load(actTaskParam, readOnly, model);
	}

}