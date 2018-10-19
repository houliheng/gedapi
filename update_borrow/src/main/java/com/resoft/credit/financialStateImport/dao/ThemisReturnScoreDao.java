package com.resoft.credit.financialStateImport.dao;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.credit.financialStateImport.entity.ThemisReturnScore;
import org.apache.ibatis.annotations.Param;

/**
 * Themis返回分数DAO接口
 * @author caoyinglong
 * @version 2016-03-14
 */
@MyBatisDao
public interface ThemisReturnScoreDao extends CrudDao<ThemisReturnScore> {
	/**
	 * 根据进件号查询财报年月
	 * @param params
	 * @return
	 */
	public List<String> findDistinctReportYearMonth(Map<String, String> params);
	void deleteByApplyNo(@Param("applyNo") String applyNo);
}