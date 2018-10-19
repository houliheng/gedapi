package com.resoft.outinterface.SV.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.outinterface.SV.dao.SVMortgageHouseDao;
import com.resoft.outinterface.SV.server.entry.request.SVMortgageHouse;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;

/**
 * SV回盘房产抵押信息Service
 * 
 * @author admin
 * @version 2016-04-21
 */
@Service
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class SVMortgageHouseService {

	@Autowired
	private SVMortgageHouseDao svMortgageHouseDao;

	public SVMortgageHouse get(String id) {
		return svMortgageHouseDao.get(id);
	}

	public List<SVMortgageHouse> findList(SVMortgageHouse sVMortgageHouse) {
		return svMortgageHouseDao.findList(sVMortgageHouse);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void save(Map<String, Object> baseInfo, List<SVMortgageHouse> houseList) {
		baseInfo.put("list", houseList);
		svMortgageHouseDao.insert(baseInfo);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void delete(SVMortgageHouse sVMortgageHouse) {
		svMortgageHouseDao.delete(sVMortgageHouse);
	}

}