package com.resoft.credit.gqgetAssetHouseUnion.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.resoft.credit.gqgetAssertHouse.entity.GqgetAssetHouse;
import com.resoft.credit.gqgetAssetHouseUnion.entity.GqgetAssetHouseUnion;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 联合授信冠e通房产资产DAO接口
 * @author lixing
 * @version 2016-12-26
 */
@MyBatisDao
public interface GqgetAssetHouseUnionDao extends CrudDao<GqgetAssetHouseUnion> {
	/**
	 * 批量保存联合授信冠e通房产资产
	 */
	  void insertGqgetAssetHouseUnionData(@Param("GqgetAssetHouses") List<GqgetAssetHouse> GqgetAssetHouses, @Param("checkApproveId") String checkApproveId);
	  /**
	   * 根据applyno删除数据
	   */
	  void deleteGqgetAssetHouseUnionData(String applyNo);
}