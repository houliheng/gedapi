package com.resoft.accounting.penaltyInterestRate.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.resoft.accounting.penaltyInterestRate.entity.PenaltyInterestRate;
import com.resoft.accounting.penaltyInterestRate.dao.PenaltyInterestRateDao;

/**
 * 罚息利率调整Service
 * @author wangguodong
 * @version 2016-08-11
 */
@Service
@Transactional(readOnly = true)
public class PenaltyInterestRateService extends CrudService<PenaltyInterestRateDao, PenaltyInterestRate> {

	public PenaltyInterestRate get(String id) {
		return super.get(id);
	}
	
	public List<PenaltyInterestRate> findList(PenaltyInterestRate penaltyInterestRate) {
		return super.findList(penaltyInterestRate);
	}
	
	public Page<PenaltyInterestRate> findPage(Page<PenaltyInterestRate> page, PenaltyInterestRate penaltyInterestRate) {
		return super.findPage(page, penaltyInterestRate);
	}
	
	@Transactional(readOnly = false)
	public void save(PenaltyInterestRate penaltyInterestRate) {
		super.save(penaltyInterestRate);
	}
	
	@Transactional(readOnly = false)
	public void delete(PenaltyInterestRate penaltyInterestRate) {
		super.delete(penaltyInterestRate);
	}
	
}