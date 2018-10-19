package com.resoft.outinterface.rest.GQget.client.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.accounting.accContract.AccContract;
import com.resoft.credit.CreGedBorrowStatus.service.CreGedBorrowStatusService;
import com.resoft.credit.applyRegister.entity.ApplyRegister;
import com.resoft.credit.applyRegister.service.ApplyRegisterService;
import com.resoft.credit.checkApproveUnion.entity.CheckApproveUnion;
import com.resoft.credit.checkApproveUnion.service.CheckApproveUnionService;
import com.resoft.outinterface.rest.GQget.client.dao.GQGetClientDao;
import com.resoft.outinterface.rest.GQget.client.entry.request.abortiveTender.AbortiveBidDataRequest;
import com.resoft.outinterface.rest.GQget.client.entry.request.bidWithdrawSuccess.WithdrawSuccessBidDataRequest;
import com.resoft.outinterface.rest.GQget.client.entry.request.issuingTender.BaseData;
import com.resoft.outinterface.rest.GQget.client.entry.request.issuingTender.BidRepayment;
import com.resoft.outinterface.rest.GQget.client.entry.request.issuingTender.CreditAdjunt;
import com.resoft.outinterface.rest.GQget.client.entry.request.issuingTender.CreditAppliance;
import com.resoft.outinterface.rest.GQget.client.entry.request.issuingTender.CreditAutomobile;
import com.resoft.outinterface.rest.GQget.client.entry.request.issuingTender.CreditCompany;
import com.resoft.outinterface.rest.GQget.client.entry.request.issuingTender.CreditHouse;
import com.resoft.outinterface.rest.GQget.client.entry.request.issuingTender.CreditInfo;
import com.resoft.outinterface.rest.GQget.client.entry.request.issuingTender.CreditOther;
import com.resoft.outinterface.rest.GQget.client.entry.request.issuingTender.CreditSurety;
import com.resoft.outinterface.rest.GQget.client.entry.request.issuingTender.HypothecariusCustInfo;
import com.resoft.outinterface.rest.GQget.client.entry.request.issuingTender.LoanCompany;
import com.resoft.outinterface.rest.GQget.client.entry.request.issuingTender.LoanCustInfo;
import com.resoft.outinterface.rest.GQget.client.entry.request.issuingTender.MiddleCustInfo;
import com.resoft.outinterface.rest.GQget.client.entry.request.repaymentWithdraw.RepaymentBidDataRequest;
import com.resoft.outinterface.rest.GQget.server.entry.request.bidWithdraw.WithdrawBidDataRequest;
import com.resoft.outinterface.rest.ged.GedClient;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.utils.StringUtils;

@Service(value = "GQGetClientService")
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = false)
public class GQGetClientService {
	private static final Logger logger=LoggerFactory.getLogger(GQGetClientService.class);
	@Autowired
	private GQGetClientDao gqGetClientDao;
	@Autowired
	private ApplyRegisterService applyRegisterService;
	@Autowired	
	private CreGedBorrowStatusService creGedBorrowStatusService;
	@Autowired
	private CheckApproveUnionService checkApproveUnionService;

	public AbortiveBidDataRequest getAbortiveBidByApplyNo(String applyNo) {
		AbortiveBidDataRequest abortiveBidDataRequest = gqGetClientDao.getAbortiveBidByApplyNo(applyNo);
		return abortiveBidDataRequest;
	}

	public String getFilialeNameByApplyNo(String applyNo) {
		String abortiveBidDataRequest = gqGetClientDao.getFilialeNameByApplyNo(applyNo);
		return abortiveBidDataRequest;
	}

	public List<CreditAdjunt> getCreditAdjuntByApplyNo(String applyNo) {
		List<CreditAdjunt> creditAdjunt = gqGetClientDao.getCreditAdjuntByApplyNo(applyNo);
		return creditAdjunt;
	}

	public WithdrawSuccessBidDataRequest getWithdrawSuccessBidDataByApplyNo(String applyNo) {
		WithdrawSuccessBidDataRequest withdrawSuccessBidDataRequest = gqGetClientDao.getWithdrawSuccessBidDataByApplyNo(applyNo);
		return withdrawSuccessBidDataRequest;
	}

