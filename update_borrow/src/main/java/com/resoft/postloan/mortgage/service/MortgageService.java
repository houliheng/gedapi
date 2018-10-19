package com.resoft.postloan.mortgage.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.postloan.mortgage.dao.MortgageDao;
import com.resoft.postloan.mortgage.entity.Mortgage;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;

/**
 * 合同展期还款计划Service
 * 
 * @author wangguodong
 * @version 2017-01-06
 */
@Service
@DbType("pl.dbType")
@Transactional(value = "PL", readOnly = true)
public class MortgageService extends CrudService<MortgageDao, Mortgage> {
	@Transactional(value = "PL", readOnly = false)
	public void updateMortgageCarData(Mortgage mortgage) {
		this.dao.updateMortgageCarData(mortgage);
	}

	@Transactional(value = "PL", readOnly = false)
	public void updateMortgageHouseData(Mortgage mortgage) {
		this.dao.updateMortgageHouseData(mortgage);
	}

	@Transactional(value = "PL", readOnly = false)
	public void updateMortgageOtherData(Mortgage mortgage) {
		this.dao.updateMortgageOtherData(mortgage);
	}

	@Transactional(value = "PL", readOnly = false)
	public List<Map<String, Object>> getAllDealAmount(String applyNo) {
		return this.dao.getAllDealAmount(applyNo);
	}
}
