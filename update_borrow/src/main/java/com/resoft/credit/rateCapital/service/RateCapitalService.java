package com.resoft.credit.rateCapital.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.rateCapital.dao.RateCapitalDao;
import com.resoft.credit.rateCapital.entity.RateCapital;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;

@Service @DbType("cre.dbType")
@Transactional(value="CRE",readOnly = true)
public class RateCapitalService extends CrudService<RateCapitalDao, RateCapital>{
	
	public RateCapital get(String id) {
		return super.get(id);
	}
	
	//本金比例页面需要的服务
	public List<RateCapital> findList(RateCapital rateCapital) {
		return super.findList(rateCapital);
	}
	
	public Page<RateCapital> findPage(Page<RateCapital> page, RateCapital rateCapital) {
		return super.findPage(page, rateCapital);
	}
	
	@Transactional(readOnly = false)
	public void save(RateCapital rateCapital) {
		super.save(rateCapital);
	}
	
	@Transactional(readOnly = false)
	public void saveAll(List<RateCapital> rateCapital){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("rateCapital", rateCapital);
		super.dao.saveAll(param);
	}
	
	@Transactional(readOnly = false)
	public void delete(RateCapital rateCapital) {
		super.delete(rateCapital);
	}
	
	@Transactional(value = "CRE", readOnly = false)
	public void banchDelete(List<String> idList) {
		if (null != idList && idList.size() > 0) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("idList", idList);
			super.dao.banchDelete(param);
		}	
	}
	@Transactional(value = "CRE", readOnly = false)
	public void update(List<RateCapital> rateCapital){
		for(RateCapital c :rateCapital){
			super.dao.update(c);
		}
	}
	
	public List<RateCapital> findListAll(RateCapital rateCapital) {
		return super.dao.findListAll(rateCapital);
	}
	public List<RateCapital> selectCapitalEdit(RateCapital rateCapital) {
		return super.dao.selectCapitalEdit(rateCapital);
	}
}
