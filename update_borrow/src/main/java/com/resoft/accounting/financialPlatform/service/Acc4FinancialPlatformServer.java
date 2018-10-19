package com.resoft.accounting.financialPlatform.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.accounting.accContract.AccContract;
import com.resoft.accounting.common.utils.JsonTransferUtils;
import com.resoft.accounting.contract.dao.ContractLockDao;
import com.resoft.accounting.contract.entity.ContractLock;
import com.resoft.accounting.deductResult.entity.DeductResult;
import com.resoft.accounting.financialPlatform.dao.Acc4FinancialPlatformDao;
import com.resoft.credit.repayPlan.entity.RepayPlan;
import com.resoft.outinterface.rest.financialPlatform.entry.AccountEnterRequest;
import com.resoft.outinterface.rest.financialPlatform.entry.BankCardChangeRequest;
import com.resoft.outinterface.rest.financialPlatform.entry.BankCardChangeResponse;
import com.resoft.outinterface.rest.financialPlatform.entry.DeductApplyRequest;
import com.resoft.outinterface.rest.financialPlatform.entry.EnterAccountList;
import com.resoft.outinterface.rest.financialPlatform.entry.RefundDepositRequest;
import com.resoft.outinterface.rest.financialPlatform.entry.RepaymentWithholdingIn;
import com.resoft.outinterface.rest.financialPlatform.entry.RepaymentWithholdingList;
import com.resoft.outinterface.rest.financialPlatform.entry.RepaymentWithholdingResponse;
import com.resoft.outinterface.rest.financialPlatform.entry.SettleList;
import com.resoft.outinterface.rest.ged.entity.GedAccAccountDeduct;
import com.resoft.outinterface.rest.ged.entity.GedRepayment;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.JsonUtil;

@Service
@DbType("acc.dbType")
@Transactional(value = "ACC", readOnly = true)
public class Acc4FinancialPlatformServer {
	private static final Logger logger = LoggerFactory.getLogger(Acc4FinancialPlatformServer.class);
	@Autowired
	private Acc4FinancialPlatformDao acc4FinancialPlatformDao;
	@Autowired
	private ContractLockDao contractLockDao;
	public RefundDepositRequest getRefundDeposit(String applyNo) {
		RefundDepositRequest bdr = acc4FinancialPlatformDao.getRefundDeposit(applyNo);
		return bdr;
	}

	@Transactional(value = "ACC", readOnly = false)
	public void bankCardUpdateSuc(BankCardChangeResponse bccr) {
		acc4FinancialPlatformDao.bankCardUpdateSuc(bccr);
		acc4FinancialPlatformDao.bankCardInterfaceIn(bccr);
	}

	public void bankCardUpdateFai(BankCardChangeResponse bccr) {
		acc4FinancialPlatformDao.bankCardUpdateFai(bccr);
	}

	public BankCardChangeRequest getBankCardChange(String applyNo) {
		BankCardChangeRequest bdr = acc4FinancialPlatformDao.getBankCardChange(applyNo);
		return bdr;
	}

	public List<DeductApplyRequest> getRepaymentWithholding() {
		List<DeductApplyRequest> dar = acc4FinancialPlatformDao.getDeductApplyRequest();
		return dar;
	}

	@Transactional(value = "ACC", readOnly = false)
	public void insertDeductApplyListRequest(List<DeductApplyRequest> dar) {
		acc4FinancialPlatformDao.insertDeductApplyListRequest(dar);
		Iterator<DeductApplyRequest> it = dar.iterator();
		while (it.hasNext()) {
			DeductApplyRequest dar1 = it.next();
			this.deleteDeductApplyRequest(dar1.getRepayList().getContract_no());
		}
	}

	// 提现成功更新ACC表中的计息日
	@Transactional(value = "ACC", readOnly = false)
	public void updateAccLoanDate(String contractNo, String withDrawDate) {
		acc4FinancialPlatformDao.updateAccLoanDate(contractNo, withDrawDate);
	}

