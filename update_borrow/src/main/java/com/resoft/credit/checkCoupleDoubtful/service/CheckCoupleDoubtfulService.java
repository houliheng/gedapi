package com.resoft.credit.checkCoupleDoubtful.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.applyRelation.entity.ApplyRelation;
import com.resoft.credit.checkCoupleDoubtful.dao.CheckCoupleDoubtfulDao;
import com.resoft.credit.checkCoupleDoubtful.entity.CheckCoupleDoubtful;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;

/**
 * 两人外访Service
 * @author yanwanmei
 * @version 2016-02-27
 */
@Service @DbType("cre.dbType")
@Transactional(value="CRE",readOnly = true)
public class CheckCoupleDoubtfulService extends CrudService<CheckCoupleDoubtfulDao, CheckCoupleDoubtful> {

	public CheckCoupleDoubtful get(String id) {
		return super.get(id);
	}
	
	public List<CheckCoupleDoubtful> findList(CheckCoupleDoubtful checkCoupleDoubtful) {
		return super.findList(checkCoupleDoubtful);
	}
	
	public Page<CheckCoupleDoubtful> findPage(Page<CheckCoupleDoubtful> page, CheckCoupleDoubtful checkCoupleDoubtful) {
		return super.findPage(page, checkCoupleDoubtful);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void save(CheckCoupleDoubtful checkCoupleDoubtful) {
		super.save(checkCoupleDoubtful);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void delete(CheckCoupleDoubtful checkCoupleDoubtful) {
		super.delete(checkCoupleDoubtful);
	}
	
	public String getCheckCoupleDoubtfulCount(ApplyRelation applyRelation,String loggerId) {
		Map<String,String> param = new HashMap<String, String>();
		param.put("applyNo", applyRelation.getApplyNo());
		param.put("custId", applyRelation.getCustId());
		param.put("roleType", applyRelation.getRoleType());
		param.put("checkUserId", loggerId);
		return super.dao.getCheckCoupleDoubtfulCount(param);
	}
	
	public String getCheckCoupleDoubtfulCountForNewJob(ApplyRelation applyRelation,String loggerId) {
		Map<String,String> param = new HashMap<String, String>();
		param.put("applyNo", applyRelation.getApplyNo());
		param.put("custId", applyRelation.getCustId());
		param.put("roleType", applyRelation.getRoleType());
		return super.dao.getCheckCoupleDoubtfulCountForNewJob(param);
	}
	
	public List<CheckCoupleDoubtful> getCheckCoupleDoubtfulByApplyNo(Map<String,String> params){
		return super.dao.getCheckCoupleDoubtfulByApplyNo(params);
	}
	
	public List<CheckCoupleDoubtful> getCheckCoupleDoubtfulByApplyNoForNewJob(Map<String,String> params){
		return super.dao.getCheckCoupleDoubtfulByApplyNoForNewJob(params);
	}
}