package com.resoft.outinterface.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.resoft.outinterface.SV.client2.WorkOrderNewField;
import com.resoft.outinterface.rest.SVFinancialToThemis.interfaces.SVClient;
import com.resoft.outinterface.rest.SVFinancialToThemis.interfacesImpl.SVClientImpl;
import com.resoft.outinterface.rest.newged.entity.*;
import com.resoft.outinterface.rest.pdf.ContractPDF;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.resoft.accounting.accContract.AccContract;
import com.resoft.accounting.advanceRepayGET.entity.AdvanceRepayGet;
import com.resoft.common.utils.JsonTransferUtils;
import com.resoft.outinterface.SV.client2.WorkOrderControllerDelegate_WorkOrderControllerPort_Client;
import com.resoft.outinterface.SV.client3.InformationMatchClient;
import com.resoft.outinterface.cn.com.experian.client.ExperianClient;
import com.resoft.outinterface.rest.GQget.client.GQGetClient;
import com.resoft.outinterface.rest.fh.client.FhRiskControlClient;
import com.resoft.outinterface.rest.financialPlatform.FinancialPlatformClient;
import com.resoft.outinterface.rest.financialPlatform.entry.RepaymentWithholdingList;
import com.resoft.outinterface.rest.ged.GedClient;
import com.resoft.outinterface.rest.ged.GedRepeatClient;
import com.resoft.outinterface.rest.ged.entity.GedApproveState;
import com.resoft.outinterface.rest.ged.entity.GedApproveStateRes;
import com.resoft.outinterface.rest.newged.entity.*;
import com.resoft.outinterface.rest.pdf.ContractPDF;
import com.resoft.outinterface.rest.sendGET.entry.SendGETRequest;
import com.resoft.outinterface.rest.sendGET.entry.SendGETResponse;
import com.resoft.outinterface.themis.ThemisServerInterfaceImpl;
import com.resoft.outinterface.themis.entry.response.ThemisResponse;

public enum Facade {

	facade;
	private XmlUtilsInterface lmd = new XMlUtilsImpl();
	private DispatchInterface dif = new Dispatcher();
	private ThemisServerInterfaceImpl tsii = new ThemisServerInterfaceImpl();
	private WorkOrderControllerDelegate_WorkOrderControllerPort_Client wocp = new WorkOrderControllerDelegate_WorkOrderControllerPort_Client();
	private SVClient svClient=new SVClientImpl();
	private ExperianClient ec = new ExperianClient();
	private FinancialPlatformClient fpc = new FinancialPlatformClient();
	private GQGetClient gqgc = new GQGetClient();
	private GedClient ged = new GedClient();
	private ContractPDF contractPDF = new ContractPDF();
	private FhRiskControlClient fh = new FhRiskControlClient();
	private InformationMatchClient infor = new InformationMatchClient();
	private GedRepeatClient gedRepeatClient = new GedRepeatClient();
	private static final Logger logger = LoggerFactory.getLogger(FinancialPlatformClient.class);

	public String formatXml(String str) {
		try {
			return lmd.formatXml(str);
		} catch (Exception e) {
			logger.error("生成xml失败", e);
		}
		return null;
	}

	public <T> T XMLStringToBean(String Xml, Class<T> objClass) throws Exception {
		return lmd.XMLStringToBean(Xml, objClass);
	}

	public <T> String BeanToXmlString(Object obj, Class<T> objClass) throws Exception {
		return lmd.BeanToXmlString(obj, objClass);
	}

	public <T> T getDispatchedInterface(int interfaceName, Class<T> objClass, String applyNo, String xml) throws Exception {
		return dif.getDispatchedInterface(interfaceName, objClass, applyNo, xml);
	}


	/**
	 * 外访新增字段接口
	 */
	public String SVAddField(WorkOrderNewField workOrderNewField) {
		logger.info("外访新增字段接口访问 -----------start");
		String flag=null;
		try {
			flag = svClient.SVAddFieldInterface(workOrderNewField);
		} catch (Exception e) {
			logger.error("外访新增字段接口访问", e);
		}
		logger.info("外访新增字段接口访问 -----------end");
		return flag;
	}

