package com.resoft.credit.checkApproveUnion.dao;

import com.resoft.credit.checkApproveUnion.entity.CompanyCustInfoRelated;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 授信企业个人关系DAO接口
 * 
 * @author wangguodong
 * @version 2016-12-14
 */
@MyBatisDao
public interface CompanyCustInfoRelatedDao extends CrudDao<CompanyCustInfoRelated> {

	/**
	 * 根据批复Id获取关系数据
	 */
	CompanyCustInfoRelated getCompanyCustInfoRelatedByApproId(String approId);
	/**
	 * 根据applyNo删除数据
	 */
	void deleteCompanyCustInfoRelatedByApplyNo(String applyNo);
}