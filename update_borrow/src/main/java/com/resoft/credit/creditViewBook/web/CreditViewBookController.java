package com.resoft.credit.creditViewBook.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.resoft.common.utils.AjaxView;
import com.resoft.common.utils.Constants;
import com.resoft.credit.applyInfo.entity.ApplyInfo;
import com.resoft.credit.applyInfo.service.ApplyInfoService;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.applyRegister.entity.ApplyRegister;
import com.resoft.credit.applyRegister.service.ApplyRegisterService;
import com.resoft.credit.checkApprove.entity.CheckApprove;
import com.resoft.credit.checkApprove.service.CheckApproveService;
import com.resoft.credit.checkFee.entity.CheckFee;
import com.resoft.credit.creditAndLine.entity.creditAnalysis.CreditAnalysis;
import com.resoft.credit.creditAndLine.service.creditAnalysis.CreditAnalysisService;
import com.resoft.credit.creditAndLine.service.creditAnalysisExtend.CreditAnalysisExtendService;
import com.resoft.credit.creditAndLine.service.creditAnalysisMostExtend.CreditAnalysisMostExtendService;
import com.resoft.credit.creditAndLine.service.creditAnalysisMostExtends.CreditAnalysisMostExtendsService;
import com.resoft.credit.creditViewBook.entity.CreditViewBook.CreditViewBook;
import com.resoft.credit.creditViewBook.entity.creditAssets.CreditAssets;
import com.resoft.credit.creditViewBook.entity.creditOtherInfo.CreditOtherInfo;
import com.resoft.credit.creditViewBook.service.creditOtherInfo.CreditOtherInfoService;
import com.resoft.credit.creditViewBook.service.creditViewBook.CreditViewBookService;
import com.resoft.credit.processSuggestionInfo.entity.ProcessSuggestionInfo;
import com.resoft.credit.processSuggestionInfo.service.ProcessSuggestionInfoService;
import com.resoft.credit.stockTaskReceive.entity.StockTaskReceive;
import com.resoft.credit.stockTaskReceive.service.StockTaskReceiveService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 信审意见书Controller
 * 
 * @author wuxi01
 * @version 2016-02-29
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/creditViewBook")
public class CreditViewBookController extends BaseController {

	@Autowired
	private ApplyRegisterService applyRegisterService;
	
	@Autowired
	private ApplyInfoService applyInfoService;
	
	@Autowired
	private CreditAnalysisExtendService creditAnalysisExtendService;
	
	@Autowired
	private CreditAnalysisMostExtendsService creditAnalysisMostExtendsService;
	
	@Autowired
	private CreditAnalysisMostExtendService creditAnalysisMostExtendService;

	@Autowired
	private ProcessSuggestionInfoService processSuggestionInfoService;

	@Autowired
	private CreditViewBookService creditViewBookService;

	@Autowired
	private CreditOtherInfoService creditOtherInfoService;

	@Autowired
	private CreditAnalysisService creditAnalysisService;
	
	@Autowired
	private CheckApproveService checkApproveService;
	@Autowired
	private StockTaskReceiveService stockTaskReceiveService;

