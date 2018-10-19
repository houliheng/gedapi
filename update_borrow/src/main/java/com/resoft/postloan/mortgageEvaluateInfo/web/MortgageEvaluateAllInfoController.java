package com.resoft.postloan.mortgageEvaluateInfo.web;


import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.resoft.postloan.mortgageCarEvaluateInfo.entity.MortgageCarEvaluateInfo;
import com.resoft.postloan.mortgageCarEvaluateInfo.service.MortgageCarEvaluateInfoService;
import com.resoft.postloan.mortgageCarInfo.entity.MortgageCarInfo;
import com.resoft.postloan.mortgageCarInfo.service.MortgageCarInfoService;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 抵质押物管理Controller
 * @author zhaohuakui
 * @version 2016-05-25
 * @param <MortgageEvaluateItems>
 */
@Controller
@RequestMapping(value = "${adminPath}/postloan/mortgageEvaluateInfo")
public class MortgageEvaluateAllInfoController extends BaseController {

	/*抵(质)押物管理*/
	@Autowired
	private MortgageCarEvaluateInfoService mortgageCarEvaluateInfoService;
	@Autowired
	private MortgageCarInfoService mortgageCarInfoService;
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