package com.resoft.multds.credit.plApplyRegister.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.multds.credit.plApplyRegister.dao.PLApplyRegisterDao;
import com.resoft.multds.credit.plApplyRegister.entity.PLApplyRegister;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;

/**
 * 个人客户登记Service
 * 
 * @author wuxi01
 * @version 2016-06-17
 */
@Service
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class PLApplyRegisterService extends CrudService<PLApplyRegisterDao, PLApplyRegister> {

	/**
	 * 根据合同号查询进件
	 * 
	 * @param contractNo
	 * @return
	 */
	public List<PLApplyRegister> findApplyRegisterByContractNo(String contractNo) {
		return super.dao.findApplyRegisterByContractNo(contractNo);
	}

	/**
	 * 借新还旧新增进件
	 * 
	 * @param plApplyRegister
	 */
	@Transactional(value = "CRE", readOnly = false)
	public void insert(PLApplyRegister plApplyRegister) {
		plApplyRegister.preInsert();
		super.dao.insert(plApplyRegister);
	}

}
