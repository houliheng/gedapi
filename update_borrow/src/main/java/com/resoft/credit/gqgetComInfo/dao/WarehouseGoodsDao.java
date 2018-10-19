package com.resoft.credit.gqgetComInfo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.resoft.credit.gqgetComInfo.entity.WarehouseGoods;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * 冠E通DAO接口(库存货物)
 * 
 * @author wangguodong
 * @version 2016-09-19
 */
@MyBatisDao
public interface WarehouseGoodsDao extends CrudDao<WarehouseGoods> {

	public List<WarehouseGoods> getPageByApplyNo(String applyNo);
	
	public List<WarehouseGoods> getPageByApplyNoUnion(WarehouseGoods warehouseGoods);

	public void saveWarehouseGoodsUnion(WarehouseGoods warehouseGoods);
	
	public void updateWarehouseGoodsUnion(WarehouseGoods warehouseGoods);

	public WarehouseGoods getWarehouseGoods(String id);
	
	void saveWarehouseGoodsTogether(@Param("warehouseGoodsList") List<WarehouseGoods> warehouseGoods);
	
}