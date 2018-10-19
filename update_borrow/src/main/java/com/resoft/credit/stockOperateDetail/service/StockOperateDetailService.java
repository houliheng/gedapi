package com.resoft.credit.stockOperateDetail.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.resoft.credit.stockOperateDetail.entity.StockOperateDetail;
import com.resoft.credit.stockOperateDetail.dao.StockOperateDetailDao;

/**
 * 股权操作轨迹Service
 * @author jml
 * @version 2017-12-15
 */
@Service
@Transactional(readOnly = true)
public class StockOperateDetailService extends CrudService<StockOperateDetailDao, StockOperateDetail> {

	public StockOperateDetail get(String id) {
		return super.get(id);
	}
	
	public List<StockOperateDetail> findList(StockOperateDetail stockOperateDetail) {
		return super.findList(stockOperateDetail);
	}
	
	public Page<StockOperateDetail> findPage(Page<StockOperateDetail> page, StockOperateDetail stockOperateDetail) {
		return super.findPage(page, stockOperateDetail);
	}
	
	@Transactional(readOnly = false)
	public void save(StockOperateDetail stockOperateDetail) {
		super.save(stockOperateDetail);
	}
	
	@Transactional(readOnly = false)
	public void delete(StockOperateDetail stockOperateDetail) {
		super.delete(stockOperateDetail);
	}
	
}