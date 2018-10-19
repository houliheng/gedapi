package com.resoft.accounting.discount.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.accounting.deductResult.entity.DeductResult;
import com.resoft.accounting.discount.dao.DiscountVoDao;
import com.resoft.accounting.discount.entity.AccDiscount;
import com.resoft.accounting.discount.entity.DiscountVo;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;

/**
* @author guoshaohua
* @version 2018年5月23日 下午12:04:03
* 
*/
@Service
@DbType("acc.dbType")
@Transactional(value = "ACC", readOnly = true)
public class DiscountVoService extends CrudService<DiscountVoDao,DiscountVo>{
	public DiscountVo get(String id) {
		return super.get(id);
	}
	
	public List<DiscountVo> findList(DiscountVo discountVo) {
		return super.findList(discountVo);
	}
	
	public Page<DiscountVo> findPage(Page<DiscountVo> page, DiscountVo discountVo) {
		return super.findPage(page, discountVo);
	}
	
	public void save(DiscountVo discountVo) {
		super.save(discountVo);
	}
	
	@Transactional(readOnly = false)
	public void delete(DiscountVo discountVo) {
		super.delete(discountVo);
	}
	
}
