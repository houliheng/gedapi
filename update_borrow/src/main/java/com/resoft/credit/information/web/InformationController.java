package com.resoft.credit.information.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Maps;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.checkCoupleDoubtful.entity.CheckCoupleDoubtful;
import com.resoft.credit.checkCoupleDoubtful.service.CheckCoupleDoubtfulService;
import com.resoft.credit.checkDoubtful.entity.CheckDoubtful;
import com.resoft.credit.checkDoubtful.service.CheckDoubtfulService;
import com.resoft.credit.checkPhone.entity.CheckPhone;
import com.resoft.credit.checkPhone.service.CheckPhoneService;
import com.resoft.credit.checkWeb.entity.CheckWeb;
import com.resoft.credit.checkWeb.service.CheckWebService;
import com.thinkgem.jeesite.common.web.BaseController;

@Controller
@RequestMapping(value = "${adminPath}/credit/information")
public class InformationController extends BaseController {

	@Autowired
	private CheckDoubtfulService checkDoubtfulService;

	@Autowired
	private CheckCoupleDoubtfulService checkCoupleDoubtfulService;

	@Autowired
	private CheckPhoneService checkPhoneService;

	@Autowired
	private CheckWebService checkWebService;

	 
	@RequiresPermissions("credit:information:view")
	@RequestMapping(value = "index")
	public String list(ActTaskParam actTaskParam,CheckDoubtful checkDoubtful,CheckCoupleDoubtful checkCoupleDoubtful, CheckPhone checkPhone,CheckWeb checkWeb, Model model,HttpServletRequest request,
			HttpServletResponse response) {
		String applyNo = actTaskParam.getApplyNo();
		
		List<CheckDoubtful> checkDoubtfullList = checkDoubtfulService.getPageByApplyNo(applyNo);
	    model.addAttribute("checkDoubtfullList", checkDoubtfullList);
	    
		Map<String,String> params =  Maps.newConcurrentMap();
		params.put("applyNo", applyNo);
	    List<CheckCoupleDoubtful> checkCoupleDoubtfulList = checkCoupleDoubtfulService.getCheckCoupleDoubtfulByApplyNo(params);
	    model.addAttribute("checkCoupleDoubtfulList", checkCoupleDoubtfulList);

	    List<CheckPhone> checkPhoneList = checkPhoneService.getCheckPhoneByApplyNo(applyNo);
	    model.addAttribute("checkPhoneList", checkPhoneList);
	    
	    List<CheckWeb> checkWebList = checkWebService.getCheckWebByApplyNo(applyNo);
	    model.addAttribute("checkWebList", checkWebList);
	    
		return "app/credit/information/informationList";
	}

}
