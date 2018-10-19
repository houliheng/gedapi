package com.resoft.credit.creditViewBook.web.creditAssets;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.google.common.collect.Maps;
import com.resoft.credit.applyRelation.service.ApplyRelationService;
import com.resoft.credit.creditViewBook.entity.creditAssets.CreditAssets;
import com.resoft.credit.creditViewBook.service.creditAssets.CreditAssetsService;

/**
 * 信审意见书资产清单Controller
 * 
 * @author wuxi01
 * @version 2016-02-29
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/creditViewBook/creditAssets")
public class CreditAssetsController extends BaseController {

	@Autowired
	private CreditAssetsService creditAssetsService;

	@Autowired
	private ApplyRelationService applyRelationService;

	@ModelAttribute
	public CreditAssets get(@RequestParam(required = false) String id) {
		CreditAssets entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = creditAssetsService.get(id);
		}
		if (entity == null) {
			entity = new CreditAssets();
		}
		return entity;
	}

	@RequiresPermissions("credit:creditViewBook:creditAssets:view")
	@RequestMapping(value = { "list", "" })
	public String list(CreditAssets creditAssets, HttpServletRequest request, HttpServletResponse response, Model model) {
		String applyNo = request.getParameter("applyNo");
		List<CreditAssets> creditAssetsList = creditAssetsService.getCreditAssetsByApplyNo(applyNo);
		model.addAttribute("creditAssetsList", creditAssetsList);
		return "app/credit/creditViewBook/creditAssets/creditAssetsList";
	}

	@RequiresPermissions("credit:creditViewBook:creditAssets:view")
	@RequestMapping(value = "form")
	public String form(CreditAssets creditAssets, Model model, HttpServletRequest request) {
		model.addAttribute("creditAssets", creditAssets);
		if ("true".equals(request.getParameter("readOnly"))) {
			model.addAttribute("readOnly", true);
		}
		String applyNo = creditAssets.getApplyNo();
		if (creditAssets != null && StringUtils.isNotBlank(creditAssets.getId())) {
			String roleType = creditAssets.getRoleType();
			Map<String, String> params = Maps.newConcurrentMap();
			params.put("applyNo", applyNo);
			params.put("roleType", roleType);
			List<CreditAssets> CreditAssetses = applyRelationService.findCustNameByRoleTypeOnCreditAssets(params);
			model.addAttribute("custNameMap", CreditAssetses);
		}
		return "app/credit/creditViewBook/creditAssets/creditAssetsForm";
	}

	@RequiresPermissions("credit:creditViewBook:creditAssets:edit")
	@RequestMapping(value = "save")
	public String save(CreditAssets creditAssets, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
	
		try{
			creditAssetsService.save(creditAssets);
			model.addAttribute("saveMessage", "保存信审意见书资产清单成功");
		}catch(Exception e){
			logger.error("保存信审意见书资产清单失败", e);
			model.addAttribute("saveMessage", "保存信审意见书资产清单失败");
		}
		
		model.addAttribute("closeWindow", true);
		return form(creditAssets, model, request);
	}

	@RequiresPermissions("credit:creditViewBook:creditAssets:edit")
	@RequestMapping(value = "delete")
	public String delete(CreditAssets creditAssets, RedirectAttributes redirectAttributes) {
		creditAssetsService.delete(creditAssets);
		addMessage(redirectAttributes, "删除信审意见书资产清单成功");
		return "redirect:" + Global.getAdminPath() + "/creditViewBook/creditAssets/?repage";
	}

	// 批量删除方法
	@RequestMapping(value = "batchDelete")
	@ResponseBody
	public String batchDelete(String ids, HttpServletResponse response) {
		if (StringUtils.isNotEmpty(ids)) {
			List<String> idList = Arrays.asList(ids.split(","));
			creditAssetsService.batchDelete(idList);
		}
		return "success";
	}

}