package com.resoft.credit.gqgetAssertHouse.service;

import java.util.List;

import com.resoft.credit.underCustInfo.entity.UnderCustInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.resoft.credit.gqgetAssertHouse.entity.GqgetAssetHouse;
import com.resoft.credit.gqgetAssertHouse.dao.GqgetAssetHouseDao;

/**
 * 冠E通房屋资产信息表Service
 * @author wanghong
 * @version 2016-10-13
 */
@Service
@Transactional(readOnly = true)
public class GqgetAssetHouseService extends CrudService<GqgetAssetHouseDao, GqgetAssetHouse> {

	public GqgetAssetHouse get(String id) {
		return super.get(id);
	}
	
	public List<GqgetAssetHouse> findList(GqgetAssetHouse gqgetAssetHouse) {
		return super.findList(gqgetAssetHouse);
	}
	
	public Page<GqgetAssetHouse> findPage(Page<GqgetAssetHouse> page, GqgetAssetHouse gqgetAssetHouse) {
		return super.findPage(page, gqgetAssetHouse);
	}
	
	@Transactional(readOnly = false)
	public void save(GqgetAssetHouse gqgetAssetHouse) {
		super.save(gqgetAssetHouse);
	}
	
	@Transactional(readOnly = false)
	public void delete(GqgetAssetHouse gqgetAssetHouse) {
		super.delete(gqgetAssetHouse);
	}

	public List<GqgetAssetHouse> getByApplyNo(String applyNo) {
		return dao.getByApplyNo(applyNo);
	}
}