package com.resoft.credit.contract.web;

import com.resoft.credit.checkApprove.utils.CheckApproveUtils;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.resoft.common.utils.CreateNumberService;
import com.resoft.common.utils.DateUtils;
import com.resoft.credit.GedAccount.entity.GedAccount;
import com.resoft.credit.GedAccount.service.GedAccountService;
import com.resoft.credit.GedCompanyAccount.entity.CreGedAccountCompany;
import com.resoft.credit.GedCompanyAccount.service.CreGedAccountCompanyService;
import com.resoft.credit.applyInfo.entity.ApplyInfo;
import com.resoft.credit.applyInfo.service.ApplyInfoService;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.applyRegister.entity.ApplyRegister;
import com.resoft.credit.applyRegister.service.ApplyRegisterService;
import com.resoft.credit.applyRelation.entity.ApplyRelation;
import com.resoft.credit.applyRelation.service.ApplyRelationService;
import com.resoft.credit.checkApprove.entity.CheckApprove;
import com.resoft.credit.checkApprove.service.CheckApproveService;
import com.resoft.credit.checkApproveUnion.entity.CheckApproveUnion;
import com.resoft.credit.checkApproveUnion.entity.CompanyCustInfoRelated;
import com.resoft.credit.checkApproveUnion.service.CheckApproveUnionService;
import com.resoft.credit.checkApproveUnion.service.CompanyCustInfoRelatedService;
import com.resoft.credit.companyInfo.entity.CompanyInfo;
import com.resoft.credit.companyInfo.service.CompanyInfoService;
import com.resoft.credit.contract.entity.Contract;
import com.resoft.credit.contract.service.ContractService;
import com.resoft.credit.custinfo.entity.CustInfo;
import com.resoft.credit.custinfo.service.CustInfoService;
import com.resoft.credit.guaranteeCompany.service.CreGuaranteeCompanyService;
import com.resoft.credit.mappingInfo.dao.MappingInfoDao;
import com.resoft.credit.mappingInfo.entity.MappingInfo;
import com.resoft.credit.mappingInfo.service.MappingInfoService;
import com.resoft.credit.mortgagedperson.entity.MortgagedPerson;
import com.resoft.credit.mortgagedperson.service.MortgagedPersonService;
import com.resoft.credit.processSuggestionInfo.entity.ProcessSuggestionInfo;
import com.resoft.credit.processSuggestionInfo.service.ProcessSuggestionInfoService;
import com.resoft.credit.rateInterest.entity.RateInterest;
import com.resoft.credit.rateInterest.service.RateInterestService;
import com.resoft.outinterface.rest.newged.entity.ContractPDFAllResponse;
import com.resoft.outinterface.rest.newged.entity.ContractPDFInstanceResponse;
import com.resoft.outinterface.rest.newged.entity.ContractPDFRequest;
import com.resoft.outinterface.utils.Facade;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.sys.service.AreaService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 合同信息Controller
 * 
 * @author yanwanmei
 * @version 2016-03-02
 */
