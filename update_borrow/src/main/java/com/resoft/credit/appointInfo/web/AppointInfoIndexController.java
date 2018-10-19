package com.resoft.credit.appointInfo.web;

import com.resoft.credit.checkApprove.utils.CheckApproveUtils;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.resoft.common.utils.AjaxView;
import com.resoft.common.utils.Constants;
import com.resoft.credit.CreGedBorrowStatus.service.CreGedBorrowStatusService;
import com.resoft.credit.GedAccount.entity.GedAccount;
import com.resoft.credit.GedAccount.service.GedAccountService;
import com.resoft.credit.GedCompanyAccount.entity.CreGedAccountCompany;
import com.resoft.credit.GedCompanyAccount.service.CreGedAccountCompanyService;
import com.resoft.credit.applyInfo.entity.ApplyInfo;
import com.resoft.credit.applyInfo.service.ApplyInfoService;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.applyRegister.entity.ApplyRegister;
import com.resoft.credit.applyRegister.service.ApplyRegisterService;
import com.resoft.credit.checkApprove.entity.CheckApprove;
import com.resoft.credit.checkApprove.service.CheckApproveService;
import com.resoft.credit.checkApproveUnion.entity.CheckApproveUnion;
import com.resoft.credit.checkApproveUnion.service.CheckApproveUnionService;
import com.resoft.credit.companyInfo.entity.CompanyInfo;
import com.resoft.credit.companyInfo.service.CompanyInfoService;
import com.resoft.credit.contract.service.ContractService;
import com.resoft.credit.custinfo.entity.CustInfo;
import com.resoft.credit.custinfo.service.CustInfoService;
import com.resoft.credit.guaranteeCompany.service.CreGuaranteeCompanyService;
import com.resoft.credit.guaranteeRelation.service.GuaranteeRelationService;
import com.resoft.credit.processSuggestionInfo.entity.ProcessSuggestionInfo;
import com.resoft.credit.processSuggestionInfo.service.ProcessSuggestionInfoService;
import com.resoft.credit.rateInterest.entity.RateInterest;
import com.resoft.credit.rateInterest.service.RateInterestService;
import com.resoft.outinterface.rest.ged.GedClient;
import com.resoft.outinterface.rest.newged.entity.AddOrderResponse;
import com.resoft.outinterface.rest.newged.entity.GedRegisterResponse;
import com.resoft.outinterface.utils.Facade;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 预约信息Controller
 * 
 * @author wuxi01
 * @version 2016-03-02
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/appointInfo")
public class AppointInfoIndexController extends BaseController {
	@Autowired
	private CreGedBorrowStatusService creGedBorrowStatusService;
	@Autowired
	private ApplyInfoService applyInfoService;
	@Autowired
	private CompanyInfoService companyInfoService;
	@Autowired
	private ApplyRegisterService applyRegisterService;
	@Autowired
	private CheckApproveService checkApproveService;
	@Autowired
	private ProcessSuggestionInfoService processSuggestionInfoService;
	@Autowired
	private ContractService contractService;
	@Autowired
	private RateInterestService rateInterestService;
	@Autowired
	private CheckApproveUnionService checkApproveUnionService;
	@Autowired
	private CustInfoService custInfoService;
	@Autowired 
	private GedAccountService gedAccountService;
	@Autowired
	private GuaranteeRelationService guaranteeRelationService;
	@Autowired
	private CreGuaranteeCompanyService creGuaranteeCompanyService;
	@Autowired
	private CreGedAccountCompanyService gedComAccountService;
	@RequiresPermissions("credit:appointInfo:edit")
	@RequestMapping(value = { "load", "" })
	public String load(ActTaskParam actTaskParam, String readOnly, Model model) {
		String applyNo = actTaskParam.getApplyNo();
		ApplyInfo applyInfo = applyInfoService.findApplyInfoByApplyNo(actTaskParam.getApplyNo());
		// 从申请客户登记信息表表中查询客户的详细信息
		ApplyRegister applyRegister = applyRegisterService.getApplyRegisterByApplyNo(actTaskParam.getApplyNo());
		String productCategoryKey=DictUtils.getDictLabel(applyRegister.getProductCategory() , "product_category", null);
		model.addAttribute("productCategoryKey",productCategoryKey);
		applyInfo.setApplyRegister(applyRegister);
		//采购字段显示
		String TypeCode = applyRegister.getApplyProductTypeCode();
		if(Constants.PRODUCT_TYPE_CG.equals(TypeCode)){//显示采购贷新增字段的标志
			model.addAttribute("showCgFlag", "1");
		}
		// 根据申请编号查询批复信息,查询最后环节的批复信息
		Map<String, String> paramCheckApprove = Maps.newConcurrentMap();
		paramCheckApprove.put("applyNo", actTaskParam.getApplyNo());
		List<CheckApprove> checkApproveList = checkApproveService.getCheckApproveByApplyNo(paramCheckApprove);
		CheckApprove checkApprove = new CheckApprove();
		if (checkApproveList.size() > 0) {
			checkApprove = checkApproveList.get(0);
		}
		String checkProductCategoryKey=DictUtils.getDictLabel(checkApprove.getProductCategory() , "product_category", null);
		model.addAttribute("checkProductCategoryKey",checkProductCategoryKey);
		// 查询总公司批复意见
		Map<String, String> topComSuggParams = Maps.newConcurrentMap();
		topComSuggParams.put("applyNo", applyNo);
		List<String> topComApproveSuggList = processSuggestionInfoService.findTopComApproveSugg(topComSuggParams);
		try {
			String topComApproveSugg = topComApproveSuggList.get(0);
			model.addAttribute("topComApproveSugg", topComApproveSugg);
		} catch (Exception e) {
			logger.warn("无总公司批复意见！");
		}
		// 预约意见
		Map<String, String> params = Maps.newConcurrentMap();
		params.put("applyNo", actTaskParam.getApplyNo());
		params.put("taskDefKey", actTaskParam.getTaskDefKey());
		ProcessSuggestionInfo processSuggestionInfo = processSuggestionInfoService.findByApplyNo(params);
		if (processSuggestionInfo == null) {
			processSuggestionInfo = new ProcessSuggestionInfo();
			processSuggestionInfo.setApplyNo(actTaskParam.getApplyNo());
			processSuggestionInfo.setTaskDefKey(actTaskParam.getTaskDefKey());
		}

		// 从cre_rate_interest中查询冠E通年利率
		Map<String, String> param = new HashMap<String, String>();
		param.put("loanRepayType", checkApprove.getApproLoanRepayType());
		param.put("periodValue", checkApprove.getApproPeriodValue());
		param.put("approProductTypeCode", checkApprove.getApproProductTypeCode());

		String interest = contractService.getInterest(param);
		BigDecimal interestD = new BigDecimal("0");
		if (interest != null && StringUtils.isNotEmpty(interest)) {
			interestD = new BigDecimal(interest).multiply(new BigDecimal("12"));
		}

		// 根据申请的产品类型查询还款方式
		Map<String, String> para = new HashMap<String, String>();
		para.put("productType", checkApprove.getApproProductTypeCode());
		List<RateInterest> loanRepayTypeList = rateInterestService.getLoanRepayType(para);
		model.addAttribute("loanRepayTypeList", loanRepayTypeList);
		//查询主借企业信息
		CompanyInfo companyMessage = new CompanyInfo();
		Map<String, Object> queryComParam = Maps.newHashMap();
		queryComParam.put("applyNo", actTaskParam.getApplyNo());
		queryComParam.put("roleType", Constants.ROLE_TYPE_ZJQY);
		List<CompanyInfo> comapnyList = companyInfoService.findListByParams(queryComParam);
		if (!comapnyList.isEmpty()) {
			companyMessage = comapnyList.get(0);
		}
		model.addAttribute("companyMessage",companyMessage);
		model.addAttribute("interest", interestD.toString());
		model.addAttribute("applyInfo", applyInfo);
		model.addAttribute("checkApprove", checkApprove);
		model.addAttribute("actTaskParam", actTaskParam);
		model.addAttribute("readOnly", readOnly);
		model.addAttribute("processSuggestionInfo", processSuggestionInfo);

		// 保证金月息差及利息月息差判断标识
		String flowCode = CheckApproveUtils.validateFlowCode(checkApprove.getApproProductTypeCode(), actTaskParam.getTaskDefKey());
		model.addAttribute("flowCode", flowCode);
		return "app/credit/appointInfo/appointInfoIndex";
	}

