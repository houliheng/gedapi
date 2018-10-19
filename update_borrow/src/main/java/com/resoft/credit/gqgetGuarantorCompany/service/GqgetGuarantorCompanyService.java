package com.resoft.credit.gqgetGuarantorCompany.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.resoft.credit.gqgetGuarantorCompany.entity.GqgetGuarantorCompany;
import com.resoft.credit.gqgetGuarantorCompany.dao.GqgetGuarantorCompanyDao;

/**
 * 冠e通担保企业Service
 * @author wangguodong
 * @version 2016-09-29
 */
@Service
@Transactional(readOnly = true)
public class GqgetGuarantorCompanyService extends CrudService<GqgetGuarantorCompanyDao, GqgetGuarantorCompany> {

	public GqgetGuarantorCompany get(String id) {
		return super.get(id);
	}
	
	public List<GqgetGuarantorCompany> findList(GqgetGuarantorCompany gqgetGuarantorCompany) {
		return super.findList(gqgetGuarantorCompany);
	}
	
	public Page<GqgetGuarantorCompany> findPage(Page<GqgetGuarantorCompany> page, GqgetGuarantorCompany gqgetGuarantorCompany) {
		return super.findPage(page, gqgetGuarantorCompany);
	}
	
	@Transactional(readOnly = false)
	public void save(GqgetGuarantorCompany gqgetGuarantorCompany) {
		super.save(gqgetGuarantorCompany);
	}
	
	@Transactional(readOnly = false)
	public void delete(GqgetGuarantorCompany gqgetGuarantorCompany) {
		super.delete(gqgetGuarantorCompany);
	}
	
}