package com.resoft.accounting.applyAdvanceRepay.web;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.resoft.accounting.applyAdvanceRepay.entity.ApplyAdvanceRepay;
import com.resoft.accounting.applyAdvanceRepay.service.ApplyAdvanceRepayService;
import com.resoft.accounting.common.utils.AjaxView;
import com.resoft.accounting.common.utils.Constants;
import com.resoft.accounting.contract.dao.ContractLockDao;
import com.resoft.accounting.contract.entity.Contract;
import com.resoft.accounting.contract.entity.ContractLock;
import com.resoft.accounting.contract.service.ContractService;
import com.resoft.accounting.staRepayPlanStatus.entity.StaRepayPlanStatus;
import com.resoft.accounting.staRepayPlanStatus.service.StaRepayPlanStatusService;
import com.resoft.accounting.taskCenter.entity.ActTaskParam;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 提前还款Controller
 * 
 * @author yanwanmei
 * @version 2016-03-03
 */
@Controller
@RequestMapping(value = "${adminPath}/accounting/applyAdvanceRepay")
public class ApplyAdvanceRepayController extends BaseController {

	@Autowired
	private StaRepayPlanStatusService staRepayPlanStatusService;

	@Autowired
	private ApplyAdvanceRepayService applyAdvanceRepayService;

	@Autowired
	private ContractService contractService;

	@Autowired
	private ContractLockDao contractLockDao;