	/**
	 * 合同预约-预约信息-保存预约结论
	 */
	@RequiresPermissions("credit:appointInfo:edit")
	@ResponseBody
	@RequestMapping(value = "saveAppointConclusion")
	public AjaxView saveAppointConclusion(ActTaskParam actTaskParam, HttpServletRequest request, Model model) {
		AjaxView rtn = new AjaxView();
		try {
			//-----------------------
			ApplyRegister applyRegister = new ApplyRegister();
			applyRegister.setApplyNo(actTaskParam.getApplyNo());
			List<ApplyRegister> registerList = applyRegisterService.findList(applyRegister);
			if (!registerList.isEmpty()) {
				applyRegister = registerList.get(0);
			}
			if(Constants.PROC_DEF_KEY_GR.equalsIgnoreCase(applyRegister.getProcDefKey())&&"yes".equals(request.getParameter("passFlag"))){
				String isRegisterAccount = guaranteeRelationService.checkGRHasRegister(actTaskParam.getApplyNo());
				if(StringUtils.isNoneBlank(isRegisterAccount)) {
					return rtn.setFailed().setMessage(isRegisterAccount); 
				}
				String isRegister = applyInfoService.checkIsRegisterGed(actTaskParam.getApplyNo());
				if(StringUtils.isNoneBlank(isRegister)) {
					return rtn.setFailed().setMessage("请先注册冠易贷账号！"); 
				}else {
					AddOrderResponse addOrderResponse = applyRegisterService.sendOrderToGED(actTaskParam.getApplyNo());
					if(addOrderResponse==null){
						return rtn.setFailed().setMessage("推送冠易贷工单失败！");
					}else {
						 applyRegister.setSendGED("1");
					     applyRegisterService.saveSendGEDStatus(applyRegister);
					     creGedBorrowStatusService.saveGedBorrowStatusByApplyNo(actTaskParam.getApplyNo(),null, GedClient.ged_shz,0,null);
					}
					GedRegisterResponse pushGEDGuarantee = guaranteeRelationService.pushGRGEDGuarantee(actTaskParam.getApplyNo());
					if(!("0".equals(pushGEDGuarantee.getCode()))) {//失败
						return rtn.setFailed().setMessage("推送担保信息失败！");
					}
				}
			}
			//------------------------
			//联合授信，需要先检查是否全部注册，然后推送工单，在进行提交流程
			if(Constants.PROC_DEF_KEY_LHSX.equalsIgnoreCase(applyRegister.getProcDefKey())&&Constants.UTASK_HTYY.equals(actTaskParam.getTaskDefKey())&&"yes".equals(request.getParameter("passFlag"))){
				//是否全部建立担保关系
				List<String> custIDResult = checkApproveUnionService.getCustIdByApplyNo(actTaskParam.getApplyNo());
				for (String companyId : custIDResult) {
					List<String> idList = guaranteeRelationService.getIdByApplyNoAndCompanyId(companyId,actTaskParam.getApplyNo());
					if(idList.size()<=0) {
						return rtn.setFailed().setMessage("请为批量借款企业建立担保关系！");
					}
				}
				String checkHasRegister = guaranteeRelationService.checkHasRegister(actTaskParam.getApplyNo());
				if(!(StringUtils.isEmpty(checkHasRegister))) {//为null表示已经注册
					return rtn.setFailed().setMessage(checkHasRegister+"请将全部企业创建冠易贷账号！");
				}
				//推单
				AddOrderResponse addOrderResponse = checkApproveService.pushUnionOrderToGED(actTaskParam.getApplyNo());
				if(addOrderResponse==null){
					return rtn.setFailed().setMessage("推送冠易贷工单失败！");
				}else {//标记此单是推送GED的
					applyRegister.setSendGED("1");
				    applyRegisterService.saveSendGEDStatus(applyRegister);
					List<CheckApproveUnion> checkApproveUnionByApplyNo = checkApproveUnionService.getCheckApproveUnionByApplyNo(applyRegister.getApplyNo());
					for (CheckApproveUnion checkApproveUnion : checkApproveUnionByApplyNo) {
						creGedBorrowStatusService.saveGedBorrowStatusByApplyNo(applyRegister.getApplyNo(),checkApproveUnion.getId(), GedClient.ged_shz,1,null);
					}
				}
				//推送担保信息
				String pushGuaranteeResult ;
				GedRegisterResponse pushGEDGuarantee = guaranteeRelationService.pushGEDGuarantee(actTaskParam.getApplyNo());
				if(!("0".equals(pushGEDGuarantee.getCode()))) {//失败
					return rtn.setFailed().setMessage("推送担保信息失败！");
				}
				
			}
			if(Constants.PROC_DEF_KEY_LHSX.equalsIgnoreCase(applyRegister.getProcDefKey())&&Constants.UTASK_HTMQ.equals(actTaskParam.getTaskDefKey())){
				String openAccountResult = gedAccountService.checkIsOpenAccount(actTaskParam.getApplyNo());
				if(StringUtils.isNoneBlank(openAccountResult)) {
					return rtn.setFailed().setMessage(openAccountResult);
				}
			}
			
			String suggestionDesc = request.getParameter("suggestionDesc");
			String reserveTime = request.getParameter("reserveTime");
			String passFlag = request.getParameter("passFlag");
			Map<String, String> processMap = Maps.newHashMap();
			processMap.put("suggestionDesc", suggestionDesc);
			processMap.put("reserveTime", reserveTime);
			processMap.put("passFlag", passFlag);
			processMap.put("flag", "0");
			if ("no".equals(passFlag)) {
				ApplyRegister applyRegisterA = new ApplyRegister();
				applyRegisterA.setApplyNo(actTaskParam.getApplyNo());
				List<ApplyRegister> registerListA = applyRegisterService.findList(applyRegisterA);
				if (!registerListA.isEmpty()) {
					applyRegisterA = registerListA.get(0);
				}
				if(Constants.PROC_DEF_KEY_GR.equalsIgnoreCase(applyRegisterA.getProcDefKey())){
					Map<String, String> paramCheckApprove = Maps.newConcurrentMap();
					paramCheckApprove.put("applyNo", actTaskParam.getApplyNo());
					List<CheckApprove> checkApproveList = checkApproveService.getCheckApproveByApplyNo(paramCheckApprove);
					if (checkApproveList.size() >0) {
						CheckApprove checkApprove = checkApproveList.get(0);
						creGuaranteeCompanyService.updateGuranteFeeByApply(checkApprove, actTaskParam.getApplyNo());
					}
					Facade.facade.finishOrderPushGed(actTaskParam.getApplyNo(),200,"0");//0是非联合授信
				}else if (Constants.PROC_DEF_KEY_LHSX.equalsIgnoreCase(applyRegisterA.getProcDefKey())) {
					creGuaranteeCompanyService.updateGuranteFeeByApplyNoUnion(actTaskParam.getApplyNo());
					Facade.facade.finishOrderPushGed(actTaskParam.getApplyNo(),200,"1");//1联合授信
				}
			}
			processSuggestionInfoService.saveApproveSuggestion(actTaskParam, processMap, model);
			addMessage(model, "预约信息保存成功！");
			rtn.setSuccess().setMessage("预约信息保存成功！");
		} catch (Exception e) {
			addMessage(model, "预约信息保存失败！");
			rtn.setFailed().setMessage("预约信息保存失败！");
			logger.error("预约信息保存失败！", e);
		}
		return rtn;
	}

	

