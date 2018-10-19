package com.resoft.credit.product.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.resoft.credit.product.entity.ProductFee;
import com.resoft.credit.product.service.ProductFeeService;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/***
 * 产品费用列表action
 */
@Controller
@RequestMapping(value="/${adminPath}/credit/product/fee")
public class ProductFeeController extends BaseController{
	
	@Resource(name="ProductFeeService")
	private ProductFeeService productFeeService;
	/**
	 * @reqno:H1511130067
	 * @date-designer:20151116-chenshaojia
	 * @date-author:20151116-chenshaojia:CRE_系统管理_产品管理_从业务使用方便考虑，需把【产品管理】功能，从账务系统移动到信贷审批系统，需求不做变动
	 * 从jee_acc合并代码至jee_cre START
	 */
	/**
	 * @reqno:H1509130046
	 * @date-designer:2015年9月22日-songmin
	 * @date-author:2015年9月22日-songmin:查询产品费用列表
	 * @param productFee-productId  必填项
	 */
	@RequestMapping(value = "list")
	public String list(ProductFee productFee, HttpServletRequest request, 
			HttpServletResponse response, Model model) {
		Page<ProductFee> page = productFeeService.findPage(new Page<ProductFee>(request, response), productFee); 
		page.setFuncName("pageFee");
		model.addAttribute("page", page);
		model.addAttribute("ProductFee", productFee);
		return "app/credit/product/productFeeList";
	} 
	
	/**
	 * @reqno:H1509130047
	 * @date-designer:2015年9月22日-songmin
	 * @date-author:2015年9月22日-songmin:跳转产品费用列表-新增、修改页面
	 */
	@RequestMapping(value = "form")
	public String form(ProductFee productFee, Model model) {
		if(null!=productFee && StringUtils.isNotEmpty(productFee.getId())){
			productFee  = productFeeService.get(productFee.getId());
		}
		model.addAttribute("ProductFee", productFee);
		return "app/credit/product/productFeeForm";
	}
	
	/**
	 * @reqno:H1509130047
	 * @date-designer:2015年9月22日-songmin
	 * @date-author:2015年9月22日-songmin:保存、修改产品期限数据
	 */
	@RequestMapping(value = "save")
	public String save(ProductFee productFee, HttpServletResponse response){
		productFeeService.save(productFee);
		return super.renderString(response, "succ");
	}
	/**
	 * @reqno:H1511130067
	 * @date-designer:20151116-chenshaojia
	 * @date-author:20151116-chenshaojia:CRE_系统管理_产品管理_从业务使用方便考虑，需把【产品管理】功能，从账务系统移动到信贷审批系统，需求不做变动
	 * 从jee_acc合并代码至jee_cre END
	 */
}
