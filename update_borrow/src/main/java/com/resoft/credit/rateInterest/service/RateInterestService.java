package com.resoft.credit.rateInterest.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.resoft.credit.rateInterest.dao.RateInterestDao;
import com.resoft.credit.rateInterest.entity.RateInterest;

/**
 * 利率信息Service
 * @author yanwanmei
 * @version 2016-07-15
 */
@Service @DbType("cre.dbType")
@Transactional(value="CRE",readOnly = true)
public class RateInterestService extends CrudService<RateInterestDao, RateInterest> {

	public RateInterest get(String id) {
		return super.get(id);
	}
	
	public List<RateInterest> getLoanRepayType(Map<String,String> param){
		return this.dao.getLoanRepayType(param);
	}
	//利率页面需要的服务
	public List<RateInterest> findList(RateInterest rateInterest) {
		return super.findList(rateInterest);
	}
	
	public Page<RateInterest> findPage(Page<RateInterest> page, RateInterest rateInterest) {
		return super.findPage(page, rateInterest);
	}
	
	@Transactional(readOnly = false)
	public void save(RateInterest rateInterest) {
		super.save(rateInterest);
	}
	
	@Transactional(readOnly = false)
	public void saveAll(List<RateInterest> rateInterest){
		for(RateInterest c :rateInterest){
			c.setId(IdGen.uuid());
		}
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("rateInterest", rateInterest);
		super.dao.saveAll(param);
	}
	
	@Transactional(readOnly = false)
	public void delete(RateInterest rateInterest) {
		super.delete(rateInterest);
	}
	
	@Transactional(value = "CRE", readOnly = false)
	public void banchDelete(List<String> idList) {
		if (null != idList && idList.size() > 0) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("idList", idList);
			super.dao.banchDelete(param);
		}
		
	}
	
	public List<RateInterest> checkRateInterestIsDouble(RateInterest rateInterestInsert) {
		return super.dao.checkRateInterestIsDouble(rateInterestInsert);
	}
	
	
}