package com.resoft.credit.gqgetComInfo.dao;

import java.util.List;

import com.resoft.credit.gqgetComInfo.entity.MortageEquipment;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 冠E通DAO接口
 * @author wanghong
 * @version 2016-09-19
 */
@MyBatisDao
public interface MortageEquipmentDao extends CrudDao<MortageEquipment> {
	
	public void updateByApplyNo(MortageEquipment MortageEquipment);
	
	public List<MortageEquipment> getPageByApplyNo(String applyNo);
	
}