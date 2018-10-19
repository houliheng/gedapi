package com.resoft.outinterface.rest.ged;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.resoft.outinterface.rest.newged.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;
import com.resoft.accounting.accContract.AccContract;
import com.resoft.common.utils.JsonTransferUtils;
import com.resoft.credit.interfaceinfo.entity.InterfaceInfo;
import com.resoft.credit.interfaceinfo.service.InterfaceInfoService;
import com.resoft.outinterface.rest.ged.entity.request.CreditInfoToRequest;
import com.resoft.outinterface.rest.ged.entity.GedApproveState;
import com.resoft.outinterface.rest.ged.entity.GqgetAssetCarInfo;
import com.resoft.outinterface.rest.ged.entity.GqgetAssetHouseInfo;
import com.resoft.outinterface.rest.ged.entity.GuanETInfo;
import com.resoft.outinterface.rest.ged.service.GedClientService;
import com.resoft.outinterface.utils.OutInterfaceUtils;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.JsonUtil;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

@RestController
@RequestMapping(value = "/f/rest/ged/service")
public class GedClient {

	private static final Logger logger = LoggerFactory.getLogger(GedClient.class);

	private InterfaceInfoService interfaceInfoService = SpringContextHolder.getBean("interfaceInfoService");
	private GedClientService gedClientService = SpringContextHolder.getBean("gedClientService");
	@RequestMapping(method = RequestMethod.POST, value = "approveState")
	public String approveState(GedApproveState gs) throws Exception {
		String response = null;
		Date sendDate = new Date();
		String json = "&custName="+gs.getCustName()+"&applyId="+gs.getApplyId()+"&approveState="+gs.getApproveState()+"&changeDate="+gs.getChangeDate();
		logger.info("发送信息："+json);
		try {
			response = OutInterfaceUtils.sendPost(Global.getConfig("approveState"), json);
			try {
				interfaceInfoService.save(new InterfaceInfo(gs.getApplyId(), "冠易贷审批状态接口", "成功！", sendDate, response));
			} catch (Exception e) {
				logger.error("接口信息记录失败！",e);
			}
		} catch (Exception e) {
			try {
				interfaceInfoService.save(new InterfaceInfo(gs.getApplyId(), "冠易贷审批状态接口", "失败！"+e.toString(), sendDate, response));
			} catch (Exception e1) {
				logger.error("接口信息记录失败！",e1);
			}
		}
		logger.info("返回信息："+response);
		return response;
	}
	
	//冠易贷注册接口
	public GedRegisterResponse registerGEDAccountInterface(GedRegisterRequest gedRegisterRequest,String applyNo){
		String json=null;
		String responseInfo=null;
		GedRegisterResponse gedRegisterResponse=null;
		try {
			json = JsonTransferUtils.bean2Json(gedRegisterRequest);
			System.out.println(json);
			responseInfo = OutInterfaceUtils.load(Global.getConfig("GedRegister"), json);
			gedRegisterResponse = JsonTransferUtils.json2Bean(responseInfo, GedRegisterResponse.class);
			interfaceInfoService.save(new InterfaceInfo( applyNo, "冠易贷注册接口","返回信息:"+responseInfo, new Date(),"发送信息:"+json));
		} catch (Exception e) {//接口异常
			e.printStackTrace();
			interfaceInfoService.save(new InterfaceInfo( applyNo, "冠易贷注册接口","异常信息"+e.toString()+"返回信息:"+responseInfo, new Date(),"发送信息:"+json));
		}
		return gedRegisterResponse;
	}

    //推送订单
	public GedApiReturn PushGedApiXinxi(String json,String contractNo ){

		GedApiReturn gedApiReturn = null;
		String responseInfo = null;
		try{
			System.out.println(json);
			responseInfo = OutInterfaceUtils.load(Global.getConfig("saveGedApi"),json);
			gedApiReturn = JsonTransferUtils.json2Bean(responseInfo, GedApiReturn.class);

			interfaceInfoService.save(new InterfaceInfo( contractNo, "冠易贷推送订单","返回信息:"+responseInfo, new Date(),"发送信息:"+json));
		}catch (Exception e){
			e.printStackTrace();
			interfaceInfoService.save(new InterfaceInfo( contractNo, "冠易贷推送订单","异常信息"+e.toString()+"返回信息:"+responseInfo, new Date(),"发送信息:"+json));

		}

        return gedApiReturn;
	}
	//线下数据推送
	public void savePushUnderInfo(String json){
        try {

			  OutInterfaceUtils.load(Global.getConfig("savePushUnderInfo"),json);
			interfaceInfoService.save(new InterfaceInfo( "线下数据推送", "线下数据推送","返回信息:此接口没有返回值", new Date(),"发送信息:"+json));
		}catch (Exception e){
        	e.printStackTrace();
			interfaceInfoService.save(new InterfaceInfo( "线下数据推送", "线下数据推送","返回信息:此接口没有返回值", new Date(),"发送信息:"+json));
		}
}

