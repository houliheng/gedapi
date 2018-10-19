package com.resoft.postloan.mortgageCarInfo.web;

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
import com.resoft.postloan.mortgageCarInfo.entity.MortgageCarInfo;
import com.resoft.postloan.mortgageCarInfo.service.MortgageCarInfoService;
import com.resoft.postloan.taskCenter.entity.ActTaskParam;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.service.AreaService;

/**
 * 车辆抵质押物信息Controller
 * 
 * @author yanwanmei
 * @version 2016-02-29
 */
@Controller("plMortgageCarInfoController")
@RequestMapping(value = "${adminPath}/postloan/mortgageCarInfo")
public class MortgageCarInfoController extends BaseController {

	@Autowired
	private AreaService areaService;// 区域地质service

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

	@RequestMapping(value = { "list", "" })
	public String list(ActTaskParam actTaskParam, MortgageCarInfo mortgageCarInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		String applyNo = actTaskParam.getApplyNo();
		List<MortgageCarInfo> mortgageCarInfoList = mortgageCarInfoService.getPageByApplyNo(applyNo);
		model.addAttribute("mortgageCarInfoList", mortgageCarInfoList);
		return "app/postloan/mortgageCarInfo/mortgageCarInfoList";
	}
	
	@RequestMapping(value = { "dealList", "" })
	public String dealList(ActTaskParam actTaskParam, MortgageCarInfo mortgageCarInfo,String readOnly, Model model) {
		String applyNo = actTaskParam.getApplyNo();
		List<MortgageCarInfo> mortgageCarInfoList = mortgageCarInfoService.getPageByApplyNo(applyNo);
		model.addAttribute("mortgageCarInfoList", mortgageCarInfoList);
		model.addAttribute("readOnly", readOnly);
		return "app/postloan/mortgage/mortgageCarList";
	}

	@RequestMapping(value = "form")
	public String form(MortgageCarInfo mortgageCarInfo, Model model, HttpServletRequest request) {
		// 基本信息省市级联处理
		// 省份下拉列表数据-注册地址+经营地址
		LinkedHashMap<String, String> provinceMap = loadAreaData("1");// 这里的1表示全国
		model.addAttribute("regProvinceMap", provinceMap);
		// 市级下拉列表数据-注册地址
		LinkedHashMap<String, String> regCityMap = loadAreaData(mortgageCarInfo.getOperateProvince());
		model.addAttribute("regCityMap", regCityMap);
		// 区县下拉列表数据-注册地址
		LinkedHashMap<String, String> regDistinctMap = loadAreaData(mortgageCarInfo.getOperateCity());
		model.addAttribute("regDistinctMap", regDistinctMap);
		model.addAttribute("mortgageHouseInfo", mortgageCarInfo);

		model.addAttribute("mortgageCarInfo", mortgageCarInfo);

		if ("true".equals(request.getParameter("readOnly"))) {
			model.addAttribute("readOnly", true);
		}
		return "app/postloan/mortgageCarInfo/mortgageCarInfoForm";
	}

	@RequestMapping(value = "save")
	public String save(MortgageCarInfo mortgageCarInfo, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
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

	// 省市级联数据加载
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private LinkedHashMap<String, String> loadAreaData(String areaCode) {
		Map param = Maps.newConcurrentMap();
		LinkedHashMap<String, String> areaMap = new LinkedHashMap<String, String>();
		if (StringUtils.isNotEmpty(areaCode)) {
			param.put("parentId", areaCode);// 根据市级ID获取区县数据信息
			List<Map<String, String>> regDistinctList = this.areaService.getTreeNode(param);
			if (null != regDistinctList && regDistinctList.size() > 0) {
				for (Map<String, String> mp : regDistinctList) {
					areaMap.put(mp.get("id"), mp.get("name"));
				}
			}
		}
		return areaMap;
	}

	// 批量删除方法
	@RequestMapping(value = "batchDelete")
	@ResponseBody
	public String batchDelete(String ids, HttpServletResponse response) {
		if (StringUtils.isNotEmpty(ids)) {
			List<String> idList = Arrays.asList(ids.split(","));
			mortgageCarInfoService.batchDelete(idList);
		}
		return "success";
	}

	@RequestMapping(value = "mortgage")
	public String mortgage(ActTaskParam actTaskParam, String readOnly, Model model) {
		model.addAttribute("actTaskParam", actTaskParam);
		model.addAttribute("readOnly", readOnly);
		return "app/postloan/mortgageCarInfo/mortgageIndex";
	}


}