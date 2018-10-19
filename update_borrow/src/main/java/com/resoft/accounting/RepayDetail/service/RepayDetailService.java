package com.resoft.accounting.RepayDetail.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.accounting.RepayDetail.dao.RepayDetailDao;
import com.resoft.accounting.RepayDetail.entity.RepayDetail;
import com.thinkgem.jeesite.common.service.CrudService;

@Service
@Transactional(readOnly = true)
public class RepayDetailService extends CrudService<RepayDetailDao,RepayDetail>{
	
	@Autowired
	private RepayDetailDao repayDetailDao;
	public List<RepayDetail> findPeriondByDeducyApplyNo(String deductApplyNo){
		return repayDetailDao.findPeriondByDeducyApplyNo(deductApplyNo);
	}
}
