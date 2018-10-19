package com.resoft.credit.stockInfo.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

import org.apache.ibatis.annotations.Param;

import com.resoft.credit.stockInfo.entity.StockInfo;

/**
 * 股权尽调业务表DAO接口
 * @author jml
 * @version 2017-12-04
 */
@MyBatisDao
public interface StockInfoDao extends CrudDao<StockInfo> {

	public StockInfo getStockInfoByApplyNo(@Param("applyNo")String applyNo);

	public StockInfo getStockInfoByApplyGrade(@Param("applyNo")String applyNo, @Param("grade")String grade);
	
	/**
	 * 查询当前流程节点
	 * @param procInstId
	 * @return
	 */
	public String selectActRuTasKDefKeyByInstId(String procInstId);
	
	public void saveOnlyApplyNo(StockInfo stockInfo);

	public void deleteStockInfoByApplyGrade(@Param("applyNo")String applyNo, @Param("grade")String grade);
}