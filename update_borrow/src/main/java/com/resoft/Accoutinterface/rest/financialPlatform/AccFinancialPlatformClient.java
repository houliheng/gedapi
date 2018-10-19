package com.resoft.Accoutinterface.rest.financialPlatform;

import com.resoft.Accoutinterface.rest.financialPlatform.entry.AccAccountDCRequest;
import com.resoft.Accoutinterface.rest.financialPlatform.entry.AccAccountDCResponse;
import com.resoft.Accoutinterface.rest.financialPlatform.entry.AccAccountEnterRequest;
import com.resoft.Accoutinterface.rest.financialPlatform.entry.AccAccountSearchRequest;
import com.resoft.Accoutinterface.rest.financialPlatform.entry.AccAccountSearchResponse;
import com.resoft.Accoutinterface.rest.financialPlatform.entry.AccBankCardChangeRequest;
import com.resoft.Accoutinterface.rest.financialPlatform.entry.AccDeductApplyRequest;
import com.resoft.Accoutinterface.rest.financialPlatform.entry.AccFinancialPlatformResponse;
import com.resoft.Accoutinterface.rest.financialPlatform.entry.AccRefundDepositRequest;
import com.resoft.Accoutinterface.rest.financialPlatform.entry.AccRepayList;
import com.resoft.Accoutinterface.rest.financialPlatform.entry.AccRepaymentWithholdingRequest;
import com.resoft.Accoutinterface.rest.financialPlatform.entry.ZjResoonseInfo;
import com.resoft.Accoutinterface.utils.AccFinancialPlatformUtils;
import com.resoft.accounting.AccfinancialPlatform.service.AccAcc4FinancialPlatformServer;
import com.resoft.accounting.common.utils.DateUtils;
import com.resoft.accounting.common.utils.JsonTransferUtils;
import com.resoft.credit.compensatory.entity.CompensatoryAccount;
import com.resoft.credit.interfaceinfo.entity.InterfaceInfo;
import com.resoft.credit.interfaceinfo.service.InterfaceInfoService;
import com.resoft.outinterface.utils.OutInterfaceUtils;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author caoyinglong
 *资金平台访问接口类
 */
//@Service@Lazy(false)
@RestController
@RequestMapping(value="/f/rest/funds/Accclient")
public class AccFinancialPlatformClient {

	private AccAcc4FinancialPlatformServer acc4FinancialPlatformServer = SpringContextHolder.getBean("accAcc4FinancialPlatformServer");
	
	private InterfaceInfoService interfaceInfoService = SpringContextHolder.getBean("interfaceInfoService");

	private static final Logger logger=LoggerFactory.getLogger(AccFinancialPlatformClient.class);

	/**
	 * @author caoyinglong
	 *保证金退还
	 */
	@RequestMapping(method=RequestMethod.GET,value="refundDeposit/{applyNo}")
	public  String refundDeposit(@PathVariable String contractNo){
		AccRefundDepositRequest car =acc4FinancialPlatformServer.getRefundDeposit(contractNo);
		try {
			car.setMchn(Global.getConfig("FPMchn"));
			car.setSeq_no(AccFinancialPlatformUtils.makeSeqNo());
			car.setSignature(car.getSeq_no());
			System.out.println(car.toString());
		} catch (Exception e1) {
			logger.error("退还保证金失败");
			return "退还保证金失败！";
		}
		try {
			String json = JsonTransferUtils.bean2Json(car);
			String response = AccFinancialPlatformUtils.load(Global.getConfig("FPRefundDeposit"), json);
			AccFinancialPlatformResponse fpr =JsonTransferUtils.json2Bean(response, AccFinancialPlatformResponse.class);
			String msg =fpr.getResp_msg();
			return msg;
		} catch (Exception e) {
			logger.error(e.toString());
			return "接口访问失败！";
		}
	}
	/**
	 * @author caoyinglong
	 *银行卡变更
	 */
	@RequestMapping(method=RequestMethod.GET,value="bankCardChange/{applyNo}")
	public  String bankCardChange(@PathVariable String contractNo){
		AccBankCardChangeRequest car =acc4FinancialPlatformServer.getBankCardChange(contractNo);
		try {
			car.setMchn(Global.getConfig("FPMchn"));
			car.setSeq_no(AccFinancialPlatformUtils.makeSeqNo());
			car.setSignature(car.getSeq_no());
			acc4FinancialPlatformServer.updateBankCardBySeqNo(car.getSeq_no(),contractNo);
			System.out.println(car.toString());
		} catch (Exception e1) {
			logger.error("银行卡变更失败，银行卡变更申请表中无该进件数据");
			return "银行卡变更失败！";
		}
		try {
			String json = JsonTransferUtils.bean2Json(car);
			String response = AccFinancialPlatformUtils.load(Global.getConfig("FPBankCardChange"), json);
			AccFinancialPlatformResponse fpr =JsonTransferUtils.json2Bean(response, AccFinancialPlatformResponse.class);
			String msg =fpr.getResp_msg();
			return msg;
		} catch (Exception e) {
			logger.error(e.toString());
			return "接口访问失败！";
		}
	}

