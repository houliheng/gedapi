package com.resoft.credit.companyHistory.dao;

import com.resoft.credit.companyHistory.entity.AccCompanyHistory;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

import java.util.List;

/**
 * 企业开户历史表DAO接口
 * @author weiruihong
 * @version 2018-06-26
 */
@MyBatisDao
public interface AccCompanyHistoryDao extends CrudDao<AccCompanyHistory> {

    public List<AccCompanyHistory> selectAccByaccountCompanyId(String companyId);
}