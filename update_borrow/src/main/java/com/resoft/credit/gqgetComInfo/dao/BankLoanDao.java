package com.resoft.credit.gqgetComInfo.dao;

import org.apache.ibatis.annotations.Param;

import com.resoft.credit.gqgetComInfo.entity.BankLoan;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 冠E通DAO接口
 * @author wanghong
 * @version 2016-09-19
 */
@MyBatisDao
public interface BankLoanDao extends CrudDao<BankLoan> {
	
	public BankLoan getBankLoanByApplyNo(@Param("applyNo")String applyNo);
	
	public void updateByApplyNo(BankLoan BankLoan);
	
}