package com.resoft.postloan.debtCollectionPhone.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.resoft.postloan.debtCollectionPhone.entity.DebtCollectionPhone;
import com.resoft.postloan.debtCollectionPhone.dao.DebtCollectionPhoneDao;

/**
 * 电话催收Service
 * 
 * @author wangguodong
 * @version 2016-06-02
 */
@Service
@DbType("pl.dbType")
@Transactional(value = "PL", readOnly = true)
public class DebtCollectionPhoneService extends CrudService<DebtCollectionPhoneDao, DebtCollectionPhone> {

	public DebtCollectionPhone get(String id) {
		return super.get(id);
	}

	public List<DebtCollectionPhone> findList(DebtCollectionPhone debtCollectionPhone) {
		return super.findList(debtCollectionPhone);
	}

	public Page<DebtCollectionPhone> findPage(Page<DebtCollectionPhone> page, DebtCollectionPhone debtCollectionPhone) {
		return super.findPage(page, debtCollectionPhone);
	}

	@Transactional(readOnly = false)
	public void save(DebtCollectionPhone debtCollectionPhone) {
		super.save(debtCollectionPhone);
	}

	@Transactional(readOnly = false)
	public void delete(DebtCollectionPhone debtCollectionPhone) {
		super.delete(debtCollectionPhone);
	}

}