package com.resoft.credit.gqgetAssetCar.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.credit.gqgetAssetCar.entity.GqgetAssetCar;
import org.apache.ibatis.annotations.Param;

/**
 * 冠e通资产车辆信息DAO接口
 * @author wangguodong
 * @version 2016-10-13
 */
@MyBatisDao
public interface GqgetAssetCarDao extends CrudDao<GqgetAssetCar> {
	/**
	 * 根据applyNo获取冠e通资产车辆信息
	 */
	public List<GqgetAssetCar> findGqgetAssetCarDataByApplyNo(String gqgetApplyNo);

	List<GqgetAssetCar> getByApplyNo(@Param("applyNo")String applyNo);
}