@Controller("ContractUnionController")
@RequestMapping(value = "${adminPath}/credit/contractUnion")
public class ContractUnionController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(ContractUnionController.class);
	private static String dateStr = DateUtils.getDate("yyMMdd");

	@Autowired
	private ContractService contractService;

	@Autowired
	private AreaService areaService;// 区域地质service

	@Autowired
	private ApplyInfoService applyInfoService;

	@Autowired
	private ApplyRegisterService applyRegisterService;

	@Autowired
	private ProcessSuggestionInfoService processSuggestionInfoService;

	@Autowired
	private MortgagedPersonService mortgagedPersonService;

	@Autowired
	private CreateNumberService createNumberService;

	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private ApplyRelationService applyRelationService;

	@Autowired
	private CustInfoService custInfoService;
	@Autowired
	private CompanyInfoService companyInfoService;

	@Autowired
	private RateInterestService rateInterestService;
	@Autowired
	private MappingInfoService mappingInfoService;
	@Autowired
	private MappingInfoDao mappingInfoDao;
	@Autowired
	private CompanyCustInfoRelatedService companyCustInfoRelatedService;
	@Autowired
	private CheckApproveUnionService checkApproveUnionService;
	@Autowired
	private CheckApproveService checkApproveService;
	@Autowired
	private GedAccountService gedAccountService;
	@Autowired
	private CreGedAccountCompanyService creGedAccountCompanyService;
	@Autowired
	private CreGuaranteeCompanyService creGuaranteeCompanyService;
	@Autowired
	private CreGedAccountCompanyService gedAccountCompanyService;
	@ModelAttribute
	public Contract get(@RequestParam(required = false) String id) {
		Contract entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = contractService.get(id);
		}
		if (entity == null) {
			entity = new Contract();
		}
		return entity;
	}

	@RequiresPermissions("credit:contract:view")
	@RequestMapping(value = { "list", "" })
	public String list(Contract contract, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Contract> page = contractService.findPage(new Page<Contract>(request, response), contract);
		model.addAttribute("page", page);
		return "app/credit/contract/contractList";
	}

	@RequiresPermissions("credit:contract:view")
	@RequestMapping(value = "form")
	public String form(Contract contract, Model model) {
		model.addAttribute("contract", contract);
		return "app/credit/contract/contractForm";
	}

	@RequiresPermissions("credit:contract:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public AjaxView save(ActTaskParam actTaskParam, String readOnly, Contract contract, Model model,
			RedirectAttributes redirectAttributes, ProcessSuggestionInfo processSuggestionInfo,
			HttpServletRequest request) {
		/*
		 * if (!beanValidator(model, contract)) { return form(contract, model);
		 * }
		 */
		AjaxView ajaxView = new AjaxView();
		try {
			String bankNo = DictUtils.getDictValue(contract.getBankNo(), "BANKS", "");
			contract.setBankNo(bankNo);
			String midBankNo = DictUtils.getDictValue(contract.getMiddlemanBankNo(), "BANKS", "");
			contract.setMiddlemanBankNo(midBankNo);
			if (Constants.YES_NO_Y.equals(contract.getIsAccord())) {
				contract.setRepBankProvince(contract.getRecBankProvince());
				contract.setRepBankCity(contract.getRecBankCity());
				contract.setRepBankDistinct(contract.getRecBankDistinct());
				contract.setRepBank(contract.getRecBank());
			}

			Map<String, String> orgLevelsMap = contractService.findOrgLevelByOrgId(request.getParameter("orgId"));
			if (orgLevelsMap.get("orgLevel2") != null && !"".equals(orgLevelsMap.get("orgLevel2"))) {
				contract.setOrgLevel2(orgLevelsMap.get("orgLevel2"));
			}
			if (orgLevelsMap.get("orgLevel3") != null && !"".equals(orgLevelsMap.get("orgLevel3"))) {
				contract.setOrgLevel3(orgLevelsMap.get("orgLevel3"));
			}
			if (orgLevelsMap.get("orgLevel4") != null && !"".equals(orgLevelsMap.get("orgLevel4"))) {
				contract.setOrgLevel4(orgLevelsMap.get("orgLevel4"));
			}

			String message = null;

			synchronized (ContractUnionController.class) {
				if (contract.getContractNo() == null || StringUtils.isBlank(contract.getContractNo())) {
					Map<String, String> params = Maps.newHashMap();
					params.put("applyNo", actTaskParam.getApplyNo());
					String currContractNo = contractService.getCurrContractNo(params);
					String contractNo = "";
					// 合同编号不存在时，设置合同编号
					if (currContractNo == null || StringUtils.isBlank(currContractNo)) {
						MappingInfo mappingInfo = mappingInfoService.get(contract.getOperateOrgId());
						// 如果映射表中没有数据，则插入数据
						if (mappingInfo == null) {
							mappingInfo = new MappingInfo();
							mappingInfo.preInsert();
							mappingInfo.setId(contract.getOperateOrgId());
							mappingInfo.setContractNoFirst("0");
							mappingInfo.setContractNoSecond("0");
							mappingInfoDao.insert(mappingInfo);
						} else {
							if (Integer.parseInt(mappingInfo.getContractNoFirst()) > 25) {
								ajaxView.setFailed().setMessage("生成合同号失败，请联系管理员");
								return ajaxView;
							}
						}
						List<Integer> flagList = new ArrayList<Integer>();
						flagList.add(0, Integer.parseInt(mappingInfo.getContractNoFirst()));
						flagList.add(1, Integer.parseInt(mappingInfo.getContractNoSecond()));
						List<String> receiveList = createNumberService.createContractNo(
								contract.getApproProductTypeCode(), contract.getOperateOrgId(), flagList, dateStr);
						// 更新当前时间
						dateStr = (String) receiveList.get(2);
						String contractNoTemp = receiveList.get(0);
						while (contractService.getContractByContractNo(contractNoTemp) != null) {
							flagList.clear();
							flagList.add(0, Integer.parseInt(receiveList.get(3)));
							flagList.add(1, Integer.parseInt(receiveList.get(4)));
							receiveList = createNumberService.createContractNo(contract.getLoanModel(),
									contract.getOperateOrgId(), flagList, dateStr);
							dateStr = (String) receiveList.get(2);
							contractNoTemp = receiveList.get(0);
						}
						// contract.setContractNo(receiveList.get(0));
						contractNo = receiveList.get(0);
						logger.info("生成的合同编号为：" + contract.getContractNo());

						// 联合授信合同号生成规则：总合同号-数量（A-Z表示）-[A-Z]
						List<CheckApproveUnion> approveList = checkApproveUnionService
								.findApproveListByApplyNo(contract.getApplyNo());
						char count = 0;
						count = (char) (approveList.size() + 64);
						contractNo = contractNo + "-" + count + "-A";
						contract.setContractNo(contractNo);
					} else {
						int len = currContractNo.length();
						String contractNoPre = currContractNo.substring(0, len - 1);
						char last = currContractNo.charAt(len - 1);
						char curr = (char) ((int) last + 1);
						contractNo = contractNoPre + curr;
						contract.setContractNo(contractNo);
					}
				}
			}

			// 合同起始日期设置为当前时间
			contract.setConStartDate(DateUtils.getCurrDateTime());

			contract.setRecBankcardName(contract.getRecBankcardId());

			/*CustInfo custInfo = custInfoService.get(contract.getRecBankcardName());
			contract.setCustName(custInfo.getCustName());
			contract.setIdType(custInfo.getIdType());
			contract.setIdNum(custInfo.getIdNum());*/
			CompanyInfo companyInfo = companyInfoService.get(contract.getRecBankcardName());
			contract.setCustName(companyInfo.getBusiRegName());
			contract.setIdType(companyInfo.getCorporationCardType());
			contract.setIdNum(companyInfo.getCorporationCardIdNo());
			String approId = request.getParameter("approId");
			message = contractService.insertContractAndRepayPlanUnion(contract, approId);
			if ("success".equalsIgnoreCase(message)) {
				contractService.updateGuranteeRelation(actTaskParam.getApplyNo(),contract.getCustId(),contract.getContractNo());
				}
			if ("success".equalsIgnoreCase(message)) {
				ajaxView.setSuccess().setMessage("保存合同信息，生成还款计划成功");
				ajaxView.put("contractNo", contract.getContractNo());
				ajaxView.put("conStartDate", new SimpleDateFormat("yyyy-MM-dd").format(contract.getConStartDate()));
				ajaxView.put("conEndDate", new SimpleDateFormat("yyyy-MM-dd").format(contract.getConEndDate()));
			} else {
				ajaxView.setFailed().setMessage("保存合同信息或生成还款计划失败");
			}
		} catch (Exception e) {
			logger.error("保存合同面签出现异常！", e);
			ajaxView.setFailed().setMessage("保存合同信息或生成还款计划失败");
		}
		ajaxView.put("applyNo", actTaskParam.getApplyNo());
		ajaxView.put("id", contract.getId());
		return ajaxView;
	}

	@RequiresPermissions("credit:contract:edit")
	@RequestMapping(value = "delete")
	public String delete(Contract contract, RedirectAttributes redirectAttributes) {
		contractService.delete(contract);
		addMessage(redirectAttributes, "删除合同信息成功");
		return "redirect:" + Global.getAdminPath() + "/contract/contract/?repage";
	}

	@RequiresPermissions("credit:contract:view")
	@RequestMapping(value = "saveSuggestion")
	// 当点“提交”时，将外访意见保存到cre_process_suggestion_info表中
	public String saveSuggestion(ActTaskParam actTaskParam, String readOnly, Contract contract,
			ProcessSuggestionInfo processSuggestionInfo, String passFlag, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		String checkFlag = null;
		Contract contractt = contractService.getContractByApplyNo(actTaskParam.getApplyNo());
		checkFlag = contractService.saveSuggestion(actTaskParam, passFlag, contractt, processSuggestionInfo);
		if ("error".equals(checkFlag)) {
			// return super.renderString(response, "500");
			model.addAttribute("message", "提交出现错误，请联系管理员");
		} else {
			processSuggestionInfo.setUpdateBy(UserUtils.getUser().getUpdateBy());
			processSuggestionInfo.setUpdateDate(UserUtils.getUser().getUpdateDate());
			processSuggestionInfoService.insertFlag(processSuggestionInfo, actTaskParam.getTaskDefKey());
		}
		return contractSignInfo(actTaskParam, readOnly, processSuggestionInfo, contract, model, checkFlag);
	}

	@RequestMapping(value = "contractInfo")
	public String contractInfo(HttpServletRequest request, HttpServletResponse reponse) {
		String contractNo = request.getParameter("contractNo");
		String url = Global.getConfig("ACC_VISIT_URL");
		return "redirect:" + url + "/f/accounting/Acccontract/contractInfo?contractNo=" + contractNo;
	}

	@RequiresPermissions("credit:contract:view")
	@RequestMapping(value = "contractSignInfo")
	public String contractSignInfo(ActTaskParam actTaskParam, String readOnly,
			ProcessSuggestionInfo processSuggestionInfo, Contract contract, Model model, String checkFlag) {
		try {
			boolean returnFlag = actTaskService.isReturned(actTaskParam.getTaskId());
			model.addAttribute("returnFlag", returnFlag);
		} catch (Exception e1) {
			// logger.error("判断是否被打回出现异常！", e1);
		}
		String applyNo = actTaskParam.getApplyNo();
		List<CheckApproveUnion> checkApproveUnionList = checkApproveUnionService.findApproveListByApplyNo(applyNo);
		// 算总合同金额
		BigDecimal contractAmount = new BigDecimal(0);
		if (checkApproveUnionList != null) {
			for (int i = 0; i < checkApproveUnionList.size(); i++) {
				if(i == 0){
					//联合授信打印时，取出公共合同编号部分
					String contractNo = checkApproveUnionList.get(i).getContractNo(); 
					if(contractNo != null){
						model.addAttribute("originContractNo", contractNo.split("-")[0]);
					}
				}
				contractAmount = contractAmount.add(checkApproveUnionList.get(i).getContractAmount());
			}
		}
		model.addAttribute("contractAmount", contractAmount);
		model.addAttribute("checkApproveUnionList", checkApproveUnionList);
		model.addAttribute("actTaskParam", actTaskParam);
		model.addAttribute("checkFlag", checkFlag);
		model.addAttribute("readOnly", readOnly);
		model.addAttribute("taskDefKeyFlag", actTaskParam.getTaskDefKey());
		// 查询面签意见
		Map<String, String> params = Maps.newHashMap();
		ProcessSuggestionInfo contractSignSuggestion = new ProcessSuggestionInfo();
		params.put("applyNo", actTaskParam.getApplyNo());
		params.put("taskDefKey", Constants.UTASK_HTMQ);
		contractSignSuggestion = processSuggestionInfoService.findByApplyNo(params);
		if (contractSignSuggestion == null) {
			contractSignSuggestion = new ProcessSuggestionInfo();
		}
		model.addAttribute("contractSignSuggestion", contractSignSuggestion);
		// 查询法务审核意见
		ProcessSuggestionInfo legalCheckSuggestion = new ProcessSuggestionInfo();
		params.put("applyNo", actTaskParam.getApplyNo());
		params.put("taskDefKey", Constants.UTASK_FWSH);
		legalCheckSuggestion = processSuggestionInfoService.findByApplyNo(params);
		if (legalCheckSuggestion == null) {
			legalCheckSuggestion = new ProcessSuggestionInfo();
		}
		model.addAttribute("legalCheckSuggestion", legalCheckSuggestion);
		model.addAttribute("applyNo", actTaskParam.getApplyNo());
		// 查询合同审核意见
		ProcessSuggestionInfo contractCheckSuggestion = new ProcessSuggestionInfo();
		params.put("applyNo", actTaskParam.getApplyNo());
		params.put("taskDefKey", Constants.UTASK_HTSH);
		contractCheckSuggestion = processSuggestionInfoService.findByApplyNo(params);
		if (contractCheckSuggestion == null) {
			contractCheckSuggestion = new ProcessSuggestionInfo();
		}
		model.addAttribute("contractCheckSuggestion", contractCheckSuggestion);
		CheckApprove approveOld = checkApproveService.getCheckApproveBackUp(applyNo);
		if(approveOld != null){
			Map<String, String> param11 = Maps.newHashMap();
			param11.put("applyNo", applyNo);
			List<CheckApprove> approves = checkApproveService.getCheckApproveByApplyNo(param11);
			CheckApprove approveNew = approves.get(0);
			List<String> messages = checkApproveService.contrastObj(approveOld,approveNew);
			if(messages.size() != 0){
				model.addAttribute("confirmxFlag", "true");
			}else{
				model.addAttribute("confirmxFlag", "false");
			}
		}
		return "app/credit/contract/contractSignInfoUnionIndex";
	}

	@RequiresPermissions("credit:contract:view")
	@RequestMapping(value = "contractSignForm")
	public String contractSignForm(ActTaskParam actTaskParam, String readOnly,
			ProcessSuggestionInfo processSuggestionInfo, Contract contract, Model model, String checkFlag,
			String custId, String approId) {
		String applyNo = actTaskParam.getApplyNo();
		ApplyRegister applyRegister = applyRegisterService.getApplyRegisterByApplyNo(actTaskParam.getApplyNo());
		String TypeCode = applyRegister.getApplyProductTypeCode();
		if(Constants.PRODUCT_TYPE_CG.equals(TypeCode)){//显示采购贷新增字段的标志
			model.addAttribute("showCgFlag", "1");
		}
		// 判断是否是被打回过的任务
		/*
		 * try { boolean returnFlag =
		 * actTaskService.isReturned(actTaskParam.getTaskId());
		 * model.addAttribute("returnFlag", returnFlag); } catch (Exception e1)
		 * { logger.error("判断是否被打回出现异常！", e1); }
		 */
		if (actTaskParam.getStatus().equals("1")) {
			readOnly = "true";
		}
		model.addAttribute("readOnly", readOnly);
		model.addAttribute("checkFlag", checkFlag);

		Map<String, String> paramContract = Maps.newConcurrentMap();
		/*
		 * paramContract.put("applyNo", applyNo); paramContract.put("custId",
		 * custId);
		 */
		paramContract.put("approveId", approId);
		contract = contractService.getContractByApproveId(paramContract);

		if (contract == null) {
			contract = new Contract();
		}

		CheckApproveUnion approveUnion = checkApproveUnionService.get(approId);
		contract.setQualityServiceMarginAmount(approveUnion.getQualityServiceMarginAmount());
		contract.setQualityServiceMarginRate(approveUnion.getQualityServiceMarginRate());

		// 开户状态
		String openAccountStatusLabel = DictUtils.getDictLabel(contract.getOpenAccountStatus(),
				Constants.OPEN_ACCOUNT_STATUS, "");
		if (openAccountStatusLabel == null || StringUtils.isBlank(openAccountStatusLabel)) {
			openAccountStatusLabel = DictUtils.getDictLabel(Constants.OPEN_ACCOUNT_STATUS_WKH,
					Constants.OPEN_ACCOUNT_STATUS, "");
		}
		model.addAttribute("openAccountStatusLabel", openAccountStatusLabel);

		if (contract.getMianContract() == null || StringUtils.isBlank(contract.getMianContract())) {
			contract.setMianContract(Constants.MIAN_CONTRACT_JKZHT);
		}
		// 查询主借人信息
		CustInfo mainCust = custInfoService.get(custId);
		if (mainCust == null) {
			mainCust = new CustInfo();
			// model.addAttribute("message", "主借人信息出现异常，请联系管理员！");
		}
		model.addAttribute("mainCust", mainCust);
		// 查询关联企业信息
		CompanyInfo companyInfo = companyInfoService.get(custId);
		if (companyInfo == null) {
			companyInfo = new CompanyInfo();
		}
		model.addAttribute("companyInfo", companyInfo);
		// 收/还款银行账户名称
		// if (contract.getRecBankcardName() == null ||
		// StringUtils.isBlank(contract.getRecBankcardName())) {
		// contract.setRecBankcardName(mainCust.getCustName());
		// }
		// if (contract.getRepBankcardName() == null ||
		// StringUtils.isBlank(contract.getRepBankcardName())) {
		// contract.setRepBankcardName(mainCust.getCustName());
		// }
		// // 收/还款人电话
		// if (contract.getRecBankMobile() == null ||
		// StringUtils.isBlank(contract.getRecBankMobile())) {
		// contract.setRecBankMobile(mainCust.getMobileNum());
		// }
		// if (contract.getRepBankMobile() == null ||
		// StringUtils.isBlank(contract.getRepBankMobile())) {
		// contract.setRepBankMobile(mainCust.getMobileNum());
		// }
		// // 收/还款人身份证号
		// if (contract.getRecBankIdNum() == null ||
		// StringUtils.isBlank(contract.getRecBankIdNum())) {
		// contract.setRecBankIdNum(mainCust.getIdNum());
		// }
		// if (contract.getRepBankIdNum() == null ||
		// StringUtils.isBlank(contract.getRepBankIdNum())) {
		// contract.setRepBankIdNum(mainCust.getIdNum());
		// }
		// 合同预约时间
		Map<String, String> param = Maps.newConcurrentMap();
		param.put("applyNo", actTaskParam.getApplyNo());
		param.put("taskDefKey", Constants.UTASK_HTYY);
		ProcessSuggestionInfo appointInfo = processSuggestionInfoService.findByApplyNo(param);
		if (appointInfo != null) {
			String reserveTime = appointInfo.getReserveTime();
			model.addAttribute("reserveTime", reserveTime);
		}

		String bankNo = DictUtils.getDictLabel(contract.getBankNo(), "BANKS", null);
		contract.setBankNo(bankNo);//-----------------------------------------------------
		// 获取省级下拉
		LinkedHashMap<String, String> contractProvinceMap = loadAreaData("1");// 这里的1表示全国
		// 获取市级下拉
		LinkedHashMap<String, String> contractCityMap = loadAreaData(contract.getContProvince());
		// 获取区级下拉
		LinkedHashMap<String, String> contractDistinctMap = loadAreaData(contract.getContCity());

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
		//--------------------------------------------------------------------
		/*String contProvice = contract.getContProvince();
		contract.setContProvince(contractProvinceMap.get(contProvice));

		String contCity = contract.getContCity();
		contract.setContCity(contractCityMap.get(contCity));

		String contDistinct = contract.getContDistinct();
		contract.setContDistinct(contractDistinctMap.get(contDistinct));*/

		// 查询申请信息
		ApplyInfo applyInfo = applyInfoService.findApplyInfoByApplyNo(actTaskParam.getApplyNo());
		if (applyInfo == null) {
			applyInfo = new ApplyInfo();
		}
		model.addAttribute("applyInfo", applyInfo);

		// 查询联合授信批复信息
		CheckApproveUnion checkApprove = checkApproveUnionService.get(approId);
		if (checkApprove == null) {
			checkApprove = new CheckApproveUnion();
		}
		model.addAttribute("checkApprove", checkApprove);
		
		 Map<String, String> parames = Maps.newHashMap();
		 parames.put("applyNo", actTaskParam.getApplyNo());
		 List<CheckApprove> checkApproves = checkApproveService.getCheckApproveByApplyNo(parames);
		 if (checkApproves != null && checkApproves.size() != 0) {
		   String approveProductCategoryKey=DictUtils.getDictLabel(checkApproves.get(0).getProductCategory(), "product_category", null);
		   model.addAttribute("approveProductCategoryKey",approveProductCategoryKey);
		 }
		
		CheckApprove approveOld = checkApproveService.getCheckApproveBackUp(applyNo);
		if(approveOld != null){
			if (!(checkApprove.getRemark().equalsIgnoreCase(approveOld.getRemark()))) {
				model.addAttribute("oldTextFlag", "1");
			}
			model.addAttribute("oldText", approveOld.getRemark());
			Map<String, String> param11 = Maps.newHashMap();
			param11.put("applyNo", applyNo);
			List<CheckApprove> approves = checkApproveService.getCheckApproveByApplyNo(param11);
			CheckApprove approveNew = approves.get(0);
			List<String> messages = checkApproveService.contrastObj(approveOld,approveNew);
			if(messages.size() != 0){
				model.addAttribute("oldMessage", "1");
				model.addAttribute("messages", messages);
			}
		}
		// 查找总公司批复意见
		@SuppressWarnings("unused")
		ProcessSuggestionInfo hasProcessSuggestionInfo = new ProcessSuggestionInfo();
		Map<String, String> params = Maps.newHashMap();
		params.put("applyNo", actTaskParam.getApplyNo());
		params.put("taskDefKey", Constants.UTASK_ZGSFKSH);
		processSuggestionInfo = processSuggestionInfoService.findByApplyNo(params);
		if (processSuggestionInfo == null) {
			processSuggestionInfo = new ProcessSuggestionInfo();
		}
		model.addAttribute("processSuggestionInfo", processSuggestionInfo);

		// 查询关联人信息
		CompanyCustInfoRelated related = companyCustInfoRelatedService.getCompanyCustInfoRelatedByApproId(approId);
		//----------contract.setRecBankcardName(related.getCustId());//------------------------------------------------------------关联的那个人的id
		//----------contract.setRecBankcardId(related.getCustId());//------------------------------------------------------------
		// 向前台传递任务编号，以此来处理合同面前，法务审核和合同审核
		model.addAttribute("taskDefKeyFlag", actTaskParam.getTaskDefKey());
		if (StringUtils.isNull(contract.getIsOrNoMIddleMan())) {
			contract.setIsOrNoMIddleMan("0");
		}
		//----------------------------------------
		if("2".equalsIgnoreCase(applyRegister.getCustType())){
			GedAccount gedAccount = null;
			gedAccount = gedAccountService.getByCustID(approId);
			if(gedAccount!=null&&gedAccount.getId()!=null) {
					contract.setRecBankcardNo(gedAccount.getCompanyAccount());//收款银行卡号：
					contract.setRecBank(gedAccount.getCompanyBankOfDeposit());//收款银行：
					contract.setRecBankName(gedAccount.getCompanyBankBranchName());//收款开户行名称：
					contract.setRecBankDetail(gedAccount.getProvinceName()+gedAccount.getCityName());
					contract.setRecBankcardName(gedAccount.getLegalPerName());
					contract.setRecBankcardId(gedAccount.getCompanyId());
					contract.setRecBankMobile(gedAccount.getLegalPerPhone());
					contract.setRecBankIdNum(gedAccount.getLegalPerNum());
					contract.setRepBankcardName(gedAccount.getLegalPerName());
					contract.setRepBankMobile(gedAccount.getLegalPerPhone());
					contract.setRepBankIdNum(gedAccount.getLegalPerNum());  
			}
			CreGedAccountCompany creGedAccountCompany = creGedAccountCompanyService.getCompanyAccount(approId);
			if(creGedAccountCompany==null) {
				creGedAccountCompany=new CreGedAccountCompany();
			}
			if (creGedAccountCompany != null) {
				// 获取省级下拉
				LinkedHashMap<String, String> contractProvince = loadAreaData("1");// 这里的1表示全国
				// 获取市级下拉
				LinkedHashMap<String, String> contractCity = loadAreaData(creGedAccountCompany.getProvinceCode());
				// 获取区级下拉
				LinkedHashMap<String, String> contractDistinct = loadAreaData(creGedAccountCompany.getCityCode());
				contract.setRecBankcardNo(creGedAccountCompany.getCompanyAccount());//收款银行卡号：
				contract.setRecBank(creGedAccountCompany.getCompanyBankOfDeposit());//收款银行：
				contract.setRecBankName(creGedAccountCompany.getCompanyBankBranchName());//收款开户行名称：
				contract.setRecBankDetail(contractProvince.get(creGedAccountCompany.getProvinceCode())+contractCity.get(creGedAccountCompany.getCityCode())+ contractDistinct.get(creGedAccountCompany.getAreaCode())+creGedAccountCompany.getCompanyBankBranchName());
				if (StringUtils.isBlank(contract.getRecBankDetail())) {
					contract.setRecBankDetail("");
				}
				contract.setRecBankcardName(creGedAccountCompany.getCompanyName());
				contract.setRecBankcardId(creGedAccountCompany.getCompanyId());
				contract.setRecBankMobile(creGedAccountCompany.getContactMobile());
				contract.setRecBankIdNum(creGedAccountCompany.getLegalCardNumber());
				contract.setRepBankcardName(creGedAccountCompany.getLegalName());
				contract.setRepBankMobile(creGedAccountCompany.getLegalMobile());
				contract.setRepBankIdNum(creGedAccountCompany.getLegalCardNumber()); 
			}
			model.addAttribute("creGedAccountCompany", creGedAccountCompany);
		}else{
			GedAccount gedAccount = null;
			gedAccount = gedAccountService.getByCustPhoneID(approId);
			if(gedAccount!=null&&gedAccount.getId()!=null) {
				contract.setRecBankcardNo(gedAccount.getCompanyAccount());//收款银行卡号：
				contract.setRecBank(gedAccount.getCompanyBankOfDeposit());//收款银行：
				contract.setRecBankName(gedAccount.getCompanyBankBranchName());//收款开户行名称：
				contract.setRecBankDetail(gedAccount.getProvinceName()+gedAccount.getCityName());
				contract.setRecBankcardName(gedAccount.getLegalPerName());
				contract.setRecBankcardId(gedAccount.getCompanyId());
				contract.setRecBankMobile(gedAccount.getLegalPerPhone());
				contract.setRecBankIdNum(gedAccount.getLegalPerNum());
				contract.setRepBankcardName(gedAccount.getLegalPerName());
				contract.setRepBankMobile(gedAccount.getLegalPerPhone());
				contract.setRepBankIdNum(gedAccount.getLegalPerNum());
			}
			CreGedAccountCompany creGedAccountCompany = creGedAccountCompanyService.getCompanyAccount(approId);
			if(creGedAccountCompany==null) {
				creGedAccountCompany=new CreGedAccountCompany();
			}
			model.addAttribute("creGedAccountCompany", creGedAccountCompany);
		}
		//----------------------------------------
		model.addAttribute("contract", contract);

		// 生成还款计划表中的数据
		// Date dateSign = DateUtils.getNextMonth(contract.getOccurDate());
		// List<RepayPlan> repayPlanList =
		// contractService.calculateRepayPlan(checkApprove,contract.getContractNo(),dateSign);
		//
		// repayPlanService.insertRepayPlanList(repayPlanList);
		//
		// model.addAttribute("repayPlanList", repayPlanList);
		// 先删除批复信息阶段生成的还款计划表中的数据
		// repayPlanService.deleteRepayPlanByApplyNoAndContractNo(actTaskParam.getApplyNo());

		// 查询抵押权人信息
		List<MortgagedPerson> mortgagedPersonList = mortgagedPersonService.getMortgagedPerson();
		model.addAttribute("mortgagedPersonList", mortgagedPersonList);

		// 查询中间人信息
		List<MortgagedPerson> middlePersonList = mortgagedPersonService.getMiddlePerson();
		model.addAttribute("middlePersonList", middlePersonList);

		// 查询收款信息列表
		List<ApplyRelation> applyRelations = applyRelationService.getCheckCoupleDoubtfulByApplyNo(applyNo);
		model.addAttribute("applyRelations", applyRelations);

		// 根据申请的产品类型查询还款方式
		Map<String, String> para = new HashMap<String, String>();
		para.put("productType", checkApprove.getApproProductTypeCode());
		para.put("loanRepayType", checkApprove.getApproLoanRepayType());
		List<RateInterest> loanRepayTypeList = rateInterestService.getLoanRepayType(para);
		if (loanRepayTypeList.size() > 0) {
			String approLoanRepayTypeName = loanRepayTypeList.get(0).getLoanRepayDesc();
			model.addAttribute("approLoanRepayTypeName", approLoanRepayTypeName);
		}
		model.addAttribute("loanRepayTypeList", loanRepayTypeList);

		// 保证金月息差及利息月息差判断标识
		String flowCode = CheckApproveUtils.validateFlowCode(checkApprove.getApproProductTypeCode(), actTaskParam.getTaskDefKey());
		model.addAttribute("flowCode", flowCode);
		return "app/credit/contract/contractSignInfoUnionForm";

	}

	// 省市级联数据加载
	private LinkedHashMap<String, String> loadAreaData(String areaCode) {
		Map<String, String> param = Maps.newConcurrentMap();
		LinkedHashMap<String, String> areaMap = new LinkedHashMap<String, String>();
		if (StringUtils.isNotEmpty(areaCode)) {
			param.put("parentId", areaCode);// 根据市级ID获取区县数据信息
			List<Map<String, String>> regDistinctList = this.areaService.getTreeNode(param);
			if (null != regDistinctList && regDistinctList.size() > 0) {
				for (Map<String, String> mp : regDistinctList) {
					areaMap.put(mp.get("id"), mp.get("name"));
				}
			}
		}
		return areaMap;
	}

	@ResponseBody
	@RequiresPermissions("credit:contract:view")
	@RequestMapping(value = "submitValidate")
	public AjaxView submitValidate(ActTaskParam actTaskParam, String passFlag) {
		AjaxView ajaxView = new AjaxView();
		if (passFlag.equals("yes")) {
			ApplyRegister applyRegister = applyRegisterService.getApplyRegisterByApplyNo(actTaskParam.getApplyNo());
			if(Constants.PROC_DEF_KEY_LHSX.equalsIgnoreCase(applyRegister.getProcDefKey())&&Constants.UTASK_HTMQ.equals(actTaskParam.getTaskDefKey())){
				/*String checkSignContract =  contractService.checkIsSignContract(actTaskParam.getApplyNo());
				if(StringUtils.isNoneBlank(checkSignContract)) {
					return ajaxView.setFailed().setMessage(checkSignContract);
				}*/
				/*String openAccountResult = gedAccountService.checkIsOpenAccount(actTaskParam.getApplyNo());
				if(StringUtils.isNoneBlank(openAccountResult)) {
					return ajaxView.setFailed().setMessage(openAccountResult);
				}
				/*String configResult = contractService.checkIsConfig(actTaskParam.getApplyNo());
				if(StringUtils.isNoneBlank(configResult)) {
					return ajaxView.setFailed().setMessage(configResult);
				}*/
			}
			String applyNo = actTaskParam.getApplyNo();
			// 判断联合授信批复个数和合同数相同
			List<CheckApproveUnion> unionList = checkApproveUnionService.findApproveListByApplyNo(applyNo);
			Map<String, String> param = Maps.newConcurrentMap();
			param.put("applyNo", applyNo);
			List<Contract> contractList = contractService.findListByApplyNo(param);
			if (unionList != null && contractList != null && unionList.size() == contractList.size()) {
				ajaxView.setSuccess();
			} else {
				ajaxView.setFailed().setMessage("合同面签未完成！");
			}
		} else {
			ajaxView.setSuccess();
		}

		return ajaxView;
	}

	

	@RequiresPermissions("credit:contract:view")
	@RequestMapping(value = "contractAuditForm")
	public String contractAuditForm(ActTaskParam actTaskParam, String readOnly,
			ProcessSuggestionInfo processSuggestionInfo, Contract contract, Model model, String checkFlag,
			String custId, String approId) {
		String applyNo = actTaskParam.getApplyNo();
		if (actTaskParam.getStatus().equals("1")) {
			readOnly = "true";
		}
		model.addAttribute("readOnly", readOnly);
		model.addAttribute("checkFlag", checkFlag);

		ApplyRegister applyRegister = applyRegisterService.getApplyRegisterByApplyNo(applyNo);
		model.addAttribute("applyRegister", applyRegister);

		String productCategoryKey=DictUtils.getDictLabel(applyRegister.getProductCategory(),"product_category", null);
		model.addAttribute("productCategoryKey",productCategoryKey);

		//采购贷新增字段显示
		String TypeCode = applyRegister.getApplyProductTypeCode();
		if(Constants.PRODUCT_TYPE_CG.equals(TypeCode)){//显示采购贷新增字段的标志
			model.addAttribute("showCgFlag", "1");
		}

		Map<String, String> paramContract = Maps.newConcurrentMap();
		paramContract.put("approveId", approId);
		contract = contractService.getContractByApproveId(paramContract);

		if (contract == null) {
			contract = new Contract();
		}
		// 开户状态
		String openAccountStatusLabel = DictUtils.getDictLabel(contract.getOpenAccountStatus(),
				Constants.OPEN_ACCOUNT_STATUS, "");
		if (openAccountStatusLabel == null || StringUtils.isBlank(openAccountStatusLabel)) {
			openAccountStatusLabel = DictUtils.getDictLabel(Constants.OPEN_ACCOUNT_STATUS_WKH,
					Constants.OPEN_ACCOUNT_STATUS, "");
		}
		model.addAttribute("openAccountStatusLabel", openAccountStatusLabel);

		if (contract.getMianContract() == null || StringUtils.isBlank(contract.getMianContract())) {
			contract.setMianContract(Constants.MIAN_CONTRACT_JKZHT);
		}

		// 合同预约时间
		Map<String, String> param = Maps.newConcurrentMap();
		param.put("applyNo", actTaskParam.getApplyNo());
		param.put("taskDefKey", Constants.UTASK_HTYY);
		ProcessSuggestionInfo appointInfo = processSuggestionInfoService.findByApplyNo(param);
		if (appointInfo != null) {
			String reserveTime = appointInfo.getReserveTime();
			model.addAttribute("reserveTime", reserveTime);
		}

		String bankNo = DictUtils.getDictLabel(contract.getBankNo(), "BANKS", null);
		contract.setBankNo(bankNo);
		// 获取省级下拉
		LinkedHashMap<String, String> contractProvinceMap = loadAreaData("1");// 这里的1表示全国
		// 获取市级下拉
		LinkedHashMap<String, String> contractCityMap = loadAreaData(contract.getContProvince());
		// 获取区级下拉
		LinkedHashMap<String, String> contractDistinctMap = loadAreaData(contract.getContCity());

		String contProvice = contract.getContProvince();
		contract.setContProvince(contractProvinceMap.get(contProvice));

		String contCity = contract.getContCity();
		contract.setContCity(contractCityMap.get(contCity));

		String contDistinct = contract.getContDistinct();
		contract.setContDistinct(contractDistinctMap.get(contDistinct));

		// 查询联合授信批复信息
		CheckApproveUnion checkApprove = checkApproveUnionService.get(approId);
		if (checkApprove == null) {
			checkApprove = new CheckApproveUnion();
		}
		model.addAttribute("CGapproves", checkApprove);

		 Map<String, String> parames = Maps.newHashMap();
		  parames.put("applyNo", actTaskParam.getApplyNo());
		  List<CheckApprove> checkApproves = checkApproveService.getCheckApproveByApplyNo(parames);
		  if (checkApproves != null && checkApproves.size() != 0) {
		   String approveProductCategoryKey=DictUtils.getDictLabel(checkApproves.get(0).getProductCategory(), "product_category", null);
		   model.addAttribute("approveProductCategoryKey",approveProductCategoryKey);
		  }
		CheckApprove approveOld = checkApproveService.getCheckApproveBackUp(applyNo);
		if(approveOld != null){
			if (!(checkApprove.getRemark().equalsIgnoreCase(approveOld.getRemark()))) {
				model.addAttribute("oldTextFlag", "1");
			}
			model.addAttribute("oldText", approveOld.getRemark());

			Map<String, String> param11 = Maps.newHashMap();
			param11.put("applyNo", applyNo);
			List<CheckApprove> approves = checkApproveService.getCheckApproveByApplyNo(param11);
			CheckApprove approveNew = approves.get(0);
			List<String> messages = checkApproveService.contrastObj(approveOld,approveNew);
			if(messages.size() != 0){
				model.addAttribute("oldMessage", "1");
				model.addAttribute("messages", messages);
			}
		}

		// 向前台传递任务编号，以此来处理合同面前，法务审核和合同审核
		model.addAttribute("taskDefKeyFlag", actTaskParam.getTaskDefKey());
		contract.setQualityServiceMarginAmount(checkApprove.getQualityServiceMarginAmount());
		contract.setQualityServiceMarginRate(checkApprove.getQualityServiceMarginRate());
		model.addAttribute("contract", contract);

		//------------------------------------
		if("1".equals(applyRegister.getCustType()))  {
			//个人开户
			GedAccount gedAccount = null;
			gedAccount = gedAccountService.getByCustID(approId);
			if(gedAccount!=null) {
					contract.setRecBankcardNo(gedAccount.getCompanyAccount());//收款银行卡号：
					contract.setRecBank(gedAccount.getCompanyBankOfDeposit());//收款银行：
					contract.setRecBankName(gedAccount.getCompanyBankBranchName());//收款开户行名称：
					if(gedAccount.getProvinceName()==null&&gedAccount.getCityName()==null){
					}else if(gedAccount.getProvinceName()==null){
						contract.setRecBankDetail(gedAccount.getCityName());
					}else if(gedAccount.getCityName()==null){
						contract.setRecBankDetail(gedAccount.getProvinceName());
					}else {
						contract.setRecBankDetail(gedAccount.getProvinceName() + gedAccount.getCityName());
					}
					contract.setRecBankcardName(gedAccount.getLegalPerName());
					contract.setRecBankcardId(gedAccount.getCompanyId());
					contract.setRecBankMobile(gedAccount.getLegalPerPhone());
					contract.setRecBankIdNum(gedAccount.getLegalPerNum());
					contract.setRepBankcardName(gedAccount.getLegalPerName());
					contract.setRepBankMobile(gedAccount.getLegalPerPhone());
					contract.setRepBankIdNum(gedAccount.getLegalPerNum());
			}
			CreGedAccountCompany creGedAccountCompany = creGedAccountCompanyService.getCompanyAccount(approId);
			if(creGedAccountCompany==null) {
				creGedAccountCompany=new CreGedAccountCompany();
			}
			model.addAttribute("creGedAccountCompany", creGedAccountCompany);
		}else{
			//企业开户信息
			CreGedAccountCompany companyAccount = gedAccountCompanyService.findCompanyAccountByApproId(approId);
			if(companyAccount!=null){
				String cityCode = companyAccount.getCityCode();
				String areaCode = companyAccount.getAreaCode();
				String proviceCode = companyAccount.getProvinceCode();
				LinkedHashMap<String, String> gedCompanyAccountCityMap = loadAreaData(proviceCode);
				// 获取区级下拉
				LinkedHashMap<String, String> gedCompanyAccountDistinctMap = loadAreaData(cityCode);
				companyAccount.setProvinceCode(contractProvinceMap.get(proviceCode));
				companyAccount.setCityCode(gedCompanyAccountCityMap.get(cityCode));
				companyAccount.setAreaCode(gedCompanyAccountDistinctMap.get(areaCode));
			}else{
				companyAccount	= new CreGedAccountCompany();
			}
			if(companyAccount!=null&&companyAccount.getId()!=null) {
				contract.setRecBankcardNo(companyAccount.getCompanyAccount());//收款银行卡号：
				contract.setRecBank(companyAccount.getCompanyBankOfDeposit());//收款银行：
				contract.setRecBankName(companyAccount.getCompanyBankBranchName());//收款开户行名称：


				if(companyAccount.getProvinceCode()==null&&companyAccount.getCityCode()==null){
				}else if(companyAccount.getProvinceCode()==null){
					contract.setRecBankDetail(companyAccount.getCityCode());
				}else if(companyAccount.getCityCode()==null){
					contract.setRecBankDetail(companyAccount.getProvinceCode());
				}else {
					contract.setRecBankDetail(companyAccount.getProvinceCode() + companyAccount.getCityCode());
				}

				contract.setRecBankcardName(companyAccount.getCompanyName());
				contract.setRecBankMobile(companyAccount.getContactMobile());
				contract.setRecBankIdNum(companyAccount.getLegalCardNumber());
				contract.setRepBankcardName(companyAccount.getCompanyName());
				contract.setRepBankMobile(companyAccount.getLegalMobile());
				contract.setRepBankIdNum(companyAccount.getLegalCardNumber());
			}
		}


		// 查询抵押权人信息
		List<MortgagedPerson> mortgagedPersonList = mortgagedPersonService.getMortgagedPerson();
		model.addAttribute("mortgagedPersonList", mortgagedPersonList);

		// 查询中间人信息
		List<MortgagedPerson> middlePersonList = mortgagedPersonService.getMiddlePerson();
		model.addAttribute("middlePersonList", middlePersonList);

		// 查询收款信息列表
		List<ApplyRelation> applyRelations = applyRelationService.getCheckCoupleDoubtfulByApplyNo(applyNo);
		model.addAttribute("applyRelations", applyRelations);

		// 根据申请的产品类型查询还款方式
		Map<String, String> para = new HashMap<String, String>();
		para.put("productType", checkApprove.getApproProductTypeCode());
		para.put("loanRepayType", checkApprove.getApproLoanRepayType());
		List<RateInterest> loanRepayTypeList = rateInterestService.getLoanRepayType(para);
		if (loanRepayTypeList.size() > 0) {
			String approLoanRepayTypeName = loanRepayTypeList.get(0).getLoanRepayDesc();
			model.addAttribute("approLoanRepayTypeName", approLoanRepayTypeName);
		}
		model.addAttribute("loanRepayTypeList", loanRepayTypeList);

		// 保证金月息差及利息月息差判断标识
		String flowCode = CheckApproveUtils.validateFlowCode(checkApprove.getApproProductTypeCode(), actTaskParam.getTaskDefKey());
		model.addAttribute("flowCode", flowCode);
		return "app/credit/contract/contractAuditInfoUnionForm";

	}


}