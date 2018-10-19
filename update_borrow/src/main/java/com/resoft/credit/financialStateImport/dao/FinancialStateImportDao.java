package com.resoft.credit.financialStateImport.dao;

import com.resoft.credit.financialStateImport.entity.FinancialStateImport;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 财报导入DAO接口
 * @author caoyinglong
 * @version 2016-03-10
 */
@MyBatisDao
public interface FinancialStateImportDao extends CrudDao<FinancialStateImport> {

}
