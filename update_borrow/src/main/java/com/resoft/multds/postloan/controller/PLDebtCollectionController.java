package com.resoft.multds.postloan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.resoft.accounting.contract.entity.Contract;
import com.resoft.accounting.contract.service.ContractService;
import com.resoft.accounting.staContractStatus.entity.DeductResultTemp;
import com.resoft.accounting.staContractStatus.entity.StaContractStatus;
import com.resoft.accounting.staContractStatus.service.StaContractStatusService;
import com.thinkgem.jeesite.common.web.BaseController;

@Controller
@RequestMapping(value = "/f/plAccounting/plDebtCollectionDetail")
public class PLDebtCollectionController extends BaseController {

	@Autowired
	private StaContractStatusService staContractStatusService;
	// 合同service
	@Autowired
	private ContractService contractService;

	@RequestMapping("plDebtCollectionDetails")
	public String parentCompanyAudit(StaContractStatus staContractStatus, Model model) {
		Contract contract = contractService.findContractByContractNo(staContractStatus.getContractNo());
		List<DeductResultTemp> deductResultTemps = staContractStatusService.queryDeductResult(staContractStatus.getContractNo(),null);
		model.addAttribute("contract", contract);
		model.addAttribute("pl", "pl");
		model.addAttribute("staContractStatus", staContractStatus);
		model.addAttribute("deductResultTemps", deductResultTemps);
		return "app/accounting/staContractStatus/staContractStatusForm";
	}
}
