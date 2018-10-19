package com.resoft.accounting.AccfinancialPlatform.dao;

import java.util.List;

import com.resoft.Accoutinterface.rest.financialPlatform.entry.AccBankCardChangeRequest;
import com.resoft.Accoutinterface.rest.financialPlatform.entry.AccBankCardChangeResponse;
import com.resoft.Accoutinterface.rest.financialPlatform.entry.AccDeductApplyRequest;
import com.resoft.Accoutinterface.rest.financialPlatform.entry.AccEnterAccountList;
import com.resoft.Accoutinterface.rest.financialPlatform.entry.AccRefundDepositRequest;
import com.resoft.Accoutinterface.rest.financialPlatform.entry.AccRepaymentWithholdingIn;
import com.resoft.Accoutinterface.rest.financialPlatform.entry.AccSettleList;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface AccAcc4FinancialPlatformDao {
	public AccRefundDepositRequest getRefundDeposit(String contractNo);
	public AccBankCardChangeRequest getBankCardChange(String contractNo);
	public List<AccDeductApplyRequest> getDeductApplyRequest();
	public List<AccDeductApplyRequest> getDeductApplyRequestByContractNo(String contractNo);
	public List<AccDeductApplyRequest> getRepayListByContractNo(String contractNo);
	public void insertDeductApplyListRequest(List<AccDeductApplyRequest> dar);
	public void insertDeductApplyRequest(AccDeductApplyRequest dar);
	public List<AccEnterAccountList> getEnterAccounList(String applyNo);
	public List<AccSettleList> getSettleList(String deductApplyNo,String period);
	public void updateAccountEnter(String deductApplyNo,String period);
	public void deleteDeductApplyRequest(String  contractNo);
	public void bankCardInterfaceIn(AccBankCardChangeResponse obj);
	public void bankCardUpdateSuc(AccBankCardChangeResponse bccr);
	public void bankCardUpdateFai(AccBankCardChangeResponse bccr);
	public void repaymentWithhodingResultIn(List<AccRepaymentWithholdingIn> obj);
	public void repaymentResultInDeduct(String seqNo);
	public void updateBankCardBySeqNo(String seqNo,String contractNo);
}
