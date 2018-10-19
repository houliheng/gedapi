package com.resoft.credit.supportDecision.dao;

import com.resoft.credit.financialStateImport.entity.ThemisReturnReview;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import org.apache.ibatis.annotations.Param;

/**
 * Themis返回评价DAO接口
 * @author wuxi01
 * @version 2016-03-26
 */
@MyBatisDao
public interface ThemisReturnReviewDao extends CrudDao<ThemisReturnReview> {
    void deleteByApplyNo(@Param("applyNo") String applyNo);
}