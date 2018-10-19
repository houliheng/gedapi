package com.resoft.credit.stockOpinion.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.resoft.credit.stockOpinion.entity.CreStockOpinion;
import com.resoft.credit.stockOpinion.dao.CreStockOpinionDao;

/**
 * 公司尽调审批意见Service
 * @author jml
 * @version 2017-12-04
 */
@Service
@Transactional(readOnly = true)
public class CreStockOpinionService extends CrudService<CreStockOpinionDao, CreStockOpinion> {
	
	public CreStockOpinion get(String id) {
		return super.get(id);
	}
	
	public List<CreStockOpinion> findList(CreStockOpinion creStockOpinion) {
		return super.findList(creStockOpinion);
	}
	
	public Page<CreStockOpinion> findPage(Page<CreStockOpinion> page, CreStockOpinion creStockOpinion) {
		return super.findPage(page, creStockOpinion);
	}
	
	@Transactional(readOnly = false)
	public void save(CreStockOpinion creStockOpinion) {
		super.save(creStockOpinion);
	}
	
	@Transactional(readOnly = false)
	public void delete(CreStockOpinion creStockOpinion) {
		super.delete(creStockOpinion);
	}

	public List<CreStockOpinion> getByApplyNo(String applyNo) {
		return dao.getByApplyNo(applyNo);
	}

	public CreStockOpinion getByOfficeGrade(String applyNo, String grade) {
		return dao.getByOfficeGrade(applyNo,grade);
	}

	public CreStockOpinion getStockInfoByApplyGrade(String applyNo, String grade) {
		return dao.getStockInfoByApplyGrade(applyNo,grade);
	}

	public void deleteStockInfoByApplyGrade(String applyNo, String grade) {
		dao.deleteStockInfoByApplyGrade(applyNo,grade);
		
	}
}