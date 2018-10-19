package com.resoft.accounting.cleanContract.dao;

import org.apache.ibatis.annotations.Param;

import com.resoft.accounting.cleanContract.entity.CleanContract;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 数据处理DAO接口
 * @author wangguodong
 * @version 2017-09-19
 */
@MyBatisDao
public interface CleanContractDao extends CrudDao<CleanContract> {
	public CleanContract findCleanContractByContractNoAndPeriodNum(@Param(value="contractNo") String contractNo,@Param(value="periodNum") String periodNum );

	public void cleanAccStaPenaltyFineExempt(CleanContract cleanContract);
	public void cleanAccApplyPenaltyFineExempt(CleanContract cleanContract);
	public void cleanAccStaRepayPlanStatus(CleanContract cleanContract);
	public void cleanAccDeductApply(CleanContract cleanContract);
	public void cleanAccDeductResult(CleanContract cleanContract);
	public void cleanAccRepayDetail(CleanContract cleanContract);
	public void cleanAccOverdueDetail(CleanContract cleanContract);
	public void cleanAccStaOverdueStatus(CleanContract cleanContract);
	public void cleanAccStaOverdueStatusTmp(CleanContract cleanContract);
	public void cleanAccApplyChangeRepay(CleanContract cleanContract);
	public void cleanAccReturnDeductResult(CleanContract cleanContract);
	
}