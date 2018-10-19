package com.resoft.credit.financialStateImport.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.credit.financialStateImport.entity.ThemisReturnHead;
import org.apache.ibatis.annotations.Param;

/**
 * Themis返回头DAO接口
 * @author caoyinglong
 * @version 2016-03-14
 */
@MyBatisDao
public interface ThemisReturnHeadDao extends CrudDao<ThemisReturnHead> {
    void deleteByApplyNo(@Param("applyNo") String applyNo);
}