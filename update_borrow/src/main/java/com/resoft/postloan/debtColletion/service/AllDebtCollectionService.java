package com.resoft.postloan.debtColletion.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.postloan.debtColletion.dao.AllDebtCollectionDao;
import com.resoft.postloan.debtColletion.entity.DebtCollection;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;

/**
 * 合同带催收统计Service
 * 
 * @author wangguodong
 * @version 2016-06-02
 */
@Service
@DbType("pl.dbType")
@Transactional(value = "PL", readOnly = true)
public class AllDebtCollectionService extends CrudService<AllDebtCollectionDao, DebtCollection> {

	public DebtCollection get(String id) {
		return super.get(id);
	}

	public List<DebtCollection> findList(DebtCollection debtCollection) {
		return super.findList(debtCollection);
	}

	public Page<DebtCollection> findPage(Page<DebtCollection> page, DebtCollection debtCollection) {
		return super.findPage(page, debtCollection);
	}

}