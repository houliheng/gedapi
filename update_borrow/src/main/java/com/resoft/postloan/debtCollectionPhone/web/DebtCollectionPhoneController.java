package com.resoft.postloan.debtCollectionPhone.web;

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

import com.google.common.collect.Maps;
import com.resoft.multds.accounting.PLContract.entity.PLContract;
import com.resoft.multds.accounting.PLContract.service.PLContractService;
import com.resoft.postloan.common.utils.AjaxView;
import com.resoft.postloan.common.utils.Constants;
import com.resoft.postloan.debtCollectionPhone.entity.DebtCollectionPhone;
import com.resoft.postloan.debtCollectionPhone.service.DebtCollectionPhoneService;
import com.resoft.postloan.debtColletion.entity.DebtCollection;
import com.resoft.postloan.debtColletion.service.DebtCollectionService;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 电话催收Controller
 * 
 * @author wangguodong
 * @version 2016-06-02
 */
@Controller
@RequestMapping(value = "${adminPath}/postloan/debtCollectionPhone")
public class DebtCollectionPhoneController extends BaseController {

	@Autowired
	private DebtCollectionPhoneService debtCollectionPhoneService;

	@Autowired
	private PLContractService plContractService;


	@Autowired
	private DebtCollectionService debtCollectionService;

	@ModelAttribute
	public DebtCollectionPhone get(@RequestParam(required = false) String id) {
		DebtCollectionPhone entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = debtCollectionPhoneService.get(id);
		}
		if (entity == null) {
			entity = new DebtCollectionPhone();
		}
		return entity;
	}

	@RequiresPermissions("postloan:debtCollectionPhone:view")
	@RequestMapping(value = "index")
	public String index(DebtCollection debtCollection, Model model) {
		try {
			// 查询合同信息
			PLContract plContract = plContractService.get(debtCollection.getContractNo());
			model.addAttribute("plContract", plContract);
			// 查询账务合同待催收统计表
			debtCollection = debtCollectionService.get(debtCollection.getId());
			model.addAttribute("debtCollection", debtCollection);
		} catch (Exception e) {
			logger.error("查询催收信息失败", e);
		}
		return "app/postloan/debtCollectionPhone/debtCollectionPhoneIndex";
	}

	@RequiresPermissions("postloan:debtCollectionPhone:view")
	@RequestMapping(value = { "list", "" })
	public String list(DebtCollectionPhone debtCollectionPhone, HttpServletRequest request, HttpServletResponse response, Model model, String readOnly) {
		Page<DebtCollectionPhone> page = debtCollectionPhoneService.findPage(new Page<DebtCollectionPhone>(request, response), debtCollectionPhone);
		model.addAttribute("page", page);
		model.addAttribute("readOnly", readOnly);
		return "app/postloan/debtCollectionPhone/debtCollectionPhoneList";
	}

	@RequiresPermissions("postloan:debtCollectionPhone:view")
	@RequestMapping(value = "form")
	public String form(DebtCollectionPhone debtCollectionPhone, String readOnly, Model model) {
		if ("true".equals(readOnly)) {
			model.addAttribute("readOnly", true);
		}
		model.addAttribute("debtCollectionPhone", debtCollectionPhone);
		return "app/postloan/debtCollectionPhone/debtCollectionPhoneForm";
	}

	@ResponseBody
	@RequiresPermissions("postloan:debtCollectionPhone:edit")
	@RequestMapping(value = "save")
	public AjaxView save(DebtCollectionPhone debtCollectionPhone, Model model, RedirectAttributes redirectAttributes) {
		AjaxView ajaxView = new AjaxView();
		try {
			debtCollectionPhoneService.save(debtCollectionPhone);
			ajaxView.setSuccess().setMessage("保存电话催收成功");
			ajaxView.put("debtId", debtCollectionPhone.getDebtId());
		} catch (Exception e) {
			logger.error("保存电话催收失败", e);
			ajaxView.setFailed().setMessage("保存电话催收失败");

		}
		return ajaxView;
	}

	@RequiresPermissions("postloan:debtCollectionPhone:edit")
	@RequestMapping(value = "delete")
	public String delete(DebtCollectionPhone debtCollectionPhone, RedirectAttributes redirectAttributes) {
		debtCollectionPhoneService.delete(debtCollectionPhone);
		addMessage(redirectAttributes, "删除电话催收成功");
		return "redirect:" + Global.getAdminPath() + "/debtCollectionPhone/debtCollectionPhone/?repage";
	}

	@ResponseBody
	@RequiresPermissions("postloan:debtCollectionPhone:edit")
	@RequestMapping(value = "finishDebtCollectionPhone")
	public AjaxView finishDebtCollectionPhone(DebtCollectionPhone debtCollectionPhone) {
		AjaxView ajaxView = new AjaxView();
		try {
			Map<String, Object> param = Maps.newHashMap();
			param.put("currCollectionStatus", Constants.DEBT_COLLECTION_STATUS_CSJS);
			param.put("contractNo", debtCollectionPhone.getContractNo());
			param.put("debtId", debtCollectionPhone.getDebtId());
			debtCollectionService.updateCurrCollectionStatus(param);
			ajaxView.setSuccess().setMessage("操作成功");
		} catch (Exception e) {
			ajaxView.setFailed().setMessage("操作失败");
			logger.error("操作失败", e);
		}
		return ajaxView;
	}

}