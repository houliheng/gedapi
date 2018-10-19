package com.resoft.outinterface.SV.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.outinterface.SV.dao.SVMortgageCarDao;
import com.resoft.outinterface.SV.server.entry.request.SVMortgageCar;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;

/**
 * SV回盘车辆抵押信息Service
 * 
 * @author admin
 * @version 2016-04-21
 */
@Service
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class SVMortgageCarService {

	@Autowired
	private SVMortgageCarDao svMortgageCarDao;

	public SVMortgageCar get(String id) {
		return svMortgageCarDao.get(id);
	}

	public List<SVMortgageCar> findList(SVMortgageCar sVMortgageCar) {
		return svMortgageCarDao.findList(sVMortgageCar);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void save(Map<String, Object> baseInfo, List<SVMortgageCar> carList) {
		baseInfo.put("list", carList);
		svMortgageCarDao.insert(baseInfo);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void delete(SVMortgageCar sVMortgageCar) {
		svMortgageCarDao.delete(sVMortgageCar);
	}

}