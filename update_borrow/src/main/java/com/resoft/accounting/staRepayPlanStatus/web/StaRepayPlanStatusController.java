package com.resoft.accounting.staRepayPlanStatus.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.resoft.accounting.applyPenaltyFineExempt.dao.ApplyPenaltyFineExemptDao;
import com.resoft.accounting.applyPenaltyFineExempt.entity.ApplyPenaltyFineExempt;
import com.resoft.accounting.common.utils.AjaxView;
import com.resoft.accounting.contract.entity.Contract;
import com.resoft.accounting.contract.service.ContractService;
import com.resoft.accounting.deductResult.entity.DeductResult;
import com.resoft.accounting.deductResult.service.DeductResultService;
import com.resoft.accounting.repayPlan.service.AccRepayPlanService;
import com.resoft.accounting.staRepayPlanStatus.entity.StaRepayPlanStatus;
import com.resoft.accounting.staRepayPlanStatus.service.StaRepayPlanStatusService;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 账务调整Controller
 * 
 * @author yanwanmei
 * @version 2016-03-03
 */
@Controller
@RequestMapping(value = "${adminPath}/accounting/staRepayPlanStatus")
public class StaRepayPlanStatusController extends BaseController {

	@Autowired
	private DeductResultService deductResultService;

	@Autowired
	private AccRepayPlanService repayPlanService;

	@Autowired
	private ContractService contractService;

	@Autowired
	private StaRepayPlanStatusService staRepayPlanStatusService;

	@Autowired
	private ApplyPenaltyFineExemptDao applyPenaltyFineExemptDao;

