package com.resoft.postloan.debtCollectionOut.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.resoft.postloan.debtCollectionOut.entity.DebtCollectionOut;
import com.resoft.postloan.debtCollectionOut.dao.DebtCollectionOutDao;

/**
 * 外包催收Service
 * 
 * @author wangguodong
 * @version 2016-06-02
 */
@Service
@DbType("pl.dbType")
@Transactional(value = "PL", readOnly = true)
public class DebtCollectionOutService extends CrudService<DebtCollectionOutDao, DebtCollectionOut> {

	public DebtCollectionOut get(String id) {
		return super.get(id);
	}

	public List<DebtCollectionOut> findList(DebtCollectionOut debtCollectionOut) {
		return super.findList(debtCollectionOut);
	}

	public Page<DebtCollectionOut> findPage(Page<DebtCollectionOut> page, DebtCollectionOut debtCollectionOut) {
		return super.findPage(page, debtCollectionOut);
	}

	@Transactional(readOnly = false)
	public void save(DebtCollectionOut debtCollectionOut) {
		super.save(debtCollectionOut);
	}

	@Transactional(readOnly = false)
	public void delete(DebtCollectionOut debtCollectionOut) {
		super.delete(debtCollectionOut);
	}

}