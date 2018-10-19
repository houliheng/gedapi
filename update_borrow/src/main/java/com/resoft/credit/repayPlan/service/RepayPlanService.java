package com.resoft.credit.repayPlan.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.repayPlan.dao.RepayPlanDao;
import com.resoft.credit.repayPlan.entity.RepayPlan;
import com.resoft.outinterface.rest.ged.entity.response.GedRepayPlanResponse;
import com.resoft.outinterface.rest.newged.entity.OrderContractReqForm;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;

/**
 * 还款计划Service
 * @author wuxi01
 * @version 2016-03-01
 */
@Service("creRepayPlanService") @DbType("cre.dbType")
@Transactional(value="CRE",readOnly = true)
public class RepayPlanService extends CrudService<RepayPlanDao, RepayPlan> {

	public RepayPlan get(String id) {
		return super.get(id);
	}
	
	public List<RepayPlan> findList(RepayPlan repayPlan) {
		return super.findList(repayPlan);
	}
	
	public Page<RepayPlan> findPage(Page<RepayPlan> page, RepayPlan repayPlan) {
		return super.findPage(page, repayPlan);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void save(RepayPlan repayPlan) {
		super.save(repayPlan);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void delete(RepayPlan repayPlan) {
		super.delete(repayPlan);
	}
	
	public List<RepayPlan> getRepayPlanByApplyNo(Map<String,String> param){
		return super.dao.getRepayPlanByApplyNo(param);
	}
	
	public List<RepayPlan> getRepayPlanByApplyNoAndTaskDefKey(Map<String,String> param){
		return super.dao.getRepayPlanByApplyNoAndTaskDefKey(param);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void insertRepayPlanList(List<RepayPlan> repayPlanList) {
		super.dao.insertRepayPlanList(repayPlanList);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void deleteRepayPlanByApplyNo(Map<String,String> param) {
		super.dao.deleteRepayPlanByApplyNo(param);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void deleteRepayPlanByApplyNoAndContractNo(String applyNo) {
		super.dao.deleteRepayPlanByApplyNoAndContractNo(applyNo);
	}
	
	public List<Map<String,String>> getOverduePenalty() {
		return super.dao.getOverduePenalty();
	}

	public List<GedRepayPlanResponse> getByContractNo(String contractNo,String taskDefKey) {
		return dao.getByContractNo(contractNo,taskDefKey);
	}

	public OrderContractReqForm getRepayAndContract(String applyNo, String taskDefKey) {
		return dao.getRepayAndContract(applyNo,taskDefKey);
	}

	public List<RepayPlan> listWithContractNoList(List<String> contractNoList) {
		return dao.listWithContractNoList(contractNoList);
	}

	


    public OrderContractReqForm getUnderRepay(String applyNo, String taskDefKey) {
		return dao.getUnderRepay(applyNo,taskDefKey);
    }
}