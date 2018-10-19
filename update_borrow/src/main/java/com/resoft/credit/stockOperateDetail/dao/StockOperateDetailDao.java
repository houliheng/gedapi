package com.resoft.credit.stockOperateDetail.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.resoft.credit.stockOperateDetail.entity.StockOperateDetail;

/**
 * 股权操作轨迹DAO接口
 * @author jml
 * @version 2017-12-15
 */
@MyBatisDao
public interface StockOperateDetailDao extends CrudDao<StockOperateDetail> {
	
}