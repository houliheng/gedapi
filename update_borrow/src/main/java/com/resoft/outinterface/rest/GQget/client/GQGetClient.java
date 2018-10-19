package com.resoft.outinterface.rest.GQget.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.resoft.credit.applyRegister.entity.ApplyRegister;
import com.resoft.credit.applyRegister.service.ApplyRegisterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;
import com.resoft.accounting.RepayDetail.entity.RepayDetail;
import com.resoft.accounting.RepayDetail.service.RepayDetailService;
import com.resoft.accounting.RepayPlanStatus.entity.RepayPlanStatus;
import com.resoft.accounting.RepayPlanStatus.service.RepayPlanStatusService;
import com.resoft.accounting.advanceRepayGET.entity.AdvanceRepayGet;
import com.resoft.common.utils.JsonTransferUtils;
import com.resoft.credit.contract.entity.Contract;
import com.resoft.credit.contract.service.ContractService;
import com.resoft.credit.gqgetComInfo.entity.GqgetComInfo;
import com.resoft.credit.gqgetComInfo.service.GqgetComInfoService;
import com.resoft.credit.gqgetComInfoUnion.entity.GqgetComInfoUnion;
import com.resoft.credit.gqgetComInfoUnion.service.GqgetComInfoUnionService;
import com.resoft.credit.interfaceinfo.entity.InterfaceInfo;
import com.resoft.credit.interfaceinfo.service.InterfaceInfoService;
import com.resoft.outinterface.rest.GQget.client.entry.request.abortiveTender.AbortiveBidDataRequest;
import com.resoft.outinterface.rest.GQget.client.entry.request.abortiveTender.GetAbortiveTenderRequest;
import com.resoft.outinterface.rest.GQget.client.entry.request.bidWithdrawSuccess.BidWithdrawSuccessRequest;
import com.resoft.outinterface.rest.GQget.client.entry.request.bidWithdrawSuccess.WithdrawSuccessBidDataRequest;
import com.resoft.outinterface.rest.GQget.client.entry.request.issuingTender.BaseData;
import com.resoft.outinterface.rest.GQget.client.entry.request.issuingTender.BidRepayment;
import com.resoft.outinterface.rest.GQget.client.entry.request.issuingTender.Biddata;
import com.resoft.outinterface.rest.GQget.client.entry.request.issuingTender.BiddataInput;
import com.resoft.outinterface.rest.GQget.client.entry.request.issuingTender.BiddataOut;
import com.resoft.outinterface.rest.GQget.client.entry.request.issuingTender.CreditAdjunt;
import com.resoft.outinterface.rest.GQget.client.entry.request.issuingTender.CreditAppliance;
import com.resoft.outinterface.rest.GQget.client.entry.request.issuingTender.CreditAutomobile;
import com.resoft.outinterface.rest.GQget.client.entry.request.issuingTender.CreditCompany;
import com.resoft.outinterface.rest.GQget.client.entry.request.issuingTender.CreditHouse;
import com.resoft.outinterface.rest.GQget.client.entry.request.issuingTender.CreditInfo;
import com.resoft.outinterface.rest.GQget.client.entry.request.issuingTender.CreditOther;
import com.resoft.outinterface.rest.GQget.client.entry.request.issuingTender.CreditSurety;
import com.resoft.outinterface.rest.GQget.client.entry.request.issuingTender.GetClientBidDataRequest;
import com.resoft.outinterface.rest.GQget.client.entry.request.issuingTender.HypothecariusCustInfo;
import com.resoft.outinterface.rest.GQget.client.entry.request.issuingTender.IssuingTenderRequest;
import com.resoft.outinterface.rest.GQget.client.entry.request.issuingTender.LoanCompany;
import com.resoft.outinterface.rest.GQget.client.entry.request.issuingTender.LoanCustInfo;
import com.resoft.outinterface.rest.GQget.client.entry.request.issuingTender.MiddleCustInfo;
import com.resoft.outinterface.rest.GQget.client.entry.request.repaymentWithdraw.RepaymentBidDataRequest;
import com.resoft.outinterface.rest.GQget.client.entry.request.repaymentWithdraw.RepaymentWithdrawRequest;
import com.resoft.outinterface.rest.GQget.client.entry.response.GetClientResponse;
import com.resoft.outinterface.rest.GQget.client.entry.response.RepayStatusResponse;
import com.resoft.outinterface.rest.GQget.client.service.GQGetClientService;
import com.resoft.outinterface.rest.financialPlatform.FinancialPlatformClient;
import com.resoft.outinterface.rest.financialPlatform.entry.RepaymentWithholdingList;
import com.resoft.outinterface.utils.OutInterfaceUtils;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;

