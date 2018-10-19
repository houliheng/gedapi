package com.resoft.postloan.debtColletion.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.postloan.debtColletion.dao.DebtCollectionOperatorDao;
import com.resoft.postloan.debtColletion.entity.DebtCollectionOperator;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;

/**
 * 合同带催收统计Service
 * @author wangguodong
 * @version 2016-06-02
 */
@Service
@DbType("pl.dbType")
@Transactional(value = "PL", readOnly = true)
public class DebtCollectionOperatorService extends CrudService<DebtCollectionOperatorDao, DebtCollectionOperator> {

	public DebtCollectionOperator get(String id) {
		return super.get(id);
	}
	
	public List<DebtCollectionOperator> findList(DebtCollectionOperator DebtCollectionOperator) {
		return super.findList(DebtCollectionOperator);
	}
	
	public Page<DebtCollectionOperator> findPage(Page<DebtCollectionOperator> page, DebtCollectionOperator DebtCollectionOperator) {
		return super.findPage(page, DebtCollectionOperator);
	}
	
	@Transactional(readOnly = false)
	public void save(DebtCollectionOperator DebtCollectionOperator) {
		super.save(DebtCollectionOperator);
	}
	
	@Transactional(readOnly = false)
	public void delete(DebtCollectionOperator DebtCollectionOperator) {
		super.delete(DebtCollectionOperator);
	}
	
}