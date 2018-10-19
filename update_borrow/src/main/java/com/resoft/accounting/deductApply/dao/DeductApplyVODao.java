package com.resoft.accounting.deductApply.dao;

import com.resoft.accounting.deductApply.entity.DeductApplyVO;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 划扣查询DAO接口
 * 
 * @author yanwanmei
 * @version 2016-03-03
 */
@MyBatisDao
public interface DeductApplyVODao extends CrudDao<DeductApplyVO> {
	/**
	 * 根据划扣申请编号获取数据
	 */
	public DeductApplyVO getDeductApplyVOByDeductApplyNo(String deductApplyNo);

	/**
	 * 根据扣款申请编号修改状态
	 */
	void updateDeductResultIsLock(String deductApplyNo);
}