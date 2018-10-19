package com.resoft.credit.guranteeProjectList.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.resoft.credit.guranteeProjectList.dao.GuranteeProjectListDao;
import com.resoft.credit.guranteeProjectList.entity.GuranteeProjectList;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;

/**
* @author guoshaohua
* @version 2018年4月19日 下午2:16:00
* 
*/
@Service
@Transactional(readOnly = true)
public class GuranteeProjectListService extends CrudService<GuranteeProjectListDao, GuranteeProjectList> {
	public GuranteeProjectList get(String id) {
		return super.get(id);
	}
	
	public List<GuranteeProjectList> findList(GuranteeProjectList creGuaranteeProject) {
		return super.findList(creGuaranteeProject);
	}
	
	public Page<GuranteeProjectList> findPage(Page<GuranteeProjectList> page, GuranteeProjectList creGuaranteeProject) {
		return super.findPage(page, creGuaranteeProject);
	}
	
	@Transactional(readOnly = false)
	public void save(GuranteeProjectList creGuaranteeProject) {
		super.save(creGuaranteeProject);
	}
	
	@Transactional(readOnly = false)
	public void delete(GuranteeProjectList creGuaranteeCompany) {
		super.delete(creGuaranteeCompany);
	}
	
	
	public GuranteeProjectList findGuranteeProjectListByContractNo(String contractNo){
		return this.dao.findGuranteeProjectListByContractNo(contractNo);
	}
}
