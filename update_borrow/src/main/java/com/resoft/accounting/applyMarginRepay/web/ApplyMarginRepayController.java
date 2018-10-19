package com.resoft.accounting.applyMarginRepay.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

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

import com.resoft.Accoutinterface.utils.AccFacade;
import com.resoft.accounting.applyMarginRepay.entity.ApplyMarginRepay;
import com.resoft.accounting.applyMarginRepay.entity.ApplyMarginRepayVO;
import com.resoft.accounting.applyMarginRepay.service.ApplyMarginRepayService;
import com.resoft.accounting.applyMarginRepay.service.ApplyMarginRepayVOService;
import com.resoft.accounting.common.utils.AjaxView;
import com.resoft.accounting.common.utils.Constants;
import com.resoft.accounting.contract.entity.Contract;
import com.resoft.accounting.contract.service.ContractService;
import com.resoft.accounting.staContractStatus.entity.DeductResultTemp;
import com.resoft.accounting.staContractStatus.service.StaContractStatusService;
import com.resoft.accounting.taskCenter.entity.ActTaskParam;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * 保证金退还申请Controller
 * 
 * @author wuxi01
 * @version 2016-03-04
 */
@Controller
@RequestMapping(value = "${adminPath}/accounting/applyMarginRepay")
public class ApplyMarginRepayController extends BaseController {

	@Autowired
	private ContractService contractService;

	@Autowired
	private ApplyMarginRepayService applyMarginRepayService;

	@Autowired
	private ApplyMarginRepayVOService applyMarginRepayVOService;

	@Autowired
	private StaContractStatusService staContractStatusService;

