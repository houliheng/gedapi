package com.resoft.outinterface.rest.financialPlatform;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.resoft.accounting.financialPlatform.service.Acc4FinancialPlatformServer;
import com.resoft.common.utils.Constants;
import com.resoft.common.utils.DateUtils;
import com.resoft.common.utils.JsonTransferUtils;
import com.resoft.credit.interfaceinfo.entity.InterfaceInfo;
import com.resoft.credit.interfaceinfo.service.InterfaceInfoService;
import com.resoft.outinterface.rest.financialPlatform.entry.AbortiveTenderRequest;
import com.resoft.outinterface.rest.financialPlatform.entry.AccountEnterRequest;
import com.resoft.outinterface.rest.financialPlatform.entry.BankCardChangeRequest;
import com.resoft.outinterface.rest.financialPlatform.entry.BorrowerDepositRequest;
import com.resoft.outinterface.rest.financialPlatform.entry.BorrowerLoanRequest;
import com.resoft.outinterface.rest.financialPlatform.entry.CreateAccountRequest;
import com.resoft.outinterface.rest.financialPlatform.entry.CreateAccountResponse;
import com.resoft.outinterface.rest.financialPlatform.entry.DeductApplyRequest;
import com.resoft.outinterface.rest.financialPlatform.entry.FinancialPlatformResponse;
import com.resoft.outinterface.rest.financialPlatform.entry.MortgageeDepositRequest;
import com.resoft.outinterface.rest.financialPlatform.entry.RefundDepositRequest;
import com.resoft.outinterface.rest.financialPlatform.entry.RepayList;
import com.resoft.outinterface.rest.financialPlatform.entry.RepaymentWithholdingRequest;
import com.resoft.outinterface.rest.financialPlatform.service.FinancialPlatformService;
import com.resoft.outinterface.rest.sendGET.entry.SendGETRequest;
import com.resoft.outinterface.rest.sendGET.entry.SendGETResponse;
import com.resoft.outinterface.utils.OutInterfaceUtils;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * @author caoyinglong
 *资金平台访问接口类
 */
@Service
@RestController
@RequestMapping(value = "/f/rest/funds/client")
public class FinancialPlatformClient {
	private FinancialPlatformService financialPlatformService = SpringContextHolder.getBean("financialPlatformService");

	private Acc4FinancialPlatformServer acc4FinancialPlatformServer = SpringContextHolder.getBean("acc4FinancialPlatformServer");

	private static final Logger logger=LoggerFactory.getLogger(FinancialPlatformClient.class);

