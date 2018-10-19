package com.resoft.postloan.debtColletion.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Maps;
import com.resoft.multds.accounting.PLContract.entity.PLContract;
import com.resoft.multds.accounting.PLContract.service.PLContractService;
import com.resoft.postloan.checkTwentyFiveInfo.entity.CheckTwentyFiveInfoVO;
import com.resoft.postloan.common.utils.AjaxView;
import com.resoft.postloan.common.utils.Constants;
import com.resoft.postloan.debtColletion.entity.DebtCollection;
import com.resoft.postloan.debtColletion.entity.DebtCollectionOperator;
import com.resoft.postloan.debtColletion.service.AllDebtCollectionService;
import com.resoft.postloan.debtColletion.service.DebtCollectionOperatorService;
import com.resoft.postloan.debtColletion.service.DebtCollectionService;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 合同带催收统计Controller
 * 
 * @author wangguodong
 * @version 2016-06-02
 */
@Controller
@RequestMapping(value = "${adminPath}/postloan/debtCollection")
public class DebtCollectionController extends BaseController {

	@Autowired
	private DebtCollectionService debtCollectionService;

	@Autowired
	private AllDebtCollectionService allDebtCollectionService;

	@Autowired
	private DebtCollectionOperatorService debtCollectionOperatorService;

	@Autowired
	private PLContractService plContractService;

