package com.resoft.credit.stockAssesseTarget.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.resoft.credit.stockAssesseTarget.entity.StockAssesseTarget;

/**
 * 年度考核时点及指标DAO接口
 * @author jml
 * @version 2017-12-04
 */
@MyBatisDao
public interface StockAssesseTargetDao extends CrudDao<StockAssesseTarget> {
	/**
	 * 通过申请编号查询数据
	 * @param applyNo
	 * @return
	 */
	public List<StockAssesseTarget> findListByApplyNo(Map<String,Object> param);
	
	/**
	 * 批量删除
	 * @param idList
	 */
	public void batchDelete(@Param("idList") List<String> idList);

	public void deleteStockInfoByApplyGrade(@Param("applyNo")String applyNo, @Param("grade")String grade);
}