package com.resoft.credit.gqgetComInfo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.gqgetComInfo.dao.WarehouseGoodsDao;
import com.resoft.credit.gqgetComInfo.entity.WarehouseGoods;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;

/**
 * 冠E通Service
 * 
 * @author yanwanmei
 * @version 2016-08-08
 */
@Service
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class WarehouseGoodsService extends CrudService<WarehouseGoodsDao, WarehouseGoods> {

	public WarehouseGoods get(String id) {
		return super.get(id);
	}
	
	public WarehouseGoods getWarehouseGoods(String id) {
		return this.dao.getWarehouseGoods(id);
	}

	public List<WarehouseGoods> findList(WarehouseGoods warehouseGoods) {
		return super.findList(warehouseGoods);
	}

	public Page<WarehouseGoods> findPage(Page<WarehouseGoods> page, WarehouseGoods warehouseGoods) {
		return super.findPage(page, warehouseGoods);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void save(WarehouseGoods warehouseGoods) {
		super.save(warehouseGoods);
	}
	
	@Transactional(value = "CRE", readOnly = false)
	public void updateWarehouseGoodsUnion(WarehouseGoods warehouseGoods) {
		this.dao.updateWarehouseGoodsUnion(warehouseGoods);
	}
	
	@Transactional(value = "CRE", readOnly = false)
	public void saveWarehouseGoodsUnion(WarehouseGoods warehouseGoods) {
		this.dao.saveWarehouseGoodsUnion(warehouseGoods);
	}

	public List<WarehouseGoods> getPageByApplyNo(String applyNo) {
		return super.dao.getPageByApplyNo(applyNo);
	}
	
	public List<WarehouseGoods>getPageByApplyNoUnion(WarehouseGoods warehouseGoods) {
		return super.dao.getPageByApplyNoUnion(warehouseGoods);
	}
}