package com.resoft.credit.stockAssesseTarget.service;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Maps;
import com.resoft.credit.stockAssesseTarget.dao.StockAssesseTargetDao;
import com.resoft.credit.stockAssesseTarget.entity.StockAssesseTarget;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
/**
 * 年度考核时点及指标Service
 * @author jml
 * @version 2017-12-04
 */
@Service
@DbType("cre.dbType")
@Transactional(value="CRE",readOnly = true)
public class StockAssesseTargetService extends CrudService<StockAssesseTargetDao, StockAssesseTarget> {
	
	
	@Autowired
	private StockAssesseTargetDao stockAssesseTargetDao;
	public StockAssesseTarget get(String id) {
		return super.get(id);
	}
	
	/**
	 * 通过申请编号查询集合
	 */
	public List<StockAssesseTarget> findList(StockAssesseTarget stockAssesseTarget) {
		return super.findList(stockAssesseTarget);
	}
	

	/**
	 * 保存数据
	 */
	@Transactional(readOnly = false)
	public void save(StockAssesseTarget stockAssesseTarget) {
		super.save(stockAssesseTarget);
	}
	
	
	/**
	 * 通过申请编号查询数据集合
	 * @param applyNo
	 * @return
	 */
	public List<StockAssesseTarget> findListByApplyNo(String applyNo,String taskDefKey,String grade){
		String grades = "";
		Map<String, Object> param = Maps.newConcurrentMap();
		if("4".equals(grade)){
			grades = "'4'";
		}
		if("3".equals(grade)){
			grades = "'4','3'";
		}
		if("2".equals(grade)){
			grades = "'4','3','2'";
		}
		if("1".equals(grade)){
			grades = "'4','3','2','1'";
		}
		param.put("applyNo",applyNo);
		param.put("grades",grades);
		return stockAssesseTargetDao.findListByApplyNo(param);
	}
	
	/**
	 * 批量删除
	 * @param idList
	 */
	@Transactional(readOnly=false)
	public void batchDelete(List<String> idList){
		super.dao.batchDelete(idList);
	}

	public void deleteStockInfoByApplyGrade(String applyNo, String grade) {
		dao.deleteStockInfoByApplyGrade(applyNo,grade);
	}
}