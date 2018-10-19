package com.resoft.postloan.debtColletion.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.postloan.debtColletion.dao.DoneDebtCollectionDao;
import com.resoft.postloan.debtColletion.entity.DoneDebtCollection;
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
public class DoneDebtCollectionService extends CrudService<DoneDebtCollectionDao, DoneDebtCollection> {

	
	public DoneDebtCollection get(String id) {
		return super.get(id);
	}

	public List<DoneDebtCollection> findList(DoneDebtCollection toDoDebtCollection) {
		return super.findList(toDoDebtCollection);
	}

	public Page<DoneDebtCollection> findPage(Page<DoneDebtCollection> page, DoneDebtCollection toDoDebtCollection) {
		return super.findPage(page, toDoDebtCollection);
	}

}