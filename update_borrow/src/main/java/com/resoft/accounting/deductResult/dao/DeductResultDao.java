package com.resoft.accounting.deductResult.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.accounting.deductResult.entity.DeductResult;

/**
 * 补录流水DAO接口
 * 
 * @author wuxi01
 * @version 2016-03-04
 */
@MyBatisDao
public interface DeductResultDao extends CrudDao<DeductResult> {
	/**
	 * 获取指定合同号的锁定中的所有金额
	 */
	String getSumDeductAmount(String contractNo);

	/**
	 * 获取指定合同号的成功划扣的所有金额
	 */
	String getSumDeductAmountInChangeResult(String contractNo);

	/**
	 * 验证流水号是否存在(可用于根据某一个属性查询记录)
	 */
	List<DeductResult> validateStreamNo(DeductResult deductResult);

	/**
	 * 根据流水号删除记录
	 */
	void deleteData(DeductResult deductResult);
}