package com.resoft.credit.mortgageCarEvaluateInfo.web;

import java.util.List;
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
import com.resoft.common.utils.Constants;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.mortgageCarEvaluateInfo.entity.MortgageCarEvaluateInfo;
import com.resoft.credit.mortgageCarEvaluateInfo.service.MortgageCarEvaluateInfoService;
import com.resoft.credit.mortgageCarInfo.entity.MortgageCarInfo;
import com.resoft.credit.mortgageCarInfo.service.MortgageCarInfoService;
import com.resoft.credit.mortgageHouseInfo.entity.MortgageHouseInfo;
import com.resoft.credit.mortgageOtherInfo.entity.MortgageOtherInfo;
import com.resoft.credit.processSuggestionInfo.entity.ProcessSuggestionInfo;
import com.resoft.credit.processSuggestionInfo.service.ProcessSuggestionInfoService;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 分公司风控审核-抵质押物信息Controller
 * 
 * @author yanwanmei
 * @version 2016-03-01
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/mortgageCarEvaluateInfo")
public class MortgageCarEvaluateInfoController extends BaseController {

	@Autowired
	private MortgageCarEvaluateInfoService mortgageCarEvaluateInfoService;

	@Autowired
	private MortgageCarInfoService mortgageCarInfoService;

	@Autowired
	private ProcessSuggestionInfoService processSuggestionInfoService;

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

	@RequiresPermissions("credit:mortgageInfo:view")
	@RequestMapping(value = "form")
	public String form(MortgageCarInfo mortgageCarInfo, MortgageCarEvaluateInfo mortgageCarEvaluateInfo, Model model) {
		//model.addAttribute("mortgageCarInfo", mortgageCarInfo);
		mortgageCarEvaluateInfo.setMortgageCarInfo(mortgageCarInfo);;
		model.addAttribute("mortgageCarEvaluateInfo", mortgageCarEvaluateInfo);
		return "app/credit/mortgageCarEvaluateInfo/mortgageCarEvaluateInfoForm";
	}

	@ResponseBody
	@RequiresPermissions("credit:mortgageInfo:edit")
	@RequestMapping(value = "save")
	public AjaxView save(MortgageCarInfo mortgageCarInfo, MortgageCarEvaluateInfo mortgageCarEvaluateInfo, ActTaskParam actTaskParam,String applyNo) {
		AjaxView ajaxView = new AjaxView();
	
		String carId = mortgageCarEvaluateInfo.getCarId();
		// 判断数据库中是否有此carId的数据
		MortgageCarEvaluateInfo hasMortgageCarEvaluateInfo = mortgageCarEvaluateInfoService.findListByCarId(carId);
		if (hasMortgageCarEvaluateInfo != null) {
			try {
				mortgageCarEvaluateInfoService.updateByCarId(mortgageCarEvaluateInfo);
				ajaxView.setSuccess().setMessage("保存车辆抵质押物评估信息成功！");
			} catch (Exception e) {
				logger.error("更新数据错误" + e.getMessage(), e);
				ajaxView.setFailed().setMessage("保存车辆抵质押物评估信息错误！");
			}
		} else {
			try {
				mortgageCarEvaluateInfoService.save(mortgageCarEvaluateInfo);
				ajaxView.setSuccess().setMessage("保存车辆抵质押物评估信息成功！");
			} catch (Exception e) {
				logger.error("保存数据错误" + e.getMessage(), e);
				ajaxView.setFailed().setMessage("保存车辆抵质押物评估信息错误！");
			}

		}
		ajaxView.setData(applyNo);
		mortgageCarInfo.setVehicleNo(mortgageCarEvaluateInfo.getVehicleNo());
		return ajaxView;
	}
	
	@RequiresPermissions("credit:mortgageInfo:view")
	@RequestMapping(value = "delete")
	public String delete(MortgageCarEvaluateInfo mortgageCarEvaluateInfo, RedirectAttributes redirectAttributes) {
		mortgageCarEvaluateInfoService.delete(mortgageCarEvaluateInfo);
		addMessage(redirectAttributes, "删除分公司风控审核-抵质押物信息成功");
		return "redirect:" + Global.getAdminPath() + "/mortgageCarEvaluateInfo/mortgageCarEvaluateInfo/?repage";
	}

	// 车辆抵质押物页面点击“编辑”后的跳转
	@RequiresPermissions("credit:mortgageInfo:view")
	@RequestMapping(value = "evaluate")
	public String evaluate(MortgageCarEvaluateInfo mortgageCarEvaluateInfo, MortgageCarInfo mortgageCarInfo, Model model,String applyNo,String flag) {
		if("1".equals(flag)){
			model.addAttribute("readonly", "true");
		}
		String carId = mortgageCarEvaluateInfo.getCarId();
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
		return "app/credit/mortgageCarEvaluateInfo/mortgageCarEvaluateInfoForm";
	}


	@RequiresPermissions("credit:mortgageInfo:view")
	@RequestMapping(value = { "list", "" })
	public String list(ActTaskParam actTaskParam, String readOnly, MortgageCarEvaluateInfo mortgageCarEvaluateInfo, MortgageCarInfo mortgageCarInfo, MortgageHouseInfo mortgageHouseInfo, MortgageOtherInfo mortgageOtherInfo, HttpServletRequest request, HttpServletResponse response, Model model) {

		model.addAttribute("actTaskParam", actTaskParam);
		model.addAttribute("readOnly", readOnly);

		String applyNo = actTaskParam.getApplyNo();

		// 根据applyNo和taskDefKey查询分析信息
		Map<String, String> params = Maps.newConcurrentMap();
		try {
			params.put("applyNo", applyNo);
			params.put("taskDefKey", Constants.UTASK_FGSFKSH);
		} catch (Exception e) {
			logger.error("流程参数丢失！", e);
			model.addAttribute("suggMessage", "流程参数丢失，请联系管理员！");
		}

		ProcessSuggestionInfo processSuggestionInfo = processSuggestionInfoService.findByApplyNo(params);
		model.addAttribute("processSuggestionInfo", processSuggestionInfo);
		return "app/credit/mortgageEvaluateInfo/mortgageEvaluateIndex";
	}

	@RequiresPermissions("credit:mortgageInfo:view")
	@RequestMapping(value = "toCarEvaluateIndex")
	public String toCarEvaluateIndex(String applyNo, Model model) {
		List<MortgageCarInfo> mortgageCarInfoList = mortgageCarInfoService.getPageByApplyNo(applyNo);
		
		for(int i=0;i<mortgageCarInfoList.size();i++){
			MortgageCarEvaluateInfo mortgageCarEvaluateInfo = mortgageCarEvaluateInfoService.findListByCarId(mortgageCarInfoList.get(i).getId());
			if(mortgageCarEvaluateInfo!=null){
				mortgageCarInfoList.get(i).setVehicleNo(mortgageCarEvaluateInfo.getVehicleNo());
			}
		}
		model.addAttribute("applyNo", applyNo);
		model.addAttribute("mortgageCarInfoList", mortgageCarInfoList);
		return "app/credit/mortgageEvaluateInfo/mortgageCarEvaluateInfoList";
	}

}