	/**
	 * 外访接口
	 */
	public void SVServiceMethod(String applyNo) {
		logger.info("外访接口访问 -----------start");
		try {
			wocp.main(applyNo);
		} catch (Exception e) {
			logger.error("外访接口访问失败", e);
		}
		logger.info("外访接口访问 -----------end");
	}

	/**
	 * 字段对接接口
	 */
	public void getDataToSVForInformationMatch(Object app) {
		logger.info("字段对接接口访问 -----------start");
		try {
			String applyNo = app.toString();
			infor.getDataToSVForInformationMatch(applyNo);
		} catch (Exception e) {
			logger.error("外访接口访问失败", e);
		}
		logger.info("字段对接接口访问 -----------end");
	}

	/**
	 * Themis
	 */
	public ThemisResponse getAnalysisResult(String applyNo) {
		ThemisResponse xml = null;
		String resp = "";
		logger.info("Themis接口访问 -----------start");
		try {
			resp = tsii.getAnalysisResult(applyNo);
			xml = Facade.facade.XMLStringToBean(resp, ThemisResponse.class);
		} catch (Exception e) {
			logger.error("Themis接口访问失败", e);
		}
		logger.info("Themis接口访问 -----------end");
		return xml;
	}

	/**
	 * 益博瑞
	 */
	public void getExperianClient(String applyNo) {
		logger.info("Experian接口访问 -----------start");
		logger.info("Experian之ApplyNo:" + applyNo);
		try {
			ec.getExperianClient(applyNo);
		} catch (Exception e) {
			logger.error("益博睿访问失败：" + e);
		}
		logger.info("Experian接口访问 -----------end");
	}

	/**
	 * @author caoyinglong 流标
	 */
	public void abortiveTender(String applyNo) {
		logger.info("资金平台流标接口访问 -----------start");
		try {
			fpc.abortiveTender(applyNo);
		} catch (Exception e) {
			logger.error("资金平台流标接口访问失败：" + e);
		}
		logger.info("资金平台流标接口访问 -----------end");
	}

	public void GetAbortiveTender(String applyNo) {
		logger.info("冠E通流标接口访问 -----------start");
		try {
			gqgc.abortiveTender(applyNo);
		} catch (Exception e) {
			logger.error("冠E通流标接口访问失败：" + e);
		}
		logger.info("冠E通流标接口访问 -----------end");
	}

	/**
	 * @author caoyinglong 银行卡变更
	 */
	public void bankCardChange(String applyNo) {
		logger.info("资金平台银行卡变更接口访问 -----------start");
		try {
			fpc.bankCardChange(applyNo);
		} catch (Exception e) {
			logger.error("资金平台银行卡变更接口访问失败：" + e);
		}
		logger.info("资金平台银行卡变更接口访问 -----------end");
	}

	/**
	 * @author caoyinglong 借款人开户
	 */
	public Map<String, String> borrowerCreateAccount(String applyNo) {
		logger.info("资金平台借款人开户接口访问 -----------start");
		Map<String, String> msg = null;
		try {
			msg = fpc.borrowerCreateAccount(applyNo);
		} catch (Exception e) {
			logger.error("资金平台借款人开户接口访问失败：" + e);
		}
		logger.info("资金平台借款人开户接口访问 -----------end");
		return msg;
	}

	/**
	 * @author caoyinglong 借款人提现
	 */
	public void borrowerDeposit(String applyNo) {
		logger.info("资金平台借款人提现接口访问 -----------start");
		try {
			fpc.borrowerDeposit(applyNo);
		} catch (Exception e) {
			logger.error("资金平台借款人提现接口访问失败：" + e);
		}
		logger.info("资金平台借款人提现接口访问 -----------end");
	}

	/**
	 * @author caoyinglong 借款人放款
	 */
	public void borrowerLoan(String applyNo) {
		logger.info("资金平台借款人放款接口访问 -----------start");
		try {
			fpc.borrowerLoan(applyNo);
		} catch (Exception e) {
			logger.error("资金平台借款人放款接口访问失败：" + e);
		}
		logger.info("资金平台借款人放款接口访问 -----------end");
	}

