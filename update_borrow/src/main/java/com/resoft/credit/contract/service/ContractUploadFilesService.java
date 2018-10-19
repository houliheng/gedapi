package com.resoft.credit.contract.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.contract.dao.ContractUploadFilesDao;
import com.resoft.credit.contract.entity.ContractUploadFiles;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;

@Service("conUploadFilesService")
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class ContractUploadFilesService extends	CrudService<ContractUploadFilesDao, ContractUploadFiles> {

	@Transactional(value = "CRE", readOnly = false)
	public void insert(ContractUploadFiles contractUploadFiles) {
		contractUploadFiles.preInsert();
		super.dao.insert(contractUploadFiles);
	}
}