package com.resoft.accounting.advanceRepayGET.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

import java.util.List;

import com.resoft.accounting.advanceRepayGET.entity.AdvanceRepayGet;

/**
 * 提前还款记录DAO接口
 * @author jiangmenglun
 * @version 2018-01-15
 */
@MyBatisDao
public interface AdvanceRepayGetDao extends CrudDao<AdvanceRepayGet> {

	String remainRepayPlan(AdvanceRepayGet advanceRepayGet);
	
	List<AdvanceRepayGet> getByContractNo(AdvanceRepayGet advanceRepayGet);
	
}