	@RequiresPermissions("credit:appointInfo:edit")
	@RequestMapping(value = "loadUnion")
	public String loadUnion(ActTaskParam actTaskParam, String readOnly, Model model) {
		String applyNo = actTaskParam.getApplyNo();
		ApplyInfo applyInfo = applyInfoService.findApplyInfoByApplyNo(actTaskParam.getApplyNo());
		// 从申请客户登记信息表表中查询客户的详细信息
		ApplyRegister applyRegister = applyRegisterService.getApplyRegisterByApplyNo(actTaskParam.getApplyNo());
		applyInfo.setApplyRegister(applyRegister);

		// 联合授信批复列表
		List<CheckApproveUnion> checkApproveUnionList = checkApproveUnionService.findApproveListByApplyNo(applyNo);

		// 预约意见
		Map<String, String> params = Maps.newConcurrentMap();
		params.put("applyNo", actTaskParam.getApplyNo());
		params.put("taskDefKey", actTaskParam.getTaskDefKey());
		ProcessSuggestionInfo processSuggestionInfo = processSuggestionInfoService.findByApplyNo(params);
		if (processSuggestionInfo == null) {
			processSuggestionInfo = new ProcessSuggestionInfo();
			processSuggestionInfo.setApplyNo(actTaskParam.getApplyNo());
			processSuggestionInfo.setTaskDefKey(actTaskParam.getTaskDefKey());
			// String reserveTime = processSuggestionInfo.getReserveTime();
		}
		//查询主借企业信息
		CompanyInfo companyMessage = new CompanyInfo();
		Map<String, Object> queryComParam = Maps.newHashMap();
		queryComParam.put("applyNo", actTaskParam.getApplyNo());
		queryComParam.put("roleType", Constants.ROLE_TYPE_ZJQY);
		List<CompanyInfo> comapnyList = companyInfoService.findListByParams(queryComParam);
		if (!comapnyList.isEmpty()) {
					companyMessage = comapnyList.get(0);
			}
		model.addAttribute("companyMessage",companyMessage);
		model.addAttribute("applyInfo", applyInfo);
		model.addAttribute("checkApproveUnionList", checkApproveUnionList);
		model.addAttribute("actTaskParam", actTaskParam);
		model.addAttribute("readOnly", readOnly);
		model.addAttribute("processSuggestionInfo", processSuggestionInfo);
		return "app/credit/appointInfo/appointInfoUnionIndex";
	}

