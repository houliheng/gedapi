package com.resoft.postloan.collateralDisposalConclusion.dao;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.postloan.collateralDisposalConclusion.entity.CollateralDisposalConclusion;

/**
 * 抵押物处置流程意见DAO接口
 * @author wangguodong
 * @version 2017-01-09
 */
@MyBatisDao
public interface CollateralDisposalConclusionDao extends CrudDao<CollateralDisposalConclusion> {
	/**
	 * 根据合同号和期数查询意见信息
	 */
	List<CollateralDisposalConclusion> findCollateralDisposalConclusionListByContractNoAndPeriodNum(Map<String, Object> params);
}