	/**
	 * @author caoyinglong 抵押权人开户
	 */
	public void mortgageeCreateAccount(String idNum) {
		logger.info("资金平台抵押权人开户接口访问 -----------start");
		try {
			fpc.mortgageeCreateAccount(idNum);
		} catch (Exception e) {
			logger.error("资金平台抵押权人开户接口访问失败：" + e);
		}
		logger.info("资金平台抵押权人开户接口访问 -----------end");
	}

	/**
	 * @author caoyinglong 抵押权人提现
	 */
	public void mortgageeDeposit(String applyNo) {
		logger.info("资金平台抵押权人提现接口访问 -----------start");
		try {
			fpc.mortgageeDeposit(applyNo);
		} catch (Exception e) {
			logger.error("资金平台抵押权人提现接口访问失败：" + e);
		}
		logger.info("资金平台抵押权人提现接口访问 -----------end");
	}

	/**
	 * @author caoyinglong 保证金退还
	 */
	public void refundDeposit(String applyNo) {
		logger.info("资金平台保证金退还接口访问 -----------start");
		try {
			fpc.refundDeposit(applyNo);
		} catch (Exception e) {
			logger.error("资金平台保证金退还接口访问失败：" + e);
		}
		logger.info("资金平台保证金退还接口访问 -----------end");
	}

	/**
	 * @author caoyinglong 发标
	 */
	public Map<String, Object> issuingTenderData(String applyNo) {
		Map<String, Object> msg = Maps.newHashMap();
		logger.info("冠E通发标接口访问 -----------start");
		try {
			msg = gqgc.issuingTenderData(applyNo);
		} catch (Exception e) {
			logger.error("冠E通发标接口访问失败：" + e);
		}
		logger.info("冠E通发标接口访问 -----------end");
		return msg;
	}

	/**
	 * @author caoyinglong 发标
	 */
	public Map<String, Object> underIssuingTenderData(String applyNo) {
		Map<String, Object> msg = Maps.newHashMap();
		logger.info("冠E通线下发标接口访问 -----------start");
		try {
			msg = gqgc.underIssuingTenderData(applyNo);
		} catch (Exception e) {
			logger.error("冠E通线下发标接口访问失败：" + e);
		}
		logger.info("冠E通线下发标接口访问 -----------end");
		return msg;
	}

	public Map<String, Object> issuingTenderData(String applyNo, String approveId) {
		Map<String, Object> msg = Maps.newHashMap();
		logger.info("联合授信冠E通发标接口访问 -----------start");
		try {
			msg = gqgc.issuingTenderData(applyNo, approveId);
		} catch (Exception e) {
			logger.error("联合授信冠E通发标接口访问失败：" + e);
		}
		logger.info("联合授信冠E通发标接口访问 -----------end");
		return msg;
	}

	/**
	 * @author caoyinglong 提前还款
	 */
	public void repaymentBidData(String applyNo) {
		logger.info("冠E通提前还款接口访问 -----------start");
		try {
			gqgc.repaymentBidData(applyNo);
		} catch (Exception e) {
			logger.error("冠E通提前还款接口访问失败：" + e);
		}
		logger.info("冠E通提前还款接口访问 -----------end");
	}
	/**
	 *  冠E通提前还款(新)
	 */
	public String repayAdvanceGet(AdvanceRepayGet advanceRepayGet) {
		logger.info("冠E通提前还款接口访问 -----------start");
		String resq_code=null;
		try {
			resq_code=gqgc.repayAdvanceGet(advanceRepayGet);
		} catch (Exception e) {
			logger.error("冠E通提前还款接口访问失败：" + e);
		}
		logger.info("冠E通提前还款接口访问 -----------end");
		return resq_code;
	}
	
	

	/**
	 * @author caoyinglong 满标提现
	 */
	public void withdrawSuccessBidData(String applyNo) {
		logger.info("冠E通满标提现接口访问 -----------start");
		try {
			gqgc.withdrawSuccessBidData(applyNo);
		} catch (Exception e) {
			logger.error("冠E通满标提现接口访问失败：" + e);
		}
		logger.info("冠E通满标提现接口访问 -----------end");
	}

