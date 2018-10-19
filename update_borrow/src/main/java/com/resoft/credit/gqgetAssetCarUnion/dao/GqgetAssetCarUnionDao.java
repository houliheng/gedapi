package com.resoft.credit.gqgetAssetCarUnion.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.credit.gqgetAssetCar.entity.GqgetAssetCar;
import com.resoft.credit.gqgetAssetCarUnion.entity.GqgetAssetCarUnion;

/**
 * 联合授信冠e通车辆资产DAO接口
 * 
 * @author lixing
 * @version 2016-12-26
 */
@MyBatisDao
public interface GqgetAssetCarUnionDao extends CrudDao<GqgetAssetCarUnion> {

	/**
	 * 批量保存联合授信冠e通车辆资产
	 */
	void insertGqgetAssetCarUnionData(@Param("GqgetAssetCars") List<GqgetAssetCar> GqgetAssetCars, @Param("checkApproveId") String checkApproveId);
	/**
	 * 根据applyNo删除数据
	 */
	void deleteGqgetAssetCarUnionData(String applyNo);
}