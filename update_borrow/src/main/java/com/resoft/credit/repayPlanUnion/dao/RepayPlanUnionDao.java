package com.resoft.credit.repayPlanUnion.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.credit.repayPlanUnion.entity.RepayPlanUnion;
import com.resoft.outinterface.rest.ged.entity.response.GedRepayPlanResponse;
import com.resoft.outinterface.rest.newged.entity.OrderContractReqForm;

/**
 * 还款计划授信DAO接口
 * 
 * @author wangguodong
 * @version 2016-12-14
 */
@MyBatisDao
public interface RepayPlanUnionDao extends CrudDao<RepayPlanUnion> {
	public void saveRepayPlanUnionList(@Param("repayPlanUnions") List<RepayPlanUnion> repayPlanUnions);
	public void deleteRepayPlanUnion(Map<String, String> params);
	public List<RepayPlanUnion> getRepayPlanByParam(Map<String,String> param);
	public List<GedRepayPlanResponse> getByApplyTaskAppro(Map<String, String> param);
	public OrderContractReqForm getRepayAndContract(@Param("applyNo")String applyNo, @Param("taskDefKey")String taskDefKey, @Param("approId")String approId);
}