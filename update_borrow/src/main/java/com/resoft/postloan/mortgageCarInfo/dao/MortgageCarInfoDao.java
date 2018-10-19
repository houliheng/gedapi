package com.resoft.postloan.mortgageCarInfo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.resoft.postloan.mortgageCarEvaluateInfo.entity.MortgageCarEvaluateInfo;
import com.resoft.postloan.mortgageCarInfo.entity.MortgageCarInfo;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 车辆抵质押物信息DAO接口
 * @author yanwanmei
 * @version 2016-02-29
 */
@MyBatisDao("plMortgageCarInfoDao")
public interface MortgageCarInfoDao extends CrudDao<MortgageCarInfo> {
	
	public void batchDelete(Map<String,Object> param);
	
	public MortgageCarEvaluateInfo selectData(String id);
	
	public long getCarCount(@Param("applyNo")String applyNo);
	
	public List<MortgageCarInfo> getPageByApplyNo(String applyNo);


}