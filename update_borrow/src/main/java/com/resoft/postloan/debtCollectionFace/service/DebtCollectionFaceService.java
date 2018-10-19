package com.resoft.postloan.debtCollectionFace.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.resoft.postloan.debtCollectionFace.entity.DebtCollectionFace;
import com.resoft.postloan.debtCollectionFace.dao.DebtCollectionFaceDao;

/**
 * 上门催收Service
 * @author wangguodong
 * @version 2016-06-02
 */
@Service
@DbType("pl.dbType")
@Transactional(value="PL",readOnly = true)
public class DebtCollectionFaceService extends CrudService<DebtCollectionFaceDao, DebtCollectionFace> {

	public DebtCollectionFace get(String id) {
		return super.get(id);
	}
	
	public List<DebtCollectionFace> findList(DebtCollectionFace debtCollectionFace) {
		return super.findList(debtCollectionFace);
	}
	
	public Page<DebtCollectionFace> findPage(Page<DebtCollectionFace> page, DebtCollectionFace debtCollectionFace) {
		return super.findPage(page, debtCollectionFace);
	}
	
	@Transactional(readOnly = false)
	public void save(DebtCollectionFace debtCollectionFace) {
		super.save(debtCollectionFace);
	}
	
	@Transactional(readOnly = false)
	public void delete(DebtCollectionFace debtCollectionFace) {
		super.delete(debtCollectionFace);
	}
	
}