package com.resoft.postloan.mortgageHouseInfo.web;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

import com.google.common.collect.Maps;
import com.resoft.postloan.common.utils.AjaxView;
import com.resoft.postloan.mortgageEvaluateInfo.entity.MortgageEvaluateInfo;
import com.resoft.postloan.mortgageEvaluateInfo.service.MortgageEvaluateInfoService;
import com.resoft.postloan.mortgageHouseInfo.entity.MortgageHouseInfo;
import com.resoft.postloan.mortgageHouseInfo.service.MortgageHouseInfoService;
import com.resoft.postloan.taskCenter.entity.ActTaskParam;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.service.AreaService;

/**
 * 房产抵质押物Controller
 * @author zhaohuakui
 * @version 2016-06-24
 */
@Controller("plMortgageHouseInfoController")
@RequestMapping(value = "${adminPath}/postloan/mortgageHouseInfo")
public class MortgageHouseInfoController extends BaseController {
	
	@Autowired
	private AreaService areaService;//区域地质service

	@Autowired
	private MortgageHouseInfoService mortgageHouseInfoService;
	
	@Autowired
	private MortgageEvaluateInfoService mortgageEvaluateInfoService;
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
	
	@RequestMapping(value = {"list", ""})
	public String list(ActTaskParam actTaskParam,MortgageHouseInfo mortgageHouseInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		String applyNo = actTaskParam.getApplyNo();
		List<MortgageHouseInfo> mortgageHouseInfoList =mortgageHouseInfoService.getPageByApplyNo(applyNo);
	    model.addAttribute("mortgageHouseInfoList", mortgageHouseInfoList);
	    return "app/postloan/mortgageEvaluateInfo/mortgageHouseEvaluateInfoList";
	}
	
	@RequestMapping(value = {"dealList", ""})
	public String dealList(ActTaskParam actTaskParam,MortgageHouseInfo mortgageHouseInfo,String readOnly, Model model) {
		String applyNo = actTaskParam.getApplyNo();
		List<MortgageHouseInfo> mortgageHouseInfoList =mortgageHouseInfoService.getPageByApplyNo(applyNo);
		model.addAttribute("mortgageHouseInfoList", mortgageHouseInfoList);
		model.addAttribute("readOnly", readOnly);
		return "app/postloan/mortgage/mortgageHouseList";
	}

	@RequestMapping(value = "form")
	public String form(MortgageHouseInfo mortgageHouseInfo, Model model,HttpServletRequest request) {
			model.addAttribute("readOnly", request.getParameter("readOnly"));
			try {
				//基本信息省市级联处理
				//省份下拉列表数据-注册地址+经营地址
				LinkedHashMap<String, String> provinceMap = loadAreaData("1");//这里的1表示全国
				model.addAttribute("contProvinceMap", provinceMap);
				//市级下拉列表数据-注册地址
				LinkedHashMap<String, String> regCityMap = loadAreaData(mortgageHouseInfo.getContProvince());
				model.addAttribute("contCityMap", regCityMap);
				//区县下拉列表数据-注册地址
				LinkedHashMap<String, String> regDistinctMap = loadAreaData(mortgageHouseInfo.getContCity());
				model.addAttribute("contDistinctMap", regDistinctMap);
				model.addAttribute("mortgageHouseInfo", mortgageHouseInfo);
				model.addAttribute("applyNo", mortgageHouseInfo.getApplyNo().replace("", ""));
				
					MortgageEvaluateInfo mortgageInfo =mortgageEvaluateInfoService.findMortgageByInfoId(mortgageHouseInfo.getId());
					if (mortgageInfo != null ){
						model.addAttribute("mortgageInfo", mortgageInfo);
				}
			} catch (Exception e) {
				logger.error("查询房屋和评估信息失败", e);
			} 
			return "app/postloan/mortgageHouseInfo/mortgageHouseEvaluateInfoForm";
	}

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
	
	@ResponseBody
	@RequestMapping(value = "delete")
	public AjaxView delete(MortgageHouseInfo mortgageHouseInfo, RedirectAttributes redirectAttributes) {
		AjaxView rtn = new AjaxView();
		addMessage(redirectAttributes, "删除房产抵质押物成功");
		try {
			mortgageHouseInfoService.delete(mortgageHouseInfo);
			rtn.setSuccess().setMessage("删除房产抵质押物成功");
		} catch (Exception e) {
			rtn.setSuccess().setMessage("删除房产抵质押物失败！");
			logger.error("保存失败！", e);
		}
		return rtn;
	}
	
//	省市级联数据加载
	private LinkedHashMap<String, String> loadAreaData(String areaCode){
		Map param = Maps.newConcurrentMap();
		LinkedHashMap<String, String> areaMap = new LinkedHashMap<String, String>();
		if(StringUtils.isNotEmpty(areaCode)){
			param.put("parentId", areaCode);//根据市级ID获取区县数据信息
			List<Map<String, String>> regDistinctList = this.areaService.getTreeNode(param);
			if (null != regDistinctList && regDistinctList.size() > 0) {
				for (Map<String, String> mp : regDistinctList) {
					areaMap.put(mp.get("id"), mp.get("name"));
				}
			}
		}
		return areaMap;
	}
	