	@ModelAttribute
	public ApplyMarginRepay get(@RequestParam(required = false) String id) {
		ApplyMarginRepay entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = applyMarginRepayService.get(id);
		}
		if (entity == null) {
			entity = new ApplyMarginRepay();
		}
		return entity;
	}

	@RequiresPermissions("accounting:applyMarginRepay:view")
	@RequestMapping(value = { "list", "" })
	public String list(ApplyMarginRepayVO applyMarginRepayVO, HttpServletRequest request, HttpServletResponse response, Model model,String queryFlag) {
		String level = null;
		if (applyMarginRepayVO != null && applyMarginRepayVO.getCompany() != null && !StringUtils.isEmpty(applyMarginRepayVO.getCompany().getId())) {
			Office office = new Office();
			office.setId(applyMarginRepayVO.getCompany().getId());
			level = staContractStatusService.getOfficeLevel(applyMarginRepayVO.getCompany().getId());
			if (Constants.OFFICE_LEVEL_DQ.equals(level)) {
				applyMarginRepayVO.setOrgLevel2(office);
			} else if (Constants.OFFICE_LEVEL_QY.equals(level)) {
				applyMarginRepayVO.setOrgLevel3(office);
			} else if (Constants.OFFICE_LEVEL_MD.equals(level)) {
				applyMarginRepayVO.setOrgLevel4(office);
			}
		}
		if("button".equals(queryFlag)){
			Page<ApplyMarginRepayVO> page = applyMarginRepayVOService.findPage(new Page<ApplyMarginRepayVO>(request, response), applyMarginRepayVO);
			model.addAttribute("page", page);
		}else{
			applyMarginRepayVO.setStartTime(new Date());
			applyMarginRepayVO.setEndTime(new Date());
			Page<ApplyMarginRepayVO> page = applyMarginRepayVOService.findPage(new Page<ApplyMarginRepayVO>(request, response), applyMarginRepayVO);
			model.addAttribute("page", page);
		}
		model.addAttribute("applyMarginRepayVO", applyMarginRepayVO);
		return "app/accounting/applyMarginRepay/applyMarginRepayList";
	}

	@RequiresPermissions("accounting:applyMarginRepay:view")
	@RequestMapping(value = "form")
	public String form(ApplyMarginRepay applyMarginRepay, Model model) {
		String contractNo = null;
		try {
			contractNo = URLDecoder.decode(applyMarginRepay.getContractNo(), "UTF-8");
			applyMarginRepay.setContractNo(contractNo);
			Contract contract = contractService.findContractByContractNo(applyMarginRepay.getContractNo());
			model.addAttribute("contract", contract);
		} catch (UnsupportedEncodingException e) {
			logger.error("汉字解码失败",e);
		}
		model.addAttribute("applyMarginRepay", applyMarginRepay);
		return "app/accounting/applyMarginRepay/applyMarginRepayForm";
	}

	@RequiresPermissions("accounting:applyMarginRepay:view")
	@RequestMapping(value = "formResult")
	public String formResult(ActTaskParam actTaskParam, ApplyMarginRepay applyMarginRepay, Model model) {
		applyMarginRepay.setContractNo(actTaskParam.getApplyNo());
		if ("1".equals(actTaskParam.getStatus())) {
			applyMarginRepay.setDoneOrDoFlag(actTaskParam.getStatus());
			model.addAttribute("readonly", "true");
		} else {
			applyMarginRepay.setMarginAmountStatus(Constants.MARGIN_AMOUNT_JSZXSH);
		}
		applyMarginRepay.setProcInsId(actTaskParam.getProcInstId());
		applyMarginRepay = applyMarginRepayService.findApplyMarginRepayByContractNo(applyMarginRepay);
		List<DeductResultTemp> deductResultTemps = staContractStatusService.queryDeductResult(applyMarginRepay.getContractNo(),null);
		model.addAttribute("deductResultTemps", deductResultTemps);
		model.addAttribute("applyMarginRepay", applyMarginRepay);
		model.addAttribute("actTaskParam", actTaskParam);
		return "app/accounting/applyMarginRepay/applyMarginRepayResult";
	}

	@RequiresPermissions("accounting:applyMarginRepay:view")
	@RequestMapping(value = "formResultValidate")
	public String formResultValidate(ActTaskParam actTaskParam, ApplyMarginRepay applyMarginRepay, Model model) {
		applyMarginRepay.setContractNo(actTaskParam.getApplyNo());
		if ("1".equals(actTaskParam.getStatus())) {
			applyMarginRepay.setDoneOrDoFlag(actTaskParam.getStatus());
		} else {
			applyMarginRepay.setMarginAmountStatus(Constants.MARGIN_AMOUNT_FGSQR);
		}
		applyMarginRepay.setProcInsId(actTaskParam.getProcInstId());
		applyMarginRepay = applyMarginRepayService.findApplyMarginRepayByContractNo(applyMarginRepay);
		model.addAttribute("applyMarginRepay", applyMarginRepay);
		model.addAttribute("actTaskParam", actTaskParam);
		return "app/accounting/applyMarginRepay/applyMarginRepayResultValidate";
	}

	@RequiresPermissions("accounting:applyMarginRepay:edit")
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxView save(ApplyMarginRepay applyMarginRepay, ActTaskParam actTaskParam, String flag) {
		AjaxView ajaxView = applyMarginRepayService.dealApplyMarginRepayData(applyMarginRepay, actTaskParam, flag);
		if (Constants.MARGIN_AMOUNT_JSZXSH.equals(flag)) {
			AccFacade.facade.refundDeposit(applyMarginRepay.getContractNo());
		}
		return ajaxView;
	}

	@RequiresPermissions("accounting:applyMarginRepay:edit")
	@RequestMapping(value = "delete")
	public String delete(ApplyMarginRepay applyMarginRepay, RedirectAttributes redirectAttributes) {
		applyMarginRepayService.delete(applyMarginRepay);
		addMessage(redirectAttributes, "删除保证金退还申请成功");
		return "redirect:" + Global.getAdminPath() + "/applyMarginRepay/applyMarginRepay/?repage";
	}

	// 保证金退还时进入。验证指定合同下是否所有期都结清 是否正在申请中
	@ResponseBody
	@RequiresPermissions("accounting:applyMarginRepay:view")
	@RequestMapping(value = "marginRepay")
	public AjaxView marginRepay(ApplyMarginRepay applyMarginRepay) {
		AjaxView ajaxView = new AjaxView();
		String contractNo;
		try {
			contractNo = URLDecoder.decode(applyMarginRepay.getContractNo(), "UTF-8");
			applyMarginRepay.setContractNo(contractNo);
			ajaxView = applyMarginRepayService.validateRepayPlanStatus(applyMarginRepay);
		} catch (UnsupportedEncodingException e) {
			logger.error("汉字解码失败",e);
		}
		return ajaxView;
	}

}