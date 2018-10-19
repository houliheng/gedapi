package com.resoft.credit.mortgageOtherInfo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.resoft.credit.mortgageOtherInfo.entity.MortgageOtherInfo;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 其他抵质押物信息DAO接口
 * @author yanwanmei
 * @version 2016-02-24
 */
@MyBatisDao
public interface MortgageOtherInfoDao extends CrudDao<MortgageOtherInfo> {
	
	public void batchDelete(Map<String,Object> param);

	public long getOtherCount(@Param("applyNo")String applyNo);
	
	public List<MortgageOtherInfo> getPageByApplyNo(String applyNo);
	//查询要推送的其他其质押物信息
	public List<MortgageOtherInfo> getOtherData(String applyNo);
	
}