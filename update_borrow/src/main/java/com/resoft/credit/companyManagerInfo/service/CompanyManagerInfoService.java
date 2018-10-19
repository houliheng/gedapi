package com.resoft.credit.companyManagerInfo.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.companyManagerInfo.dao.CompanyManagerInfoDao;
import com.resoft.credit.companyManagerInfo.entity.CompanyManagerInfo;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;

/**
 * 企业高管信息Service
 * @author caoyinglong
 * @version 2016-02-27
 */
@Service @DbType("cre.dbType")
@Transactional(value="CRE",readOnly = true)
public class CompanyManagerInfoService extends CrudService<CompanyManagerInfoDao, CompanyManagerInfo> {

	public CompanyManagerInfo get(String id) {
		return super.get(id);
	}
	
	public List<CompanyManagerInfo> findList(CompanyManagerInfo companyManagerInfo) {
		return super.findList(companyManagerInfo);
	}
	
	public Page<CompanyManagerInfo> findPage(Page<CompanyManagerInfo> page, CompanyManagerInfo companyManagerInfo) {
		return super.findPage(page, companyManagerInfo);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void save(CompanyManagerInfo companyManagerInfo) {
		super.save(companyManagerInfo);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void delete(CompanyManagerInfo companyManagerInfo) {
		super.delete(companyManagerInfo);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void batchDelete(List<String> idList) {
		super.dao.batchDelete(idList);
	}
	
	public List<CompanyManagerInfo> findListByParams(Map<String,Object> params) {
		return super.dao.findListByParams(params);
	}
	
	public List<CompanyManagerInfo> findManagerList(Map<String,Object> params) {
		return super.dao.findManagerList(params);
	}
}