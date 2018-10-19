package com.resoft.credit.stockWebCheck.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.credit.stockWebCheck.entity.StockWebCheck;

/**
 * 一次网查（企查查）DAO接口
 * @author jml
 * @version 2017-09-07
 */
@MyBatisDao
public interface StockWebCheckDao extends CrudDao<StockWebCheck> {

	public StockWebCheck getByApplyNo(String applyNo);
	
}