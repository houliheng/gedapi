package com.resoft.credit.CreGedBorrowStatus.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.credit.CreGedBorrowStatus.entity.CreGedBorrowStatus;

/**
 * 冠E贷同步状态DAO接口
 * @author zcl
 * @version 2018-03-13
 */
@MyBatisDao
public interface CreGedBorrowStatusDao extends CrudDao<CreGedBorrowStatus> {
	
	/**
	 * 冠易贷订单状态反馈状态更新
	 * @param applyNo
	 * @param loanStatus
	 */
	public boolean updateDrawStatus(String applyNo , String loanStatus);
}