	@ModelAttribute
	public StaRepayPlanStatus get(@RequestParam(required = false) String id) {
		StaRepayPlanStatus entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = staRepayPlanStatusService.get(id);
		}
		if (entity == null) {
			entity = new StaRepayPlanStatus();
		}
		return entity;
	}

	@RequiresPermissions("accounting:staRepayPlanStatus:view")
	@RequestMapping(value = { "list", "" })
	public String list(StaRepayPlanStatus staRepayPlanStatus, HttpServletRequest request, HttpServletResponse response, Model model) {
		String contractNo = null;
		try {
			contractNo = URLDecoder.decode(staRepayPlanStatus.getContractNo(),"UTF-8");
			staRepayPlanStatus.setContractNo(contractNo);
		} catch (UnsupportedEncodingException e) {
			logger.error("汉字解析失败",e);
		}
		DeductResult deductResult = new DeductResult();
		deductResult.setContractNo(staRepayPlanStatus.getContractNo());
		Page<DeductResult> page = deductResultService.findPage(new Page<DeductResult>(request, response), deductResult);
		model.addAttribute("contractNo", staRepayPlanStatus.getContractNo());
		if (page.getList().size() == 0) {
			return "app/accounting/staRepayPlanStatus/staRepayPlanStatusForm";
		} else {
			List<StaRepayPlanStatus> repayPlanStatus = staRepayPlanStatusService.findStaRepayPlanStatus(staRepayPlanStatus.getContractNo());
			model.addAttribute("repayPlanStatus", repayPlanStatus);
			return "app/accounting/staRepayPlanStatus/staRepayPlanStatusList";
		}
	}

	@RequiresPermissions("accounting:staRepayPlanStatus:view")
	@RequestMapping(value = "getDataList")
	@ResponseBody
	public List<Map<String, String>> getDataList(String contractNo) {
		String contractNoTmp = null;
		try {
			contractNoTmp = URLDecoder.decode(contractNo,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("汉字解码失败",e);
		}
		List<Map<String, String>> planTemp = repayPlanService.findRepayPlanToStaRepayPlanStatusByContractNo(contractNoTmp);
		List<Map<String, String>> plans = Lists.newArrayList();
		for (Map<String, String> plan : planTemp) {
			plan.put("factCapitalAmount", "0");
			plan.put("factInterestAmount", "0");
			plan.put("factPenaltyAmount", "0");
			plan.put("factFineAmount", "0");
			plan.put("factServiceFee", "0");
			plan.put("factMangementFee", "0");
			plan.put("factAddAmount", "0");
			plan.put("backAmount", "0");
			plans.add(plan);
		}
		return plans;
	}

	@RequiresPermissions("accounting:staRepayPlanStatus:view")
	@RequestMapping(value = "getDataForm")
	@ResponseBody
	public List<Map<String, String>> getDataForm(String contractNo) {
		String contractNoTmp = null;
		try {
			contractNoTmp = URLDecoder.decode(contractNo,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("汉字解码失败",e);
		}
		List<Map<String, String>> plans = staRepayPlanStatusService.findStaRepayPlanStatusData(contractNoTmp);
		List<Dict> dicts = DictUtils.getDictList("PERIOD_STATE");
		for (Map<String, String> plan : plans) {
			for (int i = 0; i < dicts.size(); i++) {
				if (StringUtils.isNotEmpty(plan.get("repayPeriodStatus"))) {
					if (dicts.get(i).getValue().equals(plan.get("repayPeriodStatus"))) {
						plan.put("repayPeriodStatus", dicts.get(i).getLabel());
					}
				}
			}
		}
		return plans;
	}

	@RequiresPermissions("accounting:staRepayPlanStatus:view")
	@RequestMapping(value = "getRepayPeriodStatus")
	@ResponseBody
	public String getRepayPeriodStatus(String contractNo) {
		String repayPeriodStatus = "";
		List<Dict> dicts = DictUtils.getDictList("PERIOD_STATE");
		for (int i = 0; i < dicts.size(); i++) {
			if (i > dicts.size() - 2) {
				repayPeriodStatus += dicts.get(i).getValue() + ":" + dicts.get(i).getLabel();
			} else {
				repayPeriodStatus += dicts.get(i).getValue() + ":" + dicts.get(i).getLabel() + ";";
			}
		}
		return repayPeriodStatus;
	}

	@ResponseBody
	@RequiresPermissions("accounting:staRepayPlanStatus:edit")
	@RequestMapping(value = "save")
	public AjaxView save(@RequestBody List<StaRepayPlanStatus> staRepayPlanStatus, String contractNoTemp) {
		AjaxView ajaxView = new AjaxView();
		try {
			contractNoTemp = URLDecoder.decode(contractNoTemp,"UTF-8");
			boolean amountFlag = staRepayPlanStatusService.getSumRepayAmount(contractNoTemp);
			if (amountFlag) {
				boolean flag = staRepayPlanStatusService.prepareAmount(staRepayPlanStatus, contractNoTemp);
				if (flag) {
					Contract contract = contractService.findContractByContractNo(contractNoTemp);
					for (StaRepayPlanStatus planStatus : staRepayPlanStatus) {
						if ("请选择".equals(planStatus.getRepayPeriodStatus())) {
							ajaxView.setFailed().setMessage("请选择正确的期状态");
							return ajaxView;
						} else {
							planStatus.setRepayPeriodStatus(DictUtils.getDictValue(planStatus.getRepayPeriodStatus(), "PERIOD_STATE", null));
						}
						planStatus.setContractNo(contractNoTemp);
						planStatus.setOrgLevel2(contract.getOrgLevel2());
						planStatus.setOrgLevel3(contract.getOrgLevel3());
						planStatus.setOrgLevel4(contract.getOrgLevel4());
						planStatus.preInsert();
						staRepayPlanStatusService.saveAccApplyChangeRepay(planStatus);
					}
					// 跑存储
					ajaxView = staRepayPlanStatusService.saveaccApplyChangeRepays(contractNoTemp);
				} else {
					ajaxView.setFailed().setMessage("请仔细核对所填金额！");
				}
			} else {
				ajaxView.setFailed().setMessage("数据校验失败，请联系管理员！");
			}
		} catch (Exception e) {
			ajaxView.setFailed().setMessage("账务调整操作失败！");
			logger.error("账务调整操作失败！", e);
		}
		return ajaxView;
	}

	@ResponseBody
	@RequiresPermissions("accounting:staRepayPlanStatus:edit")
	@RequestMapping(value = "savePZ")
	public AjaxView savePZ(@RequestBody List<StaRepayPlanStatus> staRepayPlanStatus, String contractNoTemp) {
		AjaxView ajaxView = new AjaxView();
		try {
			contractNoTemp = URLDecoder.decode(contractNoTemp,"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			logger.error("汉字解析失败",e1);
		}
		try {
			Contract contract = contractService.findContractByContractNo(contractNoTemp);
			for (StaRepayPlanStatus planStatus : staRepayPlanStatus) {
				if ("请选择".equals(planStatus.getRepayPeriodStatus())) {
					ajaxView.setFailed().setMessage("请选择正确的期状态");
					return ajaxView;
				} else {
					planStatus.setRepayPeriodStatus(DictUtils.getDictValue(planStatus.getRepayPeriodStatus(), "PERIOD_STATE", null));
				}
				planStatus.setContractNo(contractNoTemp);
				planStatus.setOrgLevel2(contract.getOrgLevel2());
				planStatus.setOrgLevel3(contract.getOrgLevel3());
				planStatus.setOrgLevel4(contract.getOrgLevel4());
				planStatus.preInsert();
				staRepayPlanStatusService.saveAccApplyChangeRepay(planStatus);
			}
			// 跑存储
			ajaxView = staRepayPlanStatusService.savePZ(contractNoTemp);
		} catch (Exception e) {
			ajaxView.setFailed().setMessage("账务调整操作失败！");
			logger.error("账务调整操作失败！", e);
		}
		return ajaxView;
	}

	@RequiresPermissions("accounting:staRepayPlanStatus:edit")
	@RequestMapping(value = "delete")
	public String delete(StaRepayPlanStatus staRepayPlanStatus, RedirectAttributes redirectAttributes) {
		staRepayPlanStatusService.delete(staRepayPlanStatus);
		addMessage(redirectAttributes, "删除账务调整成功");
		return "redirect:" + Global.getAdminPath() + "/staRepayPlanStatus/staRepayPlanStatus/?repage";
	}

	// 账务调整，生成罚息时，进入。验证合同号是否被锁以及生成罚息
	@ResponseBody
	@RequiresPermissions("accounting:staRepayPlanStatus:edit")
	@RequestMapping(value = "validateIdLock")
	public AjaxView validateIdLock(String contractNo,HttpServletRequest request,HttpServletResponse response) {
		AjaxView ajaxView = new AjaxView();
		try {
			contractNo = URLDecoder.decode(contractNo,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("汉字解析失败",e);
		}
		boolean pzFlag = true;
		DeductResult deductResult = new DeductResult();
		deductResult.setContractNo(contractNo);
		Page<DeductResult> page = deductResultService.findPage(new Page<DeductResult>(request, response), deductResult);
		if(page.getList() == null || page.getList().size() == 0){
			pzFlag = false;
		}
		ajaxView = staRepayPlanStatusService.validateIdLockToFineExempt(contractNo,pzFlag);
		return ajaxView;
	}

	// 点击账务调整时，进入。验证合同号是否存在有正在申请中的减免
	@ResponseBody
	@RequiresPermissions("accounting:staRepayPlanStatus:edit")
	@RequestMapping(value = "validateIsOrNoApplyPenaltyFineExempt")
	public AjaxView validateIsOrNoApplyPenaltyFineExempt(String contractNo) {
		AjaxView ajaxView = new AjaxView();
		try {

	 		if(contractNo.contains("\\")){
				contractNo = contractNo.replace("\\","\\\\");
			}

			contractNo = URLDecoder.decode(contractNo,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("汉字解析失败",e);
		}
		List<ApplyPenaltyFineExempt> applyPenaltyFineExempts = applyPenaltyFineExemptDao.getApplyPenaltyFineExemptByContractNo(contractNo);
		if (applyPenaltyFineExempts.size() != 0) {
			ajaxView.setFailed().setMessage("有正在申请中的减免操作，请稍后再试。");
		} else {
			ajaxView.setSuccess();
		}
		return ajaxView;
	}

	// 账务调整，生成罚息后 ，不进行入账，放弃操作
	@ResponseBody
	@RequiresPermissions("accounting:staRepayPlanStatus:edit")
	@RequestMapping(value = "removeLock")
	public AjaxView removeLock(String contractNo) {
		AjaxView ajaxView = new AjaxView();
		try {
			contractNo = URLDecoder.decode(contractNo,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("汉字解析失败",e);
		}
		ajaxView = staRepayPlanStatusService.deleteLock(contractNo);
		return ajaxView;
	}

}