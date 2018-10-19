package com.resoft.postloan.extendRepayPlan.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.resoft.multds.credit.PLRepayPlan.entity.PLRepayPlan;
import com.resoft.postloan.extendRepayPlan.entity.ExtendRepayPlan;
import com.resoft.postloan.extendRepayPlan.dao.ExtendRepayPlanDao;

/**
 * 合同展期还款计划Service
 * 
 * @author wangguodong
 * @version 2016-05-23
 */
@Service
@DbType("pl.dbType")
@Transactional(value = "PL", readOnly = true)
public class ExtendRepayPlanService extends CrudService<ExtendRepayPlanDao, ExtendRepayPlan> {

	public ExtendRepayPlan get(String id) {
		return super.get(id);
	}

	public List<ExtendRepayPlan> findList(ExtendRepayPlan extendRepayPlan) {
		return super.findList(extendRepayPlan);
	}

	public Page<ExtendRepayPlan> findPage(Page<ExtendRepayPlan> page, ExtendRepayPlan extendRepayPlan) {
		return super.findPage(page, extendRepayPlan);
	}

	@Transactional(readOnly = false)
	public void save(ExtendRepayPlan extendRepayPlan) {
		super.save(extendRepayPlan);
	}

	@Transactional(readOnly = false)
	public void delete(ExtendRepayPlan extendRepayPlan) {
		super.delete(extendRepayPlan);
	}

	/**
	 * 根据合同号查询最大期数
	 */
	public int findMaxExtendPeriodNum(String contractNo) {
		return this.dao.findMaxExtendPeriodNum(contractNo);
	}

	/**
	 * 根据指定合同号，删除数据
	 */
	@Transactional(value = "PL", readOnly = false)
	public void deleteExtendRepayPlan(String contractNo) {
		this.dao.deleteExtendRepayPlan(contractNo);
	}

	/**
	 * 批量保存展期还款计划
	 */
	@Transactional(value = "PL", readOnly = false)
	public void saveExtendRepayPlan(@Param("repayPlanList") List<PLRepayPlan> repayPlanList) {
		this.dao.saveExtendRepayPlan(repayPlanList);
	}

}