	/**
	 * 冠易贷审批状态接口
	 * 
	 * @param
	 */
	public GedApproveStateRes approveStates(GedApproveState gs) {
		logger.info("冠易贷审批状态接口访问 -----------start");
		GedApproveStateRes gsr = null;
		try {
			String response = ged.approveState(gs);
			gsr = JsonTransferUtils.json2Bean(response, GedApproveStateRes.class);
		} catch (Exception e) { 
			logger.error("冠易贷审批状态接口访问失败：" + e);
		}
		logger.info("冠易贷审批状态接口访问 -----------end");
		return gsr;
	}

	/**
	 * 法海风控预查接口
	 * 
	 */
	public Map<String, Object> fhRiskControl(String authCode, String applyNo, String custId, String custName, String idCard) {
		logger.info("法海风控预查接口访问 -----------start");
		Map<String, Object> map = Maps.newHashMap();
		try {
			map = fh.fhRiskControl(authCode, applyNo, custId, custName, idCard);
		} catch (Exception e) {
			logger.error("法海风控预查接口访问失败：" + e);
		}
		logger.info("法海风控预查接口访问 -----------end");
		return map;
	}

	/**
	 * 法海风控生成报告接口
	 * 
	 */
	public Map<String, Object> fhRiskControlReport(String authCode, String applyNo, String custId, String custName, String idCard) {
		logger.info("法海风控生成报告接口访问 -----------start");
		Map<String, Object> map = Maps.newHashMap();
		try {
			map = fh.fhRiskControlReport(authCode, applyNo, custId, custName, idCard);
		} catch (Exception e) {
			logger.error("法海风控生成报告接口访问失败：" + e);
		}
		logger.info("法海风控生成报告接口访问 -----------end");
		return map;
	}
	
	/**
	 * 
	 * @param
	 */
	public void repaymentSynState(List<RepaymentWithholdingList> repayList) {
		logger.info("还款状态同步冠E通接口访问 -----------start");
		try {
			gqgc.repaymentSynState(repayList);
		} catch (Exception e) {
			logger.error("冠E通提前还款接口访问失败：" + e);
		}
		logger.info("还款状态同步冠E通接口访问 -----------end");
	}
	
	public void gedPushUpdateRepaymentPlan(Map<String,Object> map,String applyNo){
		logger.info("冠易贷还款计划推送接口 -----------start");
		try {
			ged.pushUpdateRepaymentPlan(applyNo,map);
		} catch (Exception e) {
			logger.error("冠易贷还款计划推送接口失败：" + e);
		}
		logger.info("冠易贷还款计划推送接口访问结束-----------end");
	}
	
	public void gedOrderStateFeedBack(String applyNo,String applyIdChild, Integer status,AccContract contract){
		logger.info("冠易贷订单状态反馈接口访问 -----------start");
		try {
			ged.orderStateFeedBack(applyNo,applyIdChild, status,contract);
		} catch (Exception e) {
			logger.error("冠易贷订单状态反馈接口访问失败：" + e);
		}
		logger.info("冠易贷订单状态反馈接口访问 -----------end");
	}

	public GedRegisterResponse registerGEDAccountInterface(GedRegisterRequest gedRegisterRequest,String applyNo){
		logger.info("冠易贷注册接口访问 -----------start");
		GedRegisterResponse registerGEDAccountInterface=null;
		try {
			registerGEDAccountInterface = ged.registerGEDAccountInterface(gedRegisterRequest, applyNo);
		} catch (Exception e) {
			logger.error("冠易贷注册接口访问失败：" + e);
		}
		logger.info("冠易贷注册接口访问 -----------end");
		return registerGEDAccountInterface;
	}

    //冠易贷推送订单
	public GedApiReturn PushGedApiXinxi(String json,String contractNo){
		logger.info("冠易贷推送订单接口访问 -----------start");
		GedApiReturn gedApiReturn=null;
		try {
			  gedApiReturn = ged.PushGedApiXinxi(json,contractNo);
		} catch (Exception e) {
			logger.error("冠易贷推送订单接口访问失败：" + e);
		}
		logger.info("冠易贷推送订单接口访问 -----------end");
		return gedApiReturn;
	}
	//冠易贷处理线下订单任务
	 public   void    savePushUnderInfo(String json){
         logger.info("线下推送数据开始");
		 try {
			 ged.savePushUnderInfo(json);
		 } catch (Exception e) {
			 logger.error("冠易贷推送订单接口访问失败：" + e);
		 }
		 logger.info("冠易贷推送订单接口访问 -----------end");


	 }
	public FindIsRegisterResponse findGedRegisterInterface(FindIsRegisterRequest findIsRegister,String applyNo){
		logger.info("冠易贷查询注册接口访问 -----------start");
		FindIsRegisterResponse findGedRegisterInterface=null;
		try {
			findGedRegisterInterface = ged.findGedRegisterInterface(findIsRegister, applyNo);
		} catch (Exception e) {
			logger.error("冠易贷查询注册接口访问失败：" + e);
		}
		logger.info("冠易贷查询注册接口访问 -----------end");
		return findGedRegisterInterface;
	}
	
