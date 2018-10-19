package com.resoft.credit.applyLoanStatus.web;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.resoft.accounting.contract.dao.AccContractDao;
import com.resoft.common.utils.AjaxView;
import com.resoft.common.utils.Constants;
import com.resoft.credit.applyLoanStatus.entity.ApplyLoanStatus;
import com.resoft.credit.applyLoanStatus.service.ApplyLoanStatusService;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.applyRegister.entity.ApplyRegister;
import com.resoft.credit.applyRegister.service.ApplyRegisterService;
import com.resoft.credit.checkApprove.entity.CheckApprove;
import com.resoft.credit.checkApprove.service.CheckApproveService;
import com.resoft.credit.checkApproveUnion.entity.CheckApproveUnion;
import com.resoft.credit.contract.entity.Contract;
import com.resoft.credit.contract.service.ContractService;
import com.resoft.credit.guaranteeCompany.service.CreGuaranteeCompanyService;
import com.resoft.credit.processSuggestionInfo.entity.ProcessSuggestionInfo;
import com.resoft.credit.processSuggestionInfo.service.ProcessSuggestionInfoService;
import com.resoft.credit.rateInterest.entity.RateInterest;
import com.resoft.credit.rateInterest.service.RateInterestService;
import com.resoft.outinterface.rest.newged.entity.AddOrderResponse;
import com.resoft.outinterface.utils.Facade;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
/**
 * 财务放款Controller
 * 
 * @author wuxi01
 * @version 2016-03-03
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/applyLoanStatus")
public class ApplyLoanStatusController extends BaseController {
	
	@Autowired
	private ApplyRegisterService applyRegisterService;

	@Autowired
	private ApplyLoanStatusService applyLoanStatusService;

	@Autowired
	private ContractService contractService;
	

	@Autowired
	private CheckApproveService checkApproveService;
	
	@Autowired
	private ProcessSuggestionInfoService processSuggestionInfoService;

	@Autowired
	private RateInterestService rateInterestService;
	
	@Autowired
	private AccContractDao accContractDao;
	@Autowired
	private CreGuaranteeCompanyService creGuaranteeCompanyService;
	@ModelAttribute
	public ApplyLoanStatus get(@RequestParam(required = false) String id) {
		ApplyLoanStatus entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = applyLoanStatusService.get(id);
		}
		if (entity == null) {
			entity = new ApplyLoanStatus();
		}
		return entity;
	}

	@RequiresPermissions("credit:applyLoanStatus:view")
	@RequestMapping(value = { "list", "" })
	public String list(ActTaskParam actTaskParam, String readOnly, Model model) {
		model.addAttribute("actTaskParam", actTaskParam);
		model.addAttribute("readOnly", readOnly);
		return "app/credit/applyLoanStatus/applyLoanStatusList";
	}

	@RequiresPermissions("credit:applyLoanStatus:edit")
	@RequestMapping(value = "form")
	public String form(ActTaskParam actTaskParam, String readOnly, Model model) {
		String flag = null;
		String status = null;
		String applyNo = actTaskParam.getApplyNo();
		String taskDefKey = actTaskParam.getTaskDefKey();
		// 1.放款信息
		ApplyLoanStatus applyLoanStatus = new ApplyLoanStatus();
		applyLoanStatus.setApplyNo(applyNo);
		List<ApplyLoanStatus> applyLoanStatusList = applyLoanStatusService.findList(applyLoanStatus);
		if (applyLoanStatusList != null && applyLoanStatusList.size() == 1) {// 已有放款信息
			applyLoanStatus = applyLoanStatusList.get(0);
			flag = applyLoanStatus.getLoanStatus();
		} else if (applyLoanStatusList.size() > 1) {
			logger.warn("申请编号:" + applyNo + "对应的放款信息多于一条，可能是系统数据出现错误！");
		}
		ProcessSuggestionInfo processSuggestionInfo = new ProcessSuggestionInfo();
		Map<String, String> param1 = Maps.newHashMap();
		param1.put("applyNo", applyNo);
		// 2.合同信息/批复信息
		ApplyRegister applyRegister = applyRegisterService.getApplyRegisterByApplyNo(actTaskParam.getApplyNo());
		String TypeCode = applyRegister.getApplyProductTypeCode();
		if(Constants.PRODUCT_TYPE_CG.equals(TypeCode)){//显示采购贷新增字段的标志
			model.addAttribute("showCgFlag", "1");
		}
		
		List<CheckApprove> approves = checkApproveService.getCheckApproveByApplyNo(param1);
		String productCategoryKey=DictUtils.getDictLabel(approves.get(0).getProductCategory() , "product_category", null);
		model.addAttribute("productCategoryKey",productCategoryKey);
		model.addAttribute("CGapproves", approves.get(0));
		Contract contract = contractService.getContractByApplyNo(applyNo);
		contract.setQualityServiceMarginAmount(approves.get(0).getQualityServiceMarginAmount());
		contract.setQualityServiceMarginRate(approves.get(0).getQualityServiceMarginRate());
		status = contract.getApplyProductTypeCode();
		applyLoanStatusService.confirmStatus(flag, status, model);
		// 3.流程意见
		Map<String, String> params = Maps.newConcurrentMap();
		try {
			params.put("applyNo", applyNo);
			params.put("taskDefKey", taskDefKey);
		} catch (Exception e) {
			logger.error("流程参数发生错误，请联系管理员！", e);
			model.addAttribute("approveMessage", "流程参数发生错误，请联系管理员！");
		}
		processSuggestionInfo = processSuggestionInfoService.findByApplyNo(params);
		if (processSuggestionInfo == null) {// 新增
			processSuggestionInfo = new ProcessSuggestionInfo();
			processSuggestionInfo.setApplyNo(applyNo);
			processSuggestionInfo.setTaskDefKey(taskDefKey);
		}
		
		// 根据申请的产品类型查询还款方式
		Map<String, String> param = new HashMap<String, String>();
		param.put("productType", contract.getApproProductTypeCode());
		List<RateInterest> loanRepayTypeList = rateInterestService.getLoanRepayType(param);
		model.addAttribute("loanRepayTypeList", loanRepayTypeList);

		model.addAttribute("applyLoanStatus", applyLoanStatus);
		
		String accContractId = accContractDao.getHasAccContract(applyLoanStatus.getContractNo());
		if(accContractId!=null) {//已经满标
			if("0".equals(applyLoanStatus.getSend())) {//已经推过
				model.addAttribute("showButtonPush", "0");
			}else {
				model.addAttribute("showButtonPush", "1");
			}
		}else {//已经满标没有满标
			model.addAttribute("showButtonPush", "0");
		}
		
		model.addAttribute("processSuggestionInfo", processSuggestionInfo);
		model.addAttribute("contract", contract);
		model.addAttribute("readOnly", readOnly);
		model.addAttribute("actTaskParam", actTaskParam);
		return "app/credit/applyLoanStatus/applyLoanStatusForm";
	}

	/**
	 * 保存放款意见
	 * 
	 * @param processSuggestionInfo
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("credit:applyLoanStatus:edit")
	@RequestMapping(value = "saveLoanSuggestion")
	public AjaxView saveLoanSuggestion(String loanStatus, ProcessSuggestionInfo processSuggestionInfo, ActTaskParam actTaskParam) {
		AjaxView ajaxView = new AjaxView();
		String passFlag = processSuggestionInfo.getPassFlag();
		String suggestionDesc = processSuggestionInfo.getSuggestionDesc();
		ajaxView = applyLoanStatusService.saveLoanSuggestion(actTaskParam, suggestionDesc, passFlag, processSuggestionInfo);
		if("success".equals(passFlag)){
			processSuggestionInfoService.backMessageToGED(actTaskParam.getApplyNo(),null, Constants.GED_APPLY_STATUS_SHTG,null);
		}else if("flow".equals(passFlag)){
			processSuggestionInfoService.backMessageToGED(actTaskParam.getApplyNo(),null, Constants.GED_APPLY_STATUS_SQJJ,null);
		}
		if ("flow".equals(passFlag)) {
			ApplyRegister applyRegister = new ApplyRegister();
			applyRegister.setApplyNo(actTaskParam.getApplyNo());
			List<ApplyRegister> registerList = applyRegisterService.findList(applyRegister);
			if (!registerList.isEmpty()) {
				applyRegister = registerList.get(0);
			}
			if(Constants.PROC_DEF_KEY_GR.equalsIgnoreCase(applyRegister.getProcDefKey())){
				Map<String, String> paramCheckApprove = Maps.newConcurrentMap();
				paramCheckApprove.put("applyNo", actTaskParam.getApplyNo());
				List<CheckApprove> checkApproveList = checkApproveService.getCheckApproveByApplyNo(paramCheckApprove);
				if (checkApproveList.size() >0) {
					CheckApprove checkApprove = checkApproveList.get(0);
					creGuaranteeCompanyService.updateGuranteFeeByApply(checkApprove, actTaskParam.getApplyNo());
				}
				Facade.facade.finishOrderPushGed(actTaskParam.getApplyNo(),200,"0");//0是非联合授信
			}else if (Constants.PROC_DEF_KEY_LHSX.equalsIgnoreCase(applyRegister.getProcDefKey())) {
				creGuaranteeCompanyService.updateGuranteFeeByApplyNoUnion(actTaskParam.getApplyNo());
				Facade.facade.finishOrderPushGed(actTaskParam.getApplyNo(),200,"1");//1联合授信
			}
		}
		// 放款成功 将信息记录到节点信息
		if ("success".equals(processSuggestionInfo.getPassFlag())){
			processSuggestionInfoService.insertFlag(processSuggestionInfo,actTaskParam.getTaskDefKey());
		}
		return ajaxView;
	}

	/**
	 * 刷新放款状态
	 * 
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("credit:applyLoanStatus:edit")
	@RequestMapping(value = "refreshLoanStatus")
	public AjaxView refreshLoanStatus(String applyNo) {
		AjaxView ajaxView = new AjaxView();
		try {
			// 放款信息
			ApplyLoanStatus applyLoanStatus = new ApplyLoanStatus();
			applyLoanStatus.setApplyNo(applyNo);
			List<ApplyLoanStatus> applyLoanStatusList = applyLoanStatusService.findList(applyLoanStatus);
			if (applyLoanStatusList != null && applyLoanStatusList.size() == 1) {// 已有放款信息
				applyLoanStatus = applyLoanStatusList.get(0);
				Contract contract = contractService.getContractByApplyNo(applyNo);
				ajaxView = applyLoanStatusService.refreshConfirmStatus(applyLoanStatus.getLoanStatus(), contract.getApplyProductTypeCode());
				String loanStatus = DictUtils.getDictLabel(applyLoanStatus.getLoanStatus(), "LOAN_STATUS", null);
				ajaxView.put("loanStatusCode", applyLoanStatus.getLoanStatus());
				ajaxView.put("loanStatus", loanStatus);
				ajaxView.setSuccess().put("loanMessage", "刷新放款状态成功！");
			} else {
				ajaxView.setSuccess().setMessage("暂无放款信息");
			}
		} catch (Exception e) {
			logger.error("刷新放款状态失败！", e);
			ajaxView.setFailed().setMessage("刷新放款状态失败！");
		}
		return ajaxView;
	}

	/**
	 * 验证放款状态
	 * 
	 */
	@ResponseBody
	@RequiresPermissions("credit:applyLoanStatus:edit")
	@RequestMapping(value = "validateLoanStatus")
	public AjaxView validateLoanStatus(String applyNo, String productTypeCode, String passFlag) {
		AjaxView ajaxView = new AjaxView();
		// 放款信息
		List<ApplyLoanStatus> applyLoanStatusList = applyLoanStatusService.getApplyLoanStatusByApplyNo(applyNo);
		String loanStatus = applyLoanStatusList.get(0).getLoanStatus();
		if (Constants.LOAN_STATUS_YTX.equals(loanStatus)) {
			if ("success".equals(passFlag)) {
				ajaxView.setSuccess();
			} else {
				ajaxView.setFailed().setMessage("请确定放款结论与放款状态相符。");
			}
		} else if (Constants.LOAN_STATUS_LB.equals(loanStatus)) {
			if ("flow".equals(passFlag)) {
				ajaxView.setSuccess();
			} else {
				ajaxView.setFailed().setMessage("请确定放款结论与放款状态相符。");
			}
		} else {
			ajaxView.setFailed().setMessage("请确认账务已提现或者流标");
		}
		return ajaxView;
	}

	@RequiresPermissions("credit:applyLoanStatus:edit")
	@RequestMapping(value = "delete")
	public String delete(ApplyLoanStatus applyLoanStatus, RedirectAttributes redirectAttributes) {
		applyLoanStatusService.delete(applyLoanStatus);
		addMessage(redirectAttributes, "删除财务放款成功");
		return "redirect:" + Global.getAdminPath() + "/applyLoanStatus/applyLoanStatus/?repage";
	}

	@ResponseBody
	@RequiresPermissions("credit:applyLoanStatus:edit")
	@RequestMapping(value = "save")
	public AjaxView save(ApplyLoanStatus applyLoanStatus, String flag) {
		AjaxView ajaxView = new AjaxView();
		try {
			applyLoanStatus.setLoanStatus(Constants.LOAN_STATUS_YTSDTX);
			applyLoanStatusService.save(applyLoanStatus);
			ajaxView.setSuccess().setMessage("操作成功！");
		} catch (Exception e) {
			logger.error("操作失败！", e);
			ajaxView.setFailed().setMessage("操作失败！");
		}
		return ajaxView;
	}


	/**
	 * 重新建账
	 * 
	 * @param contractNo
	 * @param time
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("credit:applyLoanStatus:edit")
	@RequestMapping(value = "toPrepare")
	public AjaxView toPrepare(String contractNo, String time) {
		return applyLoanStatusService.toPrepare(contractNo, time);
	}
	
	@RequiresPermissions("credit:applyLoanStatus:edit")
	@RequestMapping(value = "formNew")
	public String formNew(ActTaskParam actTaskParam, String readOnly, Model model) {
		String applyNo = actTaskParam.getApplyNo();
//		String taskDefKey = actTaskParam.getTaskDefKey();
		// 1.放款信息
//		ApplyLoanStatus applyLoanStatus;
		ProcessSuggestionInfo processSuggestionInfo = new ProcessSuggestionInfo();
		//4.查询联合授信信息  
		Map<String,String> unionMap = Maps.newHashMap();
		unionMap.put("applyNo", applyNo);
		List<CheckApproveUnion> CheckApproveUnionLst = applyLoanStatusService.findUnionCust(unionMap);
		for(CheckApproveUnion checkApproveUnion:CheckApproveUnionLst){
			String accContractId = accContractDao.getHasAccContract(checkApproveUnion.getContractNo());
			ApplyLoanStatus applyLoanStatus = applyLoanStatusService.queryContractLoanStatus(checkApproveUnion.getContractNo());
			if(accContractId!=null) {//已经满标
				if(applyLoanStatus != null && applyLoanStatus.getSend() != null && "0".equals(applyLoanStatus.getSend())) {//已经推过
					checkApproveUnion.setConfirmFeeFlag("0");
				}else {
					checkApproveUnion.setConfirmFeeFlag("1");
				}
			}else {//没有满标
				checkApproveUnion.setConfirmFeeFlag("2");
			}
		}
		// 根据申请的产品类型查询还款方式
		Map<String, String> param = new HashMap<String, String>();
		List<RateInterest> loanRepayTypeList = rateInterestService.getLoanRepayType(param);
		model.addAttribute("loanRepayTypeList", loanRepayTypeList);
		model.addAttribute("readOnly", readOnly);
		model.addAttribute("actTaskParam", actTaskParam);
		model.addAttribute("CheckApproveUnionLst", CheckApproveUnionLst);
		model.addAttribute("processSuggestionInfo", processSuggestionInfo);
		return "app/credit/applyLoanStatus/applyLoanStatusFormNew";
	}
	
	@ResponseBody
	@RequiresPermissions("credit:applyLoanStatus:edit")
	@RequestMapping(value = "saveLoan")
	public AjaxView saveLoan(ApplyLoanStatus applyLoanStatus, String flag,String custId,String checkId) {
		AjaxView ajaxView = new AjaxView();
		Map<String, Object> msg = Maps.newHashMap();
		try {
		 applyLoanStatus.setLoanStatus(Constants.LOAN_STATUS_YTSDTX);
		 msg =applyLoanStatusService.updateLoanStatus(applyLoanStatus,custId,checkId);
		 ajaxView.setSuccess().setMessage((String) msg.get("msg"));
		}catch (Exception e) {
			logger.error("操作失败！", e);
			ajaxView.setFailed().setMessage("操作失败");
		}
		return ajaxView;
	}
	
	/**
	 * 验证放款状态
	 * 
	 */
	@ResponseBody
	@RequiresPermissions("credit:applyLoanStatus:edit")
	@RequestMapping(value = "validateLoanStatusNew")
	public AjaxView validateLoanStatusNew(String applyNo, String productTypeCode, String passFlag) {
		AjaxView ajaxView = new AjaxView();
		// 放款信息
		List<CheckApproveUnion> applyLoanStatusList = applyLoanStatusService.getLoanStatusNew(applyNo);
		if (applyLoanStatusList.size() == 1 && applyLoanStatusList.get(0).getLoanStatus().equals(Constants.LOAN_STATUS_YTX)){
			if ("success".equals(passFlag)) {
				ajaxView.setSuccess();
			} else {
				ajaxView.setFailed().setMessage("请确定放款结论与放款状态相符。");
			}
		}
		else if (applyLoanStatusList.size() == 1 && applyLoanStatusList.get(0).getLoanStatus().equals(Constants.LOAN_STATUS_LB)) {
			if ("flow".equals(passFlag)) {
				ajaxView.setSuccess();
			} else {
				ajaxView.setFailed().setMessage("请确定放款结论与放款状态相符。");
			}
		} else {
			ajaxView.setFailed().setMessage("请确认账务已提现或者流标");
		}
		return ajaxView;
	}
	
	/**
	 * 二次提现解冻操作
	 * @return
	 */
	@RequestMapping(value="pushGedLoan")
	@ResponseBody
	public AjaxView applyLoanStatus(com.resoft.accounting.contract.entity.Contract contract){
		String applyNo= contract.getApplyNo();
		String contractNo=contract.getContractNo();
		BigDecimal factGuaranteeFee = contract.getFactGuaranteeFee();
		BigDecimal factGuaranteeGold = contract.getFactGuaranteeGold();
		BigDecimal factServiceFee = contract.getFactServiceFee();
		String approProductTypeId = contract.getApproProductTypeId();
		AjaxView ajaxView = new AjaxView();
		AddOrderResponse addOrderResponse = null;
		try {
			accContractDao.updateFactGuarantee(contractNo,factGuaranteeFee,factGuaranteeGold,factServiceFee);
			Map<String,String> param = new HashMap<String,String>();
			ApplyRegister applyRegister = new ApplyRegister();
			applyRegister.setApplyNo(applyNo);
			List<ApplyRegister> registerList = applyRegisterService.findList(applyRegister);
			if (!registerList.isEmpty()) {
				applyRegister = registerList.get(0);
			}
			if(Constants.PROC_DEF_KEY_GR.equalsIgnoreCase(applyRegister.getProcDefKey())){
				param.put("orderNo",applyNo);
			}else if (Constants.PROC_DEF_KEY_LHSX.equalsIgnoreCase(applyRegister.getProcDefKey())) {
			logger.info(param.get("contractNo"));
				Contract contractQuery = contractService.getContractByContractNo(contractNo);
			if (contract != null && StringUtils.isNotBlank(contractQuery.getApproId())) {
				param.put("orderNo",contractQuery.getApproId());
				}
			}
			param.put("factGuaranteeFee", factGuaranteeFee.toString());
			param.put("factGuaranteeGold", factGuaranteeGold.toString());
			param.put("factServiceFee", factServiceFee.toString());
			if ("6".equals(approProductTypeId)) {//增加债股结合类型
				param.put("type", "0");//债股结合为0
			}else{
				param.put("type", "1");//非债股结合1
			}
			
			List<ApplyLoanStatus>  applyLoanStatusList = applyLoanStatusService.finApplyLoanStatus(param);
			//if(applyLoanStatusList.size() >0 && "0".equals(applyLoanStatusList.get(0).getServiceFeeStatus())){
			addOrderResponse =	Facade.facade.pushGedLoanBlance(param);
			String mesg="";
			if(applyLoanStatusList.size() >0 && !"0".equals(applyLoanStatusList.get(0).getServiceFeeStatus())){
				mesg=",此客户未缴纳服务费";
			}
			if(addOrderResponse != null && "0".equals(addOrderResponse.getCode())){
				ajaxView.setSuccess();
				ajaxView.setMessage("确认成功"+mesg);
				Map<String,String> conParam = new HashMap<>();
				conParam.put("contractNo", contractNo);
				conParam.put("applyNo",applyNo);
				applyLoanStatusService.updateSendGEDJd(conParam);
			}
			if(addOrderResponse != null && "0".equals(addOrderResponse.getCode())){
				ajaxView.setFailed();
				ajaxView.setMessage("解冻剩余借款失败"+mesg);
			}
			if(addOrderResponse==null) {
				ajaxView.setFailed();
				ajaxView.setMessage("解冻剩余借款失败"+mesg);
			}
		} catch (Exception e) {
			logger.error("操作失败",e);
			ajaxView.setFailed();
			ajaxView.setMessage("确认失败");
		}
		return ajaxView;
	}
	
	@RequestMapping(value = "sureGuaranteeForm")
	public String form(String applyNo,String  contractNo, Model model) {
		com.resoft.accounting.contract.entity.Contract contract = accContractDao.findContractInfoByContractNo(contractNo);
		model.addAttribute("contract", contract);
		return "app/credit/contract/payGuarantee";
	}
}