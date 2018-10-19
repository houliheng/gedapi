package com.resoft.postloan.mortgageCarEvaluateInfo.dao;


import com.resoft.postloan.mortgageCarEvaluateInfo.entity.MortgageCarEvaluateInfo;
import com.resoft.postloan.mortgageCarInfo.entity.MortgageCarInfo;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 抵质押物信息DAO接口
 * @author yanwanmei
 * @version 2016-03-01
 */
@MyBatisDao("plMortgageCarEvaluateInfoDao")
public interface MortgageCarEvaluateInfoDao extends CrudDao<MortgageCarEvaluateInfo> {
	
	public MortgageCarInfo findMortgageCarByCarId(String carId);
	
	public MortgageCarEvaluateInfo findListByCarId(String carId);
	
	public void  updateByCarId(MortgageCarEvaluateInfo mortgageCarEvaluateInfo);
	
	
}