package com.resoft.credit.checkApprove.web;

import com.resoft.credit.checkApprove.utils.CheckApproveUtils;
import com.resoft.credit.underCompanyInfo.entity.UnderCompanyInfo;
import com.resoft.credit.underCompanyInfo.service.UnderCompanyInfoService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import java.math.BigDecimal;
import java.text.DateFormat;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.resoft.common.utils.AjaxView;
import com.resoft.common.utils.Constants;
import com.resoft.credit.applyInfo.entity.ApplyInfo;
import com.resoft.credit.applyInfo.service.ApplyInfoService;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.applyRegister.entity.ApplyRegister;
import com.resoft.credit.applyRegister.service.ApplyRegisterService;
import com.resoft.credit.applyRelation.entity.ApplyRelation;
import com.resoft.credit.applyRelation.service.ApplyRelationService;
import com.resoft.credit.checkApprove.entity.CheckApprove;
import com.resoft.credit.checkApprove.service.CheckApproveService;
import com.resoft.credit.checkCoupleDoubtful.service.CheckCoupleDoubtfulService;
import com.resoft.credit.checkFee.entity.CheckFee;
import com.resoft.credit.checkFee.service.CheckFeeService;
import com.resoft.credit.checkPhone.service.CheckPhoneService;
import com.resoft.credit.checkWeb.service.CheckWebService;
import com.resoft.credit.companyInfo.entity.CompanyInfo;
import com.resoft.credit.companyInfo.service.CompanyInfoService;
import com.resoft.credit.contactInfo.entity.ContactInfo;
import com.resoft.credit.contactInfo.service.ContactInfoService;
import com.resoft.credit.contract.service.ContractService;
import com.resoft.credit.crePurchaseInfo.entity.PurchaseInfo;
import com.resoft.credit.crePurchaseInfo.service.PurchaseInfoService;
import com.resoft.credit.creditViewBook.entity.creditAssets.CreditAssets;
import com.resoft.credit.creditViewBook.service.creditAssets.CreditAssetsService;
import com.resoft.credit.custinfo.entity.CustInfo;
import com.resoft.credit.custinfo.service.CustInfoService;
import com.resoft.credit.gqgetComInfo.entity.GqgetComInfo;
import com.resoft.credit.gqgetComInfo.service.GqgetComInfoService;
import com.resoft.credit.guaranteeCompany.service.CreGuaranteeCompanyService;
import com.resoft.credit.pricedRisk.service.PricedRiskService;
import com.resoft.credit.processSuggestionInfo.entity.ProcessSuggestionInfo;
import com.resoft.credit.processSuggestionInfo.service.ProcessSuggestionInfoService;
import com.resoft.credit.product.entity.Product;
import com.resoft.credit.product.service.ProductService;
import com.resoft.credit.rateInterest.entity.RateInterest;
import com.resoft.credit.rateInterest.service.RateInterestService;
import com.resoft.credit.repayPlan.entity.RepayPlan;
import com.resoft.credit.repayPlan.service.RepayPlanService;
import com.resoft.credit.stockTaskReceive.entity.StockTaskReceive;
import com.resoft.outinterface.utils.Facade;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 批复信息Controller
 *
 * @author wuxi01
 * @version 2016-02-29
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/checkApprove")
public class CheckApproveController extends BaseController {
	//	@Autowired
//	private CreGedBorrowStatusService creGedBorrowStatusService;
	@Autowired
	private CheckApproveService checkApproveService;
	@Autowired
	private ApplyInfoService applyInfoService;
	@Autowired
	private ApplyRegisterService applyRegisterService;
	@Autowired
	private ProcessSuggestionInfoService processSuggestionInfoService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ContractService contractService;
	@Autowired
	private RepayPlanService repayPlanService;
	@Autowired
	private CreditAssetsService creditAssetsService;
	@Autowired
	private CheckFeeService checkFeeService;
	@Autowired
	private RateInterestService rateInterestService;
	@Autowired
	private PricedRiskService pricedRiskService;
	@Autowired
	private GqgetComInfoService gqgetComInfoService;
	@Autowired
	private CheckCoupleDoubtfulService checkCoupleDoubtfulService;
	@Autowired
	private ApplyRelationService applyRelationService;
	@Autowired
	private CompanyInfoService companyInfoService;
	@Autowired
	private CustInfoService custInfoService;
	@Autowired
	private CheckWebService checkWebService;
	@Autowired
	private ContactInfoService contactInfoService;
	@Autowired
	private CheckPhoneService checkPhoneService;
	@Autowired
	private PurchaseInfoService purchaseInfoService;
	@Autowired
	private CreGuaranteeCompanyService creGuaranteeCompanyService;
	@Autowired
	private UnderCompanyInfoService underCompanyInfoService;
	@ModelAttribute
	public CheckApprove get(@RequestParam(required = false) String id) {
		CheckApprove entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = checkApproveService.get(id);
		}
		if (entity == null) {
			entity = new CheckApprove();
		}
		return entity;
	}

	@RequiresPermissions("credit:checkApprove:view")
	@RequestMapping(value = "form")
	public String form(CheckApprove checkApprove, String applyNo, Model model) {
		model.addAttribute("checkApprove", checkApprove);
		// 根据产品类型加载产品列表
		// 产品类型
		String productType = checkApprove.getApproProductTypeCode();
		// 当前进件所在机构
		String orgId = null;
		try {
			orgId = applyRegisterService.getApplyRegisterByApplyNo(applyNo).getCompany().getId();
		} catch (Exception e) {
			logger.error("查询进件信息失败！", e);
		}
		// 产品查询条件
		Product product = new Product();
		product.setCompany(new Office(orgId));
		product.setProductTypeCode(productType);
		// 产品列表
		List<Product> productList = productService.findCoProductByType(product);
		// 根据产品查询产品期限
		/*
		 * String productId = checkApprove.getApproProductId();
		 * List<ProductPeriod> periodList =
		 * productPeriodService.findUseablePeriodByProductId(productId);
		 * model.addAttribute("periodList", periodList);
		 */
		// 传入对象
		model.addAttribute("productList", productList);
		return "app/credit/checkApprove/checkApproveForm";
	}

	@ResponseBody
	@RequiresPermissions("credit:checkApprove:edit")
	@RequestMapping(value = "save")
	public AjaxView save(CheckApprove checkApprove, Model model) {
		AjaxView rtn = new AjaxView();
		try {

			if (!beanValidator(model, checkApprove)) {
				rtn.setFailed().setMessage("数据验证失败!");
				return rtn;
			}
			if (checkApprove.getMarginAmount() == null) {
				checkApprove.setMarginAmount(new BigDecimal("0"));
			}
			Map<String, String> param = new HashMap<String, String>();
			param.put("loanRepayType", checkApprove.getApproLoanRepayType());
			param.put("periodValue", checkApprove.getApproPeriodValue());
			param.put("approProductTypeCode", checkApprove.getApproProductTypeCode());
			String interest = contractService.getInterest(param);
			BigDecimal guanetongRate = new BigDecimal(interest).multiply(new BigDecimal("12"));
			checkApprove.setGuanetongRate(guanetongRate);
			// 为批复信息添加流程标识
			String processSequence = DictUtils.getDictValue(checkApprove.getTaskDefKey(), "PROCESS_SEQUENCE", "");
			checkApprove.setProcessSequence(processSequence);
			String flag = checkApproveService.saveCheckApprove(checkApprove);
			if ("true".equals(flag)) {
				rtn.setSuccess().setMessage("保存批复信息成功!");
				rtn.put("id", checkApprove.getId());
				rtn.put("guanetongRate", guanetongRate);
				addMessage(model, "保存批复信息成功!");
			} else {
				rtn.setFailed().setMessage("保存批复信息失败!");
				addMessage(model, "保存批复信息失败!");
			}

		} catch (Exception e) {
			rtn.setFailed().setMessage("保存批复信息失败!");
			addMessage(model, "保存批复信息失败!");
			logger.error("保存批复信息失败!", e);
		}
		return rtn;
	}

	@RequiresPermissions("credit:checkApprove")
	@RequestMapping(value = "load")
	public String load(CheckApprove checkApprove, String readOnly, ActTaskParam actTaskParam, Model model) {
		model.addAttribute("readOnly", readOnly);
		ApplyInfo applyInfo = applyInfoService.findApplyInfoByApplyNo(actTaskParam.getApplyNo());
		// 从申请客户登记信息表中查询客户的详细信息
		ApplyRegister applyRegister = applyRegisterService.getApplyRegisterByApplyNo(actTaskParam.getApplyNo());
		applyInfo.setApplyRegister(applyRegister);
		String TypeCode = applyRegister.getApplyProductTypeCode();
		//显示采购贷新增字段的标志，及其查询采购商品的信息
		if(Constants.PRODUCT_TYPE_CG.equals(TypeCode)){
			model.addAttribute("showCgFlag", "1");
		}
		// 根据申请编号,流程id查询批复信息
		Map<String, String> paramCheckApprove = new HashMap<String, String>();
		paramCheckApprove.put("applyNo", actTaskParam.getApplyNo());
		if (!"viewHistory".equalsIgnoreCase(readOnly)) {
			paramCheckApprove.put("taskDefKey", actTaskParam.getTaskDefKey());
			paramCheckApprove.put("processSequence", DictUtils.getDictValue(actTaskParam.getTaskDefKey(), "PROCESS_SEQUENCE", null));
		}
		if ("viewHistory".equalsIgnoreCase(readOnly)) {
			paramCheckApprove.put("taskDefKeyV", checkApprove.getTaskDefKey());
		}
		List<CheckApprove> checkApproveList = checkApproveService.getCheckApproveByApplyNo(paramCheckApprove);
		// 如果当前环节没有批复信息，查询上一环节的批复信息
		if (checkApproveList.size() > 0) {
			checkApprove = checkApproveList.get(0);
			String approProductTypeCode = checkApprove.getApproProductTypeCode();
			if(Constants.PRODUCT_TYPE_ZGJH.equals(approProductTypeCode)){
				model.addAttribute("isShowCategoryKey","1");
				String productCategoryKey=DictUtils.getDictLabel(applyRegister.getProductCategory() , "product_category", null);
				model.addAttribute("productCategoryKey",productCategoryKey);
				String approveProductCategoryKey=DictUtils.getDictLabel(checkApprove.getProductCategory(), "product_category", null);
				model.addAttribute("approveProductCategoryKey",approveProductCategoryKey);
				if(Constants.UTASK_DQFKSH.equals(actTaskParam.getTaskDefKey())||Constants.UTASK_ZGSFKSH.equals(actTaskParam.getTaskDefKey())){//分类可以修改
					model.addAttribute("editCategory","1");//显示分类
				}else{
					model.addAttribute("editCategory","0");
				}
			}else{
				model.addAttribute("isShowCategoryKey","0");
			}
		} else {
			checkApprove = new CheckApprove();//首次出现债股结合的产品初始化页面显示，分公司专员没有修改权限
			if(Constants.PRODUCT_TYPE_ZGJH.equals(applyInfo.getApplyRegister().getApplyProductTypeCode())){
				checkApprove.setApproProductTypeCode(applyInfo.getApplyRegister().getApplyProductTypeCode());
				checkApprove.setApproProductId(applyInfo.getApplyRegister().getApplyProductId());
				checkApprove.setApproProductName(applyInfo.getApplyRegister().getApplyProductName());
				checkApprove.setProductCategory(applyInfo.getApplyRegister().getProductCategory());
				checkApprove.setApproProductTypeName(DictUtils.getDictLabel(applyInfo.getApplyRegister().getApplyProductTypeCode(), "PRODUCT_TYPE", null));
				String productCategoryKey=DictUtils.getDictLabel(applyRegister.getProductCategory() , "product_category", null);
				model.addAttribute("productCategoryKey",productCategoryKey);
				model.addAttribute("approveProductCategoryKey",productCategoryKey);
				model.addAttribute("isShowCategoryKey","1");
				model.addAttribute("editCategory","0");
			}else{
				model.addAttribute("isShowCategoryKey","0");
			}
		}
		// 查询外访费
		CheckFee checkFee = checkFeeService.findByApplyNo(actTaskParam.getApplyNo());
		if (checkFee == null) {
			checkFee = new CheckFee();
		}
		checkApprove.setCheckFee(checkFee.getCheckFee());
		// 审批意见
		Map<String, String> params = Maps.newConcurrentMap();
		try {
			params.put("applyNo", actTaskParam.getApplyNo());
			params.put("taskDefKey", actTaskParam.getTaskDefKey());
		} catch (Exception e) {
			logger.error("流程参数出现错误，请联系管理员！", e);
			model.addAttribute("message", "流程参数出现错误，请联系管理员！");
			return "app/credit/checkApprove/checkApproveIndex";
		}
		ProcessSuggestionInfo processSuggestionInfo = processSuggestionInfoService.findByApplyNo(params);
		if (processSuggestionInfo == null) {
			processSuggestionInfo = new ProcessSuggestionInfo();
			processSuggestionInfo.setApplyNo(actTaskParam.getApplyNo());
			processSuggestionInfo.setTaskDefKey(actTaskParam.getTaskDefKey());
		}
		model.addAttribute("applyInfo", applyInfo);
		model.addAttribute("actTaskParam", actTaskParam);
		model.addAttribute("checkApprove", checkApprove);
		model.addAttribute("processSuggestionInfo", processSuggestionInfo);
		// 根据产品类型加载产品列表
		// 产品类型
		String productType = checkApprove.getApproProductTypeCode();
		// 当前进件所在机构
		String orgId = null;
		String procDefKey = null;
		try {
			orgId = applyRegisterService.getApplyRegisterByApplyNo(actTaskParam.getApplyNo()).getCompany().getId();
			model.addAttribute("orgId", orgId);
		} catch (Exception e) {
			logger.error("查询进件信息失败！", e);
		}
		try {
			procDefKey = applyRegisterService.getApplyRegisterByApplyNo(actTaskParam.getApplyNo()).getProcDefKey();
			model.addAttribute("procDefKey", procDefKey);
		} catch (Exception e) {
			logger.error("查询流程Id失败！", e);
		}
		// 产品查询条件
		Product product = new Product();
		product.setCompany(new Office(orgId));
		product.setProductTypeCode(productType);
		product.setProcDefKey(procDefKey);
		// 产品列表
		List<Product> productList = productService.findCoProductByType(product);
		// 根据产品查询产品期限
		// 传入对象
		/*
		 * String productId = applyRegister.getApplyProductId();
		 * List<ProductPeriod> periodList =
		 * productPeriodService.findUseablePeriodByProductId(productId);
		 * model.addAttribute("periodList", periodList);
		 */
		model.addAttribute("productList", productList);
		// 查询字典表，判断是否需要显示特殊服务费率,1:显示，0：不显示
		String specialServiceFeeRate = DictUtils.getDictList("special_Service_Fee_Rate").get(0).getValue();
		if ("1".equals(specialServiceFeeRate)) {
			model.addAttribute("isShowSpecialServiceFeeRate", "1");
		} else {
			model.addAttribute("isShowSpecialServiceFeeRate", "0");
		}

		// 查询字典表，判断是否需要显示特殊服务费,1:显示，0：不显示
		String specialServiceFee = DictUtils.getDictList("special_Service_Fee").get(0).getValue();
		if ("1".equals(specialServiceFee)) {
			model.addAttribute("isShowSpecialServiceFee", "1");
		} else {
			model.addAttribute("isShowSpecialServiceFee", "0");
		}

		// 从cre_rate_interest中查询冠E通利率
		Map<String, String> param = new HashMap<String, String>();
		param.put("loanRepayType", checkApprove.getApproLoanRepayType());
		param.put("periodValue", checkApprove.getApproPeriodValue());
		param.put("approProductTypeCode", checkApprove.getApproProductTypeCode());
		String interest = contractService.getInterest(param);
		BigDecimal interestD = new BigDecimal("0");
		if (interest != null && StringUtils.isNotEmpty(interest)) {
			interestD = new BigDecimal(interest).multiply(new BigDecimal("12"));
		}
		model.addAttribute("interest", interestD.toString());

		// 根据申请的产品类型查询还款方式
		Map<String, String> param1 = new HashMap<String, String>();
		param1.put("productType", checkApprove.getApproProductTypeCode());
		List<RateInterest> loanRepayTypeList = rateInterestService.getLoanRepayType(param1);
		model.addAttribute("loanRepayTypeList", loanRepayTypeList);
		model.addAttribute("zgjhTypeCode",Constants.PRODUCT_TYPE_ZGJH);

		//保证金月息差及利息月息差显示判断
		String flowCode = CheckApproveUtils.validateMonthlySpreadStatus(actTaskParam.getTaskDefKey());
		model.addAttribute("flowCode", flowCode);
		model.addAttribute("cgdTypeCode", Constants.PRODUCT_TYPE_CG);

		return "app/credit/checkApprove/checkApproveIndex";
	}

	@RequiresPermissions("credit:checkApprove")
	@RequestMapping(value = "saveApproveSuggestion")
	public String saveApproveSuggestion(String readOnly, ActTaskParam actTaskParam, HttpServletRequest request, Model model) {
		try {
			String applyNo = actTaskParam.getApplyNo();
			String flag = request.getParameter("sta");
			//默认重新拆分
			String deleteDataFlag = "yes";
			//后台判断是否需要重新拆分
			CheckApprove approveOld = checkApproveService.getCheckApproveBackUp(applyNo);
			if(approveOld != null){
				Map<String, String> param11 = Maps.newHashMap();
				param11.put("applyNo", applyNo);
				List<CheckApprove> approves = checkApproveService.getCheckApproveByApplyNo(param11);
				CheckApprove approveNew = approves.get(0);
				List<String> messages = checkApproveService.contrastObj(approveOld,approveNew);
				if(messages.size() == 0){
					deleteDataFlag = "no";
				}
			}
			String suggestionDesc = request.getParameter("suggestionDesc");
			String passFlag = request.getParameter("passFlag");
			ApplyRegister applyRegister = applyRegisterService.getApplyRegisterByApplyNo(applyNo);
			Map<String, String> processMap = Maps.newHashMap();
			if(applyRegister != null){
				processMap.put("unionFlag", applyRegister.getProcDefKey());
			}
			processMap.put("suggestionDesc", suggestionDesc);
			processMap.put("passFlag", passFlag);
			processMap.put("flag", flag);
			processMap.put("deleteDataFlag", deleteDataFlag);
			processMap.put("blacklistRemarks", request.getParameter("blacklistRemarks"));
			processMap.put("contractAmount", request.getParameter("checkApproveAmount"));
			processMap.put("checkApproveProductType", request.getParameter("checkApproveProductType"));
			processSuggestionInfoService.saveApproveSuggestion(actTaskParam, processMap, model);
			logger.info("Experian之ApplyNo:" + applyNo);
			if ("0".equals(flag) && "yes".equals(passFlag) && Constants.UTASK_DQFKJLSH.equals(actTaskParam.getTaskDefKey())) {
				Facade.facade.getExperianClient(actTaskParam.getApplyNo());
			}
			if ("0".equals(flag) && ("no".equals(passFlag) || "black".equals(passFlag) || "finish".equals(passFlag)) && (Constants.UTASK_ZGSJLSH.equals(actTaskParam.getTaskDefKey()) || Constants.UTASK_ZGSSXQR.equals(actTaskParam.getTaskDefKey()) || Constants.UTASK_DQSXQR.equals(actTaskParam.getTaskDefKey()) || Constants.UTASK_FGSSX.equals(actTaskParam.getTaskDefKey()) || Constants.UTASK_ZGSFKSH.equals(actTaskParam.getTaskDefKey()) || Constants.UTASK_HTYY.equals(actTaskParam.getTaskDefKey()) || Constants.UTASK_ZGSZJLSH.equals(actTaskParam.getTaskDefKey()) || Constants.UTASK_FWSH.equals(actTaskParam.getTaskDefKey()))) {
				ApplyRegister applyRegisterGed = new ApplyRegister();
				applyRegisterGed.setApplyNo(actTaskParam.getApplyNo());
				List<ApplyRegister> registerList = applyRegisterService.findList(applyRegisterGed);
				if (!registerList.isEmpty()) {
					applyRegisterGed = registerList.get(0);
				}
				if(Constants.PROC_DEF_KEY_GR.equalsIgnoreCase(applyRegisterGed.getProcDefKey())){
					Map<String, String> paramCheckApprove = Maps.newConcurrentMap();
					paramCheckApprove.put("applyNo", actTaskParam.getApplyNo());
					List<CheckApprove> checkApproveList = checkApproveService.getCheckApproveByApplyNo(paramCheckApprove);
					if (checkApproveList.size() >0) {
						CheckApprove checkApprove = checkApproveList.get(0);
						creGuaranteeCompanyService.updateGuranteFeeByApply(checkApprove, actTaskParam.getApplyNo());
					}
					Facade.facade.finishOrderPushGed(actTaskParam.getApplyNo(),200,"0");//0是非联合授信
				}else if (Constants.PROC_DEF_KEY_LHSX.equalsIgnoreCase(applyRegisterGed.getProcDefKey())) {
					creGuaranteeCompanyService.updateGuranteFeeByApplyNoUnion(actTaskParam.getApplyNo());
					Facade.facade.finishOrderPushGed(actTaskParam.getApplyNo(),200,"1");//1联合授信
				}
			}
			model.addAttribute("approveMessage", "保存批复意见成功！");
			model.addAttribute("close", "close");

//			if(Constants.UTASK_ZGSFKSH.equals(actTaskParam.getTaskDefKey())){
//				if("no".equals(passFlag) || "black".equals(passFlag)){
//					creGedBorrowStatusService.saveGedBorrowStatusByApplyNo(actTaskParam.getApplyNo(), GedClient.ged_sbjj,0,null);
//				}else{
//					creGedBorrowStatusService.saveGedBorrowStatusByApplyNo(actTaskParam.getApplyNo(), GedClient.ged_shz,0,null);
//				}
//			}else if(Constants.UTASK_ZGSJLSH.equals(actTaskParam.getTaskDefKey())){
//				if("no".equals(passFlag) || "black".equals(passFlag)){
//					creGedBorrowStatusService.saveGedBorrowStatusByApplyNo(actTaskParam.getApplyNo(), GedClient.ged_sbjj,0,null);
//				}
//			}else if(Constants.UTASK_FGSSX.equals(actTaskParam.getTaskDefKey())){
//				if("no".equals(passFlag) || "black".equals(passFlag)){
//					creGedBorrowStatusService.saveGedBorrowStatusByApplyNo(actTaskParam.getApplyNo(), GedClient.ged_sbjj,0,null);
//				}
//			}else if(Constants.UTASK_DQSXQR.equals(actTaskParam.getTaskDefKey())){
//				if("no".equals(passFlag) || "black".equals(passFlag)){
//					creGedBorrowStatusService.saveGedBorrowStatusByApplyNo(actTaskParam.getApplyNo(), GedClient.ged_sbjj,0,null);
//				}
//			}else if(Constants.UTASK_ZGSSXQR.equals(actTaskParam.getTaskDefKey())){
//				if("no".equals(passFlag) || "black".equals(passFlag)){
//					creGedBorrowStatusService.saveGedBorrowStatusByApplyNo(actTaskParam.getApplyNo(), GedClient.ged_sbjj,0,null);
//				}
//			}
		} catch (Exception e) {
			logger.error("保存批复意见失败！", e);
			model.addAttribute("approveMessage", "保存批复意见失败！");
		}
		return load(new CheckApprove(), readOnly, actTaskParam, model);
	}

	@ResponseBody
	@RequiresPermissions("credit:checkApprove")
	@RequestMapping(value = "validateMandatoryField")
	public AjaxView validateMandatoryField(ActTaskParam actTaskParam, CheckApprove checkApprove, String passFlag) throws ParseException {
		AjaxView ajaxView = new AjaxView();
		String applyNo = actTaskParam.getApplyNo();
		String taskDefKey = actTaskParam.getTaskDefKey();
		String approProductTypeCode = checkApprove.getApproProductTypeCode();
		// 1.查询条件
		Map<String, String> params = Maps.newConcurrentMap();
		if ("yes".equals(passFlag)) {// 提交时
			try {
				params.put("applyNo", applyNo);
				params.put("taskDefKey", Constants.UTASK_FGSFKSH);
				params.put("approProductTypeCode", approProductTypeCode);
			} catch (Exception e) {
				logger.error("系统参数发生错误，请联系管理员！");
				ajaxView.setFailed().setMessage("系统参数发生错误，请联系管理员！");
				return ajaxView;
			}
			// 分公司风控审核校验信息
			if (Constants.UTASK_FGSFKSH.equals(taskDefKey)) {
				// 9. 五节点校验
				// 查询一个进件中，同一个对象的外访次数
				// 从数据库中查出一个进件中的所有对象
				//五个节点合成一个 分公司校验  在compareDate时间分界比较
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				ApplyRegister applyRegister = applyRegisterService.getApplyRegisterByApplyNo(applyNo);
				Date compareDate = df.parse("2017-06-22");
				if(applyRegister.getRegisterDate().getTime() > compareDate.getTime()){
					List<ApplyRelation> applyRelationList = applyRelationService.getCheckCoupleDoubtfulByApplyNo(applyNo);
					String loggerId = UserUtils.getUser().getId();
					for (int i = 0; i < applyRelationList.size(); i++) {
						ApplyRelation tempApplyRelation = applyRelationList.get(i);
						String count = checkCoupleDoubtfulService.getCheckCoupleDoubtfulCountForNewJob(tempApplyRelation,loggerId);
						if (count.equals("0")) {
							ajaxView.setFailed().setMessage("外访未完成，不能提交！");
							return ajaxView;
						}
					}
					//外访登记费
					CheckFee checkFeeTemp = checkFeeService.findByApplyNo(applyNo);
					if (checkFeeTemp == null) {
						ajaxView.setFailed().setMessage("外访费登记未完成，不能提交！");
						return ajaxView;
					}
					//
					//网查信息
					for(int i=0;i<applyRelationList.size();i++){
						ApplyRelation tempApplyRelation = applyRelationList.get(i);
						String id = tempApplyRelation.getCustId();
						//查询企业信息
						if(Constants.ROLE_TYPE_ZJQY.equals(tempApplyRelation.getRoleType()) || Constants.ROLE_TYPE_DBQY.equals(tempApplyRelation.getRoleType())){
							CompanyInfo companyInfo = companyInfoService.get(id);
							tempApplyRelation.setCompanyInfo(companyInfo);
						}else{
							//查询客户信息
							CustInfo custInfo = custInfoService.get(id);
							tempApplyRelation.setCustInfo(custInfo);
						}
						//获取访问次数
						String count = checkWebService.getCheckWebCount(tempApplyRelation.getApplyNo(),id,tempApplyRelation.getRoleType());
						if (count.equals("0")) {
							ajaxView.setFailed().setMessage("网查未完成，不能提交！");
							return ajaxView;
						}

					}
					//电话核查
					Map<String, Object> map = Maps.newConcurrentMap();
					map.put("applyNo", applyNo);
					map.put("roleType", Constants.ROLE_TYPE_ZJR);
					List<ContactInfo> contactInfoList = contactInfoService.findContactInfoByApplyNo(map);
					ContactInfo contractInfo = new ContactInfo();
					for (int j = 0; j < contactInfoList.size(); j++) {
						contractInfo = contactInfoList.get(j);
						String contractCount = checkPhoneService.getCheckPhoneCount(applyNo, contractInfo.getId(), Constants.ROLE_TYPE_CONTACT);
						if(contractCount.equals("0")){
							ajaxView.setFailed().setMessage("电话核查未完成，不能提交！");
							return ajaxView;
						}
					}
				}

				// 2.校验抵质押物信息完整性（贷款类型为抵押时）
				if (Constants.PRODUCT_TYPE_DY.equals(params.get("approProductTypeCode")) || Constants.PRODUCT_TYPE_HH.equals(params.get("approProductTypeCode"))) {
					// 校验抵质押物信息
					boolean isMortagageCompleted = checkApproveService.isMortagageCompleted(params, ajaxView);
					if (!isMortagageCompleted) {
						return ajaxView;
					}
				}
				// 3.校验征信及流水信息完整性
				boolean isCreditCompleted = checkApproveService.isCreditCompleted(params, ajaxView);
				if (!isCreditCompleted) {
					return ajaxView;
				}
				// 4.校验资产清单数据完整性
				List<CreditAssets> creditAssetsList = creditAssetsService.getCreditAssetsByApplyNo(applyNo);
				if (creditAssetsList == null || creditAssetsList.size() == 0) {
					ajaxView.setFailed().setMessage("信审意见书页面，请完善资产清单列表后再进行操作！");
					return ajaxView;
				}

//				if(applyRegisterService.checkIsStockWebCheck(applyNo)){
//					Map<String,String> map = creStockMarkApplyRelationService.verifyMarkIsCompleteByApplyNo(applyNo);
//					if("fail".equals(map.get("isOk"))){
//						ajaxView.setFailed().setMessage(map.get("message"));
//						return ajaxView;
//					}
//				}
			}
			// 5.校验信审意见书意见是否存在
			if (Constants.UTASK_FGSJLSH.equals(taskDefKey)) {
				params.put("taskDefKey", Constants.UTASK_FGSFKSH);
			} else if (Constants.UTASK_QYJLSH.equals(taskDefKey)) {
				params.put("taskDefKey", Constants.UTASK_QYFKSH);
			} else if (Constants.UTASK_DQFKJLSH.equals(taskDefKey)) {
				params.put("taskDefKey", Constants.UTASK_DQFKSH);
			} else if (Constants.UTASK_ZGSJLSH.equals(taskDefKey)) {
				params.put("taskDefKey", Constants.UTASK_ZGSFKSH);
			} else if (Constants.UTASK_ZGSZJLSH.equals(taskDefKey)) {
				params.put("taskDefKey", Constants.UTASK_ZGSFKSH);
			} else {
				params.put("taskDefKey", taskDefKey);
			}
			ProcessSuggestionInfo processSuggestionInfo = processSuggestionInfoService.findByApplyNo(params);
			if (processSuggestionInfo == null || StringUtils.isBlank(processSuggestionInfo.getCreditDesc())) {
				ajaxView.setFailed().setMessage("信审意见书页面，请完善信审意见书信息后再进行操作！");
				return ajaxView;
			}
			// 6.校验批复信息数据完整性,和是否有还款计划表数据
			if (Constants.UTASK_FGSFKSH.equals(taskDefKey) || Constants.UTASK_QYFKSH.equals(taskDefKey) || Constants.UTASK_DQFKSH.equals(taskDefKey) || Constants.UTASK_ZGSFKSH.equals(taskDefKey)) {
				Map<String, String> paramCheckApprove = Maps.newConcurrentMap();
				paramCheckApprove.put("applyNo", applyNo);
				paramCheckApprove.put("taskDefKeyV", actTaskParam.getTaskDefKey());
				List<CheckApprove> checkApprovePersis = checkApproveService.getCheckApproveByApplyNo(paramCheckApprove);
				if (checkApprovePersis.size() == 0) {
					ajaxView.setFailed().setMessage("批复信息页面，请完善批复信息后再进行操作！");
					return ajaxView;
				}

				ApplyRegister applyRegisterByApplyNo = applyRegisterService.getApplyRegisterByApplyNo(applyNo);
				String TypeCode = applyRegisterByApplyNo.getApplyProductTypeCode();
				if(Constants.PRODUCT_TYPE_CG.equals(TypeCode)){
					List<PurchaseInfo> purchaseInfoList =purchaseInfoService.getInfoByApplyNo(applyNo);
					if(purchaseInfoList.size()==0){
						ajaxView.setFailed().setMessage("批复信息页面，请完善采购信息！");
						return ajaxView;
					}
				}




				// 校验是否有还款计划数据
				Map<String, String> param = Maps.newConcurrentMap();
				param.put("applyNo", applyNo);
				param.put("taskDefKey", actTaskParam.getTaskDefKey());
				List<RepayPlan> repayPlanList = repayPlanService.getRepayPlanByApplyNoAndTaskDefKey(param);
				if (repayPlanList.size() == 0) {
					ajaxView.setFailed().setMessage("生成还款计划表数据失败，不能提交！");
					return ajaxView;
				}
			}
			// 7.校验批复产品类型改变冠e通页面必须调整再提交
			// approProductTypeCode
			GqgetComInfo gqget = gqgetComInfoService.getGqgetComInfoByApplyNo(applyNo);
			if (!(gqget != null && gqget.getProductType() != null && !gqget.getProductType().isEmpty() && gqget.getProductType().equals(approProductTypeCode))) {
				ajaxView.setFailed().setMessage("请完善冠e通页面再进行提交");
				return ajaxView;
			}
			// 8.校验 黑名单规则 再提交
			/*
			 * if (Constants.UTASK_FGSFKSH.equals(taskDefKey) ||
			 * Constants.UTASK_QYFKSH.equals(taskDefKey) ||
			 * Constants.UTASK_DQFKSH.equals(taskDefKey)){ Map<String, String>
			 * maps = Maps.newHashMap(); maps.put("applyNo", applyNo);
			 * maps.put("taskDefKey", actTaskParam.getTaskDefKey());
			 * maps.put("reviewedSheet", "1"); String idd =
			 * reviewedRuleService.checkReviewed(maps); if
			 * (StringUtils.isNull(idd)){
			 * ajaxView.setFailed().setMessage("请完善黑名单规则页面再进行提交"); return
			 * ajaxView; } }
			 */
			//验证股权尽调页面审批意见是否填写
			if(Constants.PRODUCT_TYPE_ZGJH.equals(approProductTypeCode) && (Constants.UTASK_FGSFKSH.equals(actTaskParam.getTaskDefKey()) || Constants.UTASK_QYFKSH.equals(actTaskParam.getTaskDefKey()) || Constants.UTASK_DQFKSH.equals(actTaskParam.getTaskDefKey()) || Constants.UTASK_ZGSFKSH.equals(actTaskParam.getTaskDefKey()))){
				Map<String, String> creStockParam = Maps.newConcurrentMap();
				creStockParam.put("applyNo", applyNo);
				creStockParam.put("taskDefKeyV", actTaskParam.getTaskDefKey());
				StockTaskReceive stockTaskReceive = checkApproveService.findCreStockTaskReceiveByParam(creStockParam);
				if("utask_fgsfksh".equalsIgnoreCase(creStockParam.get("taskDefKeyV")) && stockTaskReceive != null && StringUtils.isEmpty(stockTaskReceive.getStatus())){
					ajaxView.setFailed().setMessage("请完善股权尽调页面,分公司团队意见审批！");
					return ajaxView;
				}
				if("utask_qyfksh".equalsIgnoreCase(creStockParam.get("taskDefKeyV")) && stockTaskReceive != null && StringUtils.isEmpty(stockTaskReceive.getStatus())){
					ajaxView.setFailed().setMessage("请完善股权尽调页面,区域团队意见审批！");
					return ajaxView;
				}
				if("utask_dqfkzysh".equalsIgnoreCase(creStockParam.get("taskDefKeyV")) && stockTaskReceive != null && StringUtils.isEmpty(stockTaskReceive.getStatus())){
					ajaxView.setFailed().setMessage("请完善股权尽调页面,大区团队意见审批！");
					return ajaxView;
				}
				if("utask_zgsfksh".equalsIgnoreCase(creStockParam.get("taskDefKeyV")) && stockTaskReceive != null && StringUtils.isEmpty(stockTaskReceive.getStatus())){
					ajaxView.setFailed().setMessage("请完善股权尽调页面,总公司团队意见审批！");
					return ajaxView;
				}
				if(stockTaskReceive == null){
					ajaxView.setFailed().setMessage("请分配相应股权估值人员,并填写审核意见");
					return ajaxView;
				}
			}


		} else {
			if ("no".equals(passFlag) && Constants.UTASK_DQFKSH.equals(actTaskParam.getTaskDefKey())) {// 大区风控专员审核打回到分公司复议，校验信审意见书综合意见
				params.put("applyNo", applyNo);
				params.put("taskDefKey", Constants.UTASK_DQFKSH);
				ProcessSuggestionInfo processSuggestionInfo = processSuggestionInfoService.findByApplyNo(params);
				if (processSuggestionInfo == null || StringUtils.isBlank(processSuggestionInfo.getCreditDesc())) {
					ajaxView.setFailed().setMessage("信审意见书页面，请完善信审意见书意见后再进行操作！");
					return ajaxView;
				}
			}
		}
		ajaxView.setSuccess();
		return ajaxView;
	}

	@RequiresPermissions("credit:checkApprove")
	@RequestMapping(value = "checkApproveHistoryList")
	public String checkApproveHistoryList(CheckApprove checkApprove, HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			checkApprove.setProcessSequence(DictUtils.getDictValue(checkApprove.getTaskDefKey(), "PROCESS_SEQUENCE", null));
			List<CheckApprove> page = checkApproveService.findList(checkApprove);
			model.addAttribute("page", page);
		} catch (Exception e) {
			logger.error("查询历史批复信息失败", e);
		}
		return "app/credit/checkApprove/checkApproveHistoryInfoList";
	}

	@RequiresPermissions("credit:checkApprove")
	@RequestMapping(value = "getApproLoanRepayTypelist")
	@ResponseBody
	public List<RateInterest> getApproLoanRepayTypelist(String productType) {
		// 根据申请的产品类型查询还款方式
		List<RateInterest> loanRepayTypeList = null;
		try {
			Map<String, String> param = new HashMap<String, String>();
			param.put("productType", productType);
			loanRepayTypeList = rateInterestService.getLoanRepayType(param);
		} catch (Exception e) {
			logger.error("查询还款方式失败", e);
		}
		return loanRepayTypeList;
	}

	@RequiresPermissions("credit:checkApprove")
	@RequestMapping(value = "getPricedRisk")
	@ResponseBody
	public AjaxView getPricedRisk(String applyNo) {
		AjaxView ajaxView = new AjaxView();
		BigDecimal pricedRisk = new BigDecimal(0);
		try {
			pricedRisk = pricedRiskService.countPricedRisk(applyNo);
			ajaxView.setSuccess();
			ajaxView.put("pricedRisk", pricedRisk.toString());
		} catch (Exception e) {
			logger.error("风险定价费率计算失败", e);
			ajaxView.setFailed().setMessage("计算定价费率出现问题，请联系管理员");
		}
		return ajaxView;
	}

	@RequestMapping(value = "underCheckApproveIndex")
	public String underCheckApproveIndex(ActTaskParam actTaskParam, String readOnly, Model model) {
		model.addAttribute("actTaskParam", actTaskParam);
		String applyNo = actTaskParam.getApplyNo();
		CheckApprove underCheckApprove = checkApproveService.getByApplyNo(applyNo, Constants.UNDER_DQGLR);
		if (underCheckApprove == null) {
			underCheckApprove = new CheckApprove();
			underCheckApprove.setApproProductTypeCode(Constants.PRODUCT_TYPE_XY);
		}
		model.addAttribute("underCheckApprove", underCheckApprove);
		// 根据申请的产品类型查询还款方式
		Map<String, String> param1 = new HashMap<String, String>();
		param1.put("productType", underCheckApprove.getApproProductTypeCode());
		List<RateInterest> loanRepayTypeList = rateInterestService.getLoanRepayType(param1);
		model.addAttribute("loanRepayTypeList", loanRepayTypeList);
		//查询机构信息
		UnderCompanyInfo underCompanyInfo = underCompanyInfoService.getByApplyNo(applyNo);
		User user = UserUtils.getUser();
		if (user.getOffice() != null) {
			underCompanyInfo.setGqAreaName(user.getOffice().getName());
		}
		model.addAttribute("underOrgInfo", underCompanyInfo);
		if (!Constants.TRUE.equals(readOnly) && !Constants.UNDER_DQGLR.equals(actTaskParam.getTaskDefKey())) {
			readOnly = Constants.TRUE;
		}
		model.addAttribute("readOnly", readOnly);
		return "app/credit/checkApprove/underCheckApproveIndex";
	}

	@RequestMapping(value = "saveUnderCheckApprove")
	@ResponseBody
	public AjaxView saveUnderCheckApprove(CheckApprove underCheckApprove, Model model) {
		AjaxView result = new AjaxView();
		try {
			String flag = checkApproveService.saveUnderCheckApprove(underCheckApprove);
			if (Constants.TRUE.equals(flag)) {
				result.put("id", underCheckApprove.getId());
				result.setSuccess().setMessage("保存借款信息成功!");
				addMessage(model, "保存借款信息成功!");
			} else {
				result.setFailed().setMessage("保存借款信息失败!");
				addMessage(model, "保存借款信息失败!");
			}
		} catch (Exception e) {
			result.setFailed().setMessage("保存借款信息失败!");
			addMessage(model, "保存借款信息失败!");
			logger.error("保存借款信息失败!", e);
		}
		return result;
	}

	@RequestMapping("getLoanYearRateInterest")
	@ResponseBody
	public String getLoanYearRateInterest(String productType, String periodValue, String loanType) {
		List<RateInterest> loanRepayTypeList = null;
		String result = "";
		try {
			RateInterest queryEntity = new RateInterest();
			queryEntity.setLoanRepayType(loanType);
			queryEntity.setProductTypeCode(productType);
			queryEntity.setPeriodValue(periodValue);
			loanRepayTypeList = rateInterestService.checkRateInterestIsDouble(queryEntity);
			if (loanRepayTypeList.size() > 0) {
				RateInterest rateInterest = loanRepayTypeList.get(0);
				BigDecimal yearRateInterest = new BigDecimal(rateInterest.getRateInterest()).multiply(new BigDecimal(12));
				result = yearRateInterest.toString();
			}
		} catch (Exception e) {
			logger.error("查询年利率失败", e);
		}
		return result;
	}

}