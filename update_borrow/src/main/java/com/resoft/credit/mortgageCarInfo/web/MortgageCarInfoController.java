package com.resoft.credit.mortgageCarInfo.web;

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
import com.resoft.credit.mortgageCarInfo.entity.MortgageCarInfo;
import com.resoft.credit.mortgageCarInfo.entity.gqGetMortgageCarInfo;
import com.resoft.credit.mortgageCarInfo.service.MortgageCarInfoService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 车辆抵质押物信息Controller
 * 
 * @author yanwanmei
 * @version 2016-02-29
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/mortgageCarInfo")
public class MortgageCarInfoController extends BaseController {

	@Autowired
	private MortgageCarInfoService mortgageCarInfoService;

	@ModelAttribute
	public MortgageCarInfo get(@RequestParam(required = false) String id) {
		MortgageCarInfo entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = mortgageCarInfoService.get(id);
		}
		if (entity == null) {
			entity = new MortgageCarInfo();
		}
		return entity;
	}

	@RequiresPermissions("credit:mortgageInfo:view")
	@RequestMapping(value = { "list", "" })
	public String list(ActTaskParam actTaskParam, String gqgetFlag ,MortgageCarInfo mortgageCarInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		String applyNo = actTaskParam.getApplyNo();
		List<MortgageCarInfo> mortgageCarInfoList = mortgageCarInfoService.getPageByApplyNo(applyNo);
		model.addAttribute("mortgageCarInfoList", mortgageCarInfoList);
		model.addAttribute("gqgetFlag", gqgetFlag);
		if("2".equals(gqgetFlag)){
			List<gqGetMortgageCarInfo> mortCarList = mortgageCarInfoService.getGqPageByApplyNo(applyNo);
			model.addAttribute("mortCarList", mortCarList);
			return "app/credit/mortgageCarInfo/gqGetMortgageCarInfoList";
		}
		return "app/credit/mortgageCarInfo/mortgageCarInfoList";
	}

	@RequiresPermissions("credit:mortgageInfo:view")
	@RequestMapping(value = "form")
	public String form(MortgageCarInfo mortgageCarInfo, Model model, HttpServletRequest request) {

		model.addAttribute("mortgageCarInfo", mortgageCarInfo);

		if ("true".equals(request.getParameter("readOnly"))) {
			model.addAttribute("readOnly", true);
		}
		return "app/credit/mortgageCarInfo/mortgageCarInfoForm";
	}

	@RequiresPermissions("credit:mortgageInfo:view")
	@RequestMapping(value = "save")
	public String save(MortgageCarInfo mortgageCarInfo, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, mortgageCarInfo)) {
			return form(mortgageCarInfo, model, request);
		}
		try{
			mortgageCarInfoService.save(mortgageCarInfo);
			model.addAttribute("message", "保存车辆抵质押物信息成功");
		}catch(Exception e){
			logger.error("保存车辆抵质押物信息失败", e);
			model.addAttribute("message", "保存车辆抵质押物信息失败");
		}
		
		model.addAttribute("closeWindow", true);
		return form(mortgageCarInfo, model, request);
	}


	// 批量删除方法
	@RequiresPermissions("credit:mortgageInfo:view")
	@RequestMapping(value = "batchDelete")
	@ResponseBody
	public String batchDelete(String ids, HttpServletResponse response) {
		if (StringUtils.isNotEmpty(ids)) {
			List<String> idList = Arrays.asList(ids.split(","));
			mortgageCarInfoService.batchDelete(idList);
		}
		return "success";
	}

	@RequiresPermissions("credit:mortgageInfo:view")
	@RequestMapping(value = "mortgage")
	public String mortgage(ActTaskParam actTaskParam, String readOnly, Model model) {
		model.addAttribute("actTaskParam", actTaskParam);
		model.addAttribute("readOnly", readOnly);
		return "app/credit/mortgageCarInfo/mortgageIndex";
	}

}