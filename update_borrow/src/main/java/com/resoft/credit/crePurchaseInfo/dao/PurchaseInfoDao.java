package com.resoft.credit.crePurchaseInfo.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.resoft.credit.crePurchaseInfo.entity.PurchaseInfo;

/**
 * 采购商品信息DAO接口
 * @author jml
 * @version 2017-11-30
 */
@MyBatisDao
public interface PurchaseInfoDao extends CrudDao<PurchaseInfo> {

	public void batchDelete(@Param("idList")List<String> idList);

	public List<PurchaseInfo> findPurchaseByApplyNo(Map<String, Object> params);

	public List<PurchaseInfo> getInfoByApplyNo(@Param("applyNo")String applyNo);
	
}