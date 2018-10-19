package com.resoft.credit.contract.dao;

import com.resoft.credit.contract.entity.ContractUploadFiles;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;


@MyBatisDao("conUploadFilesDao")
public interface ContractUploadFilesDao extends CrudDao<ContractUploadFiles> {
	

}