	@RequiresPermissions("credit:creditViewBook")
	@RequestMapping(value = { "load", "" })
	public String load(String controGZ,ActTaskParam actTaskParam, String readOnly, HttpServletRequest request, HttpServletResponse response, Model model, ApplyInfo applyInfo, CheckFee checkFee, CreditAssets creditAssets, CreditOtherInfo creditOtherInfo,CreditAnalysis creditAnalysis, ProcessSuggestionInfo processSuggestionInfo, CreditViewBook creditViewBook) {
		String showPage=null;
		try {
			String applyNo = actTaskParam.getApplyNo();
			//申请信息在设定日期前就不验证
			ApplyRegister applyRegister= applyRegisterService.getApplyRegisterByApplyNo(applyNo);
			Date createDate = applyRegister.getRegisterDate();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		    Date dateFlag = sdf.parse("2017-8-30 00:00:00"); 
		    boolean isbeforer = createDate.before(dateFlag);
		    if(isbeforer){
		    	showPage = "app/credit/creditViewBook/creditViewBookIndexOld";
			}else{
				showPage = "app/credit/creditViewBook/creditViewBookIndex";
		    	//新增的信息
		    	creditViewBook.setCreditAnalysisExtend(creditAnalysisExtendService.getCreditAnalysisExtendByApplyNo(applyNo));
				creditViewBook.setCreditAnalysisMostExtend(creditAnalysisMostExtendService.getCreditAnalysisMostExtendByApplyNo(applyNo));
				creditViewBook.setCreditAnalysisMostExtends(creditAnalysisMostExtendsService.getCreditAnalysisMostExtendsByApplyNo(applyNo));
			}
			model.addAttribute("controGZ", controGZ);
			model.addAttribute("showStatus", request.getParameter("showStatus"));
			model.addAttribute("readOnly", readOnly);
			// 综合意见部分需要的数据
			Map<String, Object> comprehensiveOpinionMap = Maps.newHashMap();

			Map<String, String> params = Maps.newHashMap();

			// 1.申请信息部分查询出的数据
			applyInfo = applyInfoService.findApplyInfoByApplyNo(applyNo);

			model.addAttribute("applyInfo", applyInfo);

			// 2.综合信息部分查询出的数据
			// 查询两人外访意见
			params.put("applyNo", applyNo);
			params.put("taskDefKey", Constants.UTASK_LRWF1);
			ProcessSuggestionInfo processSuggestionInfoVisitor1 = processSuggestionInfoService.findByApplyNo(params);
			if(processSuggestionInfoVisitor1 != null){
				comprehensiveOpinionMap.put("visitor1", processSuggestionInfoVisitor1.getCreateBy().getLoginName());
				comprehensiveOpinionMap.put("suggession1", processSuggestionInfoVisitor1.getSuggestionDesc());
			}


			params.put("applyNo", applyNo);
			params.put("taskDefKey", Constants.UTASK_LRWF2);
			ProcessSuggestionInfo processSuggestionInfoVisitor2 = processSuggestionInfoService.findByApplyNo(params);
			if(processSuggestionInfoVisitor2 != null){
				comprehensiveOpinionMap.put("visitor2", processSuggestionInfoVisitor2.getCreateBy().getLoginName());
				comprehensiveOpinionMap.put("suggession2", processSuggestionInfoVisitor2.getSuggestionDesc());
			}else if(processSuggestionInfoVisitor1 != null){
				comprehensiveOpinionMap.put("visitor2", processSuggestionInfoVisitor1.getCreateBy().getLoginName());
				comprehensiveOpinionMap.put("suggession2", processSuggestionInfoVisitor1.getSuggestionDesc());
			}

			// 查询网查信息
			params.put("applyNo", applyNo);
			params.put("taskDefKey", Constants.UTASK_WC);
			ProcessSuggestionInfo processSuggestionInfoWeb = processSuggestionInfoService.findByApplyNo(params);
			if(processSuggestionInfoWeb != null){
				comprehensiveOpinionMap.put("webIsAbnormal", processSuggestionInfoWeb.getIsAbnormal());
				comprehensiveOpinionMap.put("webSuggession", processSuggestionInfoWeb.getSuggestionDesc());
			}

			// 查询电话核查信息
			params.put("applyNo", applyNo);
			params.put("taskDefKey", Constants.UTASK_DHHC);
			ProcessSuggestionInfo processSuggestionInfoPhone = processSuggestionInfoService.findByApplyNo(params);
			if(processSuggestionInfoPhone != null){
				comprehensiveOpinionMap.put("phoneIsAbnormal", processSuggestionInfoPhone.getIsAbnormal());
				comprehensiveOpinionMap.put("phoneSuggession", processSuggestionInfoPhone.getSuggestionDesc());
			}

			model.addAttribute("comprehensiveOpinionMap", comprehensiveOpinionMap);

			// 3.查询其他信息
			creditViewBook.setCreditOtherInfo(creditOtherInfoService.getCreditOtherInfoByApplyNo(applyNo));
			
			// 4.查询分析信息
			creditViewBook.setCreditAnalysis(creditAnalysisService.getCreditAnalysisByApplyNo(applyNo));

			// 5.查询审批信息
			params.put("applyNo", actTaskParam.getApplyNo());

			//信审意见书审批意见
			params.put("taskDefKey", Constants.UTASK_FGSFKSH);
	    	processSuggestionInfo = processSuggestionInfoService.findByApplyNo(params);
	    	if(processSuggestionInfo!=null){
	    		creditViewBook.setSuggestionBranch(processSuggestionInfo.getCreditDesc());
	    	}
	    	
			params.put("taskDefKey", Constants.UTASK_QYFKSH);
	    	processSuggestionInfo = processSuggestionInfoService.findByApplyNo(params);
	    	if(processSuggestionInfo!=null){
	    		creditViewBook.setSuggestionArea(processSuggestionInfo.getCreditDesc());
	    	}
	    	
	    	params.put("taskDefKey", Constants.UTASK_DQFKSH);
	    	processSuggestionInfo = processSuggestionInfoService.findByApplyNo(params);
	    	if(processSuggestionInfo!=null){
	    		creditViewBook.setSuggestionLargeArea(processSuggestionInfo.getCreditDesc());
	    	}
	    	
	    	params.put("taskDefKey", Constants.UTASK_ZGSFKSH);
	    	processSuggestionInfo = processSuggestionInfoService.findByApplyNo(params);
	    	if(processSuggestionInfo!=null){
	    		creditViewBook.setSuggestionHead(processSuggestionInfo.getCreditDesc());
	    	}
	        /*}*/
	        
	    	model.addAttribute("readOnly", readOnly);
			model.addAttribute("taskDefKeyFlag", actTaskParam.getTaskDefKey());
			model.addAttribute("creditViewBook", creditViewBook);
			model.addAttribute("actTaskParam", actTaskParam);
			
			//需要判断有无批复信息
			CheckApprove checkApprove = new CheckApprove();
			Map<String, String> paramCheckApprove = new HashMap<String, String>();
			paramCheckApprove.put("applyNo", actTaskParam.getApplyNo());
			List<CheckApprove> checkApproveList = checkApproveService.getCheckApproveByApplyNo(paramCheckApprove);
			if (checkApproveList.size() > 0) {
				checkApprove = checkApproveList.get(0);
			} else {
				checkApprove = new CheckApprove();
			}
			model.addAttribute("checkApprove", checkApprove);
			//显示产品分类
			String productCategoryValue=applyRegister.getProductCategory();
			String productCategoryKey=DictUtils.getDictLabel(productCategoryValue, "product_category", null);
			model.addAttribute("productCategoryKey", productCategoryKey);
			model.addAttribute("productCategoryValue", productCategoryValue);
			queryIsExistStock(actTaskParam.getTaskDefKey(),applyNo,model);
		} catch (Exception e) {
			logger.error("查询信审意见书失败",e);
		}
		return showPage;
	}