	//冠易贷查询是否已经注册接口
	public FindIsRegisterResponse findGedRegisterInterface(FindIsRegisterRequest findIsRegister,String applyNo){
		String json=null;
		String responseInfo=null;
		FindIsRegisterResponse gedIsRegisterResponse=null;
		try {
			json = JsonTransferUtils.bean2Json(findIsRegister);
			System.out.println(json);
			responseInfo = OutInterfaceUtils.load(Global.getConfig("CheckGedRegister"), json);
			gedIsRegisterResponse = JsonTransferUtils.json2Bean(responseInfo, FindIsRegisterResponse.class);
			interfaceInfoService.save(new InterfaceInfo( applyNo, "冠易贷查询注册接口","返回信息:"+responseInfo, new Date(),"发送信息:"+json));
		} catch (Exception e) {//接口异常
			e.printStackTrace();
			interfaceInfoService.save(new InterfaceInfo( applyNo, "冠易贷查询注册接口","异常信息"+e.toString()+"返回信息:"+responseInfo, new Date(),"发送信息:"+json));
		}
		return gedIsRegisterResponse;
	}

	public AddOrderResponse addOrderToGEDInterface(AddOrderRequestList addOrderRequest,String applyNoOrContractNo) {
		String json=null;
		String responseInfo=null;
		AddOrderResponse addOrderResponse=null;
		try {
			json = JsonTransferUtils.bean2Json(addOrderRequest);
			System.out.println(json);
			responseInfo = OutInterfaceUtils.load(Global.getConfig("AddOrderToGED"), json);
			addOrderResponse = JsonTransferUtils.json2Bean(responseInfo, AddOrderResponse.class);
			interfaceInfoService.save(new InterfaceInfo(applyNoOrContractNo, "申请单进件接口","返回信息:"+responseInfo, new Date(),"发送信息:"+json));
		} catch (Exception e) {//接口异常
			e.printStackTrace();
			interfaceInfoService.save(new InterfaceInfo( applyNoOrContractNo, "申请单进件接口","异常信息"+e.toString()+"返回信息:"+responseInfo, new Date(),"发送信息:"+json));
		}
		return addOrderResponse;
	}
	
	public GedRegisterResponse gedPushGuaranteeInterface(List<GedPushGuaranteeRequest> gedPushGuaranteeList,String applyNoOrContractNo) {
		String json=null;
		String responseInfo=null;
		GedRegisterResponse gedPushGuaranteeResponse=null;
		GedPushGuaranteeRequestList list = new GedPushGuaranteeRequestList();
		list.setList(gedPushGuaranteeList);
		try {
			json = JsonTransferUtils.bean2Json(list);
			System.out.println(json);
			responseInfo = OutInterfaceUtils.load(Global.getConfig("GetPushGuarantee"), json);
			gedPushGuaranteeResponse = JsonTransferUtils.json2Bean(responseInfo, GedRegisterResponse.class);
			interfaceInfoService.save(new InterfaceInfo(applyNoOrContractNo, "Ged推送担保关系","返回信息:"+responseInfo, new Date(),"发送信息:"+json));
		} catch (Exception e) {//接口异常
			e.printStackTrace();
			interfaceInfoService.save(new InterfaceInfo( applyNoOrContractNo, "Ged推送担保关系","异常信息"+e.toString()+"返回信息:"+responseInfo, new Date(),"发送信息:"+json));
		}
		return gedPushGuaranteeResponse;
	}
	
	
	public static final Integer ged_zlwsz = 100;  	//资料完善中
	public static final Integer ged_shz = 110;  	//审核中 
	public static final Integer ged_shtg = 120;  	//审核通过 
	public static final Integer ged_cbz = 130;  	//筹标中 
	public static final Integer ged_fkcg = 139;  	//放款成功 
	public static final Integer ged_yctxz = 140;  	//一次提现中 
	public static final Integer ged_ectxz = 150;  	//二次提现中 
	public static final Integer ged_hkz = 160;  	//还款中 
	public static final Integer ged_yyq = 170;  	//已逾期 
	public static final Integer ged_yhq = 180;  	//已还清 
	public static final Integer ged_yqyh = 190;  	//逾期已还 
	public static final Integer ged_sbjj = 200; 	//审批拒绝
	public static final Integer ged_lb = 210;  		//流标 
	public static final Integer ged_fqsq = 220; 	//放弃申请

