package com.resoft.multds.postloan.pushdatatopl.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.resoft.credit.mortgageCarEvaluateInfo.entity.MortgageCarEvaluateInfo;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 车辆评估抵质押物DAO接口
 * @author zhaohuakui
 * @version 2016-06-23
 */
@MyBatisDao("postloanMortgageCarEvaluateInfoDao")
public interface PlMortgageCarEvaluateInfoDao extends CrudDao<MortgageCarEvaluateInfo> {

	void insertCarEvaluateDataToPl(@Param("mortgageCarEvaluaInfolist")List<MortgageCarEvaluateInfo> mortgageCarEvaluaInfolist);
	
}