	public RepaymentBidDataRequest getRepaymentBidDataByApplyNo(String applyNo) {
		RepaymentBidDataRequest repaymentBidDataRequest = gqGetClientDao.getRepaymentBidDataByApplyNo(applyNo);
		return repaymentBidDataRequest;
	}

	public List<BidRepayment> getBidPaymentByApplyNo(String applyNo) {
		List<BidRepayment> bidRepaymentList = gqGetClientDao.getBidPaymentByApplyNo(applyNo);
		return bidRepaymentList;
	}

	public List<BidRepayment> getBidPaymentByApplyNoUnion(String approveId) {
		List<BidRepayment> bidRepaymentList = gqGetClientDao.getBidPaymentByApplyNoUnion(approveId);
		return bidRepaymentList;
	}

	public List<CreditSurety> getCreditSuretyByApplyNo(String applyNo) {
		List<CreditSurety> creditSuretyList = gqGetClientDao.getCreditSuretyByApplyNo(applyNo);
		return creditSuretyList;
	}

	public List<CreditSurety> getCreditSuretyByApplyNoUnion(String applyNo, String custId) {
		List<CreditSurety> creditSuretyList = gqGetClientDao.getCreditSuretyByApplyNoUnion(applyNo, custId);
		return creditSuretyList;
	}

	public List<CreditCompany> getCreditCompanyByApplyNo(String applyNo) {
		List<CreditCompany> creditCompanyList = gqGetClientDao.getCreditCompanyByApplyNo(applyNo);
		return creditCompanyList;
	}

	public List<CreditCompany> getCreditCompanyByApplyNoUnion(String approveId) {
		List<CreditCompany> creditCompanyList = gqGetClientDao.getCreditCompanyByApplyNoUnion(approveId);
		return creditCompanyList;
	}

	public List<CreditAppliance> getCreditApplianceByApplyNo(String applyNo) {
		List<CreditAppliance> creditApplianceList = gqGetClientDao.getCreditApplianceByApplyNo(applyNo);
		return creditApplianceList;
	}

	public List<CreditAppliance> getCreditApplianceByApplyNoUnion(String approveId) {
		List<CreditAppliance> creditApplianceList = gqGetClientDao.getCreditApplianceByApplyNoUnion(approveId);
		return creditApplianceList;
	}

	public List<CreditHouse> getCreditHouseByApplyNo(String applyNo) {
		List<CreditHouse> creditHouseList = gqGetClientDao.getCreditHouseByApplyNo(applyNo);
		return creditHouseList;
	}

	public List<CreditHouse> getAssetHouseByApplyNo(String applyNo) {
		List<CreditHouse> creditHouseList = gqGetClientDao.getAssetHouseByApplyNo(applyNo);
		return creditHouseList;
	}

	public List<CreditHouse> getAssetHouseByApplyNoUnion(String approveId) {
		List<CreditHouse> creditHouseList = gqGetClientDao.getAssetHouseByApplyNoUnion(approveId);
		return creditHouseList;
	}

	public List<CreditAutomobile> getCreditAutomobileByApplyNo(String applyNo) {
		List<CreditAutomobile> creditAutomobileList = gqGetClientDao.getCreditAutomobileByApplyNo(applyNo);
		return creditAutomobileList;
	}

	public List<CreditAutomobile> getAssetAutomobileByApplyNo(String applyNo) {
		List<CreditAutomobile> creditAutomobileList = gqGetClientDao.getAssetAutomobileByApplyNo(applyNo);
		return creditAutomobileList;
	}

	public List<CreditAutomobile> getAssetAutomobileByApplyNoUnion(String approveId) {
		List<CreditAutomobile> creditAutomobileList = gqGetClientDao.getAssetAutomobileByApplyNoUnion(approveId);
		return creditAutomobileList;
	}

	public CreditInfo getCreditInfoByApplyNo(String applyNo, String propertyType) {
		CreditInfo creditInfo = gqGetClientDao.getCreditInfoByApplyNo(applyNo, propertyType);
		return creditInfo;
	}

