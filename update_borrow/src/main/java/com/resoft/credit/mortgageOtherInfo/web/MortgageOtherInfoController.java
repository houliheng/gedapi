package com.resoft.credit.mortgageOtherInfo.web;

import java.util.Arrays;
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

import com.resoft.common.utils.AjaxView;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.mortgageOtherInfo.entity.MortgageOtherInfo;
import com.resoft.credit.mortgageOtherInfo.service.MortgageOtherInfoService;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 其他抵质押物信息Controller
 * @author yanwanmei
 * @version 2016-02-24
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/mortgageOtherInfo")
public class MortgageOtherInfoController extends BaseController {

	@Autowired
	private MortgageOtherInfoService mortgageOtherInfoService;
	
	@ModelAttribute
	public MortgageOtherInfo get(@RequestParam(required=false) String id) {
		MortgageOtherInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = mortgageOtherInfoService.get(id);
		}
		if (entity == null){
			entity = new MortgageOtherInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("credit:mortgageInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(ActTaskParam actTaskParam,MortgageOtherInfo mortgageOtherInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		String applyNo = actTaskParam.getApplyNo();
		List<MortgageOtherInfo> mortgageOtherInfoList =mortgageOtherInfoService.getPageByApplyNo(applyNo);
	    model.addAttribute("mortgageOtherInfoList", mortgageOtherInfoList);
		return "app/credit/mortgageOtherInfo/mortgageOtherInfoList";
	}

	@RequiresPermissions("credit:mortgageInfo:view")
	@RequestMapping(value = "form")
	public String form(MortgageOtherInfo mortgageOtherInfo, Model model) {
		model.addAttribute("mortgageOtherInfo", mortgageOtherInfo);
		return "app/credit/mortgageOtherInfo/mortgageOtherInfoForm";
	}

	
	@ResponseBody
	@RequiresPermissions("credit:mortgageInfo:view")
	@RequestMapping(value = "save")
	public AjaxView save(MortgageOtherInfo mortgageOtherInfo, ActTaskParam actTaskParam,String applyNo) {
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
	
	@RequiresPermissions("credit:mortgageInfo:view")
	@RequestMapping(value = "delete")
	public String delete(MortgageOtherInfo mortgageOtherInfo, RedirectAttributes redirectAttributes) {
		mortgageOtherInfoService.delete(mortgageOtherInfo);
		addMessage(redirectAttributes, "删除其他抵质押物信息成功");
		return "redirect:"+Global.getAdminPath()+"/mortgageOtherInfo/mortgageOtherInfo/?repage";
	}
	
	@RequiresPermissions("credit:mortgageInfo:view")
	@RequestMapping(value = "evaluate")
	public String evaluate(MortgageOtherInfo mortgageOtherInfo, Model model,String applyNo,String flag) {
		if("1".equals(flag)){
			model.addAttribute("readonly", "true");
		}
		model.addAttribute("mortgageOtherInfo", mortgageOtherInfo);
		return "app/credit/mortgageOtherInfo/mortgageOtherEvaluateInfoForm";
	}
	
//	批量删除方法
	@RequiresPermissions("credit:mortgageInfo:view")
	@RequestMapping(value = "batchDelete")
	@ResponseBody
	public String batchDelete(String ids, HttpServletResponse response) {
		if(StringUtils.isNotEmpty(ids)){
			List<String> idList = Arrays.asList(ids.split(","));
			mortgageOtherInfoService.batchDelete(idList);
		}
		return "success";
	}
	
	@RequiresPermissions("credit:mortgageInfo:view")
	@RequestMapping(value = "toOtherEvaluateIndex")
	public String toOtherEvaluateIndex(String applyNo, Model model) {

		List<MortgageOtherInfo> mortgageOtherInfoList = mortgageOtherInfoService.getPageByApplyNo(applyNo);
		model.addAttribute("mortgageOtherInfoList", mortgageOtherInfoList);

		return "app/credit/mortgageEvaluateInfo/mortgageOtherEvaluateInfoList";
	}
	
	@RequiresPermissions("credit:mortgageInfo:view")
	@RequestMapping(value = "saveOtherEvaluate")
	public String saveOtherEvaluate(MortgageOtherInfo mortgageOtherInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, mortgageOtherInfo)){
			return form(mortgageOtherInfo, model);
		}
		mortgageOtherInfoService.save(mortgageOtherInfo);
		addMessage(redirectAttributes, "保存其他抵质押物信息成功");
		model.addAttribute("closeWindow", true);
		String flag = null;
		return evaluate(mortgageOtherInfo, model,mortgageOtherInfo.getApplyNo(),flag);
	}
}