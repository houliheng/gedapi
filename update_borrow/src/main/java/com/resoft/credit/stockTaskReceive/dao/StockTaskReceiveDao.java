package com.resoft.credit.stockTaskReceive.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.resoft.credit.stockTaskReceive.entity.StockTaskReceive;

/**
 * 股权任务接收表DAO接口
 * @author jml
 * @version 2017-12-11
 */
@MyBatisDao
public interface StockTaskReceiveDao extends CrudDao<StockTaskReceive> {

	public StockTaskReceive getReceiveByApplyNoAndGrade(@Param("applyNo")String applyNo, @Param("grade")String grade);
	
	public void batchInsert(List<StockTaskReceive> stockTaskReceiveList);
	public Map<String,String> selectActRuTask(@Param("procInsId")String procInsId);
	public int selectActRuTasKCountByInstId(Map<String,Object> map);

	/**
	 * 根据股权信息表主键查询接收表数据
	 * @param stockInfoId
	 * @return
	 */
	public List<StockTaskReceive> selectByStockInfoId(@Param("stockInfoId") String stockInfoId);
	/**
	 * 查询当前流程节点
	 * @param procInstId
	 * @return
	 */
	public String selectActRuTasKDefKeyByInstId(@Param("procInstId")String procInstId);
	
	/**
	 * 根据申请编号查询流程实例
	 * @param applyNo
	 * @return
	 */
	public String selectProcInstIdByApplyNo(@Param("applyNo")String applyNo);
	
	/**
	 * 查询当前的估值信息
	 * @param applyNo	申请编号
	 * @param type	0估值岗        1估值中心
	 * @return
	 */
	public String selectStockInfoIdByApplyNoAndType(@Param("applyNo") String applyNo,@Param("type") String type);
	
	
	/**
	 * 查询股权分配表中对应的审批记录
	 * @param param
	 * @return
	 */
	public StockTaskReceive findStockTaskReciveByParam(Map<String,String> param);
	
	/**
	 * 更新股权分配表状态
	 * @param param
	 */
	public void updateStockTaskReciveState(Map<String,String> param);

	public void deleteStockInfoByApplyGrade(@Param("applyNo") String applyNo,@Param("grade") String grade);

	public StockTaskReceive getById(@Param("id")String id);
	
}