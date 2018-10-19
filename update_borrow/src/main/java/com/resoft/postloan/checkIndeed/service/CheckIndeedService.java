package com.resoft.postloan.checkIndeed.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.resoft.postloan.checkIndeed.entity.CheckIndeed;
import com.resoft.postloan.checkIndeed.dao.CheckIndeedDao;

/**
 * 借后实地外访Service
 * @author wangguodong
 * @version 2016-08-31
 */
@Service
@DbType("pl.dbType")
@Transactional(value="PL",readOnly = true)
public class CheckIndeedService extends CrudService<CheckIndeedDao, CheckIndeed> {

	public CheckIndeed get(String id) {
		return super.get(id);
	}
	
	public List<CheckIndeed> findList(CheckIndeed checkIndeed) {
		return super.findList(checkIndeed);
	}
	
	public Page<CheckIndeed> findPage(Page<CheckIndeed> page, CheckIndeed checkIndeed) {
		return super.findPage(page, checkIndeed);
	}
	
	@Transactional(readOnly = false)
	public void save(CheckIndeed checkIndeed) {
		super.save(checkIndeed);
	}
	
	@Transactional(readOnly = false)
	public void delete(CheckIndeed checkIndeed) {
		super.delete(checkIndeed);
	}
	
	public List<CheckIndeed> getCheckIndeedByAllocateId(String allocateId){
		return this.dao.getCheckIndeedByAllocateId(allocateId);
	}
	
}