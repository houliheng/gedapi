package com.resoft.credit.fhRiskControl.dao;

import java.util.List;
import java.util.Map;

import com.resoft.credit.fhRiskControl.entity.FhRiskControl;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 法海风控信息DAO接口
 * 
 * @author wangguodong
 * @version 2017-02-17
 */
@MyBatisDao
public interface FhRiskControlDao extends CrudDao<FhRiskControl> {

	/**
	 * 根据applyNo和custId统计查询次数
	 */
	Long getCountByApplyNoAndCustId(Map<String, Object> params);

	/**
	 * 根据applyNo和custId改变报告状态
	 */
	void updateReportStatusAndFilePathById(Map<String, Object> params);
	/**
	 * 根据applyNo和custId获取报告表中记录
	 */
	List<FhRiskControl> findFhRiskControlReportByApplyNoAndCustId(Map<String, Object> params);
	/**
	 * 新增报告生成记录
	 */
	void savefhRiskControlReport(FhRiskControl fhRiskControl);
	
	List<FhRiskControl> findRiskControlYcByApplyNo(Map<String, Object> params);
}