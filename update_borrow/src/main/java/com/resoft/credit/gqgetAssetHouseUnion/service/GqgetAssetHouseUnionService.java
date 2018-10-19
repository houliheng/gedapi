package com.resoft.credit.gqgetAssetHouseUnion.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.resoft.credit.gqgetAssetHouseUnion.entity.GqgetAssetHouseUnion;
import com.resoft.credit.gqgetAssetHouseUnion.dao.GqgetAssetHouseUnionDao;

/**
 * 联合授信冠e通房产资产Service
 * @author lixing
 * @version 2016-12-26
 */
@Service
@Transactional(readOnly = true)
public class GqgetAssetHouseUnionService extends CrudService<GqgetAssetHouseUnionDao, GqgetAssetHouseUnion> {

	public GqgetAssetHouseUnion get(String id) {
		return super.get(id);
	}
	
	public List<GqgetAssetHouseUnion> findList(GqgetAssetHouseUnion gqgetAssetHouseUnion) {
		return super.findList(gqgetAssetHouseUnion);
	}
	
	public Page<GqgetAssetHouseUnion> findPage(Page<GqgetAssetHouseUnion> page, GqgetAssetHouseUnion gqgetAssetHouseUnion) {
		return super.findPage(page, gqgetAssetHouseUnion);
	}
	
	@Transactional(readOnly = false)
	public void save(GqgetAssetHouseUnion gqgetAssetHouseUnion) {
		super.save(gqgetAssetHouseUnion);
	}
	
	@Transactional(readOnly = false)
	public void delete(GqgetAssetHouseUnion gqgetAssetHouseUnion) {
		super.delete(gqgetAssetHouseUnion);
	}
	
}