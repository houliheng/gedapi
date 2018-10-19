package com.resoft.accounting.deductApply.dao;

import java.util.List;
import java.util.Map;

import com.resoft.accounting.deductApply.entity.DeductApply;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 还款划扣DAO接口
 * 
 * @author yanwanmei
 * @version 2016-03-03
 */
@MyBatisDao
public interface DeductApplyDao extends CrudDao<DeductApply> {

	DeductApply getDeductApplyByContractNoAndPeriodNum(DeductApply deductApply);

	/**
	 * 验证在制订合同下是否有正在划扣的记录
	 * 
	 * @param deductApply
	 * @return
	 */
	List<DeductApply> validateIsLockByContractNo(DeductApply deductApply);

	/**
	 * 保存数据到：acc_deduct_apply
	 */
	public void saveDeductApplyToDeductResult(DeductApply deductApply);
	/**
	 * 根据扣款申请编号修改状态
	 */
	void updateDeductApplyIsLock(String deductApplyNo);
	
	/**
	 * 查询冠e通同步的银行卡信息
	 */
	List<Map<String, Object>> findBankDataList(Map<String, Object> params);
	/**
	 * 直接划扣，查询手机号和身份证号
	 */
	Map<String, Object> queryMobileNumAndIdNum(String contractNo);

	void saveDCDeductApply(DeductApply deductApply);
}