package com.resoft.accounting.staContractStatus.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.resoft.accounting.common.utils.AjaxView;
import com.resoft.accounting.common.utils.Constants;
import com.resoft.accounting.contract.entity.Contract;
import com.resoft.accounting.contract.entity.ContractLock;
import com.resoft.accounting.contract.service.ContractLockService;
import com.resoft.accounting.contract.service.ContractService;
import com.resoft.accounting.staContractStatus.entity.DeductResultTemp;
import com.resoft.accounting.staContractStatus.entity.StaContractStatus;
import com.resoft.accounting.staContractStatus.service.StaContractStatusService;
import com.resoft.credit.checkApprove.entity.CheckApprove;
import com.resoft.credit.checkApprove.service.CheckApproveService;
import com.resoft.credit.checkApproveUnion.entity.CheckApproveUnion;
import com.resoft.credit.checkApproveUnion.service.CheckApproveUnionService;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * 合同还款明细查询Controller
 * 
 * @author yanwanmei
 * @version 2016-03-03
 */
@Controller
@RequestMapping(value = "${adminPath}/accounting/staContractStatus")
public class StaContractStatusController extends BaseController {

	@Autowired
	private StaContractStatusService staContractStatusService;
	// 合同service
	@Autowired
	private ContractService contractService;
	// 合同service
	@Autowired
	private ContractLockService contractLockService;
	@Autowired
	private CheckApproveUnionService checkApproveUnionService;
	@Autowired
	private CheckApproveService checkApproveService;
	@ModelAttribute
	public StaContractStatus get(@RequestParam(required = false) String contractNo) {
		StaContractStatus entity = null;
		if (StringUtils.isNotBlank(contractNo)) {
			try {
				contractNo = URLDecoder.decode(contractNo, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				logger.error("汉字解码失败", e);
			}
			entity = staContractStatusService.get(contractNo);
		}
		if (entity == null) {
			entity = new StaContractStatus();
		}
		return entity;
	}

	@RequiresPermissions("accounting:staContractStatus:view")
	@RequestMapping(value = { "list", "" })
	public String list(StaContractStatus staContractStatus, HttpServletRequest request, HttpServletResponse response,
			Model model, String queryFlag) {
		String level = null;
		if (staContractStatus != null && staContractStatus.getCompany() != null
				&& staContractStatus.getCompany().getId() != null) {
			Office office = new Office();
			office.setId(staContractStatus.getCompany().getId());
			level = staContractStatusService.getOfficeLevel(staContractStatus.getCompany().getId());
			if (Constants.OFFICE_LEVEL_DQ.equals(level)) {
				staContractStatus.setOrgLevel2(office);
			} else if (Constants.OFFICE_LEVEL_QY.equals(level)) {
				staContractStatus.setOrgLevel3(office);
			} else if (Constants.OFFICE_LEVEL_MD.equals(level)) {
				staContractStatus.setOrgLevel4(office);
			}
		}
		Page<StaContractStatus> page = null;
		if ("button".equals(queryFlag)) {
			page = staContractStatusService.findPage(new Page<StaContractStatus>(request, response), staContractStatus);
			List<StaContractStatus> list = page.getList();
			if (list.size() > 0){
				for (StaContractStatus contractStatus: list) {
					//String contractNo = "GJX07A5180519AB-^-\\";
					String contractNo = contractStatus.getContractNo();
					if(contractNo.contains("\\")){
						contractNo = contractNo.replace("\\","\\\\");
						contractStatus.setContractNo(contractNo);
					}
				}
			}
			page.setList(list);
		}



		model.addAttribute("page", page);
		model.addAttribute("staContractStatus", staContractStatus);
		return "app/accounting/staContractStatus/staContractStatusList";
	}

	@RequiresPermissions("accounting:staContractStatus:view")
	@RequestMapping(value = "form")
	public String form(StaContractStatus staContractStatus, String contractNoTmp, Model model, String backUrl) {
		staContractStatus.setContractNo(contractNoTmp);
		Contract contract = contractService.findContractByContractNo(contractNoTmp);
		if (contract != null) {
			CheckApproveUnion checkApproveUnion = checkApproveUnionService.getByApplyNoAndContract(contract.getApplyNo(), contract.getContractNo());
			String factServiceFee = "0.00";
			if (checkApproveUnion != null && StringUtils.isNotBlank(checkApproveUnion.getRealityServiceFee().toString())) {
				factServiceFee= checkApproveUnion.getRealityServiceFee().toString();
			}
			
			if (checkApproveUnion == null) {
				Map<String,String> param =  new HashMap<>();
				param.put("applyNo",contract.getApplyNo());
			List<CheckApprove> checkApproves	= checkApproveService.getCheckApproveByApplyNo(param);
			if (checkApproves.size() >0 && checkApproves.get(0) != null && checkApproves.get(0).getRealityServiceFee() != null && StringUtils.isNotBlank(checkApproves.get(0).getRealityServiceFee().toString())) {
				factServiceFee = checkApproves.get(0).getRealityServiceFee().toString();
			}
			}
			model.addAttribute("factServiceFee",factServiceFee);
		}
		
		if (contract != null){
			String contractNo = contract.getContractNo();
			if(contractNo.contains("\\")){
				contractNo = contractNo.replace("\\","\\\\");
				contract.setContractNo(contractNo);
			}
		}
		List<DeductResultTemp> deductResultTemps = staContractStatusService.queryDeductResult(contractNoTmp,null);
		model.addAttribute("contract", contract);
		model.addAttribute("backUrl", backUrl);
		model.addAttribute("staContractStatus", staContractStatus);
		model.addAttribute("deductResultTemps", deductResultTemps);
		return "app/accounting/staContractStatus/staContractStatusForm";
	}

	@RequiresPermissions("accounting:staContractStatus")
	@RequestMapping(value = "deleteContractLock")
	@ResponseBody
	public AjaxView deleteContractLock(ContractLock contractLock) {
		AjaxView ajaxView = new AjaxView();
		try {
			contractLockService.deleteLock(contractLock);
			ajaxView.setSuccess().setMessage("解锁成功");
		} catch (Exception e) {
			logger.error("解锁失败", e);
			ajaxView.setFailed().setMessage("解锁失败");
		}
		return ajaxView;
	}

	@RequestMapping(value = "upload")
	public String upload(Model model, HttpServletRequest request, HttpServletResponse reponse) throws IOException {
		MultipartHttpServletRequest mhsr = (MultipartHttpServletRequest) request;
		MultipartFile file = mhsr.getFile("uploadSta");
		if (file != null) {
			String fileName = file.getOriginalFilename();
			if (fileName == null || "".equals(fileName)) {
				model.addAttribute("errorMessageSta", "请传入文件(后缀名为xls或xlsx)!");
				return "app/accounting/contract/contratImportForm";
			} else if (fileName.indexOf(".xls") == -1 && fileName.indexOf(".xlsx") == -1) {
				model.addAttribute("errorMessageSta", "请选择正确的Excel文件(后缀名为xls或xlsx)!");
				return "app/accounting/contract/contratImportForm";
			} else {
				String message = staContractStatusService.importData(file);
				if (StringUtils.isNull(message)) {
					model.addAttribute("errorMessageSta", "导入成功");
				} else {
					model.addAttribute("errorMessageSta", message);
				}
			}
		} else {
			model.addAttribute("errorMessageSta", "请重新打开小窗口上传!");
		}
		return "app/accounting/contract/contratImportForm";
	}

}