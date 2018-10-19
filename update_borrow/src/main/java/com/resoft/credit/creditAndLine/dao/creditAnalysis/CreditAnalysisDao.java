package com.resoft.credit.creditAndLine.dao.creditAnalysis;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.credit.creditAndLine.entity.creditAnalysis.CreditAnalysis;

/**
 * 征信分析信息详细DAO接口
 * @author wuxi01
 * @version 2016-02-26
 */
@MyBatisDao
public interface CreditAnalysisDao extends CrudDao<CreditAnalysis> {
	
	public CreditAnalysis getCreditAnalysisByApplyNo(@Param("applyNo")String applyNo);
	
	public void updateByApplyNo(CreditAnalysis creditAnalysis);
}