	@RequiresPermissions("credit:appointInfo:edit")
	@RequestMapping(value = "loadUnionForm")
	public String loadUnionForm(ActTaskParam actTaskParam, String readOnly, Model model, String custId, String approId) {
		String applyNo = actTaskParam.getApplyNo();
		ApplyInfo applyInfo = applyInfoService.findApplyInfoByApplyNo(actTaskParam.getApplyNo());
		// 从申请客户登记信息表表中查询客户的详细信息
		ApplyRegister applyRegister = applyRegisterService.getApplyRegisterByApplyNo(actTaskParam.getApplyNo());
		applyInfo.setApplyRegister(applyRegister);
		
		//采购贷字段显示
		String TypeCode = applyRegister.getApplyProductTypeCode();
		if(Constants.PRODUCT_TYPE_CG.equals(TypeCode)){//显示采购贷新增字段的标志
			model.addAttribute("showCgFlag", "1");
		}
		CustInfo mainCust = custInfoService.get(custId);
		if (mainCust == null) {
			mainCust = new CustInfo();
		}
		model.addAttribute("mainCust", mainCust);
		// 查询企业信息
		CompanyInfo companyInfo = companyInfoService.get(custId);
		if (companyInfo == null) {
			companyInfo = new CompanyInfo();
		}
		model.addAttribute("companyInfo", companyInfo);
		// 查询联合授信批复信息
		CheckApproveUnion checkApprove = checkApproveUnionService.get(approId);
		if (checkApprove == null) {
			checkApprove = new CheckApproveUnion();
		}
		model.addAttribute("checkApprove", checkApprove);
		// 查询总公司批复意见
		Map<String, String> topComSuggParams = Maps.newConcurrentMap();
		topComSuggParams.put("applyNo", applyNo);
		List<String> topComApproveSuggList = processSuggestionInfoService.findTopComApproveSugg(topComSuggParams);
		try {
			String topComApproveSugg = topComApproveSuggList.get(0);
			model.addAttribute("topComApproveSugg", topComApproveSugg);
		} catch (Exception e) {
			logger.warn("无总公司批复意见！");
		}
		// 预约意见
		Map<String, String> params = Maps.newConcurrentMap();
		params.put("applyNo", actTaskParam.getApplyNo());
		params.put("taskDefKey", actTaskParam.getTaskDefKey());
		ProcessSuggestionInfo processSuggestionInfo = processSuggestionInfoService.findByApplyNo(params);
		if (processSuggestionInfo == null) {
			processSuggestionInfo = new ProcessSuggestionInfo();
			processSuggestionInfo.setApplyNo(actTaskParam.getApplyNo());
			processSuggestionInfo.setTaskDefKey(actTaskParam.getTaskDefKey());
		}

		// 从cre_rate_interest中查询冠E通年利率
		Map<String, String> param = new HashMap<String, String>();
		param.put("loanRepayType", checkApprove.getApproLoanRepayType());
		param.put("periodValue", checkApprove.getApproPeriodValue());
		param.put("approProductTypeCode", checkApprove.getApproProductTypeCode());

		String interest = contractService.getInterest(param);
		BigDecimal interestD = new BigDecimal("0");
		if (interest != null && StringUtils.isNotEmpty(interest)) {
			interestD = new BigDecimal(interest).multiply(new BigDecimal("12"));
		}

		// 根据申请的产品类型查询还款方式
		Map<String, String> para = new HashMap<String, String>();
		para.put("productType", checkApprove.getApproProductTypeCode());
		List<RateInterest> loanRepayTypeList = rateInterestService.getLoanRepayType(para);
		model.addAttribute("loanRepayTypeList", loanRepayTypeList);

		model.addAttribute("interest", interestD.toString());
		model.addAttribute("applyInfo", applyInfo);
		
		model.addAttribute("checkApprove", checkApprove);
		//
		Map<String, String> parames = Maps.newHashMap();
		parames.put("applyNo", actTaskParam.getApplyNo());
		List<CheckApprove> checkApproves = checkApproveService.getCheckApproveByApplyNo(parames);
		if (checkApproves != null && checkApproves.size() != 0) {
			String approveProductCategoryKey=DictUtils.getDictLabel(checkApproves.get(0).getProductCategory(), "product_category", null);
			model.addAttribute("approveProductCategoryKey",approveProductCategoryKey);
		}
		
		model.addAttribute("actTaskParam", actTaskParam);
		model.addAttribute("readOnly", readOnly);
		model.addAttribute("processSuggestionInfo", processSuggestionInfo);
		// 保证金月息差及利息月息差判断标识
		String flowCode = CheckApproveUtils.validateFlowCode(checkApprove.getApproProductTypeCode(), actTaskParam.getTaskDefKey());
		model.addAttribute("flowCode", flowCode);
		return "app/credit/appointInfo/appointInfoUnionForm";
	}

