package com.resoft.credit.generalAudit.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.resoft.credit.generalAudit.entity.GeneralManagerAudit;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 总公司总经理审核条件配置DAO接口
 * @author chenshaojia
 * @version 2016-04-08
 */
@MyBatisDao
public interface GeneralManagerAuditDao extends CrudDao<GeneralManagerAudit> {
	/**
	 * 查询配置的条件，主要查询出操作(>=,<)及配置的金额
	 * */
	public List<GeneralManagerAudit> findConfigCondition(@Param("officeId")String officeId, @Param("productTypeCode")String productTypeCode, @Param("applyNo")String applyNo);
}