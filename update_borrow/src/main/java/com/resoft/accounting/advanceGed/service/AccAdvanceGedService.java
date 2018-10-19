package com.resoft.accounting.advanceGed.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.resoft.accounting.advanceGed.entity.AccAdvanceGed;
import com.resoft.accounting.advanceGed.dao.AccAdvanceGedDao;

/**
 * 冠E贷提前还款Service
 * @author gsh
 * @version 2018-06-24
 */
@Service
@Transactional(readOnly = true)
public class AccAdvanceGedService extends CrudService<AccAdvanceGedDao, AccAdvanceGed> {

	public AccAdvanceGed get(String id) {
		return super.get(id);
	}
	
	public List<AccAdvanceGed> findList(AccAdvanceGed accAdvanceGed) {
		return super.findList(accAdvanceGed);
	}
	
	public Page<AccAdvanceGed> findPage(Page<AccAdvanceGed> page, AccAdvanceGed accAdvanceGed) {
		return super.findPage(page, accAdvanceGed);
	}
	
	@Transactional(readOnly = false)
	public void save(AccAdvanceGed accAdvanceGed) {
		super.save(accAdvanceGed);
	}
	
	@Transactional(readOnly = false)
	public void delete(AccAdvanceGed accAdvanceGed) {
		super.delete(accAdvanceGed);
	}
	
	public AccAdvanceGed queryMaxPeriodNumAdvance(String contractNo){
		return this.dao.queryMaxPeriodNumAdvance(contractNo);
	}
	
	public AccAdvanceGed queryAdvance(String contractNo,String periodNum,String advanceFlag){
		return this.dao.queryAdvance(contractNo,periodNum,advanceFlag);
	}
	
	
}