package com.resoft.postloan.mortgage.web;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.resoft.postloan.common.utils.AjaxView;
import com.resoft.postloan.mortgage.entity.Mortgage;
import com.resoft.postloan.mortgage.service.MortgageService;
import com.resoft.postloan.taskCenter.entity.ActTaskParam;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 合同展期还款计划Controller
 * 
 * @author wangguodong
 * @version 2017-01-06
 */
@Controller
@RequestMapping(value = "${adminPath}/postloan/mortgage")
public class MortgageController extends BaseController {

	@Autowired
	MortgageService mortgageService;

	@RequestMapping(value = "toDeal")
	public String toDeal(ActTaskParam actTaskParam, Mortgage mortgage, String mortgageFlag, Model model) {
		actTaskParam.setApplyNo(mortgage.getDealApplyNo());
		model.addAttribute("mortgage", mortgage);
		model.addAttribute("mortgageFlag", mortgageFlag);
		model.addAttribute("actTaskParam", actTaskParam);
		return "app/postloan/mortgage/dealTaskForm";
	}

	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxView save(Mortgage mortgage, String mortgageFlag) {
		AjaxView ajaxView = new AjaxView();
		mortgage.setDealStatus("1");
		try {
			if ("car".equals(mortgageFlag)) {
				mortgageService.updateMortgageCarData(mortgage);
			} else if ("house".equals(mortgageFlag)) {
				mortgageService.updateMortgageHouseData(mortgage);
			} else if ("other".equals(mortgageFlag)) {
				mortgageService.updateMortgageOtherData(mortgage);
			} else {
				// mortgageFlag 为空
				ajaxView.setFailed().setMessage("无法区分抵质押物类型，保存失败");
				return ajaxView;
			}
			ajaxView.put("dealApplyNo", mortgage.getDealApplyNo());
			ajaxView.setSuccess().setMessage("保存处置信息成功");
		} catch (Exception e) {
			logger.error("保存处置信息失败", e);
			ajaxView.setFailed().setMessage("保存处置信息失败");
		}
		return ajaxView;
	}

	@ResponseBody
	@RequestMapping(value = "getDealAllAmountFuntion")
	public AjaxView save(String applyNo) {
		AjaxView ajaxView = new AjaxView();
		BigDecimal bigDecimal = new BigDecimal(0);
		//是否存在未处置抵押物 0：存在 1：不存在
		String status = "1";
		try {
			List<Map<String, Object>> maps = mortgageService.getAllDealAmount(applyNo);
			for (Map<String, Object> map : maps) {
				bigDecimal = bigDecimal.add(new BigDecimal(map.get("dealAmount").toString()));
				if("1".equals(status)){
					if("0".equals(map.get("dealStatus"))){
						status = "0";
					}
				}
			}
			ajaxView.put("dealAllAmount", bigDecimal.toString());
			ajaxView.put("dealStatus", status);
			ajaxView.setSuccess().setMessage("保存处置信息成功");
		} catch (Exception e) {
			logger.error("保存处置信息失败", e);
			ajaxView.setFailed().setMessage("保存处置信息失败");
		}
		return ajaxView;
	}

}
