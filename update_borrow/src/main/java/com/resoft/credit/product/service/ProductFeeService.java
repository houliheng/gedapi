package com.resoft.credit.product.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resoft.credit.product.dao.ProductFeeDao;
import com.resoft.credit.product.entity.ProductFee;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
/**
 * @reqno:H1511130067
 * @date-designer:20151116-chenshaojia
 * @date-author:20151116-chenshaojia:CRE_系统管理_产品管理_从业务使用方便考虑，需把【产品管理】功能，从账务系统移动到信贷审批系统，需求不做变动
 * 从jee_acc合并代码至jee_cre
 */
/***
 * 产品费用列表service
 */
/**
 * @reqno:H1509130046
 * @date-designer:2015年9月22日-songmin
 * @date-author:2015年9月22日-songmin:产品费用列表service
 * 								这里使用到了接口提供的findPage、get、insert、update方法
 */
@Service(value="ProductFeeService") @DbType("cre.dbType")
@Transactional(value="CRE",readOnly=true)
public class ProductFeeService extends CrudService<ProductFeeDao, ProductFee>{

}
