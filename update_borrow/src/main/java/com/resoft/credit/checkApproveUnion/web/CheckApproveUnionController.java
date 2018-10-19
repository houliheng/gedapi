package com.resoft.credit.checkApproveUnion.web;

import com.resoft.credit.checkApprove.utils.CheckApproveUtils;
import java.math.BigDecimal;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
import com.resoft.credit.checkApproveUnion.dao.CheckApproveUnionDao;
import com.resoft.credit.checkApproveUnion.entity.CheckApproveUnion;
import com.resoft.credit.checkApproveUnion.entity.CompanyCustInfoRelated;
import com.resoft.credit.checkApproveUnion.service.CheckApproveUnionService;
import com.resoft.credit.checkApproveUnion.service.CompanyCustInfoRelatedService;
import com.resoft.credit.checkFee.entity.CheckFee;
import com.resoft.credit.checkFee.service.CheckFeeService;
import com.resoft.credit.companyInfo.entity.CompanyInfo;
import com.resoft.credit.companyInfo.service.CompanyInfoService;
import com.resoft.credit.contract.entity.Contract;
import com.resoft.credit.contract.service.ContractService;
import com.resoft.credit.custinfo.entity.CustInfo;
import com.resoft.credit.custinfo.service.CustInfoService;
import com.resoft.credit.processSuggestionInfo.entity.ProcessSuggestionInfo;
import com.resoft.credit.processSuggestionInfo.service.ProcessSuggestionInfoService;
import com.resoft.credit.product.entity.Product;
import com.resoft.credit.product.service.ProductService;
import com.resoft.credit.rateInterest.entity.RateInterest;
import com.resoft.credit.rateInterest.service.RateInterestService;
import com.resoft.credit.repayPlanUnion.entity.RepayPlanUnion;
import com.resoft.credit.repayPlanUnion.service.RepayPlanUnionService;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 批复信息授信Controller
 * 
 * @author wangguodong
 * @version 2016-12-14
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/checkApproveUnion")
public class CheckApproveUnionController extends BaseController {

	@Autowired
	private CheckApproveUnionService checkApproveUnionService;

	@Autowired
	private ApplyInfoService applyInfoService;

	@Autowired
	private CustInfoService custInfoService;

	@Autowired
	private CheckApproveService checkApproveService;

	@Autowired
	private CheckFeeService checkFeeService;

	@Autowired
	private ProductService productService;

	@Autowired
	private RepayPlanUnionService repayPlanUnionService;

	@Autowired
	private RateInterestService rateInterestService;

	@Autowired
	private ContractService contractService;

	@Autowired
	private CompanyInfoService companyInfoService;

	@Autowired
	private ApplyRegisterService applyRegisterService;

	@Autowired
	private CompanyCustInfoRelatedService companyCustInfoRelatedService;

	@Autowired
	private ProcessSuggestionInfoService processSuggestionInfoService;
	
	@Autowired
	private CheckApproveUnionDao checkApproveUnionDao;

