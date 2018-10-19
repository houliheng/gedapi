package com.resoft.credit.markNorm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.resoft.credit.markNorm.entity.CreStockMarkNorm;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 股权加减分项DAO接口
 * @author zcl
 * @version 2017-10-13
 */
@MyBatisDao
public interface CreStockMarkNormDao extends CrudDao<CreStockMarkNorm> {
	/**
	 * 根据申请编号和加减分类型，查询出带有选中状态的数据
	 * @param applyNo
	 * @param markType
	 * @return
	 */
	public List<CreStockMarkNorm> findCheckedByApplyNoList(@Param("applyNo") String applyNo,@Param("markType") String markType);
}