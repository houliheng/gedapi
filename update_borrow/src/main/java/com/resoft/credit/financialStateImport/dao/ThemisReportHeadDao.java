package com.resoft.credit.financialStateImport.dao;


import com.resoft.outinterface.SV.client2.EnterpriseFinancialField;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.credit.financialStateImport.entity.ThemisReportHead;

import java.util.List;
import org.apache.ibatis.annotations.Param;
/**
 * Themis报表头DAO接口
 * @author caoyinglong
 * @version 2016-03-14
 */
@MyBatisDao
public interface ThemisReportHeadDao extends CrudDao<ThemisReportHead> {
	public void deleltByApplyNo(String applyNo);

    List<EnterpriseFinancialField> getFinancialHeadByApplyNo(@Param("applyNo")String applyNo);
}