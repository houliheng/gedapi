package com.resoft.multds.credit.dao;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface CreditDao {
	public void updateLoanStatus(@Param(value = "contractNo") String contractNo, @Param(value = "contractState") String contractState);
}