	/*
	 * 借款系统推送冠E贷新增
	 * */
	public AddOrderResponse addOrderToGEDInterface(AddOrderRequestList addOrderRequest,String applyNoOrContractNo) {
		logger.info("新工单推送GED接口访问 -----------start");
		AddOrderResponse addOrderResponse=null;
		try {
			addOrderResponse = ged.addOrderToGEDInterface(addOrderRequest,applyNoOrContractNo);
		} catch (Exception e) {
			logger.error("新工单推送GED接口访问失败：" + e);
		}
		logger.info("新工单推送GED接口访问 -----------end");
		return addOrderResponse;
	}
	
	/*
	 * 借款系统推送冠E贷担保关系
	 * */
	public GedRegisterResponse gedPushGuaranteeInterface(List<GedPushGuaranteeRequest> gedPushGuaranteeList,String applyNoOrContractNo) {
		logger.info("推送冠E贷担保关系接口访问 -----------start");
		GedRegisterResponse gedPushGuaranteeResponse=null;
		try {
			gedPushGuaranteeResponse = ged.gedPushGuaranteeInterface(gedPushGuaranteeList,applyNoOrContractNo);
		} catch (Exception e) {
			logger.error("推送冠E贷担保关系接口访问失败：" + e);
		}
		logger.info("推送冠E贷担保关系接口访问 -----------end");
		return gedPushGuaranteeResponse;
	}
	/*
	 * 足额通知冠E通
	 * */
	public SendGETResponse SendGET(SendGETRequest sendGETRequest,String flag) {
		SendGETResponse sendGETResponse=null;
		logger.info("通知冠E通访问 -----------start");
		try {
			sendGETResponse = fpc.sendGET(sendGETRequest,flag);
		} catch (Exception e) {
			logger.error("通知冠E通访问失败：" + e);
		}
		logger.info("通知冠E通访问访问 -----------end");
		return sendGETResponse;
	}
	/**
	 * 二次提现操作
	 * @param applyNo
	 */
	public AddOrderResponse pushGedLoanBlance(Map<String,String> param){
		logger.info("冠E贷解冻剩余借款GED接口访问 -----------start");
		AddOrderResponse addOrderResponse=null;
		try {
			addOrderResponse = ged.pushGedLoanBlance(param);
		} catch (Exception e) {
			logger.error("GED接口访问失败：" + e);
		}
		logger.info("冠E贷解冻剩余借款GED接口访问 -----------end");
		return addOrderResponse;
	}
	
	
	/**
	 * 冠E贷放回最后一期已还完状态
	 * @param contractNo
	 * @param custId
	 * @return
	 */
	public AddOrderResponse  sendGuanEDaiPeriodStatus(Map<String,Object> param) {
		 logger.info("冠E通最后一期状态接口访问 -----------start");
		 AddOrderResponse gedResponse = new AddOrderResponse();
		 try {
			 gedResponse = ged.sendGuanEDaiPeriodStatus(param);
		} catch (Exception e) {
			logger.error("冠E通最后一期状态接口访访问失败",e);
		}
		 logger.info("冠E通最后一期状态接口访问 -----------end");
		return gedResponse;
	}
	
	
	/**
	 * 推送冠E贷
	 * @param oldCompensatoryAccountList
	 * @return
	 */
	public AddOrderResponse  sendOrderNoGET(String orderNo) {
		 logger.info("存储执行完调用冠E贷接口访问 -----------start");
		 AddOrderResponse addOrderResponse = new AddOrderResponse();
		 try {
			 addOrderResponse = ged.sendOrderNoGET(orderNo);
		} catch (Exception e) {
			logger.error("存储执行完调用冠E贷接口访访问失败",e);
		}
		 logger.info("存储执行完调用冠E贷接口访问 -----------end");
		return addOrderResponse;
	}
	
	
	/**
	 * @author caoyinglong 冠E贷推送信审信息非联合授信
	 */
	public AddOrderResponse sendGuanEDCreditInfo(String applyNo) {
		AddOrderResponse addOrderResponse = new AddOrderResponse();
		logger.info("冠E贷推送信审信息接口访问 -----------start");
		try {
			addOrderResponse = ged.sendGuanEDCreditInfo(applyNo);
		} catch (Exception e) {
			logger.error("冠E贷推送信审信息访问失败：" + e);
		}
		logger.info("冠E贷推送信审信息接口访问 -----------end");
		return addOrderResponse;
	}
	
