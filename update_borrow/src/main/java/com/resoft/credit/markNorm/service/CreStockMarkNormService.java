package com.resoft.credit.markNorm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.markNorm.dao.CreStockMarkApplyRelationDao;
import com.resoft.credit.markNorm.dao.CreStockMarkNormDao;
import com.resoft.credit.markNorm.entity.CreStockMarkNorm;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;

/**
 * 股权加减分项Service
 * @author zcl
 * @version 2017-10-13
 */
@Service
@Transactional(readOnly = true)
public class CreStockMarkNormService extends CrudService<CreStockMarkNormDao, CreStockMarkNorm> {
	@Autowired
	CreStockMarkApplyRelationDao creStockMarkApplyRelationDao;
	
	public CreStockMarkNorm get(String id) {
		return super.get(id);
	}
	
	public List<CreStockMarkNorm> findList(CreStockMarkNorm creStockmarkNorm) {
		return super.findList(creStockmarkNorm);
	}
	
	public List<CreStockMarkNorm> findCheckedByApplyNoList(String applyNo, String markType) {
		return this.dao.findCheckedByApplyNoList(applyNo,markType);
	}
	
	public Page<CreStockMarkNorm> findPage(Page<CreStockMarkNorm> page, CreStockMarkNorm creStockmarkNorm) {
		return super.findPage(page, creStockmarkNorm);
	}
	
	@Transactional(readOnly = false)
	public void save(CreStockMarkNorm creStockmarkNorm) {
		super.save(creStockmarkNorm);
	}
	
	@Transactional(readOnly = false)
	public void delete(CreStockMarkNorm creStockmarkNorm) {
		super.delete(creStockmarkNorm);
		creStockMarkApplyRelationDao.deleteRelationByMarkNormId(creStockmarkNorm.getId());
	}
}