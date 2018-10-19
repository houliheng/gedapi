package com.resoft.postloan.debtCollectionLegal.web;

import java.util.Date;
import java.util.List;

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

import com.resoft.multds.accounting.PLContract.entity.PLContract;
import com.resoft.multds.accounting.PLContract.service.PLContractService;
import com.resoft.postloan.collectionPaymentInfo.entity.CollectionPaymentInfo;
import com.resoft.postloan.collectionPaymentInfo.service.CollectionPaymentInfoService;
import com.resoft.postloan.common.utils.AjaxView;
import com.resoft.postloan.debtCollectionLegal.entity.DebtCollectionLegal;
import com.resoft.postloan.debtCollectionLegal.service.DebtCollectionLegalService;
import com.resoft.postloan.debtColletion.entity.DebtCollection;
import com.resoft.postloan.debtColletion.service.DebtCollectionService;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 法务催收Controller
 * 
 * @author wangguodong
 * @version 2016-06-02
 */
@Controller
@RequestMapping(value = "${adminPath}/postloan/debtCollectionLegal")
public class DebtCollectionLegalController extends BaseController {

	@Autowired
	private DebtCollectionLegalService debtCollectionLegalService;

	@Autowired
	private PLContractService plContractService;

	@Autowired
	private CollectionPaymentInfoService collectionPaymentInfoService;

	@Autowired
	private DebtCollectionService debtCollectionService;

	@ModelAttribute
	public DebtCollectionLegal get(@RequestParam(required = false) String id) {
		DebtCollectionLegal entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = debtCollectionLegalService.get(id);
		}
		if (entity == null) {
			entity = new DebtCollectionLegal();
		}
		return entity;
	}

	@RequiresPermissions("postloan:debtCollectionLegal:view")
	@RequestMapping(value = { "list", "" })
	public String list(DebtCollectionLegal debtCollectionLegal, HttpServletRequest request, HttpServletResponse response, Model model, PLContract plContract, DebtCollection debtCollection, String contractNo, CollectionPaymentInfo collectionPaymentInfo) {
		try {

			// 查询合同信息
			plContract = plContractService.get(plContract);
			model.addAttribute("plContract", plContract);
			// 查询账务合同待催收统计表
			debtCollection = debtCollectionService.get(debtCollection);
			model.addAttribute("debtCollection", debtCollection);
			// 查询法务信息列表
			List<DebtCollectionLegal> debtCollectionLegalList = debtCollectionLegalService.findList(debtCollectionLegal);
			model.addAttribute("debtCollectionLegalList", debtCollectionLegalList);
			// 客户汇款详情信息
			List<CollectionPaymentInfo> collectionPaymentInfoList = collectionPaymentInfoService.findList(collectionPaymentInfo);
			model.addAttribute("collectionPaymentInfoList", collectionPaymentInfoList);
			model.addAttribute("contractNo", contractNo);
		} catch (Exception e) {
			logger.error("查询催收信息失败", e);
		}
		return "app/postloan/debtCollectionLegal/debtCollectionIndex";
	}

	@RequiresPermissions("postloan:debtCollectionLegal:view")
	@RequestMapping(value = "form")
	public String form(DebtCollectionLegal debtCollectionLegal, Model model, String contractNo, HttpServletRequest request) {
		try {
			if ("true".equals(request.getParameter("readOnly"))) {
				model.addAttribute("readOnly", true);
			}
			if (debtCollectionLegal.getRegistrationTime() == null) {

				debtCollectionLegal.setRegistrationTime(new Date());
			}
			User user = UserUtils.getUser();
			debtCollectionLegal.setRegister(user.getName());
			model.addAttribute("debtCollectionLegal", debtCollectionLegal);
			model.addAttribute("contractNo", contractNo);
		} catch (Exception e) {
			logger.error("法务催收信息失败", e);
		}
		return "app/postloan/debtCollectionLegal/debtCollectionLegalForm";
	}

	@ResponseBody
	@RequiresPermissions("postloan:debtCollectionLegal:edit")
	@RequestMapping(value = "save")
	public AjaxView save(DebtCollectionLegal debtCollectionLegal) {
		AjaxView ajaxView = new AjaxView();
		try {
			debtCollectionLegalService.saveDebtCollectionLegal(debtCollectionLegal);
			ajaxView.setSuccess().setMessage("保存法务催收信息成功");
			ajaxView.put("legalResult", debtCollectionLegal.getLegalResult());
			ajaxView.put("debtId", debtCollectionLegal.getDebtId());
		} catch (Exception e) {
			logger.error("保存法务催收信息失败", e);
			ajaxView.setFailed().setMessage("保存法务催收信息失败");
		}
		ajaxView.setData(debtCollectionLegal.getContractNo());
		return ajaxView;
	}

	@RequiresPermissions("postloan:debtCollectionLegal:edit")
	@RequestMapping(value = "delete")
	public String delete(DebtCollectionLegal debtCollectionLegal, RedirectAttributes redirectAttributes) {
		debtCollectionLegalService.delete(debtCollectionLegal);
		addMessage(redirectAttributes, "删除法务催收成功");
		return "redirect:" + Global.getAdminPath() + "/debtCollectionLegal/debtCollectionLegal/?repage";
	}

	@RequiresPermissions("postloan:debtCollectionLegal:view")
	@RequestMapping(value = "toDebtCollectionLegal")
	public String toDebtCollectionLegal(Model model, String readOnly, DebtCollectionLegal debtCollectionLegal) {
		try {
			// 查询法务信息列表
			List<DebtCollectionLegal> debtCollectionLegalList = debtCollectionLegalService.findList(debtCollectionLegal);
			model.addAttribute("debtCollectionLegalList", debtCollectionLegalList);
			model.addAttribute("readOnly", readOnly);
		} catch (Exception e) {
			logger.error("查询催收信息失败", e);
		}
		return "app/postloan/debtCollectionLegal/debtCollectionLegalList";
	}

	@ResponseBody
	@RequiresPermissions("postloan:debtCollectionLegal:view")
	@RequestMapping(value = "finishDebtCollectionLegal")
	public AjaxView finishDebtCollectionLegal(DebtCollectionLegal debtCollectionLegal) {
		AjaxView ajaxView = new AjaxView();
		try {
			debtCollectionLegalService.updateDebtCollectionLegal(debtCollectionLegal);
			ajaxView.setSuccess().setMessage("法务催收结束");
		} catch (Exception e) {
			ajaxView.setFailed().setMessage("法务催收结束失败");
			logger.error("法务催收结束失败", e);
		}
		return ajaxView;
	}
}