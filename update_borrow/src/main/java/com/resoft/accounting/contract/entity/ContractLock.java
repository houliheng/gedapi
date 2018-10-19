package com.resoft.accounting.contract.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 合同信息Entity
 * @author wuxi01
 * @version 2016-03-03
 */
public class ContractLock extends DataEntity<ContractLock> {
	
	private static final long serialVersionUID = 1L;
	private String contractNo;		// 合同编号
	private String lockFlag; //锁定标记
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getLockFlag() {
		return lockFlag;
	}
	public void setLockFlag(String lockFlag) {
		this.lockFlag = lockFlag;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}