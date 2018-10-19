package com.resoft.credit.gqgetComInfoUnion.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.resoft.credit.gqgetComInfoUnion.entity.GqgetComInfoUnion;
import com.resoft.credit.gqgetComInfoUnion.dao.GqgetComInfoUnionDao;

/**
 * 联合授信冠e通信息Service
 * @author lixing
 * @version 2016-12-26
 */
@Service
@Transactional(readOnly = true)
public class GqgetComInfoUnionService extends CrudService<GqgetComInfoUnionDao, GqgetComInfoUnion> {

	public GqgetComInfoUnion get(String id) {
		return super.get(id);
	}
	
	public List<GqgetComInfoUnion> findList(GqgetComInfoUnion gqgetComInfoUnion) {
		return super.findList(gqgetComInfoUnion);
	}
	
	public Page<GqgetComInfoUnion> findPage(Page<GqgetComInfoUnion> page, GqgetComInfoUnion gqgetComInfoUnion) {
		return super.findPage(page, gqgetComInfoUnion);
	}
	
	@Transactional(readOnly = false)
	public void save(GqgetComInfoUnion gqgetComInfoUnion) {
		super.save(gqgetComInfoUnion);
	}
	
	@Transactional(readOnly = false)
	public void delete(GqgetComInfoUnion gqgetComInfoUnion) {
		super.delete(gqgetComInfoUnion);
	}
	
	public GqgetComInfoUnion getGqgetComInfoByParam(Map<String, String> params){
		return super.dao.getGqgetComInfoByParam(params);
	}
}