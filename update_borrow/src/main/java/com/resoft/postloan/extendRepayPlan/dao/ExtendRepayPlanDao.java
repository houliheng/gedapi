package com.resoft.postloan.extendRepayPlan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.multds.credit.PLRepayPlan.entity.PLRepayPlan;
import com.resoft.postloan.extendRepayPlan.entity.ExtendRepayPlan;

/**
 * 合同展期还款计划DAO接口
 * 
 * @author wangguodong
 * @version 2016-05-23
 */
@MyBatisDao
public interface ExtendRepayPlanDao extends CrudDao<ExtendRepayPlan> {

	/**
	 * 根据合同号查询最大期数
	 */
	public int findMaxExtendPeriodNum(String contractNo);

	/**
	 * 根据指定合同号，删除数据
	 */
	public void deleteExtendRepayPlan(String contractNo);

	/**
	 * 批量保存展期还款计划
	 */
	public void saveExtendRepayPlan(@Param("repayPlanList") List<PLRepayPlan> repayPlanList);

}