	/**
	 * 订单状态反馈接口
	 * @param applyNo	申请编号/订单号（冠易贷名称）
	 * @param status	
	 */
	public void orderStateFeedBack(String applyNo, String applyIdChild,Integer status,AccContract contract){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("orderNo", applyNo);
		params.put("status", status);
		String json=null;
		String responseInfo=null;
		if(StringUtils.isNotEmpty(applyIdChild)) {
			params.put("applyIdChild", applyIdChild);
		}
		if(contract != null){
			BigDecimal servicceFee = contract.getServiceFee();
			BigDecimal sepciaService = contract.getSpecialServiceFee();
			//contract.setServiceFee(contract.getSpecialServiceFee()==null?null:contract.getSpecialServiceFee());
			//contract.setSpecialServiceFee(contract.getSpecialServiceFee()==null?null:contract.getSpecialServiceFee());
			params.put("serviceFee",servicceFee.add(sepciaService).toString());
			params.put("guaranteeFee", contract.getMarginAmount());
			params.put("cashDeposit", contract.getMarginAmount());
			params.put("serviceProvinceId", contract.getLoanPlatform());
			params.put("repaymentStyle", DictUtils.getDictLabel(contract.getApproLoanRepayType(), "LOAN_REPAY_TYPE", "无"));
			params.put("contractNo", contract.getContractNo());
			params.put("loanAmount", contract.getLoanAmount());
			params.put("contractAmount", contract.getContractAmount());
			params.put("loanTerm", contract.getApproPeriodValue());
			params.put("service_fee_way",contract.getServiceFeeType()==null?null:Integer.parseInt(contract.getServiceFeeType()));
		}
		try {
			json = JsonUtil.getJSONString(params);
			responseInfo = OutInterfaceUtils.load(Global.getConfig("gedUrl")+Global.getConfig("OrderStateFeedBack"), json);
			interfaceInfoService.save(new InterfaceInfo(applyNo, "订单状态反馈接口","返回信息:"+responseInfo, new Date(),"发送信息:"+json));
		} catch (Exception e) {
			e.printStackTrace();
			interfaceInfoService.save(new InterfaceInfo( applyNo, "订单状态反馈接口","异常信息"+e.toString()+"返回信息:"+responseInfo, new Date(),"发送信息:"+json));
		}
	}
	
	/**
	 * 还款计划推送
	 * @param applyNo
	 * @param map
	 */
	public void pushUpdateRepaymentPlan(String applyNo,Map<String,Object> map){
		String jsonStr = null;
		String responseInfo = null;
		try {
			jsonStr = JsonUtil.getJSONString(map);
			responseInfo = OutInterfaceUtils.load(Global.getConfig("gedUrl")+Global.getConfig("PushRepaymentPlan"), jsonStr);
			interfaceInfoService.save(new InterfaceInfo(applyNo, "还款计划推送接口","返回信息:"+responseInfo, new Date(),"发送信息:"+jsonStr));
		} catch (Exception e) {
			e.printStackTrace();
			interfaceInfoService.save(new InterfaceInfo( applyNo, "还款计划推送接口","异常信息"+e.toString()+"返回信息:"+responseInfo, new Date(),"发送信息:"+jsonStr));
		}
	}
	
