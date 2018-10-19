package com.resoft.accounting.contract.web;

import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.resoft.accounting.contract.entity.Contract;
import com.resoft.accounting.contract.service.ContractService;
import com.resoft.accounting.repayPlan.entity.AccRepayPlan;
import com.resoft.accounting.repayPlan.service.AccRepayPlanService;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 合同信息Controller
 * 
 * @author wuxi01
 * @version 2016-03-03
 */
@Controller
@RequestMapping(value = "${adminPath}/accounting/Acccontract")
public class AccContractController extends BaseController {

	@Autowired
	private ContractService contractService;

	@Autowired
	private AccRepayPlanService repayPlanService;

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

	@RequiresPermissions("accounting:contract:view")
	@RequestMapping(value = { "list", "" })
	public String list(Contract contract, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Contract> page = contractService.findPage(new Page<Contract>(request, response), contract);
		model.addAttribute("page", page);
		return "app/accounting/contract/contractList";
	}

	@RequiresPermissions("accounting:contract:view")
	@RequestMapping(value = "form")
	public String form(Contract contract, Model model) {
		model.addAttribute("contract", contract);
		return "app/accounting/contract/contractForm";
	}

	@RequiresPermissions("accounting:contract:edit")
	@RequestMapping(value = "save")
	public String save(Contract contract, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, contract)) {
			return form(contract, model);
		}
		contractService.save(contract);
		addMessage(redirectAttributes, "保存合同信息成功");
		return "redirect:" + Global.getAdminPath() + "/contract/contract/?repage";
	}

	@RequiresPermissions("accounting:contract:edit")
	@RequestMapping(value = "delete")
	public String delete(Contract contract, RedirectAttributes redirectAttributes) {
		contractService.delete(contract);
		addMessage(redirectAttributes, "删除合同信息成功");
		return "redirect:" + Global.getAdminPath() + "/contract/contract/?repage";
	}

	@RequestMapping(value = "contractInfo")
	public String contractInfo(Model model, Contract contract, HttpServletRequest request, HttpServletResponse reponse) {
		String contractNo = request.getParameter("contractNo");
		contract = contractService.findContractInfoByContractNo(contractNo);
		if (contract == null) {
			contract = new Contract();
		}
		model.addAttribute("contract", contract);

		List<AccRepayPlan> repayPlanList = repayPlanService.getRepayPlanByContractNo(contractNo);
		model.addAttribute("repayPlanList", repayPlanList);
		return "app/accounting/contract/contractForm";
	}

	@RequestMapping(value = "contractImport")
	public String contractImport(HttpServletRequest request, HttpServletResponse reponse) {
		return "app/accounting/contract/contratImportForm";
	}

	@RequestMapping(value = "upload")
	public String upload(Model model, HttpServletRequest request, HttpServletResponse reponse) throws IOException {
		MultipartHttpServletRequest mhsr = (MultipartHttpServletRequest) request;
		MultipartFile file = mhsr.getFile("uploadContract");
		if (file != null) {
			String fileName = file.getOriginalFilename();
			if (fileName == null || "".equals(fileName)) {
				model.addAttribute("errorMessage", "请传入文件(后缀名为xls或xlsx)!");
				return "app/accounting/contract/contratImportForm";
			} else if (fileName.indexOf(".xls") == -1 && fileName.indexOf(".xlsx") == -1) {
				model.addAttribute("errorMessage", "请选择正确的Excel文件(后缀名为xls或xlsx)!");
				return "app/accounting/contract/contratImportForm";
			} else {
				String message = contractService.importData(file);
				if (StringUtils.isNull(message)) {
					model.addAttribute("errorMessage", "导入成功");
				} else {
					model.addAttribute("errorMessage", message);
				}
			}
		} else {
			model.addAttribute("errorMessage", "请重新打开小窗口上传!");
		}
		return "app/accounting/contract/contratImportForm";
	}

}