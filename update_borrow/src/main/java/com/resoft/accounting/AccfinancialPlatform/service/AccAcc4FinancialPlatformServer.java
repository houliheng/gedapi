package com.resoft.accounting.AccfinancialPlatform.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.Accoutinterface.rest.financialPlatform.entry.AccAccountEnterRequest;
import com.resoft.Accoutinterface.rest.financialPlatform.entry.AccBankCardChangeRequest;
import com.resoft.Accoutinterface.rest.financialPlatform.entry.AccBankCardChangeResponse;
import com.resoft.Accoutinterface.rest.financialPlatform.entry.AccDeductApplyRequest;
import com.resoft.Accoutinterface.rest.financialPlatform.entry.AccEnterAccountList;
import com.resoft.Accoutinterface.rest.financialPlatform.entry.AccRefundDepositRequest;
import com.resoft.Accoutinterface.rest.financialPlatform.entry.AccRepaymentWithholdingIn;
import com.resoft.Accoutinterface.rest.financialPlatform.entry.AccRepaymentWithholdingList;
import com.resoft.Accoutinterface.rest.financialPlatform.entry.AccRepaymentWithholdingResponse;
import com.resoft.Accoutinterface.rest.financialPlatform.entry.AccSettleList;
import com.resoft.accounting.AccfinancialPlatform.dao.AccAcc4FinancialPlatformDao;
@Service(value="accAcc4FinancialPlatformServer")
@Transactional(value="ACC",readOnly = true)
public class AccAcc4FinancialPlatformServer {
	@Autowired
	private AccAcc4FinancialPlatformDao acc4FinancialPlatformDao;
	
	public AccRefundDepositRequest getRefundDeposit(String contractNo){
		AccRefundDepositRequest bdr = acc4FinancialPlatformDao.getRefundDeposit(contractNo);
		return bdr;
	}
	@Transactional(value="ACC",readOnly = false)
	public void bankCardUpdateSuc(AccBankCardChangeResponse bccr){
		acc4FinancialPlatformDao.bankCardUpdateSuc(bccr);
		acc4FinancialPlatformDao.bankCardInterfaceIn(bccr);
	}
	public void bankCardUpdateFai(AccBankCardChangeResponse bccr){
		acc4FinancialPlatformDao.bankCardUpdateFai(bccr);
	}
	public AccBankCardChangeRequest getBankCardChange(String contractNo){
		AccBankCardChangeRequest bdr = acc4FinancialPlatformDao.getBankCardChange(contractNo);
		return bdr;
	}
	public List<AccDeductApplyRequest> getRepaymentWithholding(){
		List<AccDeductApplyRequest> dar = acc4FinancialPlatformDao.getDeductApplyRequest();
		return dar;
	}
	public List<AccDeductApplyRequest> getDeductApplyRequestByContractNo(String contractNo){
		List<AccDeductApplyRequest> dar = acc4FinancialPlatformDao.getDeductApplyRequestByContractNo(contractNo);
		return dar;
	}
	public List<AccDeductApplyRequest> getRepayListByContractNo(String contractNo){
		List<AccDeductApplyRequest> dar = acc4FinancialPlatformDao.getRepayListByContractNo(contractNo);
		return dar;
	}
	
	public void updateBankCardBySeqNo(String seqNo,String contractNo){
		acc4FinancialPlatformDao.updateBankCardBySeqNo(seqNo,contractNo);
	}
	
	@Transactional(value="ACC",readOnly = false)
	public void insertDeductApplyListRequest(List<AccDeductApplyRequest> dar){
		acc4FinancialPlatformDao.insertDeductApplyListRequest(dar);
		Iterator<AccDeductApplyRequest> it = dar.iterator();
		while (it.hasNext()){
			AccDeductApplyRequest dar1 = it.next();
			this.deleteDeductApplyRequest(dar1.getRepayList().getContract_no());
		}
	}
	@Transactional(value = "ACC", readOnly = false)
	//repayment withhoding interface update apply table
	public void repaymentWithhodingResultIn(AccRepaymentWithholdingResponse  obj){
		List<AccRepaymentWithholdingList> lrwl =obj.getRepay_list();
		Iterator<AccRepaymentWithholdingList> it =lrwl.iterator();
		List<AccRepaymentWithholdingIn> lrwi =new ArrayList<AccRepaymentWithholdingIn>();
		while(it.hasNext()){
			AccRepaymentWithholdingIn rwi = new AccRepaymentWithholdingIn();
			AccRepaymentWithholdingList rwl = it.next();
			rwi.setMchn(obj.getMchn());
			rwi.setRespCode(obj.getResp_code());
			rwi.setRespMsg(obj.getResp_msg());
			rwi.setRwl(rwl);
			rwi.setSeqNo(obj.getSeq_no());
			rwi.setSignature(obj.getSignature());
			rwi.setTradeType(obj.getTrade_type());
			lrwi.add(rwi);
		}
		acc4FinancialPlatformDao.repaymentWithhodingResultIn(lrwi);
	}
	@Transactional(value = "ACC", readOnly = false)
	public void repaymentResultInDeduct(String  seqNo){
		acc4FinancialPlatformDao.repaymentResultInDeduct(seqNo);
	}
	@Transactional(value="ACC",readOnly = false)
	public void insertDeductApplyRequest(AccDeductApplyRequest dar){
		acc4FinancialPlatformDao.insertDeductApplyRequest(dar);
		this.deleteDeductApplyRequest(dar.getRepayList().getContract_no());
	}
	@Transactional(value="ACC",readOnly = false)
	public void deleteDeductApplyRequest(String contractNo){
		acc4FinancialPlatformDao.deleteDeductApplyRequest(contractNo);
	}
	public AccAccountEnterRequest getAccountEnter(String date){
		AccAccountEnterRequest bdr =new AccAccountEnterRequest();
		List<AccEnterAccountList> leal = acc4FinancialPlatformDao.getEnterAccounList(date);
		Iterator<AccEnterAccountList> it = leal.iterator();
		while(it.hasNext()){
			AccEnterAccountList eal =it.next();
			List<AccSettleList> sl  = acc4FinancialPlatformDao.getSettleList(eal.getSerial_number(),eal.getPeriod());
			eal.setSettle_list(sl);
		}
		bdr.setEnter_account(leal);
		return bdr;
	}
	@Transactional(value="ACC",readOnly = false)
	public void updateAccountEnter(List<AccEnterAccountList> leal){
		Iterator<AccEnterAccountList> it = leal.iterator();
		while(it.hasNext()){
			AccEnterAccountList eal = it.next();
			acc4FinancialPlatformDao.updateAccountEnter(eal.getSerial_number(),eal.getPeriod());
		}
	}
}
