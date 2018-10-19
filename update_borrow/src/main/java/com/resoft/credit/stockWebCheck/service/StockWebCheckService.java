package com.resoft.credit.stockWebCheck.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.resoft.credit.stockWebCheck.entity.StockWebCheck;
import com.resoft.credit.stockWebCheck.dao.StockWebCheckDao;

/**
 * 一次网查（企查查）Service
 * @author jml
 * @version 2017-09-07
 */
@Service
@Transactional(readOnly = true)
public class StockWebCheckService extends CrudService<StockWebCheckDao, StockWebCheck> {

	public StockWebCheck get(String id) {
		return super.get(id);
	}
	
	public List<StockWebCheck> findList(StockWebCheck stockWebCheck) {
		return super.findList(stockWebCheck);
	}
	
	public Page<StockWebCheck> findPage(Page<StockWebCheck> page, StockWebCheck stockWebCheck) {
		return super.findPage(page, stockWebCheck);
	}
	
	@Transactional(readOnly = false)
	public void save(StockWebCheck stockWebCheck) {
		super.save(stockWebCheck);
	}
	
	@Transactional(readOnly = false)
	public void delete(StockWebCheck stockWebCheck) {
		super.delete(stockWebCheck);
	}

	public StockWebCheck getByApplyNo(String applyNo) {
		return dao.getByApplyNo(applyNo);
	}
	
}