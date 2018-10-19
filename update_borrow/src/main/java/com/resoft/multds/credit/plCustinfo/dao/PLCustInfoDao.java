package com.resoft.multds.credit.plCustinfo.dao;

import java.util.Map;

import com.resoft.multds.credit.plCustinfo.entity.PLCustInfo;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 客户基本信息DAO接口
 */

@MyBatisDao
public interface PLCustInfoDao extends CrudDao<PLCustInfo> {

	/**
	 * 根据证件类型、证件号查询客户信息（单表查询）
	 * 
	 * @param map
	 * @return List<Map<String, String>>
	 */
	public PLCustInfo findCustInfoByIdCard(Map<String, String> map);

	/**
	 * 根据客户登记信息更新客户基本信息表信息
	 * 
	 * @param custInfo
	 */
	public void updateByApplyRegister(PLCustInfo custInfo);

}