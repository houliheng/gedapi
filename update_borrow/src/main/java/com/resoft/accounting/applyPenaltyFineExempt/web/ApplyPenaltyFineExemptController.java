package com.resoft.accounting.applyPenaltyFineExempt.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
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

import com.google.common.collect.Maps;
import com.resoft.accounting.applyPenaltyFineExempt.entity.ApplyPenaltyFineExempt;
import com.resoft.accounting.applyPenaltyFineExempt.entity.ApplyPenaltyFineExemptVO;
import com.resoft.accounting.applyPenaltyFineExempt.service.ApplyPenaltyFineExemptService;
import com.resoft.accounting.applyPenaltyFineExempt.service.ApplyPenaltyFineExemptVOService;
import com.resoft.accounting.common.utils.AjaxView;
import com.resoft.accounting.common.utils.Constants;
import com.resoft.accounting.contract.dao.ContractLockDao;
import com.resoft.accounting.contract.entity.ContractLock;
import com.resoft.accounting.staContractStatus.entity.DeductResultTemp;
import com.resoft.accounting.staContractStatus.service.StaContractStatusService;
import com.resoft.accounting.taskCenter.entity.ActTaskParam;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * 违约金罚息减免Controller
 * 
 * @author yanwanmei
 * @version 2016-03-03
 */
@Controller
@RequestMapping(value = "${adminPath}/accounting/applyPenaltyFineExempt")
public class ApplyPenaltyFineExemptController extends BaseController {

	@Autowired
	private ActTaskService actTaskService;

	@Autowired
	private ApplyPenaltyFineExemptService applyPenaltyFineExemptService;

	@Autowired
	private ApplyPenaltyFineExemptVOService applyPenaltyFineExemptVOService;

	@Autowired
	private StaContractStatusService staContractStatusService;

	@Autowired
	private ContractLockDao contractLockDao;
	