	@RequiresPermissions("credit:appointInfo:edit")
	@RequestMapping(value = "loadUnionTab")
	public String loadUnionTab(ActTaskParam actTaskParam, String readOnly, Model model, String custId, String approId) {
		String applyNo = actTaskParam.getApplyNo();
		ApplyInfo applyInfo = applyInfoService.findApplyInfoByApplyNo(actTaskParam.getApplyNo());
		// 从申请客户登记信息表表中查询客户的详细信息
		ApplyRegister applyRegister = applyRegisterService.getApplyRegisterByApplyNo(actTaskParam.getApplyNo());
		applyInfo.setApplyRegister(applyRegister);

		CustInfo mainCust = custInfoService.get(custId);
		if (mainCust == null) {
			mainCust = new CustInfo();
			model.addAttribute("message", "借款人信息出现异常，请联系管理员！");
		}
		model.addAttribute("mainCust", mainCust);
		// 查询联合授信批复信息
		CheckApproveUnion checkApprove = checkApproveUnionService.get(approId);
		if (checkApprove == null) {
			checkApprove = new CheckApproveUnion();
		}
		model.addAttribute("checkApprove", checkApprove);
		// 查询总公司批复意见
		Map<String, String> topComSuggParams = Maps.newConcurrentMap();
		topComSuggParams.put("applyNo", applyNo);
		List<String> topComApproveSuggList = processSuggestionInfoService.findTopComApproveSugg(topComSuggParams);
		try {
			String topComApproveSugg = topComApproveSuggList.get(0);
			model.addAttribute("topComApproveSugg", topComApproveSugg);
		} catch (Exception e) {
			logger.warn("无总公司批复意见！");
		}
		// 预约意见
		Map<String, String> params = Maps.newConcurrentMap();
		params.put("applyNo", actTaskParam.getApplyNo());
		params.put("taskDefKey", actTaskParam.getTaskDefKey());
		ProcessSuggestionInfo processSuggestionInfo = processSuggestionInfoService.findByApplyNo(params);
		if (processSuggestionInfo == null) {
			processSuggestionInfo = new ProcessSuggestionInfo();
			processSuggestionInfo.setApplyNo(actTaskParam.getApplyNo());
			processSuggestionInfo.setTaskDefKey(actTaskParam.getTaskDefKey());
		}

		// 从cre_rate_interest中查询冠E通年利率
		Map<String, String> param = new HashMap<String, String>();
		param.put("loanRepayType", checkApprove.getApproLoanRepayType());
		param.put("periodValue", checkApprove.getApproPeriodValue());
		param.put("approProductTypeCode", checkApprove.getApproProductTypeCode());

		String interest = contractService.getInterest(param);
		BigDecimal interestD = new BigDecimal("0");
		if (interest != null && StringUtils.isNotEmpty(interest)) {
			interestD = new BigDecimal(interest).multiply(new BigDecimal("12"));
		}

		// 根据申请的产品类型查询还款方式
		Map<String, String> para = new HashMap<String, String>();
		para.put("productType", checkApprove.getApproProductTypeCode());
		List<RateInterest> loanRepayTypeList = rateInterestService.getLoanRepayType(para);
		model.addAttribute("loanRepayTypeList", loanRepayTypeList);

		model.addAttribute("interest", interestD.toString());
		model.addAttribute("applyInfo", applyInfo);
		model.addAttribute("checkApprove", checkApprove);
		model.addAttribute("actTaskParam", actTaskParam);
		model.addAttribute("readOnly", readOnly);
		model.addAttribute("processSuggestionInfo", processSuggestionInfo);
		model.addAttribute("custId", custId);
		model.addAttribute("approId", approId);
		return "app/credit/appointInfo/appointInfoUnionTab";
	}
	