	@ModelAttribute
	public CheckApproveUnion get(@RequestParam(required = false) String id) {
		CheckApproveUnion entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = checkApproveUnionService.get(id);
		}
		if (entity == null) {
			entity = new CheckApproveUnion();
		}
		return entity;
	}

	@RequiresPermissions("credit:checkApproveUnion:view")
	@RequestMapping(value = "load")
	public String load(ActTaskParam actTaskParam, CheckApproveUnion checkApproveUnion, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("actTaskParam", actTaskParam);
		// 查询总的批复信息
		CheckApprove checkApproveTotal = new CheckApprove();
		Map<String, String> params = Maps.newHashMap();
		params.put("applyNo", actTaskParam.getApplyNo());
		List<CheckApprove> checkApproves = checkApproveService.getCheckApproveByApplyNo(params);
		if (checkApproves != null && checkApproves.size() != 0) {
			checkApproveTotal = checkApproves.get(0);
		}
		// 查询外访费
		CheckFee checkFee = checkFeeService.findByApplyNo(actTaskParam.getApplyNo());
		if (checkFee == null) {
			checkFee = new CheckFee();
		}
		checkApproveTotal.setCheckFee(checkFee.getCheckFee());
		model.addAttribute("checkApproveTotal", checkApproveTotal);
		//
		String approveProductCategoryKey=DictUtils.getDictLabel(checkApproveTotal.getProductCategory(), "product_category", null);
		model.addAttribute("approveProductCategoryKey",approveProductCategoryKey);
		// 查询结论
		params.put("taskDefKey", actTaskParam.getTaskDefKey());
		ProcessSuggestionInfo processSuggestionInfo = processSuggestionInfoService.findByApplyNo(params);
		if (processSuggestionInfo == null) {
			processSuggestionInfo = new ProcessSuggestionInfo();
			processSuggestionInfo.setApplyNo(actTaskParam.getApplyNo());
			processSuggestionInfo.setTaskDefKey(actTaskParam.getTaskDefKey());
		}
		model.addAttribute("processSuggestionInfo", processSuggestionInfo);
		// 产品查询条件
		String productType = checkApproveTotal.getApproProductTypeCode();
		// 当前进件所在机构
		String orgId = null;
		String procDefKey = null;
		try {
			ApplyRegister applyRegister = applyRegisterService.getApplyRegisterByApplyNo(actTaskParam.getApplyNo());
			orgId =applyRegister.getCompany().getId();
			String TypeCode = applyRegister.getApplyProductTypeCode();
			if(Constants.PRODUCT_TYPE_CG.equals(TypeCode)){//显示采购贷新增字段的标志
				model.addAttribute("showCgFlag", "1");
			}
			model.addAttribute("orgId", orgId);
		} catch (Exception e) {
			logger.error("查询进件信息失败！", e);
		}
		try {
			 ApplyRegister applyRegister = applyRegisterService.getApplyRegisterByApplyNo(actTaskParam.getApplyNo());
			procDefKey=applyRegister.getProcDefKey();
			model.addAttribute("procDefKey", procDefKey);
			String TypeCode = applyRegister.getApplyProductTypeCode();
			if(Constants.PRODUCT_TYPE_CG.equals(TypeCode)){//显示采购贷新增字段的标志
				model.addAttribute("showCgFlag", "1");
			}
		} catch (Exception e) {
			logger.error("查询流程Id失败！", e);
		}
		Product product = new Product();
		product.setCompany(new Office(orgId));
		product.setProductTypeCode(productType);
		product.setProcDefKey(procDefKey);
		// 产品列表
		List<Product> productList = productService.findCoProductByType(product);
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
		Map<String, String> param1 = new HashMap<String, String>();
		param1.put("loanRepayType", checkApproveTotal.getApproLoanRepayType());
		param1.put("periodValue", checkApproveTotal.getApproPeriodValue());
		param1.put("approProductTypeCode", checkApproveTotal.getApproProductTypeCode());
		String interest = contractService.getInterest(param1);
		BigDecimal interestD = new BigDecimal("0");
		if (interest != null && StringUtils.isNotEmpty(interest)) {
			interestD = new BigDecimal(interest).multiply(new BigDecimal("12"));
		}
		model.addAttribute("interest", interestD.toString());

		// 根据申请的产品类型查询还款方式
		Map<String, String> param = new HashMap<String, String>();
		param.put("productType", checkApproveTotal.getApproProductTypeCode());
		List<RateInterest> loanRepayTypeList = rateInterestService.getLoanRepayType(param);
		model.addAttribute("loanRepayTypeList", loanRepayTypeList);
		// 保证金月息差及利息月息差判断标识
		String flowCode = CheckApproveUtils.validateFlowCode(checkApproveTotal.getApproProductTypeCode(), actTaskParam.getTaskDefKey());
		model.addAttribute("flowCode", flowCode);
		return "app/credit/checkApproveUnion/checkApproveUnionIndex";
	}

	@RequiresPermissions("credit:checkApproveUnion:view")
	@RequestMapping(value = { "list", "" })
	public String list(CheckApproveUnion checkApproveUnion, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CheckApproveUnion> page = checkApproveUnionService.findPage(new Page<CheckApproveUnion>(request, response), checkApproveUnion);
		model.addAttribute("page", page);
		return "app/credit/checkApproveUnion/checkApproveUnionList";
	}

	@ResponseBody
	@RequiresPermissions("credit:checkApproveUnion:view")
	@RequestMapping(value = "validateSubmit")
	public AjaxView validateSubmit(CheckApproveUnion checkApproveUnion, HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxView ajaxView = new AjaxView();
		try {
			List<CheckApproveUnion> unions = checkApproveUnionService.findCustIdByApplyNo(checkApproveUnion.getApplyNo());
			if (unions != null && unions.size() != 0) {
				for (CheckApproveUnion union : unions) {
					if (StringUtils.isNull(union.getCustId())) {
						ajaxView.setFailed().setMessage("请将所有信息进行授信");
						return ajaxView;
					} /*else {
						if (StringUtils.isNull(union.getRelatedId())) {
							ajaxView.setFailed().setMessage("请将所有关联信息进行授信");
							return ajaxView;
						}
					}*/
				}
				ajaxView.setSuccess();
			} else {
				ajaxView.setFailed().setMessage("请将所有信息进行授信");
			}
		} catch (Exception e) {
			logger.error("提交检验失败", e);
			ajaxView.setFailed().setMessage("提交检验失败");
		}
		return ajaxView;
	}

	@RequiresPermissions("credit:checkApproveUnion:view")
	@RequestMapping(value = "form")
	public String form(CheckApproveUnion checkApproveUnion, String readOnly, ActTaskParam actTaskParam, Model model) {
		model.addAttribute("readOnly", readOnly);
		model.addAttribute("actTaskParam", actTaskParam);
		ApplyInfo applyInfo = applyInfoService.findApplyInfoByApplyNo(actTaskParam.getApplyNo());
		// 从申请客户登记信息表表中查询客户的详细信息
		ApplyRegister applyRegister = applyRegisterService.getApplyRegisterByApplyNo(actTaskParam.getApplyNo());
		applyInfo.setApplyRegister(applyRegister);
		String TypeCode = applyRegister.getApplyProductTypeCode();
		if(Constants.PRODUCT_TYPE_CG.equals(TypeCode)){//显示采购贷新增字段的标志
			model.addAttribute("showCgFlag", "1");
		}
		// 查询主借人信息
		CustInfo mainCust = custInfoService.get(actTaskParam.getCustId());
		if (mainCust == null) {
			mainCust = new CustInfo();
		}
		model.addAttribute("applyInfo", applyInfo);
		model.addAttribute("mainCust", mainCust);
		// 查询企业信息
		CompanyInfo companyInfo = companyInfoService.get(actTaskParam.getCustId());
		if (companyInfo == null) {
			companyInfo = new CompanyInfo();
		}
		model.addAttribute("companyInfo", companyInfo);
		// 查询批复信息
		checkApproveUnion = checkApproveUnionService.get(checkApproveUnion);
		model.addAttribute("checkApproveUnion", checkApproveUnion);
		//
		Map<String, String> params = Maps.newHashMap();
		params.put("applyNo", actTaskParam.getApplyNo());
		List<CheckApprove> checkApproves = checkApproveService.getCheckApproveByApplyNo(params);
		if (checkApproves != null && checkApproves.size() != 0) {
			String approveProductCategoryKey=DictUtils.getDictLabel(checkApproves.get(0).getProductCategory(), "product_category", null);
			model.addAttribute("approveProductCategoryKey",approveProductCategoryKey);
		}
		// 产品查询条件
		String productType = checkApproveUnion.getApproProductTypeCode();
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
		Product product = new Product();
		product.setCompany(new Office(orgId));
		product.setProductTypeCode(productType);
		product.setProcDefKey(procDefKey);
		// 产品列表
		List<Product> productList = productService.findCoProductByType(product);
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

		if (actTaskParam.getTaskDefKey().equalsIgnoreCase(Constants.UTASK_ZGSFKSH) || actTaskParam.getTaskDefKey().equalsIgnoreCase(Constants.UTASK_ZGSJLSH) || actTaskParam.getTaskDefKey().equalsIgnoreCase(Constants.UTASK_ZGSZJLSH)) {
			// 从cre_rate_interest中查询冠E通利率
			Map<String, String> param = new HashMap<String, String>();
			param.put("loanRepayType", checkApproveUnion.getApproLoanRepayType());
			param.put("periodValue", checkApproveUnion.getApproPeriodValue());
			param.put("approProductTypeCode", checkApproveUnion.getApproProductTypeCode());
			String interest = contractService.getInterest(param);
			BigDecimal interestD = new BigDecimal("0");
			if (interest != null && StringUtils.isNotEmpty(interest)) {
				interestD = new BigDecimal(interest).multiply(new BigDecimal("12"));
			}
			model.addAttribute("interest", interestD.toString());
		}

		// 根据申请的产品类型查询还款方式
		Map<String, String> param = new HashMap<String, String>();
		param.put("productType", checkApproveUnion.getApproProductTypeCode());
		List<RateInterest> loanRepayTypeList = rateInterestService.getLoanRepayType(param);
		model.addAttribute("loanRepayTypeList", loanRepayTypeList);

		// 保证金月息差及利息月息差判断标识
		String flowCode = CheckApproveUtils.validateFlowCode(checkApproveUnion.getApproProductTypeCode(), actTaskParam.getTaskDefKey());
		model.addAttribute("flowCode", flowCode);
		return "app/credit/checkApproveUnion/checkApproveUnionDetail";
	}

	@RequiresPermissions("credit:checkApproveUnion:view")
	@RequestMapping(value = { "getRepayPlanList" })
	public String getRepayPlanList(RepayPlanUnion repayPlanUnion, Model model) {
		try {
			Map<String, String> param = Maps.newConcurrentMap();
			param.put("applyNo", repayPlanUnion.getApplyNo());
			param.put("taskDefKey", repayPlanUnion.getTaskDefKey());
			param.put("approId", repayPlanUnion.getApproveId());
			List<RepayPlanUnion> repayPlanUnionList = repayPlanUnionService.getRepayPlanByParam(param);
			model.addAttribute("repayPlanList", repayPlanUnionList);
		} catch (Exception e) {
			logger.error("查询还款计划数据失败", e);
		}
		return "app/credit/repayPlan/checkApproRepayPlanList";
	}

	@RequiresPermissions("credit:checkApproveUnion:edit")
	@RequestMapping(value = "save")
	public String save(CheckApproveUnion checkApproveUnion, Model model, RedirectAttributes redirectAttributes) {
		checkApproveUnionService.save(checkApproveUnion);
		addMessage(redirectAttributes, "保存批复信息授信成功");
		return "redirect:" + Global.getAdminPath() + "/checkApproveUnion/checkApproveUnion/?repage";
	}

	@RequiresPermissions("credit:checkApproveUnion:edit")
	@RequestMapping(value = "delete")
	public String delete(CheckApproveUnion checkApproveUnion, RedirectAttributes redirectAttributes) {
		checkApproveUnionService.delete(checkApproveUnion);
		addMessage(redirectAttributes, "删除批复信息授信成功");
		return "redirect:" + Global.getAdminPath() + "/checkApproveUnion/checkApproveUnion/?repage";
	}

	@RequiresPermissions("credit:checkApproveUnion:edit")
	@RequestMapping(value = "updateRelation")
	public String updateRelation(CheckApproveUnion checkApproveUnion, Model model) {
		List<CheckApproveUnion> checkApproveUnions = checkApproveUnionService.getFreeStaffsByApplyNoOnCompany(checkApproveUnion.getApplyNo());
		model.addAttribute("checkApproveUnions", checkApproveUnions);
		model.addAttribute("checkApproveUnion", checkApproveUnion);
		return "app/credit/checkApproveUnion/checkApproveUnionCompanyList";
	}

	@RequiresPermissions("credit:checkApproveUnion:edit")
	@RequestMapping(value = "updateRelationCustInfo")
	public String updateRelationCustInfo(CheckApproveUnion checkApproveUnion, Model model) {
		List<CheckApproveUnion> checkApproveUnions = checkApproveUnionService.getFreeStaffsByApplyNoOnCustInfo(checkApproveUnion.getApplyNo());
		model.addAttribute("checkApproveUnions", checkApproveUnions);
		model.addAttribute("checkApproveUnion", checkApproveUnion);
		return "app/credit/checkApproveUnion/checkApproveUnionUserList";
	}

	@ResponseBody
	@RequiresPermissions("credit:checkApproveUnion:edit")
	@RequestMapping(value = "saveRelationRelated")
	public AjaxView saveRelationRelated(CheckApproveUnion checkApproveUnion, String newCustId, String newCustName) {
		AjaxView ajaxView = new AjaxView();
		CompanyCustInfoRelated companyCustInfoRelated = new CompanyCustInfoRelated();
		try {
//			companyCustInfoRelated.setApplyNo(checkApproveUnion.getApplyNo());
//			companyCustInfoRelated.setCustId(newCustId);
//			List<CompanyCustInfoRelated> ccrList = companyCustInfoRelatedService.findList(companyCustInfoRelated);
//			if(ccrList.size() > 0){
//				ajaxView.setFailed().setMessage("更新关联人失败,关联人不可重复授信！");
//			}else{
				CompanyCustInfoRelated related = companyCustInfoRelatedService.getCompanyCustInfoRelatedByApproId(checkApproveUnion.getId());
				if(!(StringUtils.isNull(related))){
					companyCustInfoRelated = related;
				}
				companyCustInfoRelated.setApplyNo(checkApproveUnion.getApplyNo());
				companyCustInfoRelated.setApproId(checkApproveUnion.getId());
				companyCustInfoRelated.setCompanyId(checkApproveUnion.getCustId());
				companyCustInfoRelated.setCustId(newCustId);//
				companyCustInfoRelated.setCustName(newCustName);
				companyCustInfoRelatedService.save(companyCustInfoRelated);
				Map<String, String> params = Maps.newHashMap();
				params.put("approveId", checkApproveUnion.getId());
				Contract contract = contractService.getContractByApproveId(params);
				if (!(StringUtils.isNull(contract))) {
					params.put("newCustId", checkApproveUnion.getCustId());
					contractService.updateContractDataByApproveId(params);
				}
				ajaxView.setSuccess().setMessage("更新关联人成功");
//			}
		} catch (Exception e) {
			logger.error("更新关联人失败", e);
			ajaxView.setFailed().setMessage("更新关联人失败");
		}
		return ajaxView;
	}

	@ResponseBody
	@RequiresPermissions("credit:checkApproveUnion:edit")
	@RequestMapping(value = "saveRelation")
	public AjaxView saveRelation(CheckApproveUnion checkApproveUnion, String newCustId) {
		AjaxView ajaxView = new AjaxView();
		try {
			CheckApproveUnion vo = new CheckApproveUnion();
			vo.setApplyNo(checkApproveUnion.getApplyNo());
			vo.setCustId(newCustId);
			List<CheckApproveUnion> ccrList = checkApproveUnionService.findList(vo);
			if(ccrList.size() > 0){
				ajaxView.setFailed().setMessage("更新授信企业失败,授信企业不可重复授信！");
			}else{
				Map<String, String> params = Maps.newHashMap();
				params.put("approveId", checkApproveUnion.getId());
				Contract contract = contractService.getContractByApproveId(params);
				if (!(StringUtils.isNull(contract))) {
					params.put("newCustId", newCustId);
					contractService.updateContractDataByApproveId(params);
				}
				checkApproveUnion.setCustId(newCustId);
				//checkApproveUnionService.save(checkApproveUnion);
				if(StringUtils.isEmpty(checkApproveUnion.getId())) {
					checkApproveUnion.preInsert();
					String checkId = checkApproveUnion.getId().substring(0,14);
					String randomString= (int)((Math.random()*9+1)*100000)+"";
					checkApproveUnion.setId(checkId+randomString);
					checkApproveUnionDao.insert(checkApproveUnion);
				}else {
					checkApproveUnionService.save(checkApproveUnion);
				}
				CompanyCustInfoRelated related = companyCustInfoRelatedService.getCompanyCustInfoRelatedByApproId(checkApproveUnion.getId());
				if (related != null) {
					related.setCompanyId(newCustId);
					companyCustInfoRelatedService.save(related);
				}
				ajaxView.setSuccess().setMessage("更新授信客户成功");
			}
		} catch (Exception e) {
			logger.error("更新授信客户失败", e);
			ajaxView.setFailed().setMessage("更新授信客户失败");
		}
		return ajaxView;
	}

}