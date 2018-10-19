package com.resoft.accounting.discount.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.resoft.accounting.discount.entity.AccDiscount;


/**
 * 贴息表DAO接口
 * @author gsh
 * @version 2018-05-18
 */
@MyBatisDao
public interface AccDiscountDao extends CrudDao<AccDiscount> {
	public List<AccDiscount> findAccDiscountsByContractNo(String contractNo);
	public AccDiscount findAccDisCountByContractNoAndPeriodNum(AccDiscount accDiscount);
	public int insertAccDiscount(AccDiscount accDiscount);
	//根据合同号删除贴息表中的数据

	public int deleteAccDiscount(String contractNo);
	public void deleteDiscountByContractAndPeriodNum(@Param("contractNo") String contractNo,@Param("periodNum") String periodNum);
}