package com.resoft.accounting.applyAdvanceRepay.dao;

import java.util.List;
import java.util.Map;

import com.resoft.accounting.applyAdvanceRepay.entity.ApplyAdvanceRepay;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 提前还款DAO接口
 * 
 * @author yanwanmei
 * @version 2016-03-03
 */
@MyBatisDao
public interface ApplyAdvanceRepayDao extends CrudDao<ApplyAdvanceRepay> {
	/**
	 * 提前还款费用（一个月利息）
	 */
	String getAdvanceRepayForOneMonth(ApplyAdvanceRepay advanceRepay);

	/**
	 * 提前还款费用（剩余应还5%）
	 */
	String getAdvanceRepayForThreePercents(ApplyAdvanceRepay advanceRepay);
	
	/**
	 * 获取提前还款的最小期数
	 */
	String getMinPeriodNumByContractNoAndDeductDate(Map<String, Object> params );

	/**
	 * 剩余应还
	 */
	String remainRepayPlan(ApplyAdvanceRepay advanceRepay);

	
	/**
	 * 根据合同号和申请状态查询提前还款信息
	 */
	List<ApplyAdvanceRepay> getApplyAdvanceRepayByContractNoAndstatus(Map<String, Object> params);
	
	/**
	 * 根据合同号和流程id查询提前还款信息
	 */
	List<ApplyAdvanceRepay> getApplyAdvanceRepayByContractNoAndProcInsId(Map<String, Object> params);
	
}