package com.resoft.credit.mortageEquipmentUnion.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.resoft.credit.gqgetComInfo.entity.MortageEquipment;
import com.resoft.credit.mortageEquipmentUnion.entity.MortageEquipmentUnion;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 联合授信设备抵押DAO接口
 * 
 * @author wangguodong
 * @version 2016-12-26
 */
@MyBatisDao
public interface MortageEquipmentUnionDao extends CrudDao<MortageEquipmentUnion> {
	/**
	 * 批量保存联合授信设备抵押
	 */
	void insertMortageEquipmentUnionData(@Param("MortageEquipments") List<MortageEquipment> MortageEquipments, @Param("checkApproveId") String checkApproveId);
	/**
	 * 根据applyno删除数据
	 */
	void deleteMortageEquipmentUnionData(String applyNo);
	/**
	 * 根据applyno和批复id查询数据
	 */
	public List<MortageEquipmentUnion> getPageByApplyNo(Map<String,String> params);
}