@RestController
@RequestMapping(value = "/f/rest/Get/service")
public class GQGetClient {

	private static final Logger logger = LoggerFactory.getLogger(FinancialPlatformClient.class);
	private GQGetClientService gqGetClientService = SpringContextHolder.getBean("GQGetClientService");
	private InterfaceInfoService interfaceInfoService = SpringContextHolder.getBean("interfaceInfoService");
	private ContractService creContractService = SpringContextHolder.getBean("creContractService");
	private GqgetComInfoService gqgetComInfoService = SpringContextHolder.getBean("gqgetComInfoService");
	private GqgetComInfoUnionService gqgetComInfoUnionService = SpringContextHolder.getBean("gqgetComInfoUnionService");
	private RepayDetailService repayDetailService = SpringContextHolder.getBean("repayDetailService");
	private RepayPlanStatusService repayStatusSerivce = SpringContextHolder.getBean("repayPlanStatusService");
	private ApplyRegisterService applyRegisterService = SpringContextHolder.getBean("applyRegisterService");


	// 流标
	@RequestMapping(method = RequestMethod.GET, value = "GetAbortiveTenderRequest/{applyNo}")
	public String abortiveTender(@PathVariable String applyNo) {
		GetAbortiveTenderRequest abortiveTenderRequest = new GetAbortiveTenderRequest();
		AbortiveBidDataRequest abortiveBidDataRequest = new AbortiveBidDataRequest();
		abortiveBidDataRequest = gqGetClientService.getAbortiveBidByApplyNo(applyNo);
		abortiveTenderRequest.setBiddata(abortiveBidDataRequest);
		Date sendDate = new Date();
		try {
			String json = JsonTransferUtils.bean2Json(abortiveTenderRequest);
			String response = OutInterfaceUtils.load(Global.getConfig("GetAbortiveTender"), json);
			GetClientResponse fpr = JsonTransferUtils.json2Bean(response, GetClientResponse.class);
			String msg = fpr.getBiddata().getResq_msg();
			try {
				interfaceInfoService.save(new InterfaceInfo(applyNo, "冠E通流标接口", msg, sendDate));
			} catch (Exception e) {
				logger.error("接口信息记录失败！", e);
			}
			return msg;
		} catch (Exception e) {
			logger.error(e.toString());
			try {
				interfaceInfoService.save(new InterfaceInfo(applyNo, "冠E通流标接口", "接口访问失败:" + e.toString(), sendDate));
			} catch (Exception e1) {
				logger.error("接口信息记录失败！", e1);
			}
			return "接口访问失败！";
		}
	}

	// 满标提现
	@RequestMapping(method = RequestMethod.GET, value = "GetWithdrawSuccessBidDataRequest/{applyNo}")
	public String withdrawSuccessBidData(@PathVariable String applyNo) {
		WithdrawSuccessBidDataRequest withdrawSuccessBidDataRequest = new WithdrawSuccessBidDataRequest();
		BidWithdrawSuccessRequest bidWithdrawSuccessRequest = new BidWithdrawSuccessRequest();
		withdrawSuccessBidDataRequest = gqGetClientService.getWithdrawSuccessBidDataByApplyNo(applyNo);
		bidWithdrawSuccessRequest.setBiddata(withdrawSuccessBidDataRequest);
		Date sendDate = new Date();
		try {
			String json = JsonTransferUtils.bean2Json(bidWithdrawSuccessRequest);
			String response = OutInterfaceUtils.load(Global.getConfig("GetWithdrawSuccess"), json);
			GetClientResponse fpr = JsonTransferUtils.json2Bean(response, GetClientResponse.class);
			String msg = fpr.getBiddata().getResq_msg();
			logger.debug(msg);
			try {
				interfaceInfoService.save(new InterfaceInfo(applyNo, "冠E通满标提现接口", msg, sendDate));
			} catch (Exception e) {
				logger.error("接口信息记录失败！", e);
			}
			return msg;
		} catch (Exception e) {
			logger.error("冠E通满标提现接口访问失败:", e);
			try {
				interfaceInfoService.save(new InterfaceInfo(applyNo, "冠E通满标提现接口", "接口访问失败:" + e.toString(), sendDate));
			} catch (Exception e2) {
				logger.error("接口信息记录失败！", e2);
			}
			return "接口访问失败！";
		}
	}

