package com.resoft.accounting.contract.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.accounting.contract.dao.ContractLockDao;
import com.resoft.accounting.contract.entity.ContractLock;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;

/**
 * 合同锁定信息Service
 * 
 * @author wuxi01
 * @version 2016-03-03
 */
@Service@DbType("acc.dbType")
@Transactional(value="ACC",readOnly = true)
public class ContractLockService extends CrudService<ContractLockDao, ContractLock> {

	@Transactional(value = "ACC", readOnly = false)
	public ContractLock validateIsLock(ContractLock contractLock){
		return this.dao.validateIsLock(contractLock);
	}
	
	@Transactional(value = "ACC", readOnly = false)
	public void saveLockInfo(ContractLock contractLock){
		this.dao.saveLockInfo(contractLock);
	}
	
	@Transactional(value = "ACC", readOnly = false)
	public void deleteLock(ContractLock contractLock){
		this.dao.deleteLock(contractLock);
	}
}