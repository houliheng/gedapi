package com.resoft.credit.relatedCompanyInfo.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.resoft.credit.relatedCompanyInfo.entity.CompanyRelated;
import com.resoft.credit.relatedCompanyInfo.dao.CompanyRelatedDao;

/**
 * 企业关联企业信息Service
 * @author caoyinglong
 * @version 2016-02-29
 */
@Service @DbType("cre.dbType")
@Transactional(value="CRE",readOnly = true)
public class CompanyRelatedService extends CrudService<CompanyRelatedDao, CompanyRelated> {

	public CompanyRelated get(String id) {
		return super.get(id);
	}
	
	public List<CompanyRelated> findList(CompanyRelated companyRelated) {
		return super.findList(companyRelated);
	}
	
	public Page<CompanyRelated> findPage(Page<CompanyRelated> page, CompanyRelated companyRelated) {
		return super.findPage(page, companyRelated);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void save(CompanyRelated companyRelated) {
		super.save(companyRelated);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void delete(CompanyRelated companyRelated) {
		super.delete(companyRelated);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void batchDelete(List<String> idList) {
		super.dao.batchDelete(idList);
	}
	
	public List<CompanyRelated> findListByParams(Map<String, Object> params) {
		return super.dao.findListByParams(params);
	}
	
	public List<CompanyRelated> findRelatedList(Map<String, Object> params) {
		return super.dao.findRelatedList(params);
	}
}