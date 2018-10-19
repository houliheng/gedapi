package com.resoft.credit.markNorm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.resoft.credit.markNorm.entity.CreStockMarkApplyRelation;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 股权信息加减分关系DAO接口
 * @author zcl
 * @version 2017-10-18
 */
@MyBatisDao
public interface CreStockMarkApplyRelationDao extends CrudDao<CreStockMarkApplyRelation> {
	
	/**
	 * 根据申请编号和加分类型，删除数据
	 * @param applyNo	申请编号
	 * @param markType	加分类型  1加分 2减分
	 */
	void deleteRelationByApplyNo(@Param("applyNo") String applyNo,@Param("markType") String markType);
	
	/**
	 * 根据标准表主键，删除关系表数据
	 * @param markNormId  标准表主键
	 */
	void deleteRelationByMarkNormId(@Param("markNormId") String markNormId);
	
	/**
	 * 根据申请编号，查询对应关系
	 * @param applyNo
	 * @return
	 */
	List<CreStockMarkApplyRelation> findRelationByApplyNo(@Param("applyNo") String applyNo,@Param("markType") String markType);
}