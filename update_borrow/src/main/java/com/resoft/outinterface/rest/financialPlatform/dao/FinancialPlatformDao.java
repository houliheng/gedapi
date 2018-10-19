package com.resoft.outinterface.rest.financialPlatform.dao;


import java.math.BigDecimal;
import java.util.List;

import com.resoft.outinterface.rest.financialPlatform.entry.AbortiveTenderRequest;
import com.resoft.outinterface.rest.financialPlatform.entry.AbortiveTenderResponse;
import com.resoft.outinterface.rest.financialPlatform.entry.BankCardChangeRequest;
import com.resoft.outinterface.rest.financialPlatform.entry.BankCardChangeResponse;
import com.resoft.outinterface.rest.financialPlatform.entry.BorrowerDepositRequest;
import com.resoft.outinterface.rest.financialPlatform.entry.BorrowerDespositResponse;
import com.resoft.outinterface.rest.financialPlatform.entry.BorrowerLoanRequest;
import com.resoft.outinterface.rest.financialPlatform.entry.BorrowerLoanResponse;
import com.resoft.outinterface.rest.financialPlatform.entry.CreateAccountRequest;
import com.resoft.outinterface.rest.financialPlatform.entry.DeductApplyRequest;
import com.resoft.outinterface.rest.financialPlatform.entry.EnterAccountList;
import com.resoft.outinterface.rest.financialPlatform.entry.FeeList;
import com.resoft.outinterface.rest.financialPlatform.entry.MortgageeDepositRequest;
import com.resoft.outinterface.rest.financialPlatform.entry.MortgageeDepositResponse;
import com.resoft.outinterface.rest.financialPlatform.entry.RefundDepositRequest;
import com.resoft.outinterface.rest.financialPlatform.entry.RepaymentWithhodingTempEntry;
import com.resoft.outinterface.rest.financialPlatform.entry.RepaymentWithholdingIn;
import com.resoft.outinterface.rest.financialPlatform.entry.RepaymentWithholdingRequest;
import com.resoft.outinterface.rest.financialPlatform.entry.RepaymentWithholdingResponse;
import com.resoft.outinterface.rest.financialPlatform.entry.SettleList;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 资金平台接口
 * @author caoyinglong
 * @version 2016-03-14
 */
@MyBatisDao
public interface FinancialPlatformDao {
	public CreateAccountRequest getBorrowerByApplyNo(String applyNo);
	public CreateAccountRequest getMortgageeByApplyNo(String idNum);
	public BorrowerLoanRequest getBorrowerLoan(String applyNo);
	public List<FeeList>  getBorrowerLoanFeeList(String applyNo);
	public BorrowerDepositRequest getBorrowerDeposit(String applyNo);
	public MortgageeDepositRequest getMortgageeDeposit(String applyNo);
	public AbortiveTenderRequest getAbortiveTender(String applyNo);
	public RepaymentWithholdingRequest getRepaymentWithholding(String applyNo);
	public void borrowerLoanInterfaceIn(BorrowerLoanResponse obj);
	public void borrowerLoanUpdateContract(BorrowerLoanResponse obj);
	public void borrowerLoanUpdateApply(BorrowerLoanResponse obj);
	public void borrowerLoanUpdateApplyFai(String contractNo); 
	public void borrowerLoanUpApply(String contractNo); 
	public void borrowerDepositInterfaceIn(BorrowerDespositResponse obj);
	public void mortgageeDepositInterfaceIn(MortgageeDepositResponse obj);
	public void borrowerDepositUpdateContract(BorrowerDespositResponse obj);
	public void borrowerDepositUpdateApply(BorrowerDespositResponse obj);
	public void borrowerDepositUpdateApplyFai(String contractNo); 
	public void borrowerDepositUpApply(String contractNo); 
	public void abortiveTenderUpApply(String contractno);
	public void abortiveTenderInsert(AbortiveTenderResponse atr);
	public void repaymentWithhodingInterfaceIn(List<RepaymentWithhodingTempEntry> lrwte);
	public void createAccNo2ContractNo(String  applyNo,String accNo);
	public void createAccNo2Mortgagee(String  idNum,String accNo);
	public BigDecimal getContractInterest(String applyNo);
	public String getApplyNoByContractNo(String contractNo);
	public String getProductTypeByContractNo(String contractNo);
}