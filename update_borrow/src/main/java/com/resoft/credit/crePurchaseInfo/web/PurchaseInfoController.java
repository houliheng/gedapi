package com.resoft.credit.crePurchaseInfo.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Maps;
import com.resoft.common.utils.AjaxView;
import com.resoft.credit.crePurchaseInfo.entity.PurchaseInfo;
import com.resoft.credit.crePurchaseInfo.service.PurchaseInfoService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 采购商品信息Controller
 * @author jml
 * @version 2017-11-30
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/purchaseInfo")
public class PurchaseInfoController extends BaseController {

	@Autowired
	private PurchaseInfoService purchaseInfoService;
	
	@ModelAttribute
	public PurchaseInfo get(@RequestParam(required=false) String id) {
		PurchaseInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = purchaseInfoService.get(id);
		}
		if (entity == null){
			entity = new PurchaseInfo();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(String taskDefKey,PurchaseInfo purchaseInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		String applyNo=purchaseInfo.getApplyNo();
		Map<String, Object> params = Maps.newHashMap();
		params.put("applyNo",applyNo);
		List<PurchaseInfo> purchaseInfoList = purchaseInfoService.findPurchaseByApplyNo(params); 
		model.addAttribute("purchaseInfoList", purchaseInfoList);
		model.addAttribute("applyNo",applyNo);
		model.addAttribute("hideInHtmq",taskDefKey);
		return "app/credit/crePurchaseInfo/purchaseInfoList";
	}

	@RequestMapping(value = "form")
	public String form(PurchaseInfo purchaseInfo, Model model) {
		if(StringUtils.isNotEmpty(purchaseInfo.getId())){
			PurchaseInfo purchaseInfoNew = purchaseInfoService.get(purchaseInfo.getId());
			model.addAttribute("purchaseInfo", purchaseInfoNew);
		}else{
			model.addAttribute("purchaseInfo", purchaseInfo);
		}
		return "app/credit/crePurchaseInfo/purchaseInfoForm";
	}

	@RequestMapping(value = "save")
	@ResponseBody
	public AjaxView save(PurchaseInfo purchaseInfo, Model model, RedirectAttributes redirectAttributes) {
		AjaxView rtn = new AjaxView();
		try {
			purchaseInfoService.save(purchaseInfo);
			addMessage(model, "保存采购信息成功");
			model.addAttribute("close", "close");
			rtn.setSuccess().setMessage("保存采购信息成功!");
		} catch (Exception e) {
			logger.error("保存采购信息失败！", e);
			addMessage(model, "保存采购信息失败！");
			rtn.setFailed().setMessage("保存采购信息失败!");
		}
		return rtn;
	}
	
	@ResponseBody
	@RequestMapping(value = "delete")
	public AjaxView delete(@RequestParam("ids")List<String> idList) {
		AjaxView rtn = new AjaxView();
		try {
			purchaseInfoService.batchDelete(idList);;
			rtn.setSuccess().setMessage("删除采购信息成功!");
		} catch (Exception e) {
			logger.error("删除关联企业失败", e);
			rtn.setFailed().setMessage("删除采购信息失败!");
		}
		return rtn;
	}

}