package com.resoft.credit.mortgageCarInfo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.resoft.credit.mortgageCarEvaluateInfo.entity.MortgageCarEvaluateInfo;
import com.resoft.credit.mortgageCarInfo.entity.MortgageCarInfo;
import com.resoft.credit.mortgageCarInfo.entity.gqGetMortgageCarInfo;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 车辆抵质押物信息DAO接口
 * @author yanwanmei
 * @version 2016-02-29
 */
@MyBatisDao
public interface MortgageCarInfoDao extends CrudDao<MortgageCarInfo> {
	
	public void batchDelete(Map<String,Object> param);
	
	public MortgageCarEvaluateInfo selectData(String id);
	
	public long getCarCount(@Param("applyNo")String applyNo);
	
	public List<MortgageCarInfo> getPageByApplyNo(String applyNo);

	public List<gqGetMortgageCarInfo> getGqPageByApplyNo(String applyNo);
	
	public List<MortgageCarInfo> getCarData(String applyNo);
	
	public List<String> getIdsByApplyNo(String applyNo);

    List<gqGetMortgageCarInfo> getByApplyNo(@Param("applyNo")String applyNo);
}