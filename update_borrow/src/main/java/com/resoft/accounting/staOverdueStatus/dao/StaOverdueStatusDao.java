package com.resoft.accounting.staOverdueStatus.dao;

import java.util.List;

import com.resoft.accounting.staOverdueStatus.entity.StaOverdueStatus;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 逾期信息Dao
 * 
 * @author wangguodong
 * 
 */

@MyBatisDao
public interface StaOverdueStatusDao extends CrudDao<StaOverdueStatus> {
	List<StaOverdueStatus> findStaOverdueStatusByContractNo(String contractNo);
}