	// 提前还款
	@RequestMapping(method = RequestMethod.GET, value = "GetRepaymentBidDataRequest/{applyNo}")
	public String repaymentBidData(@PathVariable String applyNo) {
		RepaymentBidDataRequest repaymentBidDataRequest = new RepaymentBidDataRequest();
		RepaymentWithdrawRequest repaymentWithdrawRequest = new RepaymentWithdrawRequest();
		repaymentBidDataRequest = gqGetClientService.getRepaymentBidDataByApplyNo(applyNo);
		repaymentWithdrawRequest.setBiddata(repaymentBidDataRequest);
		Date sendDate = new Date();
		try {
			String json = JsonTransferUtils.bean2Json(repaymentWithdrawRequest);
			String response = OutInterfaceUtils.load(Global.getConfig("GetRepayment"), json);
			GetClientResponse fpr = JsonTransferUtils.json2Bean(response, GetClientResponse.class);
			String msg = fpr.getBiddata().getResq_msg();
			try {
				interfaceInfoService.save(new InterfaceInfo(applyNo, "冠E通提前还款接口", msg, sendDate));
			} catch (Exception e) {
				logger.error("接口信息记录失败！", e);
			}
			return msg;
		} catch (Exception e) {
			logger.error(e.toString());
			try {
				interfaceInfoService.save(new InterfaceInfo(applyNo, "冠E通提前还款接口", "接口访问失败:" + e.toString(), sendDate));
			} catch (Exception e2) {
				logger.error("接口信息记录失败！", e2);
			}
			return "接口访问失败！";
		}
	}

	
	// 提前还款
	@RequestMapping(method = RequestMethod.POST)
	public String repayAdvanceGet(@PathVariable AdvanceRepayGet advanceRepayGet) {
		Biddata biddata=new Biddata();
		biddata.setContract_no(advanceRepayGet.getContractNo());
		biddata.setContract_interest(advanceRepayGet.getRemainAllInterest().toString());
		biddata.setPrincipal(advanceRepayGet.getRemainCapitalAmount().toString());
		biddata.setRepayment_day(advanceRepayGet.getRepayDate());
		biddata.setService_period_fee(advanceRepayGet.getRemainServiceFee().toString());
		String resq_msg =null;
		String resq_code =null;
		String json = null;
		try {
			BiddataOut biddataOut =new BiddataOut();
			biddataOut.setBiddata(biddata);
			json = JsonTransferUtils.bean2Json(biddataOut);
			System.out.println(json);
			String response = OutInterfaceUtils.load(Global.getConfig("AdvanceRepayGet"), json);
			BiddataInput biddataResponse = JsonTransferUtils.json2Bean(response, BiddataInput.class);
			resq_code = biddataResponse.getBiddata().getResq_code();
			resq_msg = biddataResponse.getBiddata().getResq_msg();
			try {
				interfaceInfoService.save(new InterfaceInfo(advanceRepayGet.getContractNo(), "冠E通提前还款接口(新)", resq_msg+"返回信息"+response, new Date(),"发送信息"+json));
			} catch (Exception e) {
				logger.error("接口信息记录失败！", e);
			}
		} catch (Exception e) {
			logger.error(e.toString());
			try {
				interfaceInfoService.save(new InterfaceInfo(advanceRepayGet.getContractNo(), "冠E通提前还款接口(新)", "接口访问失败:" + e.toString(), new Date(),"发送信息"+json));
			} catch (Exception e2) {
				logger.error("接口信息记录失败！", e2);
			}
		}
		return resq_code;
	}

