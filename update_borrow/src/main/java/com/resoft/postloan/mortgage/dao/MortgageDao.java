package com.resoft.postloan.mortgage.dao;

import java.util.List;
import java.util.Map;

import com.resoft.postloan.mortgage.entity.Mortgage;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface MortgageDao extends CrudDao<Mortgage> {
	/**
	 * 处置车辆抵押
	 * @param mortgage
	 */
	void updateMortgageCarData(Mortgage mortgage);
	/**
	 * 处置房产
	 * @param mortgage
	 */
	void updateMortgageHouseData(Mortgage mortgage);
	/**
	 * 处置其他抵押
	 * @param mortgage
	 */
	void updateMortgageOtherData(Mortgage mortgage);
	/**
	 * 获取总的处置金额
	 */
	List<Map<String, Object>>  getAllDealAmount(String applyNo);
}
