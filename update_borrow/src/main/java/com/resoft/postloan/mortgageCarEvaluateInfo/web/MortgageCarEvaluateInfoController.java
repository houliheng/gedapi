package com.resoft.postloan.mortgageCarEvaluateInfo.web;

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
import com.resoft.postloan.mortgageCarEvaluateInfo.entity.MortgageCarEvaluateInfo;
import com.resoft.postloan.mortgageCarEvaluateInfo.service.MortgageCarEvaluateInfoService;
import com.resoft.postloan.mortgageCarInfo.entity.MortgageCarInfo;
import com.resoft.postloan.mortgageCarInfo.service.MortgageCarInfoService;
import com.resoft.postloan.mortgageEvaluateInfo.entity.MortgageEvaluateInfo;
import com.resoft.postloan.mortgageEvaluateInfo.service.MortgageEvaluateInfoService;
import com.resoft.postloan.taskCenter.entity.ActTaskParam;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.service.AreaService;

/**
 * 抵质押物信息Controller
 * 
 * @author yanwanmei
 * @version 2016-03-01
 */
@Controller("plMortgageCarEvaluateInfoController")
@RequestMapping(value = "${adminPath}/postloan/mortgageCarEvaluateInfo")
public class MortgageCarEvaluateInfoController extends BaseController {

	@Autowired
	private AreaService areaService;// 区域地质service

	@Autowired
	private MortgageCarEvaluateInfoService mortgageCarEvaluateInfoService;

	@Autowired
	private MortgageCarInfoService mortgageCarInfoService;

	@Autowired
	private MortgageEvaluateInfoService mortgageEvaluateInfoService;

