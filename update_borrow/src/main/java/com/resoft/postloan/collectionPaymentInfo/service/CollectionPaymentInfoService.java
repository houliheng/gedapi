package com.resoft.postloan.collectionPaymentInfo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.resoft.postloan.collectionPaymentInfo.entity.CollectionPaymentInfo;
import com.resoft.postloan.collectionPaymentInfo.dao.CollectionPaymentInfoDao;

/**
 * 客户催收回款详情Service
 * @author yanwanmei
 * @version 2016-06-14
 */
@Service
@DbType("pl.dbType")
@Transactional(value="PL",readOnly = true)
public class CollectionPaymentInfoService extends CrudService<CollectionPaymentInfoDao, CollectionPaymentInfo> {

	public CollectionPaymentInfo get(String id) {
		return super.get(id);
	}
	
	public List<CollectionPaymentInfo> findList(CollectionPaymentInfo collectionPaymentInfo) {
		return super.findList(collectionPaymentInfo);
	}
	
	public Page<CollectionPaymentInfo> findPage(Page<CollectionPaymentInfo> page, CollectionPaymentInfo collectionPaymentInfo) {
		return super.findPage(page, collectionPaymentInfo);
	}
	
	@Transactional(value="PL",readOnly = false)
	public void save(CollectionPaymentInfo collectionPaymentInfo) {
		super.save(collectionPaymentInfo);
	}
	
	@Transactional(value="PL",readOnly = false)
	public void delete(CollectionPaymentInfo collectionPaymentInfo) {
		super.delete(collectionPaymentInfo);
	}
	
}