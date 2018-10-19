package com.resoft.credit.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.product.dao.ProductPeriodDao;
import com.resoft.credit.product.entity.ProductPeriod;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
/**
 * @reqno:H1509130076
 * @date-designer:2015年10月12日-songmin
 * @date-author:2015年10月12日-songmin:产品期限service
 */
@Service @DbType("cre.dbType")
@Transactional(value="CRE",readOnly=true)
public class ProductPeriodService extends CrudService<ProductPeriodDao, ProductPeriod>{

	/**
	 * @reqno:H1509130076
	 * @date-designer:2015年10月12日-songmin
	 * @date-author:2015年10月12日-songmin:根据产品ID，获取该产品下所有可用的产品期限配置信息
	 */
	public List<ProductPeriod>  findUseablePeriodByProductId(String productId){
		return super.dao.findUseablePeriodByProductId(productId);
	}
	
	/**
	 * @reqno:H1601150020
	 * @date-designer:2016年1月15日-songmin
	 * @date-author:2016年1月15日-songmin:根据产品期限ID获取产品期限信息
	 */
	public ProductPeriod getById(String id){
		return super.dao.getById(id);
	}
	
	public List<ProductPeriod> findPeriodByApplyNo(String applyNo) {
		return super.dao.findPeriodByApplyNo(applyNo);
	}
}
