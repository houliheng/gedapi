package com.resoft.postloan.collectionPaymentInfo.web;

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
import com.resoft.postloan.collectionPaymentInfo.entity.CollectionPaymentInfo;
import com.resoft.postloan.collectionPaymentInfo.service.CollectionPaymentInfoService;

/**
 * 客户催收回款详情Controller
 * @author yanwanmei
 * @version 2016-06-14
 */
@Controller
@RequestMapping(value = "${adminPath}/postloan/collectionPaymentInfo")
public class CollectionPaymentInfoController extends BaseController {

	@Autowired
	private CollectionPaymentInfoService collectionPaymentInfoService;
	
	@ModelAttribute
	public CollectionPaymentInfo get(@RequestParam(required=false) String id) {
		CollectionPaymentInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = collectionPaymentInfoService.get(id);
		}
		if (entity == null){
			entity = new CollectionPaymentInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("postloan:collectionPaymentInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(CollectionPaymentInfo collectionPaymentInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CollectionPaymentInfo> page = collectionPaymentInfoService.findPage(new Page<CollectionPaymentInfo>(request, response), collectionPaymentInfo); 
		model.addAttribute("page", page);
		return "app/postloan/collectionPaymentInfo/collectionPaymentInfoList";
	}

	@RequiresPermissions("postloan:collectionPaymentInfo:view")
	@RequestMapping(value = "form")
	public String form(CollectionPaymentInfo collectionPaymentInfo, Model model) {
		model.addAttribute("collectionPaymentInfo", collectionPaymentInfo);
		return "app/postloan/collectionPaymentInfo/collectionPaymentInfoForm";
	}

	@RequiresPermissions("postloan:collectionPaymentInfo:edit")
	@RequestMapping(value = "save")
	public String save(CollectionPaymentInfo collectionPaymentInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, collectionPaymentInfo)){
			return form(collectionPaymentInfo, model);
		}
		collectionPaymentInfoService.save(collectionPaymentInfo);
		addMessage(redirectAttributes, "保存客户催收回款详情成功");
		return "redirect:"+Global.getAdminPath()+"/CollectionPaymentInfo/collectionPaymentInfo/?repage";
	}
	
	@RequiresPermissions("postloan:collectionPaymentInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(CollectionPaymentInfo collectionPaymentInfo, RedirectAttributes redirectAttributes) {
		collectionPaymentInfoService.delete(collectionPaymentInfo);
		addMessage(redirectAttributes, "删除客户催收回款详情成功");
		return "redirect:"+Global.getAdminPath()+"/CollectionPaymentInfo/collectionPaymentInfo/?repage";
	}

}