	public Map<String, Object> underIssuingTenderData(@PathVariable String applyNo) {
		//基本信息
		BaseData baseData = gqGetClientService.getUnderBaseDataByApplyNo(applyNo,"under_dqglr");

		/* 还款计划 */
		List<BidRepayment> bidRepaymentList = new ArrayList<BidRepayment>();
		bidRepaymentList = gqGetClientService.getUnderBidPaymentByApplyNo(applyNo);
		/* 担保人信息 */
		List<CreditSurety> creditSuretyList = new ArrayList<CreditSurety>();
		creditSuretyList = gqGetClientService.getCreditSuretyByApplyNo(applyNo);
		/* 担保企业 */
		/*List<CreditCompany> creditCompanyList = new ArrayList<CreditCompany>();
		creditCompanyList = gqGetClientService.getCreditCompanyByApplyNo(applyNo);*/

		/* 抵押房屋 */
		List<CreditHouse> creditHouseList = new ArrayList<CreditHouse>();
		/* 抵押车辆 */
		List<CreditAutomobile> creditAutomobileList = new ArrayList<CreditAutomobile>();
		creditHouseList = gqGetClientService.getUnderCreditHouseByApplyNo(applyNo);
		creditAutomobileList = gqGetClientService.getUnderCreditAutomobileByApplyNo(applyNo);

		//主借人信息
		LoanCustInfo loanCustInfo = gqGetClientService.getUnderCustInfoByApplyNo(applyNo);
		//冠e通录入接口 基本信息
		CreditInfo creditInfo = gqGetClientService.getUnderCreditInfoByApplyNo(applyNo);
		//企业信息
		LoanCompany loanCompany = gqGetClientService.getUnderCompanyByApplyNo(applyNo);
		//其他
		List<CreditOther> creditOther = gqGetClientService.getUnderCreditOtherByApplyNo(applyNo);


		GetClientBidDataRequest getClientBidDataRequest = new GetClientBidDataRequest();
		getClientBidDataRequest.setCredit_house(creditHouseList);
		getClientBidDataRequest.setCredit_other(creditOther);
		getClientBidDataRequest.setCredit_automobile(creditAutomobileList);
		getClientBidDataRequest.setBase_data(baseData);
		getClientBidDataRequest.setLoan_company(loanCompany);
		getClientBidDataRequest.setBid_repayment(bidRepaymentList);
		getClientBidDataRequest.setCredit_surety(creditSuretyList);
		//getClientBidDataRequest.setCredit_company(creditCompanyList);
		//getClientBidDataRequest.setCredit_appliance(creditApplianceList);
		getClientBidDataRequest.setCredit_info(creditInfo);
		getClientBidDataRequest.setLoan_custinfo(loanCustInfo);
		IssuingTenderRequest issuingTenderRequest = new IssuingTenderRequest();
		issuingTenderRequest.setBiddata(getClientBidDataRequest);
		String json=null;
		String response=null;
		Date sendDate = new Date();
		Map<String, Object> params = Maps.newHashMap();
		try {
			json = JsonTransferUtils.bean2Json(issuingTenderRequest);
			logger.debug(json);
			response = OutInterfaceUtils.load(Global.getConfig("GetIssuingTender"), json);
			GetClientResponse fpr = JsonTransferUtils.json2Bean(response, GetClientResponse.class);
			String flag = fpr.getBiddata().getResq_code();
			String msg = fpr.getBiddata().getResq_msg();
			params.put("flag", flag);
			params.put("msg", msg);
			logger.debug(msg);

			try {
				interfaceInfoService.save(new InterfaceInfo(applyNo, "冠E通线下发标接口", response, sendDate,json));
			} catch (Exception e) {
				logger.error("接口信息记录失败！");
			}
			params.put("msg", msg);
		} catch (Exception e) {
			logger.error("冠E通接口访问失败", e);
			try {
				interfaceInfoService.save(new InterfaceInfo(applyNo, "冠E通线下发标接口", "接口访问失败:" + response +e.toString(), sendDate,json));
			} catch (Exception e2) {
				logger.error("接口信息记录失败！", e2);
			}
			params.put("flag", false);
			params.put("msg", "接口访问失败！");
		}
		return params;
	}
	
