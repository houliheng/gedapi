package com.resoft.multds.credit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.multds.credit.dao.CreditDao;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;

@Service
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class CreditService {

	@Autowired
	private CreditDao creditDao;
	
	/**
	 * 根据合同号，更新放款状态
	 * @param contractNo
	 */
	@Transactional(value = "CRE", readOnly = false)
	public void updateLoanStatus(String contractNo,String contractState){
		creditDao.updateLoanStatus(contractNo, contractState);
	}

}
