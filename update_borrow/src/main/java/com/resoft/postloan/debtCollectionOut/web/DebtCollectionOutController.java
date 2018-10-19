package com.resoft.postloan.debtCollectionOut.web;

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

import com.google.common.collect.Maps;
import com.resoft.multds.accounting.PLContract.entity.PLContract;
import com.resoft.multds.accounting.PLContract.service.PLContractService;
import com.resoft.postloan.collectionPaymentInfo.entity.CollectionPaymentInfo;
import com.resoft.postloan.collectionPaymentInfo.service.CollectionPaymentInfoService;
import com.resoft.postloan.common.utils.AjaxView;
import com.resoft.postloan.common.utils.Constants;
import com.resoft.postloan.debtCollectionOut.entity.DebtCollectionOut;
import com.resoft.postloan.debtCollectionOut.service.DebtCollectionOutService;
import com.resoft.postloan.debtColletion.entity.DebtCollection;
import com.resoft.postloan.debtColletion.service.DebtCollectionService;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 外包催收Controller
 * 
 * @author wangguodong
 * @version 2016-06-02
 */
@Controller
@RequestMapping(value = "${adminPath}/postloan/debtCollectionOut")
public class DebtCollectionOutController extends BaseController {

	@Autowired
	private DebtCollectionOutService debtCollectionOutService;

	@Autowired
	private PLContractService plContractService;

	@Autowired
	private CollectionPaymentInfoService collectionPaymentInfoService;

	@Autowired
	private DebtCollectionService debtCollectionService;

	@ModelAttribute
	public DebtCollectionOut get(@RequestParam(required = false) String id) {
		DebtCollectionOut entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = debtCollectionOutService.get(id);
		}
		if (entity == null) {
			entity = new DebtCollectionOut();
		}
		return entity;
	}

	@RequiresPermissions("postloan:debtCollectionOut:view")
	@RequestMapping(value = "index")
	public String index(DebtCollection debtCollection, CollectionPaymentInfo collectionPaymentInfo, Model model) {
		try {
			// 查询合同信息
			PLContract plContract = plContractService.get(debtCollection.getContractNo());
			model.addAttribute("plContract", plContract);
			// 查询账务合同待催收统计表
			debtCollection = debtCollectionService.get(debtCollection.getId());
			model.addAttribute("debtCollection", debtCollection);
			// 客户汇款详情信息
			List<CollectionPaymentInfo> collectionPaymentInfoList = collectionPaymentInfoService.findList(collectionPaymentInfo);
			model.addAttribute("collectionPaymentInfoList", collectionPaymentInfoList);
		} catch (Exception e) {
			logger.error("查询催收信息失败", e);
		}
		return "app/postloan/debtCollectionOut/debtCollectionOutIndex";
	}

	@RequiresPermissions("postloan:debtCollectionOut:view")
	@RequestMapping(value = { "list", "" })
	public String list(DebtCollectionOut debtCollectionOut, String readOnly, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DebtCollectionOut> page = debtCollectionOutService.findPage(new Page<DebtCollectionOut>(request, response), debtCollectionOut);
		model.addAttribute("page", page);
		model.addAttribute("readOnly", readOnly);
		return "app/postloan/debtCollectionOut/debtCollectionOutList";
	}

	@RequiresPermissions("postloan:debtCollectionOut:view")
	@RequestMapping(value = "form")
	public String form(DebtCollectionOut debtCollectionOut, String readOnly, Model model) {
		if ("true".equals(readOnly)) {
			model.addAttribute("readOnly", true);
		}
		model.addAttribute("debtCollectionOut", debtCollectionOut);
		return "app/postloan/debtCollectionOut/debtCollectionOutForm";
	}

	@ResponseBody
	@RequiresPermissions("postloan:debtCollectionOut:edit")
	@RequestMapping(value = "save")
	public AjaxView save(DebtCollectionOut debtCollectionOut, Model model, RedirectAttributes redirectAttributes) {
		AjaxView ajaxView = new AjaxView();
		try {
			debtCollectionOutService.save(debtCollectionOut);
			ajaxView.setSuccess().setMessage("保存外包催收成功");
			ajaxView.put("debtId", debtCollectionOut.getDebtId());
		} catch (Exception e) {
			logger.error("保存外包催收失败", e);
			ajaxView.setFailed().setMessage("保存外包催收失败");
		}
		return ajaxView;
	}

	@RequiresPermissions("postloan:debtCollectionOut:edit")
	@RequestMapping(value = "delete")
	public String delete(DebtCollectionOut debtCollectionOut, RedirectAttributes redirectAttributes) {
		debtCollectionOutService.delete(debtCollectionOut);
		addMessage(redirectAttributes, "删除外包催收成功");
		return "redirect:" + Global.getAdminPath() + "/debtCollectionOut/debtCollectionOut/?repage";
	}

	@ResponseBody
	@RequiresPermissions("postloan:debtCollectionOut:edit")
	@RequestMapping(value = "finishDebtCollectionOut")
	public AjaxView finishDebtCollectionOut(DebtCollectionOut debtCollectionOut) {
		AjaxView ajaxView = new AjaxView();
		try {
			Map<String, Object> param = Maps.newHashMap();
			param.put("currCollectionStatus", Constants.DEBT_COLLECTION_STATUS_CSJS);
			param.put("contractNo", debtCollectionOut.getContractNo());
			param.put("debtId", debtCollectionOut.getDebtId());
			debtCollectionService.updateCurrCollectionStatus(param);
			ajaxView.setSuccess().setMessage("操作成功");
		} catch (Exception e) {
			ajaxView.setFailed().setMessage("操作失败");
			logger.error("操作失败", e);
		}
		return ajaxView;
	}

}