	private  AccFinancialPlatformResponse repaymentWithholdingSender(AccRepaymentWithholdingRequest rwr){
		rwr.setMchn(Global.getConfig("FPMchn"));
		rwr.setSeq_no(AccFinancialPlatformUtils.makeSeqNo());
		rwr.setSignature(rwr.getSeq_no());
		rwr.setTrade_type("11093001");
		try {
			String json = JsonTransferUtils.bean2Json(rwr);
			logger.info("发送信息："+json);
			String response = AccFinancialPlatformUtils.load(Global.getConfig("FPRepaymentWithHolding"), json);
			logger.info("返回信息："+response);
			AccFinancialPlatformResponse fpr =JsonTransferUtils.json2Bean(response, AccFinancialPlatformResponse.class);
			AccFinancialPlatformResponse fpr2 = new AccFinancialPlatformResponse();
			fpr2.setMchn(Global.getConfig("FPMchn"));
			fpr2.setResp_code("0000");
			fpr2.setResp_msg("成功");
			fpr2.setSeq_no("1");
			return fpr;
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return null;
	}
	/**
	 * @author caoyinglong
	 *还款划扣
	 * @throws IOException 
	 */

	@Scheduled(fixedRate=1000*60)
	@RequestMapping(method=RequestMethod.GET,value="repaymentWithholding")
	public  void repaymentWithholding (String contractNo) throws IOException{
		AccRepaymentWithholdingRequest adminrwr =null;
		List<AccDeductApplyRequest> tempdar = new ArrayList<AccDeductApplyRequest> ();
		List<AccDeductApplyRequest> dar =acc4FinancialPlatformServer.getDeductApplyRequestByContractNo(contractNo);//查找ACC_DEDUCT_APPLY_TMP
		if (dar ==null){
			return;
		}
		List<AccRepayList> lrl  = new ArrayList<AccRepayList>();
		Iterator<AccDeductApplyRequest> it = dar.iterator();
		while(it.hasNext()){
			AccDeductApplyRequest dear =  it.next();
			if("admin".equals(dear.getDeductCustId())){
				if(adminrwr==null){
					adminrwr = new AccRepaymentWithholdingRequest();
				}
				lrl.add(dear.getRepayList());
				tempdar.add(dear);
			}else{
				AccRepaymentWithholdingRequest otherrwr = new AccRepaymentWithholdingRequest();
				List<AccRepayList> rl = new ArrayList<AccRepayList>();
				rl.add(dear.getRepayList());
				otherrwr.setRepay_list(rl);
				AccFinancialPlatformResponse fpr=this.repaymentWithholdingSender(otherrwr);
				if(fpr==null){
					logger.error("接口访问失败！请检查网络。");
					interfaceInfoService.save(new InterfaceInfo(contractNo, "还款划扣申请接口", "接口访问失败！请检查网络", new Date(), JsonTransferUtils.bean2Json(otherrwr)));
					continue;
				}else if(!"0000".equals(fpr.getResp_code())&&!"00000000".equals(fpr.getResp_code())){
					logger.error("接口访问数据错误："+fpr.getResp_msg());
					interfaceInfoService.save(new InterfaceInfo(contractNo, "还款划扣申请接口", "接口访问数据错误", new Date(), JsonTransferUtils.bean2Json(otherrwr)));
				}else{
					//将对应的划扣放入划扣申请表中。
					acc4FinancialPlatformServer.insertDeductApplyRequest(dear);
					interfaceInfoService.save(new InterfaceInfo(contractNo, "还款划扣申请接口", "扣款申请成功", new Date(), JsonTransferUtils.bean2Json(otherrwr)));
				}
			}
		}
		if(adminrwr!=null){
			adminrwr.setRepay_list(lrl);
			AccFinancialPlatformResponse fpr =this.repaymentWithholdingSender(adminrwr);
			if(fpr==null){
				logger.error("接口访问失败！请检查网络。");
			}else if(!"0000".equals(fpr.getResp_code())&&!"00000000".equals(fpr.getResp_code())){
				logger.error("接口访问数据错误："+fpr.getResp_msg());
			}else{
				//将对应的划扣放入划扣申请表中。
				acc4FinancialPlatformServer.insertDeductApplyListRequest(dar);
			}
		}
	}
	/**
	 * @author caoyinglong
	 *入账
	 */
//	@Scheduled(fixedRate=1000*60)
	@RequestMapping(method=RequestMethod.GET,value="accountEnter")
	public  void accountEnter(){
		String date=DateUtils.formatDate(new Date(), "yyyy-MM-dd");
		AccAccountEnterRequest car =acc4FinancialPlatformServer.getAccountEnter(date);
		car.setMchn(Global.getConfig("FPMchn"));
		car.setSeq_no(AccFinancialPlatformUtils.makeSeqNo());
		car.setSignature(car.getSeq_no());
		car.setTrade_type("11099001");
		try {
			String json = JsonTransferUtils.bean2Json(car);
			System.out.println(json);
			String response = AccFinancialPlatformUtils.load(Global.getConfig("FPAccountEnter"), json);
			AccFinancialPlatformResponse fpr =JsonTransferUtils.json2Bean(response, AccFinancialPlatformResponse.class);
			String msg =fpr.getResp_msg();
			if("0000".equals(fpr.getResp_code())){
				acc4FinancialPlatformServer.updateAccountEnter(car.getEnter_account());
			}else{
				logger.error(msg);
			}
		} catch (Exception e) {
			logger.error(e.toString());
		}
	}
	public AccAccountDCResponse repaymentDC(AccAccountDCRequest accAccountDCRequest,String contractNo,String flag) throws Exception {
		AccAccountDCResponse accAccountDCResponse = new AccAccountDCResponse();
		String zjResoonseInfo = JsonTransferUtils.bean2Json(new ZjResoonseInfo());
		accAccountDCResponse.setData(zjResoonseInfo);
		Map<String,String> param = new HashMap<>();
		param = AccFinancialPlatformUtils.objectToMap(accAccountDCRequest);
		String json=null;
		String response=null;
		String logName="";
		String address = "";
		if("0".equals(flag)) {
			logName="代偿";
			address = Global.getConfig("FPRepaymentDC");
			
		}else if("1".equals(flag)){
			logName="充值还款代偿";
			address = Global.getConfig("ZKToRepayDC");
		}else if("2".equals(flag)){
			logName="贴息";
			address = Global.getConfig("FPRepaymentDC");
		}else if("3".equals(flag)){
			logName="提前结清违约金";
			address = Global.getConfig("penaltyTransfer");
		}
		json = JsonTransferUtils.bean2Json(accAccountDCRequest);
		try {
			if (StringUtils.isNotEmpty(address)) {
				response = OutInterfaceUtils.doPostForm(address, param);
			}
			accAccountDCResponse =JsonTransferUtils.json2Bean(response, AccAccountDCResponse.class);
			interfaceInfoService.save(new InterfaceInfo(contractNo, logName, accAccountDCResponse.getResp_msg()+"返回信息"+response, new Date(),"发送信息"+json));
			return accAccountDCResponse;
		} catch (Exception e) {
			e.printStackTrace();
				interfaceInfoService.save(new InterfaceInfo(contractNo, logName, e.toString()+accAccountDCResponse.getResp_msg()+"返回信息"+response, new Date(),"发送信息"+json));
				return accAccountDCResponse;
		}
	}
	
	public List<CompensatoryAccount> repaymentFindAccount(List<CompensatoryAccount> oldCompensatoryAccountList) {
		int i;
		String json=null;
		String response=null;
		AccAccountSearchRequest accAccountSearchRequest=new AccAccountSearchRequest();
		AccAccountSearchResponse accAccountSearchResponse = new AccAccountSearchResponse();
		accAccountSearchRequest.setMchn(Global.getConfig("FPMchn"));
		accAccountSearchRequest.setSeq_no(OutInterfaceUtils.makeSeqNo());
		accAccountSearchRequest.setTrade_type("11021102");
		//accAccountSearchRequest.setUser_no(user_no);
		//accAccountSearchRequest.setBusi_no(busi_no);
		//accAccountSearchRequest.setSignature(signature);
		List<CompensatoryAccount> newCompensatoryAccountList = new ArrayList<>(); 
		for (i = 0; i < oldCompensatoryAccountList.size(); i++) {
			try {
				accAccountSearchRequest.setCust_no(oldCompensatoryAccountList.get(i).getCustId());
				Integer valueOf = Integer.valueOf(oldCompensatoryAccountList.get(i).getCustId());
				if(valueOf<100) {
					accAccountSearchRequest.setBusi_type("0");
				}else {
					accAccountSearchRequest.setBusi_type("1");
				}
				json = JsonTransferUtils.bean2Json(accAccountSearchRequest);
				response = AccFinancialPlatformUtils.load(Global.getConfig("FPRepaymentWithHolding"), json);
				accAccountSearchResponse =JsonTransferUtils.json2Bean(response, AccAccountSearchResponse.class);
				oldCompensatoryAccountList.get(i).setAccountAmount(accAccountSearchResponse.getAmount());
				newCompensatoryAccountList.add(oldCompensatoryAccountList.get(i));
				interfaceInfoService.save(new InterfaceInfo(oldCompensatoryAccountList.get(i).getCompensatoryAccount(), "代偿账户查询", accAccountSearchResponse.getResp_msg()+"返回信息"+response, new Date(),"发送信息"+json));
			} catch (Exception e) {
				e.printStackTrace();
				interfaceInfoService.save(new InterfaceInfo(oldCompensatoryAccountList.get(i).getCompensatoryAccount(), "代偿账户查询", accAccountSearchResponse.getResp_msg()+"返回信息"+response, new Date(),"发送信息"+json));
			}
		}
		return newCompensatoryAccountList;
	}
	
	
	public AccAccountSearchResponse repaymentFindAccountGed(String contractNO, String custId){
		String json=null;
		String response=null;
		AccAccountSearchRequest accAccountSearchRequest=new AccAccountSearchRequest();
		AccAccountSearchResponse accAccountSearchResponse = new AccAccountSearchResponse();
		accAccountSearchRequest.setBusi_type("0");
		accAccountSearchRequest.setMchn(Global.getConfig("FPMchn"));
		accAccountSearchRequest.setSeq_no(OutInterfaceUtils.makeSeqNo());
		accAccountSearchRequest.setTrade_type("11021102");
		accAccountSearchRequest.setCust_no(custId);
		try {
			json = JsonTransferUtils.bean2Json(accAccountSearchRequest);
			response = AccFinancialPlatformUtils.load(Global.getConfig("FPRepaymentWithHolding"), json);
			accAccountSearchResponse =JsonTransferUtils.json2Bean(response, AccAccountSearchResponse.class);
			interfaceInfoService.save(new InterfaceInfo(contractNO, "还款账户查询", accAccountSearchResponse.getResp_msg()+"返回信息"+response, new Date(),"发送信息"+json));
		} catch (Exception e) {
			logger.error("错误信息:",e.toString());
			interfaceInfoService.save(new InterfaceInfo(contractNO, "还款账户查询", accAccountSearchResponse.getResp_msg()+"返回信息"+response, new Date(),"发送信息"+json));
		}
		return accAccountSearchResponse;
		
	}
	/**
	 * 放款接口增加费用清算
	 * @param
	 * @return
	 */
	public AccAccountDCResponse Compensation(String json ,String Contract){
		AccAccountDCResponse accAccountDCResponse = new AccAccountDCResponse();
		String response = null;
		try{
			response = AccFinancialPlatformUtils.load(Global.getConfig("Compensation"),json);
			accAccountDCResponse = JsonTransferUtils.json2Bean(response,AccAccountDCResponse.class);
			interfaceInfoService.save(new InterfaceInfo(Contract,"资金代偿费用收取",accAccountDCResponse.getResp_msg()+"返回信息"+response,new Date(),"发送信息"+json));
			return accAccountDCResponse;
		}catch (Exception e){
			e.printStackTrace();
			interfaceInfoService.save(new InterfaceInfo(Contract,"资金代偿费用收取",accAccountDCResponse.getResp_msg()+"返回信息"+response,new Date(),"发送信息"+json));
		}
		return accAccountDCResponse;
	}

	/**
	 * 划拨充值
	 * @param request 请求实体
	 * @param contractNo 合同编号
	 * @return 响应实体
	 */
	public AccAccountDCResponse transferWithHold(AccAccountDCRequest request, String contractNo) throws Exception {
		AccAccountDCResponse response = new AccAccountDCResponse();
		Map<String, String> params = AccFinancialPlatformUtils.objectToMap(request);
		String address = Global.getConfig("FPTransferWithHold");
		String json = JsonTransferUtils.bean2Json(request);
		String result = null;
		try {
			result = OutInterfaceUtils.doPostForm(address, params);
			response = JsonTransferUtils.json2Bean(result, AccAccountDCResponse.class);
			interfaceInfoService.save(
				new InterfaceInfo(contractNo, "划拨充值", response.getResp_msg() + "返回信息" + result, new Date(),
					"发送信息" + json));
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			interfaceInfoService.save(
				new InterfaceInfo(contractNo, "划拨充值", e.toString() + response.getResp_msg() + "返回信息" + result,
					new Date(), "发送信息 : " + json));
			return response;
		}
	}
}
