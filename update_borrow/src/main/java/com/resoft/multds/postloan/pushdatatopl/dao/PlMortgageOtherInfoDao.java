package com.resoft.multds.postloan.pushdatatopl.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.resoft.credit.mortgageOtherInfo.entity.MortgageOtherInfo;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 其他抵质押物信息DAO接口
 * @author zhaohuakui
 * @version 2016-06-23
 */
@MyBatisDao("postloanMortgageOtherInfoDao")
public interface PlMortgageOtherInfoDao extends CrudDao<MortgageOtherInfo> {

	public void insertOtherDataToPl(@Param("mortgageOtherInfoList")List<MortgageOtherInfo> mortgageOtherInfoList);
}