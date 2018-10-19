package com.resoft.accounting.applyChangeBankcard.web;

import java.util.LinkedHashMap;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.resoft.Accoutinterface.utils.AccFacade;
import com.resoft.accounting.applyChangeBankcard.entity.ApplyChangeBankcard;
import com.resoft.accounting.applyChangeBankcard.service.ApplyChangeBankcardService;
import com.resoft.accounting.common.utils.AjaxView;
import com.resoft.accounting.common.utils.Constants;
import com.resoft.accounting.contract.entity.Contract;
import com.resoft.accounting.contract.entity.ContractTemp;
import com.resoft.accounting.contract.service.ContractService;
import com.resoft.accounting.staContractStatus.service.StaContractStatusService;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * 银行卡变更Controller
 * 
 * @author wuxi01
 * @version 2016-03-03
 */
@Controller
@RequestMapping(value = "${adminPath}/accounting/applyChangeBankcardIndex")
public class ApplyChangeBankcardIndexController extends BaseController {

	@Autowired
	ApplyChangeBankcardService applyChangeBankcardService;

	// 合同
	@Autowired
	private ContractService contractService;
	@Autowired
	private StaContractStatusService staContractStatusService;

	@ModelAttribute
	public ApplyChangeBankcard get(@RequestParam(required = false) String id) {
		ApplyChangeBankcard entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = applyChangeBankcardService.get(id);
		}
		if (entity == null) {
			entity = new ApplyChangeBankcard();
		}
		return entity;
	}

	@RequiresPermissions("accounting:contract:view")
	@RequestMapping(value = { "list", "" })
	public String list(Contract contract, HttpServletRequest request, HttpServletResponse response, Model model) {
		String level = null;
		if (contract != null && contract.getCompany() != null && !StringUtils.isEmpty(contract.getCompany().getId()) ) {
			Office office = new Office();
			office.setId(contract.getCompany().getId());
			level = staContractStatusService.getOfficeLevel(contract.getCompany().getId());
			if (Constants.OFFICE_LEVEL_DQ.equals(level)) {
				contract.setOrgLevel2(office);
			} else if (Constants.OFFICE_LEVEL_QY.equals(level)) {
				contract.setOrgLevel3(office);
			} else if (Constants.OFFICE_LEVEL_MD.equals(level)) {
				contract.setOrgLevel4(office);
			} 
		}
		Page<Contract> page = contractService.findPage(new Page<Contract>(request, response), contract);
		model.addAttribute("page", page);
		model.addAttribute("contract", contract);
		return "app/accounting/applyChangeBankcard/changeBankcardList";
	}

	@RequiresPermissions("accounting:applyChangeBankcardIndex:edit")
	@RequestMapping(value = "load")
	public String form(HttpServletRequest request, HttpServletResponse response, ApplyChangeBankcard applyChangeBankcard, Model model) {
		Contract contract = applyChangeBankcardService.validateIsNotBlank(applyChangeBankcard);
		model.addAttribute("contract", contract);
		Page<ApplyChangeBankcard> page = applyChangeBankcardService.findPage(new Page<ApplyChangeBankcard>(request, response), applyChangeBankcard);
		model.addAttribute("page", page);
		LinkedHashMap<String, String> provinceMap = applyChangeBankcardService.loadAreaData("1");
		model.addAttribute("provinceMap", provinceMap);
		LinkedHashMap<String, String> cityMap = applyChangeBankcardService.loadAreaData(applyChangeBankcard.getNewRepBankProvince());
		model.addAttribute("cityMap", cityMap);
		LinkedHashMap<String, String> distinctMap = applyChangeBankcardService.loadAreaData(applyChangeBankcard.getNewRepBankCity());
		model.addAttribute("distinctMap", distinctMap);
		List<ContractTemp> provinceMap1 = applyChangeBankcardService.loadAreaDataObject("1");
		model.addAttribute("provinceMap1", provinceMap1);
		List<ContractTemp> cityMap1 = applyChangeBankcardService.loadAreaDataObject(contract.getRepBankProvince());
		model.addAttribute("cityMap1", cityMap1);
		List<ContractTemp> distinctMap1 = applyChangeBankcardService.loadAreaDataObject(contract.getRepBankCity());
		model.addAttribute("distinctMap1", distinctMap1);
		return "app/accounting/applyChangeBankcard/applyChangeBankcardIndex";
	}

	@RequiresPermissions("accounting:applyChangeBankcardIndex:edit")
	@RequestMapping(value = "save")
	public String save(MultipartHttpServletRequest request, HttpServletResponse response, Model model) {
		String contractNo = request.getParameter("contractNo");
		boolean flag = applyChangeBankcardService.saveApplyChangeBankcardData(request, model);
		if (flag) {
			AccFacade.facade.bankCardChange(contractNo);
			addMessage(model, "申请成功");
		}else{
			addMessage(model, "申请失败,请查看后台信息");
		}
		Contract contract = new Contract();
		return list(contract, request, response, model);
	}

	@ResponseBody
	@RequiresPermissions("accounting:applyChangeBankcardIndex:view")
	@RequestMapping(value = "validate")
	public AjaxView validate(String contractNo) {
		AjaxView ajaxView = new AjaxView();
		List<ApplyChangeBankcard> applyChangeBankcards = applyChangeBankcardService.getApplyChangeBankcardByContractNoFail(contractNo);
		if (applyChangeBankcards.size() != 0) {
			ajaxView.setFailed().setMessage("此合同正在申请银行卡变更，请稍后操作。");
		} else {
			ajaxView.setSuccess();
		}
		return ajaxView;
	}

}