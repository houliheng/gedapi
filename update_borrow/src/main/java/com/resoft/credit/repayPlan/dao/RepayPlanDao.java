package com.resoft.credit.repayPlan.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.resoft.credit.repayPlan.entity.RepayPlan;
import com.resoft.outinterface.rest.ged.entity.response.GedRepayPlanResponse;
import com.resoft.outinterface.rest.newged.entity.OrderContractReqForm;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 还款计划DAO接口
 * @author wuxi01
 * @version 2016-03-01
 */
@MyBatisDao("creRepayPlanDao")
public interface RepayPlanDao extends CrudDao<RepayPlan> {
	public List<RepayPlan> getRepayPlanByApplyNo(Map<String,String> param);
	
	public void insertRepayPlanList(@Param("repayPlanList")List<RepayPlan> repayPlanList);
	public void deleteRepayPlanByApplyNo(Map<String,String> param);
	public List<RepayPlan> getRepayPlanByApplyNoAndTaskDefKey(Map<String,String> param);
	public void deleteRepayPlanByApplyNoAndContractNo(String applyNo);
	public List<Map<String,String>> getOverduePenalty();
	public List<GedRepayPlanResponse> getByContractNo(@Param("contractNo")String contractNo,@Param("taskDefKey")String taskDefKey);
	public OrderContractReqForm getRepayAndContract(@Param("applyNo")String applyNo, @Param("taskDefKey")String taskDefKey);

    OrderContractReqForm getUnderRepay(@Param("applyNo")String applyNo, @Param("taskDefKey")String taskDefKey);
	List<RepayPlan> listWithContractNoList(@Param("contractNoList") List<String> contractNoList);
}