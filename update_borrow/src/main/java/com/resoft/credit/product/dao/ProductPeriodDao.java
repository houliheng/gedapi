package com.resoft.credit.product.dao;

import java.util.List;

import com.resoft.credit.product.entity.ProductPeriod;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * @reqno:H1509130076
 * @date-designer:2015年10月12日-songmin
 * @date-author:2015年10月12日-songmin:产品期限service
 */
@MyBatisDao(value="ProductPeriodDao")
public interface ProductPeriodDao extends CrudDao<ProductPeriod>{

	/**
	 根据产品ID，获取该产品下所有可用的产品期限配置信息
	 */
	public List<ProductPeriod>  findUseablePeriodByProductId(String productId);
	
	/**
	 根据产品期限ID获取产品期限信息
	 */
	public ProductPeriod getById(String id);
	/**
	 根据申请编号获取登记时所选的产品的期限值
	 */
	public List<ProductPeriod> findPeriodByApplyNo(String applyNo);
}