	@ModelAttribute
	public ApplyPenaltyFineExempt get(@RequestParam(required = false) String id) {
		ApplyPenaltyFineExempt entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = applyPenaltyFineExemptService.get(id);
		}
		if (entity == null) {
			entity = new ApplyPenaltyFineExempt();
		}
		return entity;
	}

	@RequiresPermissions("accounting:applyPenaltyFineExempt:view")
	@RequestMapping(value = { "list", "" })
	public String list(ActTaskParam actTaskParam, ApplyPenaltyFineExempt applyPenaltyFineExempt, HttpServletRequest request, HttpServletResponse response, Model model) {
		applyPenaltyFineExempt.setContractNo(actTaskParam.getApplyNo());
		applyPenaltyFineExempt.setPeriodNum(actTaskParam.getPeriodNum());
		if ("1".equals(actTaskParam.getStatus())) {
			applyPenaltyFineExempt.setDoneOrDoFlag(actTaskParam.getStatus());
		}
		applyPenaltyFineExempt.setProcInsId(actTaskParam.getProcInstId());
		List<DeductResultTemp> deductResultTemps = staContractStatusService.queryDeductResult(actTaskParam.getApplyNo(),null);
		ApplyPenaltyFineExempt applyPenaltyFineExempts = applyPenaltyFineExemptService.getApplyingApplyPenaltyFineExemptByContractNoAndPeriodNum(applyPenaltyFineExempt);
		if (applyPenaltyFineExempts.getThroughTime() != null) {
			model.addAttribute("flag", "all");
		} else {
			model.addAttribute("flag", "part");
		}
		model.addAttribute("applyPenaltyFineExempt", applyPenaltyFineExempts);
		model.addAttribute("deductResultTemps", deductResultTemps);
		model.addAttribute("actTaskParam", actTaskParam);
		return "app/accounting/applyPenaltyFineExempt/applyPenaltyFineExemptList";
	}

	@RequiresPermissions("accounting:applyPenaltyFineExempt:view")
	@RequestMapping(value = "form")
	public String form(ApplyPenaltyFineExempt applyPenaltyFineExempt, Model model, String flag) {
		String contractNo;
		Map<String, Object> params = Maps.newHashMap();
		try {
			contractNo = URLDecoder.decode(applyPenaltyFineExempt.getContractNo(), "UTF-8");
			applyPenaltyFineExempt.setContractNo(contractNo);
			if ("1".equals(flag)) {
				params = applyPenaltyFineExemptService.findApplyPenaltyFineExemptOnStaRepayPlanStatus(applyPenaltyFineExempt);
			} else {
				params = applyPenaltyFineExemptService.findApplyPenaltyFineExempt(applyPenaltyFineExempt);
			}
			model.addAttribute("fineExemptAmount", params.get("amount1"));
			model.addAttribute("penaltyExemptAmount", params.get("amount2"));
		} catch (UnsupportedEncodingException e) {
			logger.error("汉字解码失败",e);
		}
		model.addAttribute("applyPenaltyFineExempt", applyPenaltyFineExempt);
		model.addAttribute("flag", flag);
		return "app/accounting/applyPenaltyFineExempt/applyPenaltyFineExemptForm";
	}

	// 减免申请进入
	@RequiresPermissions("accounting:applyPenaltyFineExempt:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public AjaxView save(ApplyPenaltyFineExempt applyPenaltyFineExempt) {
		AjaxView ajaxView = new AjaxView();
		try {
			applyPenaltyFineExempt.setApplyStatus(Constants.APPLY_STATUS_SQ);
			applyPenaltyFineExemptService.save(applyPenaltyFineExempt);
			String procInsId = actTaskService.startProcess(Constants.WORKFLOW_APPLY_MARGIN_REPAY, "acc_apply_penalty_fine_exempt", applyPenaltyFineExempt.getId());
			applyPenaltyFineExempt.setProcInsId(procInsId);
			applyPenaltyFineExemptService.updateProcInsIdById(applyPenaltyFineExempt);
			ajaxView.setSuccess().setMessage("申请违约金罚息减免成功！");
		} catch (Exception e) {
			logger.error("申请违约金罚息减免失败", e);
			ajaxView.setFailed().setMessage("申请违约金罚息减免失败！");
		}
		return ajaxView;
	}

	// 减免审核提交进入
	@ResponseBody
	@RequiresPermissions("accounting:applyPenaltyFineExempt:edit")
	@RequestMapping(value = "saveResult")
	public AjaxView saveResult(@RequestBody ApplyPenaltyFineExempt applyPenaltyFineExempt, ActTaskParam actTaskParam, String passFlag, String flag) {
		AjaxView ajaxView = new AjaxView();
		ajaxView = applyPenaltyFineExemptService.saveApplyPenaltyFineExemptResult(applyPenaltyFineExempt, actTaskParam, passFlag, flag);
		if("yes".equals(ajaxView.get("callFlag"))){
			ajaxView = applyPenaltyFineExemptService.callSPAccExemptUpdateState(applyPenaltyFineExempt);
		}
		return ajaxView;
		
	}

	@RequiresPermissions("accounting:applyPenaltyFineExempt:edit")
	@RequestMapping(value = "delete")
	public String delete(ApplyPenaltyFineExempt applyPenaltyFineExempt, RedirectAttributes redirectAttributes) {
		applyPenaltyFineExemptService.delete(applyPenaltyFineExempt);
		addMessage(redirectAttributes, "删除违约金罚息减免成功");
		return "redirect:" + Global.getAdminPath() + "/applyPenaltyFineExempt/applyPenaltyFineExempt/?repage";
	}

	@RequiresPermissions("accounting:applyPenaltyFineExempt:view")
	@RequestMapping(value = "penaltySearch")
	public String penaltySearch(ApplyPenaltyFineExemptVO applyPenaltyFineExemptVO, HttpServletRequest request, HttpServletResponse response, Model model,String queryFlag) {
		String level = null;
		if (applyPenaltyFineExemptVO != null && applyPenaltyFineExemptVO.getCompany() != null && !StringUtils.isEmpty(applyPenaltyFineExemptVO.getCompany().getId())) {
			Office office = new Office();
			office.setId(applyPenaltyFineExemptVO.getCompany().getId());
			level = staContractStatusService.getOfficeLevel(applyPenaltyFineExemptVO.getCompany().getId());
			if (Constants.OFFICE_LEVEL_DQ.equals(level)) {
				applyPenaltyFineExemptVO.setOrgLevel2(office);
			} else if (Constants.OFFICE_LEVEL_QY.equals(level)) {
				applyPenaltyFineExemptVO.setOrgLevel3(office);
			} else if (Constants.OFFICE_LEVEL_MD.equals(level)) {
				applyPenaltyFineExemptVO.setOrgLevel4(office);
			}
		}
		if("button".equals(queryFlag)){
			Page<ApplyPenaltyFineExemptVO> page = applyPenaltyFineExemptVOService.findPage(new Page<ApplyPenaltyFineExemptVO>(request, response), applyPenaltyFineExemptVO);
			model.addAttribute("page", page);
		}else{
			applyPenaltyFineExemptVO.setPenaltyEndTime(new Date());
			applyPenaltyFineExemptVO.setPenaltyStartTime(new Date());
			Page<ApplyPenaltyFineExemptVO> page = applyPenaltyFineExemptVOService.findPage(new Page<ApplyPenaltyFineExemptVO>(request, response), applyPenaltyFineExemptVO);
			model.addAttribute("page", page);
		}
		model.addAttribute("applyPenaltyFineExemptVO", applyPenaltyFineExemptVO);
		return "app/accounting/applyPenaltyFineExempt/applyPenaltyFineExemptSearchList";
	}

	@RequiresPermissions("accounting:applyPenaltyFineExempt:edit")
	@RequestMapping(value = "validatePenalty")
	@ResponseBody
	public AjaxView validatePenalty(ApplyPenaltyFineExempt applyPenaltyFineExempt, String status) {
		AjaxView ajaxView = new AjaxView();
		String contractNo;
		try {
			contractNo = URLDecoder.decode(applyPenaltyFineExempt.getContractNo(), "UTF-8");
			applyPenaltyFineExempt.setContractNo(contractNo);
			if (Constants.PERIOD_STATE_YQ.equals(status)) {
				ContractLock contractLock = new ContractLock();
				contractLock.setContractNo(applyPenaltyFineExempt.getContractNo());
				// contractLock.setLockFlag("1");
				contractLock = contractLockDao.validateIsLock(contractLock);
				if (contractLock != null) {
					ajaxView.setFailed().setMessage("此期正在进行其他操作！");
				} else {
					ApplyPenaltyFineExempt applyPenaltyFineExemptss = applyPenaltyFineExemptService.getApplyingApplyPenaltyFineExemptByContractNoAndPeriodNum(applyPenaltyFineExempt);
					if (applyPenaltyFineExemptss != null) {
						ajaxView.setFailed().setMessage("此期已有申请中操作！");
					} else {
						ajaxView.setSuccess();
					}
				}
			} else {
				ajaxView.setFailed().setMessage("此期未逾期，无需进行减免操作！");
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("汉字解码失败",e);
			ajaxView.setFailed().setMessage("汉字解码失败");
		}
		return ajaxView;
	}
	

	// 减免退回
	@ResponseBody
	@RequiresPermissions("accounting:applyPenaltyFineExempt:edit")
	@RequestMapping(value = "dealStaPenaltyFineExempt")
	public AjaxView dealStaPenaltyFineExempt(ApplyPenaltyFineExempt applyPenaltyFineExempt, ActTaskParam actTaskParam, String passFlag, String flag) {
		AjaxView ajaxView = new AjaxView();
		try {
			applyPenaltyFineExemptService.dealStaPenaltyFineExempt(applyPenaltyFineExempt);
			ajaxView.setSuccess().setMessage("减免退回成功");
			ajaxView.put("contractNo", applyPenaltyFineExempt.getContractNo());
		} catch (Exception e) {
			logger.error("减免退回失败",e);
			ajaxView.setFailed().setMessage("减免退回失败");
		}
		return ajaxView;
		
	}

	
	
}