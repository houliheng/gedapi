package com.resoft.credit.gqgetAssetCarUnion.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.resoft.credit.gqgetAssetCarUnion.entity.GqgetAssetCarUnion;
import com.resoft.credit.gqgetAssetCarUnion.dao.GqgetAssetCarUnionDao;

/**
 * 联合授信冠e通车辆资产Service
 * @author lixing
 * @version 2016-12-26
 */
@Service
@Transactional(readOnly = true)
public class GqgetAssetCarUnionService extends CrudService<GqgetAssetCarUnionDao, GqgetAssetCarUnion> {

	public GqgetAssetCarUnion get(String id) {
		return super.get(id);
	}
	
	public List<GqgetAssetCarUnion> findList(GqgetAssetCarUnion gqgetAssetCarUnion) {
		return super.findList(gqgetAssetCarUnion);
	}
	
	public Page<GqgetAssetCarUnion> findPage(Page<GqgetAssetCarUnion> page, GqgetAssetCarUnion gqgetAssetCarUnion) {
		return super.findPage(page, gqgetAssetCarUnion);
	}
	
	@Transactional(readOnly = false)
	public void save(GqgetAssetCarUnion gqgetAssetCarUnion) {
		super.save(gqgetAssetCarUnion);
	}
	
	@Transactional(readOnly = false)
	public void delete(GqgetAssetCarUnion gqgetAssetCarUnion) {
		super.delete(gqgetAssetCarUnion);
	}
	
}