package com.resoft.credit.pricedRisk.dao;

import java.util.Map;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 风险定价Dao
 * 
 * @author wangguodong
 * @version 2016-11-04
 */

@MyBatisDao
public interface PricedRiskDao {
	//获取用款企业成立年限
	Map<String, Object> getCompanyCreatePeriod(String applyNo);
	
	//获取信用卡额度使用占比
	Map<String, Object> getCreditCardUsageAccounting(String applyNo);
	
	//获取月均有效流水占比
	//获取月均有效流水
	Map<String, Object> getEffectiveFlowSum(String applyNo);
	//获取合同金额
	Map<String, Object> getContractAccountInCheckApprove(String applyNo);
	
	//获取一年内本人查询次数
	Map<String, Object> getOwnQueryTimesInOneYear(String applyNo);
	
	//获取担保人资产覆盖率
	Map<String, Object> getGuaranteedAssetCoverage(String applyNo);
	
}