	@RequiresPermissions("credit:appointInfo:edit")
	@RequestMapping(value="validateGedAccount")
	@ResponseBody
	public AjaxView validateGedAccount(String idNum,String legalNum,String custType,String socialNo){
		AjaxView ajaxView = new AjaxView();
		try {
			Boolean flag = false;
			if (StringUtils.isNotBlank(custType) && "1".equals(custType) && StringUtils.isNotBlank(idNum) && StringUtils.isNotBlank(legalNum)) {
				GedAccount gedAccount = gedAccountService.findGedAccountByIdNum(idNum);
				if(gedAccount == null){
				gedAccount = gedAccountService.findGedAccountByIdNum(legalNum);
				}
				if (gedAccount != null) {
					flag = true;
				}
			}else if ("2".equals(custType) && StringUtils.isNotBlank(socialNo)) {
				List<CreGedAccountCompany> creGedAccountCompanys = gedComAccountService.findCompanyAccountBySocialCreditNo(socialNo);
				if (creGedAccountCompanys.size() >0) {
					flag = true;
				}
			}
			
			if(flag){
				ajaxView.setSuccess();
				ajaxView.setMessage("存在资金账户");
			}else{
				ajaxView.setStatus("3");
				ajaxView.setMessage("请注意！该用户没有开通资金账户！");
			}
		} catch (Exception e) {
			logger.error("查询资金账户失败！", e);
			ajaxView.setFailed();
			ajaxView.setMessage("查询资金账户失败，请查看后台信息");
		}
		return ajaxView;
	}
}