	/**
	 * 二次提现解冻操作
	 * @param
	 * @return
	 */
	public AddOrderResponse pushGedLoanBlance(Map<String,String> param){
		String json=null;
		String responseInfo=null;
		AddOrderResponse addOrderResponse=null;
		try {
			//params.put("factGuaranteeFee", factGuaranteeFee);
			//params.put("factGuaranteeGold",factGuaranteeGold);
			json = JsonTransferUtils.bean2Json(param);
			System.out.println(json);
			responseInfo = OutInterfaceUtils.load(Global.getConfig("GuanEDServiceFee"), json);
			addOrderResponse = JsonTransferUtils.json2Bean(responseInfo, AddOrderResponse.class);
			interfaceInfoService.save(new InterfaceInfo(param.get("contractNo"), "推送冠E贷解冻余额信息接口","返回信息:"+responseInfo, new Date(),"发送信息:"+json));
		} catch (Exception e) {
			e.printStackTrace();
			interfaceInfoService.save(new InterfaceInfo(param.get("contractNo"), "推送冠E贷解冻余额信息接口","异常信息"+e.toString()+"返回信息:"+responseInfo, new Date(),"发送信息:"+json));
		}
		return addOrderResponse;
	}
	
	
	
	
	public AddOrderResponse sendGuanEDaiPeriodStatus(Map<String,Object> param){
		String json=null;
		String responseInfo=null;
		AddOrderResponse addOrderResponse=null;
		String contratNo = "";
		try {
			json = JsonTransferUtils.bean2Json(param);
			System.out.println(json);
			responseInfo = OutInterfaceUtils.load(Global.getConfig("SendGedStatus"), json);
			addOrderResponse = JsonTransferUtils.json2Bean(responseInfo, AddOrderResponse.class);
			contratNo = (String)param.get("orderNo");
			interfaceInfoService.save(new InterfaceInfo(contratNo, "冠E通最后一期状态信息接口","返回信息:"+responseInfo, new Date(),"发送信息:"+json));
		} catch (Exception e) {
			e.printStackTrace();
			interfaceInfoService.save(new InterfaceInfo(contratNo, "冠E通最后一期状态信息接口","异常信息"+e.toString()+"返回信息:"+responseInfo, new Date(),"发送信息:"+json));
		}
		return addOrderResponse;
	}
	
	public AddOrderResponse sendOrderNoGET(String orderNo){
		String json=null;
		String responseInfo=null;
		AddOrderResponse addOrderResponse=null;
		try {
			Map<String,String> param =  Maps.newHashMap();
			param.put("orderNo", orderNo);
			json = JsonTransferUtils.bean2Json(param);
			System.out.println(json);
			responseInfo = OutInterfaceUtils.load(Global.getConfig("SendGedOderNo"), json);
			addOrderResponse = JsonTransferUtils.json2Bean(responseInfo, AddOrderResponse.class);
			interfaceInfoService.save(new InterfaceInfo(orderNo, "存储执行完调用冠E贷接口","返回信息:"+responseInfo, new Date(),"发送信息:"+json));
		} catch (Exception e) {
			e.printStackTrace();
			interfaceInfoService.save(new InterfaceInfo(orderNo, "存储执行完调用冠E贷接口","异常信息"+e.toString()+"返回信息:"+responseInfo, new Date(),"发送信息:"+json));
		}
		return addOrderResponse;
	}
	
	
	/**
	 * 推送冠E贷信息审信息非联合授信
	 * @param applyNo
	 * @return
	 */
	public AddOrderResponse sendGuanEDCreditInfo(String applyNo){
		String json=null;
		String responseInfo=null;
		AddOrderResponse addOrderResponse=null;
		try {
			CreditInfoToRequest creditInfoToRquest = new CreditInfoToRequest();
			GuanETInfo guanEDInfo = gedClientService.queryGuanTInfoByApplyNo(applyNo);
			List<GqgetAssetCarInfo> gqgetAssetCarInfos = gedClientService.queryGetCarInfos(applyNo);
			List<GqgetAssetHouseInfo> gqgetAssetHouseInfos = gedClientService.queryGqHouseInfo(applyNo);
			if (StringUtils.isNotBlank(guanEDInfo.getOrderNo())) {
				creditInfoToRquest.setOrderNo(guanEDInfo.getOrderNo());
			}
			creditInfoToRquest.setGetAssetCardList(gqgetAssetCarInfos);
			creditInfoToRquest.setGqgetHouseList(gqgetAssetHouseInfos);
			creditInfoToRquest.setGuanETInfo(guanEDInfo);
			json = JsonTransferUtils.bean2Json(creditInfoToRquest);
			System.out.println(json);
			responseInfo = OutInterfaceUtils.load(Global.getConfig("sendCreidtInfoGED"), json);
			addOrderResponse = JsonTransferUtils.json2Bean(responseInfo, AddOrderResponse.class);
			interfaceInfoService.save(new InterfaceInfo(applyNo, "推送冠E贷信审信息接口","返回信息:"+responseInfo, new Date(),"发送信息:"+json));
		} catch (Exception e) {
			e.printStackTrace();
			interfaceInfoService.save(new InterfaceInfo(applyNo, "推送冠E贷信审信息接口","异常信息"+e.toString()+"返回信息:"+responseInfo, new Date(),"发送信息:"+json));
		}
		return addOrderResponse;
	}
	
	
	public AddOrderResponse sendGuanEDCreditUnionInfo(String applyNo,String approveId){
		String json=null;
		String responseInfo=null;
		AddOrderResponse addOrderResponse=null;
		try {
			CreditInfoToRequest creditInfoToRquest = new CreditInfoToRequest();
			GuanETInfo guanEDInfo = gedClientService.queryGuanTInfoByApplyNoAndApproveId(applyNo,approveId);
			List<GqgetAssetCarInfo> gqgetAssetCarInfos = gedClientService.queryGetCarInfosUnion(applyNo,approveId);
			List<GqgetAssetHouseInfo> gqgetAssetHouseInfos = gedClientService.queryGqHouseInfoUnion(applyNo,approveId);
			if (StringUtils.isNotBlank(guanEDInfo.getOrderNo())) {
				creditInfoToRquest.setOrderNo(guanEDInfo.getOrderNo());
			}
			if (StringUtils.isNotBlank(guanEDInfo.getOrderSubNo())) {
				creditInfoToRquest.setOrderSubNo(guanEDInfo.getOrderSubNo());
			}
			creditInfoToRquest.setGetAssetCardList(gqgetAssetCarInfos);
			creditInfoToRquest.setGqgetHouseList(gqgetAssetHouseInfos);
			creditInfoToRquest.setGuanETInfo(guanEDInfo);
			json = JsonTransferUtils.bean2Json(creditInfoToRquest);
			System.out.println(json);
			responseInfo = OutInterfaceUtils.load(Global.getConfig("sendCreidtInfoGED"), json);
			addOrderResponse = JsonTransferUtils.json2Bean(responseInfo, AddOrderResponse.class);
			interfaceInfoService.save(new InterfaceInfo(applyNo, "推送冠E贷信审信息接口","返回信息:"+responseInfo, new Date(),"发送信息:"+json));
		} catch (Exception e) {
			e.printStackTrace();
			interfaceInfoService.save(new InterfaceInfo(applyNo, "推送冠E贷信审信息接口","异常信息"+e.toString()+"返回信息:"+responseInfo, new Date(),"发送信息:"+json));
		}
		return addOrderResponse;
	}
	
