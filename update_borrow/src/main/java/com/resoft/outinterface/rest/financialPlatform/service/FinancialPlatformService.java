package com.resoft.outinterface.rest.financialPlatform.service;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.outinterface.rest.financialPlatform.dao.FinancialPlatformDao;
import com.resoft.outinterface.rest.financialPlatform.entry.AbortiveTenderRequest;
import com.resoft.outinterface.rest.financialPlatform.entry.AbortiveTenderResponse;
import com.resoft.outinterface.rest.financialPlatform.entry.BorrowerDepositRequest;
import com.resoft.outinterface.rest.financialPlatform.entry.BorrowerDespositResponse;
import com.resoft.outinterface.rest.financialPlatform.entry.BorrowerLoanRequest;
import com.resoft.outinterface.rest.financialPlatform.entry.BorrowerLoanResponse;
import com.resoft.outinterface.rest.financialPlatform.entry.CreateAccountRequest;
import com.resoft.outinterface.rest.financialPlatform.entry.FeeList;
import com.resoft.outinterface.rest.financialPlatform.entry.MortgageeDepositRequest;
import com.resoft.outinterface.rest.financialPlatform.entry.MortgageeDepositResponse;
import com.resoft.outinterface.rest.financialPlatform.entry.RepaymentWithhodingTempEntry;
import com.resoft.outinterface.rest.financialPlatform.entry.RepaymentWithholdingList;
import com.resoft.outinterface.rest.financialPlatform.entry.RepaymentWithholdingResponse;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;


/**
 * 资金平台接口Service
 * @author caoyinglong
 * @version 2016-03-23
 */
@Service
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class FinancialPlatformService{
	@Autowired
	private FinancialPlatformDao financialPlatformDao;
	public CreateAccountRequest getBorrowerByApplyNo(String applyNo)  {
		CreateAccountRequest car  =financialPlatformDao.getBorrowerByApplyNo(applyNo);
		return car;
	}
	public CreateAccountRequest getMortgageeByApplyNo(String idNum)  {
		CreateAccountRequest car  =financialPlatformDao.getMortgageeByApplyNo(idNum);
		return car;
	}
	public BorrowerLoanRequest getBorrowerLoan(String applyNo)  {
		BorrowerLoanRequest car  =financialPlatformDao.getBorrowerLoan(applyNo);
		if(car==null){
			return null;
		}
		List<FeeList> lfl = financialPlatformDao.getBorrowerLoanFeeList(applyNo);
		car.setFee_list(lfl);
		return car;
	}
	public String getApplyNoByContractNo(String contractNo){
		String applyNo=financialPlatformDao.getApplyNoByContractNo(contractNo);
		return applyNo;
	}
	public String getProductTypeByContractNo(String contractNo){
		String applyNo=financialPlatformDao.getProductTypeByContractNo(contractNo);
		return applyNo;
	}
	public BorrowerDepositRequest getBorrowerDeposit(String applyNo){
		BorrowerDepositRequest bdr = financialPlatformDao.getBorrowerDeposit(applyNo);
		return bdr;
	}
	public MortgageeDepositRequest getMortgageeDeposit(String applyNo){
		MortgageeDepositRequest bdr = financialPlatformDao.getMortgageeDeposit(applyNo);
		return bdr;
	}
	public AbortiveTenderRequest getAbortiveTender(String applyNo){
		AbortiveTenderRequest bdr = financialPlatformDao.getAbortiveTender(applyNo);
		if(bdr==null){
			return null;
		}
		List<FeeList> lfl = financialPlatformDao.getBorrowerLoanFeeList(applyNo);
		bdr.setFee_list(lfl);
		return bdr;
	}

	//借款人放款回盘操作
	@Transactional(value = "CRE", readOnly = false)
	public void updateBorrowerLoanSuc(BorrowerLoanResponse obj){
		financialPlatformDao.borrowerLoanUpdateContract(obj);
		financialPlatformDao.borrowerLoanUpdateApply(obj);
		financialPlatformDao.borrowerLoanInterfaceIn(obj);
	}
	@Transactional(value = "CRE", readOnly = false)
	public void updateBorrowerLoanFai(String contractNo){
		financialPlatformDao.borrowerLoanUpdateApplyFai(contractNo);
	}
	//点击借款人放款，申请发出
	@Transactional(value = "CRE", readOnly = false)
	public void borrowerLoanUpApply(String contractNo){
		financialPlatformDao.borrowerLoanUpApply(contractNo);
	}
	//借款人提现回盘操作
	@Transactional(value = "CRE", readOnly = false)
	public void updateBorrowerDepositSuc(BorrowerDespositResponse obj){
		financialPlatformDao.borrowerDepositUpdateApply(obj);
		financialPlatformDao.borrowerDepositInterfaceIn(obj);
	}
	@Transactional(value = "CRE", readOnly = false)
	public void updateBorrowerDepositFai(String contractNo){
		financialPlatformDao.borrowerDepositUpdateApplyFai(contractNo);
	}
	@Transactional(value = "CRE", readOnly = false)
	public void mortgageeDepositInterfaceIn(MortgageeDepositResponse obj){
		financialPlatformDao.mortgageeDepositInterfaceIn(obj);
	}
	//点击借款人提现，申请发出
	@Transactional(value = "CRE", readOnly = false)
	public void borrowerDepositUpApply(String contractNo){
		financialPlatformDao.borrowerDepositUpApply(contractNo);
	}
	//点击流标
	@Transactional(value = "CRE", readOnly = false)
	public void abortiveTenderUpApply(String contractNo){
		financialPlatformDao.abortiveTenderUpApply(contractNo);
	}
	@Transactional(value = "CRE", readOnly = false)
	public void abortiveTenderInsert(AbortiveTenderResponse atr){
		financialPlatformDao.abortiveTenderInsert(atr);
	}

	@Transactional(value = "CRE", readOnly = false)
	//repayment withhoding interface response insert into interface response accept table
	public void repaymentWithhodingInterfaceIn(RepaymentWithholdingResponse rwr){
		
		List<RepaymentWithholdingList> lrwl = rwr.getRepay_list();
		Iterator<RepaymentWithholdingList> it= lrwl.iterator();
		List<RepaymentWithhodingTempEntry> lrwte = new ArrayList<RepaymentWithhodingTempEntry>(); 
		while(it.hasNext()){
			RepaymentWithholdingList rwl = it.next();
			RepaymentWithhodingTempEntry rwte = new RepaymentWithhodingTempEntry();
			rwte.setRwl(rwl);
			rwte.setMchn(rwr.getMchn());
			rwte.setSeqNo(rwr.getSeq_no());
			rwte.setSignature(rwr.getSignature());
			rwte.setTradeType(rwr.getTrade_type());
			lrwte.add(rwte);
		}
		financialPlatformDao.repaymentWithhodingInterfaceIn(lrwte);
	}
	@Transactional(value = "CRE", readOnly = false)
	public void createAccNo2ContractNo(String  applyNo,String accNo){
		financialPlatformDao.createAccNo2ContractNo(applyNo,accNo);
	}
	@Transactional(value = "CRE", readOnly = false)
	public void createAccNo2Mortgagee(String  idNum,String accNo){
		financialPlatformDao.createAccNo2Mortgagee(idNum,accNo);
	}
	public BigDecimal getContractInterest(String applyNo){
		return financialPlatformDao.getContractInterest(applyNo);
	}
}