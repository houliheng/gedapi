package com.resoft.accounting.discountStream.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.resoft.accounting.discountStream.entity.AccDiscountStream;
import com.resoft.accounting.discountStream.entity.ContractDetailVo;
import com.resoft.accounting.discountStream.entity.PeriodDiscountDetail;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 贴息流水DAO接口
 * @author gsh
 * @version 2018-05-23
 */
@MyBatisDao
public interface AccDiscountStreamDao extends CrudDao<AccDiscountStream> {
	public AccDiscountStream queryDisStrBycontractNoAndPer(@Param("contractNo") String contractNo,@Param("periodNum") String periodNum);
	public ContractDetailVo findContractDiscountDetail(String contractNo);
	public List<PeriodDiscountDetail> queryDiscountDetails(String contractNo);
	public List<AccDiscountStream> getByContract(@Param("contractNo") String contractNo);
}