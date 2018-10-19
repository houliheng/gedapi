package com.resoft.accounting.loanDetail.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.accounting.loanDetail.entity.LoanDetail;

/**
 * 放款明细DAO接口
 * @author wangguodong
 * @version 2016-08-15
 */
@MyBatisDao
public interface LoanDetailDao extends CrudDao<LoanDetail> {
	
}