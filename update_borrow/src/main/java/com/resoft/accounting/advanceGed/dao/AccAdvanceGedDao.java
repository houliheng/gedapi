package com.resoft.accounting.advanceGed.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

import org.apache.ibatis.annotations.Param;

import com.resoft.accounting.advanceGed.entity.AccAdvanceGed;

/**
 * 冠E贷提前还款DAO接口
 * @author gsh
 * @version 2018-06-24
 */
@MyBatisDao
public interface AccAdvanceGedDao extends CrudDao<AccAdvanceGed> {
	public AccAdvanceGed queryMaxPeriodNumAdvance(String contractNo);
	public AccAdvanceGed queryAdvance(@Param("contractNo") String contractNo,@Param("periodNum") String periodNum,@Param("advanceFlag") String advanceFlag);
}