package com.resoft.credit.guranteeProjectList.web;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.resoft.accounting.contract.entity.Contract;
import com.resoft.accounting.contract.service.ContractService;
import com.resoft.accounting.staRepayPlanStatus.entity.StaRepayPlanStatus;
import com.resoft.accounting.staRepayPlanStatus.service.StaRepayPlanStatusService;
import com.resoft.credit.applyLoanStatus.entity.ApplyLoanStatus;
import com.resoft.credit.applyLoanStatus.service.ApplyLoanStatusService;
import com.resoft.credit.applyRegister.entity.ApplyRegister;
import com.resoft.credit.applyRegister.service.ApplyRegisterService;
import com.resoft.credit.applyRelation.entity.ApplyRelation;
import com.resoft.credit.applyRelation.service.ApplyRelationService;
import com.resoft.credit.compenSatoryDetail.service.CompensatoryDetailService;
import com.resoft.credit.guaranteeCompany.entity.CreGuaranteeCompany;
import com.resoft.credit.guaranteeCompany.service.CreGuaranteeCompanyService;
import com.resoft.credit.guranteeCompanyRelation.entity.CreApplyCompanyRelation;
import com.resoft.credit.guranteeCompanyRelation.service.CreApplyCompanyRelationService;
import com.resoft.credit.guranteeProjectList.entity.GuranteeProjectList;
import com.resoft.credit.guranteeProjectList.entity.RepayPlanDetail;
import com.resoft.credit.guranteeProjectList.service.GuranteeProjectListService;
import com.resoft.outinterface.rest.ged.entity.RepayPalnDetail;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
* @author guoshaohua
* @version 2018年4月19日 下午2:15:37
* 
*/
@Controller
@RequestMapping(value = "${adminPath}/credit/guranteeProjectManage")
public class GuranteeProjectListController extends BaseController{
	@Autowired
	private GuranteeProjectListService guranteeProjectListService;
	@Autowired
	private CompensatoryDetailService compensatoryDetailService;
	@Autowired
	private StaRepayPlanStatusService staRepayPlanStatusService;
	@Autowired
	private CreGuaranteeCompanyService creGuaranteeCompanyService;
	@Autowired
	private ContractService contractService;
	@Autowired
	private ApplyLoanStatusService applyLoanStatusService;
	@Autowired
	private ApplyRegisterService applyRegisterService;
	@Autowired
	private ApplyRelationService applyRelationService;
	@Autowired
	private CreApplyCompanyRelationService creApplyCompanyRelationService;
	public GuranteeProjectList get(@RequestParam(required=false) String id) {
		GuranteeProjectList entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = guranteeProjectListService.get(id);
		}
		if (entity == null){
			entity = new GuranteeProjectList();
		}
		return entity;
	}
	
	
	@RequestMapping(value = {"list", ""})
	public String list(GuranteeProjectList guranteeProjectList, HttpServletRequest request, HttpServletResponse response, Model model) {
		String queryLoanStatus = null;
		String queryApplyStatus = null;
		String queryAccountStatus = null;
		String guranteeId = null;
		if (StringUtils.isNotBlank(guranteeProjectList.getLoanStatus())) {
			guranteeProjectList.getLoanStatus();
			queryLoanStatus = guranteeProjectList.getLoanStatus();
		}
		if (StringUtils.isNotBlank(guranteeProjectList.getApplyStatus())) {
			guranteeProjectList.getApplyStatus();
			queryApplyStatus = guranteeProjectList.getApplyStatus();
		}
		if (StringUtils.isNotBlank(guranteeProjectList.getAccountStatus())) {
			guranteeProjectList.getAccountStatus();
			queryAccountStatus = guranteeProjectList.getAccountStatus();
		}
		if (StringUtils.isNotBlank(guranteeProjectList.getGuranteeId())) {
			guranteeId = guranteeProjectList.getGuranteeId();
		}
		List<CreGuaranteeCompany> creGuaranteeCompanies = creGuaranteeCompanyService.findAllList();
		Page<GuranteeProjectList> page = guranteeProjectListService.findCustomPage(new Page<GuranteeProjectList>(request, response), guranteeProjectList); 
		if (page.getList().size() >0) {
			List<GuranteeProjectList> guranteeProjectLists = page.getList();
			guranteeProjectLists = updateGuranteeProject(guranteeProjectLists,queryLoanStatus,queryApplyStatus,queryAccountStatus,guranteeId);
			page.setList(guranteeProjectLists);
		}
		model.addAttribute("page", page);
		model.addAttribute("creGuaranteeCompanies",creGuaranteeCompanies);
		return "app/credit/guranteeProjectList/guanrteeProjectList";
	}
	
	
	
	private List<GuranteeProjectList> updateGuranteeProject(List<GuranteeProjectList> guranteeProjectLists,String queryLoanStatus,String queryApplyStatus,String queryAccountStatus,String guranteeId){
		List<GuranteeProjectList> guranteeProjectListsFilter = new ArrayList<>();
		for(GuranteeProjectList guranteeProject:guranteeProjectLists){
			guranteeProject = confirmCurrentPeriod(guranteeProject);
			if (StringUtils.isNotBlank(guranteeProject.getAccountStatus())) {
				BigDecimal compentMoney = compensatoryDetailService.queryCompenMoneyByContractNo(guranteeProject.getContractNo());
				guranteeProject.setCompensatoryMoney(compentMoney);
			}
			if (StringUtils.isNotBlank(guranteeProject.getAccountStatus())) {
				BigDecimal contractStayMoney = new BigDecimal("0.00");
				List<RepayPalnDetail>  repayPalnDetails = staRepayPlanStatusService.findRepayPlanDetailByContract(guranteeProject.getContractNo());
				for(RepayPalnDetail repayPalnDetail :repayPalnDetails){
					contractStayMoney = contractStayMoney.add(new BigDecimal(repayPalnDetail.getStayMoney()));
					}
				guranteeProject.setStayMoney(contractStayMoney);
			}
			if (queryAccountStatus == null && queryLoanStatus == null && queryApplyStatus == null) {
				if (StringUtils.isNotBlank(guranteeProject.getApplyStatus()) && StringUtils.isNotBlank(guranteeProject.getLoanStatus()) && StringUtils.isNotBlank(guranteeProject.getAccountStatus())) {
					guranteeProject.setFlag("1");
				}
				if (StringUtils.isNotBlank(guranteeProject.getApplyStatus()) && StringUtils.isNotBlank(guranteeProject.getLoanStatus()) && StringUtils.isBlank(guranteeProject.getAccountStatus())) {
					guranteeProject.setFlag("2");
				}
				if (StringUtils.isNotBlank(guranteeProject.getApplyStatus()) && (StringUtils.isBlank(guranteeProject.getLoanStatus())) && StringUtils.isBlank(guranteeProject.getAccountStatus())) {
					guranteeProject.setFlag("3");
				}
			}else{
				if (queryAccountStatus != null && StringUtils.isNotBlank(guranteeProject.getAccountStatus()) && (queryApplyStatus == null || queryApplyStatus != null) && (queryLoanStatus == null || queryLoanStatus != null)) {
					guranteeProject.setFlag("1");
				}
				if (queryLoanStatus != null && queryAccountStatus == null && StringUtils.isNotBlank(guranteeProject.getLoanStatus()) && StringUtils.isBlank(guranteeProject.getAccountStatus()) && (queryApplyStatus == null || queryApplyStatus != null)) {
					guranteeProject.setFlag("2");
				}
				if (queryApplyStatus != null && StringUtils.isNotBlank(guranteeProject.getApplyStatus()) && StringUtils.isBlank(guranteeProject.getAccountStatus()) && StringUtils.isBlank(guranteeProject.getLoanStatus()) && queryAccountStatus == null && queryLoanStatus == null) {
					guranteeProject.setFlag("3");
				}
			}
			
			String companyId = "";
			ApplyRelation applyRelationMain = applyRelationService.getBatchRelationByCustIdAndRoleType(guranteeProject.getApplyNo(), guranteeProject.getCustId(),"");
			ApplyRelation applyRelation = applyRelationService.getByApplyNoAndRoleType(guranteeProject.getApplyNo(),"8");
			if (applyRelationMain != null && applyRelation != null && StringUtils.isNotBlank(applyRelation.getCustId())) {
				companyId = applyRelation.getCustId();
				guranteeProject.setGuranteeId(companyId);
				CreGuaranteeCompany company = creGuaranteeCompanyService.get(companyId);
				guranteeProject.setGuranteeServicceFee(new BigDecimal("1000.00"));
			}
			CreApplyCompanyRelation creApplyCompanyRelation = creApplyCompanyRelationService.findApplyRelationByCustAndRole(guranteeProject.getApplyNo(),guranteeProject.getCustId());
			if (creApplyCompanyRelation != null) {
				companyId = creApplyCompanyRelation.getCustId();
				guranteeProject.setGuranteeId(companyId);
			}
			if (StringUtils.isNotEmpty(companyId)) {
				CreGuaranteeCompany company = creGuaranteeCompanyService.get(companyId);
				guranteeProject.setGuranteeCompany(company.getGuaranteeCompanyName());
			}
		}
		
		if (guranteeId != null && StringUtils.isNotBlank(guranteeId)) {
			for(GuranteeProjectList guranteeProjectList :guranteeProjectLists){
				if (guranteeId.equals(guranteeProjectList.getGuranteeId())) {
					guranteeProjectListsFilter.add(guranteeProjectList);
				}
			}
			return guranteeProjectListsFilter;
		}else{
			return guranteeProjectLists;
		}
	}
	
	@RequestMapping(value = "contractDeatil")
	public String contractDetail(GuranteeProjectList guranteeProjectList, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<RepayPlanDetail>  repayPalnDetails = new ArrayList<>();
		if (StringUtils.isNotBlank(guranteeProjectList.getContractNo())) {
			Contract contract =contractService.findContractByContractNo(guranteeProjectList.getContractNo());
			if (contract != null) {
				guranteeProjectList = new GuranteeProjectList();
				guranteeProjectList.setBorrowName(contract.getCustName());
				guranteeProjectList.setContractNo(contract.getContractNo());
				guranteeProjectList.setBorrowMoney(contract.getContractAmount());
				guranteeProjectList.setPeriodValue(contract.getApproPeriodValue());
				guranteeProjectList.setYearRate(contract.getApproYearRate().toString());
				guranteeProjectList.setApproveLoanRepayType(contract.getApproLoanRepayType());
				ApplyLoanStatus applyLoanStatus =applyLoanStatusService.queryContractLoanStatus(contract.getContractNo());
				if (contract.getStaContractStatus() !=null && contract.getStaContractStatus().getRepayContractStatus() != null && StringUtils.isNotBlank(contract.getStaContractStatus().getRepayContractStatus())) {
					guranteeProjectList.setAccountStatus(contract.getStaContractStatus().getRepayContractStatus());
					guranteeProjectList.setFlag("1");
				}else if (applyLoanStatus != null && StringUtils.isNotBlank(applyLoanStatus.getLoanStatus())) {
					guranteeProjectList.setLoanStatus(applyLoanStatus.getLoanStatus());
					guranteeProjectList.setFlag("2");
				}else if (applyLoanStatus == null || (applyLoanStatus != null && StringUtils.isBlank(applyLoanStatus.getLoanStatus()))) {
					ApplyRegister applyRegister = applyRegisterService.getApplyRegisterByContractNo(contract.getContractNo());
					if (applyRegister != null && StringUtils.isNotBlank(applyRegister.getApplyStatus())) {
						guranteeProjectList.setApplyStatus(applyRegister.getApplyStatus());
						guranteeProjectList.setFlag("3");
					}
				}
				
				if (contract.getFactGuaranteeGold() != null && StringUtils.isNotBlank(contract.getFactGuaranteeGold().toString())) {
					guranteeProjectList.setGuranteeGoldFlag("0");
					guranteeProjectList.setGuranteeGold(contract.getFactGuaranteeGold().toString());
				}else{
					guranteeProjectList.setGuranteeGoldFlag("1");
					
				}
				
				if (contract.getFactGuaranteeFee() != null && StringUtils.isNotBlank(contract.getFactGuaranteeFee().toString())) {
					guranteeProjectList.setGuranteeServicceFeeFlag("0");
					guranteeProjectList.setGuranteeServicceFee(contract.getFactGuaranteeFee());
				}else{
					guranteeProjectList.setGuranteeServicceFeeFlag("1");
				}
				guranteeProjectList = confirmCurrentPeriod(guranteeProjectList);
				repayPalnDetails = staRepayPlanStatusService.findContractRepayDetail(guranteeProjectList.getContractNo());
			}else{
				guranteeProjectList = guranteeProjectListService.findGuranteeProjectListByContractNo(guranteeProjectList.getContractNo());
				if (guranteeProjectList == null) {
					guranteeProjectList = new GuranteeProjectList();
				}
				if (guranteeProjectList.getGuranteeGold() != null && StringUtils.isNotBlank(guranteeProjectList.getGuranteeGold())) {
					guranteeProjectList.setGuranteeGoldFlag("0");
				}else {
					guranteeProjectList.setGuranteeGoldFlag("1");
				}
				if (guranteeProjectList.getGuranteeServicceFee() != null && StringUtils.isNotBlank(guranteeProjectList.getGuranteeServicceFee().toString())) {
					guranteeProjectList.setGuranteeGold(contract.getFactGuaranteeGold().toString());
					guranteeProjectList.setGuranteeServicceFeeFlag("0");
				}else {
					guranteeProjectList.setGuranteeServicceFeeFlag("1");
				}
				if (StringUtils.isNotBlank(guranteeProjectList.getApplyStatus()) && StringUtils.isNotBlank(guranteeProjectList.getLoanStatus()) && StringUtils.isNotBlank(guranteeProjectList.getAccountStatus())) {
					guranteeProjectList.setFlag("1");
				}
				if (StringUtils.isNotBlank(guranteeProjectList.getApplyStatus()) && StringUtils.isNotBlank(guranteeProjectList.getLoanStatus()) && StringUtils.isBlank(guranteeProjectList.getAccountStatus())) {
					guranteeProjectList.setFlag("2");
				}
				if (StringUtils.isNotBlank(guranteeProjectList.getApplyStatus()) && StringUtils.isBlank(guranteeProjectList.getLoanStatus()) && StringUtils.isBlank(guranteeProjectList.getAccountStatus())) {
					guranteeProjectList.setFlag("3");
				}
			}
		}
		model.addAttribute("repayPalnDetails",repayPalnDetails);
		model.addAttribute("guranteeProjectList",guranteeProjectList);
		return "app/credit/guranteeProjectList/contractDetail";
	}
	
	
	
	private GuranteeProjectList confirmCurrentPeriod(GuranteeProjectList guranteeProject){
		Map<String,String> conparam = new HashMap<>();
		if (StringUtils.isNotBlank(guranteeProject.getAccountStatus()) && "0900".equals(guranteeProject.getAccountStatus()) && "0700".equals(guranteeProject.getAccountStatus())) {
			guranteeProject.setCurrentPeriod("");
		}else{
			conparam.put("contractNo", guranteeProject.getContractNo());
			List<StaRepayPlanStatus>  staRepayPlanStatusList = staRepayPlanStatusService.findUpdateRepayPeroidStatus(conparam);
			if (staRepayPlanStatusList.size() >0 ) {
				for(StaRepayPlanStatus staRepayPlanStatus :staRepayPlanStatusList){
					if (staRepayPlanStatus != null) {
						if (StringUtils.isNotBlank(staRepayPlanStatus.getRepayPeriodStatus()) && ("0200".equals(staRepayPlanStatus.getRepayPeriodStatus()) || "0300".equals(staRepayPlanStatus.getRepayPeriodStatus()))) {
							guranteeProject.setCurrentPeriod(staRepayPlanStatus.getPeriodNum());
						}else{
							int perNum = Integer.parseInt(staRepayPlanStatus.getPeriodNum()) + 1;
							guranteeProject.setCurrentPeriod(String.valueOf(perNum));
						}
					}else{
						Contract contract =contractService.findContractByContractNo(guranteeProject.getContractNo());
						if (contract  != null) {
							guranteeProject.setCurrentPeriod("1");
						}
					}
				}
			}else{
				Contract contract =contractService.findContractByContractNo(guranteeProject.getContractNo());
				if (contract  != null) {
					guranteeProject.setCurrentPeriod("1");
				}
			}	
		}
		return guranteeProject;
	}
	
}
