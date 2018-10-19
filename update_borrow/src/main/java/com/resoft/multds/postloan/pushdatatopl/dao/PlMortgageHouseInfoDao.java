package com.resoft.multds.postloan.pushdatatopl.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.resoft.credit.mortgageHouseInfo.entity.MortgageHouseInfo;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 房产抵质押物DAO接口
 * @author zhaohuakui
 * @version 2016-06-23
 */
@MyBatisDao("postloanMortgageHouseInfoDao")
public interface PlMortgageHouseInfoDao extends CrudDao<MortgageHouseInfo> {

	public void insertHouseDataToPl(@Param("mortgageHouseInfoList")List<MortgageHouseInfo> mortgageHouseInfoList);
	
}