	@Transactional(value = "ACC", readOnly = false)
	// repayment withhoding interface update apply table
	public void repaymentWithhodingResultIn(RepaymentWithholdingResponse obj) {
		List<RepaymentWithholdingList> lrwl = obj.getRepay_list();
		if (lrwl.size() > 0) {
			for(RepaymentWithholdingList repaymentWithholdingList: lrwl){
				if (repaymentWithholdingList.getContract_no().contains("\\")) {
					repaymentWithholdingList.setContract_no(repaymentWithholdingList.getContract_no().replace("\\", "\\\\"));
				}
			}
		}
		Iterator<RepaymentWithholdingList> it = lrwl.iterator();
		List<RepaymentWithholdingIn> lrwi = new ArrayList<RepaymentWithholdingIn>();
		while (it.hasNext()) {
			RepaymentWithholdingIn rwi = new RepaymentWithholdingIn();
			RepaymentWithholdingList rwl = it.next();
			rwi.setMchn(obj.getMchn());
			rwi.setRespCode(obj.getResp_code());
			rwi.setRespMsg(obj.getResp_msg());
			rwi.setRwl(rwl);
			rwi.setSeqNo(obj.getSeq_no());
			rwi.setSignature(obj.getSignature());
			rwi.setTradeType(obj.getTrade_type());
			rwi.setTransferRespCode(obj.getTransfer_resp_code());
			rwi.setTransferRespMsg(obj.getTransfer_resp_msg());
			lrwi.add(rwi);
		}
		acc4FinancialPlatformDao.repaymentWithhodingResultIn(lrwi);
	}

	@Transactional(value = "ACC", readOnly = false)
	public void repaymentResultInDeduct(String seqNo) {
		acc4FinancialPlatformDao.repaymentResultInDeduct(seqNo);
	}

	@Transactional(value = "ACC", readOnly = false)
	public void insertDeductApplyRequest(DeductApplyRequest dar) {
		acc4FinancialPlatformDao.insertDeductApplyRequest(dar);
		this.deleteDeductApplyRequest(dar.getRepayList().getContract_no());
	}

	@Transactional(value = "ACC", readOnly = false)
	public void deleteDeductApplyRequest(String contractNo) {
		acc4FinancialPlatformDao.deleteDeductApplyRequest(contractNo);
	}

	public String getBreakRepayStatusByContractNo(String contractNo) {
		return acc4FinancialPlatformDao.getBreakRepayStatusByContractNo(contractNo);
	}

	public AccountEnterRequest getAccountEnter(String date) {
		AccountEnterRequest bdr = new AccountEnterRequest();
		List<EnterAccountList> leal = acc4FinancialPlatformDao.getEnterAccounList(date);
		Iterator<EnterAccountList> it = leal.iterator();
		while (it.hasNext()) {
			EnterAccountList eal = it.next();
			List<SettleList> sl = acc4FinancialPlatformDao.getSettleList(eal.getSerial_number(), eal.getPeriod());
			eal.setSettle_list(sl);
		}
		bdr.setEnter_account(leal);
		return bdr;
	}

	@Transactional(value = "ACC", readOnly = false)
	public void updateAccountEnter(List<EnterAccountList> leal) {
		Iterator<EnterAccountList> it = leal.iterator();
		while (it.hasNext()) {
			EnterAccountList eal = it.next();
			acc4FinancialPlatformDao.updateAccountEnter(eal.getSerial_number(), eal.getPeriod());
		}
	}

	/**
	 * 冠e通返回数据 插入合同表及 处理还款计划表
	 * 
	 * @param repayPlanList
	 * @param contract
	 * @return
	 */

	@Transactional(value = "ACC", readOnly = false)
	public String insertAccRepayPlanAndAccContract(List<RepayPlan> repayPlanList, AccContract contract) {
		String str = "";
		try {
			// 插入合同信息
			// -----------------------------------
			AccContract hasAccContract = findContractByContractNo(contract.getContractNo());
			logger.info("cre合同信息",JsonTransferUtils.bean2Json(contract));
			if (hasAccContract != null) {
				acc4FinancialPlatformDao.updateAccContract(contract);
			} else {
				contract.setId(IdGen.uuid());
				acc4FinancialPlatformDao.insertAccContract(contract);
			}
		} catch (Exception e) {
			str = "向acc_contract表中插入数据失败";
			logger.error("向acc_contract表中插入数据失败", e);
		}

		try {
			// 插入还款计划表,插入之前先删除
			acc4FinancialPlatformDao.deleteRepayPlanByContractNo(contract.getContractNo());
			acc4FinancialPlatformDao.insertAccRepayPlanList(repayPlanList);
		} catch (Exception e) {
			str = "插入还款计划表失败";
			logger.error("插入还款计划表失败", e);
		}
		return str;
	}

