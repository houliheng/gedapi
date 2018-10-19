package com.resoft.outinterface.rest.GQget.client.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

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
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 资金平台接口
 * @author caoyinglong
 * @version 2016-03-14
 */
@MyBatisDao
public interface GQGetClientDao {
	public AbortiveBidDataRequest getAbortiveBidByApplyNo(String applyNo);
	public WithdrawSuccessBidDataRequest getWithdrawSuccessBidDataByApplyNo(String applyNo);
	public RepaymentBidDataRequest getRepaymentBidDataByApplyNo(String applyNo);
	public List<BidRepayment> getBidPaymentByApplyNo(String applyNo);
	public List<BidRepayment> getBidPaymentByApplyNoUnion(String approveId);
	public List<CreditSurety> getCreditSuretyByApplyNo(String applyNo);
	public List<CreditSurety> getCreditSuretyByApplyNoUnion(String applyNo, String custId);
	public List<CreditCompany> getCreditCompanyByApplyNo(String applyNo);
	public List<CreditCompany> getCreditCompanyByApplyNoUnion(String approveId);
	public List<CreditAppliance> getCreditApplianceByApplyNo(String applyNo);
	public List<CreditAppliance> getCreditApplianceByApplyNoUnion(String approveId);
	public List<CreditHouse> getCreditHouseByApplyNo(String applyNo);
	public List<CreditHouse> getAssetHouseByApplyNo(String applyNo);     //信用标房产
	public List<CreditHouse> getAssetHouseByApplyNoUnion(String approveId);
	public List<CreditAutomobile> getCreditAutomobileByApplyNo(String applyNo);
	public List<CreditAutomobile> getAssetAutomobileByApplyNo(String applyNo);  //信用标车产
	public List<CreditAutomobile> getAssetAutomobileByApplyNoUnion(String approveId);  //信用标车产
	public CreditInfo getCreditInfoByApplyNo(String applyNo,String propertyType);
	public CreditInfo getCreditInfoByApplyNoUnion(String approveId,String propertyType);
	public List<CreditAdjunt> getCreditAdjuntByApplyNo(String applyNo);
	public BaseData getBaseDataByApplyNo(String applyNo, String approveId);
	public LoanCustInfo getLoanCustInfoByApplyNo(String applyNo, String approveId);
	public LoanCustInfo getLoanCustInfoByApplyNoUnion(@Param(value="applyNo") String applyNo, @Param(value="approId") String approveId);
	public HypothecariusCustInfo getHypothecariusCustInfoByApplyNo(String applyNo, String approveId);
	public MiddleCustInfo getMiddleCustInfoByApplyNo(String applyNo, String approveId);
	public void updateApplyLoanStatus(String contractNo,String status,String amount);
	public void updateApplyLoanStatusWithdraw(String contractNo,String status,String amount);
	public void updateContract(String contractNo,String accNo);
	public String getFilialeNameByApplyNo(String applyNo);
	public void failSendUpdate(@Param(value = "applyNo") String applyNo,@Param(value = "contractNo") String contractNo);
	public int getJointcreditCount(String applyNo);
	public List<CreditOther> getCreditOtherByApplyNoAndApproId(Map<String, Object> params);
	public List<CreditOther> getCreditOtherByApplyNo(String applyNo);
	public LoanCompany getLoanCompanyByApplyNo(String applyNo);
	public LoanCompany getLoanCompanyByApproId(String approId);
	public LoanCompany getCustInfoAndCreditByApplyNO(String applyNO);

    LoanCustInfo getComLoanCustInfoByApplyNoUnion(@Param(value="applyNo")String applyNo, @Param(value="approveId")String approveId);

	LoanCustInfo getComLoanCustInfoByApplyNo(@Param(value="applyNo")String applyNo, @Param(value="approveId")String approveId);

	List<BidRepayment> getUnderBidPaymentByApplyNo(@Param(value = "applyNo") String applyNo);

	LoanCustInfo getUnderCustInfoByApplyNo(@Param(value = "applyNo") String applyNo);

	LoanCompany getUnderCompanyByApplyNo(String applyNo);

    CreditInfo getUnderCreditInfoByApplyNo(@Param(value = "applyNo")String applyNo);

	BaseData getUnderBaseDataByApplyNo(@Param(value = "applyNo")String applyNo,@Param(value = "taskDefKey")String taskDefKey);

	List<CreditHouse> getUnderCreditHouseByApplyNo(@Param(value = "applyNo")String applyNo);

	List<CreditAutomobile> getUnderCreditAutomobileByApplyNo(@Param(value = "applyNo")String applyNo);

	List<CreditOther> getUnderCreditOtherByApplyNo(@Param(value = "applyNo")String applyNo);
}