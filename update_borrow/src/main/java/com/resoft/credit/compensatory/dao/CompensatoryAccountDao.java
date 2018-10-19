package com.resoft.credit.compensatory.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.resoft.credit.compensatory.entity.CompensatoryAccount;

/**
 * 代偿资金账户DAO接口
 * @author jml
 * @version 2018-03-19
 */
@MyBatisDao
public interface CompensatoryAccountDao extends CrudDao<CompensatoryAccount> {

	List<CompensatoryAccount> getMostPriopity();

	List<CompensatoryAccount> getAllAccount();

	List<CompensatoryAccount> checkDouble(CompensatoryAccount compensatoryAccount);


	List<CompensatoryAccount> getGRComAccount(@Param("applyNo")String applyNo, @Param("string1")String string1);

	List<CompensatoryAccount> getGRCustAccount(@Param("applyNo")String applyNo, @Param("string1")String string1);

	List<CompensatoryAccount> getLHComAccount(@Param("applyNo")String applyNo, @Param("companyId")String companyId);
	List<CompensatoryAccount> getLHCustAccount(@Param("applyNo")String applyNo, @Param("companyId")String companyId);

	List<CompensatoryAccount> getGRDBComAccount(@Param("applyNo")String applyNo);

	List<CompensatoryAccount> getGRPJDBComAccount(@Param("applyNo")String applyNo, @Param("companyId")String companyId);
	
}