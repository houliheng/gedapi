package com.resoft.accounting.RepayPlanStatus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.accounting.RepayPlanStatus.dao.RepayPlanStatusDao;
import com.resoft.accounting.RepayPlanStatus.entity.RepayPlanStatus;
import com.thinkgem.jeesite.common.service.CrudService;

@Service
@Transactional(readOnly = true)
public class RepayPlanStatusService extends CrudService<RepayPlanStatusDao,RepayPlanStatus>{
	@Autowired
	private RepayPlanStatusDao repayPlanStatusDao;
	
	public RepayPlanStatus findStatusByConNoandPerNo(String contractNo,Integer perNo){
		return repayPlanStatusDao.findStatusByConNoandPerNo(contractNo,perNo);
	}
}
