package com.resoft.credit.experianDecisionAnalysis.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.resoft.outinterface.cn.com.experian.entry.response.ExperianResponse;
import com.resoft.outinterface.cn.com.experian.entry.response.ExperianResponseHeader;
import com.resoft.outinterface.cn.com.experian.entry.response.Resinfo;
import com.resoft.outinterface.cn.com.experian.service.ExperianClientService;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 外访费登记Controller
 * 
 * @author yanwanmei
 * @version 2016-02-29
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/experianDecisionAnalysis")
public class ExperianDecisionAnalysisController extends BaseController {


	@Autowired
	private ExperianClientService experianClientService;
	@RequiresPermissions("credit:supportDecision:view")
	@RequestMapping(value = { "list", "" })
	public String list(String applyNo, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		ExperianResponseHeader experianResponseHeader = experianClientService.getExperianResponseHeaderByApplyNo(applyNo);
		if(experianResponseHeader == null){
			experianResponseHeader =  new ExperianResponseHeader();
		}
		Resinfo resinfo = new Resinfo();
		if(experianResponseHeader.getId() != null && !"".equals(experianResponseHeader.getId())){
			resinfo = experianClientService.getResinfoById(experianResponseHeader.getId());
		}
		Map<String, Integer> map = experianClientService.scoringIndex(resinfo.getScoringScore());
		ExperianResponse experianResponse = new ExperianResponse();
		experianResponse.setHead(experianResponseHeader);
		experianResponse.setBody(resinfo);
		
		model.addAttribute("experianResponse", experianResponse);
		model.addAttribute("star", map);
		return "app/credit/experianDecisionAnalysis/experianDecisionAnalysisList";
	}


}