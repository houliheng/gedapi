package com.resoft.credit.compenSatoryList.web;

import java.math.BigDecimal;
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

import com.google.common.collect.Lists;
import com.resoft.Accoutinterface.utils.AccFacade;
import com.resoft.common.utils.AjaxView;
import com.resoft.common.utils.Constants;
import com.resoft.credit.GedAccount.entity.GedAccount;
import com.resoft.credit.GedAccount.service.GedAccountService;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.compenSatoryDetail.entity.CompensatoryDetail;
import com.resoft.credit.compenSatoryList.entity.CompenSatoryList;
import com.resoft.credit.compenSatoryList.service.CompenSatoryListService;
import com.resoft.credit.compensatory.entity.CompensatoryAccount;
import com.resoft.credit.compensatory.service.CompensatoryAccountService;
import com.resoft.credit.contract.entity.Contract;
import com.resoft.credit.contract.service.ContractService;
import com.resoft.credit.processSuggestionInfo.entity.ProcessSuggestionInfo;
import com.resoft.credit.product.entity.Product;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Office;


@Controller
@RequestMapping(value = "${adminPath}/credit/compenSatoryList")
public class CompenSatoryListController extends BaseController {

	@Autowired
	private CompenSatoryListService compenSatoryListService;
	@Autowired
	private CompensatoryAccountService compensatoryAccountService;
	@Autowired
	private ContractService contractService;
	@Autowired
	private GedAccountService gedAccountService;
	
	@ModelAttribute
	public CompenSatoryList get(@RequestParam(required=false) String id) {
		CompenSatoryList entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = compenSatoryListService.get(id);
		}
		if (entity == null){
			entity = new CompenSatoryList();
		}
		return entity;
	}
	
	
	@RequestMapping(value = {"list", ""})
	public String list(CompenSatoryList compenSatoryList, HttpServletRequest request, HttpServletResponse response, Model model) {

		  CompenSatoryList compenSatory = compenSatoryListService.findsumRemainAmount(compenSatoryList);
		  model.addAttribute("compenSatory",compenSatory);


		Page<CompenSatoryList> page = compenSatoryListService.findPage(new Page<CompenSatoryList>(request, response), compenSatoryList);
		model.addAttribute("page", page);

		return "app/credit/compenSatoryList/compenSatoryList";
	}
	@RequestMapping(value = "form")
	public String form(CompensatoryDetail compensatoryDetail,String contractNo,String periodNum,BigDecimal compensatoryAmount,CompenSatoryList compenSatoryList, Model model) {
		//List<CompensatoryAccount> compensatoryAccountList=compensatoryAccountService.getMostPriopity();
		//List<CompensatoryAccount> compensatoryAccountLists = compensatoryAccountService.getAllAccount();
		//if(compensatoryAccountList.get(0)!=null) {
		//compensatoryDetail.setCompensatoryAccount(compensatoryAccountList.get(0).getCompensatoryAccount());
		compensatoryDetail.setCompensatoryAmount(compensatoryAmount);
		compensatoryDetail.setContractNo(contractNo);
		compensatoryDetail.setPeriodNum(periodNum);
		//}
		model.addAttribute("newDate", new Date());
		model.addAttribute("compensatoryDetail", compensatoryDetail);
		model.addAttribute("compensatoryAccountList", compensatoryDetail.getCompensatoryAccount());
		//model.addAttribute("compensatoryAccountList", compensatoryAccountList);
		//model.addAttribute("compensatoryAccountLists", compensatoryAccountLists);
		return "app/credit/compenSatoryList/compenSatoryDuForm";
	}
	
	
	@RequestMapping("/compensatoryAccount")
	@ResponseBody
	public List<CompensatoryAccount> compensatoryAccount(String accountType, String contractNo, String periodNum) {
		List<CompensatoryAccount> compensatoryAccountLists = null;
		if("1".equals(accountType)) {
			Contract contract = new Contract();
			contract.setContractNo(contractNo);
			List<Contract> contractList = contractService.findList(contract);
			if(contractList.size()>0) {
				contract = contractList.get(0);
				compensatoryAccountLists = compensatoryAccountService.getOurselvesAccount(contract);
			}
		}
		if("2".equals(accountType)) {
			Contract contract = new Contract();
			contract.setContractNo(contractNo);
			List<Contract> contractList = contractService.findList(contract);
			if(contractList.size()>0) {
				contract = contractList.get(0);
				compensatoryAccountLists = compensatoryAccountService.getDanBaoCompanyAccount(contract);
			}		
		}
		if("3".equals(accountType)) {
			compensatoryAccountLists = compensatoryAccountService.getAllAccount();
		}
		//yu e
		//List<CompensatoryAccount> repaymentFindAccount = AccFacade.facade.repaymentFindAccount(compensatoryAccountLists);
		return compensatoryAccountLists;
	}
	
	

	@ResponseBody
	@RequestMapping(value = "showAccountName")
	// 当借前外访点“提交”时，将外访意见保存到cre_process_suggestion_info表中
	public AjaxView showAccountName(String custId) {
		AjaxView ajaxView = new AjaxView();
		GedAccount gedAccount = gedAccountService.getSingleByCustID(custId);
		String name=null;
		if(gedAccount!=null) {
			name=gedAccount.getLegalPerName();
			ajaxView.setSuccess().setMessage(name);
		}else {
			ajaxView.setFailed().setMessage("未找到用户名!");
		}
		return ajaxView;
	}
	
}