	@Transactional(value = "ACC", readOnly = false)
	public void createAccNo2ContractNo(String applyNo, String accNo) {
		acc4FinancialPlatformDao.createAccNo2ContractNo(applyNo, accNo);
	}

	/**
	 * 插入数据到合同还款执行情况统计表
	 * 
	 * @param contractNo
	 */
	@Transactional(value = "ACC", readOnly = false)
	public void insertAccStaContractStatus(String contractNo) {
		acc4FinancialPlatformDao.insertAccStaContractStatus(contractNo);
	}

	@Transactional(value = "ACC", readOnly = false)
	public void insertAccContract(AccContract accContract) {
		acc4FinancialPlatformDao.insertAccContract(accContract);
	}

	@Transactional(value = "ACC", readOnly = false)
	public void updateAccContract(AccContract accContract) {
		acc4FinancialPlatformDao.updateAccContract(accContract);
	}

	public AccContract findContractByContractNo(String contractNo) {
		return acc4FinancialPlatformDao.findContractByContractNo(contractNo);
	}

	/**
	 * 查看扣款结果表里是否有记录
	 * 
	 * @return
	 */
	public List<DeductResult> findDeductResultBySySNo(String sysSeqNo) {
		return acc4FinancialPlatformDao.findDeductResultBySySNo(sysSeqNo);
	}
	
	public Integer findOverdueContractPeriod(String contractNO,String periodNum){
		return acc4FinancialPlatformDao.findOverdueContractPeriod(contractNO,periodNum);
	}
	
	@Transactional(value = "ACC", readOnly = false)
	public void insertGedAccAccount(GedRepayment gedRepayment){
		gedRepayment.setDeductType("10180003");
		gedRepayment.setDeductId(Global.getConfig("deductCustId"));
		deductCommon(gedRepayment);
	}

	@Transactional(value = "ACC", rollbackFor = Exception.class)
	public void hbczCallBack(GedRepayment repayment) {
		deductCommon(repayment);
	}

	private void deductCommon(GedRepayment gedRepayment) {
		acc4FinancialPlatformDao.insertGedAccAccount(gedRepayment);
		ContractLock contractLock = new ContractLock();
		contractLock.setContractNo(gedRepayment.getContractNo());
		contractLock.setLockFlag("1");
		contractLockDao.saveLockInfo(contractLock);
		GedAccAccountDeduct gedAccAccountDeduct = new GedAccAccountDeduct();
		gedAccAccountDeduct.setContractNo(gedRepayment.getContractNo());
		gedAccAccountDeduct.setDeductAmount(gedRepayment.getDeductAmount().toString());
		gedAccAccountDeduct.setStreamNo(gedRepayment.getAccountNo());
		gedAccAccountDeduct.setCompeleteTime(gedAccAccountDeduct.getCompeleteTime());
		if(gedAccAccountDeduct.getCompeleteTime() == null){
			gedAccAccountDeduct.setCompeleteTime(new Date());
		}
		gedAccAccountDeduct.setDeductId(Global.getConfig("deductCustId"));
		gedAccAccountDeduct.setDeductApplyNo(gedRepayment.getDeductApplyNo());
		gedAccAccountDeduct.setSeqNo(gedRepayment.getSeqNo());
		gedAccAccountDeduct.setCapitalTerraceNo(gedRepayment.getCustId());
		acc4FinancialPlatformDao.insertGedAccAccountDeduct(gedAccAccountDeduct);
	}
	
	
	public String findContractStatusByContractNo(String contractNo){
		return acc4FinancialPlatformDao.findContractStatusByContractNo(contractNo);
	}
}