	@ModelAttribute
	public ApplyAdvanceRepay get(@RequestParam(required = false) String id) {
		ApplyAdvanceRepay entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = applyAdvanceRepayService.get(id);
		}
		if (entity == null) {
			entity = new ApplyAdvanceRepay();
		}
		return entity;
	}

	// 结算中心批复
	@RequiresPermissions("accounting:applyAdvanceRepay:view")
	@RequestMapping(value = "approveList")
	public String approveList(ActTaskParam actTaskParam, ApplyAdvanceRepay applyAdvanceRepay, Model model) {
		applyAdvanceRepay.setContractNo(actTaskParam.getApplyNo());
		Map<String, Object> params = Maps.newHashMap();
		params.put("contractNo", actTaskParam.getApplyNo());
		List<ApplyAdvanceRepay> applyAdvanceRepays = Lists.newArrayList();
		if ("1".equals(actTaskParam.getStatus())) {
			model.addAttribute("readOnly", true);
			params.put("procInsId",actTaskParam.getProcInstId());
			applyAdvanceRepays = applyAdvanceRepayService.getApplyAdvanceRepayByContractNoAndProcInsId(params);
		} else {
			params.put("advanceDeductStatus", Constants.ADVANCE_DEDUCT_STATUS_SQDPF);
			applyAdvanceRepays = applyAdvanceRepayService.getApplyAdvanceRepayByContractNoAndstatus(params);
		}
		if (applyAdvanceRepays.size() == 0) {
			model.addAttribute("applyAdvanceRepay", applyAdvanceRepay);
		} else {
			model.addAttribute("applyAdvanceRepay", applyAdvanceRepays.get(0));
		}
		model.addAttribute("actTaskParam", actTaskParam);
		return "app/accounting/applyAdvanceRepay/applyAdvanceRepayApproveList";
	}

	// 分公司确认和结算中心最终审核
	@RequiresPermissions("accounting:applyAdvanceRepay:view")
	@RequestMapping(value = "validateList")
	public String validateList(ActTaskParam actTaskParam, ApplyAdvanceRepay applyAdvanceRepay, Model model) {
		applyAdvanceRepay.setContractNo(actTaskParam.getApplyNo());
		Map<String, Object> params = Maps.newHashMap();
		List<ApplyAdvanceRepay> applyAdvanceRepays = Lists.newArrayList();
		params.put("contractNo", applyAdvanceRepay.getContractNo());
		if ("1".equals(actTaskParam.getStatus())) {
			model.addAttribute("readOnly", true);
			params.put("procInsId",actTaskParam.getProcInstId());
			applyAdvanceRepays = applyAdvanceRepayService.getApplyAdvanceRepayByContractNoAndProcInsId(params);
		} else {
			if (Constants.ADVANCE_DEDUCT_UTASK_FGSQR.equals(actTaskParam.getTaskDefKey())) {
				params.put("advanceDeductStatus", Constants.ADVANCE_DEDUCT_STATUS_PFDQR);
			} else if (Constants.ADVANCE_DEDUCT_UTASK_JSZXQR.equals(actTaskParam.getTaskDefKey())) {
				params.put("advanceDeductStatus", Constants.ADVANCE_DEDUCT_STATUS_HKDTZ);
			} else {
				params.put("advanceDeductStatus", null);
			}
			applyAdvanceRepays = applyAdvanceRepayService.getApplyAdvanceRepayByContractNoAndstatus(params);
		}
		if (applyAdvanceRepays.size() == 0) {
			model.addAttribute("applyAdvanceRepay", applyAdvanceRepay);
		} else {
			model.addAttribute("applyAdvanceRepay", applyAdvanceRepays.get(0));
		}
		model.addAttribute("actTaskParam", actTaskParam);
		return "app/accounting/applyAdvanceRepay/applyAdvanceRepayList";
	}

	// 提前还款申请
	@RequiresPermissions("accounting:applyAdvanceRepay:view")
	@RequestMapping(value = "form")
	public String form(ApplyAdvanceRepay applyAdvanceRepay, Model model) {
		applyAdvanceRepay.setPeriodNum(applyAdvanceRepayService.getMinPeriodNum(applyAdvanceRepay.getContractNo()));
		String remainRepayAmount = applyAdvanceRepayService.remainRepayPlan(applyAdvanceRepay);//剩余应还管理费+本金+利息
		model.addAttribute("remainRepayAmount", remainRepayAmount);
		model.addAttribute("applyAdvanceRepay", applyAdvanceRepay);
		return "app/accounting/applyAdvanceRepay/applyAdvanceRepayForm";
	}

	@ResponseBody
	@RequiresPermissions("accounting:applyAdvanceRepay:edit")
	@RequestMapping(value = "save")
	public AjaxView save(ApplyAdvanceRepay applyAdvanceRepay) {
		AjaxView ajaxView = new AjaxView();
		try {
			applyAdvanceRepayService.saveAdvanceRepay(applyAdvanceRepay);
			ajaxView.setSuccess().setMessage("保存提前还款成功！");
		} catch (Exception e) {
			logger.error("保存提前还款失败", e);
			ajaxView.setFailed().setMessage("保存申请提前还款失败！");
		}
		return ajaxView;
	}

	@ResponseBody
	@RequiresPermissions("accounting:applyAdvanceRepay:edit")
	@RequestMapping(value = "saveSuggetion")
	public AjaxView saveSuggetion(ActTaskParam actTaskParam, ApplyAdvanceRepay applyAdvanceRepay, String passFlag) {
		AjaxView ajaxView = new AjaxView();
		try {
			applyAdvanceRepayService.saveSuggestion(applyAdvanceRepay, actTaskParam, passFlag);
			ajaxView.setSuccess().setMessage("操作成功！");
		} catch (Exception e) {
			logger.error("保存提前还款批复失败", e);
			ajaxView.setSuccess().setMessage("提交失败！");
		}
		return ajaxView;
	}

	@ResponseBody
	@RequiresPermissions("accounting:applyAdvanceRepay:view")
	@RequestMapping(value = "getRepayAmount")
	public Map<String, String> getRepayAmount(ApplyAdvanceRepay applyAdvanceRepay, String remain) {
		try {
			String contractNo = URLDecoder.decode(applyAdvanceRepay.getContractNo(), "UTF-8");
			applyAdvanceRepay.setContractNo(contractNo);
		} catch (UnsupportedEncodingException e) {
			logger.error("汉字解码失败", e);
		}
		Map<String, String> params = Maps.newHashMap();
		String sum = null;
		// if ("2".equals(applyAdvanceRepay.getAdvanceDeductType())) {
		// applyAdvanceRepay.setPeriodNum(Double.toString(Double.parseDouble(applyAdvanceRepay.getPeriodNum())
		// + 1));
		// num =
		// applyAdvanceRepayService.getAdvanceRepayForOneMonth(applyAdvanceRepay);
		// } else {
		String num = applyAdvanceRepayService.getAdvanceRepayForThreePercents(applyAdvanceRepay);//提前还款费用
		// }
		if (!(StringUtils.isNull(num))) {
			sum = (new BigDecimal(remain).add(new BigDecimal(num))).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		} else {
			num = "0";
			sum = (new BigDecimal(remain).add(new BigDecimal(0))).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		}
		params.put("num", new BigDecimal(num).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		params.put("sum", sum);
		return params;
	}

	@ResponseBody
	@RequiresPermissions("accounting:applyAdvanceRepay:view")
	@RequestMapping(value = "validateApplyAdvanceRepay")
	public AjaxView validateApplyAdvanceRepay(ApplyAdvanceRepay applyAdvanceRepay) {
		try {
			String contractNo = URLDecoder.decode(applyAdvanceRepay.getContractNo(), "UTF-8");
			applyAdvanceRepay.setContractNo(contractNo);
		} catch (UnsupportedEncodingException e) {
			logger.error("汉字解码失败", e);
		}
		AjaxView ajaxView = new AjaxView();
		Contract contract = null;
		ContractLock contractLock = null;
		int num = 0;
		List<StaRepayPlanStatus> statuses = staRepayPlanStatusService.findStaRepayPlanStatus(applyAdvanceRepay.getContractNo());
		if (statuses != null) {
			for (StaRepayPlanStatus statuss : statuses) {
				num++;
				if (Constants.PERIOD_STATE_YQ.equals(statuss.getRepayPeriodStatus())) {
					ajaxView.setFailed().setMessage("第 " + num + " 期逾期未结清，请先结清逾期。");
					return ajaxView;
				} else {
					String newperiodNum = applyAdvanceRepayService.getMinPeriodNum(applyAdvanceRepay.getContractNo());
					contract = contractService.findContractByContractNo(applyAdvanceRepay.getContractNo());
					if (Integer.parseInt(newperiodNum) == Integer.parseInt(contract.getApproPeriodValue())) {
						ajaxView.setFailed().setMessage("已到最后期数，不可进行提前还款");
						return ajaxView;
					}
				}
			}
		}
		contractLock = new ContractLock();
		contractLock.setContractNo(applyAdvanceRepay.getContractNo());
		contractLock = contractLockDao.validateIsLock(contractLock);
		if (contractLock != null) {
			ajaxView.setFailed().setMessage("此合同号存在正在申请中的操作");
		} else {
			ajaxView.setSuccess();
		}
		return ajaxView;
	}

	@RequiresPermissions("accounting:applyAdvanceRepay:edit")
	@RequestMapping(value = "delete")
	public String delete(ApplyAdvanceRepay applyAdvanceRepay, RedirectAttributes redirectAttributes) {
		applyAdvanceRepayService.delete(applyAdvanceRepay);
		addMessage(redirectAttributes, "删除提前还款成功");
		return "redirect:" + Global.getAdminPath() + "/applyAdvanceRepay/applyAdvanceRepay/?repage";
	}

}