	private InterfaceInfoService interfaceInfoService = SpringContextHolder.getBean("interfaceInfoService");
	/**
	 * @author caoyinglong
	 *借款人开户
	 */
	@RequestMapping(method=RequestMethod.GET,value="createAccount/{applyNo}")
	public  Map<String,String> borrowerCreateAccount(@PathVariable String applyNo){
		CreateAccountRequest car =financialPlatformService.getBorrowerByApplyNo(applyNo);
		Map<String,String> resultMap = new HashMap<String, String>();
		try {
			String seqNo =OutInterfaceUtils.makeSeqNo();
			String mchn = Global.getConfig("FPMchn");
			car.setMchn(mchn);
			car.setSeq_no(seqNo);
			car.setSignature(OutInterfaceUtils.signatureMaker(mchn, seqNo, car.getTrade_type(), Global.getConfig("mchnKey")));
		} catch (Exception e1) {
			logger.error("开户失败，合同表无该进件的合同");
			resultMap.put("flag","false");
			resultMap.put("msg","开户失败！无合同号。");
			resultMap.put("accNo", "");
			return resultMap;
		}
		Date sendDate = new Date();
		try {
			String json = JsonTransferUtils.bean2Json(car);
			logger.info(json);
			String response = OutInterfaceUtils.load(Global.getConfig("FPcreateAccount"), json);
			logger.info(response);
			CreateAccountResponse fpr =JsonTransferUtils.json2Bean(response, CreateAccountResponse.class);
			if("0000".equals(fpr.getResp_code())||"00000000".equals(fpr.getResp_code())){
				financialPlatformService.createAccNo2ContractNo(applyNo,fpr.getAccNo());
				acc4FinancialPlatformServer.createAccNo2ContractNo(applyNo,fpr.getAccNo());
				resultMap.put("flag","true");
				resultMap.put("msg",fpr.getResp_msg());
				resultMap.put("status",DictUtils.getDictLabel(Constants.OPEN_ACCOUNT_STATUS_YKH, Constants.OPEN_ACCOUNT_STATUS, ""));
				resultMap.put("accNo",fpr.getAccNo());
				try {
					interfaceInfoService.save(new InterfaceInfo(applyNo, "资金平台借款人开户接口", fpr.getResp_msg(), sendDate));
				} catch (Exception e) {
					logger.error("接口信息记录失败！",e);
				}
			}else{
				resultMap.put("flag","false");
				resultMap.put("msg",fpr.getResp_msg());
				resultMap.put("accNo",fpr.getAccNo());
				try {
					interfaceInfoService.save(new InterfaceInfo(applyNo, "资金平台借款人开户接口", fpr.getResp_msg(),sendDate));
				} catch (Exception e) {
					logger.error("接口信息记录失败！",e);
				}
			}
			return resultMap;
		} catch (Exception e) {
			logger.error("接口访问失败！",e);
			resultMap.put("flag","false");
			resultMap.put("msg","接口访问失败！");
			resultMap.put("accNo","");
			try {
				interfaceInfoService.save(new InterfaceInfo(applyNo, "冠E通流标接口", "接口访问失败:"+e.toString(),sendDate));
			} catch (Exception e2) {
				logger.error("接口信息记录失败！",e2);
			}
			return resultMap;
		}
	}
	/**
	 * @author caoyinglong
	 *借款人放款
	 */
	@RequestMapping(method=RequestMethod.GET,value="borrowerLoan/{applyNo}")
	public  String borrowerLoan(@PathVariable String applyNo){
		BorrowerLoanRequest car =financialPlatformService.getBorrowerLoan(applyNo);
		try {
			String seqNo =OutInterfaceUtils.makeSeqNo();
			String mchn = Global.getConfig("FPMchn");
			car.setMchn(mchn);
			car.setSeq_no(seqNo);
			car.setSignature(OutInterfaceUtils.signatureMaker(mchn, seqNo, car.getTrade_type(), Global.getConfig("mchnKey")));
			car.setContractInterest(financialPlatformService.getContractInterest(applyNo));
		} catch (Exception e1) {
			logger.error("放款失败，合同表无该进件的合同");
			return "放款失败！无合同号。";
		}
		Date sendDate = new Date();
		try {
			String json = JsonTransferUtils.bean2Json(car);
			System.out.println(json);
			String response = OutInterfaceUtils.load(Global.getConfig("FPborrowerLoan"), json);
			FinancialPlatformResponse fpr =JsonTransferUtils.json2Bean(response, FinancialPlatformResponse.class);
			if("0000".equals(fpr.getResp_code())||"00000000".equals(fpr.getResp_code())){
				financialPlatformService.borrowerLoanUpApply(car.getContract_no());
			}
			String msg =fpr.getResp_msg();
			try {
				interfaceInfoService.save(new InterfaceInfo(applyNo, "资金平台借款人放款接口", msg, sendDate));
			} catch (Exception e) {
				logger.error("接口信息记录失败！",e);
			}
			return msg;
		} catch (Exception e) {
			logger.error(e.toString());
			try {
				interfaceInfoService.save(new InterfaceInfo(applyNo, "资金平台借款人放款接口", "接口访问失败："+e.toString(), sendDate));
			} catch (Exception e2) {
				logger.error("接口信息记录失败！",e2);
			}
			return "接口访问失败！";
		}
	}
	/**
	 * @author caoyinglong
	 *抵押权人开户
	 */
	@RequestMapping(method=RequestMethod.GET,value="mortgageeCreateAccount/{idNum}")
	public  String mortgageeCreateAccount(@PathVariable String idNum){
		CreateAccountRequest car =financialPlatformService.getMortgageeByApplyNo(idNum);
		try {
			String seqNo =OutInterfaceUtils.makeSeqNo();
			String mchn = Global.getConfig("FPMchn");
			car.setMchn(mchn);
			car.setSeq_no(seqNo);
			car.setSignature(OutInterfaceUtils.signatureMaker(mchn, seqNo, car.getTrade_type(), Global.getConfig("mchnKey")));
		} catch (Exception e1) {
			logger.error("开户失败，合同表无该进件的合同");
			return "开户失败！无合同号。";
		}
		Date sendDate = new Date();
		try {
			String json = JsonTransferUtils.bean2Json(car);
			logger.info(json);
			String response = OutInterfaceUtils.load(Global.getConfig("FPcreateAccount"), json);
			logger.info(response);
			CreateAccountResponse fpr =JsonTransferUtils.json2Bean(response, CreateAccountResponse.class);
			if("0000".equals(fpr.getResp_code())||"00000000".equals(fpr.getResp_code())){
				financialPlatformService.createAccNo2Mortgagee(idNum,fpr.getAcc_no());
			}
			String msg =fpr.getResp_msg();
			try {
				interfaceInfoService.save(new InterfaceInfo("idNum:"+idNum, "资金平台抵押权人开户接口", msg, sendDate));
			} catch (Exception e) {
				logger.error("接口信息记录失败！",e);
			}
			return msg;
		} catch (Exception e) {
			logger.error(e.toString());
			try {
				interfaceInfoService.save(new InterfaceInfo("idNum:"+idNum, "资金平台抵押权人开户接口", "接口访问失败"+e.toString(), sendDate));
			} catch (Exception e2) {
				logger.error("接口信息记录失败！",e2);
			}
			return "接口访问失败！";
		}
	}
	/**
	 * @author caoyinglong
	 *借款人提现
	 */
	@RequestMapping(method=RequestMethod.GET,value="borrowerDeposit/{applyNo}")
	public  String borrowerDeposit(@PathVariable String applyNo){
		BorrowerDepositRequest car =financialPlatformService.getBorrowerDeposit(applyNo);
		try {
			String seqNo =OutInterfaceUtils.makeSeqNo();
			String mchn = Global.getConfig("FPMchn");
			car.setMchn(mchn);
			car.setSeq_no(seqNo);
			car.setSignature(OutInterfaceUtils.signatureMaker(mchn, seqNo, car.getTrade_type(), Global.getConfig("mchnKey")));
		} catch (Exception e1) {
			logger.error("提现失败，合同表无该进件的合同");
			return "提现失败";
		}
		Date sendDate = new Date();
		try {
			String json = JsonTransferUtils.bean2Json(car);
			logger.info(json);
			String response = OutInterfaceUtils.load(Global.getConfig("FPBorrowerDeposit"), json);
			FinancialPlatformResponse fpr =JsonTransferUtils.json2Bean(response, FinancialPlatformResponse.class);
			if("0000".equals(fpr.getResp_code())||"00000000".equals(fpr.getResp_code())){
				financialPlatformService.borrowerDepositUpApply(car.getContract_no());
			}
			String msg =fpr.getResp_msg();
			try {
				interfaceInfoService.save(new InterfaceInfo(applyNo, "资金平台借款人提现接口", msg, sendDate));
			} catch (Exception e) {
				logger.error("接口信息记录失败！",e);
			}
			return msg;
		} catch (Exception e) {
			logger.error(e.toString());
			try {
				interfaceInfoService.save(new InterfaceInfo(applyNo, "资金平台借款人提现接口", "接口访问失败:"+e.toString(), sendDate));
			} catch (Exception e2) {
				logger.error("接口信息记录失败！",e2);
			}
			return "接口访问失败！";

		}
	}
	/**
	 * @author caoyinglong
	 *抵押权人提现
	 */
	@RequestMapping(method=RequestMethod.GET,value="mortgageeDeposit/{applyNo}")
	public  String mortgageeDeposit(@PathVariable String applyNo){
		MortgageeDepositRequest car =financialPlatformService.getMortgageeDeposit(applyNo);
		try {
			String seqNo =OutInterfaceUtils.makeSeqNo();
			String mchn = Global.getConfig("FPMchn");
			car.setMchn(mchn);
			car.setSeq_no(seqNo);
			car.setSignature(OutInterfaceUtils.signatureMaker(mchn, seqNo, car.getTrade_type(), Global.getConfig("mchnKey")));
		} catch (Exception e1) {
			logger.error("提现失败，合同表无该进件的合同");
			return "提现失败";
		}
		Date sendDate = new Date();
		try {
			String json = JsonTransferUtils.bean2Json(car);
			logger.info(json);
			String response = OutInterfaceUtils.load(Global.getConfig("FPMortgageeDeposit"), json);
			logger.info(response);
			FinancialPlatformResponse fpr =JsonTransferUtils.json2Bean(response, FinancialPlatformResponse.class);
			String msg =fpr.getResp_msg();
			try {
				interfaceInfoService.save(new InterfaceInfo(applyNo, "资金平台抵押权人提现接口", msg, sendDate));
			} catch (Exception e) {
				logger.error("接口信息记录失败！",e);
			}
			return msg;
		} catch (Exception e) {
			logger.error(e.toString());
			try {
				interfaceInfoService.save(new InterfaceInfo(applyNo, "资金平台抵押权人提现接口", "接口访问失败:"+e.toString(), sendDate));
			} catch (Exception e2) {
				logger.error("接口信息记录失败！",e2);
			}
			return "接口访问失败！";
		}
	}
	/**
	 * @author caoyinglong
	 *保证金退还
	 */
	@RequestMapping(method=RequestMethod.GET,value="refundDeposit/{applyNo}")
	public  String refundDeposit(@PathVariable String applyNo){
		RefundDepositRequest car =acc4FinancialPlatformServer.getRefundDeposit(applyNo);
		try {
			String seqNo =OutInterfaceUtils.makeSeqNo();
			String mchn = Global.getConfig("FPMchn");
			car.setMchn(mchn);
			car.setSeq_no(seqNo);
			car.setSignature(OutInterfaceUtils.signatureMaker(mchn, seqNo, car.getTrade_type(), Global.getConfig("mchnKey")));
		} catch (Exception e1) {
			logger.error("退还保证金失败");
			return "退还保证金失败！";
		}
		Date sendDate = new Date();
		try {
			String json = JsonTransferUtils.bean2Json(car);
			logger.info(json);
			String response = OutInterfaceUtils.load(Global.getConfig("FPRefundDeposit"), json);
			logger.info(response);
			FinancialPlatformResponse fpr =JsonTransferUtils.json2Bean(response, FinancialPlatformResponse.class);
			String msg =fpr.getResp_msg();
			try {
				interfaceInfoService.save(new InterfaceInfo(applyNo, "资金平台保证金退还接口", msg, sendDate));
			} catch (Exception e) {
				logger.error("接口信息记录失败！",e);
			}
			return msg;
		} catch (Exception e) {
			logger.error(e.toString());
			try {
				interfaceInfoService.save(new InterfaceInfo(applyNo, "资金平台保证金退还接口", "接口访问失败:"+e.toString(), sendDate));
			} catch (Exception e2) {
				logger.error("接口信息记录失败！",e2);
			}
			return "接口访问失败！";
		}
	}
	/**
	 * @author caoyinglong
	 *银行卡变更
	 */
	@RequestMapping(method=RequestMethod.GET,value="bankCardChange/{applyNo}")
	public  String bankCardChange(@PathVariable String applyNo){
		BankCardChangeRequest car =acc4FinancialPlatformServer.getBankCardChange(applyNo);
		try {
			String seqNo =OutInterfaceUtils.makeSeqNo();
			String mchn = Global.getConfig("FPMchn");
			car.setMchn(mchn);
			car.setSeq_no(seqNo);
			car.setSignature(OutInterfaceUtils.signatureMaker(mchn, seqNo, car.getTrade_type(), Global.getConfig("mchnKey")));
		} catch (Exception e1) {
			logger.error("银行卡变更失败，银行卡变更申请表中无该进件数据");
			return "银行卡变更失败！";
		}
		Date sendDate = new Date();
		try {
			String json = JsonTransferUtils.bean2Json(car);
			logger.info(json);
			String response = OutInterfaceUtils.load(Global.getConfig("FPBankCardChange"), json);
			logger.info(response);
			FinancialPlatformResponse fpr =JsonTransferUtils.json2Bean(response, FinancialPlatformResponse.class);
			String msg =fpr.getResp_msg();
			try {
				interfaceInfoService.save(new InterfaceInfo(applyNo, "资金平台银行卡变更接口", msg, sendDate));
			} catch (Exception e) {
				logger.error("接口信息记录失败！",e);
			}
			return msg;
		} catch (Exception e) {
			logger.error(e.toString());
			try {
				interfaceInfoService.save(new InterfaceInfo(applyNo, "资金平台银行卡变更接口", "接口访问失败:"+e.toString(), sendDate));
			} catch (Exception e2) {
				logger.error("接口信息记录失败！",e2);
			}
			return "接口访问失败！";
		}
	}

