package com.resoft.credit.processSuggestionInfo.web;

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
import com.resoft.common.utils.AjaxView;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.processSuggestionInfo.entity.ProcessSuggestionInfo;
import com.resoft.credit.processSuggestionInfo.service.ProcessSuggestionInfoService;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 流程意见Controller
 * 
 * @author wuxi01
 * @version 2016-02-29
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/processSuggestionInfo")
public class ProcessSuggestionInfoController extends BaseController {

	@Autowired
	private ProcessSuggestionInfoService processSuggestionInfoService;

	@ModelAttribute
	public ProcessSuggestionInfo get(@RequestParam(required = false) String id) {
		ProcessSuggestionInfo entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = processSuggestionInfoService.get(id);
		}
		if (entity == null) {
			entity = new ProcessSuggestionInfo();
		}
		return entity;
	}

	@RequiresPermissions("credit:processSuggestionInfo:view")
	@RequestMapping(value = { "list", "" })
	public String list(ProcessSuggestionInfo processSuggestionInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProcessSuggestionInfo> page = processSuggestionInfoService.findPage(new Page<ProcessSuggestionInfo>(request, response), processSuggestionInfo);
		model.addAttribute("page", page);
		return "app/credit/processSuggestionInfo/processSuggestionInfoList";
	}

	@RequiresPermissions("credit:processSuggestionInfo:view")
	@RequestMapping(value = "form")
	public String form(ProcessSuggestionInfo processSuggestionInfo, Model model) {
		model.addAttribute("processSuggestionInfo", processSuggestionInfo);
		return "app/credit/processSuggestionInfo/processSuggestionInfoForm";
	}

	@RequiresPermissions("credit:processSuggestionInfo:edit")
	@RequestMapping(value = "save")
	public String save(ProcessSuggestionInfo processSuggestionInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, processSuggestionInfo)) {
			return form(processSuggestionInfo, model);
		}
		processSuggestionInfoService.save(processSuggestionInfo);
		addMessage(redirectAttributes, "保存流程意见成功");
		return "redirect:" + Global.getAdminPath() + "/processSuggestionInfo/processSuggestionInfo/?repage";
	}

	@RequiresPermissions("credit:processSuggestionInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(ProcessSuggestionInfo processSuggestionInfo, RedirectAttributes redirectAttributes) {
		processSuggestionInfoService.delete(processSuggestionInfo);
		addMessage(redirectAttributes, "删除流程意见成功");
		return "redirect:" + Global.getAdminPath() + "/processSuggestionInfo/processSuggestionInfo/?repage";
	}

	/**
	 * 信审意见书-审批信息-各级别综合意见
	 * 
	 * @param processSuggestionInfo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "formCreditViewBookApproveInfo")
	public String formCreditViewBookApproveInfo(ProcessSuggestionInfo processSuggestionInfo, Model model) {
		model.addAttribute("processSuggestionInfo", processSuggestionInfo);
		return "app/credit/processSuggestionInfo/creditViewBookApproveInfoForm";
	}

	/**
	 * 批复信息-审批结论-审批意见
	 * 
	 * @param processSuggestionInfo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "checkApproveConclusionForm")
	public String checkApproveConclusionForm(ProcessSuggestionInfo processSuggestionInfo, Model model) {
		model.addAttribute("processSuggestionInfo", processSuggestionInfo);
		return "app/credit/processSuggestionInfo/checkApproveConclusionForm";
	}

	/**
	 * 分公司复议-复议信息-复议结论-审批意见
	 * 
	 * @param processSuggestionInfo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "formReviewConclusion")
	public String formReviewConclusion(ProcessSuggestionInfo processSuggestionInfo, Model model) {
		model.addAttribute("processSuggestionInfo", processSuggestionInfo);
		return "app/credit/processSuggestionInfo/reviewConclusionForm";
	}

	/**
	/**
	 * 取消审批-取消审批
	 * 
	 * @param processSuggestionInfo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "cancelApprove")
	public String cancelApprove(ActTaskParam actTaskParam, String readOnly, Model model) {
		// 预约意见
		Map<String, String> params = Maps.newConcurrentMap();
		params.put("applyNo", actTaskParam.getApplyNo());
		params.put("taskDefKey", actTaskParam.getTaskDefKey());
		ProcessSuggestionInfo processSuggestionInfo = processSuggestionInfoService.findByApplyNo(params);
		if (processSuggestionInfo == null) {
			processSuggestionInfo = new ProcessSuggestionInfo();
			processSuggestionInfo.setApplyNo(actTaskParam.getApplyNo());
			processSuggestionInfo.setTaskDefKey(actTaskParam.getTaskDefKey());
		}
		model.addAttribute("processSuggestionInfo", processSuggestionInfo);
		model.addAttribute("readOnly", readOnly);
		return "app/credit/processSuggestionInfo/cancelApproveForm";
	}

	@ResponseBody
	@RequestMapping(value = "saveEvaluate")
	public AjaxView saveEvaluate(ActTaskParam actTaskParam, ProcessSuggestionInfo processSuggestionInfo) {
		// 判断数据库中是否有此applyNo的数据
		AjaxView ajaxView = new AjaxView();
		String applyNo = processSuggestionInfo.getApplyNo();
		Map<String, String> params = Maps.newHashMap();
		params.put("applyNo", applyNo);
		params.put("taskDefKey", processSuggestionInfo.getTaskDefKey());
		ProcessSuggestionInfo hasProcessSuggestionInfo = processSuggestionInfoService.findByApplyNo(params);
		if (hasProcessSuggestionInfo != null) {
			try {
				processSuggestionInfo.preUpdate();
				processSuggestionInfoService.updateByApplyNo(processSuggestionInfo);
				ajaxView.setSuccess().setMessage("更新数据成功");
			} catch (Exception e) {
				ajaxView.setFailed().setMessage("更新数据失败");
				logger.error("更新数据错误" + e.getMessage(), e);
			}
		} else {
			try {
				processSuggestionInfoService.save(processSuggestionInfo);
				ajaxView.setSuccess().setMessage("保存数据成功");
			} catch (Exception e) {
				ajaxView.setFailed().setMessage("保存数据失败");
				logger.error("保存数据错误" + e.getMessage(), e);
			}

		}
		return ajaxView;
	}

}