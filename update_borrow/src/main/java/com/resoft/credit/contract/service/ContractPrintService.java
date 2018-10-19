package com.resoft.credit.contract.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.contract.dao.ContractPrintDao;
import com.resoft.credit.contract.entity.Contract;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;

/**
 * 合同打印Service
 * 
 * @author wuxi01
 * @version 2016-04-29
 */
@Service("contractPrintService")
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class ContractPrintService extends CrudService<ContractPrintDao, Contract> {

	public Contract get(String id) {
		return super.get(id);
	}

	public List<Contract> findList(Contract contract) {
		return super.findList(contract);
	}

	public Page<Contract> findPage(Page<Contract> page, Contract contract) {
		return super.findPage(page, contract);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void save(Contract contract) {
		super.save(contract);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void delete(Contract contract) {
		super.delete(contract);
	}
	
	@Transactional(value = "CRE", readOnly = true)
	public Map<String, Object> findMarginInfo(String contractNo) {
		return super.dao.findMarginInfo(contractNo);
	};

	public List<Map<String, Object>> findGurrantorInfo(String applyNo){
		return super.dao.findGurrantorInfo(applyNo);
	};

	public Map<String, Object> findContractBaseInfo(String contractNo){
		return super.dao.findContractBaseInfo(contractNo);
	};

	public Map<String,Object> findLoanCustInfo(String applyNo){
		return super.dao.findLoanCustInfo(applyNo);
	}
	
	public List<Map<String, Object>> findUnionCustInfo(String applyNo){
		return super.dao.findUnionCustInfo(applyNo);
	};
	
	public Map<String, Object> findCustInfo(String applyNo){
		return super.dao.findCustInfo(applyNo);
	};

	public List<Map<String, Object>> findMortgageCarInfo(String applyNo){
		return super.dao.findMortgageCarInfo(applyNo);
	};

	public List<Map<String, Object>> findCustMortgageHouseInfo(String applyNo){
		return super.dao.findCustMortgageHouseInfo(applyNo);
	};

	public List<Map<String, Object>> findComMortgageHouseInfo(String applyNo){
		return super.dao.findComMortgageHouseInfo(applyNo);
	};

	public List<Map<String, Object>> findContractInfo(String contractNo){
		return super.dao.findContractInfo(contractNo);
	};

	public List<Map<String, Object>> findRepayPlanInfo(String contractNo){
		return super.dao.findRepayPlanInfo(contractNo);
	};
	
	public List<Map<String, Object>> findRepayPlanInfoUnion(String contractNo){
		return super.dao.findRepayPlanInfoUnion(contractNo);
	}
	/**
	 * by wpy
	 * @param applyNo
	 * @return
	 */
	public List<Map<String, Object>> findRepayPlanInfoUnionNew(String contractNo){
		return super.dao.findRepayPlanInfoUnionNew(contractNo);
	}
	public List<Map<String, Object>> findMainCustInfo(String applyNo){
		return super.dao.findMainCustInfo(applyNo);
	};

	public Map<String, Object> findApplyInfo(String applyNo){
		return super.dao.findApplyInfo(applyNo);
	};

	public Map<String, Object> findRepayPlanSum(String contractNo){
		return super.dao.findRepayPlanSum(contractNo);
	};
	
	public Map<String, Object> findRepayPlanSumUnion(String contractNo){
		return super.dao.findRepayPlanSumUnion(contractNo);
	}

	public Map<String, Object> findLoanInfo(String applyNo){
		return super.dao.findLoanInfo(applyNo);
	};

	public Map<String, Object> findEarlyRepay(String contractNo){
		return super.dao.findEarlyRepay(contractNo);
	};

	public Map<String, Object> findMainCustPhone(String applyNo){
		return super.dao.findMainCustPhone(applyNo);
	}

	public List<Map<String, Object>> findCreContractInfo(String applyNo){
		return super.dao.findCreContractInfo(applyNo);
	}
	
	public String findAddres(String id) {
		return super.dao.findAddres(id);
	}


}