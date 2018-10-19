package com.resoft.accounting.loanDetail.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.resoft.accounting.loanDetail.entity.LoanDetail;
import com.resoft.accounting.loanDetail.dao.LoanDetailDao;

/**
 * 放款明细Service
 * @author wangguodong
 * @version 2016-08-15
 */
@Service
@Transactional(readOnly = true)
public class LoanDetailService extends CrudService<LoanDetailDao, LoanDetail> {

	public LoanDetail get(String id) {
		return super.get(id);
	}
	
	public List<LoanDetail> findList(LoanDetail loanDetail) {
		return super.findList(loanDetail);
	}
	
	public Page<LoanDetail> findPage(Page<LoanDetail> page, LoanDetail loanDetail) {
		return super.findPage(page, loanDetail);
	}
	
	@Transactional(readOnly = false)
	public void save(LoanDetail loanDetail) {
		super.save(loanDetail);
	}
	
	@Transactional(readOnly = false)
	public void delete(LoanDetail loanDetail) {
		super.delete(loanDetail);
	}
	
}