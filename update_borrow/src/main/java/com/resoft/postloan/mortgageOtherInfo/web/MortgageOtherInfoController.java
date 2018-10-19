package com.resoft.postloan.mortgageOtherInfo.web;

import java.util.Arrays;
import java.util.List;

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

import com.resoft.postloan.common.utils.AjaxView;
import com.resoft.postloan.mortgageEvaluateInfo.entity.MortgageEvaluateInfo;
import com.resoft.postloan.mortgageEvaluateInfo.service.MortgageEvaluateInfoService;
import com.resoft.postloan.mortgageOtherInfo.entity.MortgageOtherInfo;
import com.resoft.postloan.mortgageOtherInfo.service.MortgageOtherInfoService;
import com.resoft.postloan.taskCenter.entity.ActTaskParam;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 其他抵质押物信息Controller
 * 
 * @author yanwanmei
 * @version 2016-02-24
 */
@Controller("plMortgageOtherInfoController")
@RequestMapping(value = "${adminPath}/postloan/mortgageOtherInfo")
public class MortgageOtherInfoController extends BaseController {

	@Autowired
	private MortgageOtherInfoService mortgageOtherInfoService;

	@Autowired
	private MortgageEvaluateInfoService mortgageEvaluateInfoService;

	@ModelAttribute
	public MortgageOtherInfo get(@RequestParam(required = false) String id) {
		MortgageOtherInfo entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = mortgageOtherInfoService.get(id);
		}
		if (entity == null) {
			entity = new MortgageOtherInfo();
		}
		return entity;
	}

	@RequestMapping(value = { "list", "" })
	public String list(ActTaskParam actTaskParam, MortgageOtherInfo mortgageOtherInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		String applyNo = actTaskParam.getApplyNo();
		List<MortgageOtherInfo> mortgageOtherInfoList = mortgageOtherInfoService.getPageByApplyNo(applyNo);
		model.addAttribute("mortgageOtherInfoList", mortgageOtherInfoList);
		return "app/postloan/mortgageEvaluateInfo/mortgageOtherEvaluateInfoList";
	}

	@RequestMapping(value = { "dealList", "" })
	public String dealList(ActTaskParam actTaskParam, MortgageOtherInfo mortgageOtherInfo,String readOnly, Model model) {
		String applyNo = actTaskParam.getApplyNo();
		List<MortgageOtherInfo> mortgageOtherInfoList = mortgageOtherInfoService.getPageByApplyNo(applyNo);
		model.addAttribute("mortgageOtherInfoList", mortgageOtherInfoList);
		model.addAttribute("readOnly", readOnly);
		return "app/postloan/mortgage/mortgageOtherList";
	}

	@RequestMapping(value = "form")
	public String form(MortgageOtherInfo mortgageOtherInfo, HttpServletRequest request, Model model) {
		model.addAttribute("readOnly", request.getParameter("readOnly"));
		model.addAttribute("mortgageOtherInfo", mortgageOtherInfo);
		return "app/postloan/mortgageOtherInfo/mortgageOtherInfoForm";
	}

	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxView save(MortgageOtherInfo mortgageOtherInfo, ActTaskParam actTaskParam, String applyNo) {
		AjaxView ajaxViewOther = new AjaxView();
		try {
			mortgageOtherInfoService.save(mortgageOtherInfo);
			ajaxViewOther.setSuccess().setMessage("保存其他抵质押物信息成功！");
		} catch (Exception e) {
			logger.error("保存其他抵质押物评估信息错误" + e.getMessage(), e);
			ajaxViewOther.setFailed().setMessage("保存其他抵质押物信息错误！");
		}

		ajaxViewOther.setData(applyNo);
		return ajaxViewOther;
	}

	@ResponseBody
	@RequestMapping(value = "delete")
	public AjaxView delete(MortgageOtherInfo mortgageOtherInfo, String id, RedirectAttributes redirectAttributes) {
		AjaxView rtn = new AjaxView();
		try {
			mortgageOtherInfoService.delete(mortgageOtherInfo);
			rtn.setSuccess().setMessage("删除其他抵质押物信息成功");
		} catch (Exception e) {
			rtn.setSuccess().setMessage("删除其他抵质押物信息失败！");
			logger.error("保存失败！", e);
		}
		return rtn;
	}

	@RequestMapping(value = "evaluate")
	public String evaluate(MortgageOtherInfo mortgageOtherInfo, Model model, String applyNo, String flag) {
		model.addAttribute("readonly", "true");
		model.addAttribute("mortgageOtherInfo", mortgageOtherInfo);
		model.addAttribute("infoId", mortgageOtherInfo.getId());
		model.addAttribute("applyNo", applyNo);
		try {
			/* 查询其他抵质押物检查信息 */
			MortgageEvaluateInfo mortgageInfo = mortgageEvaluateInfoService.findMortgageByInfoId(mortgageOtherInfo.getId());
			if (mortgageInfo != null) {
				model.addAttribute("mortgageInfo", mortgageInfo);
			} else {
				MortgageEvaluateInfo mortgageInfoCheck = new MortgageEvaluateInfo();
				mortgageInfoCheck.setInfoId(mortgageOtherInfo.getId());
				model.addAttribute("mortgageInfo", mortgageInfoCheck);
			}

		} catch (Exception e) {
			logger.error("检查项目信息查询失败", e);
			model.addAttribute("message", "信息查询失败,请查看后台信息");
		}
		return "app/postloan/mortgageOtherInfo/mortgageCheckOtherItemsIndex";
	}

	@RequestMapping(value = "editEvaluate")
	public String editEvaluate(MortgageOtherInfo mortgageOtherInfo, Model model, String applyNo, String flag) {
		model.addAttribute("readonly", "true");
		model.addAttribute("mortgageOtherInfo", mortgageOtherInfo);
		try {
			MortgageEvaluateInfo mortgageInfo = mortgageEvaluateInfoService.findMortgageByInfoId(mortgageOtherInfo.getId());
			model.addAttribute("mortgageInfo", mortgageInfo);
		} catch (Exception e) {
			logger.error("检查项目信息查询失败", e);
			model.addAttribute("message", "信息查询失败,请查看后台信息");
		}
		return "app/postloan/mortgageOtherInfo/mortgageCheckOtherItemsIndex";
	}

	// 批量删除方法
	@RequestMapping(value = "batchDelete")
	@ResponseBody
	public String batchDelete(String ids, HttpServletResponse response) {
		if (StringUtils.isNotEmpty(ids)) {
			List<String> idList = Arrays.asList(ids.split(","));
			mortgageOtherInfoService.batchDelete(idList);
		}
		return "success";
	}

	@RequestMapping(value = "toOtherEvaluateIndex")
	public String toOtherEvaluateIndex(String applyNo, Model model) {

		List<MortgageOtherInfo> mortgageOtherInfoList = mortgageOtherInfoService.getPageByApplyNo(applyNo);
		model.addAttribute("mortgageOtherInfoList", mortgageOtherInfoList);
		model.addAttribute("applyNo", applyNo);

		return "app/postloan/mortgageEvaluateInfo/mortgageOtherEvaluateInfoList";
	}

	@RequestMapping(value = "saveOtherEvaluate")
	public String saveOtherEvaluate(MortgageOtherInfo mortgageOtherInfo, Model model, RedirectAttributes redirectAttributes) {
		mortgageOtherInfoService.save(mortgageOtherInfo);
		addMessage(redirectAttributes, "保存其他抵质押物信息成功");
		model.addAttribute("closeWindow", true);
		String flag = null;
		return evaluate(mortgageOtherInfo, model, mortgageOtherInfo.getApplyNo(), flag);
	}
}