	//查询是否选择了估值岗
	private void queryIsExistStock(String taskDefKey,String applyNo,Model model){
		String grade = null;
		if(Constants.UTASK_FGSFKSH.equals(taskDefKey)){
			grade = "4";
		}else if(Constants.UTASK_QYFKSH.equals(taskDefKey)){
			grade = "3";
		}else if(Constants.UTASK_DQFKSH.equals(taskDefKey)){
			grade = "2";
		}else if(Constants.UTASK_ZGSFKSH.equals(taskDefKey)){
			grade = "1";
		}
		if(grade != null){
			StockTaskReceive stockTaskReceive = stockTaskReceiveService.getReceiveByApplyNoAndGrade(applyNo, grade);
			if(stockTaskReceive != null){
				if((stockTaskReceive.getIsStockPost()==null) || (stockTaskReceive.getIsStockPost().intValue()==0)){
					model.addAttribute("isExistStock", "2");
				}else{
					model.addAttribute("isExistStock", "1");
				}
			}else{
				model.addAttribute("isExistStock", "0");
			}
		}
	}
	
	@ResponseBody
	@RequiresPermissions("credit:creditViewBook:edit")
	@RequestMapping(value = "save")
	public AjaxView save(CreditViewBook creditViewBook, String readOnly, Model model, HttpServletRequest request,ActTaskParam actTaskParam,HttpServletResponse response,ApplyInfo applyInfo,CheckFee checkFee,CreditAssets creditAssets,CreditOtherInfo creditOtherInfo,CreditAnalysis creditAnalysis,ProcessSuggestionInfo processSuggestionInfo) {
		AjaxView ajaxView = new AjaxView();  
		try{
			creditViewBook=purSave(creditViewBook);
			creditViewBookService.save(creditViewBook);
			ajaxView.setSuccess().setMessage("保存数据成功！");
		}catch(Exception e){
			logger.error("保存数据错误" + e.getMessage(), e);
			ajaxView.setFailed().setMessage("保存数据错误!");
		}
		return ajaxView;
	}
	
	
	private CreditViewBook purSave(CreditViewBook creditViewBook) throws ParseException {
		if(StringUtils.isNotBlank(creditViewBook.getSuggestionBranch())){
			String suggestionBranch = creditViewBook.getSuggestionBranch().trim().replaceAll("&nbsp;"," ").replaceAll("<br/>","\r").replaceAll("\r\n", "\n");
			creditViewBook.setSuggestionBranch(suggestionBranch);
		}
		if(StringUtils.isNotBlank(creditViewBook.getSuggestionArea())){
			String suggestionArea = creditViewBook.getSuggestionArea().trim().replaceAll("&nbsp;"," ").replaceAll("<br/>","\r").replaceAll("\r\n", "\n");
			creditViewBook.setSuggestionArea(suggestionArea);
		}
		if(StringUtils.isNotBlank(creditViewBook.getSuggestionLargeArea())){
			String suggestionLargeArea = creditViewBook.getSuggestionLargeArea().trim().replaceAll("&nbsp;"," ").replaceAll("<br/>","\r").replaceAll("\r\n", "\n");
			creditViewBook.setSuggestionLargeArea(suggestionLargeArea);
		}
		if(StringUtils.isNotBlank(creditViewBook.getSuggestionHead())){
			String suggestionHead = creditViewBook.getSuggestionHead().trim().replaceAll("&nbsp;"," ").replaceAll("<br/>","\r").replaceAll("\r\n", "\n");
			creditViewBook.setSuggestionHead(suggestionHead);
		}
		
		ApplyRegister applyRegister= applyRegisterService.getApplyRegisterByApplyNo(creditViewBook.getApplyNo());
		Date createDate = applyRegister.getRegisterDate();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    Date dateFlag = sdf.parse("2017-8-30 00:00:00"); 
	    boolean isbeforer = createDate.before(dateFlag);
	    if(isbeforer){//jiu de 
	    	String loanPurposeDesc = creditViewBook.getCreditAnalysis().getLoanPurposeDesc().trim().replaceAll("&nbsp;"," ").replaceAll("<br/>","\r").replaceAll("\r\n", "\n");
	    	String companyDesc = creditViewBook.getCreditAnalysis().getCompanyDesc().trim().replaceAll("&nbsp;"," ").replaceAll("<br/>","\r").replaceAll("\r\n", "\n");
			String loanRepayDesc = creditViewBook.getCreditAnalysis().getLoanRepayDesc().trim().replaceAll("&nbsp;"," ").replaceAll("<br/>","\r").replaceAll("\r\n", "\n");
			String guaranteeDesc = creditViewBook.getCreditAnalysis().getGuaranteeDesc().trim().replaceAll("&nbsp;"," ").replaceAll("<br/>","\r").replaceAll("\r\n", "\n");
			String riskPoint = creditViewBook.getCreditAnalysis().getRiskPoint().trim().replaceAll("&nbsp;"," ").replaceAll("<br/>","\r").replaceAll("\r\n", "\n");
			String edgeComment = creditViewBook.getCreditAnalysis().getEdgeComment().trim().replaceAll("&nbsp;"," ").replaceAll("<br/>","\r").replaceAll("\r\n", "\n");
			creditViewBook.getCreditAnalysis().setLoanPurposeDesc(loanPurposeDesc);
			creditViewBook.getCreditAnalysis().setCompanyDesc(companyDesc);
			creditViewBook.getCreditAnalysis().setLoanRepayDesc(loanRepayDesc);
			creditViewBook.getCreditAnalysis().setGuaranteeDesc(guaranteeDesc);
			creditViewBook.getCreditAnalysis().setRiskPoint(riskPoint);
			creditViewBook.getCreditAnalysis().setEdgeComment(edgeComment);
	    }else{//xin de
	    	String turnoverProfit = creditViewBook.getCreditAnalysisExtend().getTurnoverProfit().trim().replaceAll("&nbsp;"," ").replaceAll("<br/>","\r").replaceAll("\r\n", "\n");
			String tfBasis = creditViewBook.getCreditAnalysisExtend().getTfBasis().trim().replaceAll("&nbsp;"," ").replaceAll("<br/>","\r").replaceAll("\r\n", "\n");
			String staffNumberDesc = creditViewBook.getCreditAnalysisExtend().getStaffNumberDesc().trim().replaceAll("&nbsp;"," ").replaceAll("<br/>","\r").replaceAll("\r\n", "\n");
			String sndBasis = creditViewBook.getCreditAnalysisExtend().getSndBasis().trim().replaceAll("&nbsp;"," ").replaceAll("<br/>","\r").replaceAll("\r\n", "\n");
			String saleProfitDesc = creditViewBook.getCreditAnalysisExtend().getSaleProfitDesc().trim().replaceAll("&nbsp;"," ").replaceAll("<br/>","\r").replaceAll("\r\n", "\n");
			String spdBasis = creditViewBook.getCreditAnalysisExtend().getSpdBasis().trim().replaceAll("&nbsp;"," ").replaceAll("<br/>","\r").replaceAll("\r\n", "\n");
			String companyDebt = creditViewBook.getCreditAnalysisExtend().getCompanyDebt().trim().replaceAll("&nbsp;"," ").replaceAll("<br/>","\r").replaceAll("\r\n", "\n");
			String cdBasis = creditViewBook.getCreditAnalysisExtend().getCdBasis().trim().replaceAll("&nbsp;"," ").replaceAll("<br/>","\r").replaceAll("\r\n", "\n");
											
			String coreValue = creditViewBook.getCreditAnalysisMostExtends().getCoreValue().trim().replaceAll("&nbsp;"," ").replaceAll("<br/>","\r").replaceAll("\r\n", "\n");
			String coreAdvantage = creditViewBook.getCreditAnalysisMostExtends().getCoreAdvantage().trim().replaceAll("&nbsp;"," ").replaceAll("<br/>","\r").replaceAll("\r\n", "\n");
			String coreGuarantee = creditViewBook.getCreditAnalysisMostExtends().getCoreGuarantee().trim().replaceAll("&nbsp;"," ").replaceAll("<br/>","\r").replaceAll("\r\n", "\n");
			String coreMeasures = creditViewBook.getCreditAnalysisMostExtends().getCoreMeasures().trim().replaceAll("&nbsp;"," ").replaceAll("<br/>","\r").replaceAll("\r\n", "\n");
			
			String applicationDetails = creditViewBook.getCreditAnalysisMostExtend().getApplicationDetails().trim().replaceAll("&nbsp;"," ").replaceAll("<br/>","\r").replaceAll("\r\n", "\n");
			String verifyMethod = creditViewBook.getCreditAnalysisMostExtend().getVerifyMethod().trim().replaceAll("&nbsp;"," ").replaceAll("<br/>","\r").replaceAll("\r\n", "\n");
			String verifyBasis = creditViewBook.getCreditAnalysisMostExtend().getVerifyBasis().trim().replaceAll("&nbsp;"," ").replaceAll("<br/>","\r").replaceAll("\r\n", "\n");
			String repaymentSourceBasis = creditViewBook.getCreditAnalysisMostExtend().getRepaymentSourceBasis().trim().replaceAll("&nbsp;"," ").replaceAll("<br/>","\r").replaceAll("\r\n", "\n");
			String secondRepaymentSource = creditViewBook.getCreditAnalysisMostExtend().getSecondRepaymentSource().trim().replaceAll("&nbsp;"," ").replaceAll("<br/>","\r").replaceAll("\r\n", "\n");
			String otherRepaymentSource = creditViewBook.getCreditAnalysisMostExtend().getOtherRepaymentSource().trim().replaceAll("&nbsp;"," ").replaceAll("<br/>","\r").replaceAll("\r\n", "\n");
			
			String policyRiskAnalysis = creditViewBook.getCreditAnalysisMostExtends().getPolicyRiskAnalysis().trim().replaceAll("&nbsp;"," ").replaceAll("<br/>","\r").replaceAll("\r\n", "\n");
			String operateRiskAnalysis = creditViewBook.getCreditAnalysisMostExtends().getOperateRiskAnalysis().trim().replaceAll("&nbsp;"," ").replaceAll("<br/>","\r").replaceAll("\r\n", "\n");
			String creditRiskAnalysis = creditViewBook.getCreditAnalysisMostExtends().getCreditRiskAnalysis().trim().replaceAll("&nbsp;"," ").replaceAll("<br/>","\r").replaceAll("\r\n", "\n");
			String unexpectedRiskAnalysis = creditViewBook.getCreditAnalysisMostExtends().getUnexpectedRiskAnalysis().trim().replaceAll("&nbsp;"," ").replaceAll("<br/>","\r").replaceAll("\r\n", "\n");
			
			String guaranteeDetail = creditViewBook.getCreditAnalysisMostExtend().getGuaranteeDetail().trim().replaceAll("&nbsp;"," ").replaceAll("<br/>","\r").replaceAll("\r\n", "\n");
			String guaranteeCorporation = creditViewBook.getCreditAnalysisMostExtend().getGuaranteeCorporation().trim().replaceAll("&nbsp;"," ").replaceAll("<br/>","\r").replaceAll("\r\n", "\n");
			creditViewBook.getCreditAnalysisExtend().setTurnoverProfit(turnoverProfit);
			creditViewBook.getCreditAnalysisExtend().setTfBasis(tfBasis);
			creditViewBook.getCreditAnalysisExtend().setStaffNumberDesc(staffNumberDesc);
			creditViewBook.getCreditAnalysisExtend().setSndBasis(sndBasis);
			creditViewBook.getCreditAnalysisExtend().setSaleProfitDesc(saleProfitDesc);
			creditViewBook.getCreditAnalysisExtend().setSpdBasis(spdBasis);
			creditViewBook.getCreditAnalysisExtend().setCompanyDebt(companyDebt);
			creditViewBook.getCreditAnalysisExtend().setCdBasis(cdBasis);
			creditViewBook.getCreditAnalysisMostExtends().setCoreValue(coreValue);
			creditViewBook.getCreditAnalysisMostExtends().setCoreAdvantage(coreAdvantage);
			creditViewBook.getCreditAnalysisMostExtends().setCoreGuarantee(coreGuarantee);
			creditViewBook.getCreditAnalysisMostExtends().setCoreMeasures(coreMeasures);
			creditViewBook.getCreditAnalysisMostExtend().setApplicationDetails(applicationDetails);
			creditViewBook.getCreditAnalysisMostExtend().setVerifyMethod(verifyMethod);
			creditViewBook.getCreditAnalysisMostExtend().setVerifyBasis(verifyBasis);
			creditViewBook.getCreditAnalysisMostExtend().setRepaymentSourceBasis(repaymentSourceBasis);
			creditViewBook.getCreditAnalysisMostExtend().setSecondRepaymentSource(secondRepaymentSource);
			creditViewBook.getCreditAnalysisMostExtend().setOtherRepaymentSource(otherRepaymentSource);
			creditViewBook.getCreditAnalysisMostExtends().setPolicyRiskAnalysis(policyRiskAnalysis);
			creditViewBook.getCreditAnalysisMostExtends().setOperateRiskAnalysis(operateRiskAnalysis);
			creditViewBook.getCreditAnalysisMostExtends().setCreditRiskAnalysis(creditRiskAnalysis);
			creditViewBook.getCreditAnalysisMostExtends().setUnexpectedRiskAnalysis(unexpectedRiskAnalysis);
			creditViewBook.getCreditAnalysisMostExtend().setGuaranteeDetail(guaranteeDetail);
			creditViewBook.getCreditAnalysisMostExtend().setGuaranteeCorporation(guaranteeCorporation);
	    }
		String categoryDesc = creditViewBook.getCreditAnalysis().getCategoryDesc().trim().replaceAll("&nbsp;"," ").replaceAll("<br/>","\r").replaceAll("\r\n", "\n");
		creditViewBook.getCreditAnalysis().setCategoryDesc(categoryDesc);
		return creditViewBook;
	}


}