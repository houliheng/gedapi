package com.resoft.credit.mortgageCarEvaluateInfo.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.resoft.credit.mortgageCarEvaluateInfo.entity.MortgageCarEvaluateInfo;
import com.resoft.credit.mortgageCarInfo.entity.MortgageCarInfo;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 分公司风控审核-抵质押物信息DAO接口
 * @author yanwanmei
 * @version 2016-03-01
 */
@MyBatisDao
public interface MortgageCarEvaluateInfoDao extends CrudDao<MortgageCarEvaluateInfo> {
	
	public MortgageCarInfo findMortgageCarByCarId(String carId);
	
	public MortgageCarEvaluateInfo findListByCarId(String carId);
	
	public void  updateByCarId(MortgageCarEvaluateInfo mortgageCarEvaluateInfo);
	//批量查询车辆抵质押物信息
	public List<MortgageCarEvaluateInfo> findListByCarIdList(@Param("idList")List idList);
	//批量计算车辆抵质押物价值
	public BigDecimal getSumCarEvaluatePriceByCarIdList(@Param("idList")List<String> idList);
}