package com.resoft.credit.product.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.resoft.credit.product.entity.ProductPeriod;
import com.resoft.credit.product.service.ProductPeriodService;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
/***
 * 产品期限表Controller
 * @author songmin
 *
 */
@Controller
@RequestMapping("/${adminPath}/credit/product/period")
public class ProductPeriodController extends BaseController{
	@Autowired
	private ProductPeriodService productPeriodService;//产品期限表sercice
	/**
	 * @reqno:H1511130067
	 * @date-designer:20151116-chenshaojia
	 * @date-author:20151116-chenshaojia:CRE_系统管理_产品管理_从业务使用方便考虑，需把【产品管理】功能，从账务系统移动到信贷审批系统，需求不做变动
	 * 从jee_acc合并代码至jee_cre START
	 */
	/**
	 * @reqno:H1509130044
	 * @date-designer:2015年9月22日-songmin
	 * @date-author:2015年9月22日-songmin:查询产品期限列表
	 */
	@RequestMapping(value = "list")
	public String list(ProductPeriod productPeriod, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProductPeriod> page = productPeriodService.findPage(new Page<ProductPeriod>(request, response), productPeriod); 
		model.addAttribute("page", page);
		model.addAttribute("productPeriod", productPeriod);
		page.setFuncName("pagePeroid");
		return "app/credit/product/productPeroidList";
	} 
	
	/**
	 * @reqno: H1509130045
	 * @date-designer : 2015年9月26日 - songmin
	 * @date-author : 2015年9月26日 - songmin:点击“添加”按钮，跳转至产品期限添加页面，点击“修改”按钮，跳转至产品期限修改页面
	 * 		方法内部根据ID是否有值判断操作类型：添加、修改
	 * 			当前操作为修改时，查询对应的产品期限信息
	 */
	@RequestMapping(value = "form")
	public String form(ProductPeriod productPeriod, Model model) {
		if(null!=productPeriod && StringUtils.isNotEmpty(productPeriod.getId())){
			/**
			 * @reqno:H1601150020
			 * @date-designer:2016年1月15日-songmin
			 * @date-author:2016年1月15日-songmin:修改了调用方法，在代码块项目何必时，产生了同名的查询SQL，且无法做到共用查询SQL，所以进行了后面的查询SQL进行了重命名处理
			 */
			productPeriod  = productPeriodService.getById(productPeriod.getId());
		}
		model.addAttribute("productPeriod", productPeriod);
		return "app/credit/product/productPeroidForm";
	}
	/**
	 * @reqno: H1509130045
	 * @date-designer : 2015年9月26日 - songmin
	 * @db-z : ACC_PRODUCT_PERIOD : id，product_id，period_value，period_type，year_rate，del_flag，remarks
	 * @db-j : SYS_DICT : LABEL，VALUE
	 * @date-author : 2015年9月26日 - songmin:新增、修改产品期限信息
	 */
	@RequestMapping(value = "save")
	public String save(ProductPeriod productPeriod,HttpServletRequest request, 
			HttpServletResponse response, Model model, 
			RedirectAttributes redirectAttributes){
		productPeriodService.save(productPeriod);
		return super.renderString(response, "succ");
	}
	/**
	 * @reqno:H1511130067
	 * @date-designer:20151116-chenshaojia
	 * @date-author:20151116-chenshaojia:CRE_系统管理_产品管理_从业务使用方便考虑，需把【产品管理】功能，从账务系统移动到信贷审批系统，需求不做变动
	 * 从jee_acc合并代码至jee_cre END
	 */
	/**
	 * @reqno:H1509230044
	 * @date-designer:2015年10月13日-songmin
	 * @date-author:2015年10月13日-songmin:根据传入的产品ID，获取该产品下所有可用的产品期限数据 ，请求方式：ajax
	 */
	@RequestMapping(value="useablePeriod")
	@ResponseBody
	public List<ProductPeriod> loadProductPeriod(String productId){
		List<ProductPeriod>  productPeriodList = productPeriodService.findUseablePeriodByProductId(productId);
		return productPeriodList;
	}
}