	/**
	 * 结束订单推送冠E贷
	 * @param applyNo	
	 * @param status	
	 */
	public void finishOrderPushGed(String applyNo,Integer status,String flag){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("orderNo", applyNo);
		params.put("status", status);
		params.put("flag",flag);
		String json=null;
		String responseInfo=null;
		try {
			json = JsonUtil.getJSONString(params);
			responseInfo = OutInterfaceUtils.load(Global.getConfig("finishOrderPushGed"), json);
			interfaceInfoService.save(new InterfaceInfo(applyNo, "流程结束推送订单状态反馈接口","返回信息:"+responseInfo, new Date(),"发送信息:"+json));
		} catch (Exception e) {
			e.printStackTrace();
			interfaceInfoService.save(new InterfaceInfo( applyNo, "流程结束推送订单状态反馈接口","异常信息"+e.toString()+"返回信息:"+responseInfo, new Date(),"发送信息:"+json));
		}
	}
	
	
	//推送合同订单关联关系
		public AddOrderResponse sendConytractRelation(OrderContractReqFormLists orderContractReqFormLists){
			String json=null;
			String responseInfo=null;
			AddOrderResponse addOrderResponse = null;
			try {
				json = JsonTransferUtils.bean2Json(orderContractReqFormLists);
				responseInfo = OutInterfaceUtils.load(Global.getConfig("SendContractRelation"), json);
				addOrderResponse = JsonTransferUtils.json2Bean(responseInfo, AddOrderResponse.class);
				interfaceInfoService.save(new InterfaceInfo( orderContractReqFormLists.getList().get(0).getOrderNo(), "合同号推送接口","返回信息:"+responseInfo, new Date(),"发送信息:"+json));
			} catch (Exception e) {
				e.printStackTrace();
				interfaceInfoService.save(new InterfaceInfo(orderContractReqFormLists.getList().get(0).getOrderNo(), "合同号推送接口","返回信息:"+responseInfo, new Date(),"发送信息:"+json));
			}
			return addOrderResponse;
		}

}