	/**
	 * 联合授信推送冠E贷信审信息
	 * @param applyNo
	 * @param approveId
	 * @return
	 */
	public AddOrderResponse sendGuanEDCreditUnionInfo(String applyNo, String approveId) {
		AddOrderResponse addOrderResponse = new AddOrderResponse();
		logger.info("联合授信冠E贷发信审信息接口访问 -----------start");
		try {
			addOrderResponse = ged.sendGuanEDCreditUnionInfo(applyNo,approveId);
		} catch (Exception e) {
			logger.error("联合授信冠E贷发信审信息接口访问失败：" + e);
		}
		logger.info("联合授信冠E通发标接口访问 -----------end");
		return addOrderResponse;
	}
	
	public void finishOrderPushGed(String applyNo,Integer status,String flag){
		logger.info("流程结束推送冠易贷订单状态反馈接口访问 -----------start");
		try {
			ged.finishOrderPushGed(applyNo,status,flag);
		} catch (Exception e) {
			logger.error("流程结束推送冠易贷订单状态反馈接口访问失败：" + e);
		}
		logger.info("流程结束推送冠易贷订单状态反馈接口访问 -----------end");
	}
	
	/*
	 * GED推送合同号
	 * */
	public AddOrderResponse sendConytractRelation(OrderContractReqFormLists orderContractReqFormLists) {
		AddOrderResponse addOrderResponse = new AddOrderResponse();
		logger.info("GED推送合同号接口访问 -----------start");
		try {
			addOrderResponse = ged.sendConytractRelation(orderContractReqFormLists);
		} catch (Exception e) {
			logger.error("GED推送合同号接口访问失败：" + e);
		}
		logger.info("GED推送合同号接口访问 -----------end");
		return addOrderResponse;
	}

	
	public ContractPDFAllResponse getContractPDF(ContractPDFRequest contractPDFRequest,String contract) {
		ContractPDFAllResponse contractPDFAllResponse = new ContractPDFAllResponse();
		logger.info("获取合同相关PDF接口访问 -----------start");
		try {
			contractPDFAllResponse = contractPDF.getContractPDF(contractPDFRequest,contract);
		} catch (Exception e) {
			logger.error("获取合同相关PDF接口访问失败：" + e);
		}
		logger.info("获取合同相关PDF接口访问 -----------end");
		return contractPDFAllResponse;
	}
	
	public HashMap<String , Object> getContractFacePDF(ContractPDFRequest contractPDFRequest,String contract) {
		HashMap<String , Object> hashMap = new HashMap<String , Object>();
		logger.info("获取合同PDF接口访问 -----------start");
		try {
			hashMap = contractPDF.getContractFacePDF(contractPDFRequest,contract);
		} catch (Exception e) {
			logger.error("获取合同PDF接口访问失败：" + e);
		}
		logger.info("获取合同PDF接口访问 -----------end");
		return hashMap;
	}

	public void repeatAccount(String socialCreditCode,String reason,String status,String userId){
		logger.info("企业开户退回推给冠E贷状态接口-----------start");
		try {
			gedRepeatClient.repeatAccount(socialCreditCode,reason,status,userId);
		} catch (Exception e) {
			logger.error("企业开户退回推给冠E贷状态接口失败：" + e);
		}
		logger.info("企业开户退回推给冠E贷状态接口 -----------end");
	}
}
