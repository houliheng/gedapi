package com.resoft.credit.mortgageHouseInfo.web;

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

import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.mortgageHouseInfo.entity.MortgageHouseInfo;
import com.resoft.credit.mortgageHouseInfo.service.MortgageHouseInfoService;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 房产抵质押物Controller
 * @author yanwanmei
 * @version 2016-02-29
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/mortgageHouseInfo")
public class MortgageHouseInfoController extends BaseController {
	
	@Autowired
	private MortgageHouseInfoService mortgageHouseInfoService;
	
	@ModelAttribute
	public MortgageHouseInfo get(@RequestParam(required=false) String id) {
		MortgageHouseInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = mortgageHouseInfoService.get(id);
		}
		if (entity == null){
			entity = new MortgageHouseInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("credit:mortgageInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(ActTaskParam actTaskParam,MortgageHouseInfo mortgageHouseInfo,String gqgetFlag, HttpServletRequest request, HttpServletResponse response, Model model) {
		String applyNo = actTaskParam.getApplyNo();
		List<MortgageHouseInfo> mortgageHouseInfoList =mortgageHouseInfoService.getPageByApplyNo(applyNo);
	    model.addAttribute("mortgageHouseInfoList", mortgageHouseInfoList);
	    model.addAttribute("gqgetFlag", gqgetFlag);
	    if("2".equals(gqgetFlag)){
	    	return "app/credit/mortgageHouseInfo/gqGetMortgageHouseInfoList";
	    }
		return "app/credit/mortgageHouseInfo/mortgageHouseInfoList";
	}

	@RequiresPermissions("credit:mortgageInfo:view")
	@RequestMapping(value = "form")
	public String form(MortgageHouseInfo mortgageHouseInfo, Model model,HttpServletRequest request) {
		
		model.addAttribute("mortgageHouseInfo", mortgageHouseInfo);
		
		if("true".equals(request.getParameter("readOnly"))){
			model.addAttribute("readOnly", true);
		}
		return "app/credit/mortgageHouseInfo/mortgageHouseInfoForm";
	}

	@RequiresPermissions("credit:mortgageInfo:view")
	@RequestMapping(value = "save")
	public String save(MortgageHouseInfo mortgageHouseInfo, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		try{
			mortgageHouseInfoService.save(mortgageHouseInfo);
			model.addAttribute("message", "保存房产抵质押物成功");
		}catch(Exception e){
			logger.error("保存房产抵质押物失败",e);
			model.addAttribute("message", "保存房产抵质押物失败");
		}
		
		model.addAttribute("closeWindow", true);
		return form(mortgageHouseInfo, model,request);
	}
	
	@RequiresPermissions("credit:mortgageInfo:view")
	@RequestMapping(value = "delete")
	public String delete(MortgageHouseInfo mortgageHouseInfo, RedirectAttributes redirectAttributes) {
		mortgageHouseInfoService.delete(mortgageHouseInfo);
		addMessage(redirectAttributes, "删除房产抵质押物成功");
		return "redirect:"+Global.getAdminPath()+"/mortgageHouseInfo/mortgageHouseInfo/?repage";
	}
	
	
	@RequiresPermissions("credit:mortgageInfo:view")
	@RequestMapping(value = "evaluate")
	public String evaluate(MortgageHouseInfo mortgageHouseInfo, Model model,String applyNo,String flag) {
		if("1".equals(flag)){
			model.addAttribute("readonly", "true");
		}
		
		model.addAttribute("mortgageHouseInfo", mortgageHouseInfo);
		return "app/credit/mortgageHouseInfo/mortgageHouseEvaluateInfoForm";
	}
	
	
//	批量删除方法
	@RequiresPermissions("credit:mortgageInfo:view")
	@RequestMapping(value = "batchDelete")
	@ResponseBody
	public String batchDelete(String ids, HttpServletResponse response) {
		if(StringUtils.isNotEmpty(ids)){
			List<String> idList = Arrays.asList(ids.split(","));
			mortgageHouseInfoService.batchDelete(idList);
		}
		return "success";
	}

	@RequiresPermissions("credit:mortgageInfo:view")
	@RequestMapping(value = "toHouseEvaluateIndex")
	public String toHouseEvaluateIndex(String applyNo, Model model) {

		
		List<MortgageHouseInfo> mortgageHouseInfoList = mortgageHouseInfoService.getPageByApplyNo(applyNo);
		model.addAttribute("mortgageHouseInfoList", mortgageHouseInfoList);
		return "app/credit/mortgageEvaluateInfo/mortgageHouseEvaluateInfoList";
	}
	
	@RequiresPermissions("credit:mortgageInfo:view")
	@RequestMapping(value = "saveEvaluateHouse")
	public String saveEvaluateHouse(MortgageHouseInfo mortgageHouseInfo, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		try{
			mortgageHouseInfoService.save(mortgageHouseInfo);
			model.addAttribute("saveMessage", "保存房产抵质押物成功");
		}catch(Exception e){
			logger.error("保存房产抵质押物数据错误" + e.getMessage(), e);
			model.addAttribute("saveMessage", "保存房产抵质押物失败");
		}
		
		model.addAttribute("closeWindow", true);
		String flag = null;
		return evaluate(mortgageHouseInfo, model,mortgageHouseInfo.getApplyNo(),flag);
	}
}