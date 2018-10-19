package com.resoft.accounting.applyMarginRepay.dao;

import com.resoft.accounting.applyMarginRepay.entity.ApplyMarginRepay;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 保证金退还申请DAO接口
 * 
 * @author wuxi01
 * @version 2016-03-04
 */
@MyBatisDao
public interface ApplyMarginRepayDao extends CrudDao<ApplyMarginRepay> {
	/**
	 * 根据合同号查询保证金申请
	 */
	ApplyMarginRepay findApplyMarginRepayByContractNo(ApplyMarginRepay applyMarginRepay);

	/**
	 * 申请状态——审核状态
	 */
	void updateApplyMarginRepay(ApplyMarginRepay applyMarginRepay);
	/**
	 * 启动流程后更新对应的流程实例ID
	 */
	public void updateProcInsIdById(ApplyMarginRepay applyMarginRepay);
}