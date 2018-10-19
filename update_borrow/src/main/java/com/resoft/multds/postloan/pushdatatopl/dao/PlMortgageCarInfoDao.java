package com.resoft.multds.postloan.pushdatatopl.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.resoft.credit.mortgageCarEvaluateInfo.entity.MortgageCarEvaluateInfo;
import com.resoft.credit.mortgageCarInfo.entity.MortgageCarInfo;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 车辆抵质押物DAO接口
 * @author zhaohuakui
 * @version 2016-06-23
 */
@MyBatisDao("postloanMortgageCarInfoDao")
public interface PlMortgageCarInfoDao extends CrudDao<MortgageCarInfo> {

	void insertCarDataToPl(@Param("mortgageCarInfoList")List<MortgageCarInfo> mortgageCarInfoList);
	
	List<MortgageCarEvaluateInfo> findListByCarId(Map<String, Object> paramId);
	
}