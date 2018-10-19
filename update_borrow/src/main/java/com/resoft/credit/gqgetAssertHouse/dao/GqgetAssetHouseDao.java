package com.resoft.credit.gqgetAssertHouse.dao;

import java.util.List;

import com.resoft.credit.gqgetAssertHouse.entity.GqgetAssetHouse;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import org.apache.ibatis.annotations.Param;

/**
 * 冠E通房屋资产信息表DAO接口
 * @author wanghong
 * @version 2016-10-13
 */
@MyBatisDao
public interface GqgetAssetHouseDao extends CrudDao<GqgetAssetHouse> {
	/**
	 * 根据applyNo查询冠E通房屋资产信息
	 */
	public List<GqgetAssetHouse> findGqgetAssetHouseDataByApplyNo(String gqgetApplyNo);

    List<GqgetAssetHouse> getByApplyNo(@Param("applyNo")String applyNo);
}