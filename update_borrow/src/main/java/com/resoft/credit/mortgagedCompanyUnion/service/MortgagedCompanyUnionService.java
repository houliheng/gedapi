package com.resoft.credit.mortgagedCompanyUnion.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.resoft.credit.mortgagedCompanyUnion.entity.MortgagedCompanyUnion;
import com.resoft.credit.mortgagedCompanyUnion.dao.MortgagedCompanyUnionDao;

/**
 * 联合授信冠e通担保企业Service
 * @author wangguodong
 * @version 2016-12-26
 */
@Service
@Transactional(readOnly = true)
public class MortgagedCompanyUnionService extends CrudService<MortgagedCompanyUnionDao, MortgagedCompanyUnion> {

	public MortgagedCompanyUnion get(String id) {
		return super.get(id);
	}
	
	public List<MortgagedCompanyUnion> findList(MortgagedCompanyUnion mortgagedCompanyUnion) {
		return super.findList(mortgagedCompanyUnion);
	}
	
	public Page<MortgagedCompanyUnion> findPage(Page<MortgagedCompanyUnion> page, MortgagedCompanyUnion mortgagedCompanyUnion) {
		return super.findPage(page, mortgagedCompanyUnion);
	}
	
	@Transactional(readOnly = false)
	public void save(MortgagedCompanyUnion mortgagedCompanyUnion) {
		super.save(mortgagedCompanyUnion);
	}
	
	@Transactional(readOnly = false)
	public void delete(MortgagedCompanyUnion mortgagedCompanyUnion) {
		super.delete(mortgagedCompanyUnion);
	}
	
}