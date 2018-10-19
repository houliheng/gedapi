package com.resoft.credit.stockOperateDetail.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.resoft.credit.stockOperateDetail.entity.StockOperateDetail;
import com.resoft.credit.stockOperateDetail.service.StockOperateDetailService;

/**
 * 股权操作轨迹Controller
 * @author jml
 * @version 2017-12-15
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/stockOperateDetail")
public class StockOperateDetailController extends BaseController {

	@Autowired
	private StockOperateDetailService stockOperateDetailService;
	
	@ModelAttribute
	public StockOperateDetail get(@RequestParam(required=false) String id) {
		StockOperateDetail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = stockOperateDetailService.get(id);
		}
		if (entity == null){
			entity = new StockOperateDetail();
		}
		return entity;
	}
	
	@RequiresPermissions("credit:stockOperateDetail:view")
	@RequestMapping(value = {"list", ""})
	public String list(StockOperateDetail stockOperateDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<StockOperateDetail> page = stockOperateDetailService.findCustomPage(new Page<StockOperateDetail>(request, response), stockOperateDetail); 
		model.addAttribute("page", page);
		return "app/credit/stockOperateDetail/stockOperateDetailList";
	}

	@RequiresPermissions("credit:stockOperateDetail:view")
	@RequestMapping(value = "form")
	public String form(StockOperateDetail stockOperateDetail, Model model) {
		model.addAttribute("stockOperateDetail", stockOperateDetail);
		return "app/credit/stockOperateDetail/stockOperateDetailForm";
	}

	@RequiresPermissions("credit:stockOperateDetail:edit")
	@RequestMapping(value = "save")
	public String save(StockOperateDetail stockOperateDetail, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, stockOperateDetail)){
			return form(stockOperateDetail, model);
		}
		stockOperateDetailService.save(stockOperateDetail);
		addMessage(redirectAttributes, "保存股权操作轨迹成功");
		return "redirect:"+Global.getAdminPath()+"/stockOperateDetail/stockOperateDetail/?repage";
	}
	
	@RequiresPermissions("credit:stockOperateDetail:edit")
	@RequestMapping(value = "delete")
	public String delete(StockOperateDetail stockOperateDetail, RedirectAttributes redirectAttributes) {
		stockOperateDetailService.delete(stockOperateDetail);
		addMessage(redirectAttributes, "删除股权操作轨迹成功");
		return "redirect:"+Global.getAdminPath()+"/stockOperateDetail/stockOperateDetail/?repage";
	}

}