	@ModelAttribute
	public DebtCollection get(@RequestParam(required = false) String id) {
		DebtCollection entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = debtCollectionService.get(id);
		}
		if (entity == null) {
			entity = new DebtCollection();
		}
		return entity;
	}

	@RequiresPermissions("postloan:debtCollection:view")
	@RequestMapping(value = "/list/{currCollectionType}/{flag}")
	public String list(DebtCollection debtCollection, HttpServletRequest request, @PathVariable("flag") String flag, HttpServletResponse response, Model model) {
		Page<DebtCollection> page = null;
		try {
			if (Constants.DEBT_COLLECTION_STATUS_ZBDSH.equals(flag)) {
				debtCollection.setCurrCollectionStatus(Constants.DEBT_COLLECTION_STATUS_ZBDSH);
			} else if (Constants.DEBT_COLLECTION_STATUS_DFP.equals(flag)) {
				debtCollection.setCurrCollectionStatus(Constants.DEBT_COLLECTION_STATUS_DFP);
			} else {
				debtCollection.setCurrCollector(UserUtils.getUser().getId());
				debtCollection.setCurrCollectionStatus(Constants.DEBT_COLLECTION_STATUS_YFPDCS);
			}
			debtCollection.setOperateOrgId(UserUtils.getUser().getCompany().getId());
			Page<DebtCollection> pageTemp = debtCollectionService.findPage(new Page<DebtCollection>(request, response), debtCollection);
			if (Constants.DEBT_COLLECTION_STATUS_ZBDSH.equals(flag)) {
				page = debtCollectionService.getUserNames(pageTemp);
			} else {
				page = pageTemp;
			}
			model.addAttribute("page", page);
			model.addAttribute("flag", flag);
			model.addAttribute("debtCollection", debtCollection);
			model.addAttribute("currCollectionType", debtCollection.getCurrCollectionType());
		} catch (Exception e) {
			logger.error("数据出现问题", e);
		}
		if (Constants.DEBT_COLLECTION_STATUS_ZBDSH.equals(flag)) {
			return "app/postloan/turnTask/debtCollectionTurnTaskList";
		} else if (Constants.DEBT_COLLECTION_STATUS_DFP.equals(flag)) {
			return "app/postloan/debtColletion/todoDebtAllocationList";
		} else {
			return "app/postloan/debtColletion/todoDebtCollectionList";
		}
	}

	// 进入待分配人员列表
	@RequiresPermissions("postloan:debtCollection:view")
	@RequestMapping(value = "form")
	public String form(DebtCollectionOperator debtCollectionOperator, String debtId, HttpServletRequest request, HttpServletResponse response, Model model) {
		debtCollectionOperator.setOperatorOffice(UserUtils.getUser().getOffice().getId());
		Page<DebtCollectionOperator> page;
		try {
			page = debtCollectionOperatorService.findPage(new Page<DebtCollectionOperator>(request, response), debtCollectionOperator);
			model.addAttribute("page", page);
			model.addAttribute("debtCollectionOperator", debtCollectionOperator);
			model.addAttribute("debtId", debtId);
		} catch (Exception e) {
			logger.error("分配人员查询出现问题", e);
		}
		return "app/postloan/debtColletion/debtCollectionOperatorList";
	}

	@ResponseBody
	@RequiresPermissions("postloan:debtCollection:edit")
	@RequestMapping(value = "save")
	public AjaxView save(DebtCollection debtCollection, Model model, CheckTwentyFiveInfoVO checkTwentyFiveInfoVO, String contractNo, String allocateId) {
		AjaxView ajaxView = new AjaxView();
		try {
			String flag = null;
			Map<String, Object> accStaContractStatusMap = plContractService.getAccStaContractStatusByContractNo(debtCollection.getContractNo());
			if (accStaContractStatusMap != null) {
				debtCollection.setOperateOrgId(accStaContractStatusMap.get("operateOrgId").toString());
				debtCollection.setTaOverdueTimes(accStaContractStatusMap.get("taTimes") + "");
				debtCollection.setCurrOverdueAmount(accStaContractStatusMap.get("currOverdueAmount").toString());
			}
			flag = debtCollectionService.insert(debtCollection, checkTwentyFiveInfoVO,allocateId);

			if ("true".equalsIgnoreCase(flag)) {
				ajaxView.setSuccess().setMessage("流转到法务催收成功");
			} else {
				ajaxView.setFailed().setMessage("流转到法务催收失败");

			}
		} catch (Exception e) {
			logger.error("流转到法务催收失败", e);
			ajaxView.setFailed().setMessage("流转到法务催收失败");
		}
		return ajaxView;
	}

	/**
	 * 人员分配
	 */
	@ResponseBody
	@RequiresPermissions("postloan:debtCollection:edit")
	@RequestMapping(value = "allotOperator")
	public AjaxView allotOperator(DebtCollection debtCollection, String checkedOperatorId) {
		AjaxView ajaxView = new AjaxView();
		try {
			Map<String, Object> params = Maps.newHashMap();
			params.put("id", debtCollection.getId());
			params.put("currCollector", checkedOperatorId);
			params.put("collectBy", UserUtils.getUser().getId());
			debtCollectionService.updateCollectionStatusAndType(params);
			ajaxView.setSuccess().setMessage("人员分配成功");
		} catch (Exception e) {
			logger.error("人员分配失败", e);
			ajaxView.setFailed().setMessage("人员分配失败");
		}
		return ajaxView;
	}

	/**
	 * 转办和风险级别设置
	 */
	@RequiresPermissions("postloan:debtCollection:edit")
	@RequestMapping(value = "turnTaskOrRiskLelve")
	public String turnTaskOrRiskLelve(DebtCollection debtCollection, String flag, Model model) {
		model.addAttribute("flag", flag);
		model.addAttribute("debtCollection", debtCollection);
		if ("turn".equals(flag)) {
			return "app/postloan/turnTask/turnTaskForm";
		} else {
			return "app/postloan/debtColletion/updateRiskLelve";
		}
	}

	/**
	 * 转办审批
	 */
	@ResponseBody
	@RequiresPermissions("postloan:debtCollection:edit")
	@RequestMapping(value = "turnTask")
	public AjaxView turnTask(DebtCollection debtCollection, String flag) {
		AjaxView ajaxView = new AjaxView();
		try {
			debtCollectionService.approveTask(debtCollection, flag);
			ajaxView.setSuccess().setMessage("操作成功");
		} catch (Exception e) {
			logger.error("转办审批失败", e);
			ajaxView.setFailed().setMessage("操作失败");
		}
		return ajaxView;
	}

	/**
	 * 转办申请提交和风险级别设置保存
	 */
	@ResponseBody
	@RequiresPermissions("postloan:debtCollection:edit")
	@RequestMapping(value = "applySave")
	public AjaxView applySave(DebtCollection debtCollection, String flag) {
		AjaxView ajaxView = new AjaxView();
		try {
			if (Constants.DEBT_COLLECTION__ZB.equals(flag)) {
				debtCollection.setCurrCollectionStatus(Constants.DEBT_COLLECTION_STATUS_ZBDSH);
			}
			debtCollectionService.updateToDoDebtCollection(debtCollection, flag);
			ajaxView.setSuccess().setMessage("操作成功");
		} catch (Exception e) {
			if ("riskLelve".equals(flag)) {
				logger.error("设置风险变更失败", e);
			} else {
				logger.error("转办变更失败", e);
			}
			ajaxView.setFailed().setMessage("操作失败");
		}
		return ajaxView;
	}

	@RequiresPermissions("postloan:debtCollection:edit")
	@RequestMapping(value = "delete")
	public String delete(DebtCollection debtCollection, RedirectAttributes redirectAttributes) {
		debtCollectionService.delete(debtCollection);
		addMessage(redirectAttributes, "删除合同带催收统计成功");
		return "redirect:" + Global.getAdminPath() + "/debtColletion/debtCollection/?repage";
	}

	@RequiresPermissions("postloan:debtCollection:edit")
	@RequestMapping(value = "validateTypeForSkip")
	public String validateTypeForSkip(DebtCollection debtCollection, RedirectAttributes redirectAttributes) {
		if (Constants.DEBT_COLLECTION_FACE.equals(debtCollection.getCurrCollectionType())) {
			return "redirect:" + adminPath + "/postloan/debtCollectionFace/index?id=" + debtCollection.getId() + "&contractNo=" + debtCollection.getContractNo();
		} else if (Constants.DEBT_COLLECTION_OUT.equals(debtCollection.getCurrCollectionType())) {
			return "redirect:" + adminPath + "/postloan/debtCollectionOut/index?id=" + debtCollection.getId() + "&contractNo=" + debtCollection.getContractNo();
		} else if (Constants.DEBT_COLLECTION_LEGAL.equals(debtCollection.getCurrCollectionType())) {
			return "redirect:" + adminPath + "/postloan/debtCollectionLegal?id=" + debtCollection.getId() + "&contractNo=" + debtCollection.getContractNo();
		} else {
			return "redirect:" + adminPath + "/postloan/debtCollectionPhone/index?id=" + debtCollection.getId() + "&contractNo=" + debtCollection.getContractNo();
		}
	}

	// 详情主页
	@RequestMapping(value = "toDetailsPageIndex")
	public String toDetailsPageIndex(String contractNo, Model model) {
		PLContract plContract;
		try {
			plContract = plContractService.get(contractNo);
			model.addAttribute("plContract", plContract);
		} catch (Exception e) {
			logger.error("查询合同信息出现问题", e);
		}
		model.addAttribute("contractNo", contractNo);
		return "app/postloan/debtColletion/detailPageIndex";
	}

	// 催收详情页面
	@RequestMapping(value = "todebtDetailsPage")
	public String todebtDetailsPage(String contractNo, String debtId, Model model) {
		// 查询合同信息
		PLContract plContract = plContractService.get(contractNo);
		model.addAttribute("plContract", plContract);
		// 查询账务合同待催收统计表
		DebtCollection debtCollection = debtCollectionService.get(debtId);
		model.addAttribute("debtCollection", debtCollection);
		model.addAttribute("contractNo", contractNo);
		return "app/postloan/debtColletion/debtCollectionDetailIndex";
	}

	@RequiresPermissions("postloan:debtCollection:view")
	@RequestMapping(value = "/allDebtCollectionList")
	public String allDebtCollectionList(DebtCollection debtCollection, HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			debtCollection.setCurrCollectionStatus(Constants.DEBT_COLLECTION_STATUS_YFPDCS);
			debtCollection.setOperateOrgId(UserUtils.getUser().getCompany().getId());
			Page<DebtCollection> page = allDebtCollectionService.findPage(new Page<DebtCollection>(request, response), debtCollection);
			model.addAttribute("page", page);
			model.addAttribute("debtCollection", debtCollection);
		} catch (Exception e) {
			logger.error("数据出现问题", e);
		}
		return "app/postloan/debtColletion/allDebtCollectionList";
	}

}