	@RequestMapping(value = "evaluate")
	public String evaluate(MortgageHouseInfo mortgageHouseInfo,Model model,String applyNo,String flag) {
			model.addAttribute("readonly", "true");
			model.addAttribute("infoId", mortgageHouseInfo.getId());
			model.addAttribute("applyNo", applyNo);
		//基本信息省市级联处理
		//省份下拉列表数据-注册地址+经营地址
		LinkedHashMap<String, String> provinceMap = loadAreaData("1");//这里的1表示全国
		model.addAttribute("contProvinceMap", provinceMap);
		//市级下拉列表数据-注册地址
		LinkedHashMap<String, String> regCityMap = loadAreaData(mortgageHouseInfo.getContProvince());
		model.addAttribute("contCityMap", regCityMap);
		//区县下拉列表数据-注册地址
		LinkedHashMap<String, String> regDistinctMap = loadAreaData(mortgageHouseInfo.getContCity());
		model.addAttribute("contDistinctMap", regDistinctMap);
		model.addAttribute("mortgageHouseInfo", mortgageHouseInfo);
		try {
			MortgageEvaluateInfo mortgageInfo =mortgageEvaluateInfoService.findMortgageByInfoId(mortgageHouseInfo.getId());
			if (mortgageInfo != null ){
				model.addAttribute("mortgageInfo", mortgageInfo);
			}else{
				MortgageEvaluateInfo mortgageInfoCheck = new MortgageEvaluateInfo();
				mortgageInfoCheck.setInfoId(mortgageHouseInfo.getId());
				mortgageInfoCheck.setApplyNo(applyNo);
				model.addAttribute("mortgageInfo", mortgageInfoCheck);
			}
			
		} catch (Exception e) {
			logger.error("检查项目信息查询失败", e);
			model.addAttribute("message", "信息查询失败,请查看后台信息");
		}
		return "app/postloan/mortgageHouseInfo/mortgageCheckHouseItemsIndex";
	}
	
	@RequestMapping(value = "editEvaluate")
	public String editEvaluate(MortgageHouseInfo mortgageHouseInfo,Model model,String applyNo,String flag) {
		try {
			//基本信息省市级联处理
			//省份下拉列表数据-注册地址+经营地址
			LinkedHashMap<String, String> provinceMap = loadAreaData("1");//这里的1表示全国
			model.addAttribute("contProvinceMap", provinceMap);
			//市级下拉列表数据-注册地址
			LinkedHashMap<String, String> regCityMap = loadAreaData(mortgageHouseInfo.getContProvince());
			model.addAttribute("contCityMap", regCityMap);
			//区县下拉列表数据-注册地址
			LinkedHashMap<String, String> regDistinctMap = loadAreaData(mortgageHouseInfo.getContCity());
			model.addAttribute("contDistinctMap", regDistinctMap);
			model.addAttribute("mortgageHouseInfo", mortgageHouseInfo);
			model.addAttribute("applyNo", mortgageHouseInfo.getApplyNo().replace("", ""));
			
				MortgageEvaluateInfo mortgageInfo =mortgageEvaluateInfoService.findMortgageByInfoId(mortgageHouseInfo.getId());
				if (mortgageInfo != null ){
					model.addAttribute("mortgageInfo", mortgageInfo);
			}
		} catch (Exception e) {
			logger.error("查询房屋和评估信息失败", e);
		} 
		return "app/postloan/mortgageHouseInfo/mortgageHouseEvaluateInfoForm";
	}
//	批量删除方法
	@RequestMapping(value = "batchDelete")
	@ResponseBody
	public String batchDelete(String ids, HttpServletResponse response) {
		if(StringUtils.isNotEmpty(ids)){
			List<String> idList = Arrays.asList(ids.split(","));
			mortgageHouseInfoService.batchDelete(idList);
		}
		return "success";
	}

	@RequestMapping(value = "toHouseEvaluateIndex")
	public String toHouseEvaluateIndex(String applyNo, Model model) {
		model.addAttribute("applyNo", applyNo);
		List<MortgageHouseInfo> mortgageHouseInfoList = mortgageHouseInfoService.getPageByApplyNo(applyNo);
		model.addAttribute("mortgageHouseInfoList", mortgageHouseInfoList);
		return "app/postloan/mortgageEvaluateInfo/mortgageHouseEvaluateInfoList";
	}
	
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
		return evaluate(mortgageHouseInfo,model,mortgageHouseInfo.getApplyNo(),flag);
	}
}