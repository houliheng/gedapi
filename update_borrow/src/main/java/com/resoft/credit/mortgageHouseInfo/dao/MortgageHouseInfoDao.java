package com.resoft.credit.mortgageHouseInfo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.resoft.credit.mortgageHouseInfo.entity.MortgageHouseInfo;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 房产抵质押物DAO接口
 * @author yanwanmei
 * @version 2016-02-29
 */
@MyBatisDao
public interface MortgageHouseInfoDao extends CrudDao<MortgageHouseInfo> {
	
	public void batchDelete(Map<String,Object> param);
	
	public long getHoseCount(@Param("applyNo")String applyNo);
	
	public List<MortgageHouseInfo> getPageByApplyNo(String applyNo);
	//查询要推送的房屋其质押物信息
	public List<MortgageHouseInfo> getHouseData(String applyNo);

    List<MortgageHouseInfo> getByapplyNo(@Param("applyNo")String applyNo);
}