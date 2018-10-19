package com.resoft.credit.creOrgMapping.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.resoft.credit.creOrgMapping.dao.CreOrgMappingDao;
import com.resoft.credit.creOrgMapping.entity.CreOrgMapping;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;

@Service@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class CreOrgMappingService extends CrudService<CreOrgMappingDao, CreOrgMapping>{
	
	@Autowired
	private CreOrgMappingDao creOrgMappingDao;
	

	@Transactional(value = "CRE", readOnly = false)
	public void save(String code,String grade) {
		String maxOrgValue = String.valueOf(creOrgMappingDao.findMaxOrgValue()+1);
		CreOrgMapping creOrgMapping = new CreOrgMapping();
		creOrgMapping.setId(code);
		creOrgMapping.setName(grade);
		creOrgMapping.setOrgValue(maxOrgValue);
		creOrgMapping.setIsNewRecord(true);
		super.save(creOrgMapping);
	}
	
	public  CreOrgMapping findCreOrgMappingByCode(String code) {
		return creOrgMappingDao.findCreOrgMappingByCode(code);
	}
	@Transactional(value = "CRE", readOnly = false)
	public void save(CreOrgMapping creOrgMapping) {
		String maxOrgValue = String.valueOf(creOrgMappingDao.findMaxOrgValue()+1);
		creOrgMapping.setOrgValue(maxOrgValue);
		creOrgMapping.setIsNewRecord(true);
		super.save(creOrgMapping);
	}
	
	public Page<CreOrgMapping> findAllCode(Page<CreOrgMapping> page, CreOrgMapping creOrgMapping) {
		return super.findPage(page, creOrgMapping);
	}
	
	public CreOrgMapping get(String id){
		return super.get(id);
	}
	
	public CreOrgMapping findCode(String code){
		return super.get(code);
	}
	
	@Transactional(value = "CRE", readOnly = false)
	public void update(CreOrgMapping creOrgMapping){
		creOrgMapping.setIsNewRecord(false);
		super.save(creOrgMapping);
	}
}