	/**
	 * @author caoyinglong
	 *流标
	 */
	@RequestMapping(method=RequestMethod.GET,value="abortiveTender/{applyNo}")
	public  String abortiveTender(@PathVariable String applyNo){
		AbortiveTenderRequest car =financialPlatformService.getAbortiveTender(applyNo);
		try {
			String seqNo =OutInterfaceUtils.makeSeqNo();
			String mchn = Global.getConfig("FPMchn");
			car.setMchn(mchn);
			car.setSeq_no(seqNo);
			car.setSignature(OutInterfaceUtils.signatureMaker(mchn, seqNo, car.getTrade_type(), Global.getConfig("mchnKey")));
			System.out.println(car.toString());
		} catch (Exception e1) {
			logger.error("流标失败,合同表与批准表关联无数据!");
			return "流标失败！";
		}
		Date sendDate = new Date();
		try {
			String json = JsonTransferUtils.bean2Json(car);
			logger.info(json);
			String response = OutInterfaceUtils.load(Global.getConfig("FPAbortiveTender"), json);
			logger.info(response);
			FinancialPlatformResponse fpr =JsonTransferUtils.json2Bean(response, FinancialPlatformResponse.class);
			String msg =fpr.getResp_msg();
			try {
				interfaceInfoService.save(new InterfaceInfo(applyNo, "资金平台流标接口", msg, sendDate));
			} catch (Exception e) {
				logger.error("接口信息记录失败！",e);
			}
			return msg;
		} catch (Exception e) {
			logger.error(e.toString());
			try {
				interfaceInfoService.save(new InterfaceInfo(applyNo, "资金平台流标接口", "接口访问失败:"+e.toString(), sendDate));
			} catch (Exception e2) {
				logger.error("接口信息记录失败！",e2);
			}
			return "接口访问失败！";
		}
	}

