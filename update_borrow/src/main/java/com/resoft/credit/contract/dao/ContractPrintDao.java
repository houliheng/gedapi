package com.resoft.credit.contract.dao;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.credit.contract.entity.Contract;

/**
 * 合同打印DAO接口
 * @author wuxi01
 * @version 2016-04-29
 */
@MyBatisDao("contractPrintDao")
public interface ContractPrintDao extends CrudDao<Contract> {
	
	public Map<String, Object> findMarginInfo(String contractNo);
	
	public List<Map<String, Object>> findGurrantorInfo(String applyNo);
	
	public Map<String, Object> findContractBaseInfo(String contractNo);
	
	public Map<String, Object> findCustInfo(String applyNo);
	
	public List<Map<String, Object>> findUnionCustInfo(String applyNo);
	
	public Map<String,Object> findLoanCustInfo(String applyNo);
	
	public List<Map<String, Object>> findMortgageCarInfo(String applyNo);
	
	public List<Map<String, Object>> findCustMortgageHouseInfo(String applyNo);
	
	public List<Map<String, Object>> findComMortgageHouseInfo(String applyNo);
	
	public List<Map<String, Object>> findContractInfo(String contractNo);
	
	public List<Map<String, Object>> findRepayPlanInfo(String contractNo);
	
	public List<Map<String, Object>> findRepayPlanInfoUnion(String contractNo);
	
	public List<Map<String, Object>> findRepayPlanInfoUnionNew(String contractNo);
	
	public List<Map<String, Object>> findMainCustInfo(String applyNo);
	
	public Map<String, Object> findApplyInfo(String applyNo);
	
	public List<Map<String,Object>> findCreContractInfo(String applyNo);
	
	public Map<String, Object> findRepayPlanSum(String contractNo);
	
	public Map<String, Object> findRepayPlanSumUnion(String contractNo);
	
	public Map<String, Object> findLoanInfo(String applyNo);
	
	public Map<String, Object> findEarlyRepay(String contractNo);
	
	public Map<String, Object> findMainCustPhone(String applyNo);

	public String findAddres(String id);
}