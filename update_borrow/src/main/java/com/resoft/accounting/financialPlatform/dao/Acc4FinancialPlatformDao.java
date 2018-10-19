package com.resoft.accounting.financialPlatform.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.resoft.accounting.accContract.AccContract;
import com.resoft.accounting.deductApply.entity.DeductApply;
import com.resoft.accounting.deductResult.entity.DeductResult;
import com.resoft.credit.compenSatoryDetail.entity.CompensatoryDetail;
import com.resoft.credit.repayPlan.entity.RepayPlan;
import com.resoft.outinterface.rest.financialPlatform.entry.BankCardChangeRequest;
import com.resoft.outinterface.rest.financialPlatform.entry.BankCardChangeResponse;
import com.resoft.outinterface.rest.financialPlatform.entry.DeductApplyRequest;
import com.resoft.outinterface.rest.financialPlatform.entry.EnterAccountList;
import com.resoft.outinterface.rest.financialPlatform.entry.RefundDepositRequest;
import com.resoft.outinterface.rest.financialPlatform.entry.RepaymentWithholdingIn;
import com.resoft.outinterface.rest.financialPlatform.entry.SettleList;
import com.resoft.outinterface.rest.ged.entity.GedAccAccountDeduct;
import com.resoft.outinterface.rest.ged.entity.GedRepayment;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface Acc4FinancialPlatformDao {
	public RefundDepositRequest getRefundDeposit(String applyNo);
	public BankCardChangeRequest getBankCardChange(String applyNo);
	public List<DeductApplyRequest> getDeductApplyRequest();
	public void insertDeductApplyListRequest(List<DeductApplyRequest> dar);
	public void insertDeductApplyRequest(DeductApplyRequest dar);
	public List<EnterAccountList> getEnterAccounList(String applyNo);
	public List<SettleList> getSettleList(String deductApplyNo,String period);
	public void updateAccountEnter(String deductApplyNo,String period);
	public void deleteDeductApplyRequest(String  contractNo);
	public void bankCardInterfaceIn(BankCardChangeResponse obj);
	public void bankCardUpdateSuc(BankCardChangeResponse bccr);
	public void bankCardUpdateFai(BankCardChangeResponse bccr);
	public void repaymentWithhodingResultIn(List<RepaymentWithholdingIn> obj);
	public void repaymentResultInDeduct(String seqNo);
	public void updateAccLoanDate(String contractNo,String withDrawDate);
	public void insertAccRepayPlanList(List<RepayPlan> repayPlanList);
	public void insertAccStaContractStatus(String contractNo);
	public void insertAccContract(AccContract accContract);
	public void updateAccContract(AccContract accContract);
	public AccContract findContractByContractNo(String contractNo);
	public String getBreakRepayStatusByContractNo(String contractNo);
	public void createAccNo2ContractNo(String applyNo,String accNo);
	public void deleteRepayPlanByContractNo(String contractNo);
	public List<DeductResult> findDeductResultBySySNo(String sysSeqNo);
	public Integer findOverdueContractPeriod(@Param("contractNo") String contractNo,@Param("periodNum") String periodNum);
	public void insertGedAccAccount(GedRepayment gedRepayment);
	public void insertGedAccAccountDeduct(GedAccAccountDeduct gedAccAccountDeduct);
	public String findContractStatusByContractNo(String contractNo);
}
