package com.resoft.credit.gqgetAssetCar.service;

import java.util.List;

import com.resoft.credit.underCustInfo.entity.UnderCustInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.resoft.credit.gqgetAssetCar.entity.GqgetAssetCar;
import com.resoft.credit.gqgetAssetCar.dao.GqgetAssetCarDao;

/**
 * 冠e通资产车辆信息Service
 * @author wangguodong
 * @version 2016-10-13
 */
@Service
@Transactional(readOnly = true)
public class GqgetAssetCarService extends CrudService<GqgetAssetCarDao, GqgetAssetCar> {

	public GqgetAssetCar get(String id) {
		return super.get(id);
	}
	
	public List<GqgetAssetCar> findList(GqgetAssetCar gqgetAssetCar) {
		return super.findList(gqgetAssetCar);
	}
	
	public Page<GqgetAssetCar> findPage(Page<GqgetAssetCar> page, GqgetAssetCar gqgetAssetCar) {
		return super.findPage(page, gqgetAssetCar);
	}
	
	@Transactional(readOnly = false)
	public void save(GqgetAssetCar gqgetAssetCar) {
		super.save(gqgetAssetCar);
	}
	
	@Transactional(readOnly = false)
	public void delete(GqgetAssetCar gqgetAssetCar) {
		super.delete(gqgetAssetCar);
	}

	public List<GqgetAssetCar> getByApplyNo(String applyNo) {
		return  dao.getByApplyNo(applyNo);

	}
}