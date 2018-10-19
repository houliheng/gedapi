package com.resoft.accounting.applyPenaltyFineExempt.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.resoft.accounting.applyPenaltyFineExempt.entity.ApplyPenaltyFineExempt;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 违约金罚息减免DAO接口
 * 
 * @author yanwanmei
 * @version 2016-03-03
 */
@MyBatisDao
public interface ApplyPenaltyFineExemptDao extends CrudDao<ApplyPenaltyFineExempt> {
	/**
	 * 根据合同号和期数查询可减免金额
	 * @param applyPenaltyFineExempt
	 * @return
	 */
	Map<String, Object> findApplyPenaltyFineExempt(ApplyPenaltyFineExempt applyPenaltyFineExempt);
	
	/**
	 * 在账务调整过程中，根据合同号获取可减免金额
	 * @param applyPenaltyFineExempt
	 * @return
	 */
	Map<String, Object> findApplyPenaltyFineExemptOnStaRepayPlanStatus(ApplyPenaltyFineExempt applyPenaltyFineExempt);

	/**
	 * 新增减免申请和减免汇总
	 * 
	 * @param applyPenaltyFineExempt
	 */
	void saveResult(ApplyPenaltyFineExempt applyPenaltyFineExempt);

	/**
	 * 获取汇总表中指定合同号和期数已通过的金额
	 * 
	 * @param applyPenaltyFineExempt
	 * @return
	 */
	Map<String, BigDecimal> getOldApplyPenaltyFineExemptSumBycontractNoAndPeriodNum(ApplyPenaltyFineExempt applyPenaltyFineExempt);

	/**
	 * 获取指定合同号和期数下 正在申请中的减免申请
	 */
	ApplyPenaltyFineExempt getApplyingApplyPenaltyFineExemptByContractNoAndPeriodNum(ApplyPenaltyFineExempt applyPenaltyFineExempt);
	
	/**
	 * 获取指定合同号和期数下，分公司审核通过的的减免申请
	 */
	ApplyPenaltyFineExempt getCompanyPassApplyPenaltyFineExemptByContractNoAndPeriodNum(ApplyPenaltyFineExempt applyPenaltyFineExempt);
	
	/**
	 * 查询指定合同号下所有的正在进行中的申请
	 */
	List<ApplyPenaltyFineExempt> getApplyPenaltyFineExemptByContractNo(String contractNo);

	/**
	 * 新增减免汇总
	 */
	void saveStaPenaltyFineExempt(ApplyPenaltyFineExempt applyPenaltyFineExempt);

	/**
	 * 更新减免汇总
	 */
	void updateStaPenaltyFineExempt(ApplyPenaltyFineExempt applyPenaltyFineExempt);
	
	/**
	 * 启动流程后更新对应的流程实例ID
	 */
	public void updateProcInsIdById(ApplyPenaltyFineExempt applyPenaltyFineExempt);
	
	/**
	 * 根据合同号和期数退回减免金额
	 */
	public void deleteStaPenaltyFineExempt(ApplyPenaltyFineExempt applyPenaltyFineExempt);

	/**
	 * 根据合同号和期数修改申请状态
	 */
	public void updateStaPenaltyFineExemptApplyStatus(ApplyPenaltyFineExempt applyPenaltyFineExempt);
}