	// 标的推送，参数为applyNo
	@RequestMapping(method = RequestMethod.GET, value = "GetIssuingTenderData/{applyNo}")
	public Map<String, Object> issuingTenderData(@PathVariable String applyNo) {

		/* 还款计划 */
		List<BidRepayment> bidRepaymentList = new ArrayList<BidRepayment>();
		bidRepaymentList = gqGetClientService.getBidPaymentByApplyNo(applyNo);

		/* 担保人信息 */
		List<CreditSurety> creditSuretyList = new ArrayList<CreditSurety>();
		creditSuretyList = gqGetClientService.getCreditSuretyByApplyNo(applyNo);

		/* 担保企业 */
		List<CreditCompany> creditCompanyList = new ArrayList<CreditCompany>();
		creditCompanyList = gqGetClientService.getCreditCompanyByApplyNo(applyNo);

		/* 设备抵押信息 */
		List<CreditAppliance> creditApplianceList = new ArrayList<CreditAppliance>();
		creditApplianceList = gqGetClientService.getCreditApplianceByApplyNo(applyNo);

		/* 基本信息 */
		BaseData baseData = gqGetClientService.getBaseDataByApplyNo(applyNo, null);
		baseData.setFiliale_name(gqGetClientService.getFilialeNameByApplyNo(applyNo));
		if (baseData.getBidOfflineType() != null && baseData.getBidOfflineType() != 1) {
			baseData.setBidOfflineType(null);
		}
		/* 抵押房屋 */
		List<CreditHouse> creditHouseList = new ArrayList<CreditHouse>();

		/* 抵押车辆 */
		List<CreditAutomobile> creditAutomobileList = new ArrayList<CreditAutomobile>();

		if (baseData.getLoan_type() == 1) {
			creditHouseList = gqGetClientService.getAssetHouseByApplyNo(applyNo);

			creditAutomobileList = gqGetClientService.getAssetAutomobileByApplyNo(applyNo);
		} else {
			creditHouseList = gqGetClientService.getCreditHouseByApplyNo(applyNo);

			creditAutomobileList = gqGetClientService.getCreditAutomobileByApplyNo(applyNo);
		}

		/* 影像信息 */
		List<CreditAdjunt> creditAdjunt = new ArrayList<CreditAdjunt>();
		// creditAdjunt = gqGetClientService.getCreditAdjuntByApplyNo(applyNo);

		/* 资产类型 */
		int propertyType = 0;
		if (creditAutomobileList.size() > 0) {
			propertyType += 1;
		}
		if (creditHouseList.size() > 0) {
			propertyType += 2;
		}
		if (propertyType == 0) {
			propertyType = 99;
		}
		//冠e通录入接口 基本信息
		CreditInfo creditInfo = gqGetClientService.getCreditInfoByApplyNo(applyNo, (propertyType + ""));

		/* 主借人信息 */
        ApplyRegister applyRegisterByApplyNo = applyRegisterService.getApplyRegisterByApplyNo(applyNo);
        LoanCustInfo loanCustInfo = null;
        if("1".equals(applyRegisterByApplyNo.getCustType())){//非联合个人
            loanCustInfo = gqGetClientService.getLoanCustInfoByApplyNo(applyNo, null);
        }else{//非联合企业
            loanCustInfo = gqGetClientService.getComLoanCustInfoByApplyNo(applyNo, null);
        }


		HypothecariusCustInfo hypothecariusCustInfo = gqGetClientService.getHypothecariusCustInfoByApplyNo(applyNo, null);

		/**
		 * 企业信息
		 */
		LoanCompany loanCompany = gqGetClientService.getLoanCompanyByApplyNo(applyNo);
		if(loanCompany == null){
			loanCompany = gqGetClientService.getCustInfoAndCreditByApplyNO(applyNo);
		}
		/**
		 * 库存
		 */
		List<CreditOther> creditOther = gqGetClientService.getCreditOtherByApplyNo(applyNo);
		
		/**
		 * 判断获取推送冠e通是否有库存货物类型
		 */
		GqgetComInfo  gqgetComInfo = gqgetComInfoService.getGqgetComInfoByApplyNo(applyNo);
		if("1".endsWith(gqgetComInfo.getPropertyOther())){
			CreditOther co  = new CreditOther();
			co.setPle_type(3);
			co.setPle_desc(gqgetComInfo.getOther());
			creditOther.add(co);
		}

		GetClientBidDataRequest getClientBidDataRequest = new GetClientBidDataRequest();
		getClientBidDataRequest.setLoan_company(loanCompany);
		getClientBidDataRequest.setCredit_other(creditOther);
		getClientBidDataRequest.setBid_repayment(bidRepaymentList);
		getClientBidDataRequest.setCredit_surety(creditSuretyList);
		getClientBidDataRequest.setCredit_company(creditCompanyList);
		getClientBidDataRequest.setCredit_appliance(creditApplianceList);
		getClientBidDataRequest.setCredit_house(creditHouseList);
		getClientBidDataRequest.setCredit_automobile(creditAutomobileList);
		getClientBidDataRequest.setCredit_info(creditInfo);
		getClientBidDataRequest.setBase_data(baseData);
		getClientBidDataRequest.setLoan_custinfo(loanCustInfo);
		getClientBidDataRequest.setCredit_adjunt(creditAdjunt);
		getClientBidDataRequest.setHypothecarius_custinfo(hypothecariusCustInfo);
		/**
		 * 中间人信息
		 */
		Contract contract = creContractService.getContractByApplyNo(applyNo);
		if ("1".equals(contract.getIsOrNoMIddleMan())) {
			MiddleCustInfo middleCustInfo = gqGetClientService.getMiddleCustInfoByApplyNo(applyNo, null);
			getClientBidDataRequest.setThrid_custinfo(middleCustInfo);
		}
		IssuingTenderRequest issuingTenderRequest = new IssuingTenderRequest();
		issuingTenderRequest.setBiddata(getClientBidDataRequest);
		Date sendDate = new Date();
		Map<String, Object> params = Maps.newHashMap();
		String json = null;
		String response = "";
		try {
			json = JsonTransferUtils.bean2Json(issuingTenderRequest);
			logger.debug(json);
			response = OutInterfaceUtils.load(Global.getConfig("GetIssuingTender"), json);
			GetClientResponse fpr = JsonTransferUtils.json2Bean(response, GetClientResponse.class);
			String msg = fpr.getBiddata().getResq_msg();
			params.put("flag", fpr.getBiddata().isIs_success());
			logger.debug(msg);
			if (!fpr.getBiddata().isIs_success()) {
				gqGetClientService.failSendUpdate(applyNo,baseData.getContract_no());
			}
			try {
				interfaceInfoService.save(new InterfaceInfo(applyNo, "冠E通发标接口", response, sendDate,json));
			} catch (Exception e) {
				logger.error("接口信息记录失败！");
			}
			params.put("msg", msg);
		} catch (Exception e) {
			logger.error("冠E通接口访问失败", e);
			try {
				interfaceInfoService.save(new InterfaceInfo(applyNo, "冠E通发标接口", "接口访问失败:" + response +e.toString(), sendDate,json));
			} catch (Exception e2) {
				logger.error("接口信息记录失败！", e2);
			}
			gqGetClientService.failSendUpdate(applyNo,baseData.getContract_no());
			params.put("flag", false);
			params.put("msg", "接口访问失败！");
		}
		return params;
	}

	// 标的推送，参数为applyNo，custId
	@RequestMapping(method = RequestMethod.GET, value = "GetIssuingTenderData/{applyNo}/{approveId}")
	public Map<String, Object> issuingTenderData(@PathVariable String applyNo, @PathVariable String approveId) throws IOException {

		/* 还款计划 */
		List<BidRepayment> bidRepaymentList = new ArrayList<BidRepayment>();
		bidRepaymentList = gqGetClientService.getBidPaymentByApplyNoUnion(approveId);

		/* 担保人信息 */
		List<CreditSurety> creditSuretyList = new ArrayList<CreditSurety>();
		creditSuretyList = gqGetClientService.getCreditSuretyByApplyNoUnion(applyNo, approveId);

		/* 担保企业 */
		List<CreditCompany> creditCompanyList = new ArrayList<CreditCompany>();
		creditCompanyList = gqGetClientService.getCreditCompanyByApplyNoUnion(approveId);

		/* 设备抵押信息 */
		List<CreditAppliance> creditApplianceList = new ArrayList<CreditAppliance>();
		creditApplianceList = gqGetClientService.getCreditApplianceByApplyNoUnion(approveId);

		/* 基本信息 */
		BaseData baseData = gqGetClientService.getBaseDataByApplyNo(applyNo, approveId);
		if (baseData != null) {
			baseData.setJointcredit_key(applyNo);
			baseData.setJointcredit_count(gqGetClientService.getJointcreditCount(applyNo));
			baseData.setFiliale_name(gqGetClientService.getFilialeNameByApplyNo(applyNo));
			if (baseData.getBidOfflineType() != null && baseData.getBidOfflineType() != 1) {
				baseData.setBidOfflineType(null);
			}
		}

		/* 抵押房屋 */
		List<CreditHouse> creditHouseList = new ArrayList<CreditHouse>();

		/* 抵押车辆 */
		List<CreditAutomobile> creditAutomobileList = new ArrayList<CreditAutomobile>();

		if (baseData.getLoan_type() == 1) {
			creditHouseList = gqGetClientService.getAssetHouseByApplyNoUnion(approveId);

			creditAutomobileList = gqGetClientService.getAssetAutomobileByApplyNoUnion(approveId);
		} else {
			creditHouseList = gqGetClientService.getCreditHouseByApplyNo(applyNo);

			creditAutomobileList = gqGetClientService.getCreditAutomobileByApplyNo(applyNo);
		}

		/* 影像信息 */
		List<CreditAdjunt> creditAdjunt = new ArrayList<CreditAdjunt>();
		// creditAdjunt = gqGetClientService.getCreditAdjuntByApplyNo(applyNo);

		/* 资产类型 */
		int propertyType = 0;
		if (creditAutomobileList.size() > 0) {
			propertyType += 1;
		}
		if (creditHouseList.size() > 0) {
			propertyType += 2;
		}
		if (propertyType == 0) {
			propertyType = 99;
		}

		CreditInfo creditInfo = gqGetClientService.getCreditInfoByApplyNoUnion(approveId, (propertyType + ""));

		/* 主借人信息 */
        ApplyRegister applyRegisterByApplyNo = applyRegisterService.getApplyRegisterByApplyNo(applyNo);
        LoanCustInfo loanCustInfo = null;
        if("1".equals(applyRegisterByApplyNo.getCustType())){//联合个人
            loanCustInfo = gqGetClientService.getLoanCustInfoByApplyNoUnion(applyNo, approveId);
        }else{//联合企业
            loanCustInfo = gqGetClientService.getComLoanCustInfoByApplyNoUnion(applyNo, approveId);
        }


		// 抵押权人信息
		HypothecariusCustInfo hypothecariusCustInfo = gqGetClientService.getHypothecariusCustInfoByApplyNo(applyNo, approveId);

		/**
		 * 企业信息
		 */
		LoanCompany loanCompany = gqGetClientService.getLoanCompanyByApproId(approveId);
		if(loanCompany == null){
			loanCompany = gqGetClientService.getCustInfoAndCreditByApplyNO(applyNo);
		}
		/**
		 * 库存
		 */
		Map<String, Object> par = Maps.newHashMap();
		par.put("applyNo", applyNo);
		par.put("approveId", approveId);
		List<CreditOther> creditOther = gqGetClientService.getCreditOtherByApplyNoAndApproId(par);
		
		/**
		 * 判断获取推送冠e通是否有库存货物类型
		 */
		Map<String, String> param = Maps.newHashMap();
		param.put("applyNo", applyNo);
		param.put("approId", approveId);
		GqgetComInfoUnion gqgetComInfoUnion = gqgetComInfoUnionService.getGqgetComInfoByParam(param);
		if("1".endsWith(gqgetComInfoUnion.getPropertyOther())){
			CreditOther co  = new CreditOther();
			co.setPle_type(3);
			co.setPle_desc(gqgetComInfoUnion.getOther());
			creditOther.add(co);
		}

		GetClientBidDataRequest getClientBidDataRequest = new GetClientBidDataRequest();
		getClientBidDataRequest.setLoan_company(loanCompany);
		getClientBidDataRequest.setCredit_other(creditOther);
		getClientBidDataRequest.setBid_repayment(bidRepaymentList);
		getClientBidDataRequest.setCredit_surety(creditSuretyList);
		getClientBidDataRequest.setCredit_company(creditCompanyList);
		getClientBidDataRequest.setCredit_appliance(creditApplianceList);
		getClientBidDataRequest.setCredit_house(creditHouseList);
		getClientBidDataRequest.setCredit_automobile(creditAutomobileList);
		getClientBidDataRequest.setCredit_info(creditInfo);
		getClientBidDataRequest.setBase_data(baseData);
		getClientBidDataRequest.setLoan_custinfo(loanCustInfo);
		getClientBidDataRequest.setCredit_adjunt(creditAdjunt);
		getClientBidDataRequest.setHypothecarius_custinfo(hypothecariusCustInfo);
		/**
		 * 中间人信息
		 */
		Map<String, String> map = Maps.newHashMap();
		map.put("approveId", approveId);
		Contract contract = creContractService.getContractByApproveId(map);
		if ("1".equals(contract.getIsOrNoMIddleMan())) {
			MiddleCustInfo middleCustInfo = gqGetClientService.getMiddleCustInfoByApplyNo(applyNo, approveId);
			getClientBidDataRequest.setThrid_custinfo(middleCustInfo);
		}
		IssuingTenderRequest issuingTenderRequest = new IssuingTenderRequest();
		issuingTenderRequest.setBiddata(getClientBidDataRequest);
		Date sendDate = new Date();
		Map<String, Object> params = Maps.newHashMap();
		String json = JsonTransferUtils.bean2Json(issuingTenderRequest);
		logger.debug(json);
		String response = "";
		try {
			response = OutInterfaceUtils.load(Global.getConfig("GetIssuingTender"), json);
			GetClientResponse fpr = JsonTransferUtils.json2Bean(response, GetClientResponse.class);
			String msg = fpr.getBiddata().getResq_msg();
			params.put("flag", fpr.getBiddata().isIs_success());
			logger.debug("is_success:" + fpr.getBiddata().isIs_success() + ",resq_code:" + fpr.getBiddata().getResq_code() + ",resq_msg:" + msg);
			if (!fpr.getBiddata().isIs_success()) {
				gqGetClientService.failSendUpdate(applyNo,baseData.getContract_no());
			}
			try {
				interfaceInfoService.save(new InterfaceInfo(applyNo, "联合授信冠E通发标接口", response, sendDate, json));
			} catch (Exception e) {
				logger.error("接口信息记录失败！");
			}
			params.put("msg", msg);
		} catch (Exception e) {
			logger.error("联合授信冠E通接口访问失败", e);
			try {
				interfaceInfoService.save(new InterfaceInfo(applyNo, "联合授信冠E通发标接口", "联合授信接口访问失败:" +response+ e.toString(), sendDate, json));
			} catch (Exception e2) {
				logger.error("联合授信接口信息记录失败！", e2);
			}
			gqGetClientService.failSendUpdate(applyNo,baseData.getContract_no());
			params.put("flag", false);
			params.put("msg", "联合授信接口访问失败！");
		}
		return params;
	}
	
