package com.resoft.outinterface.callinterface;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;
import com.resoft.common.utils.AjaxView;
import com.resoft.common.utils.Constants;
import com.resoft.outinterface.utils.Facade;
import com.thinkgem.jeesite.common.web.BaseController;

@Controller
@RequestMapping(value = "${adminPath}/outinterface/callInterface")
public class InterfaceCallCollectionController extends BaseController {

	/**
	 * @param 外访接口
	 */
	@RequestMapping(value = "svServiceMethod")
	public void callSVServiceMethod(String applyNo) {
		try {
			Facade.facade.SVServiceMethod(applyNo);
		} catch (Exception e) {
			logger.error("外访接口访问失败!", e);
		}
	}

	/**
	 * @param 冠E通接口
	 */
	@ResponseBody
	@RequestMapping(value = "issuingTenderData")
	public AjaxView callIssuingTenderData(String applyNo) {
		AjaxView ajaxView = new AjaxView();
		Map<String, Object> msg = Maps.newHashMap();
		try {
			msg = Facade.facade.issuingTenderData(applyNo);
			if(!((Boolean) msg.get("flag"))){
				ajaxView.setFailed().setMessage((String) msg.get("msg"));
			}
		} catch (Exception e) {
			logger.error("冠E通发标接口访问失败!", e);
		}
		return ajaxView;
	}
	
	/**
	 * @param Themis接口
	 */
	@RequestMapping(value = "getAnalysisResult")
	public void callGetAnalysisResult(String applyNo) {
		try {
			Facade.facade.getAnalysisResult(applyNo);
		} catch (Exception e) {
			logger.error("Themis接口访问失败!", e);
		}
	}
	
	/**
	 * @param Experian接口
	 */
	@RequestMapping(value = "experian")
	@ResponseBody
	public void callExperianClient(String applyNo) {
		logger.info("callExperianClient:" + applyNo);
		try {
			Facade.facade.getExperianClient(applyNo);
		} catch (Exception e) {
			logger.error("Experian接口访问失败!", e);
		}
	}
	
	/**
	 * @param 流标
	 */
	@RequestMapping(value = "abortiveTender")
	public void callAbortiveTender(String applyNo) {
		try {
			Facade.facade.abortiveTender(applyNo);
		} catch (Exception e) {
			logger.error("资金平台流标接口访问失败!", e);
		}
	}
	
	/**
	 * @param 银行卡变更
	 */
	@RequestMapping(value = "bankCardChange")
	public void callBankCardChange(String applyNo) {
		try {
			Facade.facade.bankCardChange(applyNo);
		} catch (Exception e) {
			logger.error("资金平台银行卡变更接口访问失败!", e);
		}
	}
	/**
	 * @param 借款人开户
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "borrowerCreateAccount")
	public Map<String,String> callBorrowerCreateAccount(String applyNo) {
		try {
			Map<String,String> msg =Facade.facade.borrowerCreateAccount(applyNo);
			return msg;
		} catch (Exception e) {
			logger.error("资金平台借款人开户接口访问失败!", e);
			Map<String,String> resultMap = new HashMap<String, String>();
			resultMap.put("flag","false");
			resultMap.put("msg","资金平台借款人开户接口访问失败!");
			resultMap.put("status",Constants.OPEN_ACCOUNT_STATUS_WKH);
			resultMap.put("accNo", "");
			return resultMap;
		}
	}
	/**
	 * @param 借款人提现
	 */
	@RequestMapping(value = "borrowerDeposit")
	public void callBorrowerDeposit(String applyNo) {
		try {
			Facade.facade.borrowerDeposit(applyNo);
		} catch (Exception e) {
			logger.error("资金平台借款人提现接口访问失败!", e);
		}
	}
	/**
	 * @param 借款人放款
	 */
	@RequestMapping(value = "borrowerLoan")
	public void callBorrowerLoan(String applyNo) {
		try {
			Facade.facade.borrowerLoan(applyNo);
		} catch (Exception e) {
			logger.error("资金平台借款人放款接口访问失败!", e);
		}
	}
	/**
	 * @param 抵押权人开户
	 */
	@RequestMapping(value = "mortgageeCreateAccount")
	public void callMortgageeCreateAccount(String idNum) {
		try {
			Facade.facade.mortgageeCreateAccount(idNum);
		} catch (Exception e) {
			logger.error("资金平台抵押权人开户接口访问失败!", e);
		}
	}
	/**
	 * @param 抵押权人提现
	 */
	@RequestMapping(value = "mortgageeDeposit")
	public void callMortgageeDeposit(String applyNo) {
		try {
			Facade.facade.mortgageeDeposit(applyNo);
		} catch (Exception e) {
			logger.error("资金平台抵押权人提现接口访问失败!", e);
		}
	}
	/**
	 * @param 保证金退还
	 */
	@RequestMapping(value = "refundDeposit")
	public void callrefundDeposit(String applyNo) {
		try {
			Facade.facade.refundDeposit(applyNo);
		} catch (Exception e) {
			logger.error("资金平台保证金退还接口访问失败!", e);
		}
	}
}
