
package com.resoft.credit.product.dao;

import java.util.List;

import com.resoft.credit.product.entity.Product;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

/**
 * @reqno:H1509130076
 * @date-designer:2015年10月12日-songmin
 * @date-author:2015年10月12日-songmin:查询产品信息dao
 */
@MyBatisDao
public interface ProductDao extends CrudDao<Product> {
	/**
	 * @reqno:H1509130076
	 * @date-designer:2015年10月12日-songmin
	 * @date-author:2015年10月12日-songmin:根据产品类型获取该产品类型下的所有可用产品记录
	 */
	public List<Product> findProductByProductType(String productType);
	
	/**
	 * @reqno:H1511100081
	 * @date-designer:2015年11月13日-songmin
	 * @date-author:2015年11月13日-songmin:根据产品类型，获取当前操作员所在机构的可用产品记录
	 * @param productType 产品类型
	 * @param orgId	机构ID
	 */
	public List<Product>  findCoProductByType(Product product);
	/**
	 * 根据产品类型查询产品列表（进件时用）
	 * @param product
	 * @return
	 */
	public List<Product>  findCoProductByTypeToApply(Product product);
	/**
	 * @reqno:H1511130067
	 * @date-designer:20151116-chenshaojia
	 * @date-author:20151116-chenshaojia:CRE_系统管理_产品管理_从业务使用方便考虑，需把【产品管理】功能，从账务系统移动到信贷审批系统，需求不做变动
	 * 查询对应产品编码的记录条数，如果存在说明表中有已存在的同名产品编码
	 */
	public int findCodeCount(Product product);
	
	
}
