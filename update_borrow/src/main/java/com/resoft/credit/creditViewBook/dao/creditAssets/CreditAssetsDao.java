package com.resoft.credit.creditViewBook.dao.creditAssets;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.resoft.credit.creditViewBook.entity.creditAssets.CreditAssets;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 信审意见书资产清单DAO接口
 * @author wuxi01
 * @version 2016-02-29
 */
@MyBatisDao
public interface CreditAssetsDao extends CrudDao<CreditAssets> {
	
	public void batchDelete(@Param("idList")List<String> idList);
	
	public List<CreditAssets> getCreditAssetsByApplyNo(@Param("applyNo")String applyNo);
}