package com.resoft.credit.crePurchaseInfo.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.resoft.credit.crePurchaseInfo.entity.PurchaseInfo;
import com.resoft.credit.crePurchaseInfo.dao.PurchaseInfoDao;

/**
 * 采购商品信息Service
 * @author jml
 * @version 2017-11-30
 */
@Service
@Transactional(readOnly = true)
public class PurchaseInfoService extends CrudService<PurchaseInfoDao, PurchaseInfo> {

	public PurchaseInfo get(String id) {
		return super.get(id);
	}
	
	public List<PurchaseInfo> findList(PurchaseInfo purchaseInfo) {
		return super.findList(purchaseInfo);
	}
	
	public Page<PurchaseInfo> findPage(Page<PurchaseInfo> page, PurchaseInfo purchaseInfo) {
		return super.findPage(page, purchaseInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(PurchaseInfo purchaseInfo) {
		super.save(purchaseInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(PurchaseInfo purchaseInfo) {
		super.delete(purchaseInfo);
	}

	@Transactional(value="CRE",readOnly = false)
	public void batchDelete(List<String> idList) {
		super.dao.batchDelete(idList);
	}

	public List<PurchaseInfo> findPurchaseByApplyNo(Map<String, Object> params) {
		return super.dao.findPurchaseByApplyNo(params);
	}

	public List<PurchaseInfo> getInfoByApplyNo(String applyNo) {
		return super.dao.getInfoByApplyNo(applyNo);
	}
	
}