package com.resoft.credit.product.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.resoft.common.utils.AjaxView;
import com.resoft.common.utils.Constants;
import com.resoft.credit.product.entity.Product;
import com.resoft.credit.product.service.ProductService;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * 产品信息表Controller
 * 
 * @author songmin
 */
@Controller
@RequestMapping("${adminPath}/credit/product/")
public class ProductController extends BaseController {
	@Autowired
	private ProductService productService;

	/**
	 * @reqno:H1509230044
	 * @date-designer:2015年10月14日-songmin
	 * @date-author:2015年10月14日-songmin:根据传入的产品类型，查询该类型下所有“可用”的产品列表， 请求方式：ajax
	 */
	@RequestMapping("/productList")
	@ResponseBody
	public List<Product> loadProductList(String productType) {
		List<Product> products = productService.findProductByProductType(productType);
		return products;
	}

	/**
	 * @reqno:H1511100081
	 * @date-designer:2015年11月13日-songmin
	 * @date-author:2015年11月13日-songmin:根据产品类型，获取当前操作员所在机构的可用产品记录
	 * @param productType
	 *            产品类型
	 * @param orgId
	 *            机构ID
	 */
	@RequestMapping("/coProductList")
	@ResponseBody
	public List<Product> loadCoProductList(String productType, String orgId, String procDefKey) {
		Product product = new Product();
		product.setCompany(new Office(orgId));
		product.setProductTypeCode(productType);
		product.setProcDefKey(procDefKey);
		List<Product> products = productService.findCoProductByType(product);
		return products;
	}

	@RequestMapping("/applyProductList")
	@ResponseBody
	public List<Product> loadApplyProductList(String productType, String custType, String orgId, String procDefKey) {
		Product product = new Product();
		product.setCompany(new Office(orgId));
		product.setProductTypeCode(productType);
		product.setProcDefKey(procDefKey);
		List<Product> products = Lists.newArrayList();
		products = productService.findCoProductByTypeToApply(product);
		if (products != null && products.size() != 0) {
			if (Constants.CUST_TYPE_GR.equals(custType)) {
				for (int i = 0; i < products.size(); i++) {
					if (Constants.PROC_DEF_KEY_LHSX.equals(products.get(i).getProcDefKey())) {
						products.remove(i);
					}
				}
			}
		}
		return products;
	}

	/**
	 * @reqno:H1511130067
	 * @date-designer:20151116-chenshaojia
	 * @date-author:20151116-chenshaojia:CRE_系统管理_产品管理_从业务使用方便考虑，需把【产品管理】功能，从账务系统移动到信贷审批系统，需求不做变动 
	 *                                                                                            从jee_acc合并代码至jee_cre
	 *                                                                                            START
	 */
	/**
	 * @reqno: AH1509130042
	 * @date-designer:20150921-gengchang
	 * @date-author:20150921-gengchang: ACC_系统设置_系统设置_产品管理_查询
	 *                                  list方法方法findPage()方法查询分页数据
	 *                                  ，调用的方法有：findPage()查询分页数据方法，
	 *                                  获得分页总数--getCount(),查询所有数据列表--findList()
	 */
	@RequestMapping(value = { "list", "" })
	public String list(Product product, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Product> page = productService.findPage(new Page<Product>(request, response), product);
		model.addAttribute("page", page);
		return "app/credit/product/productList";
	}

	// 新增对产品编码做唯一性校验
	@RequestMapping(value = "validate")
	public String validate(Product product, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		// 查询前台的产品编码在数据表中是否有对应记录存在
		int codeCount = productService.findCodeCount(product);
		// 如果记录总数大于0，表示数据表中已经存在该产品编码对应的记录
		if (codeCount > 0) {
			return super.renderString(response, "产品编码已存在");
		} else {
			return super.renderString(response, true);
		}
	}

	/**
	 * @reqno: AH1509130043
	 * @date-designer:20150921-gengchang
	 * @date-author:20150921-gengchang: ACC_系统设置_系统设置_产品管理_新增、修改
	 *                                  新增或修改页面的保存功能，保存产品信息
	 */
	/**
	 * @reqno:H1511130067
	 * @date-designer:20151127-chenshaojia
	 * @date-author:20151127-chenshaojia:CRE_系统管理_产品管理_从业务使用方便考虑，需把【产品管理】功能，从账务系统移动到信贷审批系统，修改保存产品过后返回到产品列表
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxView save(Product product, Model model, RedirectAttributes redirectAttributes) {
		AjaxView ajaxView = new AjaxView();
		// 当code为空时，即表示进入了修改页面，页面获取的是code1 的值，此处要将code1的值赋给code
		if (StringUtils.isBlank(product.getProductCode())) {
			product.setProductCode(product.getProductCode1());
		}
		try {
			productService.save(product);
			ajaxView.setSuccess().setMessage("保存产品信息" + product.getProductName() + "成功");
		} catch (Exception e) {
			logger.error("保存产品信息失败。", e);
			ajaxView.setFailed().setMessage("保存产品信息失败。");
		}
		return ajaxView;
	}

	/**
	 * @reqno: AH1509130043
	 * @date-designer:20150921-gengchang
	 * @date-author:20150921-gengchang: ACC_系统设置_系统设置_产品管理_新增、修改
	 *                                  根据取得的id为空时判断，为空表示是产品添加；不为空则进入修改页面
	 *                                  产品新增,新增之前查询出已存在产品编码，已便前台验证
	 */
	@RequestMapping(value = "add")
	public String add(Product product, HttpServletRequest request, HttpServletResponse response, Model model) {
		// 当取得的id为空时，表示是产品添加；不为空则进入修改页面
		if (StringUtils.isNotBlank(product.getId())) {
			Product pro = productService.get(product.getId());
			model.addAttribute("product", pro);
		}
		/**
		 * @reqno:H1512160004
		 * @date-designer:2015年12月22日-songmin
		 * @date-author:2015年12月22日-songmin:CRE_信贷审批_系统管理_产品管理_新增、修改、查询_添加【关联流程】数据项 
		 *                                                                          进入产品新增
		 *                                                                          、
		 *                                                                          修改页面前
		 *                                                                          ，
		 *                                                                          查询当前系统中非挂起状态流程实例
		 */
		List<ProcessDefinition> processList = productService.loadActiveProcessList();
		model.addAttribute("processList", processList);
		return "app/credit/product/productForm";
	}

	@RequestMapping(value = "delete")
	public String delete(Product product, RedirectAttributes redirectAttributes) {
		productService.delete(product);
		addMessage(redirectAttributes, "删除产品成功");
		return "redirect:" + adminPath + "/credit/product/";
	}
	/**
	 * @reqno:H1511130067
	 * @date-designer:20151116-chenshaojia
	 * @date-author:20151116-chenshaojia:CRE_系统管理_产品管理_从业务使用方便考虑，需把【产品管理】功能，从账务系统移动到信贷审批系统，需求不做变动 
	 *                                                                                            从jee_acc合并代码至jee_cre
	 *                                                                                            END
	 */
}