	@ModelAttribute
	public MortgageCarEvaluateInfo get(@RequestParam(required = false) String id) {
		MortgageCarEvaluateInfo entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = mortgageCarEvaluateInfoService.get(id);
		}
		if (entity == null) {
			entity = new MortgageCarEvaluateInfo();
		}
		return entity;
	}

	@RequestMapping(value = "form")
	public String form(MortgageCarInfo mortgageCarInfo, MortgageCarEvaluateInfo mortgageCarEvaluateInfo, Model model) {
		// model.addAttribute("mortgageCarInfo", mortgageCarInfo);
		try {
			// 基本信息省市级联处理
			// 省份下拉列表数据-注册地址+经营地址
			LinkedHashMap<String, String> provinceMap = loadAreaData("1");// 这里的1表示全国
			model.addAttribute("regProvinceMap", provinceMap);
			mortgageCarEvaluateInfo.setMortgageCarInfo(mortgageCarInfo);
			model.addAttribute("mortgageCarEvaluateInfo", mortgageCarEvaluateInfo);
		} catch (Exception e) {
			logger.error("查询信息失败", e);
		}
		return "app/postloan/mortgageCarEvaluateInfo/mortgageCarEvaluateInfoForm";
	}

	// 保存评估信息
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxView save(MortgageCarInfo mortgageCarInfo, MortgageCarEvaluateInfo mortgageCarEvaluateInfo, ActTaskParam actTaskParam, String applyNo) {
		AjaxView ajaxView = new AjaxView();
		mortgageCarInfo = mortgageCarEvaluateInfo.getMortgageCarInfo();
		String carId = mortgageCarEvaluateInfo.getCarId();
		// 判断数据库中是否有此carId的数据
		MortgageCarEvaluateInfo hasMortgageCarEvaluateInfo = mortgageCarEvaluateInfoService.findListByCarId(carId);
		if (hasMortgageCarEvaluateInfo != null) {
			try {
				mortgageCarInfo.setId(carId);
				mortgageCarEvaluateInfoService.updateByCarId(mortgageCarEvaluateInfo, mortgageCarInfo);
				ajaxView.setSuccess().setMessage("保存车辆抵质押物评估信息成功！");
			} catch (Exception e) {
				logger.error("更新数据错误" + e.getMessage(), e);
				ajaxView.setFailed().setMessage("保存车辆抵质押物评估信息错误！");
			}
		} else {
			try {
				mortgageCarInfo.setId(IdGen.uuid());
				mortgageCarEvaluateInfo.setCarId(mortgageCarInfo.getId());
				mortgageCarEvaluateInfoService.save(mortgageCarEvaluateInfo, mortgageCarInfo);
				ajaxView.setSuccess().setMessage("保存车辆抵质押物评估信息成功！");
			} catch (Exception e) {
				logger.error("保存数据错误" + e.getMessage(), e);
				ajaxView.setFailed().setMessage("保存车辆抵质押物评估信息错误！");
			}
		}
		ajaxView.setData(mortgageCarInfo.getApplyNo());
		mortgageCarInfo.setVehicleNo(mortgageCarEvaluateInfo.getVehicleNo());
		return ajaxView;
	}

	@ResponseBody
	@RequestMapping(value = "delete")
	public AjaxView delete(MortgageCarEvaluateInfo mortgageCarEvaluateInfo, MortgageCarInfo mortgageCarInfo, RedirectAttributes redirectAttributes) {
		AjaxView rtn = new AjaxView();
		mortgageCarInfo.setId(mortgageCarEvaluateInfo.getId());
		try {
			mortgageCarEvaluateInfoService.delete(mortgageCarInfo);
			rtn.setSuccess().setMessage("删除抵质押物信息成功");
		} catch (Exception e) {
			rtn.setSuccess().setMessage("删除抵质押物信息失败！");
			logger.error("删除抵质押物信息失败！", e);
		}
		return rtn;
	}

	// 车辆抵质押物页面详情
	@RequestMapping(value = "detail")
	public String evaluate(MortgageCarEvaluateInfo mortgageCarEvaluateInfo, MortgageCarInfo mortgageCarInfo, Model model, String applyNo, String flag) {
		model.addAttribute("readonly", "true");
		String carId = mortgageCarInfo.getId();
		// 根据评估信息的carId查找车辆抵质押物数据
		mortgageCarInfo = mortgageCarEvaluateInfoService.findMortgageCarByCarId(carId);

		// 根据carId查找数据库中是否已有车辆抵质押物评估数据
		mortgageCarEvaluateInfo = mortgageCarEvaluateInfoService.findListByCarId(carId);

		if (mortgageCarEvaluateInfo == null) {
			mortgageCarEvaluateInfo = new MortgageCarEvaluateInfo();
		}
		mortgageCarEvaluateInfo.setMortgageCarInfo(mortgageCarInfo);
		model.addAttribute("mortgageCarInfo", mortgageCarInfo);
		model.addAttribute("mortgageCarEvaluateInfo", mortgageCarEvaluateInfo);

		model.addAttribute("applyNo", applyNo);
		return "app/postloan/mortgageCarEvaluateInfo/mortgageCarEvaluateInfoForm";
	}

	// 车辆抵质押物页面点击“编辑”后的跳转
	@RequestMapping(value = "evaluate")
	public String evaluate(MortgageEvaluateInfo mortgageEvaluateInfo, MortgageCarEvaluateInfo mortgageCarEvaluateInfo, MortgageCarInfo mortgageCarInfo, Model model, String applyNo, String flag, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("readonly", "true");
		String carId = mortgageEvaluateInfo.getId();
		try {
			model.addAttribute("infoId", mortgageEvaluateInfo.getId());
			// 根据评估信息的carId查找车辆抵质押物数据
			mortgageCarInfo = mortgageCarEvaluateInfoService.findMortgageCarByCarId(carId);
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

			// 根据carId查找数据库中是否已有车辆抵质押物评估数据
			mortgageCarEvaluateInfo = mortgageCarEvaluateInfoService.findListByCarId(carId);

			if (mortgageCarEvaluateInfo == null) {
				mortgageCarEvaluateInfo = new MortgageCarEvaluateInfo();
			}
			mortgageCarEvaluateInfo.setMortgageCarInfo(mortgageCarInfo);
			model.addAttribute("mortgageCarInfo", mortgageCarInfo);
			model.addAttribute("mortgageCarEvaluateInfo", mortgageCarEvaluateInfo);
			model.addAttribute("applyNo", mortgageCarInfo.getApplyNo());
		} catch (Exception e) {
			logger.error("查询失败", e);
			model.addAttribute("message", "信息查询失败,请查看后台信息");
		}
		try {
			MortgageEvaluateInfo mortgageInfo = mortgageEvaluateInfoService.findMortgageByInfoId(mortgageCarInfo.getId());
			if (mortgageInfo != null) {
				model.addAttribute("mortgageInfo", mortgageInfo);
			} else {
				MortgageEvaluateInfo mortgageInfoCheck = new MortgageEvaluateInfo();
				mortgageInfoCheck.setInfoId(mortgageCarInfo.getId());
				mortgageInfoCheck.setApplyNo(mortgageCarInfo.getApplyNo());
				model.addAttribute("mortgageInfo", mortgageInfoCheck);
			}

		} catch (Exception e) {
			logger.error("检查项目信息查询失败", e);
			model.addAttribute("message", "信息查询失败,请查看后台信息");
		}
		return "app/postloan/mortgageCarInfo/mortgageCheckCarItemsIndex";
	}

	// 车辆抵质押物页面点击“修改”后的跳转
	@RequestMapping(value = "editEvaluate")
	public String editEvaluate(MortgageEvaluateInfo mortgageEvaluateInfo, MortgageCarEvaluateInfo mortgageCarEvaluateInfo, MortgageCarInfo mortgageCarInfo, Model model, String applyNo, String flag, HttpServletRequest request, HttpServletResponse response) {
		try {
			String carId = mortgageCarEvaluateInfo.getCarId();
			// 根据评估信息的carId查找车辆抵质押物数据
			mortgageCarInfo = mortgageCarEvaluateInfoService.findMortgageCarByCarId(carId);
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

			// 根据carId查找数据库中是否已有车辆抵质押物评估数据
			mortgageCarEvaluateInfo = mortgageCarEvaluateInfoService.findListByCarId(carId);

			if (mortgageCarEvaluateInfo == null) {
				mortgageCarEvaluateInfo = new MortgageCarEvaluateInfo();
			}
			mortgageCarEvaluateInfo.setMortgageCarInfo(mortgageCarInfo);
			model.addAttribute("mortgageCarInfo", mortgageCarInfo);
			model.addAttribute("mortgageCarEvaluateInfo", mortgageCarEvaluateInfo);
			model.addAttribute("applyNo", mortgageCarInfo.getApplyNo());
		} catch (Exception e) {
			logger.error("检查车辆信息和车辆评估信息失败", e);
			model.addAttribute("message", "信息查询失败,请查看后台信息");
		}
		return "app/postloan/mortgageCarEvaluateInfo/mortgageCarEvaluateInfoForm";
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

	@RequestMapping(value = "toCarEvaluateIndex")
	public String toCarEvaluateIndex(String applyNo, Model model) {
		List<MortgageCarInfo> mortgageCarInfoList = mortgageCarInfoService.getPageByApplyNo(applyNo);

		for (int i = 0; i < mortgageCarInfoList.size(); i++) {
			MortgageCarEvaluateInfo mortgageCarEvaluateInfo = mortgageCarEvaluateInfoService.findListByCarId(mortgageCarInfoList.get(i).getId());
			if (mortgageCarEvaluateInfo != null) {
				mortgageCarInfoList.get(i).setVehicleNo(mortgageCarEvaluateInfo.getVehicleNo());
			}
		}
		model.addAttribute("applyNo", applyNo);
		model.addAttribute("mortgageCarInfoList", mortgageCarInfoList);
		return "app/postloan/mortgageEvaluateInfo/mortgageCarEvaluateInfoList";
	}

}