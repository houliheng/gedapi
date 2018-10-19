
package com.resoft.credit.product.service;

import java.util.List;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.product.dao.ProductDao;
import com.resoft.credit.product.entity.Product;
import com.thinkgem.jeesite.common.persistence.Page;import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;

/**
 * @reqno:H1509130076
 * @date-designer:2015年10月12日-songmin
 * @date-author:2015年10月12日-songmin:产品信息表service
 */
@Service @DbType("cre.dbType")
@Transactional(value="CRE",readOnly = true)
public class ProductService extends CrudService<ProductDao, Product> {
	@Autowired
	private RepositoryService repositoryService;
	/**
	 * @reqno:H1509130076
	 * @date-designer:2015年10月12日-songmin
	 * @date-author:2015年10月12日-songmin:根据产品类型获取该产品类型下的所有可用产品记录
	 */
	public List<Product> findProductByProductType(String productType){
		return super.dao.findProductByProductType(productType);
	}
	
	/**
	 * @reqno:H1511100081
	 * @date-designer:2015年11月13日-songmin
	 * @date-author:2015年11月13日-songmin:根据产品类型，获取当前操作员所在机构的可用产品记录
	 * @param productType 产品类型
	 * @param orgId	机构ID
	 */
	public List<Product>  findCoProductByType(Product product){
		return super.dao.findCoProductByType(product);
	}
	public List<Product>  findCoProductByTypeToApply(Product product){
		return super.dao.findCoProductByTypeToApply(product);
	}
	
	/**
	 * @reqno:H1511130067
	 * @date-designer:20151116-chenshaojia
	 * @date-author:20151116-chenshaojia:CRE_系统管理_产品管理_从业务使用方便考虑，需把【产品管理】功能，从账务系统移动到信贷审批系统，需求不做变动
	 * 从jee_acc合并代码至jee_cre START
	 */
	public Page<Product> findPage(Page<Product> page, Product product) {
		return super.findPage(page, product);
		
	}
	
	/**
	 * @reqno: AH1509130043
	 * @date-designer:20150921-gengchang
	 * @date-author:20150921-gengchang:	ACC_系统设置_系统设置_产品管理_新增、修改
	 * 						新增或修改页面的保存功能，保存产品信息
	 * 						
	 */
	@Transactional(value="CRE",readOnly = false)
	public void save(Product product) {
		super.save(product);
	}
	
	@Transactional(value="CRE",readOnly = false)
	public void delete(Product product) {
		super.delete(product);
	}
	
	//查询所有节假日日期
	@Transactional(value="CRE",readOnly = false)
	public int findCodeCount(Product product) {
		return dao.findCodeCount(product);
	}
	/**
	 * @reqno:H1511130067
	 * @date-designer:20151116-chenshaojia
	 * @date-author:20151116-chenshaojia:CRE_系统管理_产品管理_从业务使用方便考虑，需把【产品管理】功能，从账务系统移动到信贷审批系统，需求不做变动
	 * 从jee_acc合并代码至jee_cre END
	 */

	/**
	 * @reqno:H1512160004
	 * @date-designer:2015年12月22日-songmin
	 * @date-author:2015年12月22日-songmin:CRE_信贷审批_系统管理_产品管理_新增、修改、查询_添加【关联流程】数据项
	 * 		查询所有启动（激活）状态的流程实例
	 */
	public List<ProcessDefinition> loadActiveProcessList() {
		ProcessDefinitionQuery processDefinitionQuery = (ProcessDefinitionQuery) this.repositoryService
				.createProcessDefinitionQuery().active().latestVersion()
				.orderByProcessDefinitionKey().asc();
		List<ProcessDefinition> processDefinitionList = processDefinitionQuery.list();
		return processDefinitionList;
	}

}