	//还款状态同步冠E通
	@RequestMapping(method = RequestMethod.POST, value = "SynDeductRepayStatus")
	public void repaymentSynState (List<RepaymentWithholdingList> repayList) throws IOException{
		//List<Map<String, Object>> status = new ArrayList<Map<String, Object>>();
		Date sendDate = new Date();
		Map<String,Object> param = new HashMap<String,Object>();
		String contractNo = "";
		String json = "";
		try {
			for(RepaymentWithholdingList repaymentWithdraw :repayList){
				String	deductApplyNo = repaymentWithdraw.getSerial_number();
				contractNo = repaymentWithdraw.getContract_no();
				List<RepayDetail> repayDetailList = repayDetailService.findPeriondByDeducyApplyNo(deductApplyNo);
				if(repayDetailList.size() > 0){
					RepayDetail repayDetail = repayDetailList.get(0);
						RepayPlanStatus repayPlanStatus = repayStatusSerivce.findStatusByConNoandPerNo(repayDetail.getContractNo(), repayDetail.getPeriodNum());
						if(!StringUtils.isEmpty(repayPlanStatus.getRepayPeriodStatus()) && "0400".equals(repayPlanStatus.getRepayPeriodStatus())){	
							param.put("contractNo", repayPlanStatus.getContractNo());
							param.put("period",repayPlanStatus.getPeriodNum());
						}
						
					
				}
				
			}
				json = JsonTransferUtils.bean2Json(param);
				logger.debug("发送json数据" + json);
				String response = OutInterfaceUtils.load(Global.getConfig("SY_DEDUCT_STATUS"), json);
				RepayStatusResponse fpr  = JsonTransferUtils.json2Bean(response, RepayStatusResponse.class);
			try {
				if(param.size() == 2){
				interfaceInfoService.save(new InterfaceInfo(contractNo, "同步冠E通还款状态接口", fpr.getResult(), sendDate,json));
				}
			} catch (Exception e) {
				logger.error("接口信息记录失败！", e);
				interfaceInfoService.save(new InterfaceInfo(contractNo, "同步冠E通还款状态接口", "接口访问失败:" + e.toString(), sendDate,json));
			}
		} catch (Exception e) {
			logger.error(e.toString());
			try {
				interfaceInfoService.save(new InterfaceInfo(contractNo, "同步冠E通还款状态接口", "接口访问失败:" + e.toString(), sendDate,json));
			} catch (Exception e2) {
				logger.error("接口信息记录失败！", e2);
				interfaceInfoService.save(new InterfaceInfo(contractNo, "同步冠E通还款状态接口", "接口访问失败:" + e.toString(), sendDate,json));
			}
		}
	}
}