	private  FinancialPlatformResponse repaymentWithholdingSender(RepaymentWithholdingRequest rwr){
		String seqNo =OutInterfaceUtils.makeSeqNo();
		String mchn = Global.getConfig("FPMchn");
		rwr.setMchn(mchn);
		rwr.setSeq_no(seqNo);
		rwr.setTrade_type("11093001");
		rwr.setSignature(OutInterfaceUtils.signatureMaker(mchn, seqNo,rwr.getTrade_type(), Global.getConfig("mchnKey")));
		try {
			String json = JsonTransferUtils.bean2Json(rwr);
			logger.info(json);
			String response = OutInterfaceUtils.load(Global.getConfig("FPRepaymentWithHolding"), json);
			logger.info(response);
			FinancialPlatformResponse fpr =JsonTransferUtils.json2Bean(response, FinancialPlatformResponse.class);
			FinancialPlatformResponse fpr2 = new FinancialPlatformResponse();
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
	 */

	@RequestMapping(method=RequestMethod.GET,value="repaymentWithholding")
	public  void repaymentWithholding (){
		RepaymentWithholdingRequest adminrwr =null;
		List<DeductApplyRequest> tempdar = new ArrayList<DeductApplyRequest> ();
		List<DeductApplyRequest> dar =acc4FinancialPlatformServer.getRepaymentWithholding();
		if (dar ==null){
			return;
		}
		List<RepayList> lrl  = new ArrayList<RepayList>();
		Iterator<DeductApplyRequest> it = dar.iterator();
		while(it.hasNext()){
			DeductApplyRequest dear =  it.next();
			if("admin".equals(dear.getDeductCustId())){
				if(adminrwr==null){
					adminrwr = new RepaymentWithholdingRequest();
				}
				lrl.add(dear.getRepayList());
				tempdar.add(dear);
			}else{
				RepaymentWithholdingRequest otherrwr = new RepaymentWithholdingRequest();
				List<RepayList> rl = new ArrayList<RepayList>();
				rl.add(dear.getRepayList());
				otherrwr.setRepay_list(rl);
				FinancialPlatformResponse fpr=this.repaymentWithholdingSender(otherrwr);
				if(fpr==null){
					logger.error("接口访问失败！请检查网络。");
					continue;
				}else if(!"0000".equals(fpr.getResp_code())&&!"00000000".equals(fpr.getResp_code())){
					logger.error("接口访问数据错误："+fpr.getResp_msg());
				}else{
					//将对应的划扣放入划扣申请表中。
					acc4FinancialPlatformServer.insertDeductApplyRequest(dear);
				}
			}
		}
		if(adminrwr!=null){
			adminrwr.setRepay_list(lrl);
			FinancialPlatformResponse fpr =this.repaymentWithholdingSender(adminrwr);
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
//	private  FinancialPlatformResponse repaymentWithholdinghandle(RepaymentWithholdingRequest otherrwr){
//		FinancialPlatformResponse fpr = this.repaymentWithholdingSender(otherrwr);
//		boolean delflag= true;
//		int count =0;
//		while(delflag){
//			if(fpr==null){
//				delflag=false;
//				continue;
//			}
//			if(!"0000".equals(fpr.getResp_code())&&!"00000000".equals(fpr.getResp_code())){
//				fpr =this.repaymentWithholdingSender(otherrwr);
//				if(count==3){
//					logger.error("接口发送错误:"+fpr.getResp_msg()+otherrwr.toString());
//					delflag=false;
//				}
//				count++;
//			}
//		}
//		return fpr;
//	}
	/**
	 * @author caoyinglong
	 *入账
	 */
	@RequestMapping(method=RequestMethod.GET,value="accountEnter")
	public  void accountEnter(){
		String date=DateUtils.formatDate(new Date(), "yyyy-MM-dd");
		AccountEnterRequest car =acc4FinancialPlatformServer.getAccountEnter(date);
		if(car ==null){
			return ;
		}
		String seqNo =OutInterfaceUtils.makeSeqNo();
		String mchn = Global.getConfig("FPMchn");
		car.setMchn(mchn);
		car.setSeq_no(seqNo);
		car.setTrade_type("11099001");
		car.setSignature(OutInterfaceUtils.signatureMaker(mchn, seqNo, car.getTrade_type(), Global.getConfig("mchnKey")));
		try {
			String json = JsonTransferUtils.bean2Json(car);
			logger.info(json);
			String response = OutInterfaceUtils.load(Global.getConfig("FPAccountEnter"), json);
			logger.info(response);
			FinancialPlatformResponse fpr =JsonTransferUtils.json2Bean(response, FinancialPlatformResponse.class);
			String msg =fpr.getResp_msg();
		 if(!"0000".equals(fpr.getResp_code())&&!"00000000".equals(fpr.getResp_code())){
				acc4FinancialPlatformServer.updateAccountEnter(car.getEnter_account());
			}else{
				logger.error(msg);
			}
		} catch (Exception e) {
			logger.error(e.toString());
		}
	}
	public SendGETResponse sendGET(SendGETRequest sendGETRequest,String flag) {
		String json="";
		String response=null;
		String logName="";
		if("0".equals(flag)) {
			logName="足额代偿通知冠E通";
		}
		if("1".equals(flag)){
			logName = "正常还款通知冠E通";
		}
		if("2".equals(flag)){
			logName = "贴息还款通知冠E通";
		}
		SendGETResponse sendGETResponse = new SendGETResponse();
		try {
			json = JsonTransferUtils.bean2Json(sendGETRequest);
			response = OutInterfaceUtils.load(Global.getConfig("SendGETDC"), json);
			sendGETResponse =JsonTransferUtils.json2Bean(response, SendGETResponse.class);
			interfaceInfoService.save(new InterfaceInfo(sendGETRequest.getContractNo(), logName, sendGETResponse.getMsg()+"返回信息"+response, new Date(),"发送信息"+json));
		} catch (Exception e) {
			e.printStackTrace();
			interfaceInfoService.save(new InterfaceInfo(sendGETRequest.getContractNo(), logName, sendGETResponse.getMsg()+"返回信息"+response, new Date(),"发送信息"+json));
		}
		return sendGETResponse;
	}
}
