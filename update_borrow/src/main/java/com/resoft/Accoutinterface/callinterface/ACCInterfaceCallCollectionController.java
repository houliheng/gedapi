package com.resoft.Accoutinterface.callinterface;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.resoft.Accoutinterface.utils.AccFacade;
import com.thinkgem.jeesite.common.web.BaseController;

@Controller
@RequestMapping(value = "${adminPath}/accounting/outinterface/callInterface")
public class ACCInterfaceCallCollectionController extends BaseController {
	
	@RequestMapping(value = "bankCardChange")
	public void callBankCardChange(String contractNo) {
		try {
			AccFacade.facade.bankCardChange(contractNo);
		} catch (Exception e) {
			logger.error("资金平台银行卡变更接口访问失败!", e);
		}
	}

	@RequestMapping(value = "refundDeposit")
	public void callrefundDeposit(String contractNo) {
		try {
			AccFacade.facade.refundDeposit(contractNo);
		} catch (Exception e) {
			logger.error("资金平台保证金退还接口访问失败!", e);
		}
	}
	@RequestMapping(value = "repaymentWithholding")
	public void callRepaymentWithholding(String contractNo) {
		try {
			AccFacade.facade.repaymentWithholding(contractNo);
		} catch (Exception e) {
			logger.error("资金平台还款划扣还接口访问失败!", e);
		}
	}
}
