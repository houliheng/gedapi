package com.resoft.accounting.contract.dao;

import com.resoft.accounting.contract.entity.ContractLock;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 合同信息DAO接口
 * 
 * @author wuxi01
 * @version 2016-03-03
 */
@MyBatisDao
public interface ContractLockDao extends CrudDao<ContractLock> {
	
	/**验证合同号是否被锁
	 *
	 */
	ContractLock validateIsLock(ContractLock contractLock);
	/**
	 * 保存准备锁定的合约号
	 */
	void saveLockInfo(ContractLock contractLock);
	/**
	 * 删除锁定的合约号
	 */
	void deleteLock(ContractLock contractLock);
}