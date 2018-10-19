package com.resoft.credit.stockOpinion.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

import java.util.List;


import org.apache.ibatis.annotations.Param;

import com.resoft.credit.stockOpinion.entity.CreStockOpinion;

/**
 * 公司尽调审批意见DAO接口
 * @author jml
 * @version 2017-12-04
 */
@MyBatisDao
public interface CreStockOpinionDao extends CrudDao<CreStockOpinion> {

	public List<CreStockOpinion> getByApplyNo(String applyNo);

	public CreStockOpinion getByOfficeGrade(@Param("applyNo") String applyNo,@Param("grade") String grade);

	public CreStockOpinion getStockInfoByApplyGrade(@Param("applyNo") String applyNo,@Param("officeFlag") String officeFlag);

	public void deleteStockInfoByApplyGrade(@Param("applyNo") String applyNo,@Param("officeFlag") String officeFlag);
	
	public void updateCreStockOpionState(@Param("applyNo") String applyNo,@Param("grade") String grade);

}