package com.resoft.credit.repayPlanTrial.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Maps;
import com.resoft.common.utils.CalculatorFormulasUtils;
import com.resoft.common.utils.Constants;
import com.resoft.credit.contract.service.ContractService;
import com.resoft.credit.rateCapital.entity.RateCapital;
import com.resoft.credit.repayPlanTrial.entity.RepayPlanTrial;
import com.thinkgem.jeesite.common.web.BaseController;


@Controller
@RequestMapping(value = "${adminPath}/credit/repayPlanTrial")
public class RepayPlanTrialController extends BaseController {
	
	
	@Autowired
	private ContractService contractService;
	
	
	@RequiresPermissions("credit:repayPlanTrial:view")
	@RequestMapping(value = {"list"})
	public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
		RepayPlanTrial repayPlanTrial = new RepayPlanTrial();
		model.addAttribute("repayPlanTrial", repayPlanTrial);
		return "app/credit/repayPlanTrial/repayPlanTrialList";
	}
	
	@RequiresPermissions("credit:repayPlanTrial:view")
	@RequestMapping(value = {""})
	public String listTrans(RepayPlanTrial repayPlanTrial, HttpServletRequest request, HttpServletResponse response, Model model){
	  //List<RepayPlan> repayPlanList = new ArrayList<RepayPlan>();
		Map<String, String> params = Maps.newConcurrentMap();
		params.put("approProductTypeCode",repayPlanTrial.getProductTypeCode());
		params.put("loanRepayType", repayPlanTrial.getLoanRepayType());
		params.put("periodValue", repayPlanTrial.getPeriodValue());
		List<RateCapital> rateCapitalList = contractService.getRateCapitalCurr(params);
		// 合同金额
		BigDecimal contractAmount = new BigDecimal(repayPlanTrial.getAmountValue());
		// 期数
		int approPeriodValue = Integer.parseInt(repayPlanTrial.getPeriodValue());
		//合同利率
		BigDecimal contractRate = (new BigDecimal(repayPlanTrial.getContractRate())).divide(new BigDecimal(100), 6, BigDecimal.ROUND_HALF_UP);
		List<Map<String, BigDecimal>> exportRepayPlanList = new ArrayList<Map<String,BigDecimal>>();
		if(Constants.LOAN_REPAY_TYPE_DEBX.equals(repayPlanTrial.getLoanRepayType())){
			exportRepayPlanList = CalculatorFormulasUtils.getACPIMonthPaymentAmount(contractAmount, new BigDecimal(rateCapitalList.get(0).getRateInterest().getRateInterest()), approPeriodValue, contractRate);
		}
		else{
			exportRepayPlanList = CalculatorFormulasUtils.getLadderMonthPaymentAmount(contractAmount, new BigDecimal(rateCapitalList.get(0).getRateInterest().getRateInterest()), approPeriodValue, contractRate, rateCapitalList);
		}
			
		model.addAttribute("exportRepayPlanList", exportRepayPlanList);
		
		return "app/credit/repayPlanTrial/repayPlanTrialList" ;
	}

}