	public CreditInfo getCreditInfoByApplyNoUnion(String approveId, String propertyType) {
		CreditInfo creditInfo = gqGetClientDao.getCreditInfoByApplyNoUnion(approveId, propertyType);
		return creditInfo;
	}

	public BaseData getBaseDataByApplyNo(String applyNo, String approveId) {
		BaseData baseData = gqGetClientDao.getBaseDataByApplyNo(applyNo, approveId);
		return baseData;
	}

	public LoanCustInfo getLoanCustInfoByApplyNo(String applyNo, String approveId) {
		LoanCustInfo loanCustInfo = gqGetClientDao.getLoanCustInfoByApplyNo(applyNo, approveId);
		return loanCustInfo;
	}

	public LoanCustInfo getLoanCustInfoByApplyNoUnion(String applyNo, String approveId) {
		LoanCustInfo loanCustInfo = gqGetClientDao.getLoanCustInfoByApplyNoUnion(applyNo, approveId);
		return loanCustInfo;
	}
	
	public HypothecariusCustInfo getHypothecariusCustInfoByApplyNo(String applyNo, String approveId) {
		HypothecariusCustInfo hypothecariusCustInfo = gqGetClientDao.getHypothecariusCustInfoByApplyNo(applyNo, approveId);
		return hypothecariusCustInfo;
	}

	public MiddleCustInfo getMiddleCustInfoByApplyNo(String applyNo, String approveId) {
		return gqGetClientDao.getMiddleCustInfoByApplyNo(applyNo, approveId);
	}
	/**
	 * 冠e通提现回盘后更新cre合同表相关状态及资金平台账号
	 * @param wbdr
	 */
	@Transactional(value = "CRE", readOnly = false)
	public void updateApplyLoanStatus(WithdrawBidDataRequest wbdr) {
		String contractNo = wbdr.getContract_no();
		String depositStatus = wbdr.getDeposit_status();
		String depositAmt = wbdr.getDeposit_amt();
		String accNo = wbdr.getAcc_no();
		gqGetClientDao.updateApplyLoanStatus(contractNo, depositStatus, depositAmt);
		gqGetClientDao.updateContract(contractNo, accNo);
	}

	/**
	 * 冠e通提现回盘后更新cre合同表相关状态及资金平台账号
	 * @param wbdr
	 */
	@Transactional(value = "CRE", readOnly = false)
	public void updateApplyLoanStatusWithdraw(WithdrawBidDataRequest wbdr,AccContract contract) {
		String contractNo = wbdr.getContract_no();
		String depositStatus = wbdr.getDeposit_status();
		String depositAmt = wbdr.getDeposit_amt();
		String accNo = wbdr.getAcc_no();
		logger.info("回盘处理入参：contractNo＝" + contractNo
				+ "&&depositStatus=" + depositStatus+"&depositAmt=" 
				+ depositAmt +"&accNo=" + accNo);
		if(applyRegisterService.findGEDByContractNo(contractNo)){
			if("0".equals(depositStatus)){
				gedPushRepaymentPlanAndApplyStatus(contract.getApplyNo(),contract,GedClient.ged_fkcg,null);
				gqGetClientDao.updateApplyLoanStatusWithdraw(contractNo, "27", depositAmt);
			}else{
				gedPushRepaymentPlanAndApplyStatus(contract.getApplyNo(),contract,GedClient.ged_lb,null);
				gqGetClientDao.updateApplyLoanStatus(contractNo, depositStatus, depositAmt);
			}
		}else{
			if("0".equals(depositStatus)){
				gedPushRepaymentPlanAndApplyStatus(contract.getApplyNo(),contract,GedClient.ged_fkcg,"1");
			}
			gqGetClientDao.updateApplyLoanStatus(contractNo, depositStatus, depositAmt);
		}
		gqGetClientDao.updateContract(contractNo, accNo);
	}
	
