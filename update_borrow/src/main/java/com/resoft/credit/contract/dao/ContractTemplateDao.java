package com.resoft.credit.contract.dao;

import com.resoft.credit.contract.entity.ContractTemplate;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

@MyBatisDao("contractTemplateDao")
public interface ContractTemplateDao extends CrudDao<ContractTemplate> {
	
	public void updateName(ContractTemplate ctl) ;
}