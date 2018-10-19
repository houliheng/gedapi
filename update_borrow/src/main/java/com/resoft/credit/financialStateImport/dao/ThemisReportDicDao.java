package com.resoft.credit.financialStateImport.dao;

import com.resoft.credit.financialStateImport.entity.ThemisReportDic;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import org.apache.ibatis.annotations.Param;

/**
 * Themis报表字典表DAO接口
 * @author wuxi01
 * @version 2016-03-23
 */
@MyBatisDao
public interface ThemisReportDicDao extends CrudDao<ThemisReportDic> {
    ThemisReportDic findByCode(@Param("code") String code);
}