	@Transactional(value = "CRE", readOnly = false)
	public void failSendUpdate(String applyNo,String contractNo) {
		gqGetClientDao.failSendUpdate(applyNo,contractNo);
	}

	public int getJointcreditCount(String applyNo) {
		return gqGetClientDao.getJointcreditCount(applyNo);
	}

	public List<CreditOther> getCreditOtherByApplyNoAndApproId(Map<String, Object> params) {
		return gqGetClientDao.getCreditOtherByApplyNoAndApproId(params);
	}

	public List<CreditOther> getCreditOtherByApplyNo(String params) {
		return gqGetClientDao.getCreditOtherByApplyNo(params);
	}

	public LoanCompany getLoanCompanyByApplyNo(String params) {
		return gqGetClientDao.getLoanCompanyByApplyNo(params);
	}

	public LoanCompany getLoanCompanyByApproId(String params) {
		return gqGetClientDao.getLoanCompanyByApproId(params);
	}
	
	/**
	 * 冠e贷还款计划调用接口
	 * @param applyNo 申请编号/订单号
	 * @param repayPlanList 还款计划
	 */
	private void gedPushRepaymentPlanAndApplyStatus(String applyNo,AccContract contract,Integer status,String underFlag){
		//判断是否联合授信	
		ApplyRegister ar = applyRegisterService.getApplyRegisterByApplyNo(applyNo);
		if("1".equals(underFlag) || (ar != null && !(ar.getProcDefKey()!=null && ar.getProcDefKey().contains("union")))){
			creGedBorrowStatusService.saveGedBorrowStatusByApplyNo(applyNo,null ,status,0,contract);
		}else {
			CheckApproveUnion checkApproveUnion = checkApproveUnionService.getByApplyNoAndContract(contract.getContractNo(),applyNo);
			creGedBorrowStatusService.saveGedBorrowStatusByApplyNo(applyNo,checkApproveUnion.getId() ,status,1,contract);
		}
	}
	
	public LoanCompany getCustInfoAndCreditByApplyNO(String params){
		return gqGetClientDao.getCustInfoAndCreditByApplyNO(params);
	}

	public List<BidRepayment> getUnderBidPaymentByApplyNo(String applyNo) {
		return gqGetClientDao.getUnderBidPaymentByApplyNo(applyNo);
	}

	public LoanCustInfo getUnderCustInfoByApplyNo(String applyNo) {
		LoanCustInfo loanCustInfo = gqGetClientDao.getUnderCustInfoByApplyNo(applyNo);
		return loanCustInfo;
	}

	public LoanCompany getUnderCompanyByApplyNo(String applyNo) {
		return gqGetClientDao.getUnderCompanyByApplyNo(applyNo);
	}

	public CreditInfo getUnderCreditInfoByApplyNo(String applyNo) {
		return gqGetClientDao.getUnderCreditInfoByApplyNo(applyNo);
	}

	public BaseData getUnderBaseDataByApplyNo(String applyNo,String defTaskKey) {
		return gqGetClientDao.getUnderBaseDataByApplyNo(applyNo,defTaskKey);
	}


	public List<CreditHouse> getUnderCreditHouseByApplyNo(String applyNo) {
		return gqGetClientDao.getUnderCreditHouseByApplyNo(applyNo);
	}

	public List<CreditAutomobile> getUnderCreditAutomobileByApplyNo(String applyNo) {
		return gqGetClientDao.getUnderCreditAutomobileByApplyNo(applyNo);
	}

	public List<CreditOther> getUnderCreditOtherByApplyNo(String applyNo) {
		return gqGetClientDao.getUnderCreditOtherByApplyNo(applyNo);
	}
	public LoanCustInfo getComLoanCustInfoByApplyNoUnion(String applyNo, String approveId) {
		return gqGetClientDao.getComLoanCustInfoByApplyNoUnion(applyNo,approveId);
	}

	public LoanCustInfo getComLoanCustInfoByApplyNo(String applyNo, String approveId) {
		LoanCustInfo loanCustInfo = gqGetClientDao.getComLoanCustInfoByApplyNo(applyNo